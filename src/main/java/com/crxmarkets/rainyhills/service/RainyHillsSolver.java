package com.crxmarkets.rainyhills.service;

import com.crxmarkets.rainyhills.dto.Interval;
import com.crxmarkets.rainyhills.dto.RainResult;
import com.crxmarkets.rainyhills.exception.ArrayParsingException;
import com.google.common.base.Charsets;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RainyHillsSolver {

    private static final Logger LOGGER = Logger.getLogger("RainyHillsSolver");

    private final IntervalsCalculator intervalsCalculator;
    private final WaterFiller waterFiller;

    @SneakyThrows
    public RainResult rain(MultipartFile file) {

        int[] inputArray;
        try (InputStreamReader in = new InputStreamReader(file.getInputStream(), Charsets.UTF_8);
             BufferedReader bufferedReader = new BufferedReader(in)) {

            String csvFileString = bufferedReader
                    .lines().collect(Collectors.joining("\n"));

            LOGGER.info("Raw file contents: " + csvFileString);

            inputArray = Arrays.stream(csvFileString.split(","))
                    .map(Integer::parseInt)
                    .mapToInt(x -> x)
                    .toArray();
        } catch (Exception ex) {
            LOGGER.log(Level.WARNING, ex.getMessage(), ex);
            throw new ArrayParsingException(ex.getMessage());
        }

        return rain(inputArray);
    }

    public RainResult rain(int[] inputArray) {
        List<Interval> intervalsToFill = intervalsCalculator.calculate(inputArray);

        return waterFiller.fillWater(inputArray, intervalsToFill);
    }
}
