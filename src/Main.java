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
            PrintMenu();//выводим меню пользователю
            int command = scanner.nextInt();//считываем введёное число
            ArrayList<File> MonthData=new ArrayList<>();
            ArrayList<File> YearData=new ArrayList<>();
            GetAllFiles(MonthData, YearData);//считываем все файлы отчёты
            switch (command){
                case(1):
                    System.out.println("Считать все месячные отчёты");
                    if (MonthData.size()==0){//если в директории resources нет месячных отчётов, то сообщим об этом пользователю
                        System.out.println("Невозможно прочитать файл с месячным отчётом. Возможно, файл не находится в нужной директории.");
                    }
                    for(File file : MonthData){//для каждого месячного отчёта
                        String fileDAta;
                            try {
                                fileDAta= Files.readString(Path.of(file.getPath()));//получим содержимое текущего отчёта
                                String[] allLines = fileDAta.split("\\r\\n");//разделим строки файла
                                MonthReport thisMonthReport = new MonthReport(file.getName().split("\\.")[1]);//создадим объект месячного отчёта для текущего месяца
                                for (int i=1;i<allLines.length;i++){//для каждой записи
                                    String[] dataArr= allLines[i].split(",");// получим все столбцы записи
                                    MonthReportElement oneElement = new MonthReportElement(dataArr[0],Boolean.parseBoolean(dataArr[1]),Integer.parseInt(dataArr[2]),Integer.parseInt(dataArr[3]));//создадим объект текущей записи
                                    thisMonthReport.monthReport.add(oneElement);//добавим текущую запись в месячный отчёт
                                }
                                monthReports.put(file.getName().split("\\.")[1],thisMonthReport);//добавим месячный отчёт в таблицу со всеми отчётами
                            } catch (IOException e) {
                                System.out.println("Невозможно прочитать файл с месячным отчётом. Возможно, файл уже открыт.");//если не получается открыть файл уведомим пользователя
                        }
                    }
                    break;
                case(2):
                    System.out.println("Считать годовой отчёт");
                    if (YearData.size()==0){
                        System.out.println("Невозможно прочитать файл сгодовым отчётом. Возможно, файл не находится в нужной директории.");
                    }
                    for(File file : YearData){
                        String fileDAta;
                        try {
                            fileDAta= Files.readString(Path.of(file.getPath()));
                            String[] allLines = fileDAta.split("\\r\\n");
                            YearReport thisYearReport = new YearReport();
                            for (int i=1;i<allLines.length;i++){
                                String[] dataArr= allLines[i].split(",");
                                if(thisYearReport.containMonth(Integer.parseInt(dataArr[0]))){
                                    thisYearReport.addDataToMonth(Integer.parseInt(dataArr[0]),Integer.parseInt(dataArr[1]),Boolean.parseBoolean(dataArr[2]));
                                }else{
                                    YearReportElement oneElement = new YearReportElement(Integer.parseInt(dataArr[0]),Integer.parseInt(dataArr[1]),Boolean.parseBoolean(dataArr[2]));
                                    thisYearReport.yearReport.add(oneElement);
                                }

                            }
                            yearReports.put(file.getName().split("\\.")[1],thisYearReport);
                        } catch (IOException e) {
                            System.out.println("Невозможно прочитать файл с месячным отчётом. Возможно, файл уже открыт.");
                        }
                    }
                    break;
                case(3):
                    System.out.println("Сверить отчёты");
                     for(String year : yearReports.keySet()){
                         int errors=0;
                         for(YearReportElement oneMonth : yearReports.get(year).yearReport){
                             int monthExpensesInYearReport= oneMonth.expenses;
                             int monthProfitInYearReport = oneMonth.profit;
                             String monthReportName;
                             if(oneMonth.monthNum<10){
                                 monthReportName=year+"0"+oneMonth.monthNum;
                             }else {
                                 monthReportName=year+oneMonth.monthNum;
                             }
                             MonthReport thisMonthReport = monthReports.get(monthReportName);
                             int monthExpensesInMonthReport = thisMonthReport.calculateExpensesInMonth();
                             int monthProfitInMonthReport = thisMonthReport.calculateProfitInMonth();

                             if(!(monthExpensesInMonthReport==monthExpensesInYearReport)){
                                 errors++;
                                 System.out.println("Ошибка в отчёте по расходам год: "+year+" месяц: "+oneMonth.monthNum);
                             }
                             if(!(monthProfitInYearReport==monthProfitInMonthReport)){
                                 errors++;
                                 System.out.println("Ошибка в отчёте по доходам год: "+year+" месяц: "+oneMonth.monthNum);
                             }
                         }
                         if(errors==0){
                             System.out.println("Ошибок в отчётах нет");
                         }
                     }
                    break;
                case(4):
                    System.out.println("Вывести информацию о всех месячных отчётах");
                    for(String name: monthReports.keySet()){
                        monthReports.get(name).printInfoForOneMonth();
                    }
                    break;
                case(5):
                    System.out.println("Вывести информацию о годовом отчёте");
                    for(String name: yearReports.keySet()){
                        yearReports.get(name).printInfoForOneYear(name);
                    }
                    break;
                case(0):
                    return;
                default:
                    break;
            }


        }
    }
    public static void PrintMenu(){
        System.out.println("1: Считать все месячные отчёты");
        System.out.println("2: Считать годовой отчёт");
        System.out.println("3: Сверить отчёты");
        System.out.println("4: Вывести информацию о всех месячных отчётах");
        System.out.println("5: Вывести информацию о годовом отчёте");
        System.out.println("0: Выход");
    }
    public static void GetAllFiles(List<File>MonthData,List<File>YearData){
        File folder = new File("resources");
        File[] listOfFiles = folder.listFiles();
        if(listOfFiles!=null) {
            for (File file : listOfFiles) {
                if (file.getName().startsWith("m.")) {
                    MonthData.add(file);
                } else if (file.getName().startsWith("y.")) {
                    YearData.add(file);
                }
            }
        }
    }
}