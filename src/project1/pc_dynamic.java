package project1;


import java.util.ArrayDeque;


class isPrimeDynamic extends Thread{
        int num_of_primes = 0;
        long total_milliseconds = 0;
        int id;
        int x;

        isPrimeDynamic(int id){this.id=id;}

        public void run(){
            long startTime = System.currentTimeMillis();
            int i;

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

            long endTime = System.currentTimeMillis();
            total_milliseconds += (endTime - startTime); // record execution time

        }
        public void set_X(int x) {this.x = x;}
        public long get_Total_milliseconds(){return total_milliseconds;}
        public int get_Num_of_primes(){return num_of_primes;}

    }



public class pc_dynamic {
    private static final int NUM_END = 200000;
    private static int NUM_THREAD;
    private static csv_writer writer;
    private static Long exe_times;
    private static int num_primes;
    private static int counter;
    private isPrimeDynamic[] thread_pool;
    private static int current_num;

    pc_dynamic(int num_thread, csv_writer csv_writer){
        NUM_THREAD = num_thread;
        writer = csv_writer;
    }

    public void run_test(){
        initialize();
        int i=0;

        for(i=0; i<NUM_THREAD; i++){
            thread_pool[i].start();
        }

        i = 0;
        while(current_num < NUM_END){

            fetch_task(i);

            thread_pool[i].run();
            i = (i+1)%NUM_THREAD;
            end_task();


        }

        for(i=0; i<NUM_THREAD; i++){
            try {
                thread_pool[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        print_result();
    }

    private void initialize(){
        thread_pool = new isPrimeDynamic[NUM_THREAD];
        exe_times = (long) 0;
        counter = NUM_THREAD;
        num_primes = 0;
        current_num = 0;

        for(int i = 0; i<NUM_THREAD; i++){
            thread_pool[i] = new isPrimeDynamic(i);
        }

    }

    private synchronized void fetch_task(int i){
        if (counter <= 0){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(!thread_pool[i].isAlive()) { // i번째 스레드가 활성화 되있지 않다면
            // thread가 조사할 값 업데이트
            thread_pool[i].set_X(current_num);
            current_num++;
            counter--;
        }
    }

    private synchronized void end_task(){
        counter++;
        notify();

    }
    private void print_result(){
        // writer.add_content(NUM_THREAD, total_time);
        // System.out.println("Total " + num_primes + " prime numbers between 1 and "+ NUM_END+"\n\n");

        for(int i=0; i<NUM_THREAD; i++){
            System.out.println("Thread "+ thread_pool[i].id + ": " + thread_pool[i].get_Total_milliseconds());
            num_primes += thread_pool[i].get_Num_of_primes();
            exe_times += thread_pool[i].get_Total_milliseconds();
        }

        System.out.println("total primes : "+num_primes + " Total time : " + exe_times);
        writer.add_content(NUM_THREAD, exe_times);
    }

}
