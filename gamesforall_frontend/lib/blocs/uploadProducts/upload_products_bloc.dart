import 'dart:async';

import 'package:bloc/bloc.dart';
import 'package:equatable/equatable.dart';
import 'package:file_picker/file_picker.dart';
import 'package:gamesforall_frontend/blocs/uploadProduct/upload_product_state.dart';
import 'package:gamesforall_frontend/config/locator.dart';
import 'package:gamesforall_frontend/models/product_detail_response.dart';
import 'package:gamesforall_frontend/services/product_service.dart';
import 'package:meta/meta.dart';

import '../../models/product_request.dart';

part 'upload_products_event.dart';
part 'upload_products_state.dart';

class UploadProductsBloc
    extends Bloc<UploadProductsEvent, UploadProductsState> {
  late final ProductService productService;

  UploadProductsBloc() : super(UploadProductsInitial()) {
    productService = getIt<ProductService>();

    on<AddProduct>(AddProductBloc);
  }

  FutureOr<void> AddProductBloc(
      AddProduct event, Emitter<UploadProductsState> emit) async {
    try {
      final product =
          await productService.add(event.productRequest, event.file);
      return emit(
        state.copyWith(status: UploadStatus.addProductSuccess),
      );
    } catch (_) {
      emit(state.copyWith(status: UploadStatus.failure));
    }
  }
}
