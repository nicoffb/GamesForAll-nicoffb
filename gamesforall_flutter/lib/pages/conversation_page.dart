import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:gamesforall_frontend/models/message_request.dart';
import 'package:gamesforall_frontend/blocs/message/message_bloc.dart';
import 'package:gamesforall_frontend/models/message_response.dart';
import 'package:gamesforall_frontend/services/localstorage_service.dart';

class ConversationPage extends StatefulWidget {
  final String targetUser;
  final List<MessageResponse> messages;

  ConversationPage({required this.targetUser, required this.messages})
      : super();

  @override
  _ConversationPageState createState() => _ConversationPageState();
}

class _ConversationPageState extends State<ConversationPage> {
  final TextEditingController _textEditingController = TextEditingController();
  final LocalStorageService localStorageService = LocalStorageService();
  var currentUsername;

  @override
  void dispose() {
    _textEditingController.dispose();
    super.dispose();
  }

  @override
  void initState() {
    super.initState();
    currentUsername = localStorageService.getFromDisk("user_name");
    BlocProvider.of<MessageBloc>(context)
        .add(LoadMessages(widget.targetUser, widget.messages));
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Messages with User'),
      ),
      body: BlocConsumer<MessageBloc, MessageState>(
        listener: (context, state) {
          if (kDebugMode) {
            print(state);
          }
          if (state is MessageError) {
            // Manejar el estado de error
            // Mostrar una notificación, mensaje de error, etc.
          }
        },
        builder: (context, state) {
          if (state is MessageLoading) {
            return Center(
              child: CircularProgressIndicator(),
            );
          } else if (state is MessageLoaded) {
            return Column(
              children: [
                Expanded(
                  child: ListView.builder(
                    reverse: true,
                    itemCount: state.messages.length,
                    itemBuilder: (context, index) {
                      final message = state.messages[index];
                      final isSentByUser =
                          message.emisorUser == this.currentUsername;

                      return Container(
                        alignment: isSentByUser
                            ? Alignment.centerRight
                            : Alignment.centerLeft,
                        child: Container(
                          margin:
                              EdgeInsets.symmetric(vertical: 8, horizontal: 16),
                          padding:
                              EdgeInsets.symmetric(vertical: 8, horizontal: 16),
                          decoration: BoxDecoration(
                            color: isSentByUser ? Colors.blue : Colors.grey,
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
                          BlocProvider.of<MessageBloc>(context).add(
                            AddMessage(
                              messageText: messageText,
                              targetUser: widget.targetUser,
                            ),
                          );
                          _textEditingController.clear();
                        },
                      ),
                    ],
                  ),
                ),
              ],
            );
          } else {
            return Center(
              child: Text(
                'No hay mensajes disponibles',
                style: TextStyle(
                  fontSize: 18,
                  fontWeight: FontWeight.bold,
                ),
              ),
            );
          }
        },
      ),
    );
  }
}
