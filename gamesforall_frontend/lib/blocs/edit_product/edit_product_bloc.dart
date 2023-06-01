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
import 'package:gamesforall_frontend/services/platform_service.dart';
import 'package:gamesforall_frontend/services/product_service.dart';

import '../../models/product_request.dart';

class EditProductBloc extends FormBloc<String, String> {
  final ProductService productService = ProductService();
  final PlatformService platformService = PlatformService();
  final ProductDetailsResponse product;
  List<Platform> platforms = new List.empty();

  TextFieldBloc title;
  TextFieldBloc price;
  TextFieldBloc description;
  SelectFieldBloc<String, dynamic> producState;
  BooleanFieldBloc isShippingAvailable;
  SelectFieldBloc<Platform, dynamic> platform;

  EditProductBloc({required this.product})
      : title = TextFieldBloc(
          initialValue: product.title!,
          validators: [FieldBlocValidators.required],
        ),
        price = TextFieldBloc(
          initialValue: product.price.toString(),
          validators: [FieldBlocValidators.required],
        ),
        description = TextFieldBloc(
          initialValue: product.description!,
        ),
        producState = SelectFieldBloc(
          initialValue: product.state,
          validators: [FieldBlocValidators.required],
          items: ['Sin Abrir', 'Como Nuevo', 'Usado'],
        ),
        isShippingAvailable = BooleanFieldBloc(
          initialValue: product.shippingAvailable ?? false,
        ),
        platform = SelectFieldBloc(
          initialValue: product.platform,
          validators: [FieldBlocValidators.required],
          items: new List.empty(),
        ),
        super() {
    loadPlatforms();
  }

  void loadPlatforms() async {
    this.platforms = await platformService.getAllPlatforms();
    platform = SelectFieldBloc(
      initialValue: product.platform!,
      validators: [FieldBlocValidators.required],
      items: platforms,
    );
    addFieldBlocs(
      fieldBlocs: [
        title,
        price,
        description,
        producState,
        isShippingAvailable,
        platform,
      ],
    );
  }

  @override
  void onSubmitting() async {
    try {
      debugPrint(title.value);
      debugPrint(price.value);
      debugPrint(description.value);
      debugPrint(producState.value);
      debugPrint(isShippingAvailable.value.toString());

      ProductDetailsResponse result = await productService.edit(
          product.id!,
          ProductRequest(
            title: title.value,
            price: double.parse(price.value),
            description: description.value,
            state: producState.value,
            shippingAvailable: isShippingAvailable.value,
          ));

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
  final ProductDetailsResponse product;

  EditProductForm({Key? key, required this.product}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return BlocProvider(
      create: (context) => EditProductBloc(product: product),
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

                Navigator.of(context).pushReplacement(MaterialPageRoute(
                    builder: (_) => SuccessScreen(
                          productId: product.id!,
                        )));
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
                      TextFieldBlocBuilder(
                        textFieldBloc: productFormBloc.description,
                        keyboardType: TextInputType.multiline,
                        maxLines: null,
                        decoration: const InputDecoration(
                          labelText: 'Descripción',
                          prefixIcon: Icon(Icons.description),
                        ),
                      ),
                      DropdownFieldBlocBuilder<String>(
                        //ESTADOOOOS
                        selectFieldBloc: productFormBloc.producState,
                        decoration: const InputDecoration(
                          labelText: 'Estado',
                          prefixIcon: Icon(Icons.check_circle),
                        ),
                        itemBuilder: (context, value) => FieldItem(
                          child: Text(value),
                        ),
                      ),
                      CheckboxFieldBlocBuilder(
                        booleanFieldBloc: productFormBloc.isShippingAvailable,
                        body: const Text('Disponible para envío'),
                      ),
                      //PLATAFORMAAA
                      RadioButtonGroupFieldBlocBuilder<Platform>(
                        selectFieldBloc: productFormBloc.platform,
                        decoration: const InputDecoration(
                          labelText: 'Plataforma',
                          prefixIcon: SizedBox(),
                        ),
                        itemBuilder: (context, item) => FieldItem(
                          child: Text(item.platformName!),
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
        MaterialPageRoute(
            builder: (_) => ProductDetailsPage(productId: widget.productId)),
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
