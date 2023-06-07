import 'package:flutter/material.dart';
import 'package:flutter_form_bloc/flutter_form_bloc.dart';

import '../config/locator.dart';
import '../model/change_password.dart';
import '../service/auth_service.dart';

class PassWordChangeBloc extends FormBloc<String, String> {
  late final AuthenticationService _authService;
  final oldPassword = TextFieldBloc(
    validators: [FieldBlocValidators.required],
  );
  final password = TextFieldBloc(
    validators: [FieldBlocValidators.required],
  );
  final confirmPassword = TextFieldBloc(
    validators: [FieldBlocValidators.required],
  );

  Validator<String> _confirmPassword(
    TextFieldBloc passwordTextFieldBloc,
  ) {
    return (String? confirmPassword) {
      if (confirmPassword == passwordTextFieldBloc.value) {
        return null;
      }
      return 'Las contraseñas deben ser iguales';
    };
  }

  Validator<String> _differentPassword(
    TextFieldBloc passwordTextFieldBloc,
  ) {
    return (String? confirmPassword) {
      if (confirmPassword != passwordTextFieldBloc.value) {
        return null;
      }
      return 'Las contraseñas deben ser diferentes';
    };
  }

  PassWordChangeBloc() {
    _authService = getIt<JwtAuthenticationService>();
    addFieldBlocs(
      fieldBlocs: [oldPassword, password, confirmPassword],
    );

    confirmPassword
      ..addValidators([_confirmPassword(password)])
      ..subscribeToFieldBlocs([password]);

    password
      ..addValidators([_differentPassword(oldPassword)])
      ..subscribeToFieldBlocs([oldPassword]);
  }

  @override
  void onSubmitting() async {
    try {
      await _authService.changePassWord(ChangePasswordRequest(
          oldPassword.value, password.value, confirmPassword.value));
      emitSuccess();
    } on Exception catch (e) {
      emitFailure(failureResponse: e.toString());
    }
  }
}
