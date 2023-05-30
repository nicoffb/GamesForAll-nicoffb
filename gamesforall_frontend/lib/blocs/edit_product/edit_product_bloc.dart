import 'dart:io';

import 'package:bloc/bloc.dart';
import 'package:equatable/equatable.dart';
import 'package:file_picker/file_picker.dart';
import 'package:flutter/material.dart';
import 'package:flutter_form_bloc/flutter_form_bloc.dart';
import 'package:gamesforall_frontend/blocs/uploadProduct/upload_product_event.dart';
import 'package:gamesforall_frontend/blocs/uploadProduct/upload_product_state.dart';
import 'package:gamesforall_frontend/models/product_detail_response.dart';
import 'package:gamesforall_frontend/pages/product_details_page.dart';
import 'package:gamesforall_frontend/pages/upload_product_page.dart';
import 'package:gamesforall_frontend/services/product_service.dart';

import '../../models/product_request.dart';

class EditProductBloc extends FormBloc<String, String> {
  late ProductService productService;

  final int productId;

  TextFieldBloc title = TextFieldBloc(
    initialValue: '',
    validators: [FieldBlocValidators.required],
  );

  TextFieldBloc price = TextFieldBloc(
    initialValue: '',
    validators: [FieldBlocValidators.required],
  );


  EditProductBloc({required this.productId}) : super() {
    productService = ProductService();
    addFieldBlocs(
      fieldBlocs: [
        title,
        price,
      ],
    );

    productService.getProductDetails(productId).then((product) {
      title.updateInitialValue(product.title!);
      price.updateInitialValue(product.price.toString());
    });
  }
  
  @override
void onSubmitting() async {
    try {
      debugPrint(title.value);
      debugPrint(price.value);
      ProductDetailsResponse result = await productService.edit(
          productId,
          ProductRequest(
            title: title.value,
            price: double.parse(price.value),
          )
        );
      
      if (result != null) {
        emitSuccess();
      } else {
        emitFailure(failureResponse: 'La edición del producto falló!');
      }
    } catch (e) {
      print('Error: $e');
      emitFailure(failureResponse: 'Error: $e');
    }
  }
}

////////////////////////////////////////////////////////////////////////////////////////////

class EditProductForm extends StatelessWidget {

  final int productId;

  EditProductForm({Key? key, required this.productId}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return BlocProvider(
      create: (context) => EditProductBloc(productId: productId),
      child: Builder(
        builder: (context) {
          final productFormBloc = context.read<EditProductBloc>();

          return Scaffold(
            resizeToAvoidBottomInset: false,
            appBar: AppBar(title: const Text('EDITAR Producto')),
            body: FormBlocListener<EditProductBloc, String, String>(
              onSubmitting: (context, state) {
                LoadingDialog.show(context);
              },
              onSubmissionFailed: (context, state) {
                LoadingDialog.hide(context);
              },
              onSuccess: (context, state) {
                LoadingDialog.hide(context);

                Navigator.of(context).pushReplacement(
                    MaterialPageRoute(builder: (_) => SuccessScreen(productId: productId,)));
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

class SuccessScreen extends StatefulWidget {
  final int productId; // added a productId parameter

  const SuccessScreen({Key? key, required this.productId}) : super(key: key);

  @override
  _SuccessScreenState createState() => _SuccessScreenState();
}

class _SuccessScreenState extends State<SuccessScreen> {

  @override
  void initState() {
    super.initState();
    // Redirect to the detail page after a delay
    Future.delayed(const Duration(seconds: 1), () {
      Navigator.of(context).pushReplacement(
        MaterialPageRoute(builder: (_) => ProductDetailsPage(productId: widget.productId)),
      );
    });
  }

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
              'Producto editado con éxito',
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

