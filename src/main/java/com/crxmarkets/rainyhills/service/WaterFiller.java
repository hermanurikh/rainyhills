package com.crxmarkets.rainyhills.service;

import com.crxmarkets.rainyhills.dto.Interval;
import com.crxmarkets.rainyhills.dto.RainResult;
import com.crxmarkets.rainyhills.exception.ValidationException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Component
class WaterFiller {

    /**
     * Fills the water.
     * Intervals can be directed both ways, that is why there is a swap.
     * Interleaving is allowed, one interval can interleave another one.
     *
     * @param sourceArray     input array
     * @param intervalsToFill intervals to fill
     * @return result of filling
     */
    RainResult fillWater(int[] sourceArray, List<Interval> intervalsToFill) {

        validateInput(sourceArray, intervalsToFill);

        long[] waterToFill = new long[sourceArray.length];
        AtomicLong totalVolume = new AtomicLong();

        intervalsToFill.forEach(interval -> {
            int from = interval.getFrom();
            int to = interval.getTo();

            int left = sourceArray[from];
            int right = sourceArray[to];

            if (from > to) {
                //intervals can be bi-directed
                from = from ^ to;
                to = from ^ to;
                from = from ^ to;
            }

            int levelToFill = getLevelToFill(left, right);

            for (int i = from + 1; i < to; i++) {

                long waterDelta = ((long) levelToFill) - sourceArray[i];
                //the peak may be higher than to or from, so 0 then
                long realWaterDelta = waterDelta >= 0 ? waterDelta : 0;

                //we may have already filled with something, so fill higher only if needed
                if (realWaterDelta > waterToFill[i]) {
                    //previous filling was incorrect, remove it
                    totalVolume.addAndGet(-waterToFill[i]);
                    //fill with new one
                    waterToFill[i] = realWaterDelta;
                    //and count it
                    totalVolume.addAndGet(realWaterDelta);
                }
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

    private int getLevelToFill(int left, int right) {
        return left > right
                ? right
                : left;
    }
}