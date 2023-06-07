import 'package:file_picker/src/platform_file.dart';
import 'package:front/model/restaurante_detail.dart';
import 'package:front/model/restaurante_request.dart';
import 'package:front/repository/restaurant_repository.dart';
import 'package:get_it/get_it.dart';
import 'package:injectable/injectable.dart';

import '../model/RestauranteListResult.dart';
import 'auth_service.dart';

@Order(5)
@singleton
class RestaurantService {
  late RestaurantRepository _restaurantRepository;
  late JwtAuthenticationService _authService;

  RestaurantService() {
    _restaurantRepository = GetIt.I.get<RestaurantRepository>();
    _authService = GetIt.I.get<JwtAuthenticationService>();
  }

  Future<RestauranteListResult> getRestaurantPage(int page) async {
    return _restaurantRepository.getRestaurantPage(page);
  }

  Future<RestauranteDetailResult> getRestaurantDetails(
      String restaurantId) async {
    return _restaurantRepository.getRestaurantDetails(restaurantId);
  }

  Future<RestauranteListResult> getManagedRestaurants() async {
    return _restaurantRepository.getManagedRestaurants();
  }

  Future<void> deleteById(String restaurantId) async {
    return _restaurantRepository.deleteById(restaurantId);
  }

  Future<RestauranteDetailResult> edit(
      String restaurantId, RestauranteEditRequest editData) async {
    return _restaurantRepository.edit(restaurantId, editData);
  }

  Future<RestauranteDetailResult> editImg(
      String restaurantId, PlatformFile file) async {
    var user = await _authService.getCurrentUser();
    return _restaurantRepository.editImg(restaurantId, file, user!.accessToken);
  }

  Future<RestauranteDetailResult> create(
      RestauranteEditRequest restauranteEditRequest, PlatformFile file) async {
    var user = await _authService.getCurrentUser();
    return _restaurantRepository.create(
        restauranteEditRequest, file, user!.accessToken);
  }
}
