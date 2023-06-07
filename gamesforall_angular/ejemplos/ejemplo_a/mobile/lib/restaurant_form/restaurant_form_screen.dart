import 'dart:async';
import 'dart:io';

import 'package:file_picker/file_picker.dart';
import 'package:flutter/material.dart';
import 'package:flutter_form_bloc/flutter_form_bloc.dart';
import 'package:front/home_screen.dart';
import 'package:front/restaurant_form/restaurant_form_bloc.dart';
import 'package:image_picker/image_picker.dart';

class RestaurantForm extends StatefulWidget {
  RestaurantForm({Key? key}) : super(key: key);

  @override
  State<RestaurantForm> createState() => _RestaurantFormState();
}

class _RestaurantFormState extends State<RestaurantForm> {
  final picker = ImagePicker();
  late File _image;
  FilePickerResult? result;
  bool imgSelected = false;

  @override
  Widget build(BuildContext context) {
    return BlocProvider(
      create: (context) => RestaurantFormBloc(),
      child: Builder(
        builder: (context) {
          final formBloc = BlocProvider.of<RestaurantFormBloc>(context);

          return Theme(
            data: Theme.of(context).copyWith(
              inputDecorationTheme: InputDecorationTheme(
                border: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(20),
                ),
              ),
            ),
            child: Builder(
              builder: (context) {
                return Scaffold(
                  backgroundColor: Colors.red.shade700,
                  appBar: AppBar(title: const Text('Restaurante')),
                  body: FormBlocListener<RestaurantFormBloc, String, String>(
                    onSubmitting: (context, state) {
                      LoadingDialog.show(context);
                    },
                    onSuccess: (context, state) {
                      LoadingDialog.hide(context);

                      Navigator.of(context).pushReplacement(
                          MaterialPageRoute(builder: (_) => SuccessScreen()));
                    },
                    onFailure: (context, state) {
                      LoadingDialog.hide(context);

                      ScaffoldMessenger.of(context).showSnackBar(
                          SnackBar(content: Text(state.failureResponse!)));
                    },
                    child: SingleChildScrollView(
                      physics: const ClampingScrollPhysics(),
                      child: Padding(
                        padding: const EdgeInsets.all(8.0),
                        child: Column(
                          children: <Widget>[
                            TextFieldBlocBuilder(
                              textFieldBloc: formBloc.nombre,
                              decoration: const InputDecoration(
                                labelText: 'Nombre',
                                prefixIcon: Icon(Icons.business),
                              ),
                            ),
                            TextFieldBlocBuilder(
                              textFieldBloc: formBloc.descripcion,
                              decoration: const InputDecoration(
                                labelText: 'Descripción',
                                prefixIcon: Icon(Icons.text_fields),
                              ),
                            ),
                            TimeFieldBlocBuilder(
                              timeFieldBloc: formBloc.apertura,
                              format: DateFormat('hh:mm a'),
                              initialTime: TimeOfDay(hour: 12, minute: 00),
                              decoration: const InputDecoration(
                                labelText: 'Apertura',
                                prefixIcon: Icon(Icons.access_time),
                              ),
                            ),
                            TimeFieldBlocBuilder(
                              timeFieldBloc: formBloc.cierre,
                              format: DateFormat('hh:mm a'),
                              initialTime: TimeOfDay(hour: 12, minute: 00),
                              decoration: const InputDecoration(
                                labelText: 'Cierre',
                                prefixIcon: Icon(Icons.time_to_leave),
                              ),
                            ),
                            ElevatedButton(
                                onPressed: () async {
                                  result = await FilePicker.platform.pickFiles(
                                    withData: true,
                                    allowMultiple: false,
                                    allowedExtensions: ['jpg', 'png'],
                                  );
                                  setState(() {
                                    imgSelected = result != null;
                                  });
                                  if (imgSelected)
                                    formBloc.saveImg(result!.files[0]);
                                },
                                child: Text("Seleccione una imagen")),
                            SizedBox(
                              height: 10,
                            ),
                            if (imgSelected)
                              Stack(
                                  alignment: Alignment.bottomCenter,
                                  children: <Widget>[
                                    ClipRRect(
                                      borderRadius: BorderRadius.circular(8.0),
                                      child: Image.file(
                                        File(result!.files[0].path!),
                                        fit: BoxFit.cover,
                                        width: double.infinity,
                                      ),
                                    ),
                                    Positioned(
                                      right: 8,
                                      top: 8,
                                      child: IconButton(
                                        color: Colors.white,
                                        onPressed: () {
                                          setState(() {
                                            result = null;
                                            imgSelected = false;
                                          });
                                        },
                                        icon: Icon(Icons.close),
                                      ),
                                    ),
                                  ]),
                            if (imgSelected)
                              SizedBox(
                                height: 10,
                              ),
                            ElevatedButton(
                              onPressed: imgSelected ? formBloc.submit : null,
                              child: const Text('Crear'),
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
              'Creado con éxito',
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
