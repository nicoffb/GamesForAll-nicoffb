import 'package:flutter_form_bloc/flutter_form_bloc.dart';

import '../config/locator.dart';
import '../service/auth_service.dart';

class RegisterFormBloc extends FormBloc<String, String> {
  final registerAsOwner = BooleanFieldBloc();
  late final AuthenticationService _authenticationService;

  final username = TextFieldBloc(
    validators: [FieldBlocValidators.required],
  );

  final password = TextFieldBloc(
    validators: [
      FieldBlocValidators.required,
      FieldBlocValidators.passwordMin6Chars,
    ],
  );

  final verifyPassword = TextFieldBloc(
    validators: [
      FieldBlocValidators.required,
      FieldBlocValidators.passwordMin6Chars,
    ],
  );

  final email = TextFieldBloc<String>(
    validators: [
      FieldBlocValidators.required,
      FieldBlocValidators.email,
    ],
  );

  final name = TextFieldBloc(
    validators: [FieldBlocValidators.required],
  );

  RegisterFormBloc() {
    _authenticationService = getIt<JwtAuthenticationService>();
    addFieldBlocs(
      step: 0,
      fieldBlocs: [registerAsOwner],
    );
    addFieldBlocs(
      step: 1,
      fieldBlocs: [username, password, verifyPassword, email, name],
    );
  }

  @override
  void onSubmitting() async {
    if (state.currentStep == 0) {
      emitSuccess();
    } else if (state.currentStep == 1) {
      if (password.value != verifyPassword.value) {
        verifyPassword.addFieldError("Las contraseñas no coinciden");
        emitFailure();
      } else {
        try {
          if (registerAsOwner.value) {
            final result = await _authenticationService.registerOwner(
                username.value,
                password.value,
                verifyPassword.value,
                email.value,
                name.value);
            emitSuccess();
          } else {
            final result = await _authenticationService.registerClient(
                username.value,
                password.value,
                verifyPassword.value,
                email.value,
                name.value);
            emitSuccess();
          }
        } on Exception catch (e) {
          emitFailure();
        }
      }
    }
  }
}
