package org.healthycoderapp;

import healthycoderapp.BMICalculator;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static junit.framework.Assert.fail;

class BMICalculatorTest {

    @Test
    void should_ReturnTrue_When_DietRecommended() {

        // given
        double weight = 89.0;
        double height = 1.72;

        // when
        boolean recommended = BMICalculator.isDietRecommended(weight, height);

        // then
        assertTrue(recommended);
    }

    @Test
    void should_ReturnFalse_When_DietNotRecommended() {

        // given
        double weight = 50.0;
        double height = 1.92;

        // when
        boolean recommended = BMICalculator.isDietRecommended(weight, height);

        // then
        assertFalse(recommended);
    }

    @Test
    void should_ThrowArithmeticException_When_HeightZero() {

        // given
        double weight = 50.0;
        double height = 0.0;

        // when
        Executable recommended = () -> BMICalculator.isDietRecommended(weight, height);

        // then
        assertThrows(ArithmeticException.class, recommended);
    }
}