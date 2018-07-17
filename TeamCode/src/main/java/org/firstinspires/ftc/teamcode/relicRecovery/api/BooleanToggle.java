package org.firstinspires.ftc.teamcode.relicRecovery.api;

/**
 * Created by Lukens on 10/16/2017.
 */

public class BooleanToggle {

    private boolean lastInput;
    private boolean value;

    public void update(boolean input){

        if(input && !lastInput){
            lastInput = true;
            value = !value;
        }
        if(!input)
            lastInput = false;
    }

    public boolean getValue() {
        return value;
    }

    public void setValue(boolean value){
        this.value = value;
    }

    public BooleanToggle(){

    }

    public BooleanToggle(boolean toggled){
        setValue(toggled);
    }
}
