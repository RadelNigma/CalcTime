import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    public static void printResult(long sec) {
        int hour = (int) (Math.abs(sec) / 3600);
        int minute = (int) ((Math.abs(sec) % 3600) / 60);
        int second = (int) (Math.abs(sec) % 60);
        String formatTime =
                second != 0
                        ? String.format("%02d:%02d:%02d", hour, minute, second)
                        : String.format("%02d:%02d", hour, minute);

        if (sec >= 0) {
            System.out.println("\tРезультат: " + formatTime);
        } else if (sec != -1){
            System.out.println("\tРезультат: -" + formatTime);
        }
    }

    public static String getFormattedLine(String line) {
        char[] chars = line.toCharArray();
        StringBuilder token = new StringBuilder();

        for (char ch : chars) {
            if (Pattern.matches("[\\d.,:;]", String.valueOf(ch))) {
                if (Pattern.matches("[.,:;]", String.valueOf(ch))) {
                    ch = '.';
                }
                token.append(ch);
            } else if (Pattern.matches("[+\\-*/]", String.valueOf(ch))) {
                token.append(" ").append(ch).append(" ");
            } else {
                System.out.println("Недопустимый символ " + ch);
                return "";
            }
        }
        return String.valueOf(token);
    }

    public static long getSeconds(String[] list) {
        long sec = 0;
        String operator = "";
        try {
            for (String token : list) {
                if (!token.matches("\\d+\\.\\d+") && !token.matches("[+\\-*/]")) {
                    System.out.printf("Некорректный ввод %s\n", token);
                    return -1;
                }
                if (token.matches("\\d+\\.\\d+")) {
                    long hour = Long.parseLong(token.split("\\.")[0]);
                    long minute = Long.parseLong(token.split("\\.")[1]);
                    if (minute > 59) {
                        System.out.printf("Некорректный ввод %02d минут! Корректный ввод [0-59]\n", minute);
                        return -1;
                    }
                    long tokenSeconds = hour * 3600 + minute * 60;
                    if (operator.contains("+")) {
                        sec += tokenSeconds;
                    } else if (operator.contains("-")) {
                        sec -= tokenSeconds;
                    } else {
                        sec = tokenSeconds;
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
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
        return sec;
    }

    public static void main(String[] args) {
        System.out.println("Введите выражение например:\n" +
                "1.25+3.45-5.55*2/3\n" +
                "Где целая часть - часы, часть после запятой - минуты\n" +
                "В качестве разделителя допускается использовать '.' ',' ':' ';'\n" +
                "Так же результат можно умножать и делить на целое число\n");
        while (true) {
            System.out.print("Введите выражение:\t");
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();
            String[] list = getFormattedLine(line).split(" ");
            printResult(getSeconds(list));
        }
    }
}