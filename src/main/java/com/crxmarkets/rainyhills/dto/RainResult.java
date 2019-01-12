package com.crxmarkets.rainyhills.dto;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.Arrays;

public class RainResult {

    private final int[] sourceArray;
    private final long[] waterToFill;
    private final long volume;

    public RainResult(int[] sourceArray, long[] waterToFill, long volume) {
        //copy results not to store references to something that can be changed outside, this class should be immutable
        this.sourceArray = Arrays.copyOf(sourceArray, sourceArray.length);
        this.waterToFill = Arrays.copyOf(waterToFill, waterToFill.length);
        this.volume = volume;
    }

    @SuppressFBWarnings(
            value = "MALICIOUS_CODE",
            justification = "this getter is for jackson + tests only, no immutability needed"
    )
    public int[] getSourceArray() {
        return sourceArray;
    }

    @SuppressFBWarnings(
            value = "MALICIOUS_CODE",
            justification = "this getter is for jackson + tests only, no immutability needed"
    )
    public long[] getWaterToFill() {
        return waterToFill;
    }

    public long getVolume() {
        return volume;
    }
}