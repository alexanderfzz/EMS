package ItsAPackage;

import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import javax.swing.*;
import java.awt.*;

import static ItsAPackage.Palette.*;

public class MainFrame extends JFrame implements ActionListener {
    private CardLayout cl = new CardLayout();
    private ImageIcon icon = new ImageIcon("src/ItsAPackage/EMS_Icon.png");
    private JButton navHome, navRegister, navData, navSettings;
    private JPanel navBar, buttonContainer, content;
    public HomePanel winHome;
    private RegisterPanel winRegister;
    private DatabasePanel winData;
    public SettingsPanel winSettings;

    public MainFrame() {
        this.setSize(new Dimension(900, 600));
        this.setResizable(false);
        this.setTitle("EMS interface");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.out.println("closing");
                try {
                    writeToFile();
                    writeSystemFile();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        try {
            //set icon for macOS (other systems which do support this method)
            Taskbar.getTaskbar().setIconImage(icon.getImage());
        } catch (final UnsupportedOperationException | SecurityException e) {
            //set icon for other os
            this.setIconImage(icon.getImage());
        }

        navHome = new JButton("Home");
        navHome.setBorderPainted(false);
        navHome.setFont(new Font("Georgia", Font.PLAIN, 28));
        navHome.setOpaque(true);
        navHome.addActionListener(this);

        navRegister = new JButton("Register");
        navRegister.setBorderPainted(false);
        navRegister.setFont(new Font("Georgia", Font.PLAIN, 28));
        navRegister.setOpaque(true);
        navRegister.addActionListener(this);

        navData = new JButton("Database");
        navData.setBorderPainted(false);
        navData.setFont(new Font("Georgia", Font.PLAIN, 28));
        navData.setOpaque(true);
        navData.addActionListener(this);

        navSettings = new JButton("Settings");
        navSettings.setBorderPainted(false);
        navSettings.setFont(new Font("Georgia", Font.PLAIN, 28));
        navSettings.setOpaque(true);
        navSettings.addActionListener(this);

        GridLayout gl = new GridLayout(4, 1);
        gl.setVgap(10);
        buttonContainer = new JPanel();
        buttonContainer.setLayout(gl);
        buttonContainer.setBounds(0, 50, 200, 400);
        buttonContainer.add(navHome);
        buttonContainer.add(navRegister);
        buttonContainer.add(navData);
        buttonContainer.add(navSettings);

        navBar = new JPanel();
        navBar.setPreferredSize(new Dimension(200, 600));
        navBar.setLayout(null);


        navBar.add(buttonContainer);

        winHome = new HomePanel();
        winRegister = new RegisterPanel();
        winData = new DatabasePanel();
        winSettings = new SettingsPanel();

        content = new JPanel();
        content.setPreferredSize(new Dimension(700, 600));
        content.setLayout(cl);

        content.add(winHome, "1");
        content.add(winRegister, "2");
        content.add(winData, "3");
        content.add(winSettings, "4");

        cl.show(content, "1");

        this.add(navBar, BorderLayout.WEST);
        this.add(content, BorderLayout.CENTER);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==navHome)
            cl.show(content, "1");
        else if (e.getSource()==navRegister) {
            winRegister.setProfile(null);
            cl.show(content, "2");
        }
        else if (e.getSource()==navData) {
            winData.updateList();
            cl.show(content, "3");
        } else if (e.getSource()==navSettings) {
            try {
                winSettings.updateList(false);
                winSettings.scrollToTop();
                cl.show(content, "4");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void showProfile(EmployeeInfo profile) {
        winRegister.setProfile(profile);
        cl.show(content, "2");
    }

    public void returnToDataPanel() {
        winData.updateList();
        cl.show(content, "3");
    }

    public void writeToFile() throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(Main.file));
        EmployeeInfo[] streamOfData = Main.ht.returnAll();
        for (EmployeeInfo i : streamOfData) {
             if (i instanceof FTE)
                 bw.write("0"+"Ï"+i.getEmpNumber()+"Ï"+i.getFirstName()+"Ï"+i.getLastName()+"Ï"+i.getGender().ordinal()+"Ï"+i.getWorkLocation().ordinal()+"Ï"+i.getDeductionRate()+"Ï"+((FTE) i).getYearlySalary()+"\n");
             else
                 bw.write("1"+"Ï"+i.getEmpNumber()+"Ï"+i.getFirstName()+"Ï"+i.getLastName()+"Ï"+i.getGender().ordinal()+"Ï"+i.getWorkLocation().ordinal()+"Ï"+i.getDeductionRate()+"Ï"+((PTE) i).getHourlyWage()+"Ï"+((PTE) i).getHoursPerWeek()+"Ï"+((PTE) i).getWeeksPerYear()+"\n");
        }
        bw.close();
    }

    public void writeSystemFile() throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(Main.systemData));
        bw.write(Main.colorTheme + "\n");
        String[] path = String.valueOf(Main.file).split("/");
        bw.write(String.join("/", Arrays.copyOfRange(path, path.length-2, path.length)));
        bw.close();
    }

    public void changeColorTheme(int theme) {
        this.colorTheme(theme);
        winHome.colorTheme(theme);
        winRegister.colorTheme(theme);
        winData.colorTheme(theme);
        winSettings.colorTheme(theme);
    }

    public void colorTheme(int id) {
        if (id==0) {
            navHome.setBackground(cnavButtonBackColor);
            navHome.setForeground(cnavButtonForeColor);

            navRegister.setBackground(cnavButtonBackColor);
            navRegister.setForeground(cnavButtonForeColor);

            navData.setBackground(cnavButtonBackColor);
            navData.setForeground(cnavButtonForeColor);

            navSettings.setBackground(cnavButtonBackColor);
            navSettings.setForeground(cnavButtonForeColor);

            buttonContainer.setBackground(clistBackColor);

            navBar.setBackground(clistBackColor);
        } else {
            navHome.setBackground(dnavButtonBackColor);
            navHome.setForeground(dnavButtonForeColor);

            navRegister.setBackground(dnavButtonBackColor);
            navRegister.setForeground(dnavButtonForeColor);

            navData.setBackground(dnavButtonBackColor);
            navData.setForeground(dnavButtonForeColor);

            navSettings.setBackground(dnavButtonBackColor);
            navSettings.setForeground(dnavButtonForeColor);

            buttonContainer.setBackground(dlistBackColor);

            navBar.setBackground(dlistBackColor);
        }
    }

}
