package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.OutputStream;
import java.io.PrintStream;

public class ServerControlPanel extends JFrame {
    private boolean isServerWorking = false;
    private JTextArea logTextArea;

    public ServerControlPanel() {
        JButton startButton = new JButton("Start Server");
        JButton stopButton = new JButton("Stop Server");
        logTextArea = new JTextArea(10, 30);
        JScrollPane scrollPane = new JScrollPane(logTextArea);

        startButton.setPreferredSize(new Dimension(150, 50));
        stopButton.setPreferredSize(new Dimension(150, 50));

        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (isServerWorking) {
                    log("Server is already running");
                } else {
                    isServerWorking = true;
                    log("Server started");
                }
            }
        });

        stopButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!isServerWorking) {
                    log("Server is not running");
                } else {
                    isServerWorking = false;
                    log("Server stopped");
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startButton);
        buttonPanel.add(stopButton);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(buttonPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        this.add(panel);
        this.setTitle("Server Control Panel");
        this.setSize(400, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Центрирование окна
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

        this.setVisible(true);

        // Перенаправление вывода в JTextArea
        PrintStream printStream = new PrintStream(new CustomOutputStream(logTextArea));
        System.setOut(printStream);
        System.setErr(printStream);
    }

    void log(String message) {
        logTextArea.append(message + "\n");
    }

    public void receiveClientData(String username, String password, String message) {
        log("Received data from client - Username: " + username + ", Password: " + password + ", Message: " + message);

    }


    private class CustomOutputStream extends OutputStream {
        private JTextArea textArea;

        public CustomOutputStream(JTextArea textArea) {
            this.textArea = textArea;
        }

        @Override
        public void write(int b) {
            textArea.append(String.valueOf((char) b));
        }
    }
}
