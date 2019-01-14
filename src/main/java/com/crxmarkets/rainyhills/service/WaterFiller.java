package com.crxmarkets.rainyhills.service;

import com.crxmarkets.rainyhills.dto.Interval;
import com.crxmarkets.rainyhills.dto.RainResult;
import com.crxmarkets.rainyhills.exception.ValidationException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static java.lang.String.format;

@Component
class WaterFiller {

    /**
     * Fills the water.
     *
     * This method could be made concurrent, but performance tests show that there is literally no difference
     * between filling an array of one million and ten millions - the bottleneck is in intervalsCalculator which
     * is quite complex to split.
     * @param sourceArray input array
     * @param intervalsToFill intervals to fill
     * @return result of filling
     */
    RainResult fillWater(int[] sourceArray, List<Interval> intervalsToFill) {

        validateInput(sourceArray, intervalsToFill);

        long[] waterToFill = new long[sourceArray.length];
        AtomicLong totalVolume = new AtomicLong();

        intervalsToFill.forEach(interval -> {
            int left = sourceArray[interval.getFrom()];
            int right = sourceArray[interval.getTo()];

            int levelToFill = getLevelToFill(left, right);

            for (int i = interval.getFrom() + 1; i < interval.getTo(); i++) {
                validateNoInterleaving(waterToFill, i);

                long waterDelta = ((long) levelToFill) - sourceArray[i];

                validateWaterDelta(waterDelta, i, levelToFill, sourceArray[i]);

                waterToFill[i] = waterDelta;
                totalVolume.addAndGet(waterDelta);
            }
        });

        return new RainResult(
                sourceArray,
                waterToFill,
                totalVolume.get()
        );
    }

    private void validateInput(int[] sourceArray, List<Interval> intervalsToFill) {
        if (sourceArray == null) {
            throw new ValidationException("array to fill water should not be null");
        }

        if (intervalsToFill == null) {
            throw new ValidationException("intervals to fill water should not be null");
        }
    }

    private void validateNoInterleaving(long[] waterToFill, int i) {
        if (waterToFill[i] != 0) {
            throw new ValidationException(
                    format("interleaving of intervals should not happen, but index %d is already filled", i));
        }
    }

    private void validateWaterDelta(long waterDelta, int index, int levelToFill, int originalValue) {
        if (waterDelta < 0) {
            throw new ValidationException(
                    format("water delta should not be below zero: for index %d level to fill is %d with current value %d",
                            index, levelToFill, originalValue)
            );
        }
    }

    private int getLevelToFill(int left, int right) {
        return left > right
                ? right
                : left;
    }
}