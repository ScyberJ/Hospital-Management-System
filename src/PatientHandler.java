import java.io.*;
import java.util.Scanner;
/********************************************************************************************************************
 * Author: MUTONJ ALI BOTA
 * Details: This program is a complete module of  a patient in a hospital management system, it registers an account,
 *          it creates appointments, look for a doctor, and
 *          it views and deletes appointments made by patients.
 *******************************************************************************************************************/
public class PatientHandler extends UserHandlerImpl {
     private  final Scanner stdin = new Scanner(System.in);
     private  final UI layout = new UI();

     @Override
    public void login() {
        File doc = new File("./files/users.txt");
        Scanner reader;
         try {
             reader = new Scanner(doc);
         } catch (FileNotFoundException e) {
             throw new RuntimeException(e);
         }

        String namePass;
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
                    String option4 = "Find Doctor";
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

    @Override
    public void register() {
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

        try {
            FileWriter writeUsers = new FileWriter("./files/users.txt", true);
            writeUsers.write(namePass + "\n" );
            writeUsers.close();
        }catch (IOException io){
            throw new RuntimeException(io);
        }

        layout.createHeading("You are successfully registered " + name);
    }
    public void createAppointment() {
        String docName,date,appointmentDetails;

        System.out.print("Enter the doctor's name: ");
        docName = "Dr." + stdin.nextLine();
        String docId = String.valueOf((int) Math.floor(Math.random() * 100)) + docName.charAt(docName.length()-1);
        System.out.print("Enter the date separated by a hyphen as (dd-mm-yyyy): ");
        date = stdin.nextLine();
        appointmentDetails = (docId + separator + docName + separator + date);

        FileWriter makeAppointment;
        try {
            makeAppointment = new FileWriter("./files/appointment.txt", true);
            makeAppointment.write(appointmentDetails + "\n");
            makeAppointment.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        layout.createHeading("Your appointment was made successfully!");

    }
    private void viewAppointments() {
        File appointmentList = new File("./files/appointment.txt");
        Scanner reader;
        try {
            reader = new Scanner(appointmentList);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        String[] headers = new String[]{"ID","Doctor", "Date"};
        String[] lines = getLines(reader);


        String[][] data = new String[lines.length][];

        for (int i = 0; i < data.length; i++){
            String[] line = lines[i].split(separator);
            data[i] = new String[]{line[0], line[1], line[2]};
        }

        layout.createTable(headers, data);
    }
    private void deleteAppointment() {
        String docId;
        File appointmentList = new File("./files/appointment.txt");
        Scanner reader;
        try {
            reader = new Scanner(appointmentList);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        String[] copy = getLines(reader);

        System.out.print("Enter the ID of the appointment you wish to cancel");
        docId = stdin.nextLine();

        String copy2 = joinArray(removeLine(copy, docId));


        FileWriter writer;
        try {
            writer = new FileWriter("./files/appointment.txt");
            writer.write(copy2);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        layout.createHeading("Your appointment was cancelled successfully");

    }

     private void findDoc() {
        File doctorsFile = new File("./files/doctors.txt");
         Scanner reader;
         try {
             reader = new Scanner(doctorsFile);
         } catch (FileNotFoundException e) {
             throw new RuntimeException(e);
         }
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
            String[] doctorDetails = result.split(separator);
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
