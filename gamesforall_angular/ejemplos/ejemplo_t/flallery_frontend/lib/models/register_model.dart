class UserRegisterRequest {
  String? username;
  String? password;
  String? verifyPassword;
  String? avatar;
  String? fullName;

  UserRegisterRequest(
      {this.username,
      this.password,
      this.verifyPassword,
      this.avatar,
      this.fullName});

  UserRegisterRequest.fromJson(Map<String, dynamic> json) {
    username = json['username'];
    password = json['password'];
    verifyPassword = json['verifyPassword'];
    avatar = json['avatar'];
    fullName = json['fullName'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['username'] = this.username;
    data['password'] = this.password;
    data['verifyPassword'] = this.verifyPassword;
    data['avatar'] = this.avatar;
    data['fullName'] = this.fullName;
    return data;
  }
}

class UserRegisterResponse {
  String? id;
  String? username;
  String? avatar;
  String? fullName;
  String? createdAt;

  UserRegisterResponse(
      {this.id, this.username, this.avatar, this.fullName, this.createdAt});

  UserRegisterResponse.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    username = json['username'];
    avatar = json['avatar'];
    fullName = json['fullName'];
    createdAt = json['createdAt'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['id'] = this.id;
    data['username'] = this.username;
    data['avatar'] = this.avatar;
    data['fullName'] = this.fullName;
    data['createdAt'] = this.createdAt;
    return data;
  }
}