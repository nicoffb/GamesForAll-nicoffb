export interface JwtUserResponse {
  id: string
  username: string
  avatar: string
  fullName: string
  createdAt: string
  token: string
  refreshToken: string
  enabled: boolean
  role: string
}
