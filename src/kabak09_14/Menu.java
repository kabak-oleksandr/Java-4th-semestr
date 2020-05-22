package ua.khpi.oop.kabak09_14;

import java.io.*;
import java.util.*;


public class Menu {

    static List<Store> stores = new List<Store>();
    Random random = new Random();

    Scanner in = new Scanner(System.in);
    int choose = 0;

    public void run(String[] arg) {
        if (arg.length == 0) {
            menu();
        } else if ("-auto".equals(arg[0])) {
            autoMode();
        }
    }

    private void menu() {
        readInfoFile();
        do {
            menuInfo();
            if (choose >= 0 && choose < 10) {
                switch (choose) {
                    case 1:
                        printStores();
                        break;
                    case 2:
                        add();
                        break;
                    case 3:
                        delete();
                        break;
                    case 4:
                        stores.clear();
                        break;
                    case 5:
                        sortMenu();
                        break;
                    case 6:
                        showAdvancedMenu();
                        break;
                    case 7:
                        ThreadHelper.startThreads();
                        break;
                    case 8:
                        long b = ThreadHelper.cParallel();
                        long j = ThreadHelper.comparisonSequential();

                        System.out.println("\n\n\nВремени затрачено на последовательное выполнение: " + j);
                        System.out.println("\nВремени затрачена на паралельное выполнение: " + b);
                        break;
                    case 9:
                        generateManyObj();
                }

            } else {
                System.out.println("Вы ввели некоректное значение(Выберите от 0 до 9)");
            }
        } while (choose != 0);
        System.out.println("____________________________________________________________________________________________\n\n\n");

        writeInfoFile();
    }

    private void autoMode() {
        readInfoFile();
        sortMenu();
        printStores();
        writeInfoFile();
    }

    private void menuInfo() {
        System.out.println("_______________________________________________________________________\nСправочник покупателя\n" +
                "1. Список торговых магазинов\n" +
                "2. Добавить торговый магазин\n" +
                "3. Убрать торговый магазин списка\n" +
                "4. Очистить весь список\n" +
                "5. Сортировка магазинов\n" +
                "6. Просмотр по номеру телефона\n" +
                "7. Запуск паралелного вычисления\n" +
                "8. Проверка по времени паралельного вычисления\n" +
                "0. Завершить работу\n\n" +
                "Выберите опцию:");

        choose = in.nextInt();
    }

