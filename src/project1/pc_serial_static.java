package project1;
import java.lang.Thread;


class det_prime extends Thread{
    int num_of_primes = 0;
    long total_milliseconds = 0;
    int start =0;
    int end = 0;
    int num_thread=0;

    det_prime(int start, int end, int num_thread){
        this.start = start;
        this.end = end;
        this.num_thread = num_thread;
    }

    public void run(){
        long startTime = System.currentTimeMillis();
        int i;

        for (int x=start; x<end; x=x+num_thread) {
            boolean is_prime = true;
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
        for(int i=0; i<NUM_THREAD; i++){
            threads[i] = new det_prime(i, NUM_END, NUM_THREAD);
        }
    }

    private static void print_result(){
        int total_primes = 0;

        for(int i=0; i<NUM_THREAD; i++){
            System.out.println("Thread " + i + " execution time : "+ threads[i].get_Total_milliseconds());
            total_primes += threads[i].get_Num_of_primes();
        }

        System.out.println("Total " + total_primes + " prime numbers between 1 and "+ NUM_END);

    }

}
