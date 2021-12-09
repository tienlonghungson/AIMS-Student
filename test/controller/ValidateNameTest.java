package controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class ValidateNameTest {

    private PlaceOrderController placeOrderController;

    @BeforeEach
    void setUp() {
        placeOrderController = new PlaceOrderController();
    }

    @ParameterizedTest
    @CsvSource({
            "nacnfa1241,false",
            "TranNhat,true",
            "/##$cacs,false",
            "Nguyen Tien Long,true"
    })
    void validateName(String name, boolean expected) {
        boolean isValidated = placeOrderController.validateName(name);
        assertEquals(expected,isValidated);
    }
}