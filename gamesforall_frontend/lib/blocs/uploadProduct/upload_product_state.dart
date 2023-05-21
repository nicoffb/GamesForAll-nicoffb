import 'package:equatable/equatable.dart';
import 'package:file_picker/file_picker.dart';
import 'package:gamesforall_frontend/models/product_request.dart';

abstract class UploadProductState extends Equatable {
  const UploadProductState();

  @override
  List<Object> get props => [];
}

class AddProduct extends UploadProductState {
  AddProduct(this.productRequest, this.file);
  ProductRequest productRequest;
  PlatformFile file;
}
