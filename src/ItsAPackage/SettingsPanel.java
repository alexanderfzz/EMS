package ItsAPackage;

import javax.print.attribute.standard.JobMessageFromOperator;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

import static ItsAPackage.Palette.*;

public class SettingsPanel extends JPanel implements ActionListener {
    private JLabel title, CTheme, dataHandle, chooseData;
    private JPanel panelForScrolling, centerPanel, westMargin, eastMargin;
    public JPanel list;
    private JScrollPane listScroll, mainScroll;
    private JButton upload, reload, createButton, importButton;
    private JRadioButton dark, cream;
    private ButtonGroup themeBG;
    public ButtonGroup fileBG;
    private File dataFileFolder = new File("src/ItsAPackage/DataFileFolder");
    private String[] filePaths;
    public InputDialog inDialog;

    public SettingsPanel() {
        this.setSize(new Dimension(700, 600));
        this.setLayout(new BorderLayout());

        title = new JLabel("    Settings");
        title.setPreferredSize(new Dimension(350, 120));
        title.setFont(new Font("Trebuchet MS", Font.BOLD, 48));

        panelForScrolling = new JPanel();
        panelForScrolling.setLayout(new BorderLayout());
        panelForScrolling.setPreferredSize(new Dimension(700, 650));

        mainScroll = new JScrollPane(panelForScrolling, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        mainScroll.setPreferredSize(new Dimension(540, 480));
        mainScroll.getVerticalScrollBar().setUnitIncrement(10);
        mainScroll.setBorder(null);

        westMargin = new JPanel();
        westMargin.setPreferredSize(new Dimension(40, 480));

        eastMargin = new JPanel();
        eastMargin.setPreferredSize(new Dimension(80, 480));

        centerPanel = new JPanel();
        centerPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 40, 20));
        centerPanel.setOpaque(true);

        CTheme = new JLabel("Color Theme");
        CTheme.setPreferredSize(new Dimension(540, 60));
        CTheme.setFont(new Font("Georgia", Font.PLAIN, 28));

        dark = new JRadioButton("Dark");
        dark.setFont(new Font("Georgia", Font.PLAIN, 24));
        dark.addActionListener(this);

        cream = new JRadioButton("Cream");
        cream.setFont(new Font("Georgia", Font.PLAIN, 24));
        cream.addActionListener(this);

        switch (Main.colorTheme) {
            case 0:
                cream.setSelected(true);
                break;
            case 1:
                dark.setSelected(true);
                break;
        }

        themeBG = new ButtonGroup();
        themeBG.add(dark);
        themeBG.add(cream);

        dataHandle = new JLabel("Manual Data Handle");
        dataHandle.setPreferredSize(new Dimension(540, 60));
        dataHandle.setFont(new Font("Georgia", Font.PLAIN, 28));

        upload = new JButton("Upload data");
        upload.setPreferredSize(new Dimension(165, 60));
        upload.setFont(new Font("Georgia", Font.PLAIN, 20));
        upload.setOpaque(true);
        upload.setBorderPainted(false);
        upload.addActionListener(this);

        reload = new JButton("Reload data");
        reload.setPreferredSize(new Dimension(165, 60));
        reload.setFont(new Font("Georgia", Font.PLAIN, 20));
        reload.setOpaque(true);
        reload.setBorderPainted(false);
        reload.addActionListener(this);

        chooseData = new JLabel("Choose data file");
        chooseData.setPreferredSize(new Dimension(200, 60));
        chooseData.setFont(new Font("Georgia", Font.PLAIN, 28));

        createButton = new JButton("Create");
        createButton.setPreferredSize(new Dimension(95, 40));
        createButton.setFont(new Font("Georgia", Font.PLAIN, 16));
        createButton.setOpaque(true);
        createButton.setBorderPainted(false);
        createButton.addActionListener(this);

        importButton = new JButton("Import");
        importButton.setPreferredSize(new Dimension(95, 40));
        importButton.setFont(new Font("Georgia", Font.PLAIN, 16));
        importButton.setOpaque(true);
        importButton.setBorderPainted(false);
        importButton.addActionListener(this);

