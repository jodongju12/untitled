package ai0922;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class LAB_DecodeSecureTest {
    public static void main(String[] args) {
        try {
            String line = "";
            String decode = "";
            BufferedReade br = new BufferedReader(new FileReader("secure.txt"));
            FileWriter fw = new FileWriter("decodeSecure.txt");

            while (true) {
                decode = "";
                line = br.readLine();

                if (line == null)
                    break;

                for (int i = 0; i < line.length(); i++){
                    int num = (int)line.charAt(i);
                    num += 100;
                    decode += (char)num;
                }

                fw.write(decode + "\n");
            }

            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}