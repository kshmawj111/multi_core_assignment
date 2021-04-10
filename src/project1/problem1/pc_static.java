package project1.problem1;
import project1.csv_writer;

import java.lang.Thread;
import java.util.ArrayList;


class isPrime_static extends Thread{
    private int num_of_primes = 0;
    private long total_milliseconds = 0;
    private ArrayList<Integer> task_queue = new ArrayList<>();

    isPrime_static(){}

    public void insert_task(int num){task_queue.add(num);}

    public void run(){
        long startTime = System.currentTimeMillis();
        int i;

        for (int x: task_queue) {
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


public class pc_static {
    private static final int NUM_END = 200000;
    private static int NUM_THREAD;
    private static isPrime_static[] thread_pool;
    private static csv_writer writer;

    pc_static(int num_thread, csv_writer csv_writer){
        NUM_THREAD = num_thread;
        writer = csv_writer;
    }

    public void run_test(){
        initialize();

        for(int i=0; i<NUM_THREAD; i++){
           thread_pool[i].start();
        }

        for(int i=0; i<NUM_THREAD; i++){
            try {
                thread_pool[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        print_result();
    }

    private void initialize(){
        thread_pool = new isPrime_static[NUM_THREAD]; // Prime determine thread array

        for (int i=0; i<NUM_THREAD; i++){
            thread_pool[i] = new isPrime_static();
        }

        // allocating tasks
        for (int i=0; i<NUM_END; i++){
            thread_pool[i%NUM_THREAD].insert_task(i+1);
        }
    }

    private static void print_result(){
        int total_primes = 0;
        long total_time = 0;

        for(int i=0; i<NUM_THREAD; i++){
            System.out.println("Thread "+ i + ": " + thread_pool[i].get_Total_milliseconds() + "msec");
            total_primes += thread_pool[i].get_Num_of_primes();
            total_time += thread_pool[i].get_Total_milliseconds();
        }

        writer.add_content(NUM_THREAD, total_time);
        System.out.println("total primes : "+ total_primes + " Avg time per thread : " + total_time/(double)NUM_THREAD + "msec\n\n");

    }

}
