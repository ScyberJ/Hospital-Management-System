public class Main {
    public static void main(String[] args) {
        UI layout = new UI(180);

        String[] headers = new String[]{"Date", "Name", "Time", "Booked by", "Town"};

        String[] row1 = new String[]{"22-98-1222", "bayer", "08:00", "John", "Rehoboth"};
        String[] row2 = new String[]{"22-98-6222", "Helly", "18:00"};

        String[][] data = new String[][]{row1, row2};

        layout.createTable(headers, data);



    }


}