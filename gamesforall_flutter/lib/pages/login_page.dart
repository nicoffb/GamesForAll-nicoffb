import 'package:eva_icons_flutter/eva_icons_flutter.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:gamesforall_frontend/pages/main_page.dart';
import 'package:gamesforall_frontend/pages/register_page.dart';
import '../config/locator.dart';
import '../blocs/blocs.dart';
import '../services/services.dart';
import '../style/theme.dart' as Style;

class LoginPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.white,
      body: Column(
        mainAxisAlignment: MainAxisAlignment.start,
        crossAxisAlignment: CrossAxisAlignment.stretch,
        children: <Widget>[
          Container(
            height: MediaQuery.of(context).size.height * 0.4,
            child: Image.asset('assets/gamesforall.png'),
          ),
          Container(
            color: Colors.white,
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: <Widget>[
                SizedBox(height: 10.0),
                Text(
                  'De uno para todos y de todos para uno',
                  style: TextStyle(
                      fontSize: 18.0,
                      color: Color(0xFF38B6FF),
                      fontWeight: FontWeight.bold),
                  textAlign: TextAlign.center,
                ),
              ],
            ),
          ),
          Expanded(
            child: SafeArea(
              minimum: const EdgeInsets.all(16),
              child: BlocBuilder<AuthenticationBloc, AuthenticationState>(
                builder: (context, state) {
                  final authBloc = BlocProvider.of<AuthenticationBloc>(context);
                  if (state is AuthenticationNotAuthenticated) {
                    return _AuthForm();
                  }
                  if (state is AuthenticationFailure ||
                      state is SessionExpiredState) {
                    var msg = (state as AuthenticationFailure).message;
                    return Center(
                        child: Column(
                      mainAxisAlignment: MainAxisAlignment.center,
                      crossAxisAlignment: CrossAxisAlignment.center,
                      children: <Widget>[
                        Text(msg),
                        TextButton(
                          style: TextButton.styleFrom(
                            foregroundColor: Color(0xFF38B6FF),
                          ),
                          child: Text('Retry'),
                          onPressed: () {
                            authBloc.add(UserLoggedOut());
                          },
                        )
                      ],
                    ));
                  }
                  if (state is AuthenticationAuthenticated) {
                    return MainPage(user: state.user);
                  }
                  return Center(
                    child: CircularProgressIndicator(
                      strokeWidth: 2,
                    ),
                  );
                },
              ),
            ),
          )
        ],
      ),
    );
  }
}

class _AuthForm extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final authService = getIt<JwtAuthenticationService>();
    final authBloc = BlocProvider.of<AuthenticationBloc>(context);

    return Container(
      alignment: Alignment.center,
      child: BlocProvider<LoginBloc>(
        create: (context) => LoginBloc(authBloc, authService),
        child: _SignInForm(),
      ),
    );
  }
}

class _SignInForm extends StatefulWidget {
  @override
  __SignInFormState createState() => __SignInFormState();
}

class __SignInFormState extends State<_SignInForm> {
  final GlobalKey<FormState> _key = GlobalKey<FormState>();
  final _passwordController = TextEditingController();
  final _emailController = TextEditingController();
  bool _autoValidate = false;

