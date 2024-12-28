package com.ortech.bookstore.catalog.controller;

import com.ortech.bookstore.catalog.domain.PagedResult;
import com.ortech.bookstore.catalog.domain.Product;
import com.ortech.bookstore.catalog.exception.ProductNotFoundException;
import com.ortech.bookstore.catalog.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/products")
@RequiredArgsConstructor
class ProductController {

    private final ProductService productService;

    @GetMapping("/all")
    ResponseEntity<PagedResult<Product>> getProductsList(
            @RequestParam(name = "pageNumber", required = false) int pageNumber) {
        return ResponseEntity.ok(productService.getAllProducts(pageNumber));
    }

    @GetMapping("/{code}")
    ResponseEntity<Product> getProductByCode(@PathVariable(name = "code") String code) {
        //        sleep();
        return productService
                .getProductByCode(code)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> ProductNotFoundException.forCode(code));
    }

    void sleep() {
        try {
            Thread.sleep(6000);
        } catch (Exception ex) {
            System.out.println("Sleep:  " + ex.getMessage());
        }
    }
}
