package com.example.javalearn;

public class ClassLock {

    private static Object lock = new Object();

    /**
     * 锁住静态方法
     *
     * @throws InterruptedException
     */
    public static synchronized void methodLock() throws InterruptedException {
        System.out.println(Thread.currentThread().getName());
        Thread.sleep(10 * 1000);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            Thread worker = new Thread(new ClassLockWorker(i));
            worker.setName("kite-" + i);
            worker.start();
        }
    }

    /**
     * 锁住静态变量
     *
     * @throws InterruptedException
     */
    public void lockStaticObjectField() throws InterruptedException {
        synchronized (lock) {
            System.out.println(Thread.currentThread().getName());
            Thread.sleep(10 * 1000);
        }
    }

    /**
     * 锁住 xxx.class
     *
     * @throws InterruptedException
     */
    public void lockClass() throws InterruptedException {
        synchronized (ClassLock.class) {
            System.out.println(Thread.currentThread().getName());
            Thread.sleep(10 * 1000);
        }
    }

    public static class ClassLockWorker implements Runnable {

        int mType = 0;

        ClassLockWorker(int type) {
            mType = type;
        }

        @Override
        public void run() {
            try {
                ClassLock classLock = new ClassLock();
                switch (mType) {
                    case 1:
                    case 3:
                    case 5:
                        // 方式 1
                        classLock.lockStaticObjectField();
                        break;
                    case 2:
                    case 4:
                    case 6:
                        ClassLock.methodLock();
                        break;
                    case 0:
                    case 7:
                    case 8:
                    case 9:
                        // 方式 1
                        classLock.lockClass();
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}