package project1;
import java.lang.Thread;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


class det_prime extends Thread{
    int num_of_primes = 0;
    long total_milliseconds = 0;
    int[] numbers;
    int thread_num =0;
    int num_total_thread = 0;

    det_prime(int[] numbers, int thread_num, int num_total_thread){
        this.numbers = numbers;
        this.thread_num=thread_num;
        this.num_total_thread=num_total_thread;
    }

    public void run(){
        long startTime = System.currentTimeMillis();
        int i;

        for (int x =thread_num; x<numbers.length; x=x+num_total_thread) {
            boolean is_prime = true;

            for (i = 2; i < x/2; i++) {
                if (x % i == 0) {
                    is_prime = false;
                    break;
                }
            }
            total_milliseconds += (System.currentTimeMillis() - startTime); // record execution time

            if (is_prime) {
                num_of_primes++;
            }
        }
    }

    public long get_Total_milliseconds(){return total_milliseconds;}
    public int get_Num_of_primes(){return num_of_primes;}
}


public class pc_serial_static {
    private static final int NUM_END = 200000;
    private static final int NUM_THREAD = 4;
    private static final det_prime[] threads = new det_prime[NUM_THREAD]; // det_prime 형 threads 배열 생성
    private static final int[] numbers = new int [NUM_END];

    public static void main(String[] args){
        initialize();

        for(int i=0; i<NUM_THREAD; i++){
           threads[i].start();
        }

        for(int i=0; i<NUM_THREAD; i++){
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        print_result();
    }

    private static void initialize(){
        for (int i=0; i<NUM_END; i++){
            numbers[i] = i+1;
        }
        for(int i=0; i<NUM_THREAD; i++){
            threads[i] = new det_prime(numbers, i, NUM_THREAD);
        }
    }

    private static void print_result(){
        int total_primes = 0;

        for(int i=0; i<NUM_THREAD; i++){
            System.out.println("Thread " + i + " execution time : "+ threads[i].get_Total_milliseconds());
            total_primes += threads[i].get_Num_of_primes();
        }

        System.out.println("Total " + total_primes + " prime numbers inbetween 1 and "+ NUM_END);

    }

}
