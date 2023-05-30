import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';

import '../blocs/edit_product/edit_product_bloc.dart';
import '../blocs/uploadProduct/upload_product_bloc.dart';



class EditProductPage extends StatelessWidget {
  final int productId;

  EditProductPage({Key? key, required this.productId}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: EditProductForm(productId: productId),
    );
  }
}

