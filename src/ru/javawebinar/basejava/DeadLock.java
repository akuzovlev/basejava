package ru.javawebinar.basejava;

/**
 * Created by kuzovlea on 15.11.2017.
 */
public class DeadLock {

    private static final Object o1 = new Object();
    private static final Object o2 = new Object();

    public static void main(String[] args) throws InterruptedException {

        Thread thread1 = new Thread(new Runnable() {

            @Override
            public void run() {
                synchronized (o1) {
                    System.out.println(Thread.currentThread().getName() + " first lock");

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (o2) {
                        System.out.println(Thread.currentThread().getName() + " second lock");
                    }
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {

            @Override
            public void run() {
                synchronized (o2) {
                    System.out.println(Thread.currentThread().getName() + " first lock");
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (o1) {
                        System.out.println(Thread.currentThread().getName() + " second lock");
                    }
                }
            }
        });

        thread1.start();
        thread2.start();

        System.out.println(thread1.getState() +" "+ thread2.getState());

    }

}
