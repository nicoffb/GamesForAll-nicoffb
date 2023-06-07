import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter_form_bloc/flutter_form_bloc.dart';
import 'package:front/password_change/password_change_bloc.dart';

class PassWordChangeScreen extends StatelessWidget {
  const PassWordChangeScreen({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return BlocProvider(
      create: (context) => PassWordChangeBloc(),
      child: Builder(
        builder: (context) {
          final loginFormBloc = context.read<PassWordChangeBloc>();

          return Scaffold(
            resizeToAvoidBottomInset: false,
            appBar: AppBar(title: const Text('Cambiar contraseña')),
            body: FormBlocListener<PassWordChangeBloc, String, String>(
              onSubmitting: (context, state) {
                LoadingDialog.show(context);
              },
              onSubmissionFailed: (context, state) {
                LoadingDialog.hide(context);
              },
              onSuccess: (context, state) {
                LoadingDialog.hide(context);
                ScaffoldMessenger.of(context).showSnackBar(
                    SnackBar(content: Text("Contraseña cambiada con éxito")));
                Timer(Duration(seconds: 2), () {
                  Navigator.of(context).pop();
                });
              },
              onFailure: (context, state) {
                LoadingDialog.hide(context);
                ScaffoldMessenger.of(context).showSnackBar(
                    SnackBar(content: Text(state.failureResponse!)));
              },
              child: SingleChildScrollView(
                  physics: const ClampingScrollPhysics(),
                  child: Padding(
                    padding: EdgeInsets.all(25),
                    child: Column(
                      children: <Widget>[
                        TextFieldBlocBuilder(
                          textFieldBloc: loginFormBloc.oldPassword,
                          decoration: const InputDecoration(
                            labelText: 'Contraseña antigua',
                            prefixIcon: Icon(Icons.lock),
                          ),
                        ),
                        TextFieldBlocBuilder(
                          textFieldBloc: loginFormBloc.password,
                          autofillHints: const [AutofillHints.password],
                          keyboardType: TextInputType.emailAddress,
                          decoration: const InputDecoration(
                            labelText: 'Nueva contraseña',
                            prefixIcon: Icon(Icons.lock_outline),
                          ),
                        ),
                        TextFieldBlocBuilder(
                          textFieldBloc: loginFormBloc.confirmPassword,
                          decoration: const InputDecoration(
                            labelText: 'Confirme contraseña',
                            prefixIcon: Icon(Icons.lock),
                          ),
                        ),
                        ElevatedButton(
                          onPressed: loginFormBloc.submit,
                          child: const Text('SUBMIT'),
                        ),
                      ],
                    ),
                  )),
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
