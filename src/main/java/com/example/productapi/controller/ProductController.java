
package com.example.productapi.controller;

import com.example.productapi.api.ProductApi;
//import com.example.productapi.dto.ErrorResponse;
import com.example.productapi.exception.CategoryNotFoundException;
import com.example.productapi.facade.ProductFacade;
import com.example.productapi.model.ProductRequest;
import com.example.productapi.model.ProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.Validator;
import java.util.Set;

@RestController
//@Validated
public class ProductController implements ProductApi {

    private final ProductFacade productFacade;
    //private final ValidationService validationService;

    private final Validator validator;

    @Autowired
    public ProductController(ProductFacade productFacade, Validator validator) {
        this.productFacade = productFacade;
        //this.validationService = validationService;
        this.validator = validator;
    }


    @Override
    public Mono<ResponseEntity<ProductResponse>> createProduct(@RequestBody Mono<ProductRequest> productRequest, ServerWebExchange exchange) {
        return productRequest
                .flatMap(this::validateRequest)
                .flatMap(productFacade::createProduct)
                .map(ResponseEntity::ok);
    }


    private <T> Mono<T> validateRequest(T request) {
        Set<ConstraintViolation<T>> violations = validator.validate(request);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        return Mono.just(request);
    }

    @Override
    public Mono<ResponseEntity<ProductResponse>> updateProduct(@PathVariable Integer id,
                                                               @RequestBody Mono<ProductRequest> productRequest,
                                                               ServerWebExchange exchange) {
        return productRequest
                .flatMap(this::validateRequest)
                .flatMap(request -> productFacade.updateProduct(id, request))
                .map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<Void>> deleteProduct(@PathVariable Integer id, ServerWebExchange exchange) {
        return productFacade.deleteProduct(id)
                .map(v -> ResponseEntity.noContent().<Void>build())
                .switchIfEmpty(Mono.defer(() -> Mono.just(ResponseEntity.notFound().build())))
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).build()));
    }

    @Override
    public Mono<ResponseEntity<Flux<ProductResponse>>>  getAllProducts(ServerWebExchange exchange) {
        return Mono.just(ResponseEntity.ok(productFacade.getAllProducts()));
    }

    @Override
    public Mono<ResponseEntity<ProductResponse>> getProductById(@PathVariable Integer id,
                                                                ServerWebExchange exchange) {
        return productFacade.getProductById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.noContent().build());
    }

    @Override
    public Mono<ResponseEntity<Flux<ProductResponse>>> searchProductsByName(@Valid String name, ServerWebExchange exchange) {
        return Mono.just(ResponseEntity.ok(productFacade.searchProductsByName(name)));
    }
}
