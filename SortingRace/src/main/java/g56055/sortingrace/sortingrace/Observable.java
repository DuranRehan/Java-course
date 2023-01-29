package g56055.sortingrace.sortingrace;

import java.time.Duration;

public interface Observable {
    void addObserver(Observer v);

    void removeObserver(Observer v);

    void notifyObserver();
}
