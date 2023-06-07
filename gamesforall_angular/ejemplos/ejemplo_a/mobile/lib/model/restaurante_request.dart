import 'package:flutter/material.dart';

class RestauranteEditRequest {
  String? nombre;
  String? apertura;
  String? cierre;
  String? descripcion;

  RestauranteEditRequest(
      this.nombre, this.apertura, this.cierre, this.descripcion);

  /*RestauranteEditRequest.fromJson(Map<String, dynamic> json) {
    nombre = json['nombre'];
    apertura = TimeOfDay(
        hour: int.parse(json['apertura'].split(":")[0]),
        minute: int.parse(json['apertura'].split(":")[1]));
    cierre = TimeOfDay(
        hour: int.parse(json['cierre'].split(":")[0]),
        minute: int.parse(json['cierre'].split(":")[1]));
    descripcion = json['descripcion'];
  }*/

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['nombre'] = this.nombre;
    data['apertura'] = this.apertura;
    data['cierre'] = this.cierre;
    data['descripcion'] = this.descripcion;
    return data;
  }
}
