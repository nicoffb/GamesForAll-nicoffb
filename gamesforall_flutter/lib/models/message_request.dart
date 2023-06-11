class MessageRequest {
  String? comment;

  MessageRequest({this.comment});

  MessageRequest.fromJson(Map<String, dynamic> json) {
    comment = json['comment'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['comment'] = this.comment;
    return data;
  }
}
