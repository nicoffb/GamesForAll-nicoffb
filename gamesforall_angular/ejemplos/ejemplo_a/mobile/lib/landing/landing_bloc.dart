import 'dart:async';

import 'package:bloc/bloc.dart';
import 'package:bloc_concurrency/bloc_concurrency.dart';
import 'package:equatable/equatable.dart';
import 'package:front/service/restaurant_service.dart';
import 'package:get_it/get_it.dart';
import 'package:meta/meta.dart';
import 'package:stream_transform/stream_transform.dart';

import '../config/locator.dart';
import '../model/RestauranteListResult.dart';
import 'package:http/http.dart' as http;

part 'landing_event.dart';
part 'landing_state.dart';

const _postLimit = 20;
const throttleDuration = Duration(milliseconds: 100);

EventTransformer<E> throttleDroppable<E>(Duration duration) {
  return (events, mapper) {
    return droppable<E>().call(events.throttle(duration), mapper);
  };
}

class LandingBloc extends Bloc<LandingEvent, LandingState> {
  late final RestaurantService _restaurantService;

  LandingBloc() : super(const LandingState()) {
    _restaurantService = getIt<RestaurantService>();
    on<RestaurantsFetched>(
      _onRestaurantsFetched,
      transformer: throttleDroppable(throttleDuration),
    );
  }

  Future<void> _onRestaurantsFetched(
    RestaurantsFetched event,
    Emitter<LandingState> emit,
  ) async {
    if (state.hasReachedMax) return;
    try {
      if (state.status == LandingStatus.initial) {
        final restaurantes = await _restaurantService.getRestaurantPage(0);
        return emit(
          state.copyWith(
              status: LandingStatus.success,
              restaurantes: restaurantes.contenido,
              hasReachedMax:
                  restaurantes.paginaActual! + 1 >= restaurantes.numeroPaginas!,
              currentPage: 0),
        );
      }
      final restaurantes =
          await _restaurantService.getRestaurantPage(state.currentPage + 1);
      emit(
        state.copyWith(
            status: LandingStatus.success,
            restaurantes: List.of(state.restaurantes)
              ..addAll(restaurantes.contenido!),
            hasReachedMax:
                restaurantes.paginaActual! + 1 >= restaurantes.numeroPaginas!,
            currentPage: state.currentPage + 1),
      );
    } catch (_) {
      emit(state.copyWith(status: LandingStatus.failure));
    }
  }
}
