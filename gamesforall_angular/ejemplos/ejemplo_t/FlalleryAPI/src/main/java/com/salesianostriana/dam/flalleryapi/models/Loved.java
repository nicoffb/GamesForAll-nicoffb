package com.salesianostriana.dam.flalleryapi.models;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name="loved")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Loved {

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
    private UUID id_loved;

    private String lover;
    @ManyToOne
    @JoinColumn(name = "lovedArtwork",
            foreignKey = @ForeignKey(name="LOVEDARTWORK_ID_FK"))
    private Artwork lovedArtwork;
}
