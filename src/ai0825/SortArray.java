package ai0825;

import java.util.Arrays;
import java.util.Collections;

public class SortArray {
    public static void main(String[] args) {
        Integer[] numArr = {77, 33, 11, 99, 22, 55, 88};
//        Arrays.sort(numArr, Collections.reverseOrder()); //내림차순정렬
        Arrays.sort(numArr);//오름차순정렬
        for (int data : numArr){
            System.out.print(data + "     ");
        }


        System.out.println();

        String[] nameArr = {"손흥민", "음바페", "홀란드", "야말", "김정은"};
//        Arrays.sort(nameArr);//오름차순정렬
        Arrays.sort(nameArr, Collections.reverseOrder());//내림차순정렬
        for (String name : nameArr) {
            System.out.print(name + "      ");
        }


    }
}
