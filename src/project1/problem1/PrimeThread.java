package project1.problem1;

import java.util.ArrayDeque;
import java.util.Queue;

public class PrimeThread extends Thread{
    private int num_of_primes = 0;
    private long total_milliseconds = 0;
    private final Queue<Integer> task_queue = new ArrayDeque<>();

    PrimeThread(){}

    public void insert_task(int num){task_queue.add(num);}

    public void run(){
        long startTime = System.currentTimeMillis();
        int i;

        while (!task_queue.isEmpty()) {
            int x = task_queue.poll();
            boolean is_prime = true;

            /*
            Algorithms to determine a number is prime or not
            for the purpose of making the most similar condition with dynamic way

             */

            if (x<=1){
                is_prime=false;
            }
            else {
                for (i = 2; i < x; i++) {
                    if (x % i == 0) {
                        is_prime = false;
                        break;
                    }
                }
            }

            if (is_prime) {
                num_of_primes++;
            }
        }
        total_milliseconds += (System.currentTimeMillis() - startTime); // record execution time
    }

    public long get_Total_milliseconds(){return total_milliseconds;}
    public int get_Num_of_primes(){return num_of_primes;}
}
