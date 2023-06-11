part of 'product_bloc.dart';

enum ProductType {
  search,
  favorites,
  myproducts,
}

abstract class ProductEvent extends Equatable {
  const ProductEvent();

  @override
  List<Object> get props => [];
}

class GetProductsEvent extends ProductEvent {
  final ProductType productType;

  const GetProductsEvent({this.productType = ProductType.search});

  @override
  List<Object> get props => [productType];
}
