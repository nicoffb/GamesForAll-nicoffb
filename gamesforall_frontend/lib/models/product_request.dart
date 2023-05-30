import 'package:gamesforall_frontend/models/product_detail_response.dart';

class ProductRequest {
  String? title;
  String? description;
  String? image;
  double? price;
  String? publicationDate;
  String? state;
  bool? shippingAvailable;
  bool? sold;
  Platform? platform;
  Set<Categories>? categories;

  ProductRequest(
      {
      this.title,
      this.description,
      this.image,
      this.price,
      this.publicationDate,
      this.state,
      this.shippingAvailable,
      this.sold,
      this.platform,
      this.categories});

  ProductRequest.fromJson(Map<String, dynamic> json) {
    title = json['title'];
    description = json['description'];
    image = json['image'];
    price = json['price'];
    publicationDate = json['publication_date'];
    state = json['state'];
    shippingAvailable = json['shipping_available'];
    sold = json['sold'];
    platform = json['platform'] != null
        ? new Platform.fromJson(json['platform'])
        : null;
    if (json['categories'] != null) {
      categories = <Categories>{};
      json['categories'].forEach((v) {
        categories!.add(new Categories.fromJson(v));
      });
    }
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['title'] = this.title;
    data['description'] = this.description;
    data['image'] = this.image;
    data['price'] = this.price;
    data['publication_date'] = this.publicationDate;
    data['state'] = this.state;
    data['shipping_available'] = this.shippingAvailable;
    data['sold'] = this.sold;
    if (this.platform != null) {
      data['platform'] = this.platform!.toJson();
    }
    if (this.categories != null) {
      data['categories'] = this.categories!.map((v) => v.toJson()).toList();
    }
    return data;
  }
}

class Platform {
  int? id;
  String? platformName;

  Platform({this.id, this.platformName});

  Platform.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    platformName = json['platformName'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['id'] = this.id;
    data['platformName'] = this.platformName;
    return data;
  }
}

class Categories {
  int? id;
  String? genre;

  Categories({this.id, this.genre});

  Categories.fromJson(Map<String, dynamic> json) {
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