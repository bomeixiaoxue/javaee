package com.example.day01_thread.demo;

/**
 * @author hao
 * @date 2019-09-08 16:42
 * description 例子：创建三个窗口卖票，总票数为100张.
 * 1.问题：卖票过程中，出现了重票、错票 -->出现了线程的安全问题
 * 2.问题出现的原因：当某个线程操作车票的过程中，尚未操作完成时，其他线程参与进来，也操作车票。
 * 3.如何解决：当一个线程a在操作ticket的时候，其他线程不能参与进来。直到线程a操作完ticket时，其他
 *            线程才可以开始操作ticket。这种情况即使线程a出现了阻塞，也不能被改变。
 *
 * 4.在Java中，我们通过同步机制，来解决线程的安全问题。
 *  方式一：同步代码块
 *   synchronized(同步监视器){
 *      //需要被同步的代码
 *   }
 *  说明：1.操作共享数据的代码，即为需要被同步的代码。  -->不能包含代码多了，也不能包含代码少了。
 *       2.共享数据：多个线程共同操作的变量。比如：ticket就是共享数据。
 *       3.同步监视器，俗称：锁。任何一个类的对象，都可以充当锁。
 *          要求：多个线程必须要共用同一把锁。
 *       补充：在实现Runnable接口创建多线程的方式中，我们可以考虑使用this充当同步监视器。
 *  方式二：同步方法。
 *     如果操作共享数据的代码完整的声明在一个方法中，我们不妨将此方法声明同步的。
 *  5.同步的方式，解决了线程的安全问题。---好处
 *    操作同步代码时，只能有一个线程参与，其他线程等待。相当于是一个单线程的过程，效率低。 ---局限性
 *
 *  不管使用哪种方法，synchronized锁的对象一定要唯一
 */
//继承类，存在线程安全问题
class TicketThread extends Thread{

    private int totalTicket = 100;
    private static int ticket = 1;
    private int index = 1;

    public TicketThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        while (ticket <= totalTicket) {
            System.err.println(index + "_" + Thread.currentThread().getName() + ticket);
            index++;
            ticket++;
        }
    }
}

//接口方式，存在线程安全问题
class TicketRunnable implements Runnable{

    private int totalTicket;
    private int ticket = 1;
    private int index = 1;

    public TicketRunnable(int totalTicket) {
        this.totalTicket = totalTicket;
    }

    @Override
    public void run() {
        while (ticket <= totalTicket) {
            System.err.println(index + "_" + Thread.currentThread().getName() + ticket);
            index++;
            ticket++;
        }
    }
}

public class Thread4WindowSellTicket {

    public static void main(String[] args) {
        /**
         * 存在线程安全问题
         */
//        TicketThread thread1 = new TicketThread("窗口1、卖票，票号为: ");
//        TicketThread thread2 = new TicketThread("窗口2、卖票，票号为: ");
//        TicketThread thread3 = new TicketThread("窗口3、卖票，票号为: ");
//        TicketThread thread4 = new TicketThread("窗口4、卖票，票号为: ");
//        TicketThread thread5 = new TicketThread("窗口5、卖票，票号为: ");
//        thread1.start();
//        thread2.start();
//        thread3.start();
//        thread4.start();
//        thread5.start();

//        TicketRunnable ticketRunnable = new TicketRunnable(100);
//        Thread thread1 = new Thread(ticketRunnable, "窗口1、卖票，票号为: ");
//        Thread thread2 = new Thread(ticketRunnable, "窗口2、卖票，票号为: ");
//        Thread thread3 = new Thread(ticketRunnable, "窗口3、卖票，票号为: ");
//        Thread thread4 = new Thread(ticketRunnable, "窗口4、卖票，票号为: ");
//        Thread thread5 = new Thread(ticketRunnable, "窗口5、卖票，票号为: ");
//        thread1.start();
//        thread2.start();
//        thread3.start();
//        thread4.start();
//        thread5.start();

        /**
         * 解决线程安全问题
         */
        TicketThreadSafety thread1 = new TicketThreadSafety("窗口1、卖票，票号为: ");
        TicketThreadSafety thread2 = new TicketThreadSafety("窗口2、卖票，票号为: ");
        TicketThreadSafety thread3 = new TicketThreadSafety("窗口3、卖票，票号为: ");
        TicketThreadSafety thread4 = new TicketThreadSafety("窗口4、卖票，票号为: ");
        TicketThreadSafety thread5 = new TicketThreadSafety("窗口5、卖票，票号为: ");
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();

        /*TicketRunnableSafety TicketRunnableSafety = new TicketRunnableSafety(100);
        Thread thread1 = new Thread(TicketRunnableSafety, "窗口1、卖票，票号为: ");
        Thread thread2 = new Thread(TicketRunnableSafety, "窗口2、卖票，票号为: ");
        Thread thread3 = new Thread(TicketRunnableSafety, "窗口3、卖票，票号为: ");
        Thread thread4 = new Thread(TicketRunnableSafety, "窗口4、卖票，票号为: ");
        Thread thread5 = new Thread(TicketRunnableSafety, "窗口5、卖票，票号为: ");
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();*/

    }
}

//继承类，解决线程安全问题
class TicketThreadSafety extends Thread{

    private static int totalTicket = 100;
    private static int ticket = 1;
    private static int index = 1;
    private static Object object = new Object();

    public TicketThreadSafety(String name) {
        super(name);
    }

    @Override
    public void run() {
        while (true) {
            //同步代码块
            /*synchronized(object) {
                if (ticket <= totalTicket) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.err.println(index + "_" + Thread.currentThread().getName() + ticket);
                    index++;
                    ticket++;
                }else {
                    break;
                }
            }*/
            //同步方法
            show();
            if (ticket > totalTicket) {
                break;
            }
        }
    }

    public static synchronized void show() {
        if (ticket <= totalTicket) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.err.println(index + "_" + Thread.currentThread().getName() + ticket);
            index++;
            ticket++;
        }
    }
}

//接口方式，存解决线程安全问题
class TicketRunnableSafety implements Runnable{

    private int totalTicket;
    private int ticket = 1;
    private int index = 1;

    public TicketRunnableSafety(int totalTicket) {
        this.totalTicket = totalTicket;
    }

    @Override
    public void run() {
        while (true) {
            //同步代码块
//            synchronized (this) {
//                if (ticket <= totalTicket) {
//                    try {
//                        Thread.sleep(100);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    System.err.println(index + "_" + Thread.currentThread().getName() + ticket);
//                    index++;
//                    ticket++;
//                }else {
//                    break;
//                }
//            }
            //同步方法
            show();
            if (ticket > totalTicket) {
                break;
            }
        }
    }

    public synchronized void show() {
        if (ticket <= totalTicket) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.err.println(index + "_" + Thread.currentThread().getName() + ticket);
            index++;
            ticket++;
        }
    }
}


