package project1;

public class test_1 {
    public static void main(String[] args) {
        int[] num_threads = {6};

        long s = System.currentTimeMillis();
        csv_writer writer = new csv_writer("result_static", "src/project1");

        for(int num_thread : num_threads){
            pc_static t1 = new pc_static(num_thread, writer);
            t1.run_test();
        }
        long e = System.currentTimeMillis();
        writer.write_csv();

        long s_d = System.currentTimeMillis();
        writer = new csv_writer("result_dynamic", "src/project1");

        for(int num_thread : num_threads){
            pc_dynamic t2 = new pc_dynamic(num_thread, writer);
            t2.run_test();
        }
        long e_d = System.currentTimeMillis();
        writer.write_csv();

        System.out.println(e-s);
        System.out.println(e_d - s_d);
    }
}
