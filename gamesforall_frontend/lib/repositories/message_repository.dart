import 'dart:ffi';

import 'package:file_picker/file_picker.dart';
import 'package:gamesforall_frontend/blocs/productList/product_bloc.dart';
import 'package:gamesforall_frontend/models/message_response.dart';
import 'package:gamesforall_frontend/models/product_request.dart';
import 'package:get_it/get_it.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';

import 'package:injectable/injectable.dart';
import 'package:uuid/uuid.dart';

import '../models/product_detail_response.dart';
import '../models/product_page_response.dart';
import '../rest/rest_client.dart';

const _postLimit = 20;

@singleton
class MessageRepository {
  late RestAuthenticatedClient server;
  MessageRepository() {
    server = GetIt.I.get<RestAuthenticatedClient>();
  }

  // Future<void> addToFavorites(int productId) async {
  //   String url = '/favorites/$productId';

  //   var jsonResponse = await server.post(url, null);
  // }

  // Future<void> removeFromFavorites(int productId) async {
  //   String url = '/favorites/$productId';

  //   var jsonResponse = await server.delete(url);
  // }

  Future<List<MessageResponse>> getMessagesWithUser(String userId) async {
    String urlString = '/messages/$userId';

    var jsonResponse = await server.get(urlString);
    List<dynamic> jsonList = jsonDecode(jsonResponse);
    List<MessageResponse> messages =
        jsonList.map((item) => MessageResponse.fromJson(item)).toList();

    return messages;
  }
}
