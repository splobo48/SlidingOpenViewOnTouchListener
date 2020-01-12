package com.unittest.app.hr;

import org.junit.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

public class Array2d {


    // Complete the hourglassSum function below.
    static int hourglassSum(int[][] arr) {
        int largestSum = -63;
        int four = 4;
        for (int i = 0; i < four; i++) {
            for (int j = 0; j < four; j++) {
                int i1 = calculateSingleSum(arr, i, j);
                if (largestSum < i1) largestSum = i1;
            }
        }

        return 1;
    }

    private static int calculateSingleSum(int[][] arr,int i, int j) {
        int sum;
        sum = arr[i][j]+ arr[i][j+1]+arr[i][j+2];
        sum += arr[i+1][j+1];
        sum += arr[i+2][j]+ arr[i+2][j+1]+arr[i+2][j+2];
        return sum;
    }


    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int[][] arr = new int[6][6];

        for (int i = 0; i < 6; i++) {
            String[] arrRowItems = scanner.nextLine().split(" ");
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            for (int j = 0; j < 6; j++) {
                int arrItem = Integer.parseInt(arrRowItems[j]);
                arr[i][j] = arrItem;
            }
        }

        int result = hourglassSum(arr);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }
}
