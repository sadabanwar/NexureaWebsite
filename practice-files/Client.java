package com.springboot.blog;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Client {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        ArrayCreator creator = new ArrayCreator(10); // Example with n=10
        Future<ArrayList<Integer>> future = executor.submit(creator);
        try {
            ArrayList<Integer> result = future.get();
            System.out.println(result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        executor.shutdown();
    }
}