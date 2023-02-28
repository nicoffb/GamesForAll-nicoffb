package com.salesianostriana.gamesforall;

import com.salesianostriana.gamesforall.product.model.PlatformEnum;
import com.salesianostriana.gamesforall.product.model.Product;
import com.salesianostriana.gamesforall.product.repository.ProductRepository;
import com.salesianostriana.gamesforall.product.service.ProductService;
import com.salesianostriana.gamesforall.security.PasswordEncoderConfig;
import com.salesianostriana.gamesforall.user.model.User;
import com.salesianostriana.gamesforall.user.model.UserRole;
import com.salesianostriana.gamesforall.user.repo.UserRepository;
import com.salesianostriana.gamesforall.user.service.UserService;
import com.salesianostriana.gamesforall.valoration.model.Valoration;
import com.salesianostriana.gamesforall.valoration.model.ValorationPK;
import com.salesianostriana.gamesforall.valoration.repository.ValorationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;


@Component
@RequiredArgsConstructor
public class MainDePrueba {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ValorationRepository valorationRepository;
    private final ProductRepository productRepository;
    private final ProductService productService;

    @PostConstruct
    public void run() {

        User user1 = User.builder()
                .username("user")
                .password(passwordEncoder.encode("1234"))
                .fullName("Nicoffb")
                .genre("MALE")
                .roles(new HashSet<>(Arrays.asList(UserRole.USER,UserRole.ADMIN)))
                .accountNonExpired(true)
                .accountNonLocked(true)
                .enabled(true)
                .createdAt(LocalDateTime.of(1993,12,29,12,15))
                .lastPasswordChangeAt(LocalDateTime.of(1993,12,29,12,15))
                .build();

        User user2 = User.builder()
                .username("user2")
                .password(passwordEncoder.encode("password2"))
                .fullName("Filomeno")
                .genre("MALE")
                .roles(new HashSet<>(Arrays.asList(UserRole.USER)))
                .accountNonExpired(true)
                .accountNonLocked(true)
                .enabled(true)
                .createdAt(LocalDateTime.of(1992,12,29,12,15))
                .lastPasswordChangeAt(LocalDateTime.of(2000,12,29,12,15))
                .build();

        User user3 = User.builder()
                .username("user3")
                .password(passwordEncoder.encode("password3"))
                .fullName("Mortadelo")
                .genre("MALE")
                .roles(new HashSet<>(Arrays.asList(UserRole.USER)))
                .accountNonExpired(true)
                .accountNonLocked(true)
                .enabled(true)
                .createdAt(LocalDateTime.of(1992,12,29,12,15))
                .lastPasswordChangeAt(LocalDateTime.of(2000,12,29,12,15))
                .build();

        userRepository.saveAll(List.of(user1,user2,user3));


        Valoration valoration1 = Valoration.builder()
                .id(new ValorationPK(user1.getId(),user2.getId()))
                .score(6.7)
                .review("Juegazo como la copa de un pino")
                .reviewedUser(user2)
                .reviewer(user1)
                .build();

        Valoration valoration2 = Valoration.builder()
                .id(new ValorationPK(user2.getId(),user1.getId()))
                .score(9.3)
                .review("El peor juego que he jugado nunca")
                .reviewedUser(user1)
                .reviewer(user2)
                .build();

        valorationRepository.saveAll(List.of(valoration1,valoration2));

        Product product1 = Product.builder()
                .title("God of War")
                .description("Un juego de aventura llena de emoción")
                .image("god-of-war.jpg")
                .price(30.00)
                .publication_date(LocalDateTime.of(2023,8,14,18,30))
                .category("Aventura")
                .platform(PlatformEnum.PS5)
                .user(user1)
                .build();

        Product product2 = Product.builder()
                .title("Zelda Breath of the Wild")
                .description("El juego que revolucionó el mundo abierto")
                .image("breath-of-the-wild.jpg")
                .price(59.99)
                .publication_date(LocalDateTime.of(2022,8,14,18,30))
                .category("Mundo abierto")
                .platform(PlatformEnum.SWITCH)
                .user(user2)
                .build();

        Product product3 = Product.builder()
                .title("The Last of Us")
                .description("Me he pasado este juego 3 veces de lo bueno que es")
                .image("the-last-of-us.jpg")
                .price(10)
                .publication_date(LocalDateTime.of(2023,2,14,18,30))
                .category("Supervivencia")
                .platform(PlatformEnum.PC)
                .user(user2)
                .build();

        Product product4 = Product.builder()
                .title("Smash Brosh Ultimate")
                .description("Lo vendo porque ya no me hace falta")
                .image("smash-bros.jpg")
                .price(59.99)
                .publication_date(LocalDateTime.of(2022,8,14,18,30))
                .category("Lucha")
                .platform(PlatformEnum.SWITCH)
                .user(user2)
                .build();

        Product product5 = Product.builder()
                .title("The Witcher 3")
                .description("Le he echado 1400 horas y no me canso, lo quiero lejos de mi")
                .image("the-witcher-3.jpg")
                .price(10)
                .publication_date(LocalDateTime.of(2023,2,14,18,30))
                .category("RPG")
                .platform(PlatformEnum.PC)
                .user(user2)
                .build();



        productRepository.saveAll(List.of(product1,product2,product3,product4,product5));
        productService.addProductToFavorites(user1.getId(),product3.getId());
        productService.addProductToFavorites(user1.getId(),product2.getId());

    }
}
