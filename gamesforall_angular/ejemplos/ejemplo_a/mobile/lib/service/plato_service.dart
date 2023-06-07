import 'package:file_picker/src/platform_file.dart';
import 'package:front/model/plato_detail_result.dart';
import 'package:front/model/plato_list_result.dart';
import 'package:front/model/plato_request.dart';
import 'package:front/repository/plato_repository.dart';
import 'package:get_it/get_it.dart';
import 'package:injectable/injectable.dart';

import '../config/locator.dart';
import 'auth_service.dart';

@Order(5)
@singleton
class PlatoService {
  late PlatoRepository _platoRepository;
  late JwtAuthenticationService _authService;

  PlatoService() {
    _platoRepository = GetIt.I.get<PlatoRepository>();
    _authService = GetIt.I.get<JwtAuthenticationService>();
  }

  Future<PlatoListResult> getByRestaurant(String restaurantId, int page) async {
    return _platoRepository.getByRestaurant(restaurantId, page, "search=");
  }

  Future<PlatoDetailResult> getDetails(String platoId) async {
    return _platoRepository.getDetails(platoId);
  }

  Future<PlatoDetailResult> rate(
      String platoId, double nota, String? comentario) async {
    return _platoRepository.rate(platoId, nota, comentario);
  }

  Future<void> deleteById(String platoId) async {
    return _platoRepository.deleteById(platoId);
  }

  Future<PlatoListResult> searchByRestaurant(
      String id, String searchString, int page) async {
    return _platoRepository.getByRestaurant(id, page, searchString);
  }

  Future<PlatoDetailResult> edit(
      String platoId, PlatoRequest platoRequest) async {
    return _platoRepository.edit(platoId, platoRequest);
  }

  Future<PlatoDetailResult> editImg(String platoId, PlatformFile file) async {
    var user = await _authService.getCurrentUser();
    return _platoRepository.editImg(platoId, file, user!.accessToken);
  }

  Future<PlatoDetailResult> add(
      String restaurantId, PlatoRequest platoRequest, PlatformFile file) async {
    var user = await _authService.getCurrentUser();
    return _platoRepository.addPlato(
        restaurantId, platoRequest, file, user!.accessToken);
  }
}
