import 'dart:ffi';

import 'package:file_picker/file_picker.dart';
import 'package:gamesforall_frontend/models/product_detail_response.dart';
import 'package:gamesforall_frontend/models/product_request.dart';
import 'package:gamesforall_frontend/models/user.dart';
import 'package:gamesforall_frontend/repositories/product_repository.dart';
import 'package:gamesforall_frontend/services/services.dart';
import 'package:get_it/get_it.dart';
import 'package:injectable/injectable.dart';

import '../config/locator.dart';
import 'authentication_service.dart';

@Order(5)
@singleton
class ProductService {
  late ProductRepository _productRepository;
  late LocalStorageService localStorageService;
  late AuthenticationService authenticationService;

  ProductService() {
    _productRepository = getIt<ProductRepository>();
    GetIt.I
        .getAsync<LocalStorageService>()
        .then((value) => localStorageService = value);
    GetIt.I
        .getAsync<AuthenticationService>()
        .then((value) => localStorageService = value);
  }

  Future<ProductDetailsResponse> add(
    ProductRequest productRequest,
    PlatformFile file,
  ) async {
    var token = await localStorageService.getFromDisk("user_token");
    return await _productRepository.addProduct(productRequest, file, token);
  }

  Future<void> addToFavorites(int productId) async {
    // var token = await localStorageService.getFromDisk("user_token");
    User user = await authenticationService.getCurrentUser();
    await _productRepository.addToFavorites(productId, user);
  }
}
