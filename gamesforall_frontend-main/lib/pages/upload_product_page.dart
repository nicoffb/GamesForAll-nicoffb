import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:gamesforall_frontend/blocs/uploadProduct/upload_product_bloc.dart';

class UploadProductPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      home: ProductForm(),
    );
  }
}
