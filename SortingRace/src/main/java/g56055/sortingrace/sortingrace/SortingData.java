package g56055.sortingrace.sortingrace;

import java.time.Duration;

public class SortingData {
    private final int size;
    private final long operation;
    private final int duration;
    private final String nameSort;

    public SortingData(Sorting sort) {
        this.size = sort.getSize();
        this.operation = sort.getOperation();
        this.duration = sort.getDuration();
        this.nameSort = sort.getNameSort();
    }

    public int getSize() {
        return size;
    }

    public long getOperation() {
        return operation;
    }

    public int getDuration() {
        return duration;
    }

    public String getNameSort() {
        return nameSort;
    }
}
