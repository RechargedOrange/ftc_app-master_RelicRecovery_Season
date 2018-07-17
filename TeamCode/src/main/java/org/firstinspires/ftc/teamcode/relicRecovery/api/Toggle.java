package org.firstinspires.ftc.teamcode.relicRecovery.api;

/**
 * Created by Lukens on 10/16/2017.
 */

public class Toggle {

    private Runnable toggleRun;

    private boolean lastInput;

    public void update(boolean input){
        if(input && !lastInput){
            lastInput = true;
            toggleRun.run();
        }
        if(!input)
            lastInput = false;
    }

    public Runnable getToggleRun() {
        return toggleRun;
    }

    public void setToggleRun(Runnable toggleRun) {
        this.toggleRun = toggleRun;
    }
}
