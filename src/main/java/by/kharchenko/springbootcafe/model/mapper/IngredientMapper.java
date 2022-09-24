package by.kharchenko.springbootcafe.model.mapper;

import by.kharchenko.springbootcafe.model.Ingredient;
import by.kharchenko.springbootcafe.model.dto.IngredientDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface IngredientMapper {
    IngredientMapper INSTANCE = Mappers.getMapper(IngredientMapper.class);

    IngredientDTO ingredientToIngredientDTO(Ingredient ingredient);

    Ingredient ingredientDTOToIngredient(IngredientDTO ingredientDTO);
}
