package com.example.day01_thread.demo;

/**
 * @author hao
 * @date 2019-09-08 15:56
 * description
 * 多线程的创建，方式一：继承于Thr
 * 1. 创建一个继承于Thread类
 * 2. 重写Thread类的run(
 * 3. 创建Thread类的子类的对
 * 4. 通过此对象调用start()
 * <p>
 * 例子：遍历100以内的所有的偶数
 */

class MyThread extends Thread{

    @Override
    public void run() {
        for (int i = 0; i <= 100; i++) {
            if (i % 2 == 0) {
                System.err.println(i);
            }
        }
    }
}

public class Thread1 {
    public static void main(String[] args) {
        MyThread threadTest = new MyThread();
        threadTest.start();
    }
}
