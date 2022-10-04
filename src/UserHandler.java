import java.util.ArrayList;
import java.util.Scanner;

public interface UserHandler {
    // General input
    Scanner stdin = new Scanner(System.in);

    // In charge of the UI
    UI layout = new UI();

    // The general login/register of a user
    void login();
    void register();

    // Reads and returns all the lines of a file passed to the Scanner @reader as an array of strings
    String[] getLines(Scanner reader);

    // Reads and returns all the lines of a file passed to the scanner @reader as an array list of strings
    ArrayList<String> getLinesAsArrayList(Scanner reader);

    // Removes a specific element @line from an array @lines
    String[] removeLine(String[] lines, String line);

    // Concatenates all the elements in the array @lines and returns a single string
    String joinArray(String[] lines);


}
