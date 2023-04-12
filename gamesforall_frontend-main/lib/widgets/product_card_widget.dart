import 'package:flutter/material.dart';

import '../models/product_response.dart';

class ProductCard extends StatelessWidget {
  const ProductCard({Key? key, required this.product}) : super(key: key);

  final ProductResponse product;

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 5),
      child: Card(
        shape: RoundedRectangleBorder(
          borderRadius: BorderRadius.circular(15.0),
        ),
        clipBehavior: Clip.antiAliasWithSaveLayer,
        elevation: 3.0,
        child: Row(
          children: [
            Flexible(
              flex: 2,
              child: Container(
                padding: const EdgeInsets.all(15),
                decoration: BoxDecoration(
                  gradient: RadialGradient(
                    radius: 2,
                    colors: [
                      Color.fromARGB(0, 0, 0, 0),
                      Color.fromARGB(170, 0, 0, 0),
                    ],
                  ),
                ),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text(
                      product.title!,
                      style: TextStyle(
                        fontSize: 19,
                        fontWeight: FontWeight.bold,
                        color: Color.fromARGB(255, 51, 124, 183),
                        shadows: [
                          Shadow(
                            offset: Offset(1.0, 1.0),
                            blurRadius: 2.0,
                            color: Color.fromARGB(200, 0, 0, 0),
                          )
                        ],
                      ),
                    ),
                    SizedBox(height: 10),
                    Text(
                      product.platform!,
                      style: TextStyle(
                        fontSize: 13,
                        fontWeight: FontWeight.bold,
                        color: Color.fromARGB(255, 23, 23, 23),
                        shadows: [
                          Shadow(
                            offset: Offset(0.5, 0.5),
                            blurRadius: 2.0,
                            color: Color.fromARGB(200, 0, 0, 0),
                          )
                        ],
                      ),
                    ),
                    SizedBox(height: 10),
                    Text(
                      '${product.price?.toStringAsFixed(product.price?.truncateToDouble() == product.price ? 0 : 2)} €',
                      style: TextStyle(
                          fontSize: 14,
                          fontWeight: FontWeight.bold,
                          color: Color.fromARGB(255, 51, 124, 183),
                          shadows: [
                            Shadow(
                              offset: Offset(1.0, 1.0),
                              blurRadius: 2.0,
                              color: Color.fromARGB(200, 0, 0, 0),
                            )
                          ]),
                    ),
                    SizedBox(height: 10),
                    Text(
                      '${product.description}',
                      style: TextStyle(
                          fontSize: 18,
                          color: Color.fromARGB(255, 255, 255, 255),
                          shadows: [
                            Shadow(
                              offset: Offset(1.0, 1.0),
                              blurRadius: 2.0,
                              color: Color.fromARGB(200, 0, 0, 0),
                            )
                          ]),
                    ),
                  ],
                ),
              ),
            ),
            Expanded(
              flex: 3,
              child: Image.network(
                'http://localhost:8080/product/download/${product.image}',
                fit: BoxFit.cover,
              ),
            ),
          ],
        ),
      ),
    );
  }
}
