package by.kharchenko.springbootcafe.model.mapper;

import by.kharchenko.springbootcafe.model.Product;
import by.kharchenko.springbootcafe.model.dto.ProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductDTO productToProductDTO(Product product);

    Product productDTOToProduct(ProductDTO productDTO);
}
