import javax.swing.*; 
import java.awt.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        new MyFrame();
    }
}

class MyFrame extends JFrame {
    JList fileNameJList;
    DefaultListModel<String> fileNameList;

    MyFrame() {
        Container cp;
        cp = getContentPane();

        setupMainFrame();


        
    }

    void setupMainFrame() {
        Toolkit tk;
        Dimension d;

        tk = Toolkit.getDefaultToolkit();
        d = tk.getScreenSize();
        setSize(d.width/4, d.height/4);
        setLocation(d.width/4, d.height/4);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setTitle("Project 1");

        setVisible(true);
    }
}