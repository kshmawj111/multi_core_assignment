package project1.problem1;


import project1.csv_writer;


class isPrimeDynamic extends Thread{
        int num_of_primes = 0;
        long total_milliseconds = 0;
        int id, num_calls=0;
        int x;

        isPrimeDynamic(int id){this.id=id;}

        public void run(){
            num_calls++;
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
    private isPrimeDynamic[] thread_pool;
    private static int current_num;

    pc_dynamic(int num_thread, csv_writer csv_writer){
        NUM_THREAD = num_thread;
        writer = csv_writer;
    }

    public void run_test(){
        initialize();
        int i;

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
        current_num = 0;

        for(int i = 0; i<NUM_THREAD; i++){
            thread_pool[i] = new isPrimeDynamic(i);
        }

    }

    private synchronized void fetch_task(int i){
        if(!thread_pool[i].isAlive()) { // i번째 스레드가 활성화 되있지 않다면
            // thread가 조사할 값 업데이트
            thread_pool[i].set_X(current_num);
            current_num++;
        }
    }

    private synchronized void end_task(){
        notify();
    }

    private void print_result(){
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
