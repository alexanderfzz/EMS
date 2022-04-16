package ItsAPackage;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.regex.Pattern;

import static ItsAPackage.Palette.*;

public class HomePanel extends JPanel implements ActionListener {
    private Image logoImage = new ImageIcon("src/ItsAPackage/EMS_Icon.png").getImage().getScaledInstance(128, 128,  java.awt.Image.SCALE_SMOOTH);
    private ImageIcon icon = new ImageIcon(logoImage);
    private JLabel title;
    private JPanel northMargin, guideButtonPanel, guideWestMargin, guideEastMargin, guideSouthMargin;
    private JButton guide;
    public EmailDialog emailDialog;

    public HomePanel() {
        this.setSize(new Dimension(700, 600));

        northMargin = new JPanel();
        northMargin.setPreferredSize(new Dimension(600, 100));

        title = new JLabel();
        title.setPreferredSize(new Dimension(600, 280));
        title.setText("<html><body>Employee Management<br>System</body></html>");
        title.setIcon(icon);
        title.setIconTextGap(20);
        title.setFont(new Font("Rockwell", Font.BOLD, 60));

        guideButtonPanel = new JPanel();
        guideButtonPanel.setPreferredSize(new Dimension(600, 120));
        guideButtonPanel.setLayout(new BorderLayout());

        guideWestMargin = new JPanel();
        guideWestMargin.setPreferredSize(new Dimension(230, 90));
        guideEastMargin = new JPanel();
        guideEastMargin.setPreferredSize(new Dimension(230, 90));
        guideSouthMargin = new JPanel();
        guideSouthMargin.setPreferredSize(new Dimension(600, 60));

        guide = new JButton("Guide");
        guide.setOpaque(true);
        guide.setBorderPainted(false);
        guide.setPreferredSize(new Dimension(100, 60));
        guide.setFont(new Font("Georgia", Font.PLAIN, 20));
        guide.addActionListener(this);


        guideButtonPanel.add(guideWestMargin, BorderLayout.WEST);
        guideButtonPanel.add(guideEastMargin, BorderLayout.EAST);
        guideButtonPanel.add(guideSouthMargin, BorderLayout.SOUTH);
        guideButtonPanel.add(guide, BorderLayout.CENTER);

        this.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 50));
        this.add(northMargin, BorderLayout.NORTH);
        this.add(title, BorderLayout.CENTER);
        this.add(guideButtonPanel, BorderLayout.SOUTH);
    }

    public void colorTheme(int id) {
        if (emailDialog!=null)
            emailDialog.colorTheme(id);
        if (id==0) {
            this.setBackground(cmainBackColor);
            title.setForeground(ctitleForeColor);
            northMargin.setBackground(cmainBackColor);
            guideWestMargin.setBackground(cmainBackColor);
            guideEastMargin.setBackground(cmainBackColor);
            guideSouthMargin.setBackground(cmainBackColor);
            guide.setBackground(cbuttonBackColor);
            guide.setForeground(clabelForeColor);
        } else {
            this.setBackground(dmainBackColor);
            title.setForeground(dtitleForeColor);
            northMargin.setBackground(dmainBackColor
            );
            guideWestMargin.setBackground(dmainBackColor);
            guideEastMargin.setBackground(dmainBackColor);
            guideSouthMargin.setBackground(dmainBackColor);
            guide.setBackground(dbuttonBackColor);
            guide.setForeground(dlabelForeColor);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==guide) {
            if (emailDialog==null) {
                emailDialog = new EmailDialog();
                emailDialog.setVisible(true);
                emailDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                emailDialog.colorTheme(Main.colorTheme);
            }
        }
    }
}

class EmailDialog extends JDialog implements ActionListener{
    private JTextField input;
    private JButton confirm;
    public EmailDialog() {
        this.setTitle("Enter your email");
        this.setSize(new Dimension(550, 80));
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {Main.gui.winHome.emailDialog = null;}

            @Override
            public void windowClosed(WindowEvent e) {Main.gui.winHome.emailDialog = null;}
        });

        input = new JTextField("Enter your email here to receive the guide to this application");
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

    private boolean emailIsValid(String address) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        if (address == null)
            return false;
        return pat.matcher(address).matches();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==input || e.getSource()==confirm) {
            String email= input.getText();
            if (emailIsValid(email)) {
                sendEmail(email);
                Main.gui.winHome.emailDialog.dispose();
            } else {
                input.setText("Please enter a valid email address");
            }
        }
    }

    private void sendEmail(String address) {
        final String USERNAME = "hhacketthon@gmail.com";
        final String PASSWORD = "dgGYgf3627";
        String fromEmail = "hhacketthon@gmail.com";

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(address)
            );
            message.setSubject("EMS Guide");
            Multipart emailContent =  new MimeMultipart();

            MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setText("You can find the guide to the EMS below as a pdf attachment");

            MimeBodyPart pdfAttachment = new MimeBodyPart();
            pdfAttachment.attachFile(new File("src/ItsAPackage/\"Guide to EMS\".pdf"));

            emailContent.addBodyPart(textBodyPart);
            emailContent.addBodyPart(pdfAttachment);

            message.setContent(emailContent);

            Transport.send(message);

        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }
    }
}



