import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static java.sql.DriverManager.getConnection;

public class LoginPage {
    public LoginPage() {

        // Create the frame
        JFrame frame = new JFrame("Dirac Online Music | Log in");
        frame.setSize(900, 600);
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

        //Add Image to the left side
        ImageIcon sideImage = new ImageIcon("Images/Image01.jpg");
        JLabel imageLabel = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(sideImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        // Position the image on the left
        imageLabel.setBounds(0, 0, 400, 600);
        panel.add(imageLabel);

        class PlaceholderTextField extends JTextField {
            private String placeholder;

            public PlaceholderTextField(String placeholder) {
                this.placeholder = placeholder;
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getText().isEmpty()) {
                    Graphics2D g2d = (Graphics2D) g.create();
                    g2d.setColor(Color.GRAY);
                    g2d.drawString(placeholder, getInsets().left, getHeight() / 2 + g.getFontMetrics().getAscent() / 2 - 2);
                    g2d.dispose();
                }
            }
        }

        class PlaceholderPasswordField extends JPasswordField {
            private String placeholder;

            public PlaceholderPasswordField(String placeholder) {
                this.placeholder = placeholder;
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getPassword().length == 0) {
                    Graphics2D g2d = (Graphics2D) g.create();
                    g2d.setColor(Color.GRAY);
                    g2d.drawString(placeholder, getInsets().left, getHeight() / 2 + g.getFontMetrics().getAscent() / 2 - 2);
                    g2d.dispose();
                }
            }
        }

        // Add "LOG IN" label
        JLabel titleLabel = new JLabel("LOG IN");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 35));
        titleLabel.setForeground(new Color(178, 144, 5));
        titleLabel.setBounds(585, 50, 150, 40);
        panel.add(titleLabel);

        // Add subtitle
        JLabel subtitleLabel = new JLabel("Enter your Login credentials");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitleLabel.setForeground(Color.BLACK);
        subtitleLabel.setBounds(555, 100, 300, 20);
        panel.add(subtitleLabel);

        // Username label and text field
        PlaceholderTextField usernameField = new PlaceholderTextField("Enter Username");
        usernameField.setFont(new Font("Arial", Font.PLAIN, 16));
        usernameField.setBounds(470, 150, 350, 40);
        usernameField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(usernameField);

        // Password label and password field
        PlaceholderPasswordField passwordField = new PlaceholderPasswordField("Enter Password");
        passwordField.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordField.setBounds(470, 210, 310, 40);
        passwordField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(passwordField);

        // Create the toggle button
        JToggleButton toggleButton = new JToggleButton(new ImageIcon("Images/eye_closed.png"));
        // Resize the image to smaller dimensions
        ImageIcon closedIcon = new ImageIcon(new ImageIcon("Images/eye_closed.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        ImageIcon openIcon = new ImageIcon(new ImageIcon("Images/eye_opened.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        toggleButton.setIcon(closedIcon);
        toggleButton.setBounds(780, 210, 40, 40);
        toggleButton.setBackground(Color.white);
        toggleButton.setContentAreaFilled(true);
        toggleButton.setBorder(BorderFactory.createEmptyBorder());
        panel.add(toggleButton);

        // Remember me checkbox
        JCheckBox rememberMe = new JCheckBox("Remember me");
        rememberMe.setFont(new Font("Arial", Font.PLAIN, 14));
        rememberMe.setForeground(Color.BLACK);
        rememberMe.setBounds(470, 270, 150, 30);
        rememberMe.setOpaque(false);
        panel.add(rememberMe);

        // Log In button
        JButton loginButton= new JButton("Log In");
        loginButton.setFont(new Font("Arial", Font.BOLD, 18));
        loginButton.setBounds(470, 320, 350, 40);
        loginButton.setBackground(new Color(255, 204, 0));
        loginButton.setForeground(Color.BLACK);
        loginButton.setFocusPainted(false);
        panel.add(loginButton);

        // Sign Up text
        JLabel noAccountLabel = new JLabel("If you don't have an account,");
        noAccountLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        noAccountLabel.setForeground(Color.BLACK);
        noAccountLabel.setBounds(555, 390, 250, 30);
        panel.add(noAccountLabel);

        //Sign up Button
        JButton signupButton = new JButton("Sign Up");
        signupButton.setFont(new Font("Arial", Font.BOLD, 16));
        signupButton.setBounds(470, 430, 350, 40);
        signupButton.setBackground(Color.BLACK);
        signupButton.setForeground(Color.WHITE);
        signupButton.setFocusPainted(false);
        panel.add(signupButton);

        // Adding button action listeners
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter username and password!.", "Oops!", JOptionPane.ERROR_MESSAGE);
                } else {
                    try (Connection con = DatabaseUtil.getConnection()) {
                        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
                        PreparedStatement stmt = con.prepareStatement(query);
                        stmt.setString(1, username);
                        stmt.setString(2, password);

                        ResultSet rs = stmt.executeQuery();
                        if (rs.next()) {
                            // Retrieve user details
                            String userGender = rs.getString("gender");
                            String userDob = rs.getString("dob");
                            String userEmail = rs.getString("email");
                            JOptionPane.showMessageDialog(frame, "Welcome, " + username + "!", "Success", JOptionPane.INFORMATION_MESSAGE);
                            frame.dispose();
                            new HomePage(username, userGender, userDob, userEmail); // Redirect to home page
                        } else {
                            JOptionPane.showMessageDialog(frame, "Invalid credentials, Check your username and password!", "Oops!", JOptionPane.ERROR_MESSAGE);
                        }
                    }catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }

                }
            }
        });

        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Redirecting to Sign-up page...", "Sign up", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose(); // Close the login page
                new SignupPage(); // Open the Sign-up page
            }
        });

        //toggleButton listener
        toggleButton.addActionListener(e -> {
            if (toggleButton.isSelected()) {
                passwordField.setEchoChar((char) 0);
                toggleButton.setIcon(openIcon);
            } else {
                passwordField.setEchoChar('\u2022');
                toggleButton.setIcon(closedIcon);
            }
        });

        // Set the frame to be visible
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        // Create and show the login page when the application starts
        new LoginPage();
    }
}
