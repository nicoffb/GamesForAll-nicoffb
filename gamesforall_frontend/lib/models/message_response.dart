class MessageResponse {
  String? comment;
  String? messageDate;
  String? emisorUser;
  String? receptorUser;

  MessageResponse(
      {this.comment, this.messageDate, this.emisorUser, this.receptorUser});

  MessageResponse.fromJson(Map<String, dynamic> json) {
    comment = json['comment'];
    messageDate = json['message_date'];
    emisorUser = json['emisorUser'];
    receptorUser = json['receptorUser'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['comment'] = this.comment;
    data['message_date'] = this.messageDate;
    data['emisorUser'] = this.emisorUser;
    data['receptorUser'] = this.receptorUser;
    return data;
  }
}
