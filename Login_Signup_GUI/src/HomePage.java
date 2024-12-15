import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePage {
    public HomePage(String username, String gender, String dob, String email){

        // Create the frame
        JFrame frame = new JFrame("Welcome to Dirac Online Music");
        frame.setSize(900, 750);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a panel with a background image
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon background = new ImageIcon("Images/bgImage.jpg"); // Replace with your image path
                g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), null);
            }
        };
        panel.setLayout(null);
        frame.add(panel);

        //Add logo image
        ImageIcon sideImage = new ImageIcon("Images/logo.png");
        JLabel imageLabel = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(sideImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        // Position the image on the left
        imageLabel.setBounds(0, 0, 900, 200);
        imageLabel.setBackground(Color.BLACK);
        panel.add(imageLabel);

        // Add welcome label
        JLabel titleLabel = new JLabel("Welcome to DIRAC ONLINE MUSIC!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
        titleLabel.setForeground(new Color(178, 144, 5));
        titleLabel.setBounds(100, 250, 800, 40);
        panel.add(titleLabel);

        // Add subtitle
        JLabel subtitleLabel = new JLabel("World's best songs under one roof!");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitleLabel.setForeground(Color.BLACK);
        subtitleLabel.setBounds(320, 300, 300, 20);
        panel.add(subtitleLabel);

        // Create a horizontal separator
        JSeparator separator = new JSeparator();
        separator.setMaximumSize(new Dimension(500, 1)); // Optional: Limit the width and height of the separator
        separator.setBounds(85, 340, 700, 1);
        separator.setForeground(new Color(178, 144, 5));
        panel.add(separator);

        //Add logo image
        ImageIcon userImage = new ImageIcon("Images/user.png");
        JLabel userLabel = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(userImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        // Position the image on the left
        userLabel.setBounds(670, 365, 200, 200);
        userLabel.setBackground(Color.BLACK);
        panel.add(userLabel);

        JLabel welcomeLabel = new JLabel("Welcome, " + username + "!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 25));
        welcomeLabel.setBounds(40, 400, 300, 20);
        panel.add(welcomeLabel);

        JLabel genderLabel = new JLabel("Gender: " + gender);
        genderLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        genderLabel.setBounds(40, 450, 300, 20);
        panel.add(genderLabel);

        JLabel dobLabel = new JLabel("Date of Birth: " + dob);
        dobLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        dobLabel.setBounds(40, 480, 300, 20);
        panel.add(dobLabel);

        JLabel emailLabel = new JLabel("Email: " + email);
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        emailLabel.setBounds(40, 510, 300, 20);
        panel.add(emailLabel);

        // Create a logout button
        JButton logoutButton = new JButton("Log Out");
        logoutButton.setFont(new Font("Arial", Font.BOLD, 18));
        logoutButton.setBounds(40, 630, 800, 40);
        logoutButton.setBackground(Color.red);
        logoutButton.setForeground(Color.white);
        logoutButton.setFocusPainted(false);
        panel.add(logoutButton);

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "You have logged out from the account!...", "Dirac Online Music", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();
                // Redirect to LoginPage
                new LoginPage();
            }
        });

        // Set the frame to be visible
        frame.setVisible(true);
    }

}
