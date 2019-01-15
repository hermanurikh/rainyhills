package com.crxmarkets.rainyhills.service;

import com.crxmarkets.rainyhills.dto.Interval;
import com.crxmarkets.rainyhills.dto.RainResult;
import com.crxmarkets.rainyhills.exception.ValidationException;
import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.MIN_VALUE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("WaterFiller")
class WaterFillerTest {

    private final WaterFiller waterFiller = new WaterFiller();

    @DisplayName("operates correctly on normal situations")
    @Nested
    class NormalSituationsTest {

        @DisplayName("when there is one valid interval")
        @Test
        void fillWater_success_oneInterval() {
            //given
            int[] inputArray = {5, 4, 3, 4, 5};
            List<Interval> intervals = ImmutableList.of(
                    new Interval(0, 4)
            );

            //when
            RainResult rainResult = waterFiller.fillWater(inputArray, intervals);

            //then
            long[] waterToFill = rainResult.getWaterToFill();

            assertThat(rainResult.getSourceArray(), is(inputArray));

            assertThat(waterToFill[0], is(0L));
            assertThat(waterToFill[1], is(1L));
            assertThat(waterToFill[2], is(2L));
            assertThat(waterToFill[3], is(1L));
            assertThat(waterToFill[4], is(0L));

            assertThat(rainResult.getVolume(), is(4L));
        }

        @DisplayName("when there are none intervals")
        @Test
        void fillWater_success_noIntervals() {
            //given
            int[] inputArray = {5, 6, 7, 6, 5};
            List<Interval> intervals = Collections.emptyList();

            //when
            RainResult rainResult = waterFiller.fillWater(inputArray, intervals);

            //then
            long[] waterToFill = rainResult.getWaterToFill();

            assertThat(rainResult.getSourceArray(), is(inputArray));

            assertThat(waterToFill[0], is(0L));
            assertThat(waterToFill[1], is(0L));
            assertThat(waterToFill[2], is(0L));
            assertThat(waterToFill[3], is(0L));
            assertThat(waterToFill[4], is(0L));

            assertThat(rainResult.getVolume(), is(0L));
        }

        @DisplayName("when there are two valid intervals with different edges")
        @Test
        void fillWater_success_twoIntervals_differentEdges() {
            //given
            int[] inputArray = {5, 1, 4, -5, 3};
            List<Interval> intervals = ImmutableList.of(
                    new Interval(0, 2),
                    new Interval(2, 4)
            );

            //when
            RainResult rainResult = waterFiller.fillWater(inputArray, intervals);

            //then
            long[] waterToFill = rainResult.getWaterToFill();

            assertThat(rainResult.getSourceArray(), is(inputArray));

            assertThat(waterToFill[0], is(0L));
            assertThat(waterToFill[1], is(3L));
            assertThat(waterToFill[2], is(0L));
            assertThat(waterToFill[3], is(8L));
            assertThat(waterToFill[4], is(0L));

            assertThat(rainResult.getVolume(), is(11L));
        }

        @DisplayName("when there are two valid intervals with same edges")
        @Test
        void fillWater_success_twoIntervals_sameEdges() {
            //given
            int[] inputArray = {5, 1, 5, -5, 5};
            List<Interval> intervals = ImmutableList.of(
                    new Interval(0, 2),
                    new Interval(2, 4)
            );

            //when
            RainResult rainResult = waterFiller.fillWater(inputArray, intervals);

            //then
            long[] waterToFill = rainResult.getWaterToFill();

            assertThat(rainResult.getSourceArray(), is(inputArray));

            assertThat(waterToFill[0], is(0L));
            assertThat(waterToFill[1], is(4L));
            assertThat(waterToFill[2], is(0L));
            assertThat(waterToFill[3], is(10L));
            assertThat(waterToFill[4], is(0L));

            assertThat(rainResult.getVolume(), is(14L));
        }
    }

    @DisplayName("operates correctly on extreme values")
    @Nested
    class ExtremeIntervalsTest {

        @DisplayName("fills correctly when the difference is more than int")
        @Test
        void fillWater_success_fillMinMaxValues() {
            //given
            int[] inputArray = {MAX_VALUE, MIN_VALUE, MAX_VALUE, MIN_VALUE, MAX_VALUE};
            List<Interval> intervals = ImmutableList.of(
                    new Interval(0, 2),
                    new Interval(2, 4)
            );

            //when
            RainResult rainResult = waterFiller.fillWater(inputArray, intervals);

            //then
            long[] waterToFill = rainResult.getWaterToFill();
            long valueToAdd = ((long) MAX_VALUE) - MIN_VALUE;

            assertThat(rainResult.getSourceArray(), is(inputArray));

            assertThat(waterToFill[0], is(0L));
            assertThat(waterToFill[1], is(valueToAdd));
            assertThat(waterToFill[2], is(0L));
            assertThat(waterToFill[3], is(valueToAdd));
            assertThat(waterToFill[4], is(0L));

            assertThat(rainResult.getVolume(), is(valueToAdd * 2));
        }
    }

    @DisplayName("operates correctly on strange intervals")
    @Nested
    class StrangeIntervalsTest {

        @Test
        @DisplayName("allows interleaving intervals")
        void fillWater_success_interleavingIntervals() {
            //given
            int[] inputArray = {4, 0, 2, 1, 5};
            List<Interval> intervals = ImmutableList.of(
                    new Interval(0, 2),
                    new Interval(0, 4)
            );

            //when
            RainResult rainResult = waterFiller.fillWater(inputArray, intervals);

            //then
            long[] waterToFill = rainResult.getWaterToFill();
            assertThat(rainResult.getSourceArray(), is(inputArray));
            assertThat(rainResult.getVolume(), is(9L));

            assertThat(waterToFill[0], is(0L));
            assertThat(waterToFill[1], is(4L));
            assertThat(waterToFill[2], is(2L));
            assertThat(waterToFill[3], is(3L));
            assertThat(waterToFill[4], is(0L));
        }

        @Test
        @DisplayName("allows peak in center of interval")
        void fillWater_fail_intervalIsIncorrect() {
            //given
            int[] inputArray = {4, 5, 2, 1, 5};
            List<Interval> intervals = ImmutableList.of(
                    new Interval(0, 4)
            );

            //when
            RainResult rainResult = waterFiller.fillWater(inputArray, intervals);

            //then
            long[] waterToFill = rainResult.getWaterToFill();
            assertThat(rainResult.getSourceArray(), is(inputArray));
            assertThat(rainResult.getVolume(), is(5L));

            assertThat(waterToFill[0], is(0L));
            assertThat(waterToFill[1], is(0L));
            assertThat(waterToFill[2], is(2L));
            assertThat(waterToFill[3], is(3L));
            assertThat(waterToFill[4], is(0L));
        }
    }

    @DisplayName("operates correctly on empty values")
    @Nested
    class EmptyValuesTest {

        @Test
        @DisplayName("throws exception when array is null")
        void fillWater_failure_arrayIsNull() {
            //given
            List<Interval> someNotEmptyList = ImmutableList.of(new Interval(0, 2));

            //when
            //then
            assertThrows(ValidationException.class,
                    () -> waterFiller.fillWater(null, someNotEmptyList),
                    "array to fill water should not be null");
        }

        @Test
        @DisplayName("throws exception when list is null")
        void fillWater_failure_listIsNull() {
            //given
            int[] someNotEmptyArray = {0, 2};

            //when
            //then
            assertThrows(ValidationException.class,
                    () -> waterFiller.fillWater(someNotEmptyArray, null),
                    "intervals to fill water should not be null");
        }

        @Test
        @DisplayName("throws exception when both array and list are null")
        void fillWater_failure_bothAreNull() {
            //given
            //when
            //then
            assertThrows(ValidationException.class,
                    () -> waterFiller.fillWater(null, null));
        }
    }
}