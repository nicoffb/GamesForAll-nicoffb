// part of 'upload_products_bloc.dart';

// class UploadProductsInitial extends UploadProductsState {}

// enum UploadStatus {
//   initial,
//   success,
//   failure,
//   deleted,
//   deleteFailure,
//   editSuccess,
//   addProductSuccess
// }

// class UploadProductsState extends Equatable {
//   const UploadProductsState({
//     this.product = null,
//     this.status = UploadStatus.initial,
//   });

//   final UploadStatus status;
//   final ProductDetailsResponse? product;

//   UploadProductsState copyWith({
//     ProductDetailsResponse? product,
//     UploadStatus? status,
//   }) {
//     return UploadProductsState(
//       product: product ?? this.product,
//       status: status ?? this.status,
//     );
//   }

//   @override
//   String toString() {
//     return '''PostState { status: ${status}, restaurante: ${product} }''';
//   }

//   @override
//   List<Object> get props => [product!, status];
// }
