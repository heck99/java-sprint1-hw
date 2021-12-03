import java.util.HashMap;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Приложение по финансовому учёту запущено.\nВыберите действие, которое хотите выполнить:");
        //структура данных для хранения всех месячных отчётов
        HashMap<String, MonthReport> monthReports= new HashMap<>();
        //структура данных для хранения всех годовых отчётов
        HashMap<String, YearReport> yearReports= new HashMap<>();
        while (true) {
            PrintMenu();//выводим меню пользователю
            int command = scanner.nextInt();//считываем введёное число
            System.out.println();
            switch (command) {
                case(1):
                    SwitchCaseMethods.readMonthReports(monthReports);
                    break;
                case(2):
                    SwitchCaseMethods.readYearReports(yearReports);
                    break;
                case(3):
                    SwitchCaseMethods.compareReports(yearReports, monthReports);
                    break;
                case(4):
                    SwitchCaseMethods.printMonthReports(monthReports);
                    break;
                case(5):
                    SwitchCaseMethods.printYearReports(yearReports);
                    break;
                case(1703)://не совсем понял, про придумать комбинацию для выхода
                    System.out.println("До свидания!");
                    return;
                default:
                    System.out.println("Данной команды не существует");
                    break;
            }
        }
    }

    public static void PrintMenu() {
        System.out.println();
        System.out.println("1: Считать все месячные отчёты");
        System.out.println("2: Считать годовой отчёт");
        System.out.println("3: Сверить отчёты");
        System.out.println("4: Вывести информацию о всех месячных отчётах");
        System.out.println("5: Вывести информацию о годовом отчёте");
        System.out.println("Для выхода введите год основания Санкт-Петербурга(1703)");
    }
}