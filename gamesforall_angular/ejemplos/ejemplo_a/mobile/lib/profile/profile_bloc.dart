import 'dart:async';

import 'package:bloc/bloc.dart';
import 'package:equatable/equatable.dart';
import 'package:front/model/RestauranteListResult.dart';

import '../config/locator.dart';
import '../model/login_model.dart';
import '../service/auth_service.dart';
import '../service/restaurant_service.dart';

part 'profile_event.dart';
part 'profile_state.dart';

class ProfileBloc extends Bloc<ProfileEvent, ProfileState> {
  late final RestaurantService _restaurantService;
  late final AuthenticationService _authService;

  ProfileBloc() : super(ProfileInitial()) {
    _authService = getIt<JwtAuthenticationService>();
    _restaurantService = getIt<RestaurantService>();
    on<FetchUserEvent>(_onFetchUserEvent);
    on<FetchManagedRestaurantEvent>(_onFetchManagedRestaurantEvent);
  }

  Future<FutureOr<void>> _onFetchUserEvent(
      FetchUserEvent event, Emitter<ProfileState> emit) async {
    emit(ProfileState(status: ProfileStatus.initial));
    try {
      List<RestauranteGeneric> restaurantResult = List.empty();
      final user = await _authService.getCurrentUser();
      if (user!.roles.contains("OWNER")) {
        final restaurants = await _restaurantService.getManagedRestaurants();
        restaurantResult = restaurants.contenido!;
        emit(ProfileState(
            user: user,
            status: ProfileStatus.owner,
            restaurantes: restaurantResult));
      } else {
        emit(ProfileState(user: user, status: ProfileStatus.standard));
      }
    } catch (_) {
      print(_);
      emit(ProfileState(status: ProfileStatus.failure));
    }
  }

  FutureOr<void> _onFetchManagedRestaurantEvent(
      FetchManagedRestaurantEvent event, Emitter<ProfileState> emit) async {
    emit(state.copyWith(status: ProfileStatus.initial));
    try {
      final restaurants = await _restaurantService.getManagedRestaurants();
      emit(state.copyWith(
          restaurantes: restaurants.contenido, status: ProfileStatus.owner));
    } catch (_) {
      print(_);
      emit(ProfileState(status: ProfileStatus.failure));
    }
  }
}
