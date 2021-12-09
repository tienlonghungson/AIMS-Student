package controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class ValidateAddressTest {
    private PlaceOrderController placeOrderController;

    @BeforeEach
    void setUp() {
        placeOrderController = new PlaceOrderController();
    }

    @ParameterizedTest
    @CsvSource({
            "Coast City,true",
            "So 1 Dai Co Viet Ha Noi, true",
            "Ngach 17/5/6 Nha Khong so Pho khong ten,true",
            "S# Hanoi, false"
    })
    void validateAddress(String address, boolean expected) {
        boolean isValidated = placeOrderController.validateAddress(address);
        assertEquals(expected,isValidated);
    }
}