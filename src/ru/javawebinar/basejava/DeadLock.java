package ru.javawebinar.basejava;

/**
 * Created by kuzovlea on 15.11.2017.
 */
public class DeadLock {

    public static void main(String[] args) throws InterruptedException {

        Thread thread1 = new Thread(new Runnable() {

            @Override
            public void run() {
                synchronized (this) {
                    int i = 5;
                    try {
                        method(Thread.currentThread());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                System.out.println(Thread.currentThread().getName() + ", " + Thread.currentThread().getState());
            }
        });

        thread1.start();
        thread1.join();
        System.out.println(thread1.getState());

    }

    synchronized static void method(Thread thread) throws InterruptedException {
        thread.join();
    }
}
