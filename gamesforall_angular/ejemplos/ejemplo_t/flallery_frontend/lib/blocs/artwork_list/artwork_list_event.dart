part of 'artwork_list_bloc.dart';
abstract class ArtworkEvent extends Equatable {
  @override
  List<Object> get props => [];
}

class ArtworkFetched extends ArtworkEvent {}