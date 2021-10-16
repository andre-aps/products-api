package br.com.project.api.service;

import br.com.project.api.controller.ProductController;
import br.com.project.api.model.Product;
import br.com.project.api.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ProductService {

	private ProductRepository productRepository;
	
	public ResponseEntity<List<Product>> findAllProducts() {
		List<Product> productList = productRepository.findAll();
		
		if(productList.isEmpty()) {
			return ResponseEntity.notFound().build();
		} else {
			for (Product product : productList) {
				product.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
						.methodOn(ProductController.class).returnsProductById(product.getId()))
						.withSelfRel());
			}
		}
		return ResponseEntity.ok(productList);
	}
	
	public ResponseEntity<Product> findProductById(Long id) {
		Optional<Product> product = productRepository.findById(id);
		
		if(!product.isPresent())
			return ResponseEntity.notFound().build();
		else 
			product.get().add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
					.methodOn(ProductController.class).returnsAllProducts())
					.withRel("Product list"));
			
		return ResponseEntity.ok(product.get());
	}
	
	public ResponseEntity<Product> save(Product product) throws URISyntaxException {
		return ResponseEntity.created(new URI("/products")).body(productRepository.save(product));
	}
	
	public ResponseEntity<Product> remove(Long id) {
		Optional<Product> product = productRepository.findById(id);
		
		if(!product.isPresent()) {
			return ResponseEntity.notFound().build();
		} else {
			productRepository.delete(product.get());
			return ResponseEntity.ok().build();
		}
	}
	
	public ResponseEntity<Product> update(Long id, Product product) {
		Optional<Product> productFound = productRepository.findById(id);
		
		if(!productFound.isPresent()) {
			return ResponseEntity.notFound().build();
		} else {
			product.setId(productFound.get().getId());
			return ResponseEntity.ok().body(productRepository.save(product));
		}
	}

}
