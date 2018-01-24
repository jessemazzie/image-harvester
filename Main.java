import javax.swing.*;
import javax.swing.text.html.parser.ParserDelegator;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.print.PrinterException;
import java.awt.print.*;
import java.io.*;
import java.util.*;
import java.net.*;

public class Main {
    public static void main(String[] args) {
        new MyFrame();
    }
}

class MyFrame extends JFrame implements ActionListener, MouseListener, Printable {
    URL url;
    String domain;
    URLConnection urlConnection;
    InputStreamReader isr;
    JList fileNameJList;
    DefaultListModel<String> fileNameList;
    JTextField urlField;
    ParserCallbackTagHandler tagHandler;

    MyFrame() {
        Container cp;
        cp = getContentPane();

        JButton goButton = newJButton("Go", "GO_BTN");
        JPanel inputPanel;
        inputPanel = new JPanel();
        fileNameList = new DefaultListModel<String>();
        
        getRootPane().setDefaultButton(goButton);
        fileNameJList = new JList(fileNameList);
        fileNameJList.addMouseListener(this);
        //fileNameJList.
        JScrollPane scrollPane = new JScrollPane(fileNameJList);

        urlField = new JTextField();
        urlField.setColumns(25);
        inputPanel.add(urlField);
        inputPanel.add(goButton);
        
        cp.add(scrollPane, BorderLayout.CENTER);
        cp.add(inputPanel, BorderLayout.SOUTH);
        printList(this);
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

        setTitle("Image Harvester");

        setVisible(true);
    }

    void updateURL() {
        if(!fileNameList.isEmpty())
            fileNameList.clear();

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

    @Override
    public void mouseClicked(MouseEvent me) {
        if(me.getClickCount() == 2) {
            int index = fileNameJList.locationToIndex(me.getPoint());

            System.out.println("Double clicked index: " + index);
        }
    }

    //These exist as a requirement for implementing MouseListener, but I have no use for them.
    public void mousePressed(MouseEvent me) {}
    public void mouseReleased(MouseEvent me) {}
    public void mouseEntered(MouseEvent me) {}
    public void mouseExited(MouseEvent me) {}

    void printList(Printable printable) {
        PrinterJob pj;
        PageFormat pf;

        pj = PrinterJob.getPrinterJob();
        pf = pj.pageDialog(pj.defaultPage());
        pj.setPrintable(printable, pf);
        try {
            if(pj.printDialog())
                pj.print();
        } catch (PrinterException pe) {
            pe.printStackTrace();
        }
    }

    @Override
    public int print(Graphics g, PageFormat pf, int pageIndex) {
        return 1;
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