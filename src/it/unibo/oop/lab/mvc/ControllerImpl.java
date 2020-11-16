package it.unibo.oop.lab.mvc;

import java.util.ArrayList;
import java.util.List;

public class ControllerImpl implements Controller {
    
    private final List<String> strings = new ArrayList<>();
    private int currentIndex = -1;

    private String getStringAtIndex(final int index) throws IllegalStateException{
        if (this.strings.size() > index) {
            throw new IllegalStateException();
        }else {
            return this.strings.get(index);
        }
    }
    
    @Override
    public String getCurrentString() throws IllegalStateException {
        return this.getStringAtIndex(this.currentIndex);
    }

    @Override
    public void setNextString(final String s) throws IllegalArgumentException {
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

}
