import 'dart:convert';

import 'package:file_picker/src/platform_file.dart';
import 'package:front/model/plato_detail_result.dart';
import 'package:front/model/plato_request.dart';
import 'package:front/model/rating.dart';
import 'package:front/service/localstorage_service.dart';
import 'package:get_it/get_it.dart';
import 'package:injectable/injectable.dart';

import '../model/login_model.dart';
import '../model/plato_list_result.dart';
import '../rest_client/rest_client.dart';

const String baseUrl = "/plato/";

@singleton
class PlatoRepository {
  late RestClient _client;

  PlatoRepository() {
    _client = GetIt.I.get<RestClient>();
  }

  Future<PlatoListResult> getByRestaurant(
      String restaurantId, int page, String searchString) async {
    String url =
        baseUrl + "restaurante/${restaurantId}?page=${page}&${searchString}";

    var jsonResponse = await _client.get(url);
    return PlatoListResult.fromJson(jsonDecode(jsonResponse));
  }

  Future<PlatoDetailResult> getDetails(String platoId) async {
    String url = baseUrl + platoId;

    var jsonResponse = await _client.get(url);
    return PlatoDetailResult.fromJson(jsonDecode(jsonResponse));
  }

  Future<PlatoDetailResult> rate(
      String platoId, double nota, String? comentario) async {
    String url = baseUrl + "rate/${platoId}";
    var jsonResponse = await _client.post(
        url, RateRequest(nota: nota, comentario: comentario));
    return PlatoDetailResult.fromJson(jsonDecode(jsonResponse));
  }

  void deleteById(String platoId) async {
    String url = baseUrl + "${platoId}";
    await _client.delete(url);
  }

  Future<PlatoDetailResult> edit(
      String platoId, PlatoRequest platoRequest) async {
    String url = baseUrl + "${platoId}";

    var jsonResponse = await _client.put(url, platoRequest);
    return PlatoDetailResult.fromJson(jsonDecode(jsonResponse));
  }

  Future<PlatoDetailResult> editImg(
      String platoId, PlatformFile file, String token) async {
    String url = baseUrl + "${platoId}/img/";

    var jsonResponse = await _client.putMultiPart(url, null, file, token);
    return PlatoDetailResult.fromJson(jsonDecode(jsonResponse));
  }

  Future<PlatoDetailResult> addPlato(String restaurantId,
      PlatoRequest platoRequest, PlatformFile file, String accessToken) async {
    String url = baseUrl + "${restaurantId}";

    var jsonResponse =
        await _client.postMultiPart(url, platoRequest, file, accessToken);
    return PlatoDetailResult.fromJson(jsonDecode(jsonResponse));
  }
}
