package com.crxmarkets.rainyhills.service;

import com.crxmarkets.rainyhills.dto.Interval;
import com.crxmarkets.rainyhills.dto.RainResult;
import com.google.common.base.Charsets;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RainyHillsSolver {

    private final IntervalsCalculator intervalsCalculator;
    private final WaterFiller waterFiller;

    @SneakyThrows
    public RainResult rain(MultipartFile file) {

        try (InputStreamReader in = new InputStreamReader(file.getInputStream(), Charsets.UTF_8);
             BufferedReader bufferedReader = new BufferedReader(in)) {

            String csvFileString = bufferedReader
                    .lines().collect(Collectors.joining("\n"));

            int[] inputArray = Arrays.stream(csvFileString.split(","))
                    .map(Integer::parseInt)
                    .mapToInt(x -> x)
                    .toArray();

            return rain(inputArray);
        }
    }

    public RainResult rain(int[] inputArray) {
        List<Interval> intervalsToFill = intervalsCalculator.calculate(inputArray);

        return waterFiller.fillWater(inputArray, intervalsToFill);
    }
}
