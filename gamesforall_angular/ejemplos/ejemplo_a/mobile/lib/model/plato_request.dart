class PlatoRequest {
  String? nombre;
  String? descripcion;
  double? precio;
  List<String>? ingredientes;
  bool? sinGluten;

  PlatoRequest(this.nombre, this.descripcion, this.precio, this.ingredientes,
      this.sinGluten);

  PlatoRequest.fromJson(Map<String, dynamic> json) {
    nombre = json['nombre'];
    descripcion = json['descripcion'];
    precio = json['precio'];
    ingredientes = json['ingredientes'].cast<String>();
    sinGluten = json['sinGluten'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['nombre'] = this.nombre;
    data['descripcion'] = this.descripcion;
    data['precio'] = this.precio;
    data['ingredientes'] = this.ingredientes;
    data['sinGluten'] = this.sinGluten;
    return data;
  }
}
