package com.crxmarkets.rainyhills.service;

import com.crxmarkets.rainyhills.dto.Interval;
import com.crxmarkets.rainyhills.exception.ValidationException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;

@Component
class IntervalsCalculator {
    /**
     * Return the list of intervals to be later filled with water.
     * @param array input array, not null
     * @return a list of intervals
     */
    List<Interval> calculate(int[] array) {
        checkNotNull(array);
        if (array.length == 0) {
            return emptyList();
        }

        List<Interval> resultIntervals = new ArrayList<>();

        //index of left edge from which we need to fill water to the right edge
        int leftEdgeIndex = 0;
        int leftEdge = array[leftEdgeIndex];

        //current element, lower than left edge, but close to it as possible in terms of height
        int closestLowestIndex = -1;
        int closestLowest = Integer.MIN_VALUE;

        for (int i = 1; i < array.length; i++) {
            int currentValue = array[i];
            if (currentValue >= leftEdge) {
                if (closestLowestIndex == -1) {
                    //there was no one lower than left edge, just remember the new left edge
                    leftEdgeIndex = i;
                    leftEdge = currentValue;
                } else {
                    //there was something lower than left edge, create an interval
                    resultIntervals.add(new Interval(leftEdgeIndex, i));
                    leftEdgeIndex = i;
                    leftEdge = currentValue;
                    closestLowestIndex = -1;
                    closestLowest = Integer.MIN_VALUE;
                }
            } else {
                //new element is lower than left edge
                if (currentValue > closestLowest || closestLowestIndex == -1) {
                    //remember it only if it is higher than current highest value (lower than left edge, of course)
                    closestLowest = currentValue;
                    closestLowestIndex = i;
                }
            }
        }

        //when array has ended, draw an interval to current lowest from left edge
        if (closestLowestIndex != -1 && !edgesAreClose(leftEdgeIndex, closestLowestIndex)) {
            Interval lastInterval = new Interval(leftEdgeIndex, closestLowestIndex);
            //draw it only if it is not there already - this can be the case when on last element we already drew an interval
            if (!resultIntervals.contains(lastInterval)) {
                resultIntervals.add(lastInterval);
            }
        }

        return resultIntervals;
    }

    private void checkNotNull(int[] array) {
        if (array == null) {
            throw new ValidationException("array to calculate intervals should not be null");
        }
    }

    private boolean edgesAreClose(int firstIndex, int secondIndex) {
        return secondIndex - firstIndex == 1;
    }
}
