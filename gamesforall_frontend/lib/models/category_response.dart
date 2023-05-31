class CategoryResponse {
  int? id;
  String? genre;

  CategoryResponse({this.id, this.genre});

  CategoryResponse.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    genre = json['genre'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['id'] = this.id;
    data['genre'] = this.genre;
    return data;
  }
}
