package ItsAPackage;

import javax.swing.*;
import java.awt.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

import static ItsAPackage.EmployeeInfo.*;
import static ItsAPackage.Palette.*;

public class RegisterPanel extends JPanel implements ActionListener, ChangeListener {
    private int height = 50, margin = 7, heightCount = 0, marginCount = 1;
    private JPanel mainPanel, westMargin, calcedInfo;
    private JScrollPane scrollFrame;
    private JLabel title, imageContainer, fName, lName, empNum, gender, workLoc, empType, deductRate, yrSalary, hrWage, hpw, wpr,calcedInfoLabel, grossIncome, netIncome;
    private ImageIcon portrait;
    private JTextField inFName, inLName, inEmpNum, tinDeductRate, inYrSalary, inHrWage, tinHpw, tinWpr;
    private JComboBox inGender, inWorkLoc, inEmpType;
    private JSlider sinDeductRate, sinHpw, sinWpr;
    private JButton updateProfileImage, update, confirm, cancel, remove;
    private EmployeeInfo profile;
    private String dataFileFolderPath;
    private File chosenProfileFile;

    public RegisterPanel() {
        this.setSize(new Dimension(700, 600));
        this.setLayout(new BorderLayout());

        title = new JLabel();
        title.setPreferredSize(new Dimension(350, 120));
        title.setFont(new Font("Trebuchet MS", Font.BOLD, 48));

        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setPreferredSize(new Dimension(700, 1000));

        scrollFrame = new JScrollPane(mainPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollFrame.setPreferredSize(new Dimension( 700,300));
        scrollFrame.getVerticalScrollBar().setUnitIncrement(10);
        scrollFrame.setBorder(null);

        westMargin = new JPanel();
        westMargin.setPreferredSize(new Dimension(80, 480));

        imageContainer = new JLabel();
        imageContainer.setBounds(350, 10, 180, 240);
        imageContainer.setOpaque(true);

        updateProfileImage = new JButton("Update");
        updateProfileImage.setBounds(385, 260, 110, 40);
        updateProfileImage.setFont(new Font("Georgia", Font.PLAIN, 20));
        updateProfileImage.setOpaque(true);
        updateProfileImage.setBorderPainted(false);
        updateProfileImage.addActionListener(this);

        fName = new JLabel();
        fName.setBounds(0, this.margin*marginCount+this.height*heightCount, 350, this.height);
        fName.setText("First Name: ");
        fName.setFont(new Font("Georgia", Font.PLAIN, 20));
        marginCount++; heightCount++;

        inFName = new JTextField();
        inFName.setBounds(0, this.margin*marginCount+this.height*heightCount, 250, this.height);
        inFName.setFont(new Font("", Font.PLAIN, 16));
        marginCount++; heightCount++;

        lName = new JLabel();
        lName.setBounds(0, this.margin*marginCount+this.height*heightCount, 350, this.height);
        lName.setText("Last Name: ");
        lName.setFont(new Font("Georgia", Font.PLAIN, 20));
        marginCount++; heightCount++;

        inLName = new JTextField();
        inLName.setBounds(0, this.margin*marginCount+this.height*heightCount, 250, this.height);
        inLName.setFont(new Font("", Font.PLAIN, 16));
        marginCount++; heightCount++;

        empNum = new JLabel();
        empNum.setBounds(0, this.margin*marginCount+this.height*heightCount, 350, this.height);
        empNum.setText("Assigned Employee Number: ");
        empNum.setFont(new Font("Georgia", Font.PLAIN, 20));
        marginCount++; heightCount++;

        inEmpNum = new JTextField();
        inEmpNum.setBounds(0, this.margin*marginCount+this.height*heightCount, 250, this.height);
        inEmpNum.setFont(new Font("", Font.PLAIN, 16));
        marginCount++; heightCount++;

        gender = new JLabel();
        gender.setBounds(0, this.margin*marginCount+this.height*heightCount, 100, this.height);
        gender.setText("Gender: ");
        gender.setFont(new Font("Georgia", Font.PLAIN, 20));

        inGender = new JComboBox(new String[]{"Male", "Female", "Other"});
        inGender.setLocation(100, this.margin*marginCount+this.height*heightCount);
        inGender.setSize(150, this.height);
        inGender.setFont(new Font("", Font.PLAIN, 16));
        marginCount++; heightCount++;

        workLoc = new JLabel();
        workLoc.setBounds(0, this.margin*marginCount+this.height*heightCount, 170, this.height);
        workLoc.setText("Work Location: ");
        workLoc.setFont(new Font("Georgia", Font.PLAIN, 20));

        inWorkLoc = new JComboBox(new String[]{"Mississauga", "Ottawa", "Chicago"});
        inWorkLoc.setLocation(170, this.margin*marginCount+this.height*heightCount);
        inWorkLoc.setSize(150, this.height);
        inWorkLoc.setFont(new Font("", Font.PLAIN, 16));
        marginCount++; heightCount++;

        empType = new JLabel();
        empType.setBounds(0, this.margin*marginCount+this.height*heightCount, 170, this.height);
        empType.setText("Employee Type: ");
        empType.setFont(new Font("Georgia", Font.PLAIN, 20));

        inEmpType = new JComboBox(new String[]{"Full Time Employee", "Part Time Employee"});
        inEmpType.setLocation(170, this.margin*marginCount+this.height*heightCount);
        inEmpType.setSize(215, this.height);
        inEmpType.setFont(new Font("", Font.PLAIN, 16));
        inEmpType.addActionListener(this);
        marginCount++; heightCount++;

        deductRate = new JLabel();
        deductRate.setBounds(0, this.margin*marginCount+this.height*heightCount, 350, this.height);
        deductRate.setText("Deduction Rate(%): ");
        deductRate.setFont(new Font("Georgia", Font.PLAIN, 20));
        marginCount++; heightCount++;

        tinDeductRate = new JTextField();
        tinDeductRate.setBounds(0, this.margin*marginCount+this.height*heightCount, 70, this.height);
        tinDeductRate.setFont(new Font("", Font.PLAIN, 16));
        tinDeductRate.addActionListener(this);

        sinDeductRate = new JSlider(0, 100, 0);
        sinDeductRate.setBounds(70, this.margin*marginCount+this.height*heightCount, 400, this.height);
        sinDeductRate.setMajorTickSpacing(10);
        sinDeductRate.setMinorTickSpacing(5);
        sinDeductRate.setPaintLabels(true);
        sinDeductRate.setPaintTicks(true);
        sinDeductRate.addChangeListener(this);
        marginCount++; heightCount++;

        yrSalary = new JLabel();
        yrSalary.setBounds(0, this.margin*marginCount+this.height*heightCount, 170, this.height);
        yrSalary.setText("Yearly Salary: ");
        yrSalary.setFont(new Font("Georgia", Font.PLAIN, 20));
        marginCount++; heightCount++;

        inYrSalary = new JTextField();
        inYrSalary.setBounds(0, this.margin*marginCount+this.height*heightCount, 250, this.height);
        inYrSalary.setFont(new Font("", Font.PLAIN, 16));
        marginCount--; heightCount--;

        hrWage = new JLabel();
        hrWage.setBounds(0, this.margin*marginCount+this.height*heightCount, 170, this.height);
        hrWage.setText("Hourly Wage: ");
        hrWage.setFont(new Font("Georgia", Font.PLAIN, 20));
        hrWage.setVisible(false);
        marginCount++; heightCount++;

        inHrWage = new JTextField();
        inHrWage.setBounds(0, this.margin*marginCount+this.height*heightCount, 250, this.height);
        inHrWage.setFont(new Font("", Font.PLAIN, 16));
        inHrWage.setVisible(false);
        marginCount++; heightCount++;

        hpw = new JLabel();
        hpw.setBounds(0, this.margin*marginCount+this.height*heightCount, 350, this.height);
        hpw.setText("Hours per Week: ");
        hpw.setFont(new Font("Georgia", Font.PLAIN, 20));
        hpw.setVisible(false);
        marginCount++; heightCount++;

        tinHpw = new JTextField();
        tinHpw.setBounds(0, this.margin*marginCount+this.height*heightCount, 70, this.height);
        tinHpw.setVisible(false);
        tinHpw.setFont(new Font("", Font.PLAIN, 16));
        tinHpw.addActionListener(this);

        sinHpw = new JSlider(0, 168, 0);
        sinHpw.setBounds(70, this.margin*marginCount+this.height*heightCount, 400, this.height);
        sinHpw.setMajorTickSpacing(24);
        sinHpw.setMinorTickSpacing(6);
        sinHpw.setPaintLabels(true);
        sinHpw.setPaintTicks(true);
        sinHpw.addChangeListener(this);
        sinHpw.setVisible(false);
        marginCount++; heightCount++;

        wpr = new JLabel();
        wpr.setBounds(0, this.margin*marginCount+this.height*heightCount, 350, this.height);
        wpr.setText("Weeks per Year: ");
        wpr.setFont(new Font("Georgia", Font.PLAIN, 20));
        wpr.setVisible(false);
        marginCount++; heightCount++;

        tinWpr = new JTextField();
        tinWpr.setBounds(0, this.margin*marginCount+this.height*heightCount, 70, this.height);
        tinWpr.setVisible(false);
        tinWpr.setFont(new Font("", Font.PLAIN, 16));
        tinWpr.addActionListener(this);

        sinWpr = new JSlider(0, 53, 0);
        sinWpr.setBounds(70, this.margin*marginCount+this.height*heightCount, 400, this.height);
        sinWpr.setMajorTickSpacing(10);
        sinWpr.setMinorTickSpacing(5);
        sinWpr.setPaintLabels(true);
        sinWpr.setPaintTicks(true);
        sinWpr.addChangeListener(this);
        sinWpr.setVisible(false);
        marginCount++; heightCount++;

        calcedInfo = new JPanel();
        calcedInfo.setLayout(null);

        update = new JButton("Update");
        update.setBounds(320, this.margin*0+this.height*0, 110, this.height);
        update.setFont(new Font("Georgia", Font.PLAIN, 20));
        update.setOpaque(true);
        update.setBorderPainted(false);
        update.addActionListener(this);

        calcedInfoLabel = new JLabel("Calculated Information");
        calcedInfoLabel.setFont(new Font("Georgia", Font.BOLD, 24));
        calcedInfoLabel.setBounds(0, this.margin*0+this.height*0, 300, this.height);
        marginCount++; heightCount++;

        grossIncome = new JLabel();
        grossIncome.setText("Annual Gross Income: ");
        grossIncome.setBounds(0, this.margin*1+this.height*1, 540, this.height);
        grossIncome.setFont(new Font("Georgia", Font.PLAIN, 20));
        marginCount++; heightCount++;

        netIncome = new JLabel();
        netIncome.setText("Annual Net Income: ");
        netIncome.setBounds(0, this.margin*2+this.height*2, 540, this.height);
        netIncome.setFont(new Font("Georgia", Font.PLAIN, 20));
        marginCount++; heightCount++;

        calcedInfo.add(update);
        calcedInfo.add(calcedInfoLabel);
        calcedInfo.add(grossIncome);
        calcedInfo.add(netIncome);

        confirm = new JButton("Confirm");
        confirm.setFont(new Font("Georgia", Font.PLAIN, 20));
        confirm.setOpaque(true);
        confirm.setBorderPainted(false);
        confirm.addActionListener(this);

        cancel = new JButton("Cancel");
        cancel.setFont(new Font("Georgia", Font.PLAIN, 20));
        cancel.setOpaque(true);
        cancel.setBorderPainted(false);
        cancel.addActionListener(this);

        remove = new JButton("Remove Employee");
        remove.setFont(new Font("Georgia", Font.PLAIN, 20));
        remove.setOpaque(true);
        remove.setBorderPainted(false);
        remove.addActionListener(this);

        mainPanel.add(fName);
        mainPanel.add(imageContainer);
        mainPanel.add(updateProfileImage);
        mainPanel.add(inFName);
        mainPanel.add(lName);
        mainPanel.add(inLName);
        mainPanel.add(empNum);
        mainPanel.add(inEmpNum);
        mainPanel.add(gender);
        mainPanel.add(inGender);
        mainPanel.add(workLoc);
        mainPanel.add(inWorkLoc);
        mainPanel.add(empType);
        mainPanel.add(inEmpType);
        mainPanel.add(deductRate);
        mainPanel.add(tinDeductRate);
        mainPanel.add(sinDeductRate);
        mainPanel.add(yrSalary);
        mainPanel.add(inYrSalary);
        mainPanel.add(hrWage);
        mainPanel.add(inHrWage);
        mainPanel.add(hpw);
        mainPanel.add(tinHpw);
        mainPanel.add(sinHpw);
        mainPanel.add(wpr);
        mainPanel.add(tinWpr);
        mainPanel.add(sinWpr);
        mainPanel.add(calcedInfo);
        mainPanel.add(confirm);
        mainPanel.add(cancel);
        mainPanel.add(remove);

        this.add(title, BorderLayout.NORTH);
        this.add(scrollFrame, BorderLayout.CENTER);
        this.add(westMargin, BorderLayout.WEST);
        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==updateProfileImage) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setAcceptAllFileFilterUsed(false);
            fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Raster image", "jpg", "jpeg", "png"));
            int response = fileChooser.showOpenDialog(null);
            if (response==JFileChooser.APPROVE_OPTION) {
                chosenProfileFile = fileChooser.getSelectedFile();
                imageContainer.setIcon(new ImageIcon(String.valueOf(chosenProfileFile)));
            }
        } else if (e.getSource()==tinDeductRate) {
            try {
                sinDeductRate.setValue((int) Double.parseDouble(tinDeductRate.getText()));
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(null, "Please enter a number", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource()==tinHpw) {
            try {
                sinHpw.setValue((int) Double.parseDouble(tinHpw.getText()));
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(null, "Please enter a number", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource()==tinWpr) {
            try {
                sinWpr.setValue((int) Double.parseDouble(tinWpr.getText()));
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(null, "Please enter a number", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource()==inEmpType) {
            if(inEmpType.getSelectedIndex()==0) {
                mainPanel.setPreferredSize(new Dimension(700, 1080));
                calcedInfo.setBounds(0, this.margin*14+this.height*13, 500, this.margin*2+this.height*3);
                confirm.setBounds(0, this.margin*17+this.height*16+30, 120, this.height);
                cancel.setBounds(140, this.margin*17+this.height*16+30, 110, this.height);
                remove.setBounds(270, this.margin*17+this.height*16+30, 215, this.height);
                yrSalary.setVisible(true);
                inYrSalary.setVisible(true);
                hrWage.setVisible(false);
                inHrWage.setVisible(false);
                hpw.setVisible(false);
                tinHpw.setVisible(false);
                sinHpw.setVisible(false);
                wpr.setVisible(false);
                tinWpr.setVisible(false);
                sinWpr.setVisible(false);
            } else {
                mainPanel.setPreferredSize(new Dimension(700, 1300));
                calcedInfo.setBounds(0, this.margin*18+this.height*17, 500, this.margin*2+this.height*3);
                confirm.setBounds(0, this.margin*21+this.height*20+30, 120, this.height);
                cancel.setBounds(140, this.margin*21+this.height*20+30, 110, this.height);
                remove.setBounds(270, this.margin*21+this.height*20+30, 215, this.height);
                yrSalary.setVisible(false);
                inYrSalary.setVisible(false);
                hrWage.setVisible(true);
                inHrWage.setVisible(true);
                hpw.setVisible(true);
                tinHpw.setVisible(true);
                sinHpw.setVisible(true);
                wpr.setVisible(true);
                tinWpr.setVisible(true);
                sinWpr.setVisible(true);
            }
        } else if (e.getSource()==update) {
            sinDeductRate.setValue((int) Double.parseDouble(tinDeductRate.getText()));
            sinHpw.setValue((int) Double.parseDouble(tinHpw.getText()));
            sinWpr.setValue((int) Double.parseDouble(tinWpr.getText()));
            if (inEmpType.getSelectedIndex()==0) {
                grossIncome.setText("Annual Gross Income: " + Double.parseDouble(inYrSalary.getText()));
                netIncome.setText("Annual Net Income: " + Double.parseDouble(inYrSalary.getText()) * (1-Double.parseDouble(tinDeductRate.getText())/100));
            } else if (inEmpType.getSelectedIndex()==1) {
                grossIncome.setText("Annual Gross Income: " + Double.parseDouble(inHrWage.getText())*Integer.parseInt(tinHpw.getText())*Integer.parseInt(tinWpr.getText()));
                netIncome.setText("Annual Net Income: " + Double.parseDouble(inHrWage.getText())*Integer.parseInt(tinHpw.getText())*Integer.parseInt(tinWpr.getText()) * (1-Double.parseDouble(tinDeductRate.getText())/100));
            }
        }

        else if (e.getSource()==confirm) {
            if (chosenProfileFile != null) {
                try {
                    Files.copy(Paths.get(String.valueOf(chosenProfileFile)),
                            Paths.get(dataFileFolderPath + profile.getEmpNumber() + ".png"),
                            StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            sinDeductRate.setValue((int) Double.parseDouble(tinDeductRate.getText()));
            sinHpw.setValue((int) Double.parseDouble(tinHpw.getText()));
            sinWpr.setValue((int) Double.parseDouble(tinWpr.getText()));
            int confirmResult = JOptionPane.showConfirmDialog(this,"Sure? You want to submit?", "Confirm",
               JOptionPane.YES_NO_OPTION,
               JOptionPane.QUESTION_MESSAGE);
            if (confirmResult == JOptionPane.YES_OPTION) {
                try {
                    Integer.parseInt(inEmpNum.getText());
                } catch (NumberFormatException e1) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid employee number", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                EmployeeInfo checkDupeNumProfile = Main.ht.getFromTable(Integer.parseInt(inEmpNum.getText()));
                if (checkDupeNumProfile != null && checkDupeNumProfile != profile) {
                    JOptionPane.showMessageDialog(null, "Employee number \""+inEmpNum.getText()+"\" is already in use", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (inFName.getText().contains("Ï") || inLName.getText().contains("Ï")) {
                    JOptionPane.showMessageDialog(null, "Special character \"Ï\" must not be included in the name", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    if (profile==null) {
                        if (inEmpType.getSelectedIndex()==0) {
                            Main.ht.addToTable(new FTE(Integer.parseInt(inEmpNum.getText()), inFName.getText(), inLName.getText(), Gender.values()[inGender.getSelectedIndex()], WorkLocation.values()[inWorkLoc.getSelectedIndex()], Integer.parseInt(tinDeductRate.getText()), Double.parseDouble(inYrSalary.getText())));
                        } else {
                            Main.ht.addToTable(new PTE(Integer.parseInt(inEmpNum.getText()), inFName.getText(), inLName.getText(), Gender.values()[inGender.getSelectedIndex()], WorkLocation.values()[inWorkLoc.getSelectedIndex()], Integer.parseInt(tinDeductRate.getText()), Double.parseDouble(inHrWage.getText()), Integer.parseInt(tinHpw.getText()), Integer.parseInt(tinWpr.getText())));
                        }
                        Main.N = Main.ht.getTableSize();
                    } else {
                        Main.ht.removeFromTable(profile.getEmpNumber());
                        if (inEmpType.getSelectedIndex()==0) {
                            profile = new FTE(Integer.parseInt(inEmpNum.getText()), inFName.getText(), inLName.getText(), Gender.values()[inGender.getSelectedIndex()], WorkLocation.values()[inWorkLoc.getSelectedIndex()], Integer.parseInt(tinDeductRate.getText()), Double.parseDouble(inYrSalary.getText()));
                        } else {
                            profile = new PTE(Integer.parseInt(inEmpNum.getText()), inFName.getText(), inLName.getText(), Gender.values()[inGender.getSelectedIndex()], WorkLocation.values()[inWorkLoc.getSelectedIndex()], Integer.parseInt(tinDeductRate.getText()), Double.parseDouble(inHrWage.getText()), Integer.parseInt(tinHpw.getText()), Integer.parseInt(tinWpr.getText()));
                        }
                        Main.ht.addToTable(profile);
                    }
                    Main.gui.returnToDataPanel();
                }
            }

        } else if (e.getSource()==cancel) {
            int cancelResult = JOptionPane.showConfirmDialog(this,"Your progress will not be saved", "Cancel?",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            if (cancelResult == JOptionPane.YES_OPTION) {
                Main.gui.returnToDataPanel();
            }
        } else if (e.getSource()==remove) {
            int removeResult = JOptionPane.showConfirmDialog(this,"You CANNOT undo this action", "Remove employee?",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            if (removeResult == JOptionPane.YES_OPTION) {
                Main.ht.removeFromTable(this.profile.getEmpNumber());
                Main.N = Main.ht.getTableSize();
                Main.gui.returnToDataPanel();
            }
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource()==sinDeductRate) {
            tinDeductRate.setText(String.valueOf(sinDeductRate.getValue()));
        } else if (e.getSource()==sinHpw) {
            tinHpw.setText(String.valueOf(sinHpw.getValue()));
        } else if (e.getSource()==sinWpr) {
            tinWpr.setText(String.valueOf(sinWpr.getValue()));
        }
    }

    public void setProfile(EmployeeInfo data) {
        this.profile = data;
        JScrollBar verticalScrollBar = scrollFrame.getVerticalScrollBar();
        verticalScrollBar.setValue(verticalScrollBar.getMinimum());
        String[] dir = String.valueOf(Main.file).split("/");
        dataFileFolderPath = String.join("/", Arrays.copyOfRange(dir, 0, dir.length-1))+"/";
        if (data==null) {
            grossIncome.setText("Annual Gross Income: " + 0);
            netIncome.setText("Annual Net Income: " + 0);
            title.setText("    Register Employee");
            portrait = new ImageIcon("src/ItsAPackage/StickMan.png");
            imageContainer.setIcon(portrait);
            inFName.setText("Ex. Steve");
            inLName.setText("Ex. Jobs");
            inEmpNum.setText("Ex. 000001");
            inEmpType.setSelectedIndex(0);
            tinDeductRate.setText("0");
            sinDeductRate.setValue(0);
            inYrSalary.setText("0");
            inHrWage.setText("0");
            tinHpw.setText("0");
            sinHpw.setValue(0);
            tinWpr.setText("0");
            sinWpr.setValue(0);
            cancel.setVisible(false);
            remove.setVisible(false);
        } else {
            title.setText("    Edit Profile");
            File temp = new File(dataFileFolderPath + profile.getEmpNumber() +".png");
            if (temp.exists()) {
                portrait = new ImageIcon(String.valueOf(temp));
                portrait.getImage().flush();
            }
            else
                portrait = new ImageIcon("src/ItsAPackage/StickMan.png");
            imageContainer.setIcon(portrait);
            imageContainer.validate();
            inFName.setText(data.getFirstName());
            inLName.setText(data.getLastName());
            inEmpNum.setText(String.valueOf(data.getEmpNumber()));
            inGender.setSelectedItem(String.valueOf(String.valueOf(data.getGender()).substring(0, 1) + String.valueOf(data.getGender()).substring(1).toLowerCase()));
            inWorkLoc.setSelectedItem(String.valueOf(data.getWorkLocation()).substring(0, 1) + String.valueOf(data.getWorkLocation()).substring(1).toLowerCase());
            tinDeductRate.setText(String.valueOf(data.getDeductionRate()));
            sinDeductRate.setValue(data.getDeductionRate());
            cancel.setVisible(true);
            remove.setVisible(true);
            if (profile instanceof FTE) {
                inYrSalary.setText(String.valueOf(((FTE) profile).getYearlySalary()));
                inHrWage.setText("0");
                tinHpw.setText("0");
                sinHpw.setValue(0);
                tinWpr.setText("0");
                sinWpr.setValue(0);
                inEmpType.setSelectedIndex(0);
                grossIncome.setText("Annual Gross Income: " + ((FTE) profile).calcAnnualGrossIncome());
                netIncome.setText("Annual Net Income: " + ((FTE) profile).calcAnnualNetIncome());
            } else {
                inYrSalary.setText("0");
                inHrWage.setText(String.valueOf(((PTE) profile).getHourlyWage()));
                tinHpw.setText(String.valueOf(((PTE) data).getHoursPerWeek()));
                sinHpw.setValue(((PTE) data).getHoursPerWeek());
                tinWpr.setText(String.valueOf(((PTE) data).getWeeksPerYear()));
                sinWpr.setValue(((PTE) data).getWeeksPerYear());
                inEmpType.setSelectedIndex(1);
                grossIncome.setText("Annual Gross Income: " + ((PTE) profile).calcAnnualGrossIncome());
                netIncome.setText("Annual Net Income: " + ((PTE) profile).calcAnnualNetIncome());
            }
        }
    }

    public void colorTheme(int id) {
        if (id==0) {
            this.setBackground(cmainBackColor);
            title.setForeground(ctitleForeColor);
            mainPanel.setBackground(cmainBackColor);
            westMargin.setBackground(cmainBackColor);
            imageContainer.setBackground(cmainBackColor);
            updateProfileImage.setBackground(cbuttonBackColor);
            updateProfileImage.setForeground(clabelForeColor);
            fName.setForeground(clabelForeColor);
            inFName.setForeground(ctextFieldForeColor);
            inFName.setBackground(ctextFieldBackColor);
            inFName.setCaretColor(ctextFieldCaretColor);
            lName.setForeground(clabelForeColor);
            inLName.setForeground(ctextFieldForeColor);
            inLName.setBackground(ctextFieldBackColor);
            inLName.setCaretColor(ctextFieldCaretColor);
            empNum.setForeground(clabelForeColor);
            inEmpNum.setForeground(ctextFieldForeColor);
            inEmpNum.setBackground(ctextFieldBackColor);
            inEmpNum.setCaretColor(ctextFieldCaretColor);
            gender.setForeground(clabelForeColor);
            workLoc.setForeground(clabelForeColor);
            empType.setForeground(clabelForeColor);
            deductRate.setForeground(clabelForeColor);
            tinDeductRate.setForeground(ctextFieldForeColor);
            tinDeductRate.setBackground(ctextFieldBackColor);
            tinDeductRate.setCaretColor(ctextFieldCaretColor);
            sinDeductRate.setForeground(clabelForeColor);
            yrSalary.setForeground(clabelForeColor);
            inYrSalary.setForeground(ctextFieldForeColor);
            inYrSalary.setBackground(ctextFieldBackColor);
            inYrSalary.setCaretColor(ctextFieldCaretColor);
            hrWage.setForeground(clabelForeColor);
            inHrWage.setForeground(ctextFieldForeColor);
            inHrWage.setBackground(ctextFieldBackColor);
            inHrWage.setCaretColor(ctextFieldCaretColor);
            hpw.setForeground(clabelForeColor);
            tinHpw.setForeground(ctextFieldForeColor);
            tinHpw.setBackground(ctextFieldBackColor);
            tinHpw.setCaretColor(ctextFieldCaretColor);
            sinHpw.setForeground(clabelForeColor);
            wpr.setForeground(clabelForeColor);
            tinWpr.setForeground(ctextFieldForeColor);
            tinWpr.setBackground(ctextFieldBackColor);
            tinWpr.setCaretColor(ctextFieldCaretColor);
            sinWpr.setForeground(clabelForeColor);
            calcedInfo.setBackground(cmainBackColor);
            update.setBackground(cbuttonBackColor);
            update.setForeground(clabelForeColor);
            calcedInfoLabel.setForeground(clabelForeColor);
            grossIncome.setForeground(clabelForeColor);
            netIncome.setForeground(clabelForeColor);
            confirm.setBackground(cbuttonBackColor);
            confirm.setForeground(clabelForeColor);
            cancel.setBackground(cbuttonBackColor);
            cancel.setForeground(clabelForeColor);
            remove.setBackground(cbuttonBackColor);
            remove.setForeground(clabelForeColor);
        } else {
            this.setBackground(dmainBackColor);
            title.setForeground(dtitleForeColor);
            mainPanel.setBackground(dmainBackColor);
            westMargin.setBackground(dmainBackColor);
            imageContainer.setBackground(dmainBackColor);
            updateProfileImage.setBackground(dbuttonBackColor);
            updateProfileImage.setForeground(dlabelForeColor);
            fName.setForeground(dlabelForeColor);
            inFName.setForeground(dtextFieldForeColor);
            inFName.setBackground(dtextFieldBackColor);
            inFName.setCaretColor(dtextFieldCaretColor);
            lName.setForeground(dlabelForeColor);
            inLName.setForeground(dtextFieldForeColor);
            inLName.setBackground(dtextFieldBackColor);
            inLName.setCaretColor(dtextFieldCaretColor);
            empNum.setForeground(dlabelForeColor);
            inEmpNum.setForeground(dtextFieldForeColor);
            inEmpNum.setBackground(dtextFieldBackColor);
            inEmpNum.setCaretColor(dtextFieldCaretColor);
            gender.setForeground(dlabelForeColor);
            workLoc.setForeground(dlabelForeColor);
            empType.setForeground(dlabelForeColor);
            deductRate.setForeground(dlabelForeColor);
            tinDeductRate.setForeground(dtextFieldForeColor);
            tinDeductRate.setBackground(dtextFieldBackColor);
            tinDeductRate.setCaretColor(dtextFieldCaretColor);
            sinDeductRate.setForeground(dlabelForeColor);
            yrSalary.setForeground(dlabelForeColor);
            inYrSalary.setForeground(dtextFieldForeColor);
            inYrSalary.setBackground(dtextFieldBackColor);
            inYrSalary.setCaretColor(dtextFieldCaretColor);
            hrWage.setForeground(dlabelForeColor);
            inHrWage.setForeground(dtextFieldForeColor);
            inHrWage.setBackground(dtextFieldBackColor);
            inHrWage.setCaretColor(dtextFieldCaretColor);
            hpw.setForeground(dlabelForeColor);
            tinHpw.setForeground(dtextFieldForeColor);
            tinHpw.setBackground(dtextFieldBackColor);
            tinHpw.setCaretColor(dtextFieldCaretColor);
            sinHpw.setForeground(dlabelForeColor);
            wpr.setForeground(dlabelForeColor);
            tinWpr.setForeground(dtextFieldForeColor);
            tinWpr.setBackground(dtextFieldBackColor);
            tinWpr.setCaretColor(dtextFieldCaretColor);
            sinWpr.setForeground(dlabelForeColor);
            calcedInfo.setBackground(dmainBackColor);
            update.setBackground(dbuttonBackColor);
            update.setForeground(dlabelForeColor);
            calcedInfoLabel.setForeground(dlabelForeColor);
            grossIncome.setForeground(dlabelForeColor);
            netIncome.setForeground(dlabelForeColor);
            confirm.setBackground(dbuttonBackColor);
            confirm.setForeground(dlabelForeColor);
            cancel.setBackground(dbuttonBackColor);
            cancel.setForeground(dlabelForeColor);
            remove.setBackground(dbuttonBackColor);
            remove.setForeground(dlabelForeColor);
        }
    }
}
