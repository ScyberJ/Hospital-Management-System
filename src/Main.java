import java.io.IOException;

public class Main {
    private static final UI layout = new UI();

    private static final PatientHandler patients = new PatientHandler();
    public static void main(String[] args) {
        layout.createLandingPage();
        if (layout.currentChoice.equals("Login")){
            patients.login();
        }else if(layout.currentChoice.equals("Register")){
            patients.register();
        }else{
            System.out.println("Wrong choice");
        }
    }


}