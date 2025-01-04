package dap.spotifyAPI.observer;

import java.util.ArrayList;
import java.util.List;

public abstract class Subject {
    private final List<Observer> observers;

    public Subject() {
        this.observers = new ArrayList<>();
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }
}
