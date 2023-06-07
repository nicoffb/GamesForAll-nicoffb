part of 'landing_bloc.dart';

abstract class LandingEvent extends Equatable {
  @override
  List<Object> get props => [];
}

class RestaurantsFetched extends LandingEvent {}
