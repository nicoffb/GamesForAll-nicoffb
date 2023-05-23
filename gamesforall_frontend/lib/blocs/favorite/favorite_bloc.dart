import 'package:bloc/bloc.dart';
import 'package:equatable/equatable.dart';

part 'favorite_event.dart';
part 'favorite_state.dart';

class FavBloc extends Bloc<FavEvent, FavState> {
  FavBloc() : super(FavRemovedState());

  @override
  Stream<FavState> mapEventToState(FavEvent event) async* {
    if (event is AddToFavoritesEvent) {
      yield FavAddedState();
    } else if (event is RemoveFromFavoritesEvent) {
      yield FavRemovedState();
    }
  }
}