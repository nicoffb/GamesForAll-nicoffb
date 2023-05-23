part of 'favorite_bloc.dart';

abstract class FavEvent {}

class AddToFavoritesEvent extends FavEvent {
  final int productId;

  AddToFavoritesEvent(this.productId);
}

class RemoveFromFavoritesEvent extends FavEvent {
  final int productId;

  RemoveFromFavoritesEvent(this.productId);
}