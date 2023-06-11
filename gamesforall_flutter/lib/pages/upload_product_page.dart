import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';

import '../blocs/uploadProduct/upload_product_bloc.dart';

class UploadProductPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: ProductForm(),
    );
  }
}
