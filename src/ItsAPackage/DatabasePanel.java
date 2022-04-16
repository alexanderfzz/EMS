package ItsAPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static ItsAPackage.Palette.*;

public class DatabasePanel extends JPanel implements ActionListener {
    private JLabel title, message;
    private JPanel westMargin, eastMargin, bottomMargin, centerPanel, searchHeader, list;
    private JTextField inEmpNum;
    private JButton search;
    private JScrollPane scrollList;

    public DatabasePanel() {
        this.setSize(new Dimension(700, 600));
        this.setLayout(new BorderLayout());

        title = new JLabel();
        title.setPreferredSize(new Dimension(350, 120));
        title.setText("    Database");
        title.setFont(new Font("Trebuchet MS", Font.BOLD, 48));

        westMargin = new JPanel();
        westMargin.setPreferredSize(new Dimension(80, 430));

        eastMargin = new JPanel();
        eastMargin.setPreferredSize(new Dimension(80, 430));

        bottomMargin = new JPanel();
        bottomMargin.setPreferredSize(new Dimension(700, 50));

        centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());

        searchHeader = new JPanel();
        searchHeader.setLayout(new FlowLayout(0, 10, 0));
        searchHeader.setPreferredSize(new Dimension(540, 80));

        inEmpNum = new JTextField("Insert employee number here");
        inEmpNum.setFont(new Font("", Font.PLAIN, 16));
        inEmpNum.setPreferredSize(new Dimension(385, 50));
        inEmpNum.addActionListener(this);

        search = new JButton("search");
        search.setFont(new Font("Georgia", Font.PLAIN, 18));
        search.setBorderPainted(false);
        search.setOpaque(true);
        search.setPreferredSize(new Dimension(100, 40));
        search.addActionListener(this);

        searchHeader.add(inEmpNum);
        searchHeader.add(search);

        message = new JLabel("No Employee data in database", SwingConstants.CENTER);
        message.setFont(new Font("Georgia", Font.BOLD, 30));
        message.setOpaque(true);

        list = new JPanel();

        scrollList = new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollList.setPreferredSize(new Dimension( 540,380));
        scrollList.getVerticalScrollBar().setUnitIncrement(10);

        centerPanel.add(searchHeader, BorderLayout.NORTH);
        centerPanel.add(scrollList, BorderLayout.CENTER);

        this.add(title, BorderLayout.NORTH);
        this.add(westMargin, BorderLayout.WEST);
        this.add(eastMargin, BorderLayout.EAST);
        this.add(bottomMargin,BorderLayout.SOUTH);
        this.add(centerPanel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==search || e.getSource()==inEmpNum) {
            try {
                EmployeeInfo profile = Main.ht.getFromTable(Integer.parseInt(inEmpNum.getText()));
                if (profile!=null)
                    Main.gui.showProfile(profile);
                else
                    inEmpNum.setText("Employee not found");
            } catch (NumberFormatException exception) {
                inEmpNum.setText("Invalid employee number");
            }
        }
    }

    public void updateList() {
        list.removeAll();
        list.setLayout(new GridLayout(Main.N, 1));
        EmployeeInfo[] employees = Main.ht.returnAll();
        if (Main.N==0) {
            list.add(message);
        } else {
            for (int i=0; i<Main.N; i++) {
                EmployeeInfo current = employees[i];
                EmpListPanel temp = new EmpListPanel(current);
                temp.colorTheme(Main.colorTheme);
                list.add(temp);
            }
        }
    }

    public void colorTheme(int id) {
        if (id == 0) {
            this.setBackground(cmainBackColor);
            title.setForeground(ctitleForeColor);
            westMargin.setBackground(cmainBackColor);
            eastMargin.setBackground(cmainBackColor);
            bottomMargin.setBackground(cmainBackColor);
            searchHeader.setBackground(cmainBackColor);
            inEmpNum.setForeground(ctextFieldForeColor);
            inEmpNum.setCaretColor(ctextFieldCaretColor);
            inEmpNum.setBackground(ctextFieldBackColor);
            search.setBackground(cbuttonBackColor);
            search.setForeground(clabelForeColor);
            message.setBackground(clistBackColor);
            message.setForeground(clabelForeColor);
        } else {
            this.setBackground(dmainBackColor);
            title.setForeground(dtitleForeColor);
            westMargin.setBackground(dmainBackColor);
            eastMargin.setBackground(dmainBackColor);
            bottomMargin.setBackground(dmainBackColor);
            searchHeader.setBackground(dmainBackColor);
            inEmpNum.setForeground(dtextFieldForeColor);
            inEmpNum.setCaretColor(dtextFieldCaretColor);
            inEmpNum.setBackground(dtextFieldBackColor);
            search.setBackground(dbuttonBackColor);
            search.setForeground(dlabelForeColor);
            message.setBackground(dlistBackColor);
            message.setForeground(dlabelForeColor);
        }
    }
}
class EmpListPanel extends JPanel {
    private JLabel listType, listNum, listName;
    private JButton getProfile;
    public EmpListPanel(EmployeeInfo data) {
        this.setLayout(new GridBagLayout());
        this.setPreferredSize(new Dimension(540, 60));
        listType = new JLabel(data instanceof FTE ? "FTE" : "PTE");
        listNum = new JLabel(String.valueOf(data.getEmpNumber()));
        listName = new JLabel(data.getFirstName()+" "+data.getLastName());
        listType.setPreferredSize(new Dimension(100, 60));
        listNum.setPreferredSize(new Dimension(70, 60));
        listName.setPreferredSize(new Dimension(200, 60));
        getProfile = new JButton("get profile");
        getProfile.setFont(new Font("Georgia", Font.PLAIN, 14));
        getProfile.setBorderPainted(false);
        getProfile.setOpaque(true);
        getProfile.setPreferredSize(new Dimension(110, 40));

        getProfile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.gui.showProfile(data);
            }
        });
        this.add(listNum);
        this.add(listType);
        this.add(listName);
        this.add(getProfile);
    }

    public void colorTheme(int id) {
        if (id==0) {
            this.setBackground(clistBackColor);
            listType.setForeground(clabelForeColor);
            listNum.setForeground(clabelForeColor);
            listName.setForeground(clabelForeColor);
            getProfile.setBackground(cbuttonBackColor);
            getProfile.setForeground(clabelForeColor);
        } else {
            this.setBackground(dlistBackColor);
            listType.setForeground(dlabelForeColor);
            listNum.setForeground(dlabelForeColor);
            listName.setForeground(dlabelForeColor);
            getProfile.setBackground(dbuttonBackColor);
            getProfile.setForeground(dlabelForeColor);
        }
    }
}
