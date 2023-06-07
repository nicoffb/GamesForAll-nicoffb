part of 'profile_bloc.dart';

class ProfileInitial extends ProfileState {}

enum ProfileStatus {
  initial,
  standard,
  owner,
  failure,
}

class ProfileState extends Equatable {
  ProfileState({
    this.restaurantes = null,
    this.user = null,
    this.status = ProfileStatus.initial,
  });

  final User? user;
  final ProfileStatus status;
  final List<RestauranteGeneric>? restaurantes;

  @override
  String toString() {
    return '''PostState { status: $status, user: ${user} }''';
  }

  @override
  List<Object> get props => [status];

  ProfileState copyWith(
      {User? user,
      ProfileStatus? status,
      List<RestauranteGeneric>? restaurantes}) {
    return ProfileState(
        user: user ?? this.user,
        status: status ?? this.status,
        restaurantes: restaurantes ?? this.restaurantes);
  }
}
