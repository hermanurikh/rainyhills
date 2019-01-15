package com.crxmarkets.rainyhills.service;

import com.crxmarkets.rainyhills.dto.Interval;
import com.crxmarkets.rainyhills.exception.ValidationException;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static java.util.Collections.emptyList;

@Component
class IntervalsCalculator {
    /**
     * Return the list of intervals to be later filled with water.
     *
     * @param array input array, not null
     * @return a list of intervals
     */
    List<Interval> calculate(int[] array) {
        checkNotNull(array);
        if (array.length == 0) {
            return emptyList();
        }

        Edge[] edges = new Edge[array.length];

        for (int i = 0; i < array.length; i++) {
            edges[i] = new Edge(array[i], i);
        }

        Arrays.sort(edges, Comparator.comparingInt(edge -> edge.height));

        List<Interval> intervals = new ArrayList<>(array.length - 1);

        for (int i = edges.length - 1; i > 0; i--) {
            intervals.add(new Interval(edges[i].originalIndex, edges[i - 1].originalIndex));
        }

        //resulting intervals are in the order from highest to next highest and so on
        return intervals;
    }

    @Data
    private static class Edge {
        private final int height;
        private final int originalIndex;
    }

    private void checkNotNull(int[] array) {
        if (array == null) {
            throw new ValidationException("array to calculate intervals should not be null");
        }
    }
}
