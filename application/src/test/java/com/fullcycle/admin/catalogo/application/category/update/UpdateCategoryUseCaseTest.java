package com.fullcycle.admin.catalogo.application.category.update;

import com.fullcycle.admin.catalogo.domain.CategoryID;
import com.fullcycle.admin.catalogo.domain.category.Category;
import com.fullcycle.admin.catalogo.domain.category.CategoryGateway;
import com.fullcycle.admin.catalogo.domain.exceptions.DomainException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;
import java.util.Optional;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdateCategoryUseCaseTest {

    //1.teste caminho feliz
    //2.teste passando propriedade inválida(name)
    //3.criando categoria inativa
    //4.simular erro generico vindo do gateway
    //5.atualizar categoria passando ID invalido
    @InjectMocks
     private DefaultUpdateCategoryUseCase useCase;

    @Mock
     private CategoryGateway gateway;

    @BeforeEach
    public void cleanUp(){
        Mockito.reset(gateway);
    }

    @Test
    public void givenAValidParams_whenCallsUpdateCategory_shouldReturnCategoryId(){
        final var aCategory = Category. newCategory("Film", null, true);

        final var expectedName = "filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedId = aCategory.getId();


        final var aCommand = UpdateCategoryCommand.with(expectedId.getValue(), expectedName, expectedDescription, expectedIsActive);

        when(gateway.findById(eq(expectedId))).thenReturn(Optional.of(aCategory.clone()));
        when(gateway.update(any())).thenAnswer(returnsFirstArg());

        final var actualOutput = useCase.execute(aCommand).get();


        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        verify(gateway,times(1)).findById(eq(expectedId));
        verify(gateway, times(1)).update(
                argThat(aUpdateCategory -> Objects.equals(expectedName, aUpdateCategory.getName())
                                        && Objects.equals(expectedDescription, aUpdateCategory.getDescription() )
                                        && Objects.equals(expectedIsActive, aUpdateCategory.isActive() )
                                        && Objects.isNull(aUpdateCategory.getDeletedAt())
                                        && aCategory.getUpdatedAt().isBefore(aUpdateCategory.getUpdatedAt())
                                        && Objects.nonNull(aUpdateCategory.getUpdatedAt())
                )
        );

    }
    @Test
    public void givenAnInvalidNullName_whenCallUpdateCategory_thenShouldReturnDomainException(){

        final var aCategory = Category.newCategory("Film", null, true);

        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "'name' should not be null";
        final var expectedErrorCount = 1;
        final var expectedId = aCategory.getId();

        when(gateway.findById(eq(expectedId))).thenReturn(Optional.of(aCategory.clone()));

        final var aCommand = UpdateCategoryCommand.with(expectedId.getValue(),null, expectedDescription, expectedIsActive);


        final var actualException = useCase.execute(aCommand).getLeft();

        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        verify(gateway, times(0)).update(any());
    }

    @Test
    public void givenAValidInactivateCommand_whenCallsUpdateCategory_shouldReturnInactiveCategoryId(){
        final var aCategory = Category. newCategory("Film", null, true);

        final var expectedName = "filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = false;
        final var expectedId = aCategory.getId();

        final var aCommand = UpdateCategoryCommand.with(expectedId.getValue(), expectedName, expectedDescription, expectedIsActive);

        when(gateway.findById(eq(expectedId))).thenReturn(Optional.of(aCategory.clone()));
        when(gateway.update(any())).thenAnswer(returnsFirstArg());

        Assertions.assertTrue(aCategory.isActive());
        Assertions.assertNull(aCategory.getDeletedAt());

        final var notification = useCase.execute(aCommand).get();


        Assertions.assertNotNull(notification);
        Assertions.assertNotNull(notification.id());

        verify(gateway,times(1)).findById(eq(expectedId));
        verify(gateway, times(1)).update(
                argThat(aUpdateCategory -> Objects.equals(expectedName, aUpdateCategory.getName())
                        && Objects.equals(expectedDescription, aUpdateCategory.getDescription() )
                        && Objects.equals(expectedIsActive, aUpdateCategory.isActive() )
                        && Objects.equals(expectedId, aUpdateCategory.getId() )
                        && Objects.equals(aCategory.getCreatedAt(), aUpdateCategory.getCreatedAt() )
                        && aCategory.getUpdatedAt().isBefore(aUpdateCategory.getUpdatedAt())
                        && Objects.nonNull(aUpdateCategory.getDeletedAt())
                )
        );

    }

    @Test
    public void givenAValidCommand_whenGatewayThrowsRandom_shouldReturnAException(){

        final var aCategory = Category. newCategory("Film", null, true);
        final var expectedName = "Filme";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "Erro no Gateway";
        final var expectedErrorCount = 1;
        final var expectedId = aCategory.getId();

        final var aCommand = UpdateCategoryCommand.with(expectedId.getValue(), expectedName, expectedDescription, expectedIsActive);


        when(gateway.findById(eq(expectedId))).thenReturn(Optional.of(aCategory.clone()));
        when(gateway.update(any())).thenThrow(new IllegalStateException(expectedErrorMessage));

        final var notification = useCase.execute(aCommand).getLeft();

        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, notification.firsError().message());

        verify(gateway,times(1)).findById(eq(expectedId));
        verify(gateway, times(1)).update(
                argThat(aUpdateCategory -> Objects.equals(expectedName, aUpdateCategory.getName())
                        && Objects.equals(expectedDescription, aUpdateCategory.getDescription() )
                        && Objects.equals(expectedIsActive, aUpdateCategory.isActive() )
                        && Objects.equals(expectedId, aUpdateCategory.getId() )
                        && Objects.equals(aCategory.getCreatedAt(), aUpdateCategory.getCreatedAt() )
                        && aCategory.getUpdatedAt().isBefore(aUpdateCategory.getUpdatedAt())
                        && Objects.isNull(aUpdateCategory.getDeletedAt())
                )
        );

    }

    @Test
    public void givenAValidCommandWithInvalidID_whenCallsUpdateCategory_thenShouldReturnNotFound(){

        final var expectedName = "Filme";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "Category with ID 123 was not-found!";
        final var expectedErrorCount = 1;
        final var expectedId = "123";

        final var aCommand = UpdateCategoryCommand.with(expectedId, expectedName, expectedDescription, expectedIsActive);

        when(gateway.findById(eq(CategoryID.from(expectedId)))).thenReturn(Optional.empty());

        final var actualException =  Assertions.assertThrows(DomainException.class, () -> useCase.execute(aCommand));

        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());

        verify(gateway, times(1)).findById(CategoryID.from(expectedId));
        verify(gateway, times(0)).update(any());
    }

}
