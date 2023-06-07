import 'package:flutter/material.dart';

class RestauranteDetailResult {
  String? id;
  String? nombre;
  String? descripcion;
  String? coverImgUrl;
  TimeOfDay? apertura;
  TimeOfDay? cierre;

  RestauranteDetailResult(
      {this.id,
      this.nombre,
      this.descripcion,
      this.coverImgUrl,
      this.apertura,
      this.cierre});

  RestauranteDetailResult.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    nombre = json['nombre'];
    descripcion = json['descripcion'];
    coverImgUrl = json['coverImgUrl'];
    apertura = TimeOfDay(
        hour: int.parse(json['apertura'].split(":")[0]),
        minute: int.parse(json['apertura'].split(":")[1]));
    cierre = TimeOfDay(
        hour: int.parse(json['cierre'].split(":")[0]),
        minute: int.parse(json['cierre'].split(":")[1]));
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['id'] = this.id;
    data['nombre'] = this.nombre;
    data['descripcion'] = this.descripcion;
    data['coverImgUrl'] = this.coverImgUrl;
    data['apertura'] = this.apertura.toString();
    data['cierre'] = this.cierre.toString();
    return data;
  }
}