  @override
  Widget build(BuildContext context) {
    final _loginBloc = BlocProvider.of<LoginBloc>(context);

    _onLoginButtonPressed() {
      if (_key.currentState!.validate()) {
        _loginBloc.add(LoginInWithEmailButtonPressed(
            email: _emailController.text, password: _passwordController.text));
      } else {
        setState(() {
          _autoValidate = true;
        });
      }
    }

    return BlocListener<LoginBloc, LoginState>(
      listener: (context, state) {
        if (state is LoginFailure) {
          _showError(state.error);
        }
      },
      child: BlocBuilder<LoginBloc, LoginState>(
        builder: (context, state) {
          if (state is LoginLoading) {
            return Center(
              child: CircularProgressIndicator(),
            );
          }

          return Container(
            color: Colors.white,
            child: Form(
              key: _key,
              autovalidateMode: _autoValidate
                  ? AutovalidateMode.always
                  : AutovalidateMode.disabled,
              child: SingleChildScrollView(
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.stretch,
                  children: <Widget>[
                    TextFormField(
                      style: TextStyle(
                          fontSize: 14.0,
                          color: Style.Colors.titleColor,
                          fontWeight: FontWeight.bold),
                      decoration: InputDecoration(
                        prefixIcon:
                            Icon(EvaIcons.people, color: Colors.black26),
                        enabledBorder: OutlineInputBorder(
                            borderSide: new BorderSide(color: Colors.black12),
                            borderRadius: BorderRadius.circular(30.0)),
                        focusedBorder: OutlineInputBorder(
                            borderSide:
                                new BorderSide(color: Style.Colors.mainColor),
                            borderRadius: BorderRadius.circular(30.0)),
                        contentPadding:
                            EdgeInsets.only(left: 10.0, right: 10.0),
                        labelText: "Nombre de usuario",
                        hintStyle: TextStyle(
                            fontSize: 12.0,
                            color: Style.Colors.grey,
                            fontWeight: FontWeight.w500),
                        labelStyle: TextStyle(
                            fontSize: 12.0,
                            color: Colors.grey,
                            fontWeight: FontWeight.w500),
                      ),
                      controller: _emailController,
                      autocorrect: false,
                      validator: (value) {
                        if (value == null) {
                          return 'El nombre de usuario es obligatorio';
                        }
                        return null;
                      },
                    ),
                    SizedBox(
                      height: 12,
                    ),
                    TextFormField(
                      style: TextStyle(
                          fontSize: 14.0,
                          color: Style.Colors.titleColor,
                          fontWeight: FontWeight.bold),
                      decoration: InputDecoration(
                        fillColor: Colors.white,
                        prefixIcon: Icon(
                          EvaIcons.lockOutline,
                          color: Colors.black26,
                        ),
                        enabledBorder: OutlineInputBorder(
                            borderSide: new BorderSide(color: Colors.black12),
                            borderRadius: BorderRadius.circular(30.0)),
                        focusedBorder: OutlineInputBorder(
                            borderSide:
                                new BorderSide(color: Style.Colors.mainColor),
                            borderRadius: BorderRadius.circular(30.0)),
                        contentPadding:
                            EdgeInsets.only(left: 10.0, right: 10.0),
                        labelText: "Contraseña",
                        hintStyle: TextStyle(
                            fontSize: 12.0,
                            color: Style.Colors.grey,
                            fontWeight: FontWeight.w500),
                        labelStyle: TextStyle(
                            fontSize: 12.0,
                            color: Colors.grey,
                            fontWeight: FontWeight.w500),
                      ),
                      obscureText: true,
                      controller: _passwordController,
                      validator: (value) {
                        if (value == null) {
                          return 'La contraseña es obligatoria';
                        }
                        return null;
                      },
                    ),
                    const SizedBox(
                      height: 16,
                    ),
                    Row(
                      children: [
                        Expanded(
                          child: Container(),
                        ),
                        TextButton(
                          onPressed: () {},
                          child: Text("¿Has olvidado tu contraseña?"),
                          style: TextButton.styleFrom(
                            foregroundColor: Color(0xFF38B6FF),
                          ),
                        ),
                      ],
                    ),
                    ElevatedButton(
                      child: Text('Iniciar sesión'),
                      onPressed:
                          state is LoginLoading ? () {} : _onLoginButtonPressed,
                      style: ElevatedButton.styleFrom(
                        backgroundColor: Color(0xFF38B6FF),
                      ),
                    ),
                    Padding(
                      padding: const EdgeInsets.symmetric(
                          horizontal: 24.0, vertical: 16.0),
                      child: Row(
                        mainAxisAlignment: MainAxisAlignment.center,
                        children: [
                          Text('¿No tienes cuenta?'),
                          const SizedBox(width: 8.0),
                          TextButton(
                            onPressed: () {
                              Navigator.push(
                                context,
                                PageRouteBuilder(
                                  transitionDuration:
                                      Duration(milliseconds: 500),
                                  pageBuilder: (_, __, ___) => RegisterForm(),
                                  transitionsBuilder:
                                      (_, animation, __, child) {
                                    return SlideTransition(
                                      position: Tween<Offset>(
                                        begin: const Offset(1, 0),
                                        end: Offset.zero,
                                      ).animate(animation),
                                      child: child,
                                    );
                                  },
                                ),
                              );
                            },
                            child: Text('Regístrate'),
                            style: TextButton.styleFrom(
                              foregroundColor: Color(0xFF38B6FF),
                            ),
                          ),
                        ],
                      ),
                    ),
                  ],
                ),
              ),
            ),
          );
        },
      ),
    );
  }

  void _showError(String error) {
    /*Scaffold.of(context).showSnackBar(SnackBar(
      content: Text(error),
      backgroundColor: Theme.of(context).errorColor,
    ));*/

    ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text(error)));
  }
}
