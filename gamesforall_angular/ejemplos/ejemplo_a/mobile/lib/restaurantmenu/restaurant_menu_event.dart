part of 'restaurant_menu_bloc.dart';

abstract class RestaurantMenuEvent extends Equatable {
  const RestaurantMenuEvent();

  @override
  List<Object> get props => [];
}

class RestaurantFetched extends RestaurantMenuEvent {
  RestaurantFetched(this.restaurantId);
  String restaurantId;
}

class NextPlatosFetched extends RestaurantMenuEvent {}

class SearchPlatosEvent extends RestaurantMenuEvent {
  SearchPlatosEvent(
      this.busqueda, this.minPriceValue, this.maxPriceValue, this.noGluten);
  String? busqueda;
  double? minPriceValue;
  double? maxPriceValue;
  bool? noGluten;
}
