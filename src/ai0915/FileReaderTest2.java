package ai0915;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileReaderTest2 {

    public static void main(String[] args) {

        try {
            // File을 읽어오기 위한 입력스트림(InputStream) 생성
        //    BufferedReader br = new BufferedReader(new FileReader("D:/FileIOTest/myData1.txt"));//절대경로
            BufferedReader br = new BufferedReader(new FileReader("myData1.txt"));//상대경로

            String line = "";

            // File에서 한 줄 읽어오기
        while (true) {
            line = br.readLine();
            if (line == null)
                break;
            System.out.println(line);
        }

            // FileInputStream 닫기
            br.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}