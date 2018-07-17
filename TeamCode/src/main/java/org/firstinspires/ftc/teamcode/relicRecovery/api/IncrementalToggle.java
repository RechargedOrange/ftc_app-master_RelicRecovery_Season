package org.firstinspires.ftc.teamcode.relicRecovery.api;

/**
 * Created by Lukens on 10/16/2017.
 */

public class IncrementalToggle {

    private int value;

    private boolean lastInput;

    public void update(boolean input){
        if(input && !lastInput){
            lastInput = true;
            value++;
        }
        if(!input)
            lastInput = false;
    }

    public void setValue(int value) {
        this.value = value;
    }


    public int getValue() {
        return value;
    }

    public IncrementalToggle(int value) {
        this.value = value;
    }

    public IncrementalToggle() {
    }
}
