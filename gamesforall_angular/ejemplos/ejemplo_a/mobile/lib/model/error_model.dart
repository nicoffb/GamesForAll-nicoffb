class ApiErrorResponse {
  int? code;
  String? status;
  String? message;
  String? path;
  String? date;
  List<SubErrors>? subErrors;

  ApiErrorResponse(
      {this.code,
      this.status,
      this.message,
      this.path,
      this.date,
      this.subErrors});

  ApiErrorResponse.fromJson(Map<String, dynamic> json) {
    code = json['code'];
    status = json['status'];
    message = json['message'];
    path = json['path'];
    date = json['date'];
    if (json['subErrors'] != null) {
      subErrors = <SubErrors>[];
      json['subErrors'].forEach((v) {
        subErrors!.add(new SubErrors.fromJson(v));
      });
    }
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['code'] = this.code;
    data['status'] = this.status;
    data['message'] = this.message;
    data['path'] = this.path;
    data['date'] = this.date;
    if (this.subErrors != null) {
      data['subErrors'] = this.subErrors!.map((v) => v.toJson()).toList();
    }
    return data;
  }
}

class SubErrors {
  String? object;
  String? message;
  String? field;
  String? rejectedValue;

  SubErrors({this.object, this.message, this.field, this.rejectedValue});

  SubErrors.fromJson(Map<String, dynamic> json) {
    object = json['object'];
    message = json['message'];
    field = json['field'];
    rejectedValue = json['rejectedValue'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['object'] = this.object;
    data['message'] = this.message;
    data['field'] = this.field;
    data['rejectedValue'] = this.rejectedValue;
    return data;
  }
}
