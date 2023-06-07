package com.salesianostriana.dam.flalleryapi.models.dtos.artwork;

import com.salesianostriana.dam.flalleryapi.models.Artwork;
import com.salesianostriana.dam.flalleryapi.models.Comment;
import com.salesianostriana.dam.flalleryapi.models.dtos.comment.CommentResponse;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArtworkResponse {

    private String name;
    private UUID uuid;
    private List<CommentResponse> comments;
    private String imgUrl;

    private boolean disponibleParaComprar;
    private String owner;
    private String categoryName;
    private String description;
    public static ArtworkResponse artworkToArtworkResponse(Artwork artwork){

        return ArtworkResponse.builder()
                .name(artwork.getName())
                .categoryName(artwork.getCategory().getName())
                .uuid(artwork.getIdArtwork())
                .disponibleParaComprar(artwork.isDisponibleParaComprar())
                .imgUrl(artwork.getImgUrl())
                .owner(artwork.getOwner())
                .description(artwork.getDescription())
                .comments(artwork.getComments().stream().map(CommentResponse::commentToCommentResponse).toList())
                .build();
    }
}
