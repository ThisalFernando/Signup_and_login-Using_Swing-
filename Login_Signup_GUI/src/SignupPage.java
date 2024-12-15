import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SignupPage {
    public SignupPage(){
        // Create the frame
        JFrame frame = new JFrame("Dirac Online Music | Sign Up");
        frame.setSize(1000, 900);
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

        //Add Image to the right side
        ImageIcon sideImage = new ImageIcon("Images/Image02.jpg");
        JLabel imageLabel = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(sideImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        // Position the image on the left
        imageLabel.setBounds(490, 0, 600, 900);
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

        // Add "SIGN UP" label
        JLabel titleLabel = new JLabel("SIGN UP");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 35));
        titleLabel.setForeground(new Color(178, 144, 5));
        titleLabel.setBounds(165, 50, 150, 40);
        panel.add(titleLabel);

        // Add subtitle
        JLabel subtitleLabel = new JLabel("Enter your details required");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitleLabel.setForeground(Color.BLACK);
        subtitleLabel.setBounds(150, 100, 300, 20);
        panel.add(subtitleLabel);

        // Add username label
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        usernameLabel.setForeground(Color.BLACK);
        usernameLabel.setBounds(40, 150, 300, 20);
        panel.add(usernameLabel);

        // Username text field
        PlaceholderTextField usernameField = new PlaceholderTextField("Create Username");
        usernameField.setFont(new Font("Arial", Font.PLAIN, 16));
        usernameField.setBounds(40, 180, 400, 40);
        usernameField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(usernameField);

        // Gender selection label
        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setFont(new Font("Arial", Font.BOLD, 16));
        genderLabel.setForeground(Color.BLACK);
        genderLabel.setBounds(40, 250, 80, 30);
        panel.add(genderLabel);

        // Male radio button
        JRadioButton maleButton = new JRadioButton("Male");
        maleButton.setFont(new Font("Arial", Font.PLAIN, 14));
        maleButton.setForeground(Color.BLACK);
        maleButton.setBounds(40, 280, 80, 30);
        maleButton.setOpaque(false); // Transparent background
        panel.add(maleButton);

        // Female radio button
        JRadioButton femaleButton = new JRadioButton("Female");
        femaleButton.setFont(new Font("Arial", Font.PLAIN, 16));
        femaleButton.setForeground(Color.BLACK);
        femaleButton.setBounds(100, 280, 100, 30);
        femaleButton.setOpaque(false); // Transparent background
        panel.add(femaleButton);

        // Add radio buttons to a ButtonGroup to enforce single selection
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleButton);
        genderGroup.add(femaleButton);

        // Pre-select male option
        maleButton.setSelected(true);

        // DOB Label
        JLabel dateLabel = new JLabel("Select your birth date:");
        dateLabel.setFont(new Font("Arial", Font.BOLD, 16));
        dateLabel.setBounds(40, 320, 200, 30);
        panel.add(dateLabel);

        // Create JComboBoxes for day, month, and year
        String[] days = new String[31];
        for (int i = 1; i <= 31; i++) days[i - 1] = String.valueOf(i);

        String[] months = {
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
        };

        String[] years = new String[100];
        int currentYear = java.time.Year.now().getValue();
        for (int i = 0; i < 100; i++) years[i] = String.valueOf(currentYear - i);

        JComboBox<String> dayComboBox = new JComboBox<>(days);
        JComboBox<String> monthComboBox = new JComboBox<>(months);
        JComboBox<String> yearComboBox = new JComboBox<>(years);

        dayComboBox.setBounds(40, 350, 50, 30);
        monthComboBox.setBounds(120, 350, 100, 30);
        yearComboBox.setBounds(250, 350, 70, 30);

        panel.add(dayComboBox);
        panel.add(monthComboBox);
        panel.add(yearComboBox);

        // Add email label
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Arial", Font.BOLD, 16));
        emailLabel.setForeground(Color.BLACK);
        emailLabel.setBounds(40, 410, 300, 20);
        panel.add(emailLabel);

        // Email field
        PlaceholderTextField emailField = new PlaceholderTextField("Enter your email");
        emailField.setFont(new Font("Arial", Font.PLAIN, 16));
        emailField.setBounds(40, 440, 400, 40);
        emailField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(emailField);

        // Add password label
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 16));
        passwordLabel.setForeground(Color.BLACK);
        passwordLabel.setBounds(40, 500, 300, 20);
        panel.add(passwordLabel);

        // Password field
        PlaceholderPasswordField passwordField = new PlaceholderPasswordField("Create a strong Password");
        passwordField.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordField.setBounds(40, 530, 360, 40);
        passwordField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(passwordField);

        // Create the toggle button
        JToggleButton toggleButton = new JToggleButton(new ImageIcon("Images/eye_closed.png"));
        // Resize the image to smaller dimensions
        ImageIcon closedIcon = new ImageIcon(new ImageIcon("Images/eye_closed.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        ImageIcon openIcon = new ImageIcon(new ImageIcon("Images/eye_opened.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        toggleButton.setIcon(closedIcon);
        toggleButton.setBounds(400, 530, 40, 40);
        toggleButton.setBackground(Color.white);
        toggleButton.setContentAreaFilled(true);
        toggleButton.setBorder(BorderFactory.createEmptyBorder());
        panel.add(toggleButton);

        // Sign up button
        JButton signupButton= new JButton("Sign Up");
        signupButton.setFont(new Font("Arial", Font.BOLD, 18));
        signupButton.setBounds(40, 630, 400, 40);
        signupButton.setBackground(new Color(255, 204, 0));
        signupButton.setForeground(Color.BLACK);
        signupButton.setFocusPainted(false);
        panel.add(signupButton);

        // reset button
        JButton resetButton= new JButton("Clear Details");
        resetButton.setFont(new Font("Arial", Font.BOLD, 18));
        resetButton.setBounds(40, 680, 400, 40);
        resetButton.setBackground(Color.red);
        resetButton.setForeground(Color.white);
        resetButton.setFocusPainted(false);
        panel.add(resetButton);

        // Sign Up text and button
        JLabel noAccountLabel = new JLabel("If you already have an account,");
        noAccountLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        noAccountLabel.setForeground(Color.BLACK);
        noAccountLabel.setBounds(130, 750, 250, 30);
        panel.add(noAccountLabel);

        JButton loginButton = new JButton("Log In");
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.setBounds(40, 780, 400, 40);
        loginButton.setBackground(Color.BLACK);
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        panel.add(loginButton);

        // Adding action listeners
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String gender = maleButton.isSelected() ? "Male" : "Female";
                String dob = yearComboBox.getSelectedItem() + "-" +
                        (monthComboBox.getSelectedIndex() + 1) + "-" +
                        dayComboBox.getSelectedItem();
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());
                if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill all the details!.", "Oops!", JOptionPane.ERROR_MESSAGE);
                } else {
                    try (Connection con = DatabaseUtil.getConnection()) {
                        String query = "INSERT INTO users (username, gender, dob, email, password) VALUES (?, ?, ?, ?, ?)";
                        PreparedStatement stmt = con.prepareStatement(query);
                        stmt.setString(1, username);
                        stmt.setString(2, gender);
                        stmt.setString(3, dob);
                        stmt.setString(4, email);
                        stmt.setString(5, password);

                        stmt.executeUpdate();
                        JOptionPane.showMessageDialog(frame, "Signed Up Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    }catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }

                }
            }
        });

        // Add action listener for the reset button
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());
                if (username.isEmpty() && email.isEmpty() && password.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "No any details you have filled!.", "Oops!", JOptionPane.ERROR_MESSAGE);
                }
                else{
                    // Clear text fields
                    usernameField.setText("");
                    emailField.setText("");
                    passwordField.setText("");

                    // Reset dob
                    dayComboBox.setSelectedIndex(0);
                    monthComboBox.setSelectedIndex(0);
                    yearComboBox.setSelectedIndex(0);

                    // Deselect gender
                    maleButton.setSelected(true);
                    femaleButton.setSelected(false);

                    // Reset the toggle button to closed icon and password field to masked
                    toggleButton.setSelected(false);
                    passwordField.setEchoChar('\u2022');
                    toggleButton.setIcon(closedIcon);
                }
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Redirecting to Log in page...", "Log In", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose(); // Close the login page
                new LoginPage(); // Open the Sign-up page
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

}
