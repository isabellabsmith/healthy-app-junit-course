package org.healthycoderapp;

import healthycoderapp.BMICalculator;
import healthycoderapp.Coder;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.List;

class BMICalculatorTest {
    @BeforeAll
    static void beforeAll() {
        System.out.println("Before all unit tests");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("After all unit tests");
    }

    @ParameterizedTest(name = "weight={0}, height={1}")
    @CsvFileSource(resources = "/diet-recommended-input-data.csv", numLinesToSkip = 1)
    void should_ReturnTrue_When_DietRecommended(Double coderWeight, Double coderHeight) {
        // given
        double weight = coderWeight;
        double height = coderHeight;

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

    @Test
    void should_ReturnCoderWithWorstBMI_When_CoderListNotEmpty() {
        // given
        List<Coder> coders = new ArrayList<>();
        coders.add(new Coder(1.80, 60.0));
        coders.add(new Coder(1.82, 98.0));
        coders.add(new Coder(1.82, 64.7));

        // when
        Coder coderWorstBMI = BMICalculator.findCoderWithWorstBMI(coders);

        // then
        assertAll (
                () -> assertEquals(1.82, coderWorstBMI.getHeight()),
                () -> assertEquals(98, coderWorstBMI.getWeight())
        );
    }

    @Test
    void should_ReturnCoderWithWorstBMI_When_CoderListEmpty() {
        // given
        List<Coder> coders = new ArrayList<>();

        // when
        Coder coderWorstBMI = BMICalculator.findCoderWithWorstBMI(coders);

        // then
        assertNull(coderWorstBMI);
    }

    @Test
    void should_ReturnCorrectBMIScoreArray_When_CoderListNotEmpty() {
        // given
        List<Coder> coders = new ArrayList<>();
        coders.add(new Coder(1.80, 60.0));
        coders.add(new Coder(1.82, 98.0));
        coders.add(new Coder(1.82, 64.7));
        double[] expected = { 18.52, 29.59, 19.53};

        // when
        double[] bmiScores = BMICalculator.getBMIScores(coders);

        // then
        assertArrayEquals(expected, bmiScores);
    }
}