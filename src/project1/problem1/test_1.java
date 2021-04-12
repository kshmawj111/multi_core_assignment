package project1.problem1;

import project1.csv_writer;

public class test_1 {
    public static void main(String[] args) {
        int[] num_threads = {1, 2, 4, 6, 8, 10, 12, 14, 16, 32};

        /*csv_writer writer = new csv_writer("result_static", "src/project1/problem1");

        for(int num_thread : num_threads){
            Static t1 = new Static(num_thread, writer);
            t1.run_test();
        }
        writer.write_csv();*/


        csv_writer writer = new csv_writer("result_dynamic", "src/project1/problem1");

        for(int num_thread : num_threads){
            Dynamic t2 = new Dynamic(num_thread, writer);
            t2.run_test();
        }
        writer.write_csv();

    }
}
