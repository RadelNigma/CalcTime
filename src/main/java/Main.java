import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        String token = "";
        while (!token.equals("exit")) {
            System.out.println("Введи выражение в вид");
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();
            List<String> list = Arrays.stream(line.split(" ")).toList();
            LocalTime localTime = null;
            String operator = "";
            long sec = 0;
            try {
                for (String s : list) {
                    token = s;
                    if (token.matches("\\d+\\.\\d{2}")) {
                        localTime = LocalTime.parse(token, DateTimeFormatter.ofPattern("H.m"));
                        if (operator.contains("+")) {
                            sec += localTime.toSecondOfDay();
                        } else if (operator.contains("-")) {
                            sec -= localTime.toSecondOfDay();
                        } else {
                            sec = localTime.toSecondOfDay();
                        }
                    } else if (token.matches("\\d")) {
                        if (operator.contains("*")) {
                            sec *= Long.parseLong(token);
                        } else if (operator.contains("/")) {
                            sec /= Long.parseLong(token);
                        }
                    } else {
                        operator = token;
                    }
                }
                System.out.println("Результат: " + LocalTime.ofSecondOfDay(sec));
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}