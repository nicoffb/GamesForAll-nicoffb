import 'package:equatable/equatable.dart';

abstract class UploadProductEvent extends Equatable {
  const UploadProductEvent();

  @override
  List<Object> get props => [];
}

class SubmitProductEvent extends UploadProductEvent {}
