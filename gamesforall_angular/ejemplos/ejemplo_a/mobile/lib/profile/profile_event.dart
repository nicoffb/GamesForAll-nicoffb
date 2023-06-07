part of 'profile_bloc.dart';

abstract class ProfileEvent extends Equatable {
  const ProfileEvent();

  @override
  List<Object> get props => [];
}

class FetchUserEvent extends ProfileEvent {}

class FetchManagedRestaurantEvent extends ProfileEvent {}
