public class Person {

    private String name;
    private String lastName;
    private String patronymic;
    private String position;
    private String email;
    private String phoneNumber;
    private int salary;
    private int age;
    private boolean vaccinated;

    public Person(String lastName, String name, String patronymic,
                  String position, String email, String phoneNumber,
                  int salary, int age, boolean vaccinated) {
        this.name = name;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.position = position;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.salary = salary;
        this.age = age;
        this.vaccinated = vaccinated;
    }

    public void print() {
        System.out.printf("%-15s: %s %s %s%n", "ФИО", lastName, name, patronymic);
        System.out.printf("%-15s: %s%n", "Должность", position);
        System.out.printf("%-15s: %s%n", "Почта", email);
        System.out.printf("%-15s: %s%n", "Телефон", phoneNumber);
        System.out.printf("%-15s: %d%n", "Зарплата", salary);
        System.out.printf("%-15s: %d%n", "Возраст", age);
        System.out.printf("%-15s: %s%n", "Вакцинирован", vaccinated ? "Да" : "Нет");
        System.out.println();
    }

    public static void main(String[] args) {
        Person[] persons = new Person[5];
        persons[0] = new Person(
                "Иванов", "Иван", "Иваныч",
                "Инженер", "ivanov@mail.ru", "+7 999 999 99 99",
                100_000, 25, true
        );
        persons[1] = new Person(
                "Федоров", "Федор", "Федорович",
                "Менеджер", "fedorov@gmail.com", "+7 888 888 88 88",
                70_000, 31, false
        );
        persons[2] = new Person(
                "Петров", "Петр", "Петрович",
                "Бурильщик", "petrov@inbox.ru", "+7 777 777 77",
                95_000, 43, true
        );
        persons[3] = new Person(
        "Афанасьев", "Афанасий", "Афанасьевич",
                "Электрик", "afanasiev@yahoo.com", "+7 666 666 66 66",
                50_000, 48, false
        );
        persons[4] = new Person(
                "Васильев", "Василий", "Василиев",
                "Маркетолог", "vasiliev@yandex.ru", "+7 555 555 55 55",
                83_000, 27, true
        );
        System.out.println("----- Штат работников -----\n");
        printAll(persons);
        System.out.println("\n----- Вакцинированные работники -----\n");
        printVaccinated(persons);
    }

    private static void printAll(Person[] persons) {
        for (Person person : persons) {
            person.print();
        }
    }

    private static void printVaccinated(Person[] persons) {
        for (Person person : persons) {
            if (person.isVaccinated()) {
                person.print();
            }
        }
    }

    public boolean isVaccinated() {
        return vaccinated;
    }

}
