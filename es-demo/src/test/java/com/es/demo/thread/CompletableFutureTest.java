package com.es.demo.thread;

import org.junit.Test;

import java.sql.Time;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author walle
 * @version 1.0
 * @create 2019-06-30
 */
public class CompletableFutureTest {

    @Test
    public void test(){
        String join = CompletableFuture.supplyAsync(() -> "helle").thenApplyAsync(s -> s + " world").join();
        System.out.println(join);
    }


    @Test
    public void test2(){
        CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello";
        }).thenAcceptAsync(s -> System.out.println(s));
        while (true){

        }
    }

    @Test
    public void test3(){
        CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello";
        }).thenRunAsync(() -> {
            System.out.println("你猜我是谁？");
        });

        while (true){

        }
    }

    @Test
    public void test4(){

        String join = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello";
        }).thenCombine(CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(6);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "world";
        }), (s1, s2) -> s1 + "  " + s2).join();

        System.out.println(join);
    }

}
