package com.crxmarkets.rainyhills.service;

import com.crxmarkets.rainyhills.dto.RainResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.MIN_VALUE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Integration test.
 */
@DisplayName("RainyHillsSolver")
class RainyHillsSolverTest {

    private RainyHillsSolver rainyHillsSolver = new RainyHillsSolver(
            new IntervalsCalculator(), new WaterFiller()
    );


    @DisplayName("correctly solves all inputs")
    @ParameterizedTest
    @MethodSource("getData")
    void rain_success(RainResult word) {
        RainResult rainResult = rainyHillsSolver.rain(word.getSourceArray());

        assertThat(rainResult.getVolume(), is(word.getVolume()));
        assertThat(rainResult.getWaterToFill(), is(word.getWaterToFill()));
    }

    private static Stream<RainResult> getData() {
        return Stream.of(
                new RainResult(
                        new int[]{0, 1, 2, 1, 0},
                        new long[]{0L, 0L, 0L, 0L, 0L},
                        0L),
                new RainResult(
                        new int[]{0, 2, 3, 1, 2},
                        new long[]{0L, 0L, 0L, 1L, 0L},
                        1L),
                new RainResult(
                        new int[]{0, 2, 2, 1, 3},
                        new long[]{0L, 0L, 0L, 1L, 0L},
                        1L),
                new RainResult(
                        new int[]{0, 4, 1, 2, 1, 3},
                        new long[]{0L, 0L, 2L, 1L, 2L, 0L},
                        5L),
                new RainResult(
                        new int[]{1, 4, 1, 4, 1, 4},
                        new long[]{0L, 0L, 3L, 0L, 3L, 0L},
                        6L),
                new RainResult(
                        new int[]{1, 4, 4, 4, 1, 2, 4},
                        new long[]{0L, 0L, 0L, 0L, 3L, 2L, 0L},
                        5L),
                new RainResult(
                        new int[]{5},
                        new long[]{0},
                        0L),
                new RainResult(
                        new int[]{1, 3, 4, 2, 6, 7, 9, 10, 0, 5, 4, 5},
                        new long[]{0L, 0L, 0L, 2L, 0L, 0L, 0L, 0L, 5L, 0L, 1L, 0L},
                        8L),
                new RainResult(
                        new int[]{3, 2, 4, 1, 2},
                        new long[]{0L, 1L, 0L, 1L, 0L},
                        2L),
                new RainResult(
                        new int[]{4, 1, 1, 0, 2, 3},
                        new long[]{0L, 2L, 2L, 3L, 1L, 0L},
                        8L),
                new RainResult(
                        new int[]{MAX_VALUE, MIN_VALUE, MAX_VALUE, MIN_VALUE, MAX_VALUE},
                        new long[]{0L, ((long) MAX_VALUE) - MIN_VALUE, 0L, ((long) MAX_VALUE) - MIN_VALUE, 0},
                        (((long) MAX_VALUE) - MIN_VALUE) * 2),
                new RainResult(
                        new int[]{MIN_VALUE, MAX_VALUE, MIN_VALUE, MAX_VALUE, MIN_VALUE},
                        new long[]{0L, 0L, ((long) MAX_VALUE) - MIN_VALUE, 0L, 0L},
                        ((long) MAX_VALUE) - MIN_VALUE),
                new RainResult(
                        new int[]{},
                        new long[]{},
                        0L)
        );
    }
}