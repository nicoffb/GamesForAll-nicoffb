part of 'message_bloc.dart';

abstract class MessageEvent extends Equatable {
  const MessageEvent();

  @override
  List<Object> get props => [];
}

class LoadMessages extends MessageEvent {
  final String targetUser;
  final List<MessageResponse> messages;

  LoadMessages(this.targetUser, this.messages);

  @override
  List<Object> get props => [targetUser, messages];
}

class AddMessage extends MessageEvent {
  final String messageText;
  final String targetUser;

  AddMessage({required this.messageText, required this.targetUser});

  @override
  List<Object> get props => [messageText, targetUser];
}
