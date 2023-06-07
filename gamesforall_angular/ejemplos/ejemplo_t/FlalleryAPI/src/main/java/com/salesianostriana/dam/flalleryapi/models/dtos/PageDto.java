package com.salesianostriana.dam.flalleryapi.models.dtos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;


@Getter
@Setter
public class PageDto<T> {

    private List<T> content;
    private int totalPages;
    private Long totalElements;
    private int pageSize;

    public PageDto(Page<T> page) {
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.content = page.getContent();
        this.pageSize = page.getNumberOfElements();
    }
}
