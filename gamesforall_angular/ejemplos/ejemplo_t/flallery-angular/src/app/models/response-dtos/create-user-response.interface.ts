export type UserList = UserResponse[]

export interface UserResponse {
  id: string
  username: string
  avatar: string
  fullName: string
  createdAt: string
  enabled: boolean
  role: string
}