    private void readInfoFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("C:\\Greed\\Study\\Java\\Lab9\\person.dat"))) {
            stores = (List<Store>) ois.readObject();
            System.out.println(stores);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void printStores() {
        if (stores.size() != 0) {
            System.out.println("Список магазинов:");
            System.out.println(stores.toString());
        } else {
            System.out.println("Список пуст!");
        }
    }

    private void add() {
        Scanner scan = new Scanner(System.in);
        Graphic[] graphic = new Graphic[7];
        ArrayList<Integer> telephone = new ArrayList<Integer>();
        int t = 0;

        String name = regName();
        String specialization = regSpecialization();

        System.out.println("Введите адресс:");
        String address = scan.nextLine();

        System.out.print("Введите номер телефона(Должен содержать больше 6 цифр): ");
        telephone = checkTelephone();

        for (int i = 0; i < 7; i++)
            graphic[i] = new Graphic(8, 18, 30, 30);


        Store store = new Store(name, address, telephone, graphic, specialization);
        stores.add(store);
    }

    private void delete() {
        Scanner in1 = new Scanner(System.in);
        System.out.println("Введите номер магазина, который желаете удалить");
        int ind = in1.nextInt();
        try {
            stores.remove(ind - 1);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Попробуйте ещё раз!");
        }
    }

    private void sortMenu() {
        System.out.println("Выберите нужную сортировку:\n" +
                "1. По названию\n" +
                "2. По назначению\n" +
                "3. По адресу\n\n" +
                "Выберите опцию:");

        choose = in.nextInt();
        switch (choose) {
            case 1:
                stores.sort(new Comparator<Store>() {
                                @Override
                                public int compare(Store o1, Store o2) {
                                    return ((Store) o1).getName().compareTo(((Store) o2).getName());
                                }
                            }
                        , stores.toArray(new Store[stores.size()]));
                break;

            case 2:
                stores.sort(new Comparator<Store>() {
                                @Override
                                public int compare(Store o1, Store o2) {
                                    return ((Store) o1).getSpecialization().compareTo(((Store) o2).getSpecialization());
                                }
                            }
                        , stores.toArray(new Store[stores.size()]));
                break;

            case 3:
                stores.sort(new Comparator<Store>() {
                                @Override
                                public int compare(Store o1, Store o2) {
                                    return ((Store) o1).getAddress().compareTo(((Store) o2).getAddress());
                                }
                            }
                        , stores.toArray(new Store[stores.size()]));
                break;
        }
    }

    private void writeInfoFile() {
        try (
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("person.dat"))) {
            oos.writeObject(stores);
        } catch (
                Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private String regName() {
        String tmp = in.nextLine();
        while (!tmp.matches("(^[A-Z])(\\D)([a-zA-Z[ -]]+)")) {
            System.out.println("Введите название корректно(с заглавной,без знаков(допускается \"-\"и \" \"))");
            tmp = in.nextLine();
        }
        for (Store tmpStore : stores) {
            if (tmp.equals(tmpStore)) {
                System.out.println("Данное название уже занято, попробуйте ещё раз");
                tmp = regName();
                break;
            }
        }
        return tmp;
    }

    private String regSpecialization() {
        String tmp = in.nextLine();
        while (!tmp.matches("(^[A-Z])(\\D)([a-zA-Z[ -]]+)")) {
            System.out.println("Введите название корректно(с заглавной,без знаков(допускается \"-\"и \" \"))");
            tmp = in.nextLine();
        }
        return tmp;
    }

    private ArrayList<Integer> checkTelephone() {
        ArrayList<Integer> tmp = new ArrayList<>();
        int tmpInt;
        do {
            tmpInt = in.nextInt();
            if (tmpInt > 99999) {
                tmp.add(tmpInt);
            }
            System.out.println("Желаете добавить ещё один номер телефона?(Нажмите 0 если не желаете добавлять)");
        } while (in.nextLine().equals("0"));
        return tmp;
    }

    private void showAdvancedMenu() {
        System.out.println("Выберите параметр для просмотра\n" +
                "1. По коротким номерам(6-значный)\n" +
                "2. По украинским номерам\n");
        choose = in.nextInt();
        switch (choose) {
            case 1:
                regPhoneShort();
                break;

            case 2:
                regUkrPhone();
                break;
        }
    }

    private void regPhoneShort() {
        for (Store tmp : stores) {
            for (int i = 0; i < tmp.getTelephone().size(); i++) {
                if (tmp.getTelephone().get(i).toString().matches("(\\+*)\\d{6}"))
                    System.out.println(tmp.toString());

            }
        }
    }

    private void regUkrPhone() {
        for (Store tmp : stores) {
            for (int i = 0; i < tmp.getTelephone().size(); i++) {
                if (tmp.getTelephone().get(i).toString().matches("\\A(380)\\d{6,9}"))
                    System.out.println(tmp.toString());
            }
        }
    }

    void addMany() {
        Graphic[] graphic = new Graphic[7];
        for (int i = 0; i < 7; i++)
            graphic[i] = new Graphic(8, 18, 30, 30);

        ArrayList<Integer> telephone = new ArrayList<Integer>();
        telephone.add(random.nextInt(999_000_001));

        Store store = new Store("Store", "Pushkina", telephone, graphic, "Clothes");
        stores.add(store);
    }

    void generateManyObj() {
        for (int i = 0; i < 3_000_000; i++) {
            addMany();
        }
    }
}
