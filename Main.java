import javax.swing.*;
import javax.swing.text.html.parser.ParserDelegator;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.*;

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
        JButton goButton = newJButton("Go", "GO_BTN");
        JPanel inputPanel;
        inputPanel = new JPanel();
        fileNameList = new DefaultListModel<String>();
        
        Container cp;
        cp = getContentPane();
        getRootPane().setDefaultButton(goButton);
        fileNameJList = new JList(fileNameList);
        //fileNameJList.addMouseListener(this);
        //fileNameJList.
        JScrollPane scrollPane = new JScrollPane(fileNameJList);

        urlField = new JTextField();
        urlField.setColumns(25);
        inputPanel.add(urlField);
        inputPanel.add(goButton);
        
        cp.add(scrollPane, BorderLayout.CENTER);
        cp.add(inputPanel, BorderLayout.SOUTH);

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

    void updateURL() {
        System.out.println("Test");
        try {
            domain = urlField.getText();
            System.out.println(domain);
            url = new URL(domain);
            urlConnection = url.openConnection();
            isr = new InputStreamReader(urlConnection.getInputStream());
            tagHandler = new ParserCallbackTagHandler(domain, fileNameList);

            new ParserDelegator().parse(isr, tagHandler, true);
        } catch(MalformedURLException mue) {
            JOptionPane.showMessageDialog(this, "Error: URL is invalid.", "Invalid URL", JOptionPane.ERROR_MESSAGE);
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }

    JButton newJButton(String label, String actionCommand) {
        JButton tempButton = new JButton(label);
        tempButton.setActionCommand(actionCommand);
        tempButton.addActionListener(this);

        return tempButton;
    }

    public void actionPerformed(ActionEvent ae) {
        String ac = ae.getActionCommand();

        if(ac.equals("GO_BTN")) {
            updateURL();
        }
    }
}

class ParserCallbackTagHandler extends HTMLEditorKit.ParserCallback {
    String domain;
    Object attribute;
    DefaultListModel<String> fileNameList;

    ParserCallbackTagHandler(String domain, DefaultListModel<String> fileNameList) {
        this.domain = domain;
        this.fileNameList = fileNameList;
    }

    @Override
    public void handleSimpleTag(HTML.Tag tag, MutableAttributeSet attSet, int pos) {
        if(tag == HTML.Tag.IMG) {
            attribute = attSet.getAttribute(HTML.Attribute.SRC);
            if(attribute != null)
                fileNameList.addElement(attribute.toString());
        }
    }
}