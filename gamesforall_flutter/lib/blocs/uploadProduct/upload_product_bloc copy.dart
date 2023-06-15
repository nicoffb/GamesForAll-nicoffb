// import 'dart:io';

// import 'package:bloc/bloc.dart';
// import 'package:equatable/equatable.dart';
// import 'package:file_picker/file_picker.dart';
// import 'package:flutter/material.dart';
// import 'package:flutter_form_bloc/flutter_form_bloc.dart';
// import 'package:gamesforall_frontend/blocs/uploadProduct/upload_product_event.dart';
// import 'package:gamesforall_frontend/blocs/uploadProduct/upload_product_state.dart';
// import 'package:gamesforall_frontend/pages/main_page.dart';
// import 'package:gamesforall_frontend/pages/product_details_page.dart';
// import 'package:gamesforall_frontend/pages/upload_product_page.dart';
// import 'package:gamesforall_frontend/services/localstorage_service.dart';
// import 'package:gamesforall_frontend/services/product_service.dart';

// import '../../models/product_detail_response.dart';
// import '../../models/product_request.dart';
// import '../../models/product_response.dart';
// import '../../services/category_service.dart';
// import '../../services/platform_service.dart';

// class UploadProductBloc extends FormBloc<String, String> {
//   ProductService productService = ProductService();
//   PlatformService platformService = PlatformService();
//   CategoryService categoryService = CategoryService();
//   int? newProductId;

//   final title = TextFieldBloc(
//     validators: [FieldBlocValidators.required],
//   );

//   final price = TextFieldBloc(
//     validators: [
//       FieldBlocValidators.required,
//     ],
//   );

//   final description = TextFieldBloc(
//     validators: [
//       FieldBlocValidators.required,
//     ],
//   );

//   final producState = SelectFieldBloc(
//     items: ['Sin Abrir', 'Como Nuevo', 'Usado'],
//     validators: [
//       FieldBlocValidators.required,
//     ],
//   );

//   final isShippingAvailable = BooleanFieldBloc(
//     validators: [],
//   );

//   final platform = SelectFieldBloc<Platform, dynamic>();
//   final categoriesSelect = MultiSelectFieldBloc<Categories, dynamic>();

//   File? selectedImage;

//   UploadProductBloc() : super() {
//     productService = ProductService();
//     loadFields();
//     addFieldBlocs(
//       fieldBlocs: [
//         title,
//         price,
//         description,
//         producState,
//         isShippingAvailable,
//         platform,
//         categoriesSelect
//       ],
//     );
//   }

//   void loadFields() async {
//     List<Platform> allPlatforms = await platformService.getAllPlatforms();
//     platform.updateItems(allPlatforms);

//     List<Categories> allCategories = await categoryService.getAllCategories();
//     categoriesSelect.updateItems(allCategories);
//   }

//   @override
//   void onSubmitting() async {
//     try {
//       debugPrint(title.value);
//       debugPrint(price.value);
//       debugPrint(platform.value!.platformName);
//       debugPrint(selectedImage.toString());
//       if (selectedImage != null) {
//         ProductDetailsResponse result = await productService.add(
//           ProductRequest(
//             title: title.value,
//             price: double.parse(price.value),
//             description: description.value,
//             state: producState.value,
//             is_shipping_available: isShippingAvailable.value,
//             platform: platform.value,
//             categories:
//                 categoriesSelect.value.map((e) => e as Categories).toSet(),
//           ),
//           PlatformFile(
//             name: selectedImage!.path.split('\\').last,
//             bytes: await selectedImage!.readAsBytes(),
//             size: selectedImage!.lengthSync(),
//           ),
//         );

//         if (result != null) {
//           newProductId = result.id!;
//           emitSuccess();
//         } else {
//           emitFailure(
//               failureResponse: 'Error al añadir el producto: ${result}');
//         }
//       }
//     } catch (e) {
//       print('Error: $e');
//       emitFailure(failureResponse: 'Error: $e');
//     }
//   }
// }

// ////////////////////////////////////////////////////////////////////////////////////////////

// class ProductForm extends StatefulWidget {
//   @override
//   _ProductFormState createState() => _ProductFormState();
// }

// class _ProductFormState extends State<ProductForm> {
//   File? selectedImage;

