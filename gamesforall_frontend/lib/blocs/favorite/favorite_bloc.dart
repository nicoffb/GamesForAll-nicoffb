import 'package:bloc/bloc.dart';
import 'package:equatable/equatable.dart';
import 'package:gamesforall_frontend/services/product_service.dart';

import '../../models/product_page_response.dart';

part 'favorite_event.dart';
part 'favorite_state.dart';

class FavoriteBloc extends Bloc<FavoriteEvent, FavoriteState> {
  final ProductService productService;

  FavoriteBloc({required this.productService}) : super(FavoriteLoading()) {
    on<FetchFavoritesEvent>((event, emit) async {
      try {
        final favorites = await productService.getFavorites();
        emit(FavoritesLoaded(favorites));
      } catch (e) {
        emit(FavoriteError(e.toString()));
      }
    });

    on<AddToFavoritesEvent>((event, emit) async {
      try {
        await productService.addToFavorites(event.productId);
        final updatedFavorites = await productService.getFavorites();
        emit(FavoritesLoaded(updatedFavorites));
      } catch (e) {
        emit(FavoriteError(e.toString()));
      }
    });

    on<RemoveFromFavoritesEvent>((event, emit) async {
      try {
        await productService.removeFromFavorites(event.productId);
        final updatedFavorites = await productService.getFavorites();
        emit(FavoritesLoaded(updatedFavorites));
      } catch (e) {
        emit(FavoriteError(e.toString()));
      }
    });
  }
}
