package com.crxmarkets.rainyhills.service;

import com.crxmarkets.rainyhills.dto.Interval;
import com.crxmarkets.rainyhills.exception.ValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.MIN_VALUE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("IntervalsCalculator")
class IntervalsCalculatorTest {

    private final IntervalsCalculator intervalsCalculator = new IntervalsCalculator();

    @Nested
    @DisplayName("calculates custom intervals correctly")
    class CustomIntervalsTest {

        @Test
        @DisplayName("when there are none")
        void calculate_success_noIntervals() {
            //given
            int[] inputArray = {0, 1, 2, 1, 0};

            //when
            List<Interval> result = intervalsCalculator.calculate(inputArray);

            //then
            assertThat(result.size(), is(4));
        }

        @Test
        @DisplayName("when interval starts with highest middle and goes to end")
        void calculate_success_intervalInHighestMiddleAndEnd() {
            //given
            int[] inputArray = {0, 2, 3, 1, 2};

            //when
            List<Interval> result = intervalsCalculator.calculate(inputArray);

            //then
            assertThat(result.size(), is(4));
            assertThat(result.get(0).getFrom(), is(2));
            assertThat(result.get(0).getTo(), is(4));
        }

        @Test
        @DisplayName("when interval starts with middle and goes to highest end")
        void calculate_success_intervalInMiddleAndHighestEnd() {
            //given
            int[] inputArray = {0, 2, 2, 1, 3};

            //when
            List<Interval> result = intervalsCalculator.calculate(inputArray);

            //then
            assertThat(result.size(), is(4));
            assertThat(result.get(0).getFrom(), is(4));
            assertThat(result.get(0).getTo(), is(2));
        }

        @Test
        @DisplayName("when there is a peak in middle which should be skipped")
        void calculate_success_peakInMiddleShouldBeSkipped() {
            //given
            int[] inputArray = {0, 4, 1, 2, 1, 3};

            //when
            List<Interval> result = intervalsCalculator.calculate(inputArray);

            //then
            assertThat(result.size(), is(5));
            assertThat(result.get(0).getFrom(), is(1));
            assertThat(result.get(0).getTo(), is(5));
        }

        @Test
        @DisplayName("when there are several similar peaks")
        void calculate_success_similarPeaks() {
            //given
            int[] inputArray = {1, 4, 1, 4, 1, 4};

            //when
            List<Interval> result = intervalsCalculator.calculate(inputArray);

            //then
            assertThat(result.size(), is(5));
            assertThat(result.get(0).getFrom(), is(5));
            assertThat(result.get(0).getTo(), is(3));
            assertThat(result.get(1).getFrom(), is(3));
            assertThat(result.get(1).getTo(), is(1));
        }

        @Test
        @DisplayName("when there are several similar peaks together")
        void calculate_success_similarPeaksTogether() {
            //given
            int[] inputArray = {1, 4, 4, 4, 1, 2, 4};

            //when
            List<Interval> result = intervalsCalculator.calculate(inputArray);

            //then
            assertThat(result.size(), is(6));
            assertThat(result.get(0).getFrom(), is(6));
            assertThat(result.get(0).getTo(), is(3));
        }

        @Test
        @DisplayName("when array has only one element")
        void calculate_success_arrayHasOneElement() {
            //given
            int[] inputArray = {5};

            //when
            List<Interval> result = intervalsCalculator.calculate(inputArray);

            //then
            assertThat(result.size(), is(0));
        }

        @Test
        @DisplayName("when array has similar peaks in the end")
        void calculate_success_arrayHasSimilarInEnd() {
            //given
            int[] inputArray = {1, 3, 4, 2, 6, 7, 9, 10, 0, 5, 4, 5};

            //when
            List<Interval> result = intervalsCalculator.calculate(inputArray);

            //then
            assertThat(result.size(), is(11));
            assertThat(result.get(0).getFrom(), is(7));
            assertThat(result.get(0).getTo(), is(6));
            assertThat(result.get(1).getFrom(), is(6));
            assertThat(result.get(1).getTo(), is(5));
        }
    }

    @Nested
    @DisplayName("calculates intervals from task correctly")
    class TaskIntervalsTest {

        @Test
        @DisplayName("for 1st example from task")
        void calculate_success_array1FromTask() {
            //given
            int[] inputArray = {3, 2, 4, 1, 2};

            //when
            List<Interval> result = intervalsCalculator.calculate(inputArray);

            //then
            assertThat(result.size(), is(4));
            assertThat(result.get(0).getFrom(), is(2));
            assertThat(result.get(0).getTo(), is(0));
            assertThat(result.get(1).getFrom(), is(0));
            assertThat(result.get(1).getTo(), is(4));
            assertThat(result.get(2).getFrom(), is(4));
            assertThat(result.get(2).getTo(), is(1));
            assertThat(result.get(3).getFrom(), is(1));
            assertThat(result.get(3).getTo(), is(3));
        }

