import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
/********************************************************************************************************************
 * Author: MUTONJ ALI BOTA
 * Details: This program is a complete module of  a patient in a hospital management system, it registers an account,
 *          it creates appointments, look for a doctor, and
 *          it views and deletes appointments made by patients.
 *******************************************************************************************************************/
public class PatientHandler {
     private static final Scanner stdin = new Scanner(System.in);
     private static final UI layout = new UI();

    public static void login() throws IOException {
        String namePass;
        File doc = new File("./files/users.txt");
        Scanner reader = new Scanner(doc);
        boolean correct = false;
        String[] users = getLines(reader);

        String[] credentials = layout.createLoginPage();

        String name = credentials[0];
        String pass = credentials[1];

        namePass=(name + "\t" + "\t" + "\t" + pass);

        boolean stop = false;
        while (!stop) {
            for (String user: users){
                if (user.equals(namePass)) {
                    correct = true;
                    break;
                }
            }
            if (correct) {
                layout.createBorder("*");
                layout.createHeading("Welcome " + name);
                do {
                    String option1 = "Make an Appointment";
                    String option2 = "View Appointments";
                    String option3 = "Delete an Appointment";
                    String option4 = "Find Docter";
                    String option5 = "Quit";
                    String[] patientOptions = new String[]{option1, option2, option3, option4, option5};
                    layout.createOptionList(patientOptions);

                    String response = layout.currentChoice;

                    if (response.equals(option1)) createAppointment();
                    if (response.equals(option2)) viewAppointments();
                    if (response.equals(option3)) deleteAppointment();
                    if (response.equals(option4)) findDoc();
                    if (response.equals(option5)) {
                        layout.createHeading("Bye");
                        stop = true;
                    }

                } while (!layout.currentChoice.equals("Quit"));
                layout.createBorder("*");
            } else {
                layout.createHeading("Wrong password or username");
                credentials = layout.createLoginPage();

                name = credentials[0];
                pass = credentials[1];

                namePass=(name + "\t" + "\t" + "\t" + pass);
            }
        }

    }
    public static void register() throws IOException {
        boolean success = false;
        String name = "", pass = "", confirmPass;
        while(!success){
            String[] credentials =layout.createRegisterPage();

            name = credentials[0];
            pass = credentials[1];
            confirmPass = credentials[2];
            if (!pass.equals(confirmPass)){
                layout.createHeading("Please make sure that your passwords match");
            }else if(pass.length() < 8){
                 layout.createHeading("Please enter a password that is 8 characters long");
            }else{
                success = true;
            }
        }

        String namePass = (name + "\t" + "\t" + "\t" + pass);

        FileWriter writeUsers = new FileWriter("./files/users.txt", true);
        writeUsers.write(namePass + "\n" );
        writeUsers.close();

        layout.createHeading("You are successfully registered " + name);
    }
    public static void createAppointment() throws IOException{
        String docName,date,appointmentDetails;
        String seperator = "\t" + "\t" + "\t";

        System.out.print("Enter the doctor's name: ");
        docName = "Dr." + stdin.nextLine();
        String docId = String.valueOf((int) Math.floor(Math.random() * 100)) + docName.charAt(docName.length()-1);
        System.out.print("Enter the date separated by a hyphen as (dd-mm-yyyy): ");
        date = stdin.nextLine();
        appointmentDetails = (docId + seperator + docName + seperator + date);

        FileWriter makeAppointment = new FileWriter("./files/appointment.txt", true);
        makeAppointment.write(appointmentDetails + "\n");
        makeAppointment.close();

        layout.createHeading("Your appointment was made successfully!");

    }
    private static void viewAppointments() throws FileNotFoundException{
        String seperator = "\t" + "\t" + "\t";
        File appointmentList = new File("./files/appointment.txt");
        Scanner reader = new Scanner(appointmentList);
        String[] headers = new String[]{"ID","Doctor", "Date"};
        String[] lines = getLines(reader);


        String[][] data = new String[lines.length][];

        for (int i = 0; i < data.length; i++){
            String[] line = lines[i].split(seperator);
            data[i] = new String[]{line[0], line[1], line[2]};
        }

        layout.createTable(headers, data);
    }
    private static void deleteAppointment() throws IOException{
        String docId, appointmentDetails;
        File appointmentlist = new File("./files/appointment.txt");
        Scanner reader = new Scanner(appointmentlist);
        String[] copy = getLines(reader);

        int size = copy.length;

        System.out.print("Enter the ID of the appointment you wish to cancel");
        docId = stdin.nextLine();

        int a = 0;
        String copy2 = joinArray(removeLine(copy, docId));



        FileWriter writer = new FileWriter("./files/appointment.txt");
        writer.write(copy2);
        writer.close();
        layout.createHeading("Your appointment was cancelled successfully");

    }

    private static ArrayList<String> getLinesAsArrayList(Scanner reader){
        ArrayList<String> lines = new ArrayList<>();
        while (reader.hasNextLine()){
            String line = reader.nextLine();
            lines.add(line);
        }
        return lines;
    }

    private static String[] getLines(Scanner reader){
        ArrayList<String> lines = getLinesAsArrayList(reader);
        String[] arr = new String[lines.size()];
        lines.toArray(arr);
        return arr;
    }

    private static String[] removeLine(String[] lines, String line){
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

    private static String joinArray(String[] arr){
        String join = "";
        for (String s : arr) {
            join = join.concat(s);
        }
        return join;
    }

     private static void findDoc() throws IOException{
        String seperator = "\t" + "\t" + "\t";
        File doctorsFile = new File("./files/doctors.txt");
        Scanner reader = new Scanner(doctorsFile);
        String doc;
        boolean exists = false;

        System.out.print("Enter doctor's name or id to find a doctor: ");
        doc = stdin.nextLine();

        String[] doctors = getLines(reader);
        String result = "";

        for (String doctor: doctors){
            if (doctor.contains(doc)){
                System.out.println(doctor);
                exists = true;
                result = doctor;
                break;
            }
        }


        if (exists){
            String[] doctorDetails = result.split(seperator);
            String name = doctorDetails[1];
            layout.createHeading("We found " + name + "!");
            layout.createTable(new String[]{"ID", "Name"}, new String[][]{doctorDetails});
            String option1 = "Make an Appointment";
            layout.createOptionList(new String[]{option1, "Return"});
            if (layout.currentChoice.equals(option1)) createAppointment();
        }
        else{layout.createHeading("The doctor does not exist");}

    }
}
