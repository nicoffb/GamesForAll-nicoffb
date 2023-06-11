part of 'message_bloc.dart';

abstract class MessageState extends Equatable {
  const MessageState();

  @override
  List<Object> get props => [];
}

class MessageLoading extends MessageState {}

class MessageLoaded extends MessageState {
  final List<MessageResponse> messages;

  MessageLoaded(this.messages);

  @override
  List<Object> get props => [messages];
}

class MessageError extends MessageState {}
