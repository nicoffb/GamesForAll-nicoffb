import 'dart:async';

import 'package:bloc/bloc.dart';
import 'package:equatable/equatable.dart';
import 'package:front/model/plato_detail_result.dart';
import 'package:front/platos_manage/platos_manage_event.dart';

import '../config/locator.dart';
import '../model/plato_list_result.dart';
import '../service/plato_service.dart';

part 'platos_manage_state.dart';

class PlatosManageBloc extends Bloc<PlatosManageEvent, PlatosManageState> {
  late final PlatoService _platoService;
  PlatosManageBloc() : super(PlatosManageInitial()) {
    _platoService = getIt<PlatoService>();
    on<EditCancelled>(
      (event, emit) {
        emit(state.copyWith(status: PlatosManageStatus.success));
      },
    );
    on<PlatosFetchedEvent>(
      _onPlatosFetchedEvent,
    );
    on<DeletePlatoEvent>(
      _onDeletePlatoEventEvent,
    );
    on<PlatoDetailFetchedEvent>(_onPlatoDetailFetchedEvent);
    on<EditPlato>(_onEditPlato);
    on<ChangeImgEvent>(_onChangeImgEvent);
  }

  FutureOr<void> _onPlatosFetchedEvent(
      PlatosFetchedEvent event, Emitter<PlatosManageState> emit) async {
    if (state.hasReachedMax && state.status != PlatosManageStatus.editSuccess)
      return;
    try {
      if (state.status == PlatosManageStatus.initial ||
          state.status == PlatosManageStatus.editSuccess) {
        final platos =
            await _platoService.getByRestaurant(event.restaurantId, 0);
        return emit(
          state.copyWith(
              restaurantId: event.restaurantId,
              status: PlatosManageStatus.success,
              platos: platos.contenido!,
              hasReachedMax: platos.paginaActual! + 1 >= platos.numeroPaginas!,
              currentPage: platos.paginaActual! + 1),
        );
      }
      final platos = await _platoService.getByRestaurant(
          state.restaurantId, state.currentPage);
      emit(
        state.copyWith(
            status: PlatosManageStatus.success,
            platos: List.of(state.platos)..addAll(platos.contenido!),
            hasReachedMax: platos.paginaActual! + 1 >= platos.numeroPaginas!,
            currentPage: state.currentPage + 1),
      );
    } catch (_) {
      emit(state.copyWith(status: PlatosManageStatus.failure));
    }
  }

  FutureOr<void> _onDeletePlatoEventEvent(
      DeletePlatoEvent event, Emitter<PlatosManageState> emit) async {
    try {
      await _platoService.deleteById(event.platoId);
      final platos = await _platoService.getByRestaurant(state.restaurantId, 0);
      return emit(
        state.copyWith(
            restaurantId: state.restaurantId,
            status: PlatosManageStatus.success,
            platos: platos.contenido!,
            hasReachedMax: platos.paginaActual! + 1 >= platos.numeroPaginas!,
            currentPage: 1),
      );
    } catch (_) {
      emit(state.copyWith(status: PlatosManageStatus.failure));
    }
  }

  FutureOr<void> _onPlatoDetailFetchedEvent(
      PlatoDetailFetchedEvent event, Emitter<PlatosManageState> emit) async {
    emit(state.copyWith(
      status: PlatosManageStatus.initial,
      platoEditing: null,
    ));
    try {
      final plato = await _platoService.getDetails(event.platoId);
      return emit(
        state.copyWith(
          status: PlatosManageStatus.editing,
          platoEditing: plato,
        ),
      );
    } catch (_) {
      emit(state.copyWith(status: PlatosManageStatus.failure));
    }
  }

  FutureOr<void> _onEditPlato(
      EditPlato event, Emitter<PlatosManageState> emit) async {
    try {
      final plato = await _platoService.edit(event.platoId, event.platoRequest);
      return emit(
        state.copyWith(
          status: PlatosManageStatus.editSuccess,
          platoEditing: plato,
        ),
      );
    } catch (_) {
      emit(state.copyWith(status: PlatosManageStatus.failure));
    }
  }

  FutureOr<void> _onChangeImgEvent(
      ChangeImgEvent event, Emitter<PlatosManageState> emit) async {
    try {
      final plato = await _platoService.editImg(event.platoId, event.file);
      return emit(
        state.copyWith(
          status: PlatosManageStatus.editSuccess,
          platoEditing: plato,
        ),
      );
    } catch (_) {
      emit(state.copyWith(status: PlatosManageStatus.failure));
    }
  }
}
