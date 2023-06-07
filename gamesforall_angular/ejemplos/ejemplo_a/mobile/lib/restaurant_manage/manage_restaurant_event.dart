import 'package:equatable/equatable.dart';
import 'package:file_picker/file_picker.dart';

import '../model/plato_request.dart';
import '../model/restaurante_request.dart';

abstract class ManageRestaurantEvent extends Equatable {
  const ManageRestaurantEvent();

  @override
  List<Object> get props => [];
}

class RestaurantFetched extends ManageRestaurantEvent {
  RestaurantFetched(this.restaurantId);
  String restaurantId;
}

class DeleteRestaurantEvent extends ManageRestaurantEvent {
  DeleteRestaurantEvent(this.restaurantId);
  String restaurantId;
}

class EditRestaurant extends ManageRestaurantEvent {
  EditRestaurant(this.restaurantId, this.editData);
  String restaurantId;
  RestauranteEditRequest editData;
}

class ChangeImgEvent extends ManageRestaurantEvent {
  ChangeImgEvent(this.restaurantId, this.file);
  String restaurantId;
  PlatformFile file;
}

class AddPlato extends ManageRestaurantEvent {
  AddPlato(this.platoRequest, this.file);
  PlatoRequest platoRequest;
  PlatformFile file;
}
