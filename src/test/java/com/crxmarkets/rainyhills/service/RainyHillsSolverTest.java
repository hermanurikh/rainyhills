package com.crxmarkets.rainyhills.service;

import com.crxmarkets.rainyhills.dto.Interval;
import com.crxmarkets.rainyhills.dto.RainResult;
import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("RainyHillsSolver")
class RainyHillsSolverTest {

    @Mock
    private IntervalsCalculator intervalsCalculator;
    @Mock
    private WaterFiller waterFiller;

    private RainyHillsSolver rainyHillsSolver;

    @BeforeEach
    void setUp() {
        rainyHillsSolver = new RainyHillsSolver(intervalsCalculator, waterFiller);
    }

    @DisplayName("rains correctly")
    @Test
    void rain() {
        //given
        int[] inputArray = {4, 3, 4};
        List<Interval> intervals = ImmutableList.of(
                new Interval(0, 2)
        );
        RainResult expectedResult = Mockito.mock(RainResult.class);

        when(intervalsCalculator.calculate(inputArray)).thenReturn(intervals);
        when(waterFiller.fillWater(inputArray, intervals)).thenReturn(expectedResult);

        //when
        RainResult result = rainyHillsSolver.rain(inputArray);

        //then
        assertThat(result, is(expectedResult));
    }
}