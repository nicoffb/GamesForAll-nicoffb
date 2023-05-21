// import 'dart:async';
// import 'dart:io';

// import 'package:gamesforall_frontend/blocs/productList/product_bloc.dart';
// import 'package:gamesforall_frontend/blocs/uploadProducts/upload_products_bloc.dart';
// import 'package:http/http.dart' as http;
// import 'package:path/path.dart' as path;
// import 'package:bloc/bloc.dart';
// import 'package:equatable/equatable.dart';
// import 'package:file_picker/file_picker.dart';
// import 'package:flutter/material.dart';
// import 'package:flutter_form_bloc/flutter_form_bloc.dart';
// import 'package:gamesforall_frontend/pages/upload_product_page.dart';

// import '../../models/product_request.dart';

// class UploadProductPage extends StatelessWidget {
//   @override
//   Widget build(BuildContext context) {
//     return Scaffold(
//       body: ProductForm(),
//     );
//   }
// }

// ////////////////////////////////////////////////////////////////////////////////////////////

// class ProductForm extends StatefulWidget {
//   @override
//   State<ProductForm> createState() => ProductFormState();
// }

// class ProductFormState extends State<ProductForm> {
//   @override
//   Widget build(BuildContext context) {
//     return BlocConsumer<UploadProductsBloc, UploadProductsState>(
//       listenWhen: (previous, current) {
//         return current.status == UploadStatus.deleteFailure ||
//             current.status == UploadStatus.editSuccess ||
//             current.status == UploadStatus.addProductSuccess;
//       },
//       listener: (context, state) {
//         if (state.status == UploadStatus.deleteFailure) _showDeleteError();
//         if (state.status == UploadStatus.editSuccess)
//           ScaffoldMessenger.of(context).showSnackBar(
//             const SnackBar(content: Text('Modificado con éxito')),
//           );
//         if (state.status == UploadStatus.addProductSuccess)
//           ScaffoldMessenger.of(context).showSnackBar(
//             const SnackBar(content: Text('Plato creado con éxito')),
//           );
//       },
//       buildWhen: (previous, current) {
//         return current.status != UploadStatus.deleteFailure;
//       },
//       builder: (manageContext, state) {
//         switch (state.status) {
//           case UploadStatus.failure:
//             return const Center(child: Text('Fallo al cargar el restaurante'));
//           case UploadStatus.success:
//             return _buildScreen(state, manageContext);
//           case UploadStatus.initial:
//             return const Center(child: CircularProgressIndicator());
//           // case UploadStatus.deleted:
//           //   return const DeletedScreen();
//           // case UploadStatus.editSuccess:
//           //   return _buildScreen(state, manageContext);
//           // case UploadStatus.deleteFailure:
//           //   return Text("data");
//           case UploadStatus.addProductSuccess:
//             return _buildScreen(state, manageContext);
//         }
//       },
//     );
//   }

//   void _showDeleteError() {
//     ScaffoldMessenger.of(context).showSnackBar(SnackBar(
//         content: Text(
//             "No se ha podido borrar el restaurante, por favor trate de eliminar todos sus platos antes.")));
//   }
// }

// class AddProductForm extends StatelessWidget {
//   BuildContext formContext;

//   AddProductForm(this.formContext);

//   @override
//   Widget build(BuildContext context) {
//     return BlocProvider.value(
//       value: BlocProvider.of<UploadProductsBloc>(formContext),
//       child: AddProductFormUI(),
//     );
//   }
// }

// class AddProductFormUI extends StatefulWidget {
//   AddProductFormUI();

//   @override
//   State<AddProductFormUI> createState() => AddProductFormUIState();
// }

// class AddProductFormUIState extends StatelessWidget {
//   bool imgSelected = false;
//   FilePickerResult? result;
//   TextEditingController _titleController = TextEditingController();
//   TextEditingController _priceController = TextEditingController();

