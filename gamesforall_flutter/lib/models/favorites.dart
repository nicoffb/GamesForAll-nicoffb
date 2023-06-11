class Favorites {
  String? title;
  String? description;
  double? price;
  String? platform;
  String? image;

  Favorites(
      {this.title, this.description, this.price, this.platform, this.image});

  Favorites.fromJson(Map<String, dynamic> json) {
    title = json['title'];
    description = json['description'];
    price = json['price'];
    platform = json['platform'];
    image = json['image'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['title'] = this.title;
    data['description'] = this.description;
    data['price'] = this.price;
    data['platform'] = this.platform;
    data['image'] = this.image;
    return data;
  }
}
