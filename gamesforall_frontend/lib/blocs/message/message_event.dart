part of 'message_bloc.dart';

abstract class MessageEvent extends Equatable {
  const MessageEvent();

  @override
  List<Object> get props => [];
}

class LoadMessages extends MessageEvent {}

class AddMessage extends MessageEvent {
  final String messageText;

  AddMessage({required this.messageText});

  @override
  List<Object> get props => [messageText];
}
