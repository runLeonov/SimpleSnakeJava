import javax.swing.*;

public class MainWindow extends JFrame {
    public MainWindow() {
        setTitle("Змеюка");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocation(400,400);
        add(new GameField());
        setVisible(true);
    }

    public static void main(String[] args) {
        MainWindow window = new MainWindow();
    }
}
