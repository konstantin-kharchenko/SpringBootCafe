package by.kharchenko.springbootcafe.validator.annotation;

import by.kharchenko.springbootcafe.model.AbstractEntity;
import by.kharchenko.springbootcafe.validator.annotation.impl.CustomFutureOrPresentImpl;

import javax.validation.Constraint;
import java.lang.annotation.*;

@Constraint(validatedBy = { CustomFutureOrPresentImpl.class })
@Documented
@Target({ ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.FIELD ,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomFutureOrPresent {
    String message() default "Будущее или настоящее";

    Class<?>[] groups() default {};
    Class<? extends AbstractEntity>[] payload() default {};
}
