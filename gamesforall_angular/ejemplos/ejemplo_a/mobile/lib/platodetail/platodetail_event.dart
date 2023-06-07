part of 'platodetail_bloc.dart';

abstract class PlatodetailEvent extends Equatable {
  const PlatodetailEvent();

  @override
  List<Object> get props => [];
}

class PlatoFetched extends PlatodetailEvent {
  PlatoFetched(this.platoId);
  String platoId;
}

class RateEvent extends PlatodetailEvent {
  RateEvent(this.platoId, this.nota, this.comentario);
  String platoId;
  double nota;
  String? comentario;
}
