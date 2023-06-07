import { ArtworkResponse } from "./artwork-response-list.interface"

export type ArtworkCategoryList = ArtworkCategory[]

export interface ArtworkCategory {
  name: string
  artworkResponseList: ArtworkResponse[]
}
