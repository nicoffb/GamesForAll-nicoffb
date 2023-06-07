class PlatoDetailResult {
  String? id;
  String? nombre;
  String? descripcion;
  double? precio;
  String? imgUrl;
  List<String>? ingredientes;
  bool? sinGluten;
  List<Valoracion>? valoraciones;
  double? valoracionMedia;

  PlatoDetailResult(
      {this.id,
      this.nombre,
      this.descripcion,
      this.precio,
      this.imgUrl,
      this.ingredientes,
      this.sinGluten,
      this.valoraciones,
      this.valoracionMedia});

  PlatoDetailResult.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    nombre = json['nombre'];
    descripcion = json['descripcion'];
    precio = json['precio'];
    imgUrl = json['imgUrl'];
    ingredientes = json['ingredientes'].cast<String>();
    sinGluten = json['sinGluten'];
    if (json['valoraciones'] != null) {
      valoraciones = <Valoracion>[];
      json['valoraciones'].forEach((v) {
        valoraciones!.add(new Valoracion.fromJson(v));
      });
    }
    if (json['valoracionMedia'] is String) {
      valoracionMedia = null;
    } else {
      valoracionMedia = json['valoracionMedia'];
    }
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['id'] = this.id;
    data['nombre'] = this.nombre;
    data['descripcion'] = this.descripcion;
    data['precio'] = this.precio;
    data['imgUrl'] = this.imgUrl;
    data['ingredientes'] = this.ingredientes;
    data['sinGluten'] = this.sinGluten;
    if (this.valoraciones != null) {
      data['valoraciones'] = this.valoraciones!.map((v) => v.toJson()).toList();
    }
    data['valoracionMedia'] = this.valoracionMedia;
    return data;
  }
}

class Valoracion {
  String? username;
  double? nota;
  String? comentario;

  Valoracion({this.username, this.nota, this.comentario});

  Valoracion.fromJson(Map<String, dynamic> json) {
    username = json['username'];
    nota = json['nota'];
    comentario = json['comentario'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['username'] = this.username;
    data['nota'] = this.nota;
    data['comentario'] = this.comentario;
    return data;
  }
}