//   @override
//   Widget build(BuildContext context) {
//     return BlocConsumer<UploadProductsBloc, UploadProductsState>(
//       listenWhen: (previous, current) {
//         return current.status == UploadStatus.addProductSuccess ||
//             current.status == UploadStatus.failure;
//       },
//       listener: (context, state) {
//         if (state.status == UploadStatus.addProductSuccess) {
//           Navigator.of(context).pop();
//         } else if (state.status == UploadStatus.failure) {
//           ScaffoldMessenger.of(context)
//               .showSnackBar(SnackBar(content: Text("Fallo al crear el plato")));
//         }
//       },
//       buildWhen: (previous, current) {
//         return current.status != UploadStatus.failure;
//       },
//       builder: (context, state) {
//         return Scaffold(
//             appBar: AppBar(
//               leading: BackButton(
//                 onPressed: () {
//                   Navigator.of(context).pop();
//                 },
//               ),
//               title: Text("Nuevo plato"),
//               backgroundColor: Theme.of(context).colorScheme.onSecondary,
//               foregroundColor: Theme.of(context).colorScheme.primary,
//             ),
//             body: SingleChildScrollView(
//               child: Form(
//                   child: Padding(
//                 padding: EdgeInsets.all(25),
//                 child: Column(
//                   children: <Widget>[
//                     Row(
//                       children: [
//                         Expanded(
//                           flex: 4,
//                           child: Text("Nombre"),
//                         ),
//                         Expanded(
//                           flex: 10,
//                           child: TextFormField(
//                             controller: _titleController,
//                           ),
//                         )
//                       ],
//                     ),
//                     SizedBox(
//                       height: 10,
//                     ),
//                     Row(
//                       children: [
//                         Expanded(
//                           flex: 4,
//                           child: Text("Descripción"),
//                         ),
//                         Expanded(
//                           flex: 10,
//                           child: TextFormField(
//                             minLines: 2,
//                             maxLines: 5,
//                             // controller: _descripcionController,
//                           ),
//                         )
//                       ],
//                     ),
//                     SizedBox(
//                       height: 10,
//                     ),
//                     Row(
//                       children: [
//                         Expanded(
//                           flex: 4,
//                           child: Text("Precio"),
//                         ),
//                         Expanded(
//                           flex: 10,
//                           child: TextFormField(
//                             keyboardType:
//                                 TextInputType.numberWithOptions(decimal: true),
//                             controller: _priceController,
//                           ),
//                         )
//                       ],
//                     ),
//                     ElevatedButton(
//                         onPressed: () async {
//                           result = await FilePicker.platform.pickFiles(
//                             withData: true,
//                             allowMultiple: false,
//                             allowedExtensions: ['jpg', 'png'],
//                           );
//                           setState(() {
//                             imgSelected = result != null;
//                           });
//                         },
//                         child: Text("Seleccione una imagen")),
//                     SizedBox(
//                       height: 10,
//                     ),
//                     if (imgSelected)
//                       Stack(
//                           alignment: Alignment.bottomCenter,
//                           children: <Widget>[
//                             ClipRRect(
//                               borderRadius: BorderRadius.circular(8.0),
//                               child: Image.file(
//                                 File(result!.files[0].path!),
//                                 fit: BoxFit.cover,
//                                 width: double.infinity,
//                               ),
//                             ),
//                             Positioned(
//                               right: 8,
//                               top: 8,
//                               child: IconButton(
//                                 color: Colors.white,
//                                 onPressed: () {
//                                   setState(() {
//                                     result = null;
//                                     imgSelected = false;
//                                   });
//                                 },
//                                 icon: Icon(Icons.close),
//                               ),
//                             ),
//                           ]),
//                     if (imgSelected)
//                       SizedBox(
//                         height: 10,
//                       ),
//                     if (imgSelected)
//                       ElevatedButton(
//                         onPressed: () {
//                           context.read<UploadProductsBloc>().add(AddProduct(
//                               ProductRequest(
//                                 title: _titleController.text,
//                                 price: double.parse(_priceController.text),
//                               ),
//                               result!.files[0]));
//                         },
//                         child: const Text('Submit'),
//                       ),
//                   ],
//                 ),
//               )),
//             ));
//         // } else {
//         //   return Scaffold(
//         //     body: Center(
//         //       child: CircularProgressIndicator(),
//         //     ),
//         //   );
//         // }
//       },
//     );
//   }
// }
