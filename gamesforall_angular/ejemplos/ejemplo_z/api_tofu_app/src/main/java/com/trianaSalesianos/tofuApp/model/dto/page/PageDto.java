package com.trianaSalesianos.tofuApp.model.dto.page;

import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PageDto<T> {
    private List<T> content;
    private boolean last;
    private boolean first;
    private int totalPages;
    private Long totalElements;

    public PageDto(Page<T> page){
        this.content = page.getContent();
        this.last = page.isLast();
        this.first = page.isFirst();
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
    }
}
