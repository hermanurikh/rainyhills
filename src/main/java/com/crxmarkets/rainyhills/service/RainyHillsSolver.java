package com.crxmarkets.rainyhills.service;

import com.crxmarkets.rainyhills.dto.Interval;
import com.crxmarkets.rainyhills.dto.RainResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RainyHillsSolver {

    private final IntervalsCalculator intervalsCalculator;
    private final WaterFiller waterFiller;

    public RainResult rain(int[] inputArray) {
        List<Interval> intervalsToFill = intervalsCalculator.calculate(inputArray);

        return waterFiller.fillWater(inputArray, intervalsToFill);
    }
}
