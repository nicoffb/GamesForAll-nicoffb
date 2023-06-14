class UserRequest {
  String? username;
  String? password;
  String? verifyPassword;
  String? fullName;
  String? avatar;
  String? address;

  UserRequest(
      {this.username,
      this.password,
      this.verifyPassword,
      this.fullName,
      this.avatar,
      this.address});

  UserRequest.fromJson(Map<String, dynamic> json) {
    username = json['username'];
    password = json['password'];
    verifyPassword = json['verifyPassword'];
    fullName = json['fullName'];
    avatar = json['avatar'];
    address = json['address'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['username'] = this.username;
    data['password'] = this.password;
    data['verifyPassword'] = this.verifyPassword;
    data['fullName'] = this.fullName;
    data['avatar'] = this.avatar;
    data['address'] = this.address;
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
