import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';

import '../blocs/edit_product/edit_product_bloc.dart';
import '../blocs/uploadProduct/upload_product_bloc.dart';
import '../models/product_detail_response.dart';



class EditProductPage extends StatelessWidget {
  final ProductDetailsResponse product;

  EditProductPage({Key? key, required this.product}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: EditProductForm(product: product),
    );
  }
}
