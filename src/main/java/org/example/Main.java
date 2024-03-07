package org.example;

public class Main {
    public static void main(String[] args) {
        ServerControlPanel serverWindow = new ServerControlPanel();
        new ClientGUI(serverWindow);
    }
}
