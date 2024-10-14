package com.example;

import java.util.Scanner;

import com.example.view.DisplayPrompt;

public class Main {
    public static void main(String[] args) {
        Scanner prompt = new Scanner(System.in);
        new DisplayPrompt(prompt).display();
    }
}