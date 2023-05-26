import 'package:bloc/bloc.dart';
import 'package:equatable/equatable.dart';

import '../../models/message_request.dart';
import '../../models/message_response.dart';
import '../../services/message_service.dart';

part 'message_event.dart';
part 'message_state.dart';

class MessageBloc extends Bloc<MessageEvent, MessageState> {
  final List<MessageResponse> messages;
  final MessageService messageService = MessageService();
  final String targetUser;

  MessageBloc({required this.messages, required this.targetUser})
      : super(MessageInitial());

  @override
  Stream<MessageState> mapEventToState(
    MessageEvent event,
  ) async* {
    if (event is LoadMessages) {
      yield MessageLoading();

      try {
        List<MessageResponse> loadedMessages =
            await messageService.getMessagesWithUser(targetUser);
        yield MessageLoaded(loadedMessages);
      } catch (e) {
        yield MessageInitial();
      }
    } else if (event is AddMessage) {
      yield MessageLoading();

      try {
        MessageRequest messageRequest =
            MessageRequest(comment: event.messageText);
        MessageResponse messageResponse =
            await messageService.addMessage(messageRequest, targetUser);
        List<MessageResponse> updatedMessages = [...messages, messageResponse];
        yield MessageLoaded(updatedMessages);
      } catch (e) {
        yield MessageInitial();
      }
    }
  }
}
