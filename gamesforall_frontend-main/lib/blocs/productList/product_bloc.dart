import 'dart:async';

import 'package:bloc/bloc.dart';
import 'package:bloc_concurrency/bloc_concurrency.dart';
import 'package:equatable/equatable.dart';

import 'package:get_it/get_it.dart';
import 'package:http/http.dart' as http;
import 'package:stream_transform/stream_transform.dart';

import '../../models/product_page_response.dart';
import '../../models/product_response.dart';
import '../../repositories/product_repository.dart';

part 'product_event.dart';
part 'product_state.dart';

const throttleDuration = Duration(milliseconds: 100);

EventTransformer<E> throttleDroppable<E>(Duration duration) {
  return (events, mapper) {
    return droppable<E>().call(events.throttle(duration), mapper);
  };
}

class ProductBloc extends Bloc<ProductEvent, ProductState> {
  final ProductRepository productRepository;
  int numPage = 0;
  final ProductType productType;

  ProductBloc({required this.productRepository, required this.productType})
      : super(const ProductState()) {
    on<GetProductsEvent>(
      _onGetProductsEvent,
      transformer: throttleDroppable(throttleDuration),
    );
  }

  Future<void> _onGetProductsEvent(
      GetProductsEvent event, Emitter<ProductState> emit) async {
    if (state.hasReachedMax) {
      return;
    }
    try {
      if (state.status == ProductStatus.initial) {
        final productPage =
            await productRepository.getProductList(0, productType: productType);
        return emit(state.copyWith(
          status: ProductStatus.success,
          products: productPage.content,
          hasReachedMax: productPage.content!.length < 10 ? true : false,
        ));
      }

      numPage++;
      final productPage = await productRepository.getProductList(numPage,
          productType: productType);
      productPage.content!.length < 10 || productPage.content!.isEmpty
          ? emit(state.copyWith(hasReachedMax: true))
          : emit(
              state.copyWith(
                status: ProductStatus.success,
                products: List.of(state.products)..addAll(productPage.content!),
                hasReachedMax: false,
              ),
            );
    } catch (e) {
      print(e);
      emit(state.copyWith(status: ProductStatus.failure));
    }
  }
}
