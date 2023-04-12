
import 'package:flutter_form_bloc/flutter_form_bloc.dart';
import '../config/locator.dart';
import '../services/authentication_service.dart';

class RegisterFormBloc extends FormBloc<String, String> {
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

  final fullName = TextFieldBloc(
    validators: [FieldBlocValidators.required],
  );

  RegisterFormBloc() {
    _authenticationService = getIt<JwtAuthenticationService>();
    addFieldBlocs(
      fieldBlocs: [username, password, verifyPassword, fullName],
    );
  }

  @override
  void onSubmitting() async {
    if (password.value != verifyPassword.value) {
      verifyPassword.addFieldError("Las contraseñas no coinciden");
      emitFailure();
    } else {
      try {
        final result = await _authenticationService.registerUser(username.value,
            password.value, verifyPassword.value, fullName.value);
        emitSuccess();
      } on Exception catch (e) {
        emitFailure();
      }
    }
  }
}
