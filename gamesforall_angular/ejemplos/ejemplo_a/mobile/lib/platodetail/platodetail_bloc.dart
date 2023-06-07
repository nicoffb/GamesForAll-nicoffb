import 'dart:async';

import 'package:bloc/bloc.dart';
import 'package:equatable/equatable.dart';
import 'package:front/model/plato_detail_result.dart';

import '../config/locator.dart';
import '../service/plato_service.dart';

part 'platodetail_event.dart';
part 'platodetail_state.dart';

class PlatodetailBloc extends Bloc<PlatodetailEvent, PlatodetailState> {
  late final PlatoService _platoService;
  PlatodetailBloc() : super(PlatodetailInitial()) {
    _platoService = getIt<PlatoService>();
    on<PlatoFetched>(
      _onPlatoFetched,
    );
    on<RateEvent>(
      _onRateEvent,
    );
  }

  FutureOr<void> _onPlatoFetched(
      PlatoFetched event, Emitter<PlatodetailState> emit) async {
    try {
      final platoDetail = await _platoService.getDetails(event.platoId);
      return emit(PlatodetailState(
          id: event.platoId,
          plato: platoDetail,
          status: PlatodetailStatus.success));
    } catch (_) {
      emit(PlatodetailState(
          id: event.platoId, status: PlatodetailStatus.failure));
    }
  }

  FutureOr<void> _onRateEvent(
      RateEvent event, Emitter<PlatodetailState> emit) async {
    try {
      final platoDetail =
          await _platoService.rate(event.platoId, event.nota, event.comentario);
      return emit(PlatodetailState(
          id: event.platoId,
          plato: platoDetail,
          status: PlatodetailStatus.success));
    } catch (_) {
      emit(PlatodetailState(
          id: event.platoId, status: PlatodetailStatus.failure));
    }
  }
}
