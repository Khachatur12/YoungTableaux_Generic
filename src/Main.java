import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Young<Integer> young = new Young<>(4);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. push \n2. pop");
            int i = scanner.nextInt();
            if (i == 1) {
                int data = scanner.nextInt();
                young.push(data);
            } else if ( i == 2){
                young.pop();
            } else {
                System.out.println("invalid command!");
            }

            System.out.println(young.toString());
            System.out.println("size: " + young.getSize());
            System.out.println("current_length: " + young.getCurrent_length());
            System.out.println("col: " + young.getCol());
            System.out.println("row: " + young.getRow());
            System.out.println("is empty: " + young.empty());
            System.out.println("is full: " + young.full());
        }

    }
}
