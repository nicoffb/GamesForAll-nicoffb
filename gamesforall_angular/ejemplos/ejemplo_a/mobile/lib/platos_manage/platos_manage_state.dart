part of 'platos_manage_bloc.dart';

class PlatosManageInitial extends PlatosManageState {}

enum PlatosManageStatus { initial, success, failure, editing, editSuccess }

class PlatosManageState extends Equatable {
  const PlatosManageState(
      {this.restaurantId = "",
      this.status = PlatosManageStatus.initial,
      this.platos = const <PlatoGeneric>[],
      this.hasReachedMax = false,
      this.currentPage = 0,
      this.platoEditing = null});

  final String restaurantId;
  final int currentPage;
  final PlatosManageStatus status;
  final List<PlatoGeneric> platos;
  final bool hasReachedMax;
  final PlatoDetailResult? platoEditing;

  PlatosManageState copyWith(
      {String? restaurantId,
      PlatosManageStatus? status,
      List<PlatoGeneric>? platos,
      bool? hasReachedMax,
      int? currentPage,
      PlatoDetailResult? platoEditing}) {
    return PlatosManageState(
      restaurantId: restaurantId ?? this.restaurantId,
      status: status ?? this.status,
      platos: platos ?? this.platos,
      hasReachedMax: hasReachedMax ?? this.hasReachedMax,
      currentPage: currentPage ?? this.currentPage,
      platoEditing: platoEditing ?? this.platoEditing,
    );
  }

  @override
  String toString() {
    return '''PostState { status: $status, hasReachedMax: $hasReachedMax, restauranteId: ${restaurantId} }''';
  }

  @override
  List<Object> get props =>
      [restaurantId, status, platos, hasReachedMax, currentPage];
}
