import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:gamesforall_frontend/pages/conversation_page.dart';

import '../models/message_response.dart';
import '../models/product_detail_response.dart';
import '../repositories/product_repository.dart';
import '../services/message_service.dart';

class ProductDetailsPage extends StatelessWidget {
  final int productId;
  final MessageService messageService = MessageService();

  ProductDetailsPage({Key? key, required this.productId}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final ProductRepository repository = ProductRepository();
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
            return Center(
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Container(
                    height: 200,
                    width: 200,
                    decoration: BoxDecoration(
                      borderRadius: BorderRadius.circular(10),
                      image: DecorationImage(
                        image: NetworkImage(
                          "http://localhost:8080/product/download/${product.image}",
                        ),
                        fit: BoxFit.cover,
                      ),
                    ),
                  ),
                  SizedBox(height: 16),
                  Text(
                    'ID del producto: $productId',
                    style: TextStyle(fontSize: 10),
                  ),
                  SizedBox(height: 8),
                  Text(
                    'Avatar: ${product.user?.avatar}',
                    style: TextStyle(fontSize: 16),
                  ),
                  SizedBox(height: 8),
                  Text(
                    'Nombre del usuario: ${product.user?.username}',
                    style: TextStyle(fontSize: 16),
                  ),
                  SizedBox(height: 8),
                  Text(
                    'Media del usuario: ${product.user?.userScore}',
                    style: TextStyle(fontSize: 16),
                  ),
                  SizedBox(height: 16),
                  Text(
                    'Nombre: ${product.title}',
                    style: TextStyle(fontSize: 24),
                  ),
                  SizedBox(height: 8),
                  Text(
                    'Descripción: ${product.description}',
                    style: TextStyle(fontSize: 16),
                  ),
                  SizedBox(height: 8),
                  Text(
                    'Plataforma: ${product.platform?.platformName}',
                    style: TextStyle(fontSize: 16),
                  ),
                  SizedBox(height: 8),
                  Text(
                    'Estado del producto: ${product.state}',
                    style: TextStyle(fontSize: 16),
                  ),
                  SizedBox(height: 8),
                  Text(
                    'Disponibilidad de envío: ${product.shippingAvailable! ? 'No disponible' : 'Disponible'}',
                    style: TextStyle(fontSize: 16),
                  ),
                  SizedBox(height: 8),
                  Text(
                    'Vendido: ${product.sold! ? 'Vendido' : 'En venta'}',
                    style: TextStyle(fontSize: 16),
                  ),
                  SizedBox(height: 8),
                  const SizedBox(height: 8),
                  Text(
                    'Dirección: ${product.user?.address}',
                    style: TextStyle(fontSize: 16),
                  ),
                  SizedBox(height: 8),
                  Text(
                    'Fecha de subida: ${product.publicationDate}',
                    style: TextStyle(fontSize: 16),
                  ),
                  SizedBox(height: 4),
                  Text(
                    'Categorías:',
                    style: TextStyle(fontSize: 16, fontWeight: FontWeight.bold),
                  ),
                  SizedBox(height: 4),
                  Wrap(
                    spacing: 4,
                    children: product.categories?.map<Widget>((category) {
                          String? genre = category.genre;
                          return Chip(label: Text(genre ?? ''));
                        }).toList() ??
                        [],
                  ),
                  TextButton(
                    onPressed: () async {
                      if (product.user != null) {
                        List<MessageResponse> messages = await messageService
                            .getMessagesWithUser(product.user!.id!);
                        Navigator.push(
                          context,
                          MaterialPageRoute(
                            builder: (context) =>
                                ConversationPage(messages: messages),
                          ),
                        );
                      }
                    },
                    child: Text('Iniciar conversación'),
                  ),
                ],
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
