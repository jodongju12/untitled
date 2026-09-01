package ai0901;

import java.util.Scanner;

public class MultiArrayTest2 {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int[][] arr = new int[3][4];
        int count = 0;


        for (int i = 0; i < arr.length; i++) { // 행의 길이 만큼 반복
            for (int j = 0; j < arr[i].length; j++) {
                System.out.print(++count + " - 정수값 입력:");
                arr[i][j] = s.nextInt();

            }
        }

        System.out.println("<arr[0][0] ~ arr[2][3] 요소에 저장된 값을 출력>");

        for (int i = 0; i < arr.length; i++) { // 행의 길이 만큼 반복
            for (int j = 0; j < arr[i].length; j++) {// 열의 길이 만큼 반복
                System.out.printf("%3d", arr[i][j]);
            }
            System.out.println();
        }
    }
}
