package com.trianaSalesianos.tofuApp.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Parameter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Entity
@Table(name = "user_entity")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter @ToString
@Builder
public class User implements UserDetails {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator",
            parameters = {
                    @Parameter(
                            name = "uuid_gen_strategy_class",
                            value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
                    )
            }
    )
    @Column(columnDefinition = "uuid")
    private UUID id;

    @NaturalId
    @Column(unique = true, updatable = false)
    private String username;
    private String fullname;
    private String password;
    @NaturalId
    @Column(unique = true, updatable = false)
    private String email;
    @Builder.Default
    private String avatar = "default_user_avatar.png";
    @Builder.Default
    private LocalDate birthday = LocalDate.of(2000,1,1);

    @Builder.Default
    private String description = "";


    @ToString.Exclude
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Recipe> recipes = new ArrayList<>();

    @ToString.Exclude
    @ManyToMany(cascade = CascadeType.PERSIST, mappedBy = "following")
    @Builder.Default
    private List<User> followers = new ArrayList<>();

    @ToString.Exclude
    @JoinTable(name = "followers",
    joinColumns = {@JoinColumn(name = "user_id")},
    inverseJoinColumns = {@JoinColumn(name = "follower_id")})
    @ManyToMany(cascade = CascadeType.PERSIST)
    @Builder.Default
    private List<User> following = new ArrayList<>();

    @ToString.Exclude
    @ManyToMany
    @JoinTable(joinColumns = @JoinColumn(name = "user_id",
            foreignKey = @ForeignKey(name="FK_FAVORITE_USER")),
            inverseJoinColumns = @JoinColumn(name = "recipe_id",
                    foreignKey = @ForeignKey(name="FK_FAVORITE_RECIPE")),
            name = "favorites"
    )
    @Builder.Default
    private List<Recipe> favorites = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "author")
    @Builder.Default
    private List<Ingredient> ingredients = new ArrayList<>();
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<DietDays> dietDays = new ArrayList<>();
    @CreatedDate
    private LocalDateTime createdAt;
    @Builder.Default
    private LocalDateTime lastPasswordChangeAt = LocalDateTime.now();
    @Builder.Default
    private boolean accountNonExpired = true;
    @Builder.Default
    private boolean accountNonLocked = true;
    @Builder.Default
    private boolean credentialsNonExpired = true;
    @Builder.Default
    private boolean enabled = true;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<UserRole> roles;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> "ROLE_" + role)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    //===============================
    //METODOS PRE-REMOVE Y AUXILIARES
    //===============================

    @PreRemove
    public void setNull(){
        ingredients.forEach(a -> a.setAuthor(null));

        following.forEach(this::removeFromFollowing);
        followers.forEach(a -> a.getFollowing().remove(this));

        favorites.forEach(this::removeFromFavorite);
    }

    public void favoriteRecipe(Recipe r) {
        this.favorites.add(r);
        r.getFavoritedBy().add(this);
    }

    public void removeFromFavorite(Recipe r) {
        this.favorites.remove(r);
        r.getFavoritedBy().remove(this);
    }


    public void follow(User u) {
        this.following.add(u);
        u.getFollowers().add(this);
    }

    public void removeFromFollowing(User u) {
        this.following.remove(u);
        u.getFollowers().remove(this);
    }
}
