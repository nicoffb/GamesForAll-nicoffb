import 'package:bloc/bloc.dart';
import 'package:equatable/equatable.dart';

import '../auth/auth_bloc.dart';
import '../exceptions/authentication_exception.dart';
import '../service/auth_service.dart';

part 'login_event.dart';
part 'login_state.dart';

class LoginBloc extends Bloc<LoginEvent, LoginState> {
  final AuthenticationBloc _authenticationBloc;
  final AuthenticationService _authenticationService;

  LoginBloc(AuthenticationBloc authenticationBloc,
      AuthenticationService authenticationService)
      : assert(authenticationBloc != null),
        assert(authenticationService != null),
        _authenticationBloc = authenticationBloc,
        _authenticationService = authenticationService,
        super(LoginInitial()) {
    on<LoginInWithUsernameButtonPressed>(__onLoginInWithUsernameButtonPressed);
  }

  __onLoginInWithUsernameButtonPressed(
    LoginInWithUsernameButtonPressed event,
    Emitter<LoginState> emit,
  ) async {
    emit(LoginLoading());
    try {
      final user = await _authenticationService.signInWithUsernameAndPassword(
          event.username, event.password);
      if (user != null) {
        _authenticationBloc.add(UserLoggedIn(user: user));
        emit(LoginSuccess());
        emit(LoginInitial());
      } else {
        emit(LoginFailure(error: 'Something very weird just happened'));
      }
    } on AuthenticationException catch (e) {
      emit(LoginFailure(error: e.message));
    } on Exception catch (err) {
      emit(LoginFailure(error: 'An unknown error occurred ${err.toString()}'));
    }
  }
}
