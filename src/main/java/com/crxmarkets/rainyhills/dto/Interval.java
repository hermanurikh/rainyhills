package com.crxmarkets.rainyhills.dto;

import lombok.Data;

@Data
public class Interval {
    /**
     * Array index for left edge.
     */
    private final int from;
    /**
     * Array index for right edge.
     */
    private final int to;
}