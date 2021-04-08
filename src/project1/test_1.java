package project1;

public class test_1 {
    public static void main(String[] args) {
        csv_writer writer = new csv_writer("result_dynamic", "src/project1");

        int[] num_threads = {1};

        for(int num_thread : num_threads){
            pc_serial_dynamic t1 = new pc_serial_dynamic(num_thread, writer);
            t1.run_test();
        }
        // writer.write_csv();
    }
}
