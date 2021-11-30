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
        HashMap<String, YearReport> yearReports= new HashMap<>();
        while (true) {
            PrintMenu();//выводим меню пользователю
            int command = scanner.nextInt();//считываем введёное число
            ArrayList<File> MonthData=new ArrayList<>();
            ArrayList<File> YearData=new ArrayList<>();
            GetAllFiles(MonthData, YearData);//считываем все файлы отчёты
            switch (command){
                case(1):
                    System.out.println("Считать все месячные отчёты");
                    if (MonthData.size()==0){
                        System.out.println("Невозможно прочитать файл с месячным отчётом. Возможно, файл не находится в нужной директории.");
                    }
                    for(File file : MonthData){
                        String fileDAta="";
                            try {
                                fileDAta= Files.readString(Path.of(file.getPath()));
                                String[] allLines = fileDAta.split("\\r\\n");
                                MonthReport thisMonthReport = new MonthReport(file.getName().split("\\.")[1]);
                                for (int i=1;i<allLines.length;i++){
                                    String[] dataArr= allLines[i].split(",");
                                    MonthReportElement oneElement = new MonthReportElement(dataArr[0],Boolean.parseBoolean(dataArr[1]),Integer.parseInt(dataArr[2]),Integer.parseInt(dataArr[3]));
                                    thisMonthReport.monthReport.add(oneElement);
                                }
                                monthReports.put(file.getName().split("\\.")[1],thisMonthReport);
                            } catch (IOException e) {
                                System.out.println("Невозможно прочитать файл с месячным отчётом. Возможно, файл не находится в нужной директории.");
                        }
                    }
                    break;
                case(2):
                    System.out.println("Считать годовой отчёт");
                    if (YearData.size()==0){
                        System.out.println("Невозможно прочитать файл сгодовым отчётом. Возможно, файл не находится в нужной директории.");
                    }
                    for(File file : YearData){
                        String fileDAta="";
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
                            System.out.println("Невозможно прочитать файл с месячным отчётом. Возможно, файл не находится в нужной директории.");
                        }
                    }
                    break;
                case(3):
                    System.out.println("Сверить отчёты");
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
        for(File file : listOfFiles){
            if(file.getName().startsWith("m.")){
                MonthData.add(file);
            }else if(file.getName().startsWith("y.")){
                YearData.add(file);
            }
        }
    }
}