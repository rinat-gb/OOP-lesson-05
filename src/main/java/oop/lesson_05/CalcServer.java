package oop.lesson_05;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public final class CalcServer {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(1234)) {
            System.out.println("Сервер Calculator запущен, жду соединения с клиентом...");

            Socket socket = serverSocket.accept();
            System.out.println("Клиент Calculator подключился, жду команд от клиента...");

            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

            Calculator calculator = new Calculator();

            while (true) {
                String readString = inputStream.readUTF();

                if ("end".equals(readString))
                    break;

                System.out.println("Запрос от клиента: " + readString);

                String evaluated = calculator.evaluate(readString);

                System.out.println("Вычисленное значение: " + evaluated);
                outputStream.writeUTF(evaluated);
            }
        } catch (IOException e) {
            System.out.println("I/O ошибка запуска сервера Calculator:");
            e.printStackTrace();
        }
        System.out.println("Сервер Calculator завершился нормально.");
    }
}
