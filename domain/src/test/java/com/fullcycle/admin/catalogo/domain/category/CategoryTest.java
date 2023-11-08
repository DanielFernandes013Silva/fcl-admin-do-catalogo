package com.fullcycle.admin.catalogo.domain.category;

import com.fullcycle.admin.catalogo.domain.exceptions.DomainException;
import com.fullcycle.admin.catalogo.domain.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;

public class CategoryTest {

    @Test
    public void givenAValidaParams_whenCallNewCategory_thenInstantiateACategory(){
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expextedIsActive = true;

        final var actuaCategory =
                Category.newCategory(expectedName, expectedDescription, expextedIsActive);


        Assertions.assertNotNull(actuaCategory);
        Assertions.assertNotNull(actuaCategory.getId());
        Assertions.assertEquals(expectedName, actuaCategory.getName());
        Assertions.assertEquals(expectedDescription, actuaCategory.getDescription());
        Assertions.assertEquals(expextedIsActive, actuaCategory.isActive());
        Assertions.assertNotNull(actuaCategory.getCreatedAt());
        Assertions.assertNotNull(actuaCategory.getUpdatedAt());
        Assertions.assertNull(actuaCategory.getDeletedAt());
    }

    @Test
    public void givenAnInvalidNullName_whenCallNewCategoryAndValidate_thenShouldReceiveError(){
        final String expectedName = null;
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "'name' should not be null";
        final var expectedErrorCount = 1;

        final var actualCategory =
                Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final var actualException =
                Assertions.assertThrows(DomainException.class, () -> actualCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
    }
    @Test
    public void givenAnInvalidEmptyName_whenCallNewCategoryAndValidate_thenShouldReceiveError(){
        final String expectedName = "  ";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "'name' should not be empty";
        final var expectedErrorCount = 1;

        final var actualCategory =
                Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final var actualException =
                Assertions.assertThrows(DomainException.class, () -> actualCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
    }
    @Test
    public void givenAnInvalidNameLengthLessThan3_whenCallNewCategoryAndValidate_thenShouldReceiveError(){
        final String expectedName = "Fi ";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "'name' must between 3 and 255 characters";
        final var expectedErrorCount = 1;

        final var actualCategory =
                Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final var actualException =
                Assertions.assertThrows(DomainException.class, () -> actualCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    public void givenAnInvalidNameLengthLessMoreThan255_whenCallNewCategoryAndValidate_thenShouldReceiveError(){
        final String expectedName = """
                O Fabuloso Gerador de Lero-lero v2.0 é capaz de gerar qualquer quantidade de texto vazio e prolixo, 
                ideal para engrossar uma tese de mestrado, impressionar seu chefe ou preparar discursos capazes de
                 curar a insônia da platéia.Voilá! Em dois nano-segundos você terá um texto - ou mesmo um livro inteiro
                  - pronto para impressão. Ou, se preferir, faça copy/paste para um editor de texto para formatá-lo mais
                   sofisticadamente. Lembre-se: aparência é tudo, conteúdo é nada.
                """ ;
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "'name' must between 3 and 255 characters";
        final var expectedErrorCount = 1;

        final var actualCategory =
                Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final var actualException =
                Assertions.assertThrows(DomainException.class, () -> actualCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    public void givenAValidEmptyDescription_whenCallNewCategory_thenInstantiateACategory(){
        final var expectedName = "Filmes";
        final var expectedDescription = " ";
        final var expectedIsActive = true;

        final var actualCategory =
                Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertDoesNotThrow(() -> actualCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertNotNull(actualCategory);
        Assertions.assertNotNull(actualCategory.getId());
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, actualCategory.isActive());
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertNotNull(actualCategory.getUpdatedAt());
        Assertions.assertNull(actualCategory.getDeletedAt());
    }

    @Test
    public void givenAValidFalseIsActive_whenCallNewCategory_thenInstantiateACategory(){
        final var expectedName = "Filmes";
        final var expectedDescription = "A Categoria mais assistida";
        final var expectedIsActive = false;

        final var actualCategory =
                Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertDoesNotThrow(() -> actualCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertNotNull(actualCategory);
        Assertions.assertNotNull(actualCategory.getId());
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, actualCategory.isActive());
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertNotNull(actualCategory.getUpdatedAt());
        Assertions.assertNotNull(actualCategory.getDeletedAt());
    }

    @Test
    public void givenAValidActiveCategory_whenCallDeactivate_thenReturnCategoryInactive(){
        final var expectedName = "Filmes";
        final var expectedDescription = "A Categoria mais assistida";
        final var expectedIsActive = false;

        final var aCategory =
                Category.newCategory(expectedName, expectedDescription, true);

        Assertions.assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));

        final var updatedAt = aCategory.getUpdatedAt();

        Assertions.assertNotNull(aCategory.getUpdatedAt());
        Assertions.assertTrue(aCategory.isActive());

        final var actualCategory = aCategory.deactivate();

        Assertions.assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(aCategory.getId(), actualCategory.getId());
        Assertions.assertEquals(aCategory.getName(), actualCategory.getName());
        Assertions.assertEquals(aCategory.getDescription(), actualCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, actualCategory.isActive());
        Assertions.assertNotNull(aCategory.getCreatedAt());
//        Assertions.assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));
        Assertions.assertNotNull(aCategory.getDeletedAt());
    }

    @Test
    public void givenAValidInactiveCategory_whenCallDActivate_thenReturnCategoryActive(){
        final var expectedName = "Filmes";
        final var expectedDescription = "A Categoria mais assistida";
        final var expectedIsActive = true;

        final var aCategory =
                Category.newCategory(expectedName, expectedDescription, false);

        Assertions.assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));

        final var updatedAt = aCategory.getUpdatedAt();
        final var  createdAt = aCategory.getCreatedAt();

        Assertions.assertNotNull(aCategory.getDeletedAt());
        Assertions.assertFalse(aCategory.isActive());

        final var actualCategory = aCategory.activate();

        Assertions.assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(aCategory.getId(), actualCategory.getId());
        Assertions.assertEquals(aCategory.getName(), actualCategory.getName());
        Assertions.assertEquals(aCategory.getDescription(), actualCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, actualCategory.isActive());
        Assertions.assertEquals(aCategory.getCreatedAt(), actualCategory.getCreatedAt());
        Assertions.assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));
        Assertions.assertNull(aCategory.getDeletedAt());
    }

