import 'package:gamesforall_frontend/models/product_response.dart';

class ProductPageResponse {
  List<ProductResponse>? content;
  bool? last;
  bool? first;
  int? totalPages;
  int? totalElements;
  int? currentPage;

  ProductPageResponse(
      {this.content,
      this.last,
      this.first,
      this.totalPages,
      this.totalElements,
      this.currentPage});

  ProductPageResponse.fromJson(Map<String, dynamic> json) {
    if (json['content'] != null) {
      content = <ProductResponse>[];
      json['content'].forEach((v) {
        content!.add(new ProductResponse.fromJson(v));
      });
    }
    last = json['last'];
    first = json['first'];
    totalPages = json['totalPages'];
    totalElements = json['totalElements'];
    currentPage = json['currentPage'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    if (this.content != null) {
      data['content'] = this.content!.map((v) => v.toJson()).toList();
    }
    data['last'] = this.last;
    data['first'] = this.first;
    data['totalPages'] = this.totalPages;
    data['totalElements'] = this.totalElements;
    data['currentPage'] = this.currentPage;
    return data;
  }
}

class Product {
  String? title;
  String? description;
  double? price;
  String? platform;
  String? image;

  Product(
      {this.title, this.description, this.price, this.platform, this.image});

  Product.fromJson(Map<String, dynamic> json) {
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
