package src;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter a username: ");
        String username = input.nextLine();

        FileWriter doc = new FileWriter("./username.txt", true);

        doc.write(username);

        doc.close();
    }

}