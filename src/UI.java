import java.util.InputMismatchException;
import java.util.Scanner;

public class UI {
    final int FULL_WIDTH;
    String currentChoice, previousChoice;
    Scanner stdin = new Scanner(System.in);

    public UI(int width){
        this.FULL_WIDTH = width;
    }

    public UI(){
        this.FULL_WIDTH = 100;
    }


   public void createLandingPage(){
       createBorder("/");
       createHeading("Hospital Management System");
       createHeading("Choose whether to login or register");
       createOptionList(new String[]{"Login", "Register"});
   }

   public String[] createLoginPage(){
       createBorder("/");
       createHeading("Hospital Management System");
       createHeading("Please Enter Your Credentials");
       createHeading("Login");
       String username = createFormInput("Username: ", 33);
       String password = createFormInput("Password: ", 33);
       return new String[]{username, password};
   }

    public String[] createRegisterPage(){
        createBorder("/");
        createHeading("Hospital Management System");
        createHeading("Please Enter Your Credentials");
        createHeading("Registration");
        String username = createFormInput("New Username: ", 33);
        String password = createFormInput("New Password: ", 33);
        String confirmPassword = createFormInput("Confirm Password: ", 33);
        return new String[]{username, password, confirmPassword};
    }


   public void createOptionList(String[] options) {
       Scanner input = new Scanner(System.in);
       previousChoice = currentChoice;
       for (int i = 0; i < options.length; i++) {
           System.out.println(createSpace(33) + "| " + (i + 1) + " " + options[i]);
       }

       int choice = 0;
       System.out.print("Please choose a option (choose a number): ");
        if (input.hasNextInt()) {
            choice = input.nextInt();
        }else{
            createOptionList(options);
        }

       String option;

       if (choice > options.length || choice <= 0) {
           option = "Out of Bounds";
       } else {
           option = options[choice - 1];
       }

       currentChoice = option;
   }

    public  void createHeading(String heading) {
        System.out.println(createSpace((this.FULL_WIDTH/2)-(heading.length()/2)) + heading);
    }

    public void createBorder(String borderStyle) {
        System.out.println(borderStyle.repeat(this.FULL_WIDTH));
    }

    public String createFormInput(String label, int padding){
        System.out.print(createSpace(padding) + label);
        return stdin.next();
    }

    public String createSpace(int numberOfSpaces){
        return " ".repeat(numberOfSpaces);
    }

    public String createCenteredText(int width, String text){
       int size = text.length();
       if (size > width){
           size = width;
       }
       int sideWidth = (width - size) / 2;
       return createSpace(sideWidth) + text + createSpace(sideWidth);
    }

    public void createTable(String[] headers, String[][] data){
       createRow(headers);
       for (int i = 0; i < data.length; i++){
           createRow(data[i]);
           if (i == data.length-1){
               createBorder("-");
           }
       }

    }

    public void createRow(String[] data){
        int cellWidth = FULL_WIDTH/data.length;
        createBorder("-");
        for(int i = 0; i < data.length; i++){
            System.out.print("|" + (createCenteredText(cellWidth, data[i])));
            if (i == data.length -1){
                System.out.println("|");
            }

        }
    }

    public void makeChoice(String choice){
        switch (choice) {
            case "Login" -> createLoginPage();
            case "Register" -> createRegisterPage();
            default -> {
                System.out.println("Wrong Choice! Please try again");
                createLandingPage();
            }
        }
    }

}

