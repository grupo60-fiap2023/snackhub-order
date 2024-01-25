package com.snackhuborder.domain.exceptions;

import com.snackhuborder.domain.validation.Error;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class DomainExceptionTest {

    @Test
    public void givenException_whenCallValidation_thenFillDomainException(){
        Error error = new Error("Mensagem");
        DomainException domainException = DomainException.with(error);
        Assertions.assertNotNull(domainException.getErrors());
        Assertions.assertEquals("Mensagem", domainException.getMessage());
        List<Error> erros = domainException.getErrors();
        Assertions.assertEquals(error, erros.stream().findFirst().get());
    }

    @Test
    public void givenListException_whenCallValidation_thenFillDomainException(){
        Error error = new Error("Mensagem");
        DomainException domainException = DomainException.with(Arrays.asList(error));
        Assertions.assertNotNull(domainException.getErrors());
        List<Error> erros = domainException.getErrors();
        Assertions.assertEquals(error, erros.stream().findFirst().get());
    }
}
