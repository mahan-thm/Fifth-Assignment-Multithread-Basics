package sbu.cs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MatrixMultiplication {
    static List<List<Integer>> tempMatrixProduct = new ArrayList<>();

    // You are allowed to change all code in the BlockMultiplier class
    public static class BlockMultiplier implements Runnable {
        List<List<Integer>> matrix_p1 = new ArrayList<>();
        List<List<Integer>> matrix_p2 = new ArrayList<>();
        List<List<Integer>> matrix_p3 = new ArrayList<>();
        List<List<Integer>> matrix_p4 = new ArrayList<>();
        List<List<Integer>> matrix_A;
        List<List<Integer>> matrix_B;
        int partition;

        public BlockMultiplier(List<List<Integer>> matrix_A, List<List<Integer>> matrix_B, int partition) {
            this.matrix_A = matrix_A;
            this.matrix_B = matrix_B;
            this.partition = partition;
        }

        @Override
        public void run() {

            int a_row = matrix_A.size();
            int b_row = matrix_B.size();
            int a_column = matrix_A.get(0).size();
            int b_column = matrix_B.get(0).size();

            if (partition == 1) {
                for (int i = 0; i < a_row / 2; i++) {
                    List<Integer> row = new ArrayList<>();
                    for (int j = 0; j < b_column / 2; j++) {
                        int sum = 0;
                        for (int k = 0; k < a_column; k++) {
                            sum += matrix_A.get(i).get(k) * matrix_B.get(k).get(j);
                        }
                        row.add(sum);
                    }
                    matrix_p1.add(row);
                }
            } else if (partition == 3) {
                for (int i = a_row / 2; i < a_row; i++) {
                    List<Integer> row = new ArrayList<>();
                    for (int j = 0; j < b_column / 2; j++) {
                        int sum = 0;
                        for (int k = 0; k < b_row; k++) {
                            sum += matrix_A.get(i).get(k) * matrix_B.get(k).get(j);
                        }
                        row.add(sum);
                    }
                    matrix_p3.add(row);
                }
            } else if (partition == 2) {
                for (int i = 0; i < a_row / 2; i++) {
                    List<Integer> row = new ArrayList<>();
                    for (int j = b_column / 2; j < b_column; j++) {
                        int sum = 0;
                        for (int k = 0; k < a_column; k++) {
                            sum += matrix_A.get(i).get(k) * matrix_B.get(k).get(j);
                        }
                        row.add(sum);
                    }
                    matrix_p2.add(row);
                }
            } else if (partition == 4) {
                for (int i = a_row / 2; i < a_row; i++) {
                    List<Integer> row = new ArrayList<>();
                    for (int j = b_column / 2; j < b_column; j++) {
                        int sum = 0;
                        for (int k = 0; k < a_column; k++) {
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

        for (int i = 0; i < blockMultiplier1.matrix_p1.size(); i++) {
            for (int j = 0; j < blockMultiplier2.matrix_p2.get(0).size(); j++) {
                blockMultiplier1.matrix_p1.get(i).add(blockMultiplier2.matrix_p2.get(i).get(j));
            }
        }
        List<List<Integer>> halfUp_matrix = blockMultiplier1.matrix_p1;
        for (int i = 0; i < blockMultiplier3.matrix_p3.size(); i++) {
            for (int j = 0; j < blockMultiplier4.matrix_p4.get(0).size(); j++) {
                blockMultiplier3.matrix_p3.get(i).add(blockMultiplier4.matrix_p4.get(i).get(j));
            }
        }
        List<List<Integer>> halfDown_matrix = blockMultiplier3.matrix_p3;


        tempMatrixProduct.addAll(halfUp_matrix);
        tempMatrixProduct.addAll(halfDown_matrix);

        return tempMatrixProduct;
    }

    public static void main(String[] args) {

        List<List<Integer>> matrix_A = Arrays.asList(
                Arrays.asList(1, 2, 3, 2),
                Arrays.asList(4, 5, 6, 4)
        );
        List<List<Integer>> matrix_B = Arrays.asList(
                Arrays.asList(1, 2, 5, 2),
                Arrays.asList(4, 5, 3, 4),
                Arrays.asList(4, 5, 4, 3),
                Arrays.asList(4, 5, 6, 1)

        );

        System.out.println(ParallelizeMatMul(matrix_A, matrix_B));
    }
}
