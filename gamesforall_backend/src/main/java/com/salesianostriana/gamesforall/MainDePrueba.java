package com.salesianostriana.gamesforall;


import com.salesianostriana.gamesforall.product.model.Category;
import com.salesianostriana.gamesforall.product.model.Platform;
import com.salesianostriana.gamesforall.product.model.StateEnum;
import com.salesianostriana.gamesforall.product.model.Product;
import com.salesianostriana.gamesforall.product.repository.CategoryRepository;
import com.salesianostriana.gamesforall.product.repository.PlatformRepository;
import com.salesianostriana.gamesforall.product.repository.ProductRepository;
import com.salesianostriana.gamesforall.product.service.ProductService;
import com.salesianostriana.gamesforall.shipping.model.Shipping;
import com.salesianostriana.gamesforall.shipping.repository.ShippingRepository;
import com.salesianostriana.gamesforall.trade.model.Trade;
import com.salesianostriana.gamesforall.trade.repository.TradeRepository;
import com.salesianostriana.gamesforall.user.model.User;
import com.salesianostriana.gamesforall.user.model.UserRole;
import com.salesianostriana.gamesforall.user.repo.UserRepository;
import com.salesianostriana.gamesforall.message.model.Message;
import com.salesianostriana.gamesforall.message.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Component
@RequiredArgsConstructor
public class MainDePrueba {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MessageRepository messageRepository;
    private final ProductRepository productRepository;
    private final ProductService productService;

    private final PlatformRepository platformRepository;
    private final CategoryRepository categoryRepository;
    private final ShippingRepository shippingRepository;
    private final TradeRepository tradeRepository;


