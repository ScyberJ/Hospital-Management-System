import java.io.*;
import java.util.Scanner;
/********************************************************************************************************************
 * Author: MUTONJ ALI BOTA
 * Details: This program is a complete module of  a patient in a hospital management system, it registers an account,
 *          it creates appointments, look for a doctor, and
 *          it views and deletes appointments made by patients.
 *******************************************************************************************************************/
public class PatientHandler {
    static Scanner stdin=new Scanner(System.in);
    public static void main(String[] args) throws IOException {

        char ans;

        System.out.print("Enter 'l' to login or 'r' register?: ");
        ans=stdin.nextLine().charAt(0);

        if (ans=='l' || ans=='L'){
            login();

        }
        else{
            register();
            login();
        }


    }
    public static void login() throws IOException {

        File doc=new File("./files/users.txt");
        String name,pass,namePass;
        boolean correct=false;

        System.out.println("Enter your user name to login: ");
        name=stdin.nextLine();
        System.out.println("Enter your password: ");
        pass=stdin.nextLine();
        namePass=(name + "\t" + "\t" + "\t" + pass);

        Scanner reader=new Scanner(doc);
        while (reader.hasNextLine()) {
            if (reader.nextLine().equals(namePass)) {
                correct = true;
            }
        }
        if (correct){
            System.out.println("Welcome "+ name + "\n");
            char response;
           do{
               System.out.println("""
                    Enter 1 to view appointments\s
                    OR 2 to cancel an appointment
                    OR 3 to look for a doctor
                    OR 4 to quit""");
               response=stdin.nextLine().charAt(0);

               //if (response=='1'){createAppointment();}
               if (response=='1'){viewAppointments();}
               else if (response=='2'){deleteAppointment();}
               else if (response=='3'){findDoc();}
               else if (response=='4'){System.out.println("Good bye " + name + "!!");}
           }while(response!='4');
        }
        else{System.out.println("Wrong password or user name ");}

    }
    public static void register() throws IOException {

        String name,pass,namePass;

        System.out.print("Enter a user name: ");
        name=stdin.nextLine();
        System.out.print("Enter a 8 digit password to create an account: ");
        pass=stdin.nextLine();
        namePass=(name + "\t" + "\t" + "\t" + pass);

        FileWriter writeUsers = new FileWriter("./files/users.txt", true);
        writeUsers.write(namePass + "\n" );
        writeUsers.close();



        System.out.println("You are successfully registered " + name + "\n");

    }
    public static void createAppointment() throws IOException{
        String docName,date,appointmentDetails;

        System.out.print("Enter the doctor's name: ");
        docName="Dr." + stdin.nextLine();
        System.out.print("Enter the date separated by a hyphen as (dd-mm-yyyy): ");
        date=stdin.nextLine();
        appointmentDetails=(docName + "\t" + "\t" + "\t" + date);

        FileWriter makeAppointment=new FileWriter("./files/appointment.txt", true);
        makeAppointment.write(appointmentDetails + "\n");
        makeAppointment.close();

        System.out.println("your appointment is done successfully!");

    }
    public static void viewAppointments() throws FileNotFoundException{

        File appointmentList= new File("./files/appointment.txt");
        Scanner reader=new Scanner(appointmentList);

        while (reader.hasNextLine()){
            System.out.println(reader.nextLine());
        }

    }
    public static void deleteAppointment() throws IOException{
        String docName,date,appointmentDetails;
        File appointmentlist=new File("./files/appointment.txt");
        String[] copy;
        copy= new String[10];
        int i=0;

        Scanner reader=new Scanner(appointmentlist);
        while (reader.hasNextLine() && i< copy.length){
            copy[i]=reader.nextLine();
            i++;

        }

        int size= getLength(copy);

        System.out.print("Enter name of your doctor to cancel the appointment: ");
        docName="Dr." + stdin.nextLine();
        System.out.print("Enter the date created for the appointment separated by an hyphen as (dd-m-yyy): ");
        date=stdin.nextLine();
        appointmentDetails=(docName + "\t" + "\t" + "\t" + date);

        int a=0;
        String copy2="";

        while (a<size){
            if (!(appointmentDetails.equalsIgnoreCase(copy[a]))){
                String v=copy[a];
                copy2=copy2.concat(v+"\n");
            }
            a++;
        }

        FileWriter writer=new FileWriter("./files/appointment.txt");
        writer.write(copy2);
        writer.close();
        System.out.println("Your appointment was cancelled successfully");

    }
    public static int getLength(String[] a){
        int count=0;
        for(String ap:a){
            if (ap != null){
                count++;
            }
        }
        return count;
    }
    public static void findDoc() throws IOException{

        File doctors=new File("./files/doctors.txt");
        Scanner reader=new Scanner(doctors);
        String doc,temp="";
        boolean exist=false;
        char response;

        System.out.print("Enter doctor's name or id to find a doctor: ");
        doc= stdin.next();

        while(reader.hasNext()){
            if(doc.equalsIgnoreCase(reader.next())){
                exist=true;
                temp=reader.nextLine();
            }
        }
        if (exist){
            System.out.println("\nThe doctor you searched for is found" + "\n" + temp + "\n");
            System.out.print("enter 1 to create an appointment or 2 to return: ");
            response=stdin.next().charAt(0);
            if (response=='1'){createAppointment();}
        }
        else{System.out.println("The doctor does not exist" + "\n");}

    }
}
