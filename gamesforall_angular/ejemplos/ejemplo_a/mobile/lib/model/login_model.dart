class LoginRequest {
  String? username;
  String? password;

  LoginRequest({this.username, this.password});

  LoginRequest.fromJson(Map<String, dynamic> json) {
    username = json['username'];
    password = json['password'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['username'] = username;
    data['password'] = password;
    return data;
  }
}

class LoginResponse {
  String? id;
  String? username;
  String? nombre;
  List<String>? roles;
  String? createdAt;
  String? token;
  String? refreshToken;

  LoginResponse(
      {this.id,
      this.username,
      this.nombre,
      this.roles,
      this.createdAt,
      this.token,
      this.refreshToken});

  LoginResponse.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    username = json['username'];
    nombre = json['nombre'];
    roles = json['roles'].cast<String>();
    createdAt = json['createdAt'];
    token = json['token'];
    refreshToken = json['refreshToken'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['id'] = this.id;
    data['username'] = this.username;
    data['nombre'] = this.nombre;
    data['roles'] = this.roles;
    data['createdAt'] = this.createdAt;
    data['token'] = this.token;
    data['refreshToken'] = this.refreshToken;
    return data;
  }
}

class User {
  final String name;
  final String email;
  final String accessToken;
  final String refreshToken;
  final List<String> roles;

  User(
      {required this.name,
      required this.email,
      required this.accessToken,
      required this.roles,
      required this.refreshToken});

  @override
  String toString() => 'User { name: $name, email: $email}';
}