        list = new JPanel();
        fileBG = new ButtonGroup();

        listScroll = new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        listScroll.setPreferredSize(new Dimension( 540,150));
        listScroll.getVerticalScrollBar().setUnitIncrement(10);

        centerPanel.add(CTheme);
        centerPanel.add(dark);
        centerPanel.add(cream);
        centerPanel.add(dataHandle);
        centerPanel.add(upload);
        centerPanel.add(reload);
        centerPanel.add(chooseData);
        centerPanel.add(createButton);
        centerPanel.add(importButton);
        centerPanel.add(listScroll);

        panelForScrolling.add(westMargin, BorderLayout.WEST);
        panelForScrolling.add(eastMargin, BorderLayout.EAST);
        panelForScrolling.add(centerPanel);

        this.add(title, BorderLayout.NORTH);
        this.add(mainScroll, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==upload) {
            int uploadConfirm = JOptionPane.showConfirmDialog(this,"you CANNOT undo this action!", "Upload data?",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            if (uploadConfirm == JOptionPane.YES_OPTION) {
                try {
                    Main.gui.writeToFile();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } else if (e.getSource()==reload) {
            int reloadConfirm = JOptionPane.showConfirmDialog(this,"you CANNOT undo this action!", "Reload data?",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            if (reloadConfirm == JOptionPane.YES_OPTION) {
                try {
                    Main.checkFile();
                    Main.readFile();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } else if (e.getSource()==dark) {
            Main.colorTheme = 1;
            Main.gui.changeColorTheme(Main.colorTheme);
            try {this.updateList(false);} catch (IOException ex) {ex.printStackTrace();}
        } else if (e.getSource()== cream) {
            Main.colorTheme = 0;
            Main.gui.changeColorTheme(Main.colorTheme);
            try {this.updateList(false);} catch (IOException ex) {ex.printStackTrace();}
        } else if (e.getSource()==createButton) {
            if (inDialog==null) {
                inDialog = new InputDialog();
                inDialog.setVisible(true);
                inDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                inDialog.colorTheme(Main.colorTheme);
            }
        } else if (e.getSource()==importButton) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int response = fileChooser.showOpenDialog(null);
            if (response == JFileChooser.APPROVE_OPTION) {
                File chosenFile = fileChooser.getSelectedFile();
                String chosenFilePath = chosenFile.getAbsolutePath();
                String[] fileFolderPath = chosenFilePath.split("/");
                if (fileFolderPath[fileFolderPath.length-2].equals("DataFileFolder") || Arrays.asList(new File("src/ItsAPackage/DataFileFolder").list()).contains(fileFolderPath[fileFolderPath.length-1])) {
                    JOptionPane.showMessageDialog(null, "Selected file is already in the system");
                } else {
                    String[] options = {"proceed", "cancel"};
                    int proceedResponse = JOptionPane.showOptionDialog(null,
                            "Importing files that are not created using this application is not recommended! \nFile format mismatch will result in malfunction of application", "Proceed?",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            options,
                            0);
                    if (proceedResponse==0) {
                        new File("src/ItsAPackage/DataFileFolder/" + fileFolderPath[fileFolderPath.length-1]).mkdir();
                        try {
                            for (int i=0; i<chosenFile.listFiles().length; i++) {
                                Files.copy(Paths.get(String.valueOf(chosenFile.listFiles()[i])),
                                        Paths.get("src/ItsAPackage/DataFileFolder/" + fileFolderPath[fileFolderPath.length-1] + "/" + chosenFile.list()[i]),
                                        StandardCopyOption.REPLACE_EXISTING);
                            }
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        Main.file = new File("src/ItsAPackage/DataFileFolder/" + fileFolderPath[fileFolderPath.length-1] + "/" + fileFolderPath[fileFolderPath.length-1] + ".txt");
                        try {
                            Main.readFile();
                            Main.gui.winSettings.updateList(false);
                            Main.gui.winSettings.validate();
                        } catch (IOException ex) {ex.printStackTrace();}
                    }
                }
            }
        }
    }

    public void scrollToTop() {
        JScrollBar scrollBar = mainScroll.getVerticalScrollBar();
        scrollBar.setValue(scrollBar.getMinimum());
    }

    public void updateList(boolean removed) throws IOException {
        list.removeAll();
        filePaths = dataFileFolder.list();
        if (filePaths.length==0) {
            new File("src/ItsAPackage/DataFileFolder/Default").mkdir();
            Main.file = new File("src/ItsAPackage/DataFileFolder/Default/Default.txt");
            Main.checkFile();
            Main.readFile();
            JOptionPane.showMessageDialog(this, "Auto created the file \"Default.txt\" and set it to default data file", "No existing data file", JOptionPane.PLAIN_MESSAGE);
            filePaths = dataFileFolder.list();
        } else if (removed) {
            Main.file = new File("src/ItsAPackage/DataFileFolder/" + filePaths[0] +"/"+ filePaths[0] +".txt");
            Main.readFile();
        }
        boolean screwDS_Store = false;
        for (int i=0; i<filePaths.length; i++) {
            String current = filePaths[i];
            if (current.equals(".DS_Store")) {
                Files.deleteIfExists(Paths.get("src/ItsAPackage/DataFileFolder/.DS_Store"));
                screwDS_Store = true;
                continue;
            }
            String[] path = String.valueOf(Main.file).split("/");
            boolean isSameFile = current.equals(path[path.length-2]);
            dataListPanel temp = new dataListPanel(current, isSameFile);
            temp.colorTheme(Main.colorTheme);
            list.add(temp);
        }
        if (screwDS_Store) {
            list.setLayout(new GridLayout(filePaths.length-1, 1));
        } else
            list.setLayout(new GridLayout(filePaths.length, 1));
        list.validate();
    }

    public void colorTheme(int id) {
        if (this.inDialog!=null)
            this.inDialog.colorTheme(id);
        if (id==0) {
            this.setBackground(cmainBackColor);
            title.setForeground(ctitleForeColor);
            westMargin.setBackground(cmainBackColor);
            eastMargin.setBackground(cmainBackColor);
            centerPanel.setBackground(cmainBackColor);
            dataHandle.setForeground(clabelForeColor);
            upload.setBackground(cbuttonBackColor);
            upload.setForeground(clabelForeColor);
            reload.setBackground(cbuttonBackColor);
            reload.setForeground(clabelForeColor);
            CTheme.setForeground(clabelForeColor);
            dark.setForeground(clabelForeColor);
            cream.setForeground(clabelForeColor);
            chooseData.setForeground(clabelForeColor);
            createButton.setBackground(cbuttonBackColor);
            createButton.setForeground(clabelForeColor);
            importButton.setBackground(cbuttonBackColor);
            importButton.setForeground(clabelForeColor);
        } else {
            this.setBackground(dmainBackColor);
            title.setForeground(dtitleForeColor);
            westMargin.setBackground(dmainBackColor);
            eastMargin.setBackground(dmainBackColor);
            centerPanel.setBackground(dmainBackColor);
            dataHandle.setForeground(dlabelForeColor);
            upload.setBackground(dbuttonBackColor);
            upload.setForeground(dlabelForeColor);
            reload.setBackground(dbuttonBackColor);
            reload.setForeground(dlabelForeColor);
            CTheme.setForeground(dlabelForeColor);
            dark.setForeground(dlabelForeColor);
            cream.setForeground(dlabelForeColor);
            chooseData.setForeground(dlabelForeColor);
            createButton.setBackground(dbuttonBackColor);
            createButton.setForeground(dlabelForeColor);
            importButton.setBackground(dbuttonBackColor);
            importButton.setForeground(dlabelForeColor);
        }
    }
}

class dataListPanel extends JPanel implements ActionListener{
    JLabel name;
    JRadioButton selected;
    JButton delete;
    String fileName;
    public dataListPanel(String fileName, boolean isSelected) {
        this.fileName = fileName;
        this.setLayout(new GridBagLayout());
        this.setPreferredSize(new Dimension(540, 60));
        name = new JLabel(fileName);
        name.setPreferredSize(new Dimension(300, 60));
        name.setFont(new Font("Georgia", Font.PLAIN, 18));
        selected = new JRadioButton();
        selected.setPreferredSize(new Dimension(80, 60));
        Main.gui.winSettings.fileBG.add(selected);
        selected.setSelected(isSelected);
        selected.addActionListener(this);
        delete = new JButton("delete");
        delete.setPreferredSize(new Dimension(100, 40));
        delete.setBorderPainted(false);
        delete.setOpaque(true);
        delete.setFont(new Font("Georgia", Font.PLAIN, 18));
        delete.addActionListener(this);
        this.add(name);
        this.add(selected);
        this.add(delete);
    }

    public void colorTheme(int id) {
        if (id==0) {
            this.setBackground(clistBackColor);
            name.setForeground(clabelForeColor);
            delete.setBackground(cbuttonBackColor);
            delete.setForeground(clabelForeColor);
        } else {
            this.setBackground(dlistBackColor);
            name.setForeground(dlabelForeColor);
            delete.setBackground(dbuttonBackColor);
            delete.setForeground(dlabelForeColor);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==selected) {
            try {
                Main.gui.writeToFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            Main.file = new File("src/ItsAPackage/DataFileFolder/" + fileName + "/" + fileName+".txt");
            try {
                Main.readFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource()==delete) {
            int result = JOptionPane.showConfirmDialog(null, "you CANNOT undo this action!", "Delete file?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (result == JOptionPane.YES_OPTION) {
                try {
                    File targetFolder = new File("src/ItsAPackage/DataFileFolder/" + fileName);
                    for (File subfile : targetFolder.listFiles())
                        subfile.delete();
                    targetFolder.delete();
                    Main.gui.winSettings.updateList(true);
                    Main.gui.winSettings.validate();
                } catch(NoSuchFileException exception) {
                    System.out.println("File not found");
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
    }
}

class InputDialog extends JDialog implements ActionListener{
    private JTextField input;
    private JButton confirm;
    public InputDialog() {
        this.setTitle("Set File Name");
        this.setSize(new Dimension(400, 80));
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Main.gui.winSettings.inDialog = null;
            }

            @Override
            public void windowClosed(WindowEvent e) {
                Main.gui.winSettings.inDialog = null;
            }
        });

        input = new JTextField("Enter desired data file name here");
        input.setPreferredSize(new Dimension(275, 50));
        input.setBorder(null);
        input.addActionListener(this);

        confirm = new JButton("Confirm");
        confirm.setPreferredSize(new Dimension(100, 50));
        confirm.setFont(new Font("Georgia", Font.PLAIN, 14));
        confirm.setBorderPainted(false);
        confirm.setOpaque(true);
        confirm.addActionListener(this);

        this.add(input, BorderLayout.CENTER);
        this.add(confirm, BorderLayout.EAST);
    }
    public void colorTheme(int id) {
        if (id==0) {
            this.setBackground(cmainBackColor);
            input.setBackground(ctextFieldBackColor);
            input.setForeground(ctextFieldForeColor);
            input.setCaretColor(ctextFieldCaretColor);
            confirm.setBackground(cbuttonBackColor);
            confirm.setForeground(clabelForeColor);
        } else {
            this.setBackground(dmainBackColor);
            input.setBackground(dtextFieldBackColor);
            input.setForeground(dtextFieldForeColor);
            input.setCaretColor(dtextFieldCaretColor);
            confirm.setBackground(dbuttonBackColor);
            confirm.setForeground(dlabelForeColor);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==input || e.getSource()==confirm) {
            String fileName= input.getText();
            fileName = fileName.replace("/", "");
            if (fileName.contains("."))
                fileName = fileName.substring(0, fileName.indexOf("."));
            File newFile = new File("src/ItsAPackage/DataFileFolder/" + fileName);
            if (!newFile.mkdir()) {
                input.setText("The file \"" + fileName + "\" already exists");
            } else {
                Main.file = new File("src/ItsAPackage/DataFileFolder/" + fileName + "/" + fileName + ".txt");
                try {
                    Main.gui.winSettings.updateList(false);
                    Main.gui.winSettings.validate();
                    Main.checkFile();
                    Main.readFile();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                Main.gui.winSettings.inDialog.dispose();
            }
        }
    }
}