    @PostConstruct
    public void run() {

        User user1 = User.builder()
                .username("user")
                .password(passwordEncoder.encode("1234"))
                .fullName("Nicoffb")
                .genre("MALE")
                .address("Tomares, Sevilla")
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
                .address("La Macarena, Sevilla")
                .roles(new HashSet<>(Arrays.asList(UserRole.USER)))
                .accountNonExpired(true)
                .accountNonLocked(true)
                .enabled(true)
                .createdAt(LocalDateTime.of(1992,12,29,12,15))
                .lastPasswordChangeAt(LocalDateTime.of(2000,12,29,12,15))
                .build();

        User user3 = User.builder()
                .username("user3")
                .address("Sevilla Este")
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


        Message message1 = Message.builder()
                .comment("Estoy interesado en tu juego")
                .message_date(LocalDateTime.of(2023,05,06,12,00))
                .emisor(user1)
                .receptor(user2)
                .build();

        Message message2 = Message.builder()
                .comment("¿Cuanto estas dispuesto a pagar?")
                .message_date(LocalDateTime.of(2023,05,06,12,05))
                .emisor(user2)
                .receptor(user1)
                .build();

        Message message3 = Message.builder()
                .comment("5 euros menos")
                .message_date(LocalDateTime.of(2023,05,06,12,15))
                .emisor(user1)
                .receptor(user2)
                .build();

        Message message4 = Message.builder()
                .comment("Por 5 euros desayuno como un rey, lo siento pero no me interesa vendertelo.")
                .message_date(LocalDateTime.of(2023,05,06,12,45))
                .emisor(user2)
                .receptor(user1)
                .build();

        messageRepository.saveAll(List.of(message1,message2,message3,message4));

        Platform platform1 = new Platform(1L,"PS5");
        Platform platform2 = new Platform(2L,"XBOX");
        Platform platform3 = new Platform(3L,"SWITCH");
        Platform platform4 = new Platform(4L,"PC");

        platformRepository.saveAll(List.of(platform1,platform2,platform3,platform4));

        Category category1 = new Category("Horror");
        Category category2 = new Category("Aventura");
        Category category3 = new Category("Ciencia Ficción");
        Category category4 = new Category("Arcade");
        Category category5 = new Category("Simulación");
        Category category6 = new Category("Lucha");
        Category category7 = new Category("MMORPG");
        Category category8 = new Category("RPG");

        categoryRepository.saveAll(List.of(category1,category2,category3,category4,category5,category6,category7,category8));

        Product product1 = Product.builder()
                .title("God of War")
                .description("Lo vendo porque ya no me hace falta")
                .image("god-of-war.jpg")
                .price(30.00)
                .publication_date(LocalDateTime.of(2023,8,14,18,30))
                .shipping_available(true)
                .platform(platform1)
                .categories(new HashSet<>(Arrays.asList(category1,category2)))
                .state(StateEnum.SinAbrir)
                .user(user1)
                .sold(false)
                .build();

        Product product2 = Product.builder()
                .title("Zelda Breath of the Wild")
                .description("Le he echado 1400 horas y no me canso, lo quiero lejos de mi ")
                .image("breath-of-the-wild.jpg")
                .price(59.99)
                .publication_date(LocalDateTime.of(2022,8,14,18,30))
                .shipping_available(false)
                .platform(platform3)
                .categories(Set.of(category2, category8))
                .state(StateEnum.Usado)
                .user(user2)
                .sold(false)
                .build();

        Product product3 = Product.builder()
                .title("The Last of Us")
                .description("Una aventura grande y llena de emoción")
                .image("the-last-of-us.jpg")
                .price(10.00)
                .publication_date(LocalDateTime.of(2023,2,14,18,30))
                .shipping_available(true)
                .categories(Set.of(category1))
                .platform(platform4)
                .state(StateEnum.ComoNuevo)
                .sold(false)
                .user(user2)
                .build();

        Product product4 = Product.builder()
                .title("Smash Brosh Ultimate")
                .description("Me he pasado este juego 3 veces de lo bueno que es")
                .image("smash-bros.jpg")
                .price(39.99)
                .publication_date(LocalDateTime.of(2022,8,14,18,30))
                .platform(platform3)
                .state(StateEnum.Usado)
                .sold(false)
                .user(user2)
                .build();

        Product product5 = Product.builder()
                .title("The Witcher 3")
                .description("El juego que revolucionó el mundo abierto")
                .image("the-witcher-3.jpg")
                .price(9.99)
                .publication_date(LocalDateTime.of(2023,2,14,18,30))
                .platform(platform4)
                .state(StateEnum.SinAbrir)
                .sold(false)
                .user(user2)
                .build();

        Product product6 = Product.builder()
                .title("Ejemplo")
                .description("Ejemplo de descripción")
                .image("not-found.jpg")
                .price(9.99)
                .publication_date(LocalDateTime.of(2023,2,14,18,30))
                .platform(platform4)
                .state(StateEnum.SinAbrir)
                .sold(false)
                .user(user1)
                .build();

        Product product7 = Product.builder()
                .title("Ejemplo")
                .description("Ejemplo de descripción")
                .image("not-found.jpg")
                .price(9.99)
                .publication_date(LocalDateTime.of(2023,2,14,18,30))
                .platform(platform4)
                .state(StateEnum.SinAbrir)
                .sold(false)
                .user(user1)
                .build();

        Product product8 = Product.builder()
                .title("Ejemplo")
                .description("Ejemplo de descripción")
                .image("not-found.jpg")
                .price(9.99)
                .publication_date(LocalDateTime.of(2023,2,14,18,30))
                .platform(platform4)
                .state(StateEnum.SinAbrir)
                .sold(false)
                .user(user1)
                .build();

        Product product9 = Product.builder()
                .title("Ejemplo")
                .description("Ejemplo de descripción")
                .image("not-found.jpg")
                .price(9.99)
                .publication_date(LocalDateTime.of(2023,2,14,18,30))
                .platform(platform4)
                .state(StateEnum.SinAbrir)
                .sold(false)
                .user(user1)
                .build();

        Product product10 = Product.builder()
                .title("Ejemplo")
                .description("Ejemplo de descripción")
                .image("not-found.jpg")
                .price(9.99)
                .publication_date(LocalDateTime.of(2023,2,14,18,30))
                .platform(platform4)
                .state(StateEnum.SinAbrir)
                .sold(false)
                .user(user1)
                .build();

        productRepository.saveAll(List.of(product1,product2,product3,product4,product5, product6, product7, product8,product9,product10));
        productService.addProductToFavorites(user1.getId(),product3.getId());
        productService.addProductToFavorites(user1.getId(),product2.getId());

        Shipping shipping1 = Shipping.builder()
                .preparation_period("2023-05-04 --- 2023-05-06")
                .sending_date(LocalDateTime.of(2023,5,6,18,30))
                .delivery_date(LocalDateTime.of(2023,5,20,18,30))
                .location("la macarena sevilla")
                .build();

        shippingRepository.saveAll(List.of(shipping1));



        Trade trade1 = Trade.builder()
                .buyer(user1)
                .seller(user2)
                .finalPrice(20)
                .score(5.0)
                .review("buen vendedor")
                .sending(true)
                .product(product1)
                .shipping(shipping1)
                .build();

        Trade trade2 = Trade.builder()
                .buyer(user2)
                .seller(user1)
                .finalPrice(10)
                .score(2.0)
                .review("vendedor inútil")
                .sending(false)
                .product(product2)
                .build();

        Trade trade3 = Trade.builder()
                .buyer(user3)
                .seller(user1)
                .finalPrice(20)
                .score(3.0)
                .review("regulero")
                .sending(true)
                .product(product3)
               //esto da error
                // al tener dos trades con el mismo shipping
                //al tener dos trades con el mismo producto
                .build();

        tradeRepository.saveAll(List.of(trade1,trade2,trade3));

//        Chat chat = Chat.builder()
//                .id(new ChatPK(user1.getId(),user2.getId()))
//                .messages(List.of(message1,message2,message3,message4))
//                .build();
//        chatRepository.save(chat);

    }
}
