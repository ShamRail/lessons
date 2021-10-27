
import java.util.Random;
import java.util.Scanner;

public class Cross {

    // 3. Определяем размеры массива
    static final int SIZE_X = 5;
    static final int SIZE_Y = 5;

    // 1. Создаем двумерный массив
    static char[][] field = new char[SIZE_Y][SIZE_X];

    // 2. Обозначаем кто будет ходить какими фишками
    static final char PLAYER_DOT = 'X';
    static final char AI_DOT = '0';
    static final char EMPTY_DOT = '.';

    // 8. Создаем сканер
    static Scanner scanner = new Scanner(System.in);
    // 12. Создаем рандом
    static final Random rand = new Random();

    // Флаг того ходит ли AI в первый раз. В первый раз ходит случайно
    static boolean isAIFirstStep = true;

    // Число подряд идущих фишек для выигрыша
    static final int WIN_SEQUENCE_AMOUNT = 4;

    // 4. Заполняем на массив
    private static void initField() {
        for (int i = 0; i < SIZE_Y; i++) {
            for (int j = 0; j < SIZE_X; j++) {
                field[i][j] = EMPTY_DOT;
            }
        }
    }

    // 5. Выводим на массив на печать
    private static void printField() {
        //6. украшаем картинку
        System.out.println("-------");
        for (int i = 0; i < SIZE_Y; i++) {
            System.out.print("|");
            for (int j = 0; j < SIZE_X; j++) {
                System.out.print(field[i][j] + "|");
            }
            System.out.println();
        }
        //6. украшаем картинку
        System.out.println("-------");
    }

    // 7. Метод который устанавливает символ
    private static void setSym(int y, int x, char sym) {
        field[y][x] = sym;
    }

    // 9. Ход игрока
    private static void playerStep() {
        // 11. с проверкой
        int x;
        int y;
        do {
            System.out.printf("Введите координаты: X Y (1-%d)%n", SIZE_X);
            x = scanner.nextInt() - 1;
            y = scanner.nextInt() - 1;
        } while (!isCellValid(y, x));
        setSym(y, x, PLAYER_DOT);

    }

    // 13. Ход ПК
    private static void aiStep() {
        int x;
        int y;
        if (isAIFirstStep) { // Первый ход AI делает случайно
            do {
                 x = rand.nextInt(SIZE_X);
                 y = rand.nextInt(SIZE_Y);
            } while (!isCellValid(y, x));
            isAIFirstStep = false;
        } else {
            // ищем выигрышные ситуации для бота
            // выбираем строку/столбец/диагональ где больше всего фишек
            int[] aiResult = maxResult(
                    aiCheckRow(AI_DOT, PLAYER_DOT),
                    aiCheckCol(AI_DOT, PLAYER_DOT),
                    aiCheckDiagonals(AI_DOT, PLAYER_DOT)
            );
            // ищем выигрышные ситуации для пользователя
            // выбираем строку/столбец/диагональ где больше всего фишек
            int[] playerResult = maxResult(
                    aiCheckRow(PLAYER_DOT, AI_DOT),
                    aiCheckCol(PLAYER_DOT, AI_DOT),
                    aiCheckDiagonals(PLAYER_DOT, AI_DOT)
            );
            // Если пользователь близок к победе, а бот нет
            if (playerResult[0] == WIN_SEQUENCE_AMOUNT - 1 && aiResult[0] != WIN_SEQUENCE_AMOUNT - 1) {
                // не даем пользователю выиграть.
                y = playerResult[1];
                x = playerResult[2];
            } else {
                // иначе AI делает свой ход
                y = aiResult[1];
                x = aiResult[2];
            }
        }
        setSym(y, x, AI_DOT);
    }

    // 13.1. Возвращает массив с ходом, где больше всего подряд идущиз фишек
    private static int[] maxResult(int[] rowResult, int[] colResult, int[] diagResult) {
        int[] result = rowResult[0] > colResult[0] ? rowResult : colResult;
        result = result[0] > diagResult[0] ? result : diagResult;
        return result;
    }

    // 13.2. Анализ строк
    private static int[] aiCheckRow(char player, char opponent) {
        return aiCheckRowAndCol(SIZE_Y, true, player, opponent);
    }

    // 13.3. Анализ столбцов
    private static int[] aiCheckCol(char player, char opponent) {
        return aiCheckRowAndCol(SIZE_X, false, player, opponent);
    }

    // 13.4. Обобщенный метод, который анализирует строку или столбец
    // Возвращает массив, где больше всего фишек с позицией последующего хода
    private static int[] aiCheckRowAndCol(int size, boolean checkRow, char player, char opponent) {
        int max = 0;
        int[] result = {0, -1, -1};
        int dy = checkRow ? 0 : 1;
        int dx = checkRow ? 1 : 0;
        for (int start = 0; start <= size - WIN_SEQUENCE_AMOUNT; start++) {
            int maxI = -1;
            for (int i = 0; i < size; i++) {
                int localMax = checkRow ? getLengthWinSequence(player, opponent, i, start, dy, dx) :
                        getLengthWinSequence(player, opponent, start, i, dy, dx);
                if (localMax > max) {
                    max = localMax;
                    maxI = i;
                }
            }
            if (maxI != -1) {
                int[] position = checkRow ? choosePosition(maxI, start, dy, dx, player):
                        choosePosition(start, maxI, dy, dx, player);
                result = new int[]{max, position[0], position[1]};
            }
        }
        return result;
    }

