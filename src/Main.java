import java.io.IOException;

public class Main {
    private static final UI layout = new UI();
    public static void main(String[] args) throws IOException {
        layout.createLandingPage();
        if (layout.currentChoice.equals("Login")){
            PatientHandler.login();
        }else if(layout.currentChoice.equals("Register")){
            PatientHandler.register();
        }else{
            System.out.println("Wrong choice");
        }
    }


}