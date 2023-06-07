export interface PageDTO<TData> {
    contenido: TData[]
    numeroResultados: number
    numeroPaginas: number
    paginaActual: number
  }