package sbu.cs;

import java.util.ArrayList;
import java.util.List;

public class TaskScheduler
{
    public static class Task implements Runnable
    {
        /*
            ------------------------- You don't need to modify this part of the code -------------------------
         */
        String taskName;
        int processingTime;

        public Task(String taskName, int processingTime) {
            this.taskName = taskName;
            this.processingTime = processingTime;
        }
        /*
            ------------------------- You don't need to modify this part of the code -------------------------
         */

        @Override
        public void run() {
            try {
                Thread.sleep(processingTime);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private static void bubbleSort(ArrayList<Task> tasks, int n)
    {
        int i, j;
        Task temp;
        boolean swapped;
        for (i = 0; i < n - 1; i++) {
            swapped = false;
            for (j = 0; j < n - i - 1; j++) {
                if (tasks.get(j).processingTime < tasks.get(j+1).processingTime) {
                    temp = tasks.get(j);
                    tasks.set(j, tasks.get(j+1));
                    tasks.set(j+1, temp);
                    swapped = true;
                }
            }
            if (swapped == false)
                break;
        }
    }

    public static ArrayList<String> doTasks(ArrayList<Task> tasks) {
        ArrayList<String> finishedTasks = new ArrayList<>();
        bubbleSort(tasks, tasks.size());
        for (int i = 0; i < tasks.size(); i++){
            Thread tread = new Thread(tasks.get(i));
            tread.start();
            try {
                tread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            finishedTasks.add(tasks.get(i).taskName);

        }


        return finishedTasks;
    }

    public static void main(String[] args) {
        // Test your code here
    }
}
