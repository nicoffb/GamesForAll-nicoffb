import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:gamesforall_frontend/blocs/message/message_bloc.dart';
import 'package:gamesforall_frontend/pages/conversation_page.dart';

import '../blocs/edit_product/edit_product_bloc.dart';
import '../models/message_response.dart';
import '../models/product_detail_response.dart';
import '../repositories/product_repository.dart';
import '../services/localstorage_service.dart';
import '../services/message_service.dart';
import 'edit_product_page.dart';

import 'package:flutter_rating_bar/flutter_rating_bar.dart';

class ProductDetailsPage extends StatelessWidget {
  final int productId;
  final MessageService messageService = MessageService();
  var currentUsername;
  ProductDetailsPage({Key? key, required this.productId}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final ProductRepository repository = ProductRepository();
    final LocalStorageService localStorageService = LocalStorageService();
    currentUsername = localStorageService.getFromDisk("user_name");
    final Future<ProductDetailsResponse> productFuture =
        repository.getProductById(productId);

    return Scaffold(
      appBar: AppBar(
        title: Text('Detalles del producto'),
      ),
      body: FutureBuilder<ProductDetailsResponse>(
        future: productFuture,
        builder: (context, snapshot) {
          if (snapshot.hasData) {
            final ProductDetailsResponse product = snapshot.data!;
            late ImageProvider avatarImage;

            if (product.user?.avatar != null) {
              avatarImage = NetworkImage(product.user!.avatar!);
            } else {
              avatarImage = AssetImage('assets/gamesforall.png');
            }

            return SingleChildScrollView(
              child: Center(
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Container(
                      padding: EdgeInsets.all(10),
                      child: Row(
                        children: [
                          Container(
                            height: 250,
                            width: 200,
                            decoration: BoxDecoration(
                              borderRadius: BorderRadius.circular(10),
                              image: DecorationImage(
                                image: NetworkImage(
                                  "http://10.0.2.2:8080/product/download/${product.image}",
                                ),
                                fit: BoxFit.cover,
                              ),
                            ),
                          ),
                          SizedBox(width: 25),
                          Column(
                            children: [
                              CircleAvatar(
                                radius: 72,
                                backgroundColor: Colors.blue,
                                child: CircleAvatar(
                                  radius: 70,
                                  backgroundImage: avatarImage,
                                ),
                              ),
                              SizedBox(height: 8),
                              Text(
                                product.user?.username ?? 'Usuario desconocido',
                                style: TextStyle(fontSize: 16),
                              ),
                              SizedBox(height: 8),
                              RatingBarIndicator(
                                rating: product.user?.userScore ?? 0,
                                itemBuilder: (context, index) => Icon(
                                  Icons.star,
                                  color: Colors.amber,
                                ),
                                itemCount: 5,
                                itemSize: 15.0,
                              ),
                            ],
                          ),
                        ],
                      ),
                    ),
                    Padding(
                      padding: const EdgeInsets.only(left: 12.0, top: 9),
                      child: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          Text(
                            'Nombre: ${product.title}',
                            style: TextStyle(
                              fontSize: 22,
                              fontWeight: FontWeight.bold,
                              letterSpacing: 2.0,
                              color: Colors.black,
                              shadows: [
                                Shadow(
                                  color: Colors.black.withOpacity(0.5),
                                  offset: Offset(2, 2),
                                  blurRadius: 3,
                                ),
                              ],
                            ),
                          ),
                          SizedBox(height: 16),
                          Text(
                            'Precio: ${product.price}€',
                            style: TextStyle(fontSize: 16, color: Colors.black),
                          ),
                          SizedBox(height: 16),
                          Text(
                            'Descripción: ${product.description}',
                            style: TextStyle(fontSize: 16, color: Colors.black),
                          ),
                          SizedBox(height: 16),
                          Text(
                            'Plataforma: ${product.platform?.platformName}',
                            style: TextStyle(fontSize: 16, color: Colors.black),
                          ),
                          SizedBox(height: 16),
                          Text(
                            'Estado del producto: ${product.state}',
                            style: TextStyle(fontSize: 16, color: Colors.black),
                          ),
                          // SizedBox(height: 16),
                          // Text(
                          //   'Disponibilidad: ${product.sold! ? 'Vendido' : 'En venta'}',
                          //   style: TextStyle(fontSize: 16, color: Colors.black),
                          // ),
                          SizedBox(height: 16),
                          Text(
                            'Disponibilidad de envío: ${product.shippingAvailable! ? 'Disponible' : 'No disponible'}',
                            style: TextStyle(fontSize: 16, color: Colors.black),
                          ),
                          SizedBox(height: 16),
                          Text(
                            'Dirección: ${product.user?.address}',
                            style: TextStyle(fontSize: 16, color: Colors.black),
                          ),
                          SizedBox(height: 16),
                          Text(
                            'Fecha de subida: ${product.publicationDate}',
                            style: TextStyle(fontSize: 16, color: Colors.black),
                          ),
                          SizedBox(height: 25),
                          Text(
                            'Categorías:',
                            style: TextStyle(
                              fontSize: 16,
                              fontWeight: FontWeight.bold,
                              letterSpacing: 2.0,
                              color: Colors.black,
                              shadows: [
                                Shadow(
                                  color: Colors.black.withOpacity(0.5),
                                  offset: Offset(2, 2),
                                  blurRadius: 3,
                                ),
                              ],
                            ),
                          ),
                          SizedBox(height: 16),
                          Wrap(
                            spacing: 8,
                            children:
                                product.categories?.map<Widget>((category) {
                                      String? genre = category.genre;
                                      return Chip(
                                        label: Text(
                                          genre ?? '',
                                          style: TextStyle(
                                            fontSize: 14,
                                            color: Colors.white,
                                          ),
                                        ),
                                        backgroundColor: Colors.blue,
                                        padding: EdgeInsets.symmetric(
                                          vertical: 8,
                                          horizontal: 12,
                                        ),
                                      );
                                    }).toList() ??
                                    [],
                          ),
                          SizedBox(height: 16),
                          if (currentUsername != product.user?.username)
                            TextButton(
                              onPressed: () async {
                                if (product.user != null) {
                                  final messages = await messageService
                                      .getMessagesWithUser(product.user!.id!);
                                  Navigator.push(
                                    context,
                                    MaterialPageRoute(
                                      builder: (context) => ConversationPage(
                                        targetUser: product.user!.id!,
                                        messages: messages,
                                      ),
                                    ),
                                  );
                                }
                              },
                              style: ButtonStyle(
                                backgroundColor:
                                    MaterialStateProperty.all(Colors.blue),
                                foregroundColor:
                                    MaterialStateProperty.all(Colors.white),
                                padding: MaterialStateProperty.all(
                                  EdgeInsets.symmetric(
                                    vertical: 12,
                                    horizontal: 16,
                                  ),
                                ),
                              ),
                              child: Container(
                                child: Row(
                                  mainAxisAlignment: MainAxisAlignment.center,
                                  children: <Widget>[
                                    Icon(Icons.chat),
                                    SizedBox(width: 10),
                                    Text('Iniciar conversación'),
                                  ],
                                ),
                              ),
                            ),
                          if (currentUsername == product.user?.username)
                            Container(
                              margin: EdgeInsets.all(5),
                              child: TextButton(
                                style: ButtonStyle(
                                  backgroundColor:
                                      MaterialStateProperty.all(Colors.yellow),
                                  foregroundColor:
                                      MaterialStateProperty.all(Colors.black),
                                  padding: MaterialStateProperty.all(
                                      EdgeInsets.symmetric(
                                          vertical: 10, horizontal: 100)),
                                ),
                                onPressed: () {
                                  Navigator.push(
                                    context,
                                    MaterialPageRoute(
                                      builder: (context) => EditProductPage(
                                          product: snapshot.data!),
                                    ),
                                  );
                                },
                                child: Row(
                                  children: [
                                    Icon(Icons.edit),
                                    SizedBox(width: 12),
                                    Text('Editar producto'),
                                  ],
                                ),
                              ),
                            ),
                        ],
                      ),
                    ),
                  ],
                ),
              ),
            );
          } else if (snapshot.hasError) {
            return Center(
              child: Text('Error al cargar el producto'),
            );
          } else {
            return Center(
              child: CircularProgressIndicator(),
            );
          }
        },
      ),
    );
  }
}
