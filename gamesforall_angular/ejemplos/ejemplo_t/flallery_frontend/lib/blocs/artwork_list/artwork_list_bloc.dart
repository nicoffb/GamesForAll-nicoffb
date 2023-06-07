import 'dart:async';

import 'package:bloc/bloc.dart';
import 'package:bloc_concurrency/bloc_concurrency.dart';
import 'package:equatable/equatable.dart';
import 'package:flallery_frontend/models/artwork_list_response.dart';
import 'package:flallery_frontend/services/artwork_service.dart';
import 'package:http/src/client.dart';
//import 'package:http/http.dart' as http;
import 'package:stream_transform/stream_transform.dart';



part 'artwork_list_event.dart';
part 'artwork_list_state.dart';

//const _artworkLimit = 20;
var page = 0;
const throttleDuration = Duration(milliseconds: 100);

EventTransformer<E> throttleDroppable<E>(Duration duration) {
  return (events, mapper) {
    return droppable<E>().call(events.throttle(duration), mapper);
  };
}

class ArtworkBloc extends Bloc<ArtworkEvent, ArtworkState> {
  final ArtworkService _artworkService;
  // final int id;
  ArtworkBloc(ArtworkService artworkService)
      : assert(artworkService != null),
        _artworkService = artworkService,
        super(const ArtworkState()) {
    on<ArtworkFetched>(
      _onArtworkFetched,
      transformer: throttleDroppable(throttleDuration),
    );
  }

  Future<void> _onArtworkFetched(
    ArtworkFetched event,
    Emitter<ArtworkState> emit,
  ) async {
    if (state.hasReachedMax) {
      return;
    }
    try {
      if (state.status == ArtworkStatus.initial) {
        page = 0;
        final artworks = await _artworkService.getAllArtworks(page);
        return emit(
          state.copyWith(
            status: ArtworkStatus.success,
            artworkList: artworks.content,
            hasReachedMax: artworks.totalPages! - 1 <= page,
          ),
        );
      }
      page++;
      final artworks = await _artworkService.getAllArtworks(page);
      emit(state.copyWith(
        status: ArtworkStatus.success,
        artworkList: List.of(state.artworkList)..addAll(artworks.content as Iterable<Artwork>),
        hasReachedMax: artworks.totalPages! - 1 <= page,
      ));
    } catch(_) {
      emit(state.copyWith(status: ArtworkStatus.failure));
    }
  }
}