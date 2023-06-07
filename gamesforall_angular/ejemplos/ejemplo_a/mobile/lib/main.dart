import 'package:bloc/bloc.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:front/config/locator.dart';
import 'package:front/home_screen.dart';
import 'package:front/landing/landing_screen.dart';
import 'package:front/service/auth_service.dart';

import 'auth/auth_bloc.dart';

void main() {
  setupAsyncDependencies();
  configureDependencies();
  runApp(BlocProvider<AuthenticationBloc>(
    create: (context) {
      final authService = getIt<JwtAuthenticationService>();
      return AuthenticationBloc(authService)..add(AppLoaded());
    },
    child: App(),
  ));
}

class App extends MaterialApp {
  App({super.key})
      : super(
            debugShowCheckedModeBanner: false,
            home: HomeScreen(),
            theme: ThemeData(
                sliderTheme: SliderThemeData(
                  showValueIndicator: ShowValueIndicator.always,
                  trackHeight: 1,
                ),
                colorScheme: ColorScheme(
                    brightness: Brightness.light,
                    primary: Colors.white,
                    onPrimary: Colors.red.shade700,
                    secondary: Colors.black54,
                    onSecondary: Colors.red.shade600,
                    error: Color.fromARGB(255, 75, 7, 7),
                    onError: Color.fromARGB(255, 255, 255, 255),
                    background: Colors.white,
                    onBackground: Colors.red.shade700,
                    surface: Colors.grey,
                    onSurface: Colors.black),
                fontFamily: 'Inter',
                inputDecorationTheme:
                    InputDecorationTheme(focusColor: Colors.white)));
}
