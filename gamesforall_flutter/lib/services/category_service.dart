import 'package:gamesforall_frontend/repositories/category_repository.dart';
import 'package:get_it/get_it.dart';
import 'package:injectable/injectable.dart';

import '../config/locator.dart';
import '../models/product_detail_response.dart';
import 'localstorage_service.dart';

@Order(5)
@singleton
class CategoryService {
  late LocalStorageService localStorageService;
  late final CategoryRepository categoryRepository;

  CategoryService() {
    categoryRepository = getIt<CategoryRepository>();
    GetIt.I
        .getAsync<LocalStorageService>()
        .then((value) => localStorageService = value);
  }

  Future<List<Categories>> getAllCategories() async {
    return await categoryRepository.getAllCategories();
  }
}
