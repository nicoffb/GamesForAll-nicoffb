class ProductResponse {
  late int id;
  String? title;
  String? image;
  double? price;
  String? publicationDate;
  String? state;
  String? platform;
  Set<String>? category;
  String? description;

  ProductResponse({
    required this.id,
    this.title,
    this.image,
    this.price,
    this.publicationDate,
    this.state,
    this.platform,
    this.category,
    this.description,
  });

  ProductResponse.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    title = json['title'];
    image = json['image'];
    price = json['price'];
    publicationDate = json['publicationDate'];
    state = json['state'];
    platform = json['platform'];
    category =
        json['category'] != null ? Set<String>.from(json['category']) : null;
    description = json['description'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['id'] = this.id;
    data['title'] = this.title;
    data['image'] = this.image;
    data['price'] = this.price;
    data['publicationDate'] = this.publicationDate;
    data['state'] = this.state;
    data['platform'] = this.platform;
    data['category'] = this.category;
    data['description'] = this.description;
    return data;
  }
}
