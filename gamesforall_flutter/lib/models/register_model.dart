class RegisterRequest {
  String username;
  String password;
  String verifyPassword;
  String email;

  RegisterRequest(
      {required this.username,
      required this.password,
      required this.verifyPassword,
      required this.email});

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['username'] = username;
    data['password'] = password;
    data['verifyPassword'] = verifyPassword;
    data['email'] = email;
    return data;
  }
}

class RegisterResponse {
  String? id;
  String? username;
  String? fullName;
  String? email;
  String? createdAt;

  RegisterResponse({this.id, this.username, this.email, this.createdAt});

  RegisterResponse.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    username = json['username'];
    fullName = json['fullName'];
    email = json['email'];
    createdAt = json['createdAt'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['id'] = this.id;
    data['username'] = this.username;
    data['fullName'] = this.fullName;
    data['email'] = this.email;
    data['createdAt'] = this.createdAt;
    return data;
  }
}
