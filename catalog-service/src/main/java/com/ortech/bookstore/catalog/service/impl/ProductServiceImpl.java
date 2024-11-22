package com.ortech.bookstore.catalog.service.impl;

import com.ortech.bookstore.catalog.config.ApplicationProperties;
import com.ortech.bookstore.catalog.domain.PagedResult;
import com.ortech.bookstore.catalog.domain.Product;
import com.ortech.bookstore.catalog.domain.ProductMapper;
import com.ortech.bookstore.catalog.repository.ProductRepository;
import com.ortech.bookstore.catalog.service.ProductService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ApplicationProperties applicationProperties;

    @Override
    public PagedResult<Product> getAllProducts(int pageNumber) {
        Sort sort = Sort.by("name").ascending();
        pageNumber = pageNumber <= 0 ? 0 : pageNumber - 1;
        PageRequest pageRequest = PageRequest.of(pageNumber, applicationProperties.pageSize(), sort);

        Page<Product> page = productRepository.findAll(pageRequest).map(ProductMapper::toProduct);

        return new PagedResult<>(
                page.getContent(),
                page.getTotalElements(),
                page.getNumber() + 1,
                page.getTotalPages(),
                page.isFirst(),
                page.isLast(),
                page.hasNext(),
                page.hasPrevious());
    }

    @Override
    public Optional<Product> getProductByCode(String code) {
        return productRepository.findByCode(code).map(ProductMapper::toProduct);
    }
}
