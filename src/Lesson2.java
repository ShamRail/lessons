public class Lesson2 {

    // 1. Задать массив 0 и 1. Заменить в массиве 0 на 1, а 1 на 0.
    static void reverse01() {
        int[] data = {1, 1, 0, 0, 1, 0, 1, 1, 0, 0};
        for (int i = 0; i < data.length; i++) {
            data[i] = data[i] == 1 ? 0 : 1;
        }
    }

    // 2. Задать пустой целочисленный массив размера 8. С помощью цикла
    // заполнить его значениями 0, 3, 6, 9, 12, 15, 18, 21
    static void fillArray() {
        int[] array = new int[8];
        for (int i = 0; i < array.length; i++) {
            array[i] = 3 * i;
        }
    }

    // 3. Заполнить массив числами 1, 5, 3, 2, 11, 4, 5, 2, 4, 8, 9, 1.
    // Умножить числа массива меньшие 6 на 2.
    static void handleArray() {
        int[] array = {
                1, 5, 3, 2, 11, 4, 5, 2, 4, 8, 9, 1
        };
        for (int i = 0; i < array.length; i++) {
            array[i] = array[i] < 6 ? array[i] * 2 : array[i];
        }
    }

    // 4. Заполнить диагональными элементы матрицы 1.
    static void fillDiagonalOfMatrix() {
        int[][] data = {
                {38, 34, 11, 14, 20},
                {20, 38, 13, 39, 12},
                {43, 35, 39, 38, 49},
                {10, 51, 45, 23, 38},
                {49, 39, 50, 42, 17}
        };
        for (int i = 0; i < data.length; i++) {
            int left = i;
            int right = data.length - 1 - i;
            data[left][i] = 1;
            data[right][i] = 1;
        }
    }

    // 5. Задать одномерный массив и найти мин./макс.
    static void minMax() {
        int[] array = {
                44, 35, 45, 42, 45, 34, 36, 52, 52, 18, 19, 47, 47, 36, 34
        };
        int min = array[0];
        int max = array[0];
        for (int value : array) {
            min = Math.min(value, min);
            max = Math.max(value, max);
        }
    }

    // 6. Написать метод, который проверяет, что в массиве есть место,
    // для которого суммы элементов левой и правой частей будут равны.
    static boolean checkBalance(int[] array) {
        boolean found = false;
        for (int border = 1; border < array.length && !found; border++) {
            int leftSum = 0;
            for (int l = 0; l < border; l++) {
                leftSum += array[l];
            }
            int rightSum = 0;
            for (int r = border; r < array.length; r++) {
                rightSum += array[r];
            }
            found = leftSum == rightSum;
        }
        return found;
    }

}
