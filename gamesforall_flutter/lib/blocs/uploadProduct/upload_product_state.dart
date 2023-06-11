import 'package:equatable/equatable.dart';
import 'package:file_picker/file_picker.dart';
import 'package:gamesforall_frontend/models/product_request.dart';

abstract class UploadProductState {}

class InitialState extends UploadProductState {}

class ImageSelectedState extends UploadProductState {}

class AddProduct extends UploadProductState {
  AddProduct(this.productRequest, this.file);
  ProductRequest productRequest;
  PlatformFile file;
}
