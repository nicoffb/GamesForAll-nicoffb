part of 'landing_bloc.dart';

enum LandingStatus {
  initial,
  success,
  failure,
}

class LandingState extends Equatable {
  const LandingState(
      {this.status = LandingStatus.initial,
      this.restaurantes = const <RestauranteGeneric>[],
      this.hasReachedMax = false,
      this.currentPage = 0});

  final int currentPage;
  final LandingStatus status;
  final List<RestauranteGeneric> restaurantes;
  final bool hasReachedMax;

  LandingState copyWith(
      {LandingStatus? status,
      List<RestauranteGeneric>? restaurantes,
      bool? hasReachedMax,
      int? currentPage}) {
    return LandingState(
        status: status ?? this.status,
        restaurantes: restaurantes ?? this.restaurantes,
        hasReachedMax: hasReachedMax ?? this.hasReachedMax,
        currentPage: currentPage ?? this.currentPage);
  }

  @override
  String toString() {
    return '''PostState { status: $status, hasReachedMax: $hasReachedMax, restaurantes: ${restaurantes.length} }''';
  }

  @override
  List<Object> get props => [status, restaurantes, hasReachedMax, currentPage];
}
