import javax.swing.*;
import javax.swing.text.html.parser.ParserDelegator;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.net.*;

public class Main {
    public static void main(String[] args) {
        new MyFrame();
    }
}

class MyFrame extends JFrame implements ActionListener {
    URL url;
    String domain;
    URLConnection urlConnection;
    InputStreamReader isr;
    JList fileNameJList;
    DefaultListModel<String> fileNameList;
    JTextField urlField;
    ParserCallbackTagHandler tagHandler;

    MyFrame() {
        try {
            domain = "http://tomcuchta.com/";
            url = new URL(domain);
            urlConnection = url.openConnection();
            isr = new InputStreamReader(urlConnection.getInputStream());
            tagHandler = new ParserCallbackTagHandler(domain);

            new ParserDelegator().parse(isr, tagHandler, true);
        } catch(MalformedURLException mue) {
            mue.printStackTrace();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }

        
        Container cp;
        cp = getContentPane();

        fileNameList = new DefaultListModel<String>();
        fileNameList.addElement("Test");

        fileNameJList = new JList(fileNameList);
        //fileNameJList.addMouseListener(this);
        //fileNameJList.
        JScrollPane scrollPane = new JScrollPane(fileNameJList);

        urlField = new JTextField();

        cp.add(urlField, BorderLayout.SOUTH);
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