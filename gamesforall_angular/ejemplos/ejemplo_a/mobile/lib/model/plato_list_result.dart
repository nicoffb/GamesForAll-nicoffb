class PlatoListResult {
  List<PlatoGeneric>? contenido;
  int? numeroResultados;
  int? numeroPaginas;
  int? paginaActual;

  PlatoListResult(
      {this.contenido,
      this.numeroResultados,
      this.numeroPaginas,
      this.paginaActual});

  PlatoListResult.fromJson(Map<String, dynamic> json) {
    if (json['contenido'] != null) {
      contenido = <PlatoGeneric>[];
      json['contenido'].forEach((v) {
        contenido!.add(new PlatoGeneric.fromJson(v));
      });
    }
    numeroResultados = json['numeroResultados'];
    numeroPaginas = json['numeroPaginas'];
    paginaActual = json['paginaActual'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    if (this.contenido != null) {
      data['contenido'] = this.contenido!.map((v) => v.toJson()).toList();
    }
    data['numeroResultados'] = this.numeroResultados;
    data['numeroPaginas'] = this.numeroPaginas;
    data['paginaActual'] = this.paginaActual;
    return data;
  }
}

class PlatoGeneric {
  String? id;
  String? nombre;
  double? precio;
  String? imgUrl;
  bool? sinGluten;

  PlatoGeneric(
      {this.id, this.nombre, this.precio, this.imgUrl, this.sinGluten});

  PlatoGeneric.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    nombre = json['nombre'];
    precio = json['precio'];
    imgUrl = json['imgUrl'];
    sinGluten = json['sinGluten'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['id'] = this.id;
    data['nombre'] = this.nombre;
    data['precio'] = this.precio;
    data['imgUrl'] = this.imgUrl;
    data['sinGluten'] = this.sinGluten;
    return data;
  }
}
