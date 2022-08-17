package org.healthycoderapp;

import healthycoderapp.BMICalculator;
import healthycoderapp.Coder;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

class BMICalculatorTest {
    private String environment = "prod";
    @BeforeAll
    static void beforeAll() {
        System.out.println("Before all unit tests");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("After all unit tests");
    }

    @Nested
    class IsDietRecommendTests {
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
    }

    @Nested
    @DisplayName("Sample inner class display name")
    class FindCoderWithWorstBMITests {
        @Test
        @DisplayName(">>>> sample methods display name")
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

        @Disabled //@Disabled.ON for a determined os
        @Test
        void should_ReturnCoderWithWorstBMIIn500Ms_When_CoderHas10000Elements() {
            // given
            assumeTrue(BMICalculatorTest.this.environment.equals("prod"));
            List<Coder> coders = new ArrayList<>();
            for(int i=0; i<10000; i++) {
                coders.add(new Coder(1.0 + i, 10.0 + i));
            }

            // when
            Executable executable = () -> BMICalculator.findCoderWithWorstBMI(coders);

            // then
            assertTimeout(Duration.ofMillis(500), executable);
        }
    }

    @Nested
    class GetBMIScoresTests {
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
}