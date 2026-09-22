package ai0922;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class LAB_SecureTest {
    public static void main(String[] args) {
        try {


            Scanner s = new Scanner(System.in);
            String line = "";
            String secure = "";

            FileWriter fw = new FileWriter("secure.txt");

            while (true) {
                System.out.print("스파이에게 전달할 메시지 :");
                line = s.nextLine();
                if (line.equals(""))
                    break;

                for (int i = 0; i < line.length(); i++){
                    int num = (int)line.charAt(i);
                    num += 100;
                    secure += (char)num;
                }

                fw.write(secure + "\n");
            }

            s.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

