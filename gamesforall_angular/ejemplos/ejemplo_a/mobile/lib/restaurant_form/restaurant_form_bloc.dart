import 'package:file_picker/src/platform_file.dart';
import 'package:flutter/material.dart';
import 'package:flutter_form_bloc/flutter_form_bloc.dart';
import 'package:front/service/restaurant_service.dart';

import '../config/locator.dart';
import '../model/restaurante_request.dart';

class RestaurantFormBloc extends FormBloc<String, String> {
  late final RestaurantService _restaurantService;
  late PlatformFile file;
  final nombre = TextFieldBloc(
    validators: [FieldBlocValidators.required],
  );
  final descripcion = TextFieldBloc(
    validators: [FieldBlocValidators.required],
  );

  final apertura = InputFieldBloc<TimeOfDay?, Object>(
    initialValue: TimeOfDay(hour: 12, minute: 00),
    validators: [FieldBlocValidators.required],
  );

  final cierre = InputFieldBloc<TimeOfDay?, Object>(
    initialValue: TimeOfDay(hour: 12, minute: 00),
    validators: [FieldBlocValidators.required],
  );

  RestaurantFormBloc() {
    _restaurantService = getIt<RestaurantService>();
    addFieldBlocs(fieldBlocs: [nombre, descripcion, apertura, cierre]);
  }

  @override
  void onSubmitting() async {
    try {
      await _restaurantService.create(
          RestauranteEditRequest(
              nombre.value,
              "${apertura.value!.hour < 10 ? "0${apertura.value!.hour}" : apertura.value!.hour}:${apertura.value!.minute < 10 ? "0${apertura.value!.minute}" : apertura.value!.minute}:00",
              "${cierre.value!.hour < 10 ? "0${cierre.value!.hour}" : cierre.value!.hour}:${cierre.value!.minute < 10 ? "0${cierre.value!.minute}" : cierre.value!.minute}:00",
              descripcion.value),
          file);
      emitSuccess();
    } catch (e) {
      emitFailure();
    }
  }

  void saveImg(PlatformFile newFile) {
    file = newFile;
  }
}
