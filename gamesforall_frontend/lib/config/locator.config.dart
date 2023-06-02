// GENERATED CODE - DO NOT MODIFY BY HAND

// **************************************************************************
// InjectableConfigGenerator
// **************************************************************************

// ignore_for_file: unnecessary_lambdas
// ignore_for_file: lines_longer_than_80_chars
// coverage:ignore-file

// ignore_for_file: no_leading_underscores_for_library_prefixes
import 'package:gamesforall_frontend/repositories/authentication_repository.dart'
    as _i4;
import 'package:gamesforall_frontend/repositories/category_repository.dart'
    as _i6;
import 'package:gamesforall_frontend/repositories/message_repository.dart'
    as _i7;
import 'package:gamesforall_frontend/repositories/platform_repository.dart'
    as _i8;
import 'package:gamesforall_frontend/repositories/product_repository.dart'
    as _i9;
import 'package:gamesforall_frontend/repositories/user_repository.dart' as _i5;
import 'package:gamesforall_frontend/rest/rest_client.dart' as _i3;
import 'package:gamesforall_frontend/services/authentication_service.dart'
    as _i10;
import 'package:gamesforall_frontend/services/category_service.dart' as _i11;
import 'package:gamesforall_frontend/services/message_service.dart' as _i12;
import 'package:gamesforall_frontend/services/platform_service.dart' as _i13;
import 'package:gamesforall_frontend/services/product_service.dart' as _i14;
import 'package:get_it/get_it.dart' as _i1;
import 'package:injectable/injectable.dart' as _i2;

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
    gh.singleton<_i3.RestAuthenticatedClient>(_i3.RestAuthenticatedClient());
    gh.singleton<_i3.RestClient>(_i3.RestClient());
    gh.singleton<_i4.AuthenticationRepository>(_i4.AuthenticationRepository());
    gh.singleton<_i5.UserRepository>(_i5.UserRepository());
    gh.singleton<_i6.CategoryRepository>(_i6.CategoryRepository());
    gh.singleton<_i7.MessageRepository>(_i7.MessageRepository());
    gh.singleton<_i8.PlatformRepository>(_i8.PlatformRepository());
    gh.singleton<_i9.ProductRepository>(_i9.ProductRepository());
    gh.singleton<_i10.JwtAuthenticationService>(
        _i10.JwtAuthenticationService());
    gh.singleton<_i11.CategoryService>(_i11.CategoryService());
    gh.singleton<_i12.MessageService>(_i12.MessageService());
    gh.singleton<_i13.PlatformService>(_i13.PlatformService());
    gh.singleton<_i14.ProductService>(_i14.ProductService());
    return this;
  }
}
