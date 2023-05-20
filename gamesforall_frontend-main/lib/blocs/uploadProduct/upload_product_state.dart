part of 'upload_product_bloc.dart';

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
