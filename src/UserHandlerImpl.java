import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public abstract class UserHandlerImpl implements UserHandler {

    String separator = "\t" + "\t" + "\t";

    /**
     * Displays a login page with username and password fields
     */
    public void login() {
    }

    /**
     * Displays a register page with username, password and confirm password fields
     */
    public void register() {
    }

    /**
     * Runs the getLinesAsArrayList method and then converts it to an Array
     *
     * @param reader A Scanner that has a file passed to it
     * @return Array of all the lines of the file
     */
    @Override
    public String[] getLines(Scanner reader) {
        ArrayList<String> lines = getLinesAsArrayList(reader);
        String[] arr = new String[lines.size()];
        lines.toArray(arr);
        return arr;
    }

    /**
     * This method takes a Scanner that has a file passed to it and reads all the lines from
     * that file to an ArrayList
     *
     * @param reader A Scanner that has a file passed to it
     * @return ArrayList of all the lines in the file
     */
    @Override
    public ArrayList<String> getLinesAsArrayList(Scanner reader) {
        ArrayList<String> lines = new ArrayList<>();
        while (reader.hasNextLine()){
            String line = reader.nextLine();
            lines.add(line);
        }
        return lines;
    }

    /**
     * This method removes a certain element from an Array of Strings
     *
     * @param lines An Array of Strings
     * @param line A String or Sub-String of the element you want removed
     * @return The Array with the line removed
     */
    @Override
    public String[] removeLine(String[] lines, String line) {
        ArrayList<String> linesList = new ArrayList<>(Arrays.asList(lines));
        for (int i = 0; i < linesList.size(); i++){
            if (linesList.get(i).contains(line)){
                linesList.remove(i);
                break;
            }
        }
        String[] arr = new String[linesList.size()];
        linesList.toArray(arr);
        return arr;
    }

    /**
     * This method concatenates all the elements in an Array of Strings into one String
     *
     * @param lines An Array of Strings
     * @return The concatenated String
     */
    @Override
    public String joinArray(String[] lines) {
        String join = "";
        for (String s : lines) {
            join = join.concat(s);
        }
        return join;
    }
}
