part of 'favorite_bloc.dart';

abstract class FavoriteState {}

class FavoriteLoading extends FavoriteState {}

class FavoritesLoaded extends FavoriteState {
  final List<Product> favoriteProducts;

  FavoritesLoaded(this.favoriteProducts);
}

class FavoriteError extends FavoriteState {
  final String message;

  FavoriteError(this.message);
}
