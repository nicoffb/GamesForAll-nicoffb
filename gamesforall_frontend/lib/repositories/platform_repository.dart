import 'dart:ffi';

import 'package:file_picker/file_picker.dart';
import 'package:gamesforall_frontend/blocs/productList/product_bloc.dart';
import 'package:gamesforall_frontend/models/product_request.dart';
import 'package:get_it/get_it.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';

import 'package:injectable/injectable.dart';

import '../models/product_detail_response.dart';
import '../models/product_page_response.dart';
import '../rest/rest_client.dart';

@singleton
class PlatformRepository {
  late RestAuthenticatedClient server;
  PlatformRepository() {
    server = GetIt.I.get<RestAuthenticatedClient>();
  }

  Future<List<Platform>> getAllPlatforms() async {
    String url = '/platform/list';

    var jsonResponse = await server.get(url);
    List<dynamic> jsonList = jsonDecode(jsonResponse);
    List<Platform> platforms =
        jsonList.map((item) => Platform.fromJson(item)).toList();

    return platforms;
  }
}
