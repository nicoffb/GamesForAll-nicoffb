import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:gamesforall_frontend/models/message_request.dart';
import 'package:gamesforall_frontend/services/message_service.dart';

import '../blocs/message/message_bloc.dart';
import '../models/message_response.dart';

class ConversationPage extends StatefulWidget {
  final List<MessageResponse> messages;
  final MessageService messageService = MessageService();
  final String targetUser;

  ConversationPage({required this.messages, required this.targetUser});

  @override
  _ConversationPageState createState() => _ConversationPageState();
}

class _ConversationPageState extends State<ConversationPage> {
  TextEditingController _textEditingController = TextEditingController();

  @override
  void dispose() {
    _textEditingController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Messages with User'),
      ),
      body: Column(
        children: [
          Expanded(
            child: ListView.builder(
              reverse: true,
              itemCount: widget.messages.length,
              itemBuilder: (context, index) {
                final message = widget.messages[index];
                final isSentByUser = message.emisorUser;

                return Container(
                  alignment: isSentByUser != null
                      ? Alignment.centerRight
                      : Alignment.centerLeft,
                  child: Container(
                    margin: EdgeInsets.symmetric(vertical: 8, horizontal: 16),
                    padding: EdgeInsets.symmetric(vertical: 8, horizontal: 16),
                    decoration: BoxDecoration(
                      color: isSentByUser != null ? Colors.blue : Colors.grey,
                      borderRadius: BorderRadius.circular(16),
                    ),
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Text(
                          message.comment!,
                          style: TextStyle(
                            color: Colors.white,
                            fontSize: 16,
                            fontWeight: FontWeight.bold,
                          ),
                        ),
                        SizedBox(height: 4),
                        Text(
                          message.messageDate.toString(),
                          style: TextStyle(
                            color: Colors.white,
                            fontSize: 12,
                          ),
                        ),
                      ],
                    ),
                  ),
                );
              },
            ),
          ),
          Container(
            padding: EdgeInsets.all(8),
            color: Colors.grey[200],
            child: Row(
              children: [
                Expanded(
                  child: TextField(
                    controller: _textEditingController,
                    decoration: InputDecoration(
                      hintText: 'Escribe tu mensaje...',
                      border: OutlineInputBorder(
                        borderRadius: BorderRadius.circular(20),
                      ),
                    ),
                  ),
                ),
                SizedBox(width: 8),
                IconButton(
                  icon: Icon(Icons.send),
                  onPressed: () {
                    String messageText = _textEditingController.text;
                    MessageRequest messageRequest =
                        MessageRequest(comment: messageText);
                    widget.messageService
                        .addMessage(messageRequest, widget.targetUser)
                        .then((messageResponse) {
                      // Aquí puedes realizar acciones adicionales con la respuesta del servicio
                      print('Mensaje enviado con éxito');
                      setState(() {
                        // Añadir el nuevo mensaje a la lista
                        widget.messages.add(messageResponse);
                        BlocProvider.of<MessageBloc>(context)
                            .add(LoadMessages());
                      });
                    }).catchError((error) {
                      print('Error al enviar el mensaje: $error');
                    });

                    _textEditingController.clear();
                  },
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }
}
