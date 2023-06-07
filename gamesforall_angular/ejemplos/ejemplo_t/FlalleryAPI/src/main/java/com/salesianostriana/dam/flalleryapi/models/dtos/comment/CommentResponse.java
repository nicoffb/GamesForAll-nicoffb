package com.salesianostriana.dam.flalleryapi.models.dtos.comment;


import com.salesianostriana.dam.flalleryapi.models.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CommentResponse {


    private String text;
    private String writer;

    public static CommentResponse commentToCommentResponse(Comment c){

        return CommentResponse.builder()
                .text(c.getText())
                .writer(c.getWriter())
                .build();

    }
}
