

import 'dart:convert';

import 'package:flallery_frontend/models/login.dart';
import 'package:flallery_frontend/models/register_model.dart';
import 'package:get_it/get_it.dart';
import 'package:injectable/injectable.dart';

import 'package:flallery_frontend/rest/rest.dart';

@Order(-1)
@singleton
class AuthenticationRepository {

  late RestClient _client;

  AuthenticationRepository() {
    _client = GetIt.I.get<RestClient>();
    //_client = RestClient();
  }

  Future<dynamic> doLogin(String username, String password) async {
    String url = "/auth/login";

    var jsonResponse = await _client.post(url, LoginRequest(username: username, password: password));
    return LoginResponse.fromJson(jsonDecode(jsonResponse));

  }


  Future<UserRegisterResponse> registerUser(String username, String password,
      String verifyPassword, String avatar, String fullName) async {
    String url = "/auth/register";

    var jsonResponse = await _client.post(
        url,
        UserRegisterRequest(
            username: username,
            password: password,
            verifyPassword: verifyPassword,
            avatar: avatar,
            fullName: fullName));
    return UserRegisterResponse.fromJson(jsonDecode(jsonResponse));
  }





}