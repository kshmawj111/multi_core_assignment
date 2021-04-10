package project1.problem1;
import project1.csv_writer;


public class Static extends PrimeAbstract {
    Static(int num_thread, csv_writer csv_writer) {
        super(num_thread, csv_writer);
    }

    @Override
    public void run_test() {
        initialize();
        allocate_task();

        for(PrimeThread x: threads){
            x.start();
        }

        for(PrimeThread x: threads){
            try {
                x.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        print_result();
    }

    @Override
    protected void initialize() {
        PrimeThread[] threads = new PrimeThread[NUM_THREAD];

        for (int i=0; i<NUM_THREAD; i++){
            threads[i] = new PrimeThread();
        }
    }

    @Override
    protected void allocate_task() {
        for (int i=0; i<NUM_END; i++){
            threads[i%NUM_THREAD].insert_task(i+1);
        }
    }


}
