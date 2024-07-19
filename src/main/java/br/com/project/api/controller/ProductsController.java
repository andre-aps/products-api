package br.com.project.api.controller;

import br.com.project.api.dto.ProductDto;
import br.com.project.api.exception.ProductAlreadyExistException;
import br.com.project.api.service.ProductService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/products")
@OpenAPIDefinition(info = @Info(title = "Products API", version = "1.0", description = "REST API for products management"))
public class ProductsController {

    private static final String PRODUCT_ID = "id";
    private ProductService productService;

    @Operation(summary = "Returns product list")
    @GetMapping
    public ResponseEntity<List<ProductDto>> findAll() {
        return productService.findAll();
    }

    @Operation(summary = "Returns an existing product")
    @GetMapping(path = "/{id}")
    public ResponseEntity<ProductDto> findById(@PathVariable(value = PRODUCT_ID) Long id) {
        return productService.findById(id);
    }

    @Operation(summary = "Save a new product")
    @PostMapping
    public ResponseEntity<ProductDto> save(@RequestBody @Valid ProductDto productDto) throws URISyntaxException,
            ProductAlreadyExistException {
        return productService.save(productDto);
    }

    @Operation(summary = "Remove an existing product")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> remove(@PathVariable(value = PRODUCT_ID) Long id) {
        return productService.remove(id);
    }

    @Operation(summary = "Update an existing product")
    @PutMapping(path = "/{id}")
    public ResponseEntity<ProductDto> update(@PathVariable(value = PRODUCT_ID) Long id, @RequestBody @Valid ProductDto productDto) {
        return productService.update(id, productDto);
    }

}
