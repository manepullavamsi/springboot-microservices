package com.ortech.bookstore.catalog.service;

import com.ortech.bookstore.catalog.domain.PagedResult;
import com.ortech.bookstore.catalog.domain.Product;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {

    public PagedResult<Product> getAllProducts(int pageNumber);

    public Optional<Product> getProductByCode(String code);
}
