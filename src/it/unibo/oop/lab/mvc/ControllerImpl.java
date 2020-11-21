package it.unibo.oop.lab.mvc;

import java.util.ArrayList;
import java.util.List;

public class ControllerImpl implements Controller {

    private final List<String> strings = new ArrayList<>();
    private int currentIndex;

    private String getStringAtIndex(final int index) {
        if (this.strings.size() <= index) {
            throw new IllegalStateException();
        } else {
            return this.strings.get(index);
        }
    }

    @Override
    public String getCurrentString() {
        return this.getStringAtIndex(this.currentIndex);
    }

    @Override
    public void setNextString(final String s) {
        if (s == null || s.equals("")) {
            throw new IllegalArgumentException();
        }
        this.strings.add(s);
    }

    @Override
    public String getNextString() {
        return this.getStringAtIndex(this.currentIndex);
    }

    @Override
    public List<String> getStringHistory() {
        return new ArrayList<String>(this.strings);
    }

    @Override
    public void printCurrentString() {
        System.out.println(this.getCurrentString());
        this.currentIndex++;
    }

}
