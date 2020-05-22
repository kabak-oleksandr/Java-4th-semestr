package ua.khpi.oop.kabak09_14;

import java.io.Serializable;

public class Graphic implements Serializable {
    private int startHourWork;
    private int startMinuteWork;
    private int endHourWork;
    private int endMinuteWork;

    Graphic(int startHourWork, int endHourWork,int startMinuteWork, int endMinuteWork) {
        this.startHourWork = startHourWork;
        this.endHourWork = endHourWork;
        this.startMinuteWork = startMinuteWork;
        this.endMinuteWork = endMinuteWork;
    }

    public int getEndHourWork() {
        return endHourWork;
    }

    public int getEndMinuteWork() {
        return endMinuteWork;
    }

    public int getStartMinuteWork() {
        return startMinuteWork;
    }

    public int getStartHourWork() {
        return startHourWork;
    }
}
