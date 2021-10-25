public class Lesson1 {
    public static void main(String[] args) {
        // 1. создать переменные всех пройденных типов и проиниализировать их
        byte b = 1;
        short s = 2;
        int i = 1_000_000;
        long l = 1_000_000_000;
        boolean bn = true;
        char c = 'J';
        float f = 1.23f;
        double d = 2.e-5;
    }

    // 2. Написать метод, вычисляющий выражение и возвращающий результат
    static int calculate(int a, int b, int c, int d) {
        return a * (b + (c / d));
    }

    // 3. Написать метод, принимающий на вход два числа и проверяющий, что сумма лежит
    // в пределах 10 и 20 включительно
    static boolean checkSumBelongDiapason(int first, int second) {
        int sum = first + second;
        return 10 <= sum && sum <= 20;
    }

    // 4. Написать метод, которому в качества параметра передается целое число
    // Метод должен напечать в консоль положительное ли число передали или отрицательное
    static void printSign(int number) {
        if (number < 0) {
            System.out.printf("%d отрицательное%n", number);
        } else {
            System.out.printf("%d положительное%n", number);
        }
    }

    // 5. Написать метод, которому в качестве параметра передается целое число
    // Метод должен вернуть true, если число отрицательное.
    static boolean isNegative(int number) {
        return number < 0;
    }

    // 6. Написать метод, которому в качестве параметра передается строка,
    // обозначающее имя. Вывести "Привет, указанное_имя!"
    static void printHello(String name) {
        System.out.printf("Привет, %s!%n", name);
    }

    // 7. Написать метод, который определяет является ли год высокосным и выводит
    // сообщение в консоль.
    static void printIsLeapYear(int year) {
        if (year % 4 == 0 && ((year % 100 != 0) || year % 400 == 0)) {
            System.out.printf("%d высокосный.%n", year);
        } else {
            System.out.printf("%d не высокосный.%n", year);
        }
    }

}
