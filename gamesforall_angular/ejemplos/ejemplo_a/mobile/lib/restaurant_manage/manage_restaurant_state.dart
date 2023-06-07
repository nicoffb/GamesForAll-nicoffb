part of 'manage_restaurant_bloc.dart';

class ManageRestaurantInitial extends ManageRestaurantState {}

enum ManageRestaurantStatus {
  initial,
  success,
  failure,
  deleted,
  deleteFailure,
  editSuccess,
  createPlatoSuccess
}

class ManageRestaurantState extends Equatable {
  const ManageRestaurantState({
    this.restaurante = null,
    this.id = "",
    this.status = ManageRestaurantStatus.initial,
  });

  final String id;
  final ManageRestaurantStatus status;
  final RestauranteDetailResult? restaurante;

  ManageRestaurantState copyWith({
    String? id,
    RestauranteDetailResult? restaurante,
    ManageRestaurantStatus? status,
  }) {
    return ManageRestaurantState(
      id: id ?? this.id,
      restaurante: restaurante ?? this.restaurante,
      status: status ?? this.status,
    );
  }

  @override
  String toString() {
    return '''PostState { status: ${status}, restaurante: ${restaurante} }''';
  }

  @override
  List<Object> get props => [id, restaurante!, status];
}
