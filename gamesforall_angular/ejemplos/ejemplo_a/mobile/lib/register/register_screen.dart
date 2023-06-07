import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter_form_bloc/flutter_form_bloc.dart';
import 'package:front/login/login_screen.dart';
import 'package:front/register/register_bloc.dart';

import '../home_screen.dart';

const textBaseStyle = TextStyle(color: Colors.white);
const textDetailStyle =
    TextStyle(color: Colors.white, fontWeight: FontWeight.w300, fontSize: 12);

class RegisterForm extends StatefulWidget {
  const RegisterForm({Key? key}) : super(key: key);

  @override
  _RegisterFormState createState() => _RegisterFormState();
}

class _RegisterFormState extends State<RegisterForm> {
  var _type = StepperType.horizontal;

  @override
  Widget build(BuildContext context) {
    return BlocProvider(
      create: (context) => RegisterFormBloc(),
      child: Builder(
        builder: (context) {
          return Theme(
            data: Theme.of(context).copyWith(
              inputDecorationTheme: InputDecorationTheme(
                border: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(20),
                ),
              ),
            ),
            child: Scaffold(
              backgroundColor: Colors.red.shade700,
              resizeToAvoidBottomInset: false,
              appBar: AppBar(
                title: const Text('Registro'),
              ),
              body: SafeArea(
                child: FormBlocListener<RegisterFormBloc, String, String>(
                  onSubmitting: (context, state) => LoadingDialog.show(context),
                  onSubmissionFailed: (context, state) =>
                      LoadingDialog.hide(context),
                  onSuccess: (context, state) {
                    LoadingDialog.hide(context);
                    if (state.stepCompleted == state.lastStep) {
                      Navigator.of(context).pushReplacement(MaterialPageRoute(
                          builder: (context) => SuccessScreen()));
                    }
                  },
                  onFailure: (context, state) {
                    LoadingDialog.hide(context);
                  },
                  child: StepperFormBlocBuilder<RegisterFormBloc>(
                    formBloc: context.read<RegisterFormBloc>(),
                    type: _type,
                    physics: const ClampingScrollPhysics(),
                    stepsBuilder: (formBloc) {
                      return [
                        _registerTypeStep(formBloc!),
                        _personalStep(formBloc),
                      ];
                    },
                  ),
                ),
              ),
            ),
          );
        },
      ),
    );
  }

  FormBlocStep _registerTypeStep(RegisterFormBloc registerFormBloc) {
    return FormBlocStep(
      title: const Text('¿Eres cliente?'),
      content: Column(
        children: <Widget>[
          Container(
              height: 300,
              width: 300,
              child: Image.asset("assets/register_img.png")),
          SwitchFieldBlocBuilder(
            booleanFieldBloc: registerFormBloc.registerAsOwner,
            body: Container(
              alignment: Alignment.centerLeft,
              child: const Text(
                '¿Lo que quieres es repartir tu comida con nuestra ayuda?, ¡Marca esta casilla y te ayudamos a hacerlo!',
                style: textDetailStyle,
              ),
            ),
          ),
        ],
      ),
    );
  }

  FormBlocStep _personalStep(RegisterFormBloc registerFormBloc) {
    return FormBlocStep(
      title: const Text('Datos personales'),
      content: Column(
        children: <Widget>[
          TextFieldBlocBuilder(
            textStyle: textBaseStyle,
            textFieldBloc: registerFormBloc.username,
            keyboardType: TextInputType.name,
            decoration: const InputDecoration(
              labelText: 'Username',
              prefixIcon: Icon(Icons.person),
            ),
          ),
          TextFieldBlocBuilder(
            textStyle: textBaseStyle,
            textFieldBloc: registerFormBloc.password,
            keyboardType: TextInputType.visiblePassword,
            decoration: const InputDecoration(
              labelText: 'Contraseña',
              prefixIcon: Icon(Icons.password),
            ),
          ),
          TextFieldBlocBuilder(
            textStyle: textBaseStyle,
            textFieldBloc: registerFormBloc.verifyPassword,
            keyboardType: TextInputType.visiblePassword,
            decoration: const InputDecoration(
              labelText: 'Verificación de contraseña',
              prefixIcon: Icon(Icons.password),
            ),
          ),
          TextFieldBlocBuilder(
            textStyle: textBaseStyle,
            textFieldBloc: registerFormBloc.email,
            keyboardType: TextInputType.emailAddress,
            decoration: const InputDecoration(
              labelText: 'email',
              prefixIcon: Icon(Icons.email),
            ),
          ),
          TextFieldBlocBuilder(
            textStyle: TextStyle(color: Colors.white),
            cursorColor: Colors.white,
            textFieldBloc: registerFormBloc.name,
            keyboardType: TextInputType.name,
            decoration: const InputDecoration(
              focusColor: Colors.white,
              focusedBorder: OutlineInputBorder(
                  borderRadius: BorderRadius.all(Radius.circular(20)),
                  borderSide:
                      const BorderSide(color: Colors.white, width: 2.0)),
              labelText: 'Nombre completo',
              prefixIcon: Icon(Icons.person_2_outlined),
            ),
          ),
        ],
      ),
    );
  }
}

class LoadingDialog extends StatelessWidget {
  static void show(BuildContext context, {Key? key}) => showDialog<void>(
        context: context,
        useRootNavigator: false,
        barrierDismissible: false,
        builder: (_) => LoadingDialog(key: key),
      ).then((_) => FocusScope.of(context).requestFocus(FocusNode()));

  static void hide(BuildContext context) => Navigator.pop(context);

  const LoadingDialog({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return WillPopScope(
      onWillPop: () async => false,
      child: Center(
        child: Card(
          child: Container(
            width: 80,
            height: 80,
            padding: const EdgeInsets.all(12.0),
            child: const CircularProgressIndicator(),
          ),
        ),
      ),
    );
  }
}

class SuccessScreen extends StatefulWidget {
  const SuccessScreen({Key? key}) : super(key: key);

  @override
  State<SuccessScreen> createState() => _SuccessScreenState();
}

class _SuccessScreenState extends State<SuccessScreen> {
  @override
  void initState() {
    Timer(Duration(seconds: 3), () {
      Navigator.pushReplacement(
          context,
          MaterialPageRoute(
              builder: (context) => HomeScreen.withIndex(
                    indexInitial: 0,
                  )));
    });
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Theme.of(context).colorScheme.onPrimary,
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            const Icon(
              Icons.check_circle_outline_rounded,
              size: 100,
              color: Colors.white,
            ),
            const SizedBox(height: 10),
            const Text(
              'Registrado con éxito',
              style: TextStyle(fontSize: 24, color: Colors.white),
              textAlign: TextAlign.center,
            ),
            const SizedBox(height: 10),
          ],
        ),
      ),
    );
  }
}
