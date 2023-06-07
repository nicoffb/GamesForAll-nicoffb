import { DireccionResponse } from "./direccionResponse";
import { UserResponse } from "./userResponse";

export interface VentaResponseOverview {

    gastoEnvio: number;

    totalPedido: number;

    total: number;

    fecha: Date;

    comprador: UserResponse;

    direccionEnvio: DireccionResponse;

}