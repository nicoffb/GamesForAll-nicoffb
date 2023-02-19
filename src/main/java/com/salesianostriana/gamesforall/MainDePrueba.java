package com.salesianostriana.gamesforall;

import com.salesianostriana.gamesforall.product.model.Product;
import com.salesianostriana.gamesforall.product.repository.ProductRepository;
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

    @PostConstruct
    public void run() {

        User user1 = User.builder()
                .username("user1")
                .password(passwordEncoder.encode("password1"))
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
                .image("hello.jpg")
                .publication_date(LocalDateTime.of(2023,8,14,18,30))
                .category("Aventura")
                .user(user1)
                .build();

        productRepository.save(product1);

    }
}
