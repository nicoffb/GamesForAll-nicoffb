package com.salesianostriana.dam.flalleryapi;

import com.salesianostriana.dam.flalleryapi.models.Artwork;
import com.salesianostriana.dam.flalleryapi.models.ArtworkCategory;
import com.salesianostriana.dam.flalleryapi.models.Venta;
import com.salesianostriana.dam.flalleryapi.services.ArtworkCategoryService;
import com.salesianostriana.dam.flalleryapi.services.ArtworkService;
import com.salesianostriana.dam.flalleryapi.services.VentaService;
import jdk.jfr.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.UUID;


@Component
@RequiredArgsConstructor
public class TestModel {

    private final ArtworkService artworkService;
    private final ArtworkCategoryService artworkCategoryService;

    private final VentaService ventaService;

    @PostConstruct
    public void run() {

        ArtworkCategory graffiti = ArtworkCategory.builder().name("Graffiti").build();
        ArtworkCategory oleos = ArtworkCategory.builder().name("Óleos").build();

        artworkCategoryService.save(graffiti);
        artworkCategoryService.save(oleos);

        Artwork artwork1 = Artwork.builder()
                .description("Drama|Mystery")
                .imgUrl("Jacqueline-con-sombrero-ok-495x600-mqh4hwg1zvsxomp9zx0gsxvu1t88tjxyhy9iz2gnpw.jpg")
                .name("Lost Memories")
                .owner("mcampos")
                .category(graffiti)
                .disponibleParaComprar(true)
                .build();

        Artwork artwork2 = Artwork.builder()
                .description("Action|Thriller")
                .imgUrl("paisajes-coloridos-arte-moderno_1.jpg")
                .name("The Last Stand")
                .owner("mcampos")
                .category(graffiti)
                .disponibleParaComprar(true)
                .build();

        Artwork artwork3 = Artwork.builder()
                .description("Adventure|Fantasy")
                .imgUrl("PICASSO-Desnudo-acostado-800x600-m6fc19o6xm3jooeva220xmjyur1fpgh2cp2afnm2ok.jpg")
                .name("Realm of Legends")
                .owner("mcampos")
                .category(oleos)
                .disponibleParaComprar(true)
                .build();

        Artwork artwork4 = Artwork.builder()
                .description("Romance|Drama")
                .imgUrl("pintura2-1-e1538148276170.jpg")
                .name("Whispered Promises")
                .owner("mcampos")
                .category(graffiti)
                .disponibleParaComprar(true)
                .build();

        Artwork artwork5 = Artwork.builder()
                .description("Comedy")
                .imgUrl("R5DKCO5IV5A7PANZKPAZNRVNJU.jpg")
                .name("Laugh Out Loud")
                .owner("mcampos")
                .category(oleos)
                .disponibleParaComprar(true)
                .build();

        Artwork artwork6 = Artwork.builder()
                .description("Documentary|Biography")
                .imgUrl("origen-del-arte-abstracto-e1635552766306.jpg")
                .name("Untold Legacies")
                .owner("mcampos")
                .category(graffiti)
                .disponibleParaComprar(true)
                .build();

        Artwork artwork7 = Artwork.builder()
                .description("Animation|Family")
                .imgUrl("14067131_img-20210110-144801-ed.jpg")
                .name("The Magical Adventure")
                .owner("mcampos")
                .category(oleos)
                .disponibleParaComprar(true)
                .build();

        Artwork artwork8 = Artwork.builder()
                .description("Sci-Fi|Thriller")
                .imgUrl("770x420-amantes.jpg")
                .name("Beyond the Unknown")
                .owner("mcampos")
                .category(graffiti)
                .disponibleParaComprar(true)
                .build();

        Artwork artwork9 = Artwork.builder()
                .description("Action|Adventure")
                .imgUrl("el_nacimiento_de_venus_por_sandro_botticelli-1024x639.jpg")
                .name("The Lost Treasure")
                .owner("mcampos")
                .category(oleos)
                .disponibleParaComprar(true)
                .build();

        Artwork artwork10 = Artwork.builder()
                .description("Drama|Romance")
                .imgUrl("mujer-escondida-actual.jpg")
                .name("Fleeting Moments")
                .owner("mcampos")
                .category(graffiti)
                .disponibleParaComprar(true)
                .build();

        artworkService.add(artwork1);
        artworkService.add(artwork2);
        artworkService.add(artwork3);
        artworkService.add(artwork4);
        artworkService.add(artwork5);
        artworkService.add(artwork6);
        artworkService.add(artwork7);
        artworkService.add(artwork8);
        artworkService.add(artwork9);
        artworkService.add(artwork10);


        Venta venta1 = Venta.builder().usernameComprador("lmlopez").usernameVendedor(artwork1.getOwner()).artwork(artwork1).precio(99.0).build();
        Venta venta2 = Venta.builder().usernameComprador("lmlopez").usernameVendedor(artwork2.getOwner()).artwork(artwork2).precio(992.0).build();
        Venta venta3 = Venta.builder().usernameComprador("lmlopez").usernameVendedor(artwork3.getOwner()).artwork(artwork3).precio(9.0).build();
        Venta venta4 = Venta.builder().usernameComprador("lmlopez").usernameVendedor(artwork4.getOwner()).artwork(artwork4).precio(79.0).build();
        Venta venta5 = Venta.builder().usernameComprador("lmlopez").usernameVendedor(artwork5.getOwner()).artwork(artwork5).precio(549.0).build();
        Venta venta6 = Venta.builder().usernameComprador("lmlopez").usernameVendedor(artwork6.getOwner()).artwork(artwork6).precio(9355.0).build();
        Venta venta7 = Venta.builder().usernameComprador("lmlopez").usernameVendedor(artwork7.getOwner()).artwork(artwork7).precio(67.0).build();
        Venta venta8 = Venta.builder().usernameComprador("lmlopez").usernameVendedor(artwork8.getOwner()).artwork(artwork8).precio(234.0).build();

        ventaService.add(venta1);
        ventaService.add(venta2);
        ventaService.add(venta3);
        ventaService.add(venta4);
        ventaService.add(venta5);
        ventaService.add(venta6);
        ventaService.add(venta7);
        ventaService.add(venta8);


    }
}

