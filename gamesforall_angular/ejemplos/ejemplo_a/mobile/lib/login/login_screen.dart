import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

import '../auth/auth_bloc.dart';
import '../config/locator.dart';
import '../register/register_screen.dart';
import '../service/auth_service.dart';
import 'login_bloc.dart';

class LoginScreen extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.red.shade700,
      body: SafeArea(
          minimum: const EdgeInsets.all(16),
          child: BlocBuilder<AuthenticationBloc, AuthenticationState>(
            builder: (context, state) {
              final authBloc = BlocProvider.of<AuthenticationBloc>(context);
              if (state is AuthenticationNotAuthenticated) {
                return _AuthForm();
              }
              if (state is AuthenticationFailure) {
                return Center(
                    child: Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  crossAxisAlignment: CrossAxisAlignment.center,
                  children: <Widget>[
                    Text(state.message),
                    TextButton(
                      //textColor: Theme.of(context).primaryColor,
                      child: Text('Retry'),
                      onPressed: () {
                        authBloc.add(AppLoaded());
                      },
                    )
                  ],
                ));
              }
              if (state is AuthenticationAuthenticated) {
                Navigator.of(context).pop();
              }
              return Center(
                child: CircularProgressIndicator(
                  strokeWidth: 2,
                ),
              );
            },
          )),
    );
  }
}

class _AuthForm extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    //final authService = RepositoryProvider.of<AuthenticationService>(context);
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
  final _usernameController = TextEditingController();
  bool _autoValidate = false;

  @override
  Widget build(BuildContext context) {
    final _loginBloc = BlocProvider.of<LoginBloc>(context);

    _onLoginButtonPressed() {
      if (_key.currentState!.validate()) {
        _loginBloc.add(LoginInWithUsernameButtonPressed(
            username: _usernameController.text,
            password: _passwordController.text));
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
          return Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Image.asset(
                "assets/logowhite.png",
                height: 250,
              ),
              Form(
                key: _key,
                autovalidateMode: _autoValidate
                    ? AutovalidateMode.always
                    : AutovalidateMode.disabled,
                child: SingleChildScrollView(
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.stretch,
                    children: <Widget>[
                      TextFormField(
                        decoration: InputDecoration(
                            labelStyle: TextStyle(
                                color: Colors.white,
                                fontWeight: FontWeight.w200),
                            labelText: 'Username',
                            filled: true,
                            isDense: true,
                            icon: Icon(Icons.person)),
                        controller: _usernameController,
                        keyboardType: TextInputType.text,
                        autocorrect: false,
                        validator: (value) {
                          if (value == null) {
                            return 'Username is required.';
                          }
                          return null;
                        },
                      ),
                      SizedBox(
                        height: 12,
                      ),
                      TextFormField(
                        decoration: InputDecoration(
                          focusColor: Colors.white,
                          labelStyle: TextStyle(
                              color: Colors.white, fontWeight: FontWeight.w200),
                          labelText: 'Contraseña',
                          filled: true,
                          isDense: true,
                          icon: Icon(Icons.password),
                        ),
                        obscureText: true,
                        controller: _passwordController,
                        validator: (value) {
                          if (value == null) {
                            return 'Password is required.';
                          }
                          return null;
                        },
                      ),
                      const SizedBox(
                        height: 16,
                      ),
                      //RaisedButton(
                      ElevatedButton(
                        //textColor: Colors.white,
                        //padding: const EdgeInsets.all(16),
                        //shape: new RoundedRectangleBorder(borderRadius: new BorderRadius.circular(8.0)),
                        child: Text('LOG IN'),
                        onPressed: state is LoginLoading
                            ? () {}
                            : _onLoginButtonPressed,
                      ),
                      Padding(
                        padding: EdgeInsets.all(5),
                        child: Row(
                          mainAxisAlignment: MainAxisAlignment.center,
                          children: [
                            Text("¿No tienes cuenta?"),
                            TextButton(
                                style: TextButton.styleFrom(
                                  foregroundColor: Colors.white,
                                ),
                                onPressed: () => Navigator.push(
                                    context,
                                    MaterialPageRoute(
                                        builder: (context) => RegisterForm())),
                                child: Text("Registrate aquí."))
                          ],
                        ),
                      )
                    ],
                  ),
                ),
              )
            ],
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
