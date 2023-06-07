class RestauranteListResult {
  List<RestauranteGeneric>? contenido;
  int? numeroResultados;
  int? numeroPaginas;
  int? paginaActual;

  RestauranteListResult(
      {this.contenido,
      this.numeroResultados,
      this.numeroPaginas,
      this.paginaActual});

  RestauranteListResult.fromJson(Map<String, dynamic> json) {
    if (json['contenido'] != null) {
      contenido = <RestauranteGeneric>[];
      json['contenido'].forEach((v) {
        contenido!.add(new RestauranteGeneric.fromJson(v));
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

class RestauranteGeneric {
  String? id;
  String? nombre;
  String? coverImgUrl;
  String? apertura;
  String? cierre;

  RestauranteGeneric(
      {this.id, this.nombre, this.coverImgUrl, this.apertura, this.cierre});

  RestauranteGeneric.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    nombre = json['nombre'];
    coverImgUrl = json['coverImgUrl'];
    apertura = json['apertura'];
    cierre = json['cierre'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['id'] = this.id;
    data['nombre'] = this.nombre;
    data['coverImgUrl'] = this.coverImgUrl;
    data['apertura'] = this.apertura;
    data['cierre'] = this.cierre;
    return data;
  }
}