    // 13.5. Анализ диагоналей
    // Возвращает массив, где больше всего фишек с позицией последующего хода
    private static int[] aiCheckDiagonals(char player, char opponent) {
        int maxI = -1;
        int maxJ = -1;
        int max = 0;
        int dx = 0;
        int[] result = {0, -1, -1};
        for (int i = 0; i <= SIZE_Y - WIN_SEQUENCE_AMOUNT; i++) {
            for (int j = 0; j <= SIZE_X - WIN_SEQUENCE_AMOUNT; j++) {
                int leftMax = getLengthWinSequence(player, opponent, i, j, 1, 1);
                if (leftMax > max) {
                    max = leftMax;
                    maxI = i;
                    maxJ = j;
                    dx = 1;
                }
                int rightMax = getLengthWinSequence(player, opponent, i, SIZE_X - j - 1, 1, -1);
                if (rightMax > max) {
                    max = rightMax;
                    maxI = i;
                    maxJ = SIZE_X - j - 1;
                    dx = -1;
                }
            }
        }
        if (maxI != -1) {
            int[] position = choosePosition(maxI, maxJ, 1, dx, player);
            result = new int[]{max, position[0], position[1]};
        }
        return result;
    }

    // 13.5 Находит количество фишек в последовательности
    private static int getLengthWinSequence(char dot, char opposite, int y, int x, int dy, int dx) {
        int count = 0;
        for (int i = 0; i < WIN_SEQUENCE_AMOUNT; i++, y += dy, x += dx) {
            if (field[y][x] == dot) {
                count++;
            }
            // если в игровой последовательности встречен ход противника,
            // то последовательность не может быть выигрышной
            if (field[y][x] == opposite) {
                count = 0;
                break;
            }
        }
        return count;
    }

    // 13.6 Выбирает позицию для следуюшего хода
    private static int[] choosePosition(int y, int x, int dy, int dx, char dot) {
        int[] position = {y, x};
        // Находим первую пустую позицию
        for (int i = 0, yc = y, xc = x; i < WIN_SEQUENCE_AMOUNT; i++, yc += dy, xc += dx) {
            if (field[yc][xc] == EMPTY_DOT) {
                position = new int[] {yc, xc};
                break;
            }
        }
        // Также пытаемся найти позицию, где находится фишка. Поставновка фишки рядом предпочтительнее
        for (int i = 1, yc = y + dy, xc = x + dx; i < WIN_SEQUENCE_AMOUNT; i++, yc += dy, xc += dx) {
            if (field[yc][xc] == dot && field[yc - dy][xc - dx] == EMPTY_DOT) {
                position = new int[] {yc - dy, xc - dx};
                break;
            }
        }
        return position;
    }

    // 14. Проверка победы
    private static boolean checkWin(char sym) {
        return checkRow(sym) || checkCol(sym) || checkDiagonal(sym);
    }

    // 14. 1) Проверки выигрыша по строкам
    private static boolean checkRow(char sym) {
        boolean result = false;
        for (int start = 0; start <= SIZE_Y - WIN_SEQUENCE_AMOUNT; start++) {
            for (int row = 0; row < SIZE_Y; row++) {
                if (getLengthWinSequence(sym, sym == PLAYER_DOT ? AI_DOT : PLAYER_DOT, row, start, 0, 1) == WIN_SEQUENCE_AMOUNT) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    // 14. 2) Проверки выигрыша по столбцам
    private static boolean checkCol(char sym) {
        boolean result = false;
        for (int start = 0; start <= SIZE_X - WIN_SEQUENCE_AMOUNT; start++) {
            for (int col = 0; col < SIZE_X; col++) {
                if (getLengthWinSequence(sym, sym == PLAYER_DOT ? AI_DOT : PLAYER_DOT, start, col, 1, 0) == WIN_SEQUENCE_AMOUNT) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    // 14. 3) Проверки выигрыша по диагоналям
    private static boolean checkDiagonal(char sym) {
        for (int i = 0; i <= SIZE_Y - WIN_SEQUENCE_AMOUNT; i++) {
            for (int j = 0; j <= SIZE_X - WIN_SEQUENCE_AMOUNT; j++) {
                if (getLengthWinSequence(sym, sym == PLAYER_DOT ? AI_DOT : PLAYER_DOT, i, j, 1, 1) == WIN_SEQUENCE_AMOUNT
                        || getLengthWinSequence(sym, sym == PLAYER_DOT ? AI_DOT : PLAYER_DOT, i, SIZE_X - j - 1, 1, -1) == WIN_SEQUENCE_AMOUNT) {
                    return true;
                }
            }
        }
        return false;
    }

    // 16. Проверка полное ли поле? возможно ли ходить?
    private static boolean isFieldFull() {
        for (int i = 0; i < SIZE_Y; i++) {
            for (int j = 0; j < SIZE_X; j++) {
                if (field[i][j] == EMPTY_DOT) {
                    return false;
                }
            }
        }
        return true;
    }

    // 10. Проверяем возможен ли ход
    private static boolean isCellValid(int y, int x) {
        // если вываливаемся за пределы возвращаем false
        if (x < 0 || y < 0 || x > SIZE_X - 1 || y > SIZE_Y - 1) {
            return false;
        }
        // если не путое поле тоже false
        return (field[y][x] == EMPTY_DOT);
    }

    public static void main(String[] args) {
        // 1 - 1 иницируем и выводим на печать
        initField();
        printField();
        // 1 - 1 иницируем и выводим на печать

        // 15 Основной ход программы

        while (true) {
            playerStep();
            printField();
            if (checkWin(PLAYER_DOT)) {
                System.out.println("Player WIN!");
                break;
            }
            if (isFieldFull()) {
                System.out.println("DRAW");
                break;
            }

            aiStep();
            printField();
            if (checkWin(AI_DOT)) {
                System.out.println("Win SkyNet!");
                break;
            }
            if (isFieldFull()) {
                System.out.println("DRAW!");
                break;
            }
        }

    }
}
