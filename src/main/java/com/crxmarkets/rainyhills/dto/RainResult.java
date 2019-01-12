package com.crxmarkets.rainyhills.dto;

import lombok.Data;

@Data
public class RainResult {
    private final int[] sourceArray;
    private final long[] waterToFill;
    private final long volume;
}