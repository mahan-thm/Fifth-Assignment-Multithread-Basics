package sbu.cs;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MatrixMultiplication {
    static List<List<Integer>> tempMatrixProduct = new ArrayList<>();
        static List<List<Integer>> matrix_p1 = new ArrayList<>();
        static List<List<Integer>> matrix_p2 = new ArrayList<>();
        static List<List<Integer>> matrix_p3 = new ArrayList<>();
        static List<List<Integer>> matrix_p4 = new ArrayList<>();

    // You are allowed to change all code in the BlockMultiplier class
    public static class BlockMultiplier implements Runnable {
        List<List<Integer>> matrix_A;
        List<List<Integer>> matrix_B;
        int partition;

        public BlockMultiplier(List<List<Integer>> matrix_A, List<List<Integer>> matrix_B, int partition) {

            // TODO

        }



        @Override
        public void run() {
            /*
            TODO
                Perform the calculation and store the final values in tempMatrixProduct
            */

            int a_row = matrix_A.size();
            int b_row = matrix_B.size();

            if (partition == 1) {
                for (int i = 0; i < a_row / 2; i++) {
                    List<Integer> row = new ArrayList<>();
                    int sum = 0;
                    for (int j = 0; j < b_row; j++) {
                        for (int k = 0; k < a_row / 2; k++) {
                            sum += matrix_A.get(i).get(k) * matrix_B.get(k).get(j);
                        }
                        row.add(sum);
                    }
                    matrix_p1.add(row);
                }
            } else if (partition == 2) {
                for (int i = a_row / 2; i <= a_row; i++) {
                    List<Integer> row = new ArrayList<>();
                    int sum = 0;
                    for (int j = 0; j < b_row; j++) {
                        for (int k = 0; k < a_row / 2; k++) {
                            sum += matrix_A.get(i).get(k) * matrix_B.get(k).get(j);
                        }
                        row.add(sum);
                    }
                    matrix_p2.add(row);
                }

            } else if (partition == 3) {
                for (int i = 0; i < a_row / 2; i++) {
                    List<Integer> row = new ArrayList<>();
                    int sum = 0;
                    for (int j = 0; j < b_row; j++) {
                        for (int k = a_row / 2; k <= a_row; k++) {
                            sum += matrix_A.get(i).get(k) * matrix_B.get(k).get(j);
                        }
                        row.add(sum);
                    }
                    matrix_p3.add(row);
                }

            } else if (partition == 4) {
                for (int i = a_row / 2; i <= a_row; i++) {
                    List<Integer> row = new ArrayList<>();
                    int sum = 0;
                    for (int j = 0; j < b_row; j++) {
                        for (int k = a_row / 2; k <= a_row; k++) {
                            sum += matrix_A.get(i).get(k) * matrix_B.get(k).get(j);
                        }
                        row.add(sum);
                    }
                    matrix_p4.add(row);
                }

            }

        }
    }

    /*
    Matrix A is of the form p x q
    Matrix B is of the form q x r
    both p and r are even numbers
    */
    public static List<List<Integer>> ParallelizeMatMul(List<List<Integer>> matrix_A, List<List<Integer>> matrix_B) {
        /*
        TODO
            Parallelize the matrix multiplication by dividing tasks between 4 threads.
            Each thread should calculate one block of the final matrix product. Each block should be a quarter of the final matrix.
            Combine the 4 resulting blocks to create the final matrix product and return it.
         */

        BlockMultiplier blockMultiplier1 = new BlockMultiplier(matrix_A, matrix_B, 1);
        BlockMultiplier blockMultiplier2 = new BlockMultiplier(matrix_A, matrix_B, 2);
        BlockMultiplier blockMultiplier3 = new BlockMultiplier(matrix_A, matrix_B, 3);
        BlockMultiplier blockMultiplier4 = new BlockMultiplier(matrix_A, matrix_B, 4);

        Thread thread1 = new Thread(blockMultiplier1);
        Thread thread2 = new Thread(blockMultiplier2);
        Thread thread3 = new Thread(blockMultiplier3);
        Thread thread4 = new Thread(blockMultiplier4);

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
            thread4.join();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        for(int i = 0  )





        return null;
    }

    public static void main(String[] args) {
        // Test your code here

//        Scanner input = new Scanner(System.in);
//        String dividedNumbers = input.nextLine();
//        String[] numbers = dividedNumbers.split(",");
//
//        for (String number : numbers) {
//            List<Integer> row = new ArrayList<>();
//            row.add(Integer.parseInt(number));
//            tempMatrixProduct.add(row);
//        }
//
//        for (List<Integer> row : tempMatrixProduct) {
//            System.out.println(row);
//        }
    }
}
