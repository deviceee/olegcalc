import java.util.Scanner;

class Main {
    public static final String[] ROME = {"C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
    public static final int[] INTEGER = {100, 90, 50, 40, 10, 9, 5, 4, 1};

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println("Введите арифметическое выражение или end, чтобы завершить программу: ");
            String input = scanner.nextLine();
            if(input.equalsIgnoreCase("end")) break;

            try {
                String result = calc(input);
                System.out.println("Результат: " + result);
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
    }

    public static String calc(String input) {
        String[] parts = input.split(" ");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Неправильный формат выражения");
        }

        int num1 = parseNumber(parts[0]);
        int num2 = parseNumber(parts[2]);
        String operator = parts[1];

        if ((isRoman(parts[0]) && !isRoman(parts[2])) || (!isRoman(parts[0]) && isRoman(parts[2]))) {
            throw new IllegalArgumentException("Используются разные системы счисления");
        }

        int result;
        switch (operator) {
            case "+":
                result = num1 + num2;
                break;
            case "-":
                result = num1 - num2;
                if (isRoman(parts[0]) && result <= 0) {
                    throw new IllegalArgumentException("В римской системе нет отрицательных чисел");
                }
                break;
            case "*":
                result = num1 * num2;
                break;
            case "/":
                if (num2 == 0) {
                    throw new IllegalArgumentException("Деление на ноль");
                }
                result = num1 / num2;
                break;
            default:
                throw new IllegalArgumentException("Недопустимая операция");
        }

        return toOutputFormat(result, isRoman(parts[0]));
    }

    private static int parseNumber(String str) {
        if (isRoman(str)) {
            return convertFromRome(str);
        } else {
            try {
                int num = Integer.parseInt(str);
                if (num < 1 || num > 100) {
                    throw new IllegalArgumentException("Число вне диапазона 1-100");
                }
                return num;
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Неправильное число");
            }
        }
    }

    private static boolean isRoman(String str) {
        return str.matches("[IVXLCDM]+");
    }

    private static String toOutputFormat(int num, boolean isRoman) {
        if (isRoman) {
            return convertToRome(num);
        } else {
            return String.valueOf(num);
        }
    }

    private static int convertFromRome(String romanNumber) {
        int result = 0;

        for (int i = 0; i < ROME.length; i++) {
            while (romanNumber.startsWith(ROME[i])) {
                result += INTEGER[i];
                romanNumber = romanNumber.substring(ROME[i].length());
            }
        }

        return result;
    }

    private static String convertToRome(int num) {
        StringBuilder romanNumber = new StringBuilder();

        for (int i = 0; i < ROME.length; i++) {
            while (num >= INTEGER[i]) {
                romanNumber.append(ROME[i]);
                num -= INTEGER[i];
            }
        }

        return romanNumber.toString();
    }

}
