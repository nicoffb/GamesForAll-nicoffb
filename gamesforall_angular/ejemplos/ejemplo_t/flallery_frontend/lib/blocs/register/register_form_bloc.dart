import 'package:flallery_frontend/pages/login_page.dart';
import 'package:flallery_frontend/services/authentication_service.dart';
import 'package:flutter/material.dart';
import 'package:flutter_form_bloc/flutter_form_bloc.dart';

class RegisterFormBloc extends FormBloc<String, String> {
  late final JwtAuthenticationService authenticationService =
      new JwtAuthenticationService();
  final username = TextFieldBloc(
    validators: [
      FieldBlocValidators.required,
    ],
  );

  final avatar = TextFieldBloc(
    validators: [
      FieldBlocValidators.required,
    ],
  );

  final fullName = TextFieldBloc(
    validators: [
      FieldBlocValidators.required,
    ],
  );

  final password = TextFieldBloc(
    validators: [
      FieldBlocValidators.required,
    ],
  );

  final verifyPassword = TextFieldBloc(
    validators: [
      FieldBlocValidators.required,
    ],
  );

  final showSuccessResponse = BooleanFieldBloc();

  RegisterFormBloc() {
    addFieldBlocs(
      fieldBlocs: [
        username,
        avatar,
        password,
        verifyPassword,
        fullName,
      ],
    );
  }
  @override
  void onSubmitting() async {
    // await Future<void>.delayed(const Duration(seconds: 1));
    try {
      final result = await authenticationService.registerUser(username.value,
          password.value, verifyPassword.value, avatar.value, fullName.value);
      emitSuccess();
    } on Exception catch (_) {
      emitFailure();
    }
  }
}

class RegisterForm extends StatelessWidget {
  const RegisterForm({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return BlocProvider(
      create: (context) => RegisterFormBloc(),
      child: Builder(
        builder: (context) {
          final registerFormBloc = context.read<RegisterFormBloc>();

          return Scaffold(
            resizeToAvoidBottomInset: false,
            appBar: AppBar(title: const Text('Register')),
            body: FormBlocListener<RegisterFormBloc, String, String>(
              onSubmitting: (context, state) {
                LoadingDialog.show(context);
                Navigator.push(context, MaterialPageRoute(builder: (context) {
                  return ErrorScreen();
                }));
              },
              onSubmissionFailed: (context, state) {
                LoadingDialog.hide(context);
                Navigator.push(context, MaterialPageRoute(builder: (context) {
                  return ErrorScreen();
                }));
              },
              onSuccess: (context, state) {
                LoadingDialog.hide(context);

                Navigator.push(context, MaterialPageRoute(builder: (context) {
                  return SuccessScreen();
                }));
              },
              onFailure: (context, state) {
                LoadingDialog.hide(context);

                Navigator.push(context, MaterialPageRoute(builder: (context) {
                  return ErrorScreen();
                }));
              },
              child: SingleChildScrollView(
                physics: const ClampingScrollPhysics(),
                child: AutofillGroup(
                  child: Column(
                    children: <Widget>[
                      TextFieldBlocBuilder(
                        textFieldBloc: registerFormBloc.username,
                        keyboardType: TextInputType.name,
                        autofillHints: const [
                          AutofillHints.username,
                        ],
                        decoration: const InputDecoration(
                          labelText: 'Username',
                          prefixIcon: Icon(Icons.man),
                        ),
                      ),
                      TextFieldBlocBuilder(
                        textFieldBloc: registerFormBloc.password,
                        keyboardType: TextInputType.visiblePassword,
                        suffixButton: SuffixButton.obscureText,
                        autofillHints: const [
                          AutofillHints.password,
                        ],
                        decoration: const InputDecoration(
                          labelText: 'Password',
                          prefixIcon: Icon(Icons.password),
                        ),
                      ),
                      TextFieldBlocBuilder(
                        textFieldBloc: registerFormBloc.verifyPassword,
                        keyboardType: TextInputType.visiblePassword,
                        suffixButton: SuffixButton.obscureText,
                        autofillHints: const [
                          AutofillHints.password,
                        ],
                        decoration: const InputDecoration(
                          labelText: 'Verify Password',
                          prefixIcon: Icon(Icons.password),
                        ),
                      ),
                      TextFieldBlocBuilder(
                        textFieldBloc: registerFormBloc.avatar,
                        keyboardType: TextInputType.url,
                        autofillHints: const [
                          AutofillHints.url,
                        ],
                        decoration: const InputDecoration(
                          labelText: 'Avatar (URL Image)',
                          prefixIcon: Icon(Icons.insert_emoticon_sharp),
                        ),
                      ),
                      TextFieldBlocBuilder(
                        textFieldBloc: registerFormBloc.fullName,
                        autofillHints: const [AutofillHints.name],
                        decoration: const InputDecoration(
                          labelText: 'Full Name',
                          prefixIcon: Icon(Icons.abc),
                        ),
                      ),
                      Text(''),
                      ElevatedButton(
                        onPressed: () {
                          registerFormBloc.onSubmitting();
                        },
                        child: const Text('REGISTER'),
                      ),
                      Text(''),
                      ElevatedButton.icon(
                        onPressed: () => Navigator.of(context).pop(),
                        icon: const Icon(Icons.replay),
                        label: const Text('Volver'),
                      ),
                    ],
                  ),
                ),
              ),
            ),
          );
        },
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

class SuccessScreen extends StatelessWidget {
  const SuccessScreen({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            const Icon(Icons.tag_faces, size: 100),
            const SizedBox(height: 10),
            const Text(
              'User created',
              style: TextStyle(fontSize: 54, color: Colors.black),
              textAlign: TextAlign.center,
            ),
            const SizedBox(height: 10),
            ElevatedButton.icon(
              onPressed: () => Navigator.of(context).pushReplacement(
                  MaterialPageRoute(builder: (_) => LoginPage())),
              icon: const Icon(Icons.replay),
              label: const Text('Log in'),
            ),
          ],
        ),
      ),
    );
  }
}

class ErrorScreen extends StatelessWidget {
  const ErrorScreen({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            const Icon(Icons.error_outline, size: 100),
            const SizedBox(height: 10),
            const Text(
              'Ha habido un fallo en el registro:',
              style: TextStyle(fontSize: 30, color: Colors.black),
              textAlign: TextAlign.center,
            ),
            const SizedBox(height: 10),
            ElevatedButton.icon(
              onPressed: () => Navigator.of(context).pushReplacement(
                  MaterialPageRoute(builder: (_) => RegisterForm())),
              icon: const Icon(Icons.replay),
              label: const Text('Volver'),
            ),
          ],
        ),
      ),
    );
  }
}