        @Test
        @DisplayName("for 2nd example from task")
        void calculate_success_array2FromTask() {
            //given
            int[] inputArray = {4, 1, 1, 0, 2, 3};

            //when
            List<Interval> result = intervalsCalculator.calculate(inputArray);

            //then
            assertThat(result.size(), is(5));
            assertThat(result.get(0).getFrom(), is(0));
            assertThat(result.get(0).getTo(), is(5));
        }
    }

    @Nested
    @DisplayName("calculates extreme intervals correctly")
    class ExtremeIntervalsTest {

        @Test
        @DisplayName("when edges are max values of integer")
        void calculate_success_maxValues() {
            //given
            int[] inputArray = {MAX_VALUE, MIN_VALUE, MAX_VALUE, MIN_VALUE, MAX_VALUE};

            //when
            List<Interval> result = intervalsCalculator.calculate(inputArray);

            //then
            assertThat(result.size(), is(4));
            assertThat(result.get(0).getFrom(), is(4));
            assertThat(result.get(0).getTo(), is(2));
            assertThat(result.get(1).getFrom(), is(2));
            assertThat(result.get(1).getTo(), is(0));
            assertThat(result.get(2).getFrom(), is(0));
            assertThat(result.get(2).getTo(), is(3));
            assertThat(result.get(3).getFrom(), is(3));
            assertThat(result.get(3).getTo(), is(1));
        }

        @Test
        @DisplayName("when edges are max values of integer, but after min values")
        void calculate_success_maxValuesAfterMinValues() {
            //given
            int[] inputArray = {MIN_VALUE, MAX_VALUE, MIN_VALUE, MAX_VALUE, MIN_VALUE};

            //when
            List<Interval> result = intervalsCalculator.calculate(inputArray);

            //then
            assertThat(result.size(), is(4));
            assertThat(result.get(0).getFrom(), is(3));
            assertThat(result.get(0).getTo(), is(1));
            assertThat(result.get(1).getFrom(), is(1));
            assertThat(result.get(1).getTo(), is(4));
            assertThat(result.get(2).getFrom(), is(4));
            assertThat(result.get(2).getTo(), is(2));
            assertThat(result.get(3).getFrom(), is(2));
            assertThat(result.get(3).getTo(), is(0));
        }

        @Test
        @DisplayName("when edges are max values of integer, but after min values, and several min values are in the end")
        void calculate_success_maxValuesAfterMinValuesWithSeveralEndMinValues() {
            //given
            int[] inputArray = {MIN_VALUE, MAX_VALUE, MIN_VALUE, MAX_VALUE, MIN_VALUE, MIN_VALUE, MIN_VALUE};

            //when
            List<Interval> result = intervalsCalculator.calculate(inputArray);

            //then
            assertThat(result.size(), is(6));
            assertThat(result.get(0).getFrom(), is(3));
            assertThat(result.get(0).getTo(), is(1));
            assertThat(result.get(1).getFrom(), is(1));
            assertThat(result.get(1).getTo(), is(6));
            assertThat(result.get(2).getFrom(), is(6));
            assertThat(result.get(2).getTo(), is(5));
            assertThat(result.get(3).getFrom(), is(5));
            assertThat(result.get(3).getTo(), is(4));
            assertThat(result.get(4).getFrom(), is(4));
            assertThat(result.get(4).getTo(), is(2));
            assertThat(result.get(5).getFrom(), is(2));
            assertThat(result.get(5).getTo(), is(0));
        }
    }

    @Nested
    @DisplayName("operates correctly on empty values")
    class EmptyValuesTest {

        @Test
        @DisplayName("returns empty list when array is empty")
        void calculate_success_arrayIsEmpty() {
            //given
            int[] inputArray = {};

            //when
            List<Interval> result = intervalsCalculator.calculate(inputArray);

            //then
            assertThat(result.size(), is(0));
        }

        @Test
        @DisplayName("throws exception when array is null")
        void calculate_fail_arrayIsNull() {
            //given
            String expectedMessage = "array to calculate intervals should not be null";

            //when
            //then
            assertThrows(
                    ValidationException.class,
                    () -> intervalsCalculator.calculate(null),
                    expectedMessage);

        }
    }
}