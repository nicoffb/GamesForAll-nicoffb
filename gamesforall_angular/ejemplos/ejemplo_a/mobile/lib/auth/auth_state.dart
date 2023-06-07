part of 'auth_bloc.dart';

abstract class AuthenticationState extends Equatable {
  const AuthenticationState();

  @override
  List<Object> get props => [];
}

class AuthenticationInitial extends AuthenticationState {}

class AuthenticationLoading extends AuthenticationState {}

class AuthenticationNotAuthenticated extends AuthenticationState {}

class AuthenticationAuthenticated extends AuthenticationState {
  final User user;

  AuthenticationAuthenticated({required this.user});

  @override
  List<Object> get props => [user];
}

class AuthenticationAuthenticatedClient extends AuthenticationAuthenticated {
  AuthenticationAuthenticatedClient({required user}) : super(user: user);

  @override
  List<Object> get props => [user];
}

class AuthenticationAuthenticatedOwner extends AuthenticationAuthenticated {
  AuthenticationAuthenticatedOwner({required user}) : super(user: user);

  @override
  List<Object> get props => [user];
}

class AuthenticationFailure extends AuthenticationState {
  final String message;

  AuthenticationFailure({required this.message});

  @override
  List<Object> get props => [message];
}
