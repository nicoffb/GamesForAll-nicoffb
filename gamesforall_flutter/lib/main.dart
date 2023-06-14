import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:gamesforall_frontend/services/category_service.dart';
import 'package:gamesforall_frontend/services/message_service.dart';
import 'package:gamesforall_frontend/services/platform_service.dart';
import 'package:provider/provider.dart';
import 'package:gamesforall_frontend/pages/login_page.dart';
import 'package:gamesforall_frontend/pages/main_page.dart';

import 'package:gamesforall_frontend/services/authentication_service.dart';
import 'package:gamesforall_frontend/services/product_service.dart';

import 'blocs/authentication/authentication_bloc.dart';
import 'blocs/authentication/authentication_event.dart';
import 'blocs/authentication/authentication_state.dart';
import 'blocs/favorite/favorite_bloc.dart';
import 'blocs/message/message_bloc.dart';
import 'config/locator.dart';

void main() {
  setupAsyncDependencies();
  configureDependencies();

  runApp(MultiProvider(
    providers: [
      BlocProvider<AuthenticationBloc>(
        create: (context) {
          final authService = getIt<JwtAuthenticationService>();
          return AuthenticationBloc(authService)..add(AppLoaded());
        },
      ),
      BlocProvider<FavoriteBloc>(
        create: (context) {
          final productService = getIt<ProductService>();
          return FavoriteBloc(productService: productService);
        },
      ),
      BlocProvider<MessageBloc>(
        create: (context) {
          final messageService = getIt<MessageService>();
          return MessageBloc(messageService: messageService);
        },
      ),
      Provider<ProductService>(
        create: (context) => getIt<ProductService>(),
      ),
      Provider<MessageService>(
        create: (context) => getIt<MessageService>(),
      ),
      Provider<PlatformService>(
        create: (context) => getIt<PlatformService>(),
      ),
      Provider<CategoryService>(
        create: (context) => getIt<CategoryService>(),
      ),
    ],
    child: MyApp(),
  ));
}

class GlobalContext {
  static late BuildContext ctx;
}

class MyApp extends StatelessWidget {
  static late MyApp _instance;

  static Route route() {
    print("Enrutando al login");
    return MaterialPageRoute<void>(builder: (context) {
      var authBloc = BlocProvider.of<AuthenticationBloc>(context);
      authBloc..add(SessionExpiredEvent());
      return _instance;
    });
  }

  MyApp() {
    _instance = this;
  }

  @override
  Widget build(BuildContext context) {
    GlobalContext.ctx = context;
    return MaterialApp(
      title: 'Authentication Demo',
      theme: ThemeData(
        primarySwatch: getMaterialColor(Color(0xFF38B6FF)),
      ),
      debugShowCheckedModeBanner: false,
      home: BlocBuilder<AuthenticationBloc, AuthenticationState>(
        builder: (context, state) {
          if (state is AuthenticationAuthenticated) {
            // show home page
            return MainPage(user: state.user);
          }
          // otherwise show login page
          return LoginPage();
        },
      ),
    );
  }
}

MaterialColor getMaterialColor(Color color) {
  final int red = color.red;
  final int green = color.green;
  final int blue = color.blue;

  final Map<int, Color> shades = {
    50: Color.fromRGBO(red, green, blue, .1),
    100: Color.fromRGBO(red, green, blue, .2),
    200: Color.fromRGBO(red, green, blue, .3),
    300: Color.fromRGBO(red, green, blue, .4),
    400: Color.fromRGBO(red, green, blue, .5),
    500: Color.fromRGBO(red, green, blue, .6),
    600: Color.fromRGBO(red, green, blue, .7),
    700: Color.fromRGBO(red, green, blue, .8),
    800: Color.fromRGBO(red, green, blue, .9),
    900: Color.fromRGBO(red, green, blue, 1),
  };

  return MaterialColor(color.value, shades);
}
