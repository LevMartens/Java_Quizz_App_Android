package com.example.Controller;

import com.example.melbournequizzapp.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class Observer {

    List<MainActivity> listeners = new ArrayList<MainActivity>();

    public void addListener(MainActivity mainActivity) {
        listeners.add(mainActivity);
    }
    public void removeListener(MainActivity mainActivity) {
        listeners.remove(mainActivity);
    }
    public void notifyListeners() {
        for (MainActivity sub: listeners) {
            sub.updateUIOnFireStoreFetchCompletion();
        }
    }

}
