part of 'favorite_bloc.dart';

abstract class FavoriteState {
  final List<ProductDetailsResponse> favoriteProducts;

  FavoriteState(this.favoriteProducts);
}

class FavoriteInitial extends FavoriteState {
  FavoriteInitial(List<ProductDetailsResponse> favoriteProducts)
      : super(favoriteProducts);
}

class FavoriteLoading extends FavoriteState {
  FavoriteLoading(List<ProductDetailsResponse> favoriteProducts)
      : super(favoriteProducts);
}

class FavoritesLoaded extends FavoriteState {
  FavoritesLoaded(List<ProductDetailsResponse> favoriteProducts)
      : super(favoriteProducts);
}

class FavoriteError extends FavoriteState {
  final String message;

  FavoriteError(this.message, List<ProductDetailsResponse> favoriteProducts)
      : super(favoriteProducts);
}
