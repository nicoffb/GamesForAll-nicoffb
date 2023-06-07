import 'dart:async';

import 'package:bloc/bloc.dart';
import 'package:equatable/equatable.dart';

import '../model/login_model.dart';
import '../service/auth_service.dart';

part 'auth_event.dart';
part 'auth_state.dart';

class AuthenticationBloc extends Bloc<AuthEvent, AuthenticationState> {
  final AuthenticationService _authenticationService;

  AuthenticationBloc(AuthenticationService authenticationService)
      : assert(authenticationService != null),
        _authenticationService = authenticationService,
        super(AuthenticationInitial()) {
    on<AppLoaded>(_onAppLoaded);
    on<UserLoggedIn>(_onUserLoggedIn);
    on<UserLoggedOut>(_onUserLoggedOut);
    on<DeleteAccountEvent>(_onDeleteAccountEvent);
  }

  _onAppLoaded(
    AppLoaded event,
    Emitter<AuthenticationState> emit,
  ) async {
    emit(AuthenticationLoading());
    try {
      final currentUser = await _authenticationService.getCurrentUser();

      if (currentUser != null) {
        if (currentUser.roles.contains("OWNER")) {
          emit(AuthenticationAuthenticatedOwner(user: currentUser));
        } else {
          emit(AuthenticationAuthenticatedClient(user: currentUser));
        }
      } else {
        emit(AuthenticationNotAuthenticated());
      }
    } on Exception catch (e) {
      emit(AuthenticationFailure(
          message: 'An unknown error occurred: ${e.toString()}'));
    }
  }

  _onUserLoggedIn(
    UserLoggedIn event,
    Emitter<AuthenticationState> emit,
  ) async {
    if (event.user.roles.contains("OWNER")) {
      emit(AuthenticationAuthenticatedOwner(user: event.user));
    } else {
      emit(AuthenticationAuthenticatedClient(user: event.user));
    }
  }

  _onUserLoggedOut(
    UserLoggedOut event,
    Emitter<AuthenticationState> emit,
  ) async {
    await _authenticationService.signOut();
    emit(AuthenticationNotAuthenticated());
  }

  FutureOr<void> _onDeleteAccountEvent(
      DeleteAccountEvent event, Emitter<AuthenticationState> emit) async {
    try {
      await _authenticationService.deleteAccount();
    } on Exception catch (e) {
      // TODO
    }
    await _authenticationService.signOut();
    emit(AuthenticationNotAuthenticated());
  }
}
