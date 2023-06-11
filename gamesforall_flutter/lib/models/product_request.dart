import 'package:gamesforall_frontend/models/product_detail_response.dart';

class ProductRequest {
  String? title;
  String? description;
  String? image;
  double? price;
  String? publicationDate;
  String? state;
  bool? is_shipping_available;
  bool? sold;
  Platform? platform;
  Set<Categories>? categories;

  ProductRequest(
      {this.title,
      this.description,
      this.image,
      this.price,
      this.publicationDate,
      this.state,
      this.is_shipping_available,
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
    is_shipping_available = json['is_shipping_available'];
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
    data['is_shipping_available'] = this.is_shipping_available;
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
