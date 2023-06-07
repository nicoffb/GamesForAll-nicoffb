export interface Venta {
  artworkName: string;
  precio: number;
  fechaVenta: string;
  usernameComprador: string;
  usernameVendedor: string;
}

export interface VentaResponse {
  content: Venta[];
  totalPages: number;
  totalElements: number;
  pageSize: number;
}

export interface PageDto<T> {
  content: T[];
  totalPages: number;
  totalElements: number;
  pageSize: number;
}

