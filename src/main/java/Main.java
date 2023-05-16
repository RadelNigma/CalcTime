import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    public static void printResult(long sec) {
        int hour = (int) (Math.abs(sec) / 3600);
        int minute = (int) ((Math.abs(sec)  % 3600) / 60);
        int second = (int) (Math.abs(sec)  % 60);
        String formatTime =
                second != 0
                        ? String.format("%02d:%02d:%02d", hour, minute, second)
                        : String.format("%02d:%02d", hour, minute);

        if (sec >= 0) {
            System.out.println("\tРезультат: " + formatTime);
        } else {
            System.out.println("\tРезультат: -" + formatTime);
        }
    }

    public static String getFormattedLine (String line) {
        char[] chars = line.toCharArray();
        StringBuilder token = new StringBuilder();

        for (char ch : chars) {
            if (Pattern.matches("[\\d.,:;]", String.valueOf(ch))) {
                if (Pattern.matches("[.,:;]", String.valueOf(ch))){
                    ch = '.';
                }
                token.append(ch);
            } else if (Pattern.matches("[+\\-*/]", String.valueOf(ch))){
                token.append(" ").append(ch).append(" ");
            }
        }
        return String.valueOf(token);
    }

    public static void main(String[] args) {
        System.out.println("Введите выражение например:\n" +
                "1.25+3.45-5.55*2/3\n" +
                "Где целая часть - часы, часть после запятой - минуты\n" +
                "В качестве разделителя допускается использовать '.' ',' ':' ';'\n" +
                "Так же результат можно умножать и делить на целое число\n");
        String token = "";
        while (!token.equals("exit")) {
            System.out.print("Введите выражение:\t");
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();
            String[] list = getFormattedLine(line).split(" ");
            LocalTime localTime;
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
                printResult(sec);
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}