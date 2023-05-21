import 'dart:io';

import 'package:bloc/bloc.dart';
import 'package:equatable/equatable.dart';
import 'package:file_picker/file_picker.dart';
import 'package:flutter/material.dart';
import 'package:flutter_form_bloc/flutter_form_bloc.dart';
import 'package:gamesforall_frontend/blocs/uploadProduct/upload_product_event.dart';
import 'package:gamesforall_frontend/blocs/uploadProduct/upload_product_state.dart';
import 'package:gamesforall_frontend/pages/upload_product_page.dart';
import 'package:gamesforall_frontend/services/product_service.dart';

import '../../models/product_request.dart';

class UploadProductBloc extends FormBloc<String, String> {
  late ProductService productService;

  final title = TextFieldBloc(
    validators: [
      FieldBlocValidators.required,
    ],
  );

  final price = TextFieldBloc(
    validators: [
      FieldBlocValidators.required,
    ],
  );

  final showSuccessResponse = BooleanFieldBloc();

  File? selectedImage;

  UploadProductBloc() : super() {
    productService = ProductService();
    addFieldBlocs(
      fieldBlocs: [
        title,
        price,
        showSuccessResponse,
      ],
    );
  }

  void selectImage(BuildContext context) async {
    final file = await FilePicker.platform.pickFiles(
        type: FileType.custom, allowedExtensions: ['jpg', 'jpeg', 'png']);
    if (file != null) {
      selectedImage = File(file.files.single.path!);
      // Aquí, podrías considerar emitir un evento que cambie el estado de tu aplicación,
      // posiblemente emitiendo un evento con el Bloc o cambiando el estado de tu FormBloc.
    }
  }

  @override
  void onSubmitting() async {
    try {
      debugPrint(title.value);
      debugPrint(price.value);
      debugPrint(showSuccessResponse.value.toString());
      debugPrint(selectedImage.toString());
      if (selectedImage != null) {
        productService.add(
          ProductRequest(
            title: title.value,
            price: double.parse(price.value),
          ),
          PlatformFile(
            name: selectedImage!.path.split('/').last,
            bytes: await selectedImage!.readAsBytes(),
            size: selectedImage!.lengthSync(),
          ),
        );
      }

      if (showSuccessResponse.value) {
        emitSuccess();
      } else {
        emitFailure(failureResponse: 'This is an awesome error!');
      }
    } catch (e) {
      print('Error: $e');
      emitFailure(failureResponse: 'Error: $e');
    }
  }
}

////////////////////////////////////////////////////////////////////////////////////////////

class ProductForm extends StatelessWidget {
  const ProductForm({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return BlocProvider(
      create: (context) => UploadProductBloc(),
      child: Builder(
        builder: (context) {
          final productFormBloc = context.read<UploadProductBloc>();

          return Scaffold(
            resizeToAvoidBottomInset: false,
            appBar: AppBar(title: const Text('Subir Producto')),
            body: FormBlocListener<UploadProductBloc, String, String>(
              onSubmitting: (context, state) {
                LoadingDialog.show(context);
              },
              onSubmissionFailed: (context, state) {
                LoadingDialog.hide(context);
              },
              onSuccess: (context, state) {
                LoadingDialog.hide(context);

                Navigator.of(context).pushReplacement(
                    MaterialPageRoute(builder: (_) => const SuccessScreen()));
              },
              onFailure: (context, state) {
                LoadingDialog.hide(context);

                ScaffoldMessenger.of(context).showSnackBar(
                    SnackBar(content: Text(state.failureResponse!)));
              },
              child: SingleChildScrollView(
                physics: const ClampingScrollPhysics(),
                child: AutofillGroup(
                  child: Column(
                    children: <Widget>[
                      TextFieldBlocBuilder(
                        textFieldBloc: productFormBloc.title,
                        keyboardType: TextInputType.name,
                        autofillHints: const [
                          AutofillHints.username,
                        ],
                        decoration: const InputDecoration(
                          labelText: 'Título',
                          prefixIcon: Icon(Icons.title),
                        ),
                      ),
                      TextFieldBlocBuilder(
                        textFieldBloc: productFormBloc.price,
                        keyboardType: TextInputType.number,
                        decoration: const InputDecoration(
                          labelText: 'Precio',
                          prefixIcon: Icon(Icons.price_check_rounded),
                        ),
                      ),
                      SizedBox(
                        width: 250,
                        child: CheckboxFieldBlocBuilder(
                          booleanFieldBloc: productFormBloc.showSuccessResponse,
                          body: Container(
                            alignment: Alignment.centerLeft,
                            child: const Text('Show success response'),
                          ),
                        ),
                      ),
                      if (productFormBloc.selectedImage != null)
                        Image.file(productFormBloc.selectedImage!),
                      ElevatedButton(
                        onPressed: () {
                          productFormBloc.selectImage(context);
                        },
                        child: const Text('SELECCIONAR IMAGEN'),
                      ),
                      ElevatedButton(
                        onPressed: productFormBloc.submit,
                        child: const Text('VENDER PRODUCTO'),
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
              'Success',
              style: TextStyle(fontSize: 54, color: Colors.black),
              textAlign: TextAlign.center,
            ),
            const SizedBox(height: 10),
            ElevatedButton.icon(
              onPressed: () => Navigator.of(context).pushReplacement(
                  MaterialPageRoute(builder: (_) => UploadProductPage())),
              icon: const Icon(Icons.replay),
              label: const Text('AGAIN'),
            ),
          ],
        ),
      ),
    );
  }
}
