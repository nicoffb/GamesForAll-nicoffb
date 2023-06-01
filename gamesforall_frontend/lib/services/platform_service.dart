import 'package:get_it/get_it.dart';
import 'package:injectable/injectable.dart';

import '../config/locator.dart';
import '../models/product_detail_response.dart';
import '../repositories/platform_repository.dart';
import 'localstorage_service.dart';

@Order(5)
@singleton
class PlatformService {
  late LocalStorageService localStorageService;
  late final PlatformRepository platformRepository;

  PlatformService() {
    platformRepository = getIt<PlatformRepository>();
    GetIt.I
        .getAsync<LocalStorageService>()
        .then((value) => localStorageService = value);
  }

  Future<List<Platform>> getAllPlatforms() async {
    return await platformRepository.getAllPlatforms();
  }
}
