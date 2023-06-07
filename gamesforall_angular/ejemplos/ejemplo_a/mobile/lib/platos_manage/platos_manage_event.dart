import 'package:equatable/equatable.dart';
import 'package:file_picker/src/platform_file.dart';

import '../model/plato_request.dart';

abstract class PlatosManageEvent extends Equatable {
  const PlatosManageEvent();

  @override
  List<Object> get props => [];
}

class PlatosFetchedEvent extends PlatosManageEvent {
  PlatosFetchedEvent(this.restaurantId);
  String restaurantId;
}

class DeletePlatoEvent extends PlatosManageEvent {
  DeletePlatoEvent(this.platoId);
  String platoId;
}

class PlatoDetailFetchedEvent extends PlatosManageEvent {
  PlatoDetailFetchedEvent(this.platoId);
  String platoId;
}

class EditCancelled extends PlatosManageEvent {}

class EditPlato extends PlatosManageEvent {
  EditPlato(this.platoId, this.platoRequest);
  String platoId;
  PlatoRequest platoRequest;
}

class ChangeImgEvent extends PlatosManageEvent {
  ChangeImgEvent(this.platoId, this.file);
  String platoId;
  PlatformFile file;
}
