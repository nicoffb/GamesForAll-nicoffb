package com.salesianostriana.meal.security.user;

import com.salesianostriana.meal.model.Plato;
import com.salesianostriana.meal.model.Restaurante;
import com.salesianostriana.meal.model.Valoracion;
import com.salesianostriana.meal.model.converter.StringListConverter;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name="user_entity")
@EntityListeners(AuditingEntityListener.class)
@NamedEntityGraph(name = "owner-con-administraciones", attributeNodes = @NamedAttributeNode(value = "administra"))
public class User implements UserDetails {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(
                            name = "uuid_gen_strategy_class",
                            value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
                    )
            }
    )
    @Column(columnDefinition = "uuid")
    private UUID id;

    private String nombre;
    private String email;
    private String username;
    private String password;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Valoracion> valoraciones = new ArrayList<>();

    @OneToMany(mappedBy = "restaurantAdmin", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Restaurante> administra = new ArrayList<>();

    @Builder.Default
    private boolean accountNonExpired = true;
    @Builder.Default
    private boolean accountNonLocked = true;
    @Builder.Default
    private boolean credentialsNonExpired = true;
    @Builder.Default
    private boolean enabled = true;

    // TODO Adaptar a converter
    //@Convert(converter = StringListConverter.class)
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Roles> roles;

    @CreatedDate
    private LocalDateTime createdAt;

    @Builder.Default
    private LocalDateTime lastPasswordChangeAt = LocalDateTime.now();



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> "ROLE_" + role)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
    

}
