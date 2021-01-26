package br.com.project.api.controller;

import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.project.api.model.Product;
import br.com.project.api.service.ProductService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/products")
@Tag(name = "products", description = "Product operations")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@ApiOperation(value = "Returns product list")
	@Tag(name = "products")
	@GetMapping(produces = "application/json")
	public ResponseEntity<List<Product>> returnsAllProducts() {
		return productService.findAllProducts();
	}
	
	@ApiOperation(value = "Returns an existing product")
	@Tag(name = "products")
	@GetMapping(path = "/{id}", produces = "application/json")
	public ResponseEntity<Product> returnsProductById(@PathVariable(value = "id") Long id) {
		return productService.findProductById(id);
	}

	@ApiOperation(value = "Save product")
	@Tag(name = "products")
	@PostMapping(produces = "application/json")
	public ResponseEntity<Product> saveProduct(@RequestBody @Valid Product product) throws URISyntaxException {
		return productService.save(product);
	}

	@ApiOperation(value = "Remove an existing product")
	@Tag(name = "products")
	@DeleteMapping(path = "/{id}", produces = "application/json")
	public ResponseEntity<Product> removeProduct(@PathVariable(value = "id") Long id) {
		return productService.remove(id);
	}

	@ApiOperation(value = "Update an existing product")
	@Tag(name = "products")
	@PutMapping(path = "/{id}", produces = "application/json")
	public ResponseEntity<Product> updateProduct(@PathVariable(value = "id") Long id,
			@RequestBody @Valid Product product) {
		return productService.update(id, product);
	}

}
