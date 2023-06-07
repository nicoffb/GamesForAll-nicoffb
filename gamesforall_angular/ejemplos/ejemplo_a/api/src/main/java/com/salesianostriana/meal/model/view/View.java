package com.salesianostriana.meal.model.view;

public class View {

    public static interface RestauranteView{
        public static interface RestauranteDetailView{}
        public static interface RestauranteGenericView{}
    }

    public static interface PlatoView{
        public static interface PlatoDetailView{}
        public static interface PlatoGenericView{}
    }

    public static interface VentaView {
        public static interface VentaOverView{}
        public static interface VentaDetailView{}

    }

}
