class RateRequest {
  double nota;
  String? comentario;

  RateRequest({required this.nota, this.comentario});

  /*RateRequest.fromJson(Map<String, dynamic> json) {
    nota = json['nota'];
    comentario = json['comentario'];
  }*/

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['nota'] = nota;
    data['comentario'] = comentario;
    return data;
  }
}
