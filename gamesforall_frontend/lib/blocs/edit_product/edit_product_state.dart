import 'package:equatable/equatable.dart';
import 'package:file_picker/file_picker.dart';
import 'package:gamesforall_frontend/models/product_request.dart';

abstract class EditProductState {}

class InitialState extends EditProductState {}


class EditProduct extends EditProductState {
  EditProduct(this.productRequest);
  ProductRequest productRequest;
}
