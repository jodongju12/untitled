package ai0901;

import java.util.Scanner;

public class MultiArrayTest3 {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int[][] arr = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}};


        System.out.println("<arr[0][0] ~ arr[2][3] 요소에 저장된 값을 출력>");

        // 배열 값 출력
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {

                System.out.printf("%3d", arr[i][j]);

            }
            System.out.println();
        }

        sc.close();
    }
}