//   void selectImage() async {
//     final file = await FilePicker.platform.pickFiles(
//         type: FileType.custom, allowedExtensions: ['jpg', 'jpeg', 'png']);
//     if (file != null) {
//       File? imageFile = File(file.files.single.path!);
//       print('File path: ${imageFile.path}');
//       setState(() {
//         selectedImage = imageFile;
//       });
//     }
//   }

//   @override
//   Widget build(BuildContext context) {
//     return BlocProvider(
//       create: (context) => UploadProductBloc(),
//       child: Builder(
//         builder: (context) {
//           final productFormBloc = context.read<UploadProductBloc>();

//           return Scaffold(
//             resizeToAvoidBottomInset: false,
//             appBar: AppBar(title: const Text('Subir Producto')),
//             body: FormBlocListener<UploadProductBloc, String, String>(
//               onSubmitting: (context, state) {
//                 LoadingDialog.show(context);
//               },
//               onSubmissionFailed: (context, state) {
//                 LoadingDialog.hide(context);
//               },
//               onSuccess: (context, state) {
//                 LoadingDialog.hide(context);

//                 Navigator.of(context).pushReplacement(MaterialPageRoute(
//                     builder: (_) => SuccessScreen(
//                         productId: productFormBloc.newProductId!)));
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
//                       TextFieldBlocBuilder(
//                         textFieldBloc: productFormBloc.description,
//                         keyboardType: TextInputType.multiline,
//                         maxLines: null,
//                         decoration: const InputDecoration(
//                           labelText: 'Descripción',
//                           prefixIcon: Icon(Icons.description),
//                         ),
//                       ),
//                       DropdownFieldBlocBuilder<String>(
//                         //ESTADOOOOS
//                         selectFieldBloc: productFormBloc.producState,
//                         decoration: const InputDecoration(
//                           labelText: 'Estado',
//                           prefixIcon: Icon(Icons.check_circle),
//                         ),
//                         itemBuilder: (context, value) => FieldItem(
//                           child: Text(value),
//                         ),
//                       ),
//                       SwitchFieldBlocBuilder(
//                         booleanFieldBloc: productFormBloc.isShippingAvailable,
//                         body: Container(
//                           alignment: Alignment.centerLeft,
//                           child: const Text('Disponibilidad de envío'),
//                         ),
//                       ),
//                       //PLATAFORMAAA
//                       RadioButtonGroupFieldBlocBuilder<Platform>(
//                         selectFieldBloc: productFormBloc.platform,
//                         decoration: const InputDecoration(
//                           labelText: 'Plataforma',
//                           prefixIcon: SizedBox(),
//                         ),
//                         itemBuilder: (context, item) => FieldItem(
//                           child: Text(item.platformName!),
//                         ),
//                       ),
//                       CheckboxGroupFieldBlocBuilder<Categories>(
//                         multiSelectFieldBloc: productFormBloc.categoriesSelect,
//                         itemBuilder: (context, item) => FieldItem(
//                           child: Text(item.genre!),
//                         ),
//                         decoration: const InputDecoration(
//                           labelText: 'Categorias',
//                           prefixIcon: SizedBox(),
//                         ),
//                       ),
//                       if (selectedImage != null) Image.file(selectedImage!),
//                       ElevatedButton(
//                         onPressed: selectImage,
//                         child: const Text('SELECCIONAR IMAGEN'),
//                       ),
//                       ElevatedButton(
//                         onPressed: productFormBloc.selectedImage != null
//                             ? productFormBloc.submit
//                             : () {
//                                 ScaffoldMessenger.of(context)
//                                     .showSnackBar(SnackBar(
//                                   content:
//                                       Text('Por favor, selecciona una imagen.'),
//                                 ));
//                               },
//                         child: const Text('VENDER PRODUCTO'),
//                       ),
//                       // ElevatedButton(
//                       //   onPressed: productFormBloc.submit,
//                       //   child: const Text('VENDER PRODUCTO'),
//                       // ),
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
//   final int productId;

//   SuccessScreen({Key? key, required this.productId}) : super(key: key);

//   @override
//   Widget build(BuildContext context) {
//     LocalStorageService localStorageService = LocalStorageService();

//     Future.delayed(const Duration(seconds: 1), () {
//       Navigator.of(context).pushReplacement(MaterialPageRoute(
//         builder: (_) => ProductDetailsPage(productId: productId),
//       ));
//     });

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
//           ],
//         ),
//       ),
//     );
//   }
// }
