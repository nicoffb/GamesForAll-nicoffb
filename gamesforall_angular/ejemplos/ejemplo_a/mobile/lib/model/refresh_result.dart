class RefreshTokenResponse {
  String? token;
  String? refreshToken;

  RefreshTokenResponse({this.token, this.refreshToken});

  RefreshTokenResponse.fromJson(Map<String, dynamic> json) {
    token = json['token'];
    refreshToken = json['refreshToken'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['token'] = this.token;
    data['refreshToken'] = this.refreshToken;
    return data;
  }
}
