import 'dart:convert';

import 'package:front/model/change_password.dart';
import 'package:get_it/get_it.dart';
import 'package:injectable/injectable.dart';

import '../model/login_model.dart';
import '../model/register_model.dart';
import '../repository/auth_repository.dart';
import 'localstorage_service.dart';

abstract class AuthenticationService {
  Future<User?> getCurrentUser();
  Future<User?> signInWithUsernameAndPassword(String username, String password);
  Future<RegisterResponse> registerClient(String username, String password,
      String verifyPassword, String email, String name);
  Future<RegisterResponse> registerOwner(String username, String password,
      String verifyPassword, String email, String name);
  Future<void> signOut();
  Future<void> changePassWord(ChangePasswordRequest changePasswordRequest);
  Future<void> deleteAccount();
}

@Order(2)
@singleton
class JwtAuthenticationService extends AuthenticationService {
  late AuthenticationRepository _authenticationRepository;
  late LocalStorageService _localStorageService;

  JwtAuthenticationService() {
    _authenticationRepository = GetIt.I.get<AuthenticationRepository>();
    GetIt.I
        .getAsync<LocalStorageService>()
        .then((value) => _localStorageService = value);
  }

  @override
  Future<User?> getCurrentUser() async {
    String? loggedUser = _localStorageService.getFromDisk("user");
    if (loggedUser != null) {
      var user = LoginResponse.fromJson(jsonDecode(loggedUser));
      return User(
          email: user.username ?? "",
          name: user.nombre ?? "",
          accessToken: user.token ?? "",
          refreshToken: user.refreshToken ?? "",
          roles: user.roles ?? List.empty());
    }
    return null;
  }

  @override
  Future<User> signInWithUsernameAndPassword(
      String username, String password) async {
    LoginResponse response =
        await _authenticationRepository.doLogin(username, password);
    await _localStorageService.saveToDisk(
        'user', jsonEncode(response.toJson()));
    return User(
        email: response.username ?? "",
        name: response.nombre ?? "",
        accessToken: response.token ?? "",
        refreshToken: response.refreshToken ?? "",
        roles: response.roles ?? List.empty());
  }

  @override
  Future<void> signOut() async {
    await _localStorageService.deleteFromDisk("user");
  }

  @override
  Future<RegisterResponse> registerClient(String username, String password,
      String verifyPassword, String email, String name) async {
    RegisterResponse response = await _authenticationRepository.registerClient(
        username, password, verifyPassword, email, name);
    return response;
  }

  @override
  Future<RegisterResponse> registerOwner(String username, String password,
      String verifyPassword, String email, String name) async {
    RegisterResponse response = await _authenticationRepository.registerOwner(
        username, password, verifyPassword, email, name);
    return response;
  }

  @override
  Future<LoginResponse> changePassWord(
      ChangePasswordRequest changePasswordRequest) async {
    LoginResponse response =
        await _authenticationRepository.changePassword(changePasswordRequest);
    return response;
  }

  @override
  Future<void> deleteAccount() async {
    await _authenticationRepository.deleteAccount();
  }
}
