package com.fullcycle.admin.catalogo.application.category.delete;

import com.fullcycle.admin.catalogo.domain.CategoryID;
import com.fullcycle.admin.catalogo.domain.category.Category;
import com.fullcycle.admin.catalogo.domain.category.CategoryGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteCategoryUseCaseTest {

    @InjectMocks
    private DefaultDeleteCategoryUseCase useCase;
    @Mock
    private CategoryGateway gateway;

    @BeforeEach
    public void cleanUo() {
        reset(gateway);
    }

    @Test
    public void givenAValidId_whenCallsDeleteCategory_shouldBeOK() {
        final var aCategory = Category.newCategory("Filmes", "A categoria mais assistida", true);
        final var expectedId = aCategory.getId();

        doNothing()
                .when(gateway).deleteById(eq(expectedId));

        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        Mockito.verify(gateway, times(1)).deleteById(eq(expectedId));
    }

    @Test
    public void givenAInvalidId_whenCallsDeleteCategory_shouldBeOK() {
        final var expectedId = CategoryID.from("123");

        doNothing()
                .when(gateway).deleteById(eq(expectedId));

        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        Mockito.verify(gateway, times(1)).deleteById(eq(expectedId));
    }

    @Test
    public void givenAValidId_whenGatewayThrowsException_shouldReturnException() {
        final var aCategory = Category.newCategory("Filmes", "A categoria mais assistida", true);
        final var expectedId = aCategory.getId();

        doThrow(new IllegalStateException("Gateway error"))
                .when(gateway).deleteById(eq(expectedId));

        Assertions.assertThrows(IllegalStateException.class, () -> useCase.execute(expectedId.getValue()));

        Mockito.verify(gateway, times(1)).deleteById(eq(expectedId));
    }
}

