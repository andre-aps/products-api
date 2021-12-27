package br.com.project.api.controller;

import br.com.project.api.dto.ProductDto;
import br.com.project.api.exception.ProductAlreadyExistException;
import br.com.project.api.service.ProductService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/products")
public class ProductsController {

    private static final String PRODUCT_ID = "id";
    private ProductService productService;

    @ApiOperation(value = "Returns product list")
    @GetMapping
    public ResponseEntity<ProductDto> findAll() {
        return productService.findAll();
    }

    @ApiOperation(value = "Returns an existing product")
    @GetMapping(path = "/{id}")
    public ResponseEntity<ProductDto> findById(@PathVariable(value = PRODUCT_ID) Long id) {
        return productService.findById(id);
    }

    @ApiOperation(value = "Save product")
    @PostMapping
    public ResponseEntity<ProductDto> save(@RequestBody @Valid ProductDto productDto) throws URISyntaxException,
            ProductAlreadyExistException {
        return productService.save(productDto);
    }

    @ApiOperation(value = "Remove an existing product")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity remove(@PathVariable(value = PRODUCT_ID) Long id) {
        return productService.remove(id);
    }

    @ApiOperation(value = "Update an existing product")
    @PutMapping(path = "/{id}")
    public ResponseEntity<ProductDto> update(@PathVariable(value = PRODUCT_ID) Long id, @RequestBody @Valid ProductDto productDto) {
        return productService.update(id, productDto);
    }

}
