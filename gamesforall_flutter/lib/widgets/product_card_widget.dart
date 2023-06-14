import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:gamesforall_frontend/services/product_service.dart';
import 'package:provider/provider.dart';
import '../blocs/favorite/favorite_bloc.dart';
import '../models/product_response.dart';
import '../pages/product_details_page.dart';

class ProductCard extends StatelessWidget {
  const ProductCard({Key? key, required this.product}) : super(key: key);

  final ProductResponse product;

  @override
  Widget build(BuildContext context) {
    final productService = Provider.of<ProductService>(context, listen: false);
    final favBloc = BlocProvider.of<FavoriteBloc>(context);

    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 8),
      child: InkWell(
        onTap: () {
          Navigator.push(
            context,
            MaterialPageRoute(
              builder: (context) => ProductDetailsPage(productId: product.id),
            ),
          );
        },
        child: Card(
          color: Color.fromARGB(85, 56, 182, 255),
          shape: RoundedRectangleBorder(
            borderRadius: BorderRadius.circular(15.0),
          ),
          clipBehavior: Clip.antiAliasWithSaveLayer,
          elevation: 6.0,
          child: Stack(
            alignment: Alignment.centerLeft,
            children: [
              Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  AspectRatio(
                    aspectRatio: 0.8,
                    child: Stack(
                      fit: StackFit.expand,
                      children: [
                        Image.network(
                          'http://10.0.2.2:8080/product/download/${product.image}',
                          fit: BoxFit.cover,
                        ),
                        Container(
                          decoration: BoxDecoration(
                            gradient: LinearGradient(
                              begin: Alignment.topCenter,
                              end: Alignment.bottomCenter,
                              colors: [
                                // Colors.black.withOpacity(0.3),
                                // Colors.black.withOpacity(0.1),
                              ],
                            ),
                          ),
                        ),
                      ],
                    ),
                  ),
                  Padding(
                    padding: const EdgeInsets.all(8),
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Text(
                          product.title!,
                          style: TextStyle(
                            fontSize: 14,
                            fontWeight: FontWeight.bold,
                            color: Color.fromARGB(255, 254, 254,
                                254), // Cambiamos el color del texto a blanco
                          ),
                          maxLines: 2,
                          overflow: TextOverflow.ellipsis,
                        ),
                        SizedBox(height: 5),
                        Text(
                          product.platform!,
                          style: TextStyle(
                              fontSize: 14,
                              color: Colors
                                  .cyanAccent, // Color brillante para la plataforma
                              fontWeight: FontWeight.bold),
                          maxLines: 1,
                          overflow: TextOverflow.ellipsis,
                        ),
                        SizedBox(height: 5),
                        Text(
                          '${product.price?.toStringAsFixed(product.price?.truncateToDouble() == product.price ? 0 : 2)} €',
                          style: TextStyle(
                            fontSize: 14,
                            fontWeight: FontWeight.bold,
                            color: Colors
                                .greenAccent, // Color brillante para el precio
                          ),
                        ),
                        SizedBox(height: 5),
                        Text(
                          '${product.state}',
                          style: TextStyle(
                            fontSize: 13,
                            color: Color.fromARGB(255, 124, 124, 124),
                          ),
                          maxLines: 1,
                          overflow: TextOverflow.ellipsis,
                        ),
                      ],
                    ),
                  ),
                ],
              ),
              Positioned(
                right: 10,
                top: 10,
                child: BlocBuilder<FavoriteBloc, FavoriteState>(
                  bloc: favBloc,
                  builder: (context, state) {
                    if (state is FavoritesLoaded) {
                      bool isFavorite = state.favoriteProducts.any(
                          (favoriteProduct) =>
                              favoriteProduct.id == product.id);

                      return IconButton(
                        icon: Icon(
                          isFavorite ? Icons.favorite : Icons.favorite_border,
                          color: Colors.red,
                        ),
                        onPressed: () {
                          if (isFavorite) {
                            favBloc.add(RemoveFromFavoritesEvent(product.id));
                          } else {
                            favBloc.add(AddToFavoritesEvent(product.id));
                          }
                        },
                      );
                    }
                    return IconButton(
                      icon: Icon(Icons.favorite_border, color: Colors.red),
                      onPressed: null,
                    );
                  },
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
