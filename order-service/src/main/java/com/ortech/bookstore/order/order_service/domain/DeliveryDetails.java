package com.ortech.bookstore.order.order_service.domain;

import jakarta.validation.constraints.NotBlank;

public record DeliveryDetails(
        @NotBlank(message = "addressLine1 Can't be empty") String addressLine1,
        @NotBlank(message = "addressLine2 Can't be empty") String addressLine2,
        @NotBlank(message = "addressCity Can't be empty") String addressCity,
        @NotBlank(message = "addressState can't be empty") String addressState,
        @NotBlank(message = "addressZipCode can't be empty") String addressZipCode,
        @NotBlank(message = "addressCountry can't be empty") String addressCountry) {}