    @Test
    public void givenAValidCategory_whenCallUpdate_thenReturnCategoryUpdated(){
        final var expectedName = "Filmes";
        final var expectedDescription = "A Categoria mais assistida";
        final var expectedIsActive = true;

        final var aCategory =
                Category.newCategory("Filme", "A categoria", true);

        Assertions.assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));

        final var createdAt = aCategory.getUpdatedAt();
        final var updatedAt = aCategory.getUpdatedAt();

        final var actualCategory = aCategory.update(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(aCategory.getId(), actualCategory.getId());
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription , actualCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, actualCategory.isActive());
        Assertions.assertEquals(createdAt, actualCategory.getCreatedAt());
//        Assertions.assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));
        Assertions.assertNull(aCategory.getDeletedAt());
    }

    @Test
    public void givenAValidCategory_whenCallUpdateToInactiveCategory_thenReturnCategoryUpdated(){
        final var expectedName = "Filmes";
        final var expectedDescription = "A Categoria mais assistida";
        final var expectedIsActive = false;

        final var aCategory =
                Category.newCategory("Filme", "A categoria", true);

        Assertions.assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));

        final var createdAt = aCategory.getUpdatedAt();
        final var updatedAt = aCategory.getUpdatedAt();

        Assertions.assertTrue(aCategory.isActive());
        Assertions.assertNull(aCategory.getDeletedAt());


        final var actualCategory = aCategory.update(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(aCategory.getId(), actualCategory.getId());
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription , actualCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, actualCategory.isActive());
        Assertions.assertEquals(createdAt, actualCategory.getCreatedAt());
        Assertions.assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));
    }

    @Test
    public void givenAValidCategory_whenCallUpdateWithInvalidParams_thenReturnCategoryUpdated(){
        final String expectedName = null;
        final var expectedDescription = "A Categoria mais assistida";
        final var expectedIsActive = true;

        final var aCategory =
                Category.newCategory("Filmes", "A categoria", true);

        Assertions.assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));

        final var createdAt = aCategory.getUpdatedAt();
        final var updatedAt = aCategory.getUpdatedAt();

        final var actualCategory = aCategory.update(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertEquals(aCategory.getId(), actualCategory.getId());
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription , actualCategory.getDescription());
        Assertions.assertTrue(actualCategory.isActive());
        Assertions.assertEquals(createdAt, actualCategory.getCreatedAt());
//        Assertions.assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));
        Assertions.assertNull(actualCategory.getDeletedAt());
    }

}