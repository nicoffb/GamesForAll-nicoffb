import 'package:gamesforall_frontend/blocs/productList/product_bloc.dart';
import 'package:get_it/get_it.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';

import 'package:injectable/injectable.dart';

import '../models/product_page_response.dart';
import '../rest/rest_client.dart';

const _postLimit = 20;

@singleton
class ProductRepository {
  late RestAuthenticatedClient server;
  ProductRepository() {
    server = GetIt.I.get<RestAuthenticatedClient>();
  }

  Future<ProductPageResponse> getProductList(int page,
      {ProductType productType = ProductType.search}) async {
    String urlString;

    switch (productType) {
      case ProductType.search:
        urlString = '/product/search/?page=$page';
        break;
      case ProductType.favorites:
        urlString = '/favorites/?page=$page';
        break;
      case ProductType.myproducts:
        urlString = '/myproducts/?page=$page';
        break;
    }

    var jsonResponse = await server.get(urlString);
    ProductPageResponse pagedProducts =
        ProductPageResponse.fromJson(jsonDecode(jsonResponse));

    return pagedProducts;
  }
}
