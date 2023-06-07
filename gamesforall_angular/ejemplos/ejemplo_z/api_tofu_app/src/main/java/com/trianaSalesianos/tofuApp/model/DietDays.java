package com.trianaSalesianos.tofuApp.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class DietDays {
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
    private UUID id;
    private LocalDate day;

    @ManyToMany
    @JoinTable(
            name = "diet_days_recipes",
            joinColumns = @JoinColumn(name = "diet_days_id", foreignKey = @ForeignKey(name="FK_RECIPE_DIET_DAYS")),
            inverseJoinColumns = @JoinColumn(name = "recipe_id", foreignKey = @ForeignKey(name="FK_DIET_DAYS_RECIPE"))
    )
    @Builder.Default
    private List<Recipe> recipes =  new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    //===============================
    //METODOS PRE-REMOVE Y AUXILIARES
    //===============================
    @PreRemove
    public void removeFollows(){
        this.recipes.clear();
    }
}
