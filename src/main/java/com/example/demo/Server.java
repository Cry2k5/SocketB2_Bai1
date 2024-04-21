package com.example.demo;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("Server started. Waiting for clients...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket);

                // Tạo một thread mới để xử lý kết nối đến từ client
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clientHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ClientHandler extends Thread {
    private Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            for (int i = 1; i <= 1000; i++) {
                out.println(i);
                Thread.sleep(1000); // Chờ 1 giây trước khi gửi số tiếp theo
            }

            out.close();
            clientSocket.close();
            System.out.println("Client disconnected: " + clientSocket);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}