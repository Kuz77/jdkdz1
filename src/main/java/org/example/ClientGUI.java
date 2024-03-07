package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileWriter;
import java.io.IOException;

public class ClientGUI extends JFrame {
    private JTextArea chatTextArea;
    private JTextField messageTextField;
    private JTextField usernameTextField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton sendButton;
    private ServerControlPanel serverWindow;

    public ClientGUI(ServerControlPanel serverWindow) {
        this.serverWindow = serverWindow;

        JPanel panel = new JPanel(new BorderLayout());

        chatTextArea = new JTextArea(10, 30);
        JScrollPane scrollPane = new JScrollPane(chatTextArea);
        chatTextArea.setEditable(false);

        messageTextField = new JTextField(20);

        usernameTextField = new JTextField(10);
        passwordField = new JPasswordField(10);

        loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameTextField.getText();
                String password = new String(passwordField.getPassword());
                serverWindow.log("User logged in: " + username);
                // Здесь можно добавить код для отправки логина и пароля на сервер
            }
        });

        sendButton = new JButton("Send");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = messageTextField.getText();
                sendMessageToServer(message);
                messageTextField.setText("");
            }
        });

        JPanel loginPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        loginPanel.add(new JLabel("Username:"));
        loginPanel.add(usernameTextField);
        loginPanel.add(new JLabel("Password:"));
        loginPanel.add(passwordField);
        loginPanel.add(loginButton);

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(messageTextField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.LINE_END);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(loginPanel, BorderLayout.PAGE_START);
        panel.add(inputPanel, BorderLayout.PAGE_END);

        this.add(panel);
        this.setTitle("Client Panel");
        this.setSize(400, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);


        messageTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String message = messageTextField.getText();
                    sendMessageToServer(message);
                    messageTextField.setText("");
                }
            }
        });
    }
    private void saveChatHistory(String message) {
        try {
            FileWriter writer = new FileWriter("chat_history.txt", true);
            writer.write(message + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessageToServer(String message) {
        String username = usernameTextField.getText();
        String fullMessage = "From " + username + ": " + message;
        serverWindow.log(fullMessage);
        saveChatHistory(fullMessage);
        // Пример: serverWindow.receiveMessageFromClient(username, message);





    }
}