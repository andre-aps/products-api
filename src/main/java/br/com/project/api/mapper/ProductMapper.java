package br.com.project.api.mapper;

import br.com.project.api.dto.ProductDto;
import br.com.project.api.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    Product toModel(ProductDto productDto);

    ProductDto toDto(Product product);

}
