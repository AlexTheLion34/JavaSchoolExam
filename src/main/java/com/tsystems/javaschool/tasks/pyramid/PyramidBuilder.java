package com.tsystems.javaschool.tasks.pyramid;

import java.util.*;

public class PyramidBuilder {

    public static void main(String[] args) {
        PyramidBuilder pyramidBuilder = new PyramidBuilder();
        pyramidBuilder.printPyramid(buildPyramid(Arrays.asList(11, 1, 21, 12, 3, 16, 2, 13, 9, 4, 17, 5, 14, 10, 18, 8, 7, 19, 15, 6, 20)));
        pyramidBuilder.printPyramid(buildPyramid(Collections.nCopies(Integer.MAX_VALUE - 1, 0)));
    }

    /**
     * Builds a pyramid with sorted values (with minumum value at the top line and maximum at the bottom,
     * from left to right). All vacant positions in the array are zeros.
     *
     * @param inputNumbers to be used in the pyramid
     * @return 2d array with pyramid inside
     * @throws {@link CannotBuildPyramidException} if the pyramid cannot be build with given input
     */
    public static int[][] buildPyramid(List<Integer> inputNumbers) {
        for (Integer i : inputNumbers) {
            if (i == null) {
                throw new CannotBuildPyramidException("Cannot build pyramid: " + i + " was given");
            }
        }
        Queue<Integer> heap;
        try {
            heap = new PriorityQueue<>(inputNumbers);
        } catch (Error e) {
            throw new CannotBuildPyramidException("Cannot build pyramid: " + e.getMessage());
        }
        int height = calculateHeight(inputNumbers.size());
        int width = 2 * height - 1;

        int[][] pyramid = new int[height][width];
        int row = 0;
        while (!heap.isEmpty()) {
            int count = 0;
            int column = width / 2 - row;
            while (count != row + 1) {
                pyramid[row][column] = heap.remove();
                count++;
                column += 2;
            }
            row++;
        }
        return pyramid;
    }

    private static int calculateHeight(int size) {
        int height = 0;
        while (size > 0) {
            size -= height;
            height++;
        }
        if (size < 0) {
            throw new CannotBuildPyramidException("Cannot build pyramid: wrong amount of elements");
        } else {
            return --height;
        }
    }

    protected void printPyramid(int[][] pyramid) {
        for (int[] row : pyramid) {
            System.out.println(Arrays.toString(row));
        }
    }
}
