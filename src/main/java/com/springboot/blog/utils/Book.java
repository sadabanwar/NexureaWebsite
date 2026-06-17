package com.springboot.blog.utils;

public class Book {
    private String author;

    public Book(String id, String name, double price, int quantity, String author) {
        super();
        this.author = author;
    }



    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}