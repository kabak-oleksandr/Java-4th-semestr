package Lab_15;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class Store implements Serializable {
    private String name;
    private String address;
    private String specialization;
    private ArrayList<Integer> telephone;
    private Graphic[] graphic;

    Store(String name, String address, ArrayList<Integer> telephone, Graphic[] graphic, String specialization) {
        this.name = name;
        this.address = address;
        this.telephone = telephone;
        this.graphic = graphic;
        this.specialization = specialization;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getSpecialization() {
        return specialization;
    }

    public ArrayList<Integer> getTelephone() {
        return telephone;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("\n\nНазвание: " + this.name + "\n  Адресс: " + this.address + "\n  Номер(а) телефона(ов):" + this.telephone + "\n  Специализация:" + this.specialization);

        result.append("\n  Расписание работы магазина:" +
                "\n\tПн:" + graphic[0].getStartHourWork() + ":" + graphic[0].getStartMinuteWork() + "-" + graphic[0].getEndHourWork() + ":" + graphic[0].getEndMinuteWork() +
                "\n\tВт:" + graphic[1].getStartHourWork() + ":" + graphic[1].getStartMinuteWork() + "-" + graphic[1].getEndHourWork() + ":" + graphic[1].getEndMinuteWork() +
                "\n\tCр:" + graphic[2].getStartHourWork() + ":" + graphic[2].getStartMinuteWork() + "-" + graphic[2].getEndHourWork() + ":" + graphic[2].getEndMinuteWork() +
                "\n\tЧт:" + graphic[3].getStartHourWork() + ":" + graphic[3].getStartMinuteWork() + "-" + graphic[3].getEndHourWork() + ":" + graphic[3].getEndMinuteWork() +
                "\n\tПт:" + graphic[4].getStartHourWork() + ":" + graphic[4].getStartMinuteWork() + "-" + graphic[4].getEndHourWork() + ":" + graphic[4].getEndMinuteWork() +
                "\n\tСб:" + graphic[5].getStartHourWork() + ":" + graphic[5].getStartMinuteWork() + "-" + graphic[5].getEndHourWork() + ":" + graphic[5].getEndMinuteWork() +
                "\n\tВс:" + graphic[6].getStartHourWork() + ":" + graphic[6].getStartMinuteWork() + "-" + graphic[6].getEndHourWork() + ":" + graphic[6].getEndMinuteWork());
        String endResult = new String(result);
        return endResult;
    }

}