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

  ProductService() {
    _productRepository = getIt<ProductRepository>();
    GetIt.I
        .getAsync<LocalStorageService>()
        .then((value) => localStorageService = value);
  }

  Future<ProductDetailsResponse> getProductDetails(int productId) async {
    return await _productRepository.getProductById(productId);
  }

  Future<ProductDetailsResponse> add(
    ProductRequest productRequest,
    PlatformFile file,
  ) async {
    var token = await localStorageService.getFromDisk("user_token");
    return await _productRepository.addProduct(productRequest, file, token);
  }

    Future<ProductDetailsResponse> edit(int id,
    ProductRequest productRequest,
  ) async {
    return await _productRepository.editProduct(id,productRequest);
  }

  Future<void> addToFavorites(int productId) async {
    await _productRepository.addToFavorites(productId);
  }

  Future<void> removeFromFavorites(int productId) async {
    await _productRepository.removeFromFavorites(productId);
  }

  Future<List<ProductDetailsResponse>> getUserFavorites() async {
    return await _productRepository.getUserFavorites();
  }
}
