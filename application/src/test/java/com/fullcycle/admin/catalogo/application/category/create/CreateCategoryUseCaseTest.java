package com.fullcycle.admin.catalogo.application.category.create;

import com.fullcycle.admin.catalogo.domain.category.Category;
import com.fullcycle.admin.catalogo.domain.category.CategoryGateway;
import com.fullcycle.admin.catalogo.domain.exceptions.DomainException;
import com.fullcycle.admin.catalogo.domain.handler.Notification;
import com.fullcycle.admin.catalogo.domain.handler.ThrowsValidationHandler;
import io.vavr.NotImplementedError;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;

import static org.mockito.AdditionalAnswers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateCategoryUseCaseTest {

    //1.teste caminho feliz
    //2.teste passando propriedade inválida(name)
    //3.criando categoria inativa
    //4.simular erro generico vindo do gateway

    @InjectMocks
    private DefaultCreateCategoryUseCase useCase;

    @Mock
    private CategoryGateway gateway;


    @Test
    public void givenAValidCommand_whenCallsCrateCategory_shouldReturnCategoryId(){

        final var expectedName = "Filme";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var aCommand = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);

        final var actualCategory = when(gateway.create(any())).thenAnswer(returnsFirstArg());

        final var actualOutput = useCase.execute(aCommand).get();

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        verify(gateway, times(1)).create(argThat(
                aCategory ->
                   Objects.nonNull(aCategory.getId())
                && Objects.equals(aCategory.getName(), expectedName)
                && Objects.equals(aCategory.getDescription(), expectedDescription)
                && Objects.equals(aCategory.isActive(), expectedIsActive)
                && Objects.isNull(aCategory.getDeletedAt())
                && Objects.nonNull(aCategory.getCreatedAt())
                && Objects.nonNull(aCategory.getUpdatedAt())
        ));

    }

    @Test
    public void givenAInvalidName_whenCallsCreateCategory_ShouldReturnDomainException() {

        final String expectedName = null;
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "'name' should not be null";
        final var expectedErrorCount = 1;

        final var aCommand = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);

        final var notification = useCase.execute(aCommand).getLeft();

        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, notification.firsError().message());

        verify(gateway, times(0)).create(any());
    }

    @Test
    public void givenAValidCommandWithInactiveCategory_whenCallsCrateCategory_shouldReturnInactiveCategoryId(){

        final var expectedName = "Filme";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = false;

        final var aCommand = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);

        final var actualCategory = when(gateway.create(any())).thenAnswer(returnsFirstArg());

        final var actualOutput = useCase.execute(aCommand).get();

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        verify(gateway, times(1)).create(argThat(
                aCategory ->
                        Objects.nonNull(aCategory.getId())
                                && Objects.equals(aCategory.getName(), expectedName)
                                && Objects.equals(aCategory.getDescription(), expectedDescription)
                                && Objects.equals(aCategory.isActive(), expectedIsActive)
                                && Objects.nonNull(aCategory.getDeletedAt())
                                && Objects.nonNull(aCategory.getCreatedAt())
                                && Objects.nonNull(aCategory.getUpdatedAt())
        ));
    }

    @Test
    public void givenAValidCommand_whenGatewayThrowsRandom_shouldReturnAException(){

        final var expectedName = "Filme";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "Erro no Gateway";
        final var expectedErrorCount = 1;

        final var aCommand = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);

        final var actualCategory = when(gateway.create(any())).thenThrow(new IllegalStateException(expectedErrorMessage));

        final var notification = useCase.execute(aCommand).getLeft();

        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, notification.firsError().message());

        verify(gateway, times(1)).create(argThat(
                aCategory ->
                        Objects.nonNull(aCategory.getId())
                                && Objects.equals(aCategory.getName(), expectedName)
                                && Objects.equals(aCategory.getDescription(), expectedDescription)
                                && Objects.equals(aCategory.isActive(), expectedIsActive)
                                && Objects.isNull(aCategory.getDeletedAt())
                                && Objects.nonNull(aCategory.getCreatedAt())
                                && Objects.nonNull(aCategory.getUpdatedAt())
        ));

    }
}
