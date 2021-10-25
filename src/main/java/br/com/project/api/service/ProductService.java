package br.com.project.api.service;

import br.com.project.api.controller.ProductsController;
import br.com.project.api.dto.ProductDto;
import br.com.project.api.mapper.ProductMapper;
import br.com.project.api.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
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

        return products.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(products
                .stream()
                .map(productMapper::toDto)
                .map(ProductService::addSelfLinkRelation)
                .collect(Collectors.toList()));
    }

    private static ProductDto addSelfLinkRelation(ProductDto productDto) {
        return productDto.add(linkTo(methodOn(ProductsController.class).findById(productDto.getId())).withSelfRel());
    }

    public ResponseEntity<ProductDto> findById(Long id) {
        var productFound = productRepository.findById(id);

        return productFound.isEmpty() ? ResponseEntity.notFound().build() :
                ResponseEntity.ok(addLinkRelation(productMapper.toDto(productFound.get())));
    }

    private static ProductDto addLinkRelation(ProductDto productDto) {
        return productDto.add(linkTo(methodOn(ProductsController.class).findAll()).withRel("Product list"));
    }

    public ResponseEntity<ProductDto> save(ProductDto productDto) throws URISyntaxException {
        var product = productRepository.save(productMapper.toModel(productDto));

        return ResponseEntity.created(new URI("/products"))
                .body(productMapper.toDto(product));
    }

    public ResponseEntity remove(Long id) {
        var productFound = productRepository.findById(id);

        if (productFound.isPresent()) {
            productRepository.delete(productFound.get());
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<ProductDto> update(Long id, @Valid ProductDto productDto) {
        var productFound = productRepository.findById(id);

        if (productFound.isPresent()) {
            productDto.setId(productFound.get().getId());
            var product = productRepository.save(productMapper.toModel(productDto));
            return ResponseEntity.ok().body(productMapper.toDto(product));
        }

        return ResponseEntity.notFound().build();
    }

}
