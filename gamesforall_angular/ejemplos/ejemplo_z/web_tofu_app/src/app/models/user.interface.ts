// ================ REQUESTS ================
export interface LoginRequest {
  username: string;
  password: string;
}

export interface EditUserRequest {
  email: string;
  fullname: string;
  birthday: string;
  description: string;
}

export interface CreateUserRequest {
  username: string;
  password: string;
  verifyPassword: string;
  email: string;
  verifyEmail: string;
  fullname: string;
}

export interface ChangePasswordRequest {
  oldPassword: string;
  newPassword: string;
  verifyNewPassword: string;
}

export interface RefreshTokenRequest {
  refreshToken: string;
}


// ================ RESPONSES ================
export interface JwtResponse{
    token: string;
    refreshToken: string;
}
export interface JwtUserResponse {
  username: string;
  avatar: string;
  fullname: string;
  description: string;
  token: string;
  refreshToken: string;
}

export interface UserResponse {
  id: string;
  username: string;
  avatar: string;
  fullname: string;
  description: string;
}

export interface UserDetailsResponse {
  username: string;
  avatar: string;
  fullname: string;
  email: string;
  description: string;
  birthday: string;
  createdAt: string;
  nfollowers: number;
  nfollowing: number;
}
