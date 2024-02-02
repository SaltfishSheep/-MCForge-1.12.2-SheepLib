package saltsheep.lib;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Test {

    public static void main(String[] args){
        Object obj = new Object();
        int count = new Scanner(System.in).nextInt();
        Thread t = new Thread(()->{
            synchronized (obj) {
                for (int i = 0; i < count; i++)
                    i++;
            }
        });
        t.start();
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t.suspend();
        System.out.println(t.getState());
        t.resume();
        System.out.println(t.getState());
    }

}
