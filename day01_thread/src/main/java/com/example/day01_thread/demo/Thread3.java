package com.example.day01_thread.demo;

/**
 * @author hao
 * @date 2019-09-08 16:04
 * description 创建多线程-匿名内部类写法
 */
public class Thread3 {
    public static void main(String[] args) {
        new Thread("测试线程"){
            @Override
            public void run() {
                for (int i = 0; i < 101; i++) {
                    System.err.println(Thread.currentThread().getName() + ": " + i);
                }
            }
        }.start();

    }
}
