package br.com.project.api.service;

import br.com.project.api.controller.ProductsController;
import br.com.project.api.dto.ProductDto;
import br.com.project.api.exception.ProductAlreadyExistException;
import br.com.project.api.mapper.ProductMapper;
import br.com.project.api.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper = ProductMapper.INSTANCE;

    public ResponseEntity<List<ProductDto>> findAll() {
        var products = productRepository.findAll();

        if (products.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(products
                .stream()
                .map(productMapper::toDto)
                .map(this::addSelfLinkRelation)
                .collect(Collectors.toList()));
    }

    private ProductDto addSelfLinkRelation(ProductDto productDto) {
        return productDto.add(linkTo(methodOn(ProductsController.class).findById(productDto.getId())).withSelfRel());
    }

    public ResponseEntity<ProductDto> findById(Long id) {
        var productFound = productRepository.findById(id);

        return productFound.map(product -> ResponseEntity.ok(addLinkRelation(productMapper.toDto(product))))
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    private ProductDto addLinkRelation(ProductDto productDto) {
        return productDto.add(linkTo(methodOn(ProductsController.class).findAll()).withRel("Product list"));
    }

    public ResponseEntity<ProductDto> save(ProductDto productDto) throws URISyntaxException, ProductAlreadyExistException {
        verifyIfProductAlreadyExist(productDto.getName());

        var product = productRepository.save(productMapper.toModel(productDto));

        return ResponseEntity.created(new URI("/products")).body(productMapper.toDto(product));
    }

    private void verifyIfProductAlreadyExist(String name) throws ProductAlreadyExistException {
        var productFound = productRepository.findByName(name);

        if (productFound.isPresent()) {
            throw new ProductAlreadyExistException(name);
        }
    }

    public ResponseEntity<Void> remove(Long id) {
        var productFound = productRepository.findById(id);

        if (productFound.isPresent()) {
            productRepository.delete(productFound.get());
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<ProductDto> update(Long id, ProductDto productDto) {
        var productFound = productRepository.findById(id);

        if (productFound.isPresent()) {
            productDto.setId(productFound.get().getId());
            var product = productRepository.save(productMapper.toModel(productDto));
            return ResponseEntity.ok().body(productMapper.toDto(product));
        }

        return ResponseEntity.notFound().build();
    }

}
