// GENERATED CODE - DO NOT MODIFY BY HAND

// **************************************************************************
// InjectableConfigGenerator
// **************************************************************************

// ignore_for_file: no_leading_underscores_for_library_prefixes
import 'package:front/repository/auth_repository.dart' as _i4;
import 'package:front/repository/plato_repository.dart' as _i5;
import 'package:front/repository/restaurant_repository.dart' as _i6;
import 'package:front/rest_client/rest_client.dart' as _i3;
import 'package:front/service/auth_service.dart' as _i7;
import 'package:front/service/plato_service.dart' as _i8;
import 'package:front/service/restaurant_service.dart' as _i9;
import 'package:get_it/get_it.dart' as _i1;
import 'package:injectable/injectable.dart'
    as _i2; // ignore_for_file: unnecessary_lambdas

// ignore_for_file: lines_longer_than_80_chars
extension GetItInjectableX on _i1.GetIt {
  // initializes the registration of main-scope dependencies inside of GetIt
  _i1.GetIt init({
    String? environment,
    _i2.EnvironmentFilter? environmentFilter,
  }) {
    final gh = _i2.GetItHelper(
      this,
      environment,
      environmentFilter,
    );
    gh.singleton<_i3.RestClient>(_i3.RestClient());
    gh.singleton<_i4.AuthenticationRepository>(_i4.AuthenticationRepository());
    gh.singleton<_i5.PlatoRepository>(_i5.PlatoRepository());
    gh.singleton<_i6.RestaurantRepository>(_i6.RestaurantRepository());
    gh.singleton<_i7.JwtAuthenticationService>(_i7.JwtAuthenticationService());
    gh.singleton<_i8.PlatoService>(_i8.PlatoService());
    gh.singleton<_i9.RestaurantService>(_i9.RestaurantService());
    return this;
  }
}
