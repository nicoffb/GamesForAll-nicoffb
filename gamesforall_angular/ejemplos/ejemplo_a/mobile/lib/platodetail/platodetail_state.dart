part of 'platodetail_bloc.dart';

class PlatodetailInitial extends PlatodetailState {}

enum PlatodetailStatus {
  initial,
  success,
  failure,
}

class PlatodetailState extends Equatable {
  PlatodetailState({
    this.plato = null,
    this.id = "",
    this.status = PlatodetailStatus.initial,
  });

  final String id;
  final PlatodetailStatus status;
  PlatoDetailResult? plato;

  @override
  String toString() {
    return '''PostState { status: $status, plato: ${plato} }''';
  }

  @override
  List<Object> get props => [id, status, plato!];
}
