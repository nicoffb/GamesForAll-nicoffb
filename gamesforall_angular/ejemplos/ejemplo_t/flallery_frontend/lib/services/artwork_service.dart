import 'package:flallery_frontend/config/locator.dart';
import 'package:flallery_frontend/models/artwork_list_response.dart';
import 'package:flallery_frontend/repositories/artwork_repository.dart';
import 'package:flallery_frontend/services/services.dart';
import 'package:get_it/get_it.dart';
import 'package:injectable/injectable.dart';

@Order(2)
@singleton
class ArtworkService {
  late ArtworkRepository _artworkRepository;
  late LocalStorageService _localStorageService;

  ArtworkService() {
    _artworkRepository = getIt<ArtworkRepository>();
    GetIt.I
        .getAsync<LocalStorageService>()
        .then((value) => _localStorageService = value);
  }

  Future<ArtworkResponse> getAllArtworks(page) async {
    String? token = _localStorageService.getFromDisk("user_token");
   ArtworkResponse artworks= await _artworkRepository.fetchArtwork(page);
    return artworks;
  }

}
