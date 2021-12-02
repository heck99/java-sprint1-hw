import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Приложение по финансовому учёту запущено.\nВыберите действие, которое хотите выполнить:");
        HashMap<String, MonthReport> monthReports= new HashMap<>();//структура данных для хранения всех месячных отчётов, возможно стоило использовать List потому что год и месяц записаны в объекте MonthReport
        HashMap<String, YearReport> yearReports= new HashMap<>();//в текущем задании существует только один годовой отчёт(из-за чего данная структура не нужна), но при расширении данной программы может понадобится работа с несколькими отчётами, поэтому создадим данную структуру
        while (true) {
            SwichCaseMethods.PrintMenu();//выводим меню пользователю
            int command = scanner.nextInt();//считываем введёное число
            System.out.println();
            switch (command){
                case(1):
                    SwichCaseMethods.caseOne(monthReports);
                    break;
                case(2):
                    SwichCaseMethods.caseTwo(yearReports);
                    break;
                case(3):
                    SwichCaseMethods.caseThree(yearReports, monthReports);
                    break;
                case(4):
                    SwichCaseMethods.caseFour(monthReports);
                    break;
                case(5):
                    SwichCaseMethods.caseFive(yearReports);
                    break;
                case(1703)://не совсем понял, про придумать комбинацию для выхода
                    return;
                default:
                    break;
            }
        }
    }
}