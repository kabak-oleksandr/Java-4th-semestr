package ua.khpi.oop.kabak09_14;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ThreadHelper {

    public static void test1() throws InterruptedException {
        int count = 0;
        System.out.println("Первий поток окончен");
        try {
            for (Store elem : Menu.stores) {
                if (!Thread.currentThread().isInterrupted()) {
                    for (int i = 0; i < elem.getTelephone().size(); i++) {
                        if (elem.getTelephone().get(i).toString().matches("(\\+*)\\d{6}")) {
                            count++;
                        }
                    }
                } else {
                    throw new InterruptedException();
                }
            }
            System.out.println("6-значных номеров: " + count);
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void test2() throws InterruptedException {
        int count = 0;
        System.out.println("Второй поток окончен");
        for (Store elem : Menu.stores) {
            if (!Thread.currentThread().isInterrupted()) {
                for (int i = 0; i < elem.getTelephone().size(); i++) {
                    if (elem.getTelephone().get(i).toString().matches("\\A(380)\\d{6,9}")) {
                        count++;
                    }
                }
            } else {
                throw new InterruptedException();
            }

        }System.out.println("Украинских номеров: " + count);
    }

    public static void test3() throws InterruptedException {
        int count = 0;
        System.out.println("Третий поток окончен");
        for (Store elem : Menu.stores) {
            if (!Thread.currentThread().isInterrupted()) {
                for (int i = 0; i < elem.getTelephone().size(); i++) {
                    if (!elem.getTelephone().get(i).toString().matches("\\A(380)\\d{6,9}")) {
                        count++;
                    }
                }
            } else {
                throw new InterruptedException();
            }

        }System.out.println("Остальных номеров: " + count);

    }

    public static void startThreads() {
        int timer = 20000;
        System.out.println("Задан таймер ["+(timer/1000)+"]: ");

        System.out.println("Запуск потоков...");

        Thread1 first = new Thread1();
        Thread t1 = new Thread(first, "Первий поток");

        Thread2 second = new Thread2();
        Thread t2 = new Thread(second, "Второй поток");

        Thread3 third = new Thread3();
        Thread t3 = new Thread(third, "Третий поток");

        t1.start();
        t2.start();
        t3.start();
        Timer timerTmp = new Timer(timer, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                System.out.println("Потоки прерваны...");
                t1.interrupt();
                t2.interrupt();
                t3.interrupt();
            }
        });
        timerTmp.setRepeats(false);
        timerTmp.start();
        try {
            t1.join();
            t2.join();
            t3.join();
            timerTmp.stop();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Завершены все потоки...");
    }

    public static long cParallel() {
        long time_start = System.currentTimeMillis();
        System.out.println("Запуск всех потоков...");
        Thread1 first = new Thread1();
        Thread t1 = new Thread(first, "Первий поток");

        Thread2 second = new Thread2();
        Thread t2 = new Thread(second, "Второй поток");

        Thread3 third = new Thread3();
        Thread t3 = new Thread(third, "Третий поток");

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Завершены все потоки...");
        return System.currentTimeMillis() - time_start;
    }

    public static long comparisonSequential() {
        long time_start = System.currentTimeMillis();
        System.out.println("Запустить последовательность");
        try {
            ThreadHelper.test1();
            ThreadHelper.test2();
            ThreadHelper.test3();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Последовательность окончена");
        return System.currentTimeMillis() - time_start;
    }

    static class Thread1 implements Runnable {
        public void run() {
            int count = 0;
            System.out.println("Первий поток запущен");
            try {
                for (Store elem : Menu.stores) {
                    if (!Thread.currentThread().isInterrupted()) {
                        for (int i = 0; i < elem.getTelephone().size(); i++) {
                            if (elem.getTelephone().get(i).toString().matches("(\\+*)\\d{6}")) {
                                count++;
                            }
                        }
                    } else {
                        throw new InterruptedException();
                    }
                }
                System.out.println("6-значних номеров: " + count);
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    static class Thread2 implements Runnable {
        public void run() {
            int count = 0;
            System.out.println("Второй поток запущен");
            try {
                for (Store elem : Menu.stores) {
                    if (!Thread.currentThread().isInterrupted()) {
                        for (int i = 0; i < elem.getTelephone().size(); i++) {
                            if (elem.getTelephone().get(i).toString().matches("\\A(380)\\d{6,9}")) {
                                count++;
                            }
                        }
                    } else {
                        throw new InterruptedException();
                    }

                }
                System.out.println("Украинских номеров: " + count);
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }

        }
    }

    static class Thread3 implements Runnable {
        public void run() {
            int count = 0;
            System.out.println("Третий поток окончен");
            try {
                for (Store elem : Menu.stores) {
                    if (!Thread.currentThread().isInterrupted()) {
                        for (int i = 0; i < elem.getTelephone().size(); i++) {
                            if (!elem.getTelephone().get(i).toString().matches("\\A(380)\\d{6,9}")) {
                                count++;
                            }
                        }
                    } else {
                        throw new InterruptedException();
                    }

                }
                System.out.println("Остальных номеров: " + count);
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}

