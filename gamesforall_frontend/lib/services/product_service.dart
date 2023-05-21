import 'package:file_picker/file_picker.dart';
import 'package:gamesforall_frontend/models/product_detail_response.dart';
import 'package:gamesforall_frontend/models/product_request.dart';
import 'package:gamesforall_frontend/repositories/product_repository.dart';
import 'package:gamesforall_frontend/services/services.dart';
import 'package:injectable/injectable.dart';

import 'authentication_service.dart';

@Order(5)
@singleton
class ProductService {
  late ProductRepository _productRepository;
  late LocalStorageService localStorageService;

  ProductService() {
    localStorageService = LocalStorageService();
  }

  Future<ProductDetailsResponse> add(
    ProductRequest productRequest,
    PlatformFile file,
  ) async {
    var token = await localStorageService.getFromDisk("user_token");
    return _productRepository.addProduct(productRequest, file, token);
  }
}
