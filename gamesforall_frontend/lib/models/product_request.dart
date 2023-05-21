import 'package:gamesforall_frontend/models/product_detail_response.dart';

class ProductRequest {
  String? title;
  String? description;
  double? price;
  String? state;
  Platform? platform;
  Set<Categories>? categories;

  ProductRequest(
      {this.title,
      this.description,
      this.price,
      this.state,
      this.platform,
      this.categories});

  ProductRequest.fromJson(Map<String, dynamic> json) {
    title = json['title'];
    description = json['description'];
    price = json['price'];
    state = json['state'];
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
    data['price'] = this.price;
    data['state'] = this.state;
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

  Platform({this.id});

  Platform.fromJson(Map<String, dynamic> json) {
    id = json['id'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['id'] = this.id;
    return data;
  }
}
