import javax.swing.*; 
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.net.*;

public class Main {
    public static void main(String[] args) {
        new MyFrame();
    }
}

class MyFrame extends JFrame implements ActionListener {
    URL url;
    URLConnection urlConnection;
    JList fileNameJList;
    DefaultListModel<String> fileNameList;
    JTextField urlField;

    MyFrame() {
        try {
            url = new URL("http://www.agile5technologies.com");
        } catch(MalformedURLException mue) {
            mue.printStackTrace();
        }
        Container cp;
        cp = getContentPane();

        fileNameList = new DefaultListModel<String>();
        fileNameList.addElement("Test");

        fileNameJList = new JList(fileNameList);
        JScrollPane scrollPane = new JScrollPane(fileNameJList);

        urlField = new JTextField();

        cp.add(urlField, BorderLayout.NORTH);
        cp.add(scrollPane, BorderLayout.CENTER);
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

    public void actionPerformed(ActionEvent ae) {

    }
}