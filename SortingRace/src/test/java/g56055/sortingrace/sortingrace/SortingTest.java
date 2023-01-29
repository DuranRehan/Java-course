package g56055.sortingrace.sortingrace;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author g56055
 */
public class SortingTest {

    public SortingTest() {
    }

    @Test
    public void testBubbleSort() {
        int[] array = {2, 1, 4, 6, 3, 5};
        int[] sortedArray = {1, 2, 3, 4, 5, 6};
        Sorting control = new Sorting("BUBBLE",array);
        control.bubbleSort(array);

        assertArrayEquals(array, sortedArray);
    }

    @Test
    public void testPositive() {
        int[] actual = {5, 1, 6, 2, 3, 4};
        int[] expected = {1, 2, 3, 4, 5, 6};
        Sorting control = new Sorting("MERGE",actual);
        control.mergeSort(actual);
        assertArrayEquals(expected, actual);
    }
}
