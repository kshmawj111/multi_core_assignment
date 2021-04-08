package project1;
import java.lang.Thread;


class isPrime_static extends Thread{
    int num_of_primes = 0;
    long total_milliseconds = 0;
    int start, end, num_thread;
    int num_calls = 0;

    isPrime_static(int start, int end, int num_thread){
        this.start = start;
        this.end = end;
        this.num_thread = num_thread;
    }

    public void run(){
        long startTime = System.currentTimeMillis();
        int i;

        for (int x=start; x<end; x=x+num_thread) {
            num_calls++;
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

    public void change_num_threads(int num) {NUM_THREAD = num;}

    private void initialize(){
        thread_pool = new isPrime_static[NUM_THREAD]; // det_prime 형 threads 배열 생성

        for(int i=0; i<NUM_THREAD; i++){
            thread_pool[i] = new isPrime_static(i, NUM_END, NUM_THREAD);
        }
    }

    private static void print_result(){
        int total_primes = 0;
        long total_time = 0;

        for(int i=0; i<NUM_THREAD; i++){
            System.out.println("Thread "+ i + ": " + thread_pool[i].get_Total_milliseconds() + " and " +
                    thread_pool[i].num_calls + "calls");
            total_primes += thread_pool[i].get_Num_of_primes();
            total_time += thread_pool[i].get_Total_milliseconds();
        }

        writer.add_content(NUM_THREAD, total_time);
        System.out.println("total primes : "+ total_primes + " Total time : " + total_time + "\n\n");

    }

}
