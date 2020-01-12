package com.unittest.app.hr;

import org.junit.Test;

import java.util.Scanner;
import java.util.Stack;

/**
 * ref: https://www.hackerrank.com/challenges/maximum-element/problem
 *
 */
public class MaxStackElement {

    @Test
    public void doThis(){
        performTask( "10\n" +
                        "1 97\n" +
                        "2\n" +
                        "1 20\n" +
                        "2\n" +
                        "1 26\n" +
                        "1 20\n" +
                        "2\n" +
                        "3\n" +
                        "1 91\n" +
                        "3");
    }

    public static void performTask(String s){
        // create a new scanner
        // with the specified String Object
        Scanner scanner = new Scanner(s);
        int N = scanner.nextInt();
        Stack<Integer> integerStack = new Stack<>();
        Stack<Integer> maxIntStack = new Stack<>();

        for (int i = 0; i < N; i++) {
            int commandCode = scanner.nextInt();
            switch (commandCode){
                case 1:
                    int numToPush = scanner.nextInt();
                    integerStack.push(numToPush);
                    if (maxIntStack.isEmpty() || maxIntStack.peek() <= numToPush){
                        maxIntStack.push(numToPush);
                    }
                    break;
                case 2:
                    if (integerStack.isEmpty()){break;}
                    if (!maxIntStack.isEmpty() && maxIntStack.peek().equals(integerStack.peek())){
                        maxIntStack.pop();
                    }
                    integerStack.pop();
                    break;
                case 3:
                    System.out.println(maxIntStack.peek());
                    break;
            }
        }



    }
}
