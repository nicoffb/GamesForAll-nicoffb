// import 'dart:async';
// import 'dart:io';
// import 'package:gamesforall_frontend/blocs/uploadProduct/upload_product_state.dart';
// import 'package:http/http.dart' as http;
// import 'package:path/path.dart' as path;
// import 'package:bloc/bloc.dart';
// import 'package:equatable/equatable.dart';
// import 'package:file_picker/file_picker.dart';
// import 'package:flutter/material.dart';
// import 'package:flutter_form_bloc/flutter_form_bloc.dart';
// import 'package:gamesforall_frontend/pages/upload_product_page.dart';

// import '../../models/product_request.dart';

// class UploadProductBloc extends FormBloc<String, String> {
//   final title = TextFieldBloc(
//     validators: [
//       FieldBlocValidators.required,
//     ],
//   );

//   final price = TextFieldBloc(
//     validators: [
//       FieldBlocValidators.required,
//     ],
//   );

//   final showSuccessResponse = BooleanFieldBloc();

//   UploadProductBloc() {
//     addFieldBlocs(
//       fieldBlocs: [
//         title,
//         price,
//         showSuccessResponse,
//       ],
//     );
//   }

//   @override
//   void onSubmitting() async {
//     debugPrint(title.value);
//     debugPrint(price.value);
//     debugPrint(showSuccessResponse.value.toString());

//     //await Future<void>.delayed(const Duration(seconds: 1));

//     if (showSuccessResponse.value) {
//       emitSuccess();
//     } else {
//       emitFailure(failureResponse: 'This is an awesome error!');
//     }
//   }
// }
// ////////////////////////////////////////////////////////////////////////////////////////////

// class ProductForm extends StatelessWidget {
//   ProductForm({Key? key}) : super(key: key);
//   bool imgSelected = false;
//   FilePickerResult? result;
//   TextEditingController _titleController = TextEditingController();
//   TextEditingController _priceController = TextEditingController();

//   @override
//   Widget build(BuildContext context) {
//     return BlocProvider(
//       create: (context) => UploadProductBloc(),
//       child: Builder(
//         builder: (context) {
//           final productFormBloc = context.read<UploadProductBloc>();

//           return Scaffold(
//             resizeToAvoidBottomInset: false,
//             appBar: AppBar(title: const Text('Login')),
//             body: FormBlocListener<UploadProductBloc, String, String>(
//               onSubmitting: (context, state) {
//                 LoadingDialog.show(context);
//               },
//               onSubmissionFailed: (context, state) {
//                 LoadingDialog.hide(context);
//               },
//               onSuccess: (context, state) {
//                 LoadingDialog.hide(context);

//                 Navigator.of(context).pushReplacement(
//                     MaterialPageRoute(builder: (_) => const SuccessScreen()));
//               },
//               onFailure: (context, state) {
//                 LoadingDialog.hide(context);

//                 ScaffoldMessenger.of(context).showSnackBar(
//                     SnackBar(content: Text(state.failureResponse!)));
//               },
//               child: SingleChildScrollView(
//                 physics: const ClampingScrollPhysics(),
//                 child: AutofillGroup(
//                   child: Column(
//                     children: <Widget>[
//                       TextFieldBlocBuilder(
//                         textFieldBloc: productFormBloc.title,
//                         keyboardType: TextInputType.name,
//                         autofillHints: const [
//                           AutofillHints.username,
//                         ],
//                         decoration: const InputDecoration(
//                           labelText: 'Título',
//                           prefixIcon: Icon(Icons.title),
//                         ),
//                       ),
//                       TextFieldBlocBuilder(
//                         textFieldBloc: productFormBloc.price,
//                         keyboardType: TextInputType.number,
//                         decoration: const InputDecoration(
//                           labelText: 'Precio',
//                           prefixIcon: Icon(Icons.price_check_rounded),
//                         ),
//                       ),
//                       SizedBox(
//                         width: 250,
//                         child: CheckboxFieldBlocBuilder(
//                           booleanFieldBloc: productFormBloc.showSuccessResponse,
//                           body: Container(
//                             alignment: Alignment.centerLeft,
//                             child: const Text('Show success response'),
//                           ),
//                         ),
//                       ),
//                       ElevatedButton(
//                         onPressed: productFormBloc.submit,
//                         child: const Text('VENDER PRODUCTO'),
//                       ),
//                       // ElevatedButton(
//                       //     onPressed: () async {
//                       //       result = await FilePicker.platform.pickFiles(
//                       //         withData: true,
//                       //         allowMultiple: false,
//                       //         allowedExtensions: ['jpg', 'png'],
//                       //       );
//                       //       setState(() {
//                       //         imgSelected = result != null;
//                       //       });
//                       //     },
//                       //     child: Text("Seleccione una imagen")),
//                       // SizedBox(
//                       //   height: 10,
//                       // ),
//                       // if (imgSelected)
//                       //   Stack(
//                       //       alignment: Alignment.bottomCenter,
//                       //       children: <Widget>[
//                       //         ClipRRect(
//                       //           borderRadius: BorderRadius.circular(8.0),
//                       //           child: Image.file(
//                       //             File(result!.files[0].path!),
//                       //             fit: BoxFit.cover,
//                       //             width: double.infinity,
//                       //           ),
//                       //         ),
//                       //         Positioned(
//                       //           right: 8,
//                       //           top: 8,
//                       //           child: IconButton(
//                       //             color: Colors.white,
//                       //             onPressed: () {
//                       //               setState(() {
//                       //                 result = null;
//                       //                 imgSelected = false;
//                       //               });
//                       //             },
//                       //             icon: Icon(Icons.close),
//                       //           ),
//                       //         ),
//                       //       ]),
//                       if (imgSelected)
//                         SizedBox(
//                           height: 10,
//                         ),
//                       if (imgSelected)
//                         ElevatedButton(
//                           onPressed: () {
//                             String titleInput = productFormBloc.title.value;
//                             double priceInput =
//                                 double.parse(productFormBloc.price.value);
//                             context.read<UploadProductBloc>().add(AddProduct(
//                                 ProductRequest(
//                                     title: titleInput, price: priceInput),
//                                 result!.files[0]));
//                           },
//                           child: const Text('Submit'),
//                         ),
//                     ],
//                   ),
//                 ),
//               ),
//             ),
//           );
//         },
//       ),
//     );
//   }
// }

// class LoadingDialog extends StatelessWidget {
//   static void show(BuildContext context, {Key? key}) => showDialog<void>(
//         context: context,
//         useRootNavigator: false,
//         barrierDismissible: false,
//         builder: (_) => LoadingDialog(key: key),
//       ).then((_) => FocusScope.of(context).requestFocus(FocusNode()));

//   static void hide(BuildContext context) => Navigator.pop(context);

//   const LoadingDialog({Key? key}) : super(key: key);

//   @override
//   Widget build(BuildContext context) {
//     return WillPopScope(
//       onWillPop: () async => false,
//       child: Center(
//         child: Card(
//           child: Container(
//             width: 80,
//             height: 80,
//             padding: const EdgeInsets.all(12.0),
//             child: const CircularProgressIndicator(),
//           ),
//         ),
//       ),
//     );
//   }
// }

// class SuccessScreen extends StatelessWidget {
//   const SuccessScreen({Key? key}) : super(key: key);

//   @override
//   Widget build(BuildContext context) {
//     return Scaffold(
//       body: Center(
//         child: Column(
//           mainAxisAlignment: MainAxisAlignment.center,
//           children: <Widget>[
//             const Icon(Icons.tag_faces, size: 100),
//             const SizedBox(height: 10),
//             const Text(
//               'Success',
//               style: TextStyle(fontSize: 54, color: Colors.black),
//               textAlign: TextAlign.center,
//             ),
//             const SizedBox(height: 10),
//             ElevatedButton.icon(
//               onPressed: () => Navigator.of(context).pushReplacement(
//                   MaterialPageRoute(builder: (_) => UploadProductPage())),
//               icon: const Icon(Icons.replay),
//               label: const Text('AGAIN'),
//             ),
//           ],
//         ),
//       ),
//     );
//   }
// }
