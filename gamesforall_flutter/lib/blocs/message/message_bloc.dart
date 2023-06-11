import 'package:bloc/bloc.dart';
import 'package:equatable/equatable.dart';

import '../../models/message_request.dart';
import '../../models/message_response.dart';
import '../../services/message_service.dart';

part 'message_event.dart';
part 'message_state.dart';

class MessageBloc extends Bloc<MessageEvent, MessageState> {
  final MessageService messageService;

  MessageBloc({required this.messageService}) : super(MessageLoaded([])) {
    on<LoadMessages>(_mapLoadMessagesToState);
    on<AddMessage>(_mapAddMessageToState);
  }

  Future<void> _mapLoadMessagesToState(
    LoadMessages event,
    Emitter<MessageState> emit,
  ) async {
    emit(MessageLoading());
    try {
      List<MessageResponse> loadedMessages = event.messages;
      emit(MessageLoaded(loadedMessages));
    } catch (e) {
      emit(MessageError());
    }
  }

  Future<void> _mapAddMessageToState(
    AddMessage event,
    Emitter<MessageState> emit,
  ) async {
    if (state is MessageLoaded) {
      try {
        MessageRequest messageRequest =
            MessageRequest(comment: event.messageText);
        await messageService.addMessage(
          messageRequest,
          event.targetUser,
        );

        List<MessageResponse> updatedMessages =
            await messageService.getMessagesWithUser(event.targetUser);

        emit(MessageLoaded(updatedMessages));
      } catch (e) {
        emit(MessageError());
      }
    }
  }
}
