import 'package:bloc/bloc.dart';
import 'package:equatable/equatable.dart';
import 'package:gamesforall_frontend/pages/product_details_page.dart';
import 'package:gamesforall_frontend/services/product_service.dart';

import '../../models/product_detail_response.dart';
import '../../models/product_page_response.dart';

part 'favorite_event.dart';
part 'favorite_state.dart';

class FavoriteBloc extends Bloc<FavoriteEvent, FavoriteState> {
  final ProductService productService;

  FavoriteBloc({required this.productService})
      : super(FavoriteInitial(<ProductDetailsResponse>[])) {
    on<FetchFavoritesEvent>(_onFetchFavorites);
    on<AddToFavoritesEvent>(_onAddToFavorites);
    on<RemoveFromFavoritesEvent>(_onRemoveFromFavorites);

    _fetchFavorites();
  }

  Future<void> _fetchFavorites() async {
    emit(FavoriteLoading(state.favoriteProducts));
    try {
      final favorites = await productService.getUserFavorites();
      emit(FavoritesLoaded(favorites));
    } catch (e) {
      emit(FavoriteError(e.toString(), state.favoriteProducts));
    }
  }

  void _onFetchFavorites(
      FetchFavoritesEvent event, Emitter<FavoriteState> emit) async {
    _fetchFavorites();
  }

  Future<void> _onAddToFavorites(
      AddToFavoritesEvent event, Emitter<FavoriteState> emit) async {
    emit(FavoriteLoading(state.favoriteProducts));
    try {
      await productService.addToFavorites(event.productId);
      final updatedFavorites = await productService.getUserFavorites();
      emit(FavoritesLoaded(updatedFavorites));
    } catch (e) {
      emit(FavoriteError(e.toString(), state.favoriteProducts));
    }
  }

  Future<void> _onRemoveFromFavorites(
      RemoveFromFavoritesEvent event, Emitter<FavoriteState> emit) async {
    emit(FavoriteLoading(state.favoriteProducts));
    try {
      await productService.removeFromFavorites(event.productId);
      final updatedFavorites = await productService.getUserFavorites();
      emit(FavoritesLoaded(updatedFavorites));
    } catch (e) {
      emit(FavoriteError(e.toString(), state.favoriteProducts));
    }
  }
}
