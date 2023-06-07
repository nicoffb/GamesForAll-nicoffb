package com.trianaSalesianos.tofuApp.service;

import com.trianaSalesianos.tofuApp.exception.CategoryNotFoundException;
import com.trianaSalesianos.tofuApp.exception.TypeNotFoundException;
import com.trianaSalesianos.tofuApp.model.Category;
import com.trianaSalesianos.tofuApp.model.Type;
import com.trianaSalesianos.tofuApp.model.dto.category.CategoryRequest;
import com.trianaSalesianos.tofuApp.model.dto.category.CategoryResponse;
import com.trianaSalesianos.tofuApp.model.dto.page.PageDto;
import com.trianaSalesianos.tofuApp.model.dto.type.TypeRequest;
import com.trianaSalesianos.tofuApp.model.dto.type.TypeResponse;
import com.trianaSalesianos.tofuApp.repository.TypeRepository;
import com.trianaSalesianos.tofuApp.search.spec.GenericSpecificationBuilder;
import com.trianaSalesianos.tofuApp.search.util.SearchCriteria;
import com.trianaSalesianos.tofuApp.search.util.SearchCriteriaExtractor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TypeService {
    private final TypeRepository typeRepository;

    public PageDto<TypeResponse> search(List<SearchCriteria> params, Pageable pageable){
        GenericSpecificationBuilder<Type> typeSpecificationBuilder = new GenericSpecificationBuilder<>(params, Type.class);

        Specification<Type> spec = typeSpecificationBuilder.build();
        Page<TypeResponse> typeResponsePage = typeRepository.findAll(spec,pageable).map(TypeResponse::fromType);

        return new PageDto<>(typeResponsePage);
    }
    public PageDto<TypeResponse> getAllBySearch(String search, Pageable pageable) {
        List<SearchCriteria> params = SearchCriteriaExtractor.extractSearchCriteriaList(search);
        PageDto<TypeResponse> res = search(params,pageable);

        if (res.getContent().isEmpty()) throw new TypeNotFoundException();

        return res;
    }

    public Type findById(UUID id) {
        return typeRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException());
    }

    public Type save(Type type) {
        return typeRepository.save(type);
    }

    public TypeResponse update(TypeRequest typeRequest, UUID id) {
        Type t = typeRepository.findById(id)
                .orElseThrow(() -> new TypeNotFoundException());

        if(!typeRequest.getName().isEmpty())
            t.setName(typeRequest.getName());

        return TypeResponse.fromType(typeRepository.save(t));
    }


    public void delete(UUID id) {
        Type t = findById(id);

        typeRepository.delete(t);
    }

    public boolean typeExists(String s) {
        return typeRepository.existsByName(s);
    }
}
