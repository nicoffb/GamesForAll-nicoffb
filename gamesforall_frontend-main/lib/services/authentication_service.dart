import 'dart:convert';
//import 'dart:developer';

import '../config/locator.dart';
import '../models/change_password.dart';
import '../models/register_model.dart';
import '../services/localstorage_service.dart';
import 'package:get_it/get_it.dart';
import 'package:injectable/injectable.dart';

//import '../exceptions/exceptions.dart';
import '../models/models.dart';
import '../repositories/repositories.dart';

abstract class AuthenticationService {
  Future<User?> getCurrentUser();
  Future<User> signInWithEmailAndPassword(String email, String password);
  Future<void> signOut();
  Future<UserResponse> registerUser(
      String username, String password, String verifyPassword, String fullName);
  Future<void> changePassWord(ChangePasswordRequest changePasswordRequest);
  Future<void> deleteAccount();
}

@Order(2)
//@Singleton(as: AuthenticationService)
@singleton
class JwtAuthenticationService extends AuthenticationService {
  late AuthenticationRepository _authenticationRepository;
  late LocalStorageService _localStorageService;
  late UserRepository _userRepository;

  JwtAuthenticationService() {
    _authenticationRepository = getIt<AuthenticationRepository>();
    _userRepository = getIt<UserRepository>();
    GetIt.I
        .getAsync<LocalStorageService>()
        .then((value) => _localStorageService = value);
  }

  @override
  Future<User?> getCurrentUser() async {
    //String? loggedUser = _localStorageService.getFromDisk("user");
    print("get current user");
    String? token = _localStorageService.getFromDisk("user_token");
    if (token != null) {
      UserResponse response = await _userRepository.me();
      return response;
    }
    return null;
  }

  @override
  Future<User> signInWithEmailAndPassword(String email, String password) async {
    LoginResponse response =
        await _authenticationRepository.doLogin(email, password);
    //await _localStorageService.saveToDisk('user', jsonEncode(response.toJson()));
    await _localStorageService.saveToDisk('user_token', response.token);
    return User.fromLoginResponse(response);
  }

  @override
  Future<void> signOut() async {
    print("borrando token");
    await _localStorageService.deleteFromDisk("user_token");
  }

  //NUEVOS  METODOS

  @override
  Future<UserResponse> registerUser(String username, String password,
      String verifyPassword, String fullName) async {
    UserResponse response = await _authenticationRepository.registerUser(
        username, password, verifyPassword, fullName);
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
