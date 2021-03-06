package project1;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class csv_writer {
    String filename;
    String file_path;
    ArrayList<ArrayList<Object>> _buffer = new ArrayList<>();

    public csv_writer(String file_name, String file_path) {
        /*
        *  arg1: file name, arg2: file path
        * */
        this.filename = file_name;
        this.file_path = file_path;
        add_content("num threads", "exe_time", "avg_time_per_thread");
    }

    public void add_content(Object num_thread, Object exe_time, Object avg){
        ArrayList<Object> temp = new ArrayList<>();
        temp.add(num_thread.toString());
        temp.add(exe_time.toString());
        temp.add(avg.toString());
        _buffer.add(temp);
    }

    public void write_csv(){
        try {
            BufferedWriter fw = new BufferedWriter(new FileWriter(file_path + "/"+filename+".csv", true));

            for (ArrayList<Object> e: _buffer){
                fw.write(e.get(0) +","+ e.get(1)+","+e.get(2));
                fw.newLine();

            }

            fw.close();

        } catch (IOException e) {
            System.out.println("Cannot create file.\n");
            e.printStackTrace();
        }

    }

}
