//RESPONSES 
export type UserList = UserResponse[]
export interface UserResponse {
  id: string
  username: string
  avatar: string
  fullName: string
  userScore: number 
  createdAt: string
  token: string
  refreshToken: string
  enabled: boolean
  address: string
}

export interface UserDetailsResponse {
username: string;
avatar: string;
fullname: string;
email: string;
description: string;

}




//REQUESTS 
export interface LoginRequest {
  username: string;
  password: string;
}

export interface EditUserRequest {
  id: string
  avatar: string
  fullName: string
}

export interface CreateUserRequest {
  username: string;
  password: string;
  verifyPassword: string;
  adress: string;
  avatar: string;
  fullName: string;
}

export interface ChangePasswordRequest {
  oldPassword: string;
  newPassword: string;
  verifyNewPassword: string;
}

export interface RefreshTokenRequest {
  refreshToken: string;
}


