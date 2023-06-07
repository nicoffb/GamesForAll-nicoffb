
import 'package:flallery_frontend/services/services.dart';
import 'package:get_it/get_it.dart'; 
import 'package:injectable/injectable.dart';
import 'package:flallery_frontend/config/locator.config.dart'; 

final getIt = GetIt.instance;


@InjectableInit()
void configureDependencies() => getIt.init();

void setupAsyncDependencies() {
  //var localStorageService = await LocalStorageService.getInstance();
  //getIt.registerSingleton(localStorageService);
  getIt.registerSingletonAsync<LocalStorageService>(() => LocalStorageService.getInstance());
}