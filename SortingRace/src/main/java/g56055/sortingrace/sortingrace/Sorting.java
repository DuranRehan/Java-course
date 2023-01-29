package g56055.sortingrace.sortingrace;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sorting extends Thread implements Observable {
    private final String nameSort;
    private int[] array;
    private int size;
    private long operation;
    private Duration duration;
    private final List<Observer> observers;

    public Sorting(String name, int[] arr) {
        this.nameSort = name;
        this.array = arr;
        this.observers = new ArrayList<>();
        this.size = arr.length;
        this.operation = 0;
    }

    public Sorting(String name, int n) {
        this.nameSort = name;
        this.array = initializeArray(n);
        this.observers = new ArrayList<>();
        this.size = array.length;
        this.operation = 0;
    }

    public void setArray(int n) {
        this.array = new int[n];
        this.array = Arrays.copyOf(initializeArray(n), n);
        this.size = this.array.length;
    }

    public synchronized void bubbleSort(int[] arr) {
        operation = 0;
        int i = 0; //une
        int n = arr.length;//une
        boolean swapNeeded;//une
        operation += 3;
        while (i < n - 1) {
            swapNeeded = false;//une
            operation++;
            for (int j = 1; j < n - i; j++) {
                if (arr[j - 1] > arr[j]) {//une
                    int temp = arr[j - 1];//une
                    arr[j - 1] = arr[j];//une
                    arr[j] = temp;//une
                    swapNeeded = true;//une
                    operation += 5;
                }
            }
            if (!swapNeeded) {//une
                operation++;
                break;
            }
            i++;//une
            operation++;
        }
    }

    public void mergeSort(int[] arr) {
        operation = 0;
        operation += fusionSort(arr, arr.length);
    }

    private int fusionSort(int[] arr, int n) {
        int count = 0;
        if (n < 2) {//une
            count++;
            return count;
        }
        int mid = n / 2;//une
        int[] l = new int[mid];//une
        int[] r = new int[n - mid];//une
        count += 3;

        for (int i = 0; i < mid; i++) {
            l[i] = arr[i];//une
            count++;
        }
        for (int i = mid; i < n; i++) {
            r[i - mid] = arr[i];//une
            count++;
        }

        count += fusionSort(l, mid);

        count += fusionSort(r, n - mid);

        count += merge(arr, l, r, mid, n - mid);
        return count;
    }

    private long merge(int[] a, int[] l, int[] r, int left, int right) {
        int i = 0;//une
        int j = 0;//une
        int k = 0;//une
        operation += 3;
        while (i < left && j < right) {
            if (l[i] <= r[j]) {//une
                a[k++] = l[i++];//une
                operation += 2;
            } else {
                a[k++] = r[j++];//une
                operation++;
            }
        }
        while (i < left) {
            a[k++] = l[i++];//une
            operation++;
        }
        while (j < right) {
            a[k++] = r[j++];//une
            operation++;
        }
        return operation;
    }

    @Override
    public synchronized void run() {
        LocalDateTime startTime = LocalDateTime.now();
        if (this.nameSort.equals("BUBBLE")) {
            bubbleSort(this.array);
        } else {
            fusionSort(this.array, this.array.length);
        }
        LocalDateTime endTime = LocalDateTime.now();
        this.duration = Duration.between(startTime, endTime);
        notifyObserver();
    }

    @Override
    public void addObserver(Observer v) {
        this.observers.add(v);
    }

    @Override
    public void removeObserver(Observer v) {
        this.observers.remove(v);
    }

    @Override
    public void notifyObserver() {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }

    public String getNameSort() {
        return nameSort;
    }

    public int getSize() {
        return size;
    }

    public long getOperation() {
        return operation;
    }

    public int getDuration() {
        return (int) duration.toMillis();
    }

    public int[] initializeArray(int n) {
        int[] tempArray = new int[n];
        for (int i = 0; i < n; i++) {
            int random = (int) (Math.random() * 1000) + 1;
            tempArray[i] = random;
        }
        return tempArray;
    }
}
