package oop.lesson_05;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public final class CalcClient {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            try (Socket socket = new Socket("localhost", 1234)) {
                DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

                System.out.println("Введите математическое выражение или \"end\" для завершения работы");

                while (true) {
                    System.out.println("введите выражение:");

                    String expression = scanner.nextLine();
                    outputStream.writeUTF(expression);

                    if ("end".equals(expression))
                        break;

                    String responce = inputStream.readUTF();
                    System.out.println("Ответ сервера: " + responce);
                }
            } catch (UnknownHostException e) {
                System.out.println("Неизвестный хост при запуске клиента Calculator:");
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("I/O ошибка запуска клиента Calculator:");
                e.printStackTrace();
            }
        }

        System.out.println("Клиент Calculator завершился нормально.");
    }
}
