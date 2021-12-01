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
            System.out.println();
            ArrayList<File> MonthData=new ArrayList<>();
            ArrayList<File> YearData=new ArrayList<>();
            GetAllFiles(MonthData, YearData);//считываем все файлы отчёты
            switch (command){
                case(1):
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
                    System.out.println("Месячные отчёты успешно считаны");
                    break;
                case(2):
                    if (YearData.size()==0){//если нет файлов с месячными отчётами
                        System.out.println("Невозможно прочитать файл сгодовым отчётом. Возможно, файл не находится в нужной директории.");
                    }
                    for(File file : YearData){//поочереди открываем каждый файл
                        String fileDAta;
                        try {
                            fileDAta= Files.readString(Path.of(file.getPath()));//считываем все данные
                            String[] allLines = fileDAta.split("\\r\\n");//разделяем на строки
                            YearReport thisYearReport = new YearReport();
                            for (int i=1;i<allLines.length;i++){
                                String[] dataArr= allLines[i].split(",");//разделяем на столбцы
                                if(thisYearReport.containMonth(Integer.parseInt(dataArr[0]))){//если этот месяц уже добавлен в годовой отчёт
                                    thisYearReport.addDataToMonth(Integer.parseInt(dataArr[0]),Integer.parseInt(dataArr[1]),Boolean.parseBoolean(dataArr[2]));//добавляем запись в тот же отчёт
                                }else{
                                    //создаём новую запись
                                    YearReportElement oneElement = new YearReportElement(Integer.parseInt(dataArr[0]),Integer.parseInt(dataArr[1]),Boolean.parseBoolean(dataArr[2]));
                                    thisYearReport.yearReport.add(oneElement);
                                }

                            }
                            yearReports.put(file.getName().split("\\.")[1],thisYearReport);//добавляем годовой отчёт ко всем отчётам
                        } catch (IOException e) {
                            System.out.println("Невозможно прочитать файл с месячным отчётом. Возможно, файл уже открыт.");
                        }
                    }
                    System.out.println("Годовые отчёты успешно считаны");
                    break;
                case(3):
                    if(yearReports.size()==0|| monthReports.size()==0){
                        System.out.println("Вы забыли ввести данные");
                        break;
                    }
                     for(String year : yearReports.keySet()){
                         int errors=0;//счётчик ошибок
                         for(YearReportElement oneMonth : yearReports.get(year).yearReport){
                             int monthExpensesInYearReport= oneMonth.expenses;//траты за месяц в годовом отчёте
                             int monthProfitInYearReport = oneMonth.profit; //прибыль за месяц в годовом отчёте
                             String monthReportName;
                             //создадим ключ для хэш таблицы месячных отчётов
                             if(oneMonth.monthNum<10){
                                 monthReportName=year+"0"+oneMonth.monthNum;
                             }else {
                                 monthReportName=year+oneMonth.monthNum;
                             }
                             MonthReport thisMonthReport = monthReports.get(monthReportName);//получим месячный отчёт за нужный месяц
                             int monthExpensesInMonthReport = thisMonthReport.calculateExpensesInMonth();//траты за месяц в месячном отчёте
                             int monthProfitInMonthReport = thisMonthReport.calculateProfitInMonth();//прибыль за месяц в месячном отчёте

                             if(!(monthExpensesInMonthReport==monthExpensesInYearReport)){//если ошибка по тратам в отчётах
                                 errors++;
                                 System.out.println("Ошибка в отчёте по расходам год: "+year+" месяц: "+oneMonth.monthNum);
                             }
                             if(!(monthProfitInYearReport==monthProfitInMonthReport)){//если ошибка по прибыли в отчётах
                                 errors++;
                                 System.out.println("Ошибка в отчёте по доходам год: "+year+" месяц: "+oneMonth.monthNum);
                             }
                         }
                         if(errors==0){//если во всех очтётах нет ошибок
                             System.out.println("Ошибок в отчётах нет");
                         }
                     }
                    break;
                case(4):
                    if(monthReports.size()==0){
                        System.out.println("Вы забыли ввести данные");
                        break;
                    }
                    for(String name: monthReports.keySet()){
                        monthReports.get(name).printInfoForOneMonth();//печатаем месячный отчёт
                    }
                    break;
                case(5):
                    if(yearReports.size()==0){
                        System.out.println("Вы забыли ввести данные");
                        break;
                    }
                    for(String name: yearReports.keySet()){
                        yearReports.get(name).printInfoForOneYear(name);//печатаем годовой отчёт
                    }
                    break;
                case(1703)://не совсем понял, про придумать комбинацию для выхода
                    return;
                default:
                    break;
            }
        }
    }
    public static void PrintMenu(){
        System.out.println();
        System.out.println("1: Считать все месячные отчёты");
        System.out.println("2: Считать годовой отчёт");
        System.out.println("3: Сверить отчёты");
        System.out.println("4: Вывести информацию о всех месячных отчётах");
        System.out.println("5: Вывести информацию о годовом отчёте");
        System.out.println("Для выхода введите год основания Санкт-Петербурга");
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