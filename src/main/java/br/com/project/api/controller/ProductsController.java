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
@RequestMapping(value = "/api/v1/products")
public class ProductsController {

    private ProductService productService;

    @ApiOperation(value = "Returns product list")
    @GetMapping(produces = "application/json")
    public ResponseEntity findAll() {
        return productService.findAll();
    }

    @ApiOperation(value = "Returns an existing product")
    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity findById(@PathVariable(value = "id") Long id) {
        return productService.findById(id);
    }

    @ApiOperation(value = "Save product")
    @PostMapping(produces = "application/json")
    public ResponseEntity save(@RequestBody @Valid ProductDto productDto) throws URISyntaxException,
            ProductAlreadyExistException {
        return productService.save(productDto);
    }

    @ApiOperation(value = "Remove an existing product")
    @DeleteMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity remove(@PathVariable(value = "id") Long id) {
        return productService.remove(id);
    }

    @ApiOperation(value = "Update an existing product")
    @PutMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity update(@PathVariable(value = "id") Long id, @RequestBody @Valid ProductDto productDto) {
        return productService.update(id, productDto);
    }

}
