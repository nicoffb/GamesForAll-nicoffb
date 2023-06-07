import 'dart:async';

import 'package:bloc/bloc.dart';
import 'package:bloc_concurrency/bloc_concurrency.dart';
import 'package:equatable/equatable.dart';
import 'package:front/service/plato_service.dart';
import 'package:stream_transform/stream_transform.dart';

import '../config/locator.dart';
import '../model/plato_list_result.dart';
import '../model/restaurante_detail.dart';
import '../service/restaurant_service.dart';

part 'restaurant_menu_event.dart';
part 'restaurant_menu_state.dart';

const throttleDuration = Duration(milliseconds: 100);

EventTransformer<E> throttleDroppable<E>(Duration duration) {
  return (events, mapper) {
    return droppable<E>().call(events.throttle(duration), mapper);
  };
}

class RestaurantMenuBloc
    extends Bloc<RestaurantMenuEvent, RestaurantMenuState> {
  late final RestaurantService _restaurantService;
  late final PlatoService _platoService;

  RestaurantMenuBloc() : super(RestaurantMenuInitial()) {
    _restaurantService = getIt<RestaurantService>();
    _platoService = getIt<PlatoService>();
    on<RestaurantFetched>(
      _onRestaurantFetched,
    );
    on<NextPlatosFetched>(
      _onNextPlatosFetched,
    );
    on<SearchPlatosEvent>(
      _onSearchPlatosEvent,
    );
  }

  Future<void> _onRestaurantFetched(
    RestaurantFetched event,
    Emitter<RestaurantMenuState> emit,
  ) async {
    var restauranteDetails;
    var platos;
    try {
      if (state.status == RestaurantMenuStatus.initial) {
        restauranteDetails =
            await _restaurantService.getRestaurantDetails(event.restaurantId);
      }
    } catch (_) {
      return emit(state.copyWith(
          id: event.restaurantId, status: RestaurantMenuStatus.failure));
    }
    try {
      platos = await _platoService.getByRestaurant(event.restaurantId, 0);
    } catch (_) {
      return emit(state.copyWith(
          restaurante: restauranteDetails,
          id: event.restaurantId,
          status: RestaurantMenuStatus.noneFound));
    }
    return emit(
      state.copyWith(
          id: event.restaurantId,
          status: RestaurantMenuStatus.success,
          restaurante: restauranteDetails,
          platos: platos.contenido,
          hasReachedMax: platos.paginaActual! + 1 >= platos.numeroPaginas!,
          currentPage: 0),
    );
  }

  FutureOr<void> _onNextPlatosFetched(
      NextPlatosFetched event, Emitter<RestaurantMenuState> emit) async {
    if (state.hasReachedMax) return;
    try {
      if (state.status == RestaurantMenuStatus.success) {
        final platos = await _platoService.getByRestaurant(
            state.id, state.currentPage + 1);
        emit(
          state.copyWith(
              status: RestaurantMenuStatus.success,
              platos: List.of(state.platos)..addAll(platos.contenido!),
              hasReachedMax: platos.paginaActual! + 1 >= platos.numeroPaginas!,
              currentPage: state.currentPage + 1),
        );
      } else if (state.status == RestaurantMenuStatus.searchFound) {
        final platos = await _platoService.searchByRestaurant(
            state.id, state.lastSearch, state.currentPage + 1);
        emit(
          state.copyWith(
              status: RestaurantMenuStatus.success,
              platos: List.of(state.platos)..addAll(platos.contenido!),
              hasReachedMax: platos.paginaActual! + 1 >= platos.numeroPaginas!,
              currentPage: state.currentPage + 1),
        );
      }
    } catch (_) {
      emit(state.copyWith(status: RestaurantMenuStatus.noneFound));
    }
  }

  FutureOr<void> _onSearchPlatosEvent(
      SearchPlatosEvent event, Emitter<RestaurantMenuState> emit) async {
    try {
      String searchString = "search=";
      if (event.busqueda != null) searchString += "nombre:${event.busqueda!},";
      if (event.maxPriceValue != null)
        searchString += "precio<${event.maxPriceValue!},";
      if (event.minPriceValue != null)
        searchString += "precio>${event.minPriceValue!},";
      if (event.noGluten != null)
        searchString += "sinGluten:${event.noGluten! ? 1 : 0},";
      final platos =
          await _platoService.searchByRestaurant(state.id, searchString, 0);
      return emit(
        state.copyWith(
            status: RestaurantMenuStatus.searchFound,
            platos: platos.contenido,
            hasReachedMax: platos.paginaActual! + 1 >= platos.numeroPaginas!,
            currentPage: 0,
            lastSearch: searchString),
      );
    } catch (_) {
      emit(state.copyWith(status: RestaurantMenuStatus.noneFound));
    }
  }
}
