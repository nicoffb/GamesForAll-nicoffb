part of 'artwork_list_bloc.dart';

enum ArtworkStatus { initial, success, failure }

class ArtworkState extends Equatable {
  const ArtworkState({
    this.status = ArtworkStatus.initial,
    this.artworkList = const <Artwork>[],
    this.hasReachedMax = false,
  });

  final ArtworkStatus status;
  final List<Artwork> artworkList;
  final bool hasReachedMax;

  ArtworkState copyWith({
    ArtworkStatus? status,
    List<Artwork>? artworkList,
    bool? hasReachedMax,
  }) {
    return ArtworkState(
      status: status ?? this.status,
      artworkList: artworkList ?? this.artworkList,
      hasReachedMax: hasReachedMax ?? this.hasReachedMax,
    );
  }

  @override
  String toString() {
    return '''ArtworkState { status: $status, hasReachedMax: $hasReachedMax, artworkList: ${artworkList.length} }''';
  }

  @override
  List<Object> get props => [status, artworkList, hasReachedMax];
}
