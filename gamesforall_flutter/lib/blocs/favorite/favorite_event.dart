part of 'favorite_bloc.dart';

abstract class FavoriteEvent {}

class FetchFavoritesEvent extends FavoriteEvent {}

class AddToFavoritesEvent extends FavoriteEvent {
  final int productId;

  AddToFavoritesEvent(this.productId);
}

class RemoveFromFavoritesEvent extends FavoriteEvent {
  final int productId;

  RemoveFromFavoritesEvent(this.productId);
}
