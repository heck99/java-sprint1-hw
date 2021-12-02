import java.util.HashMap;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Приложение по финансовому учёту запущено.\nВыберите действие, которое хотите выполнить:");
        HashMap<String, MonthReport> monthReports= new HashMap<>();//структура данных для хранения всех месячных отчётов, возможно стоило использовать List потому что год и месяц записаны в объекте MonthReport
        HashMap<String, YearReport> yearReports= new HashMap<>();//в текущем задании существует только один годовой отчёт(из-за чего данная структура не нужна), но при расширении данной программы может понадобится работа с несколькими отчётами, поэтому создадим данную структуру
        while (true) {
            SwitchCaseMethods.PrintMenu();//выводим меню пользователю
            int command = scanner.nextInt();//считываем введёное число
            System.out.println();
            switch (command){
                case(1):
                    SwitchCaseMethods.caseOne(monthReports);
                    break;
                case(2):
                    SwitchCaseMethods.caseTwo(yearReports);
                    break;
                case(3):
                    SwitchCaseMethods.caseThree(yearReports, monthReports);
                    break;
                case(4):
                    SwitchCaseMethods.caseFour(monthReports);
                    break;
                case(5):
                    SwitchCaseMethods.caseFive(yearReports);
                    break;
                case(1703)://не совсем понял, про придумать комбинацию для выхода
                    System.out.println("До свидания!");
                    return;
                default:
                    break;
            }
        }
    }
}