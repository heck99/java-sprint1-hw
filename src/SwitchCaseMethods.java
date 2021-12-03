//Не совсем понял, как с Вами можно связаться напрямую, но надеюсь, я правильно понял, что Вы говорили про вынести все методы в отдельный класс
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SwitchCaseMethods {

    public static void readMonthReports(HashMap<String, MonthReport> monthReports) {
        ArrayList<File> monthData=new ArrayList<>();
        GetAllMonthFiles(monthData);
        //если в директории resources нет месячных отчётов, то сообщим об этом пользователю
        if (monthData.size()==0) {
            System.out.println("Невозможно прочитать файл с месячным отчётом. Возможно, файл не находится в нужной директории.");
        }
        for (File file : monthData) {//для каждого месячного отчёта
            String fileDAta = readDataFromFile(file.getPath());
            if (fileDAta == null) {
                System.out.println("Не удалось прочитать файл " + file.getName());
            } else {
                String[] allLines = fileDAta.split("\\r\\n");//разделим строки файла
                //создадим объект месячного отчёта для текущего месяца
                MonthReport thisMonthReport = new
                        MonthReport(file.getName().split("\\.")[1]);
                for (int i = 1; i < allLines.length; i++) {//для каждой записи
                    String[] dataArr = allLines[i].split(",");// получим все столбцы записи
                    //создадим объект текущей записи
                    MonthReportElement oneElement = new
                            MonthReportElement(dataArr[0], Boolean.parseBoolean(dataArr[1]),
                            Integer.parseInt(dataArr[2]), Integer.parseInt(dataArr[3]));
                    thisMonthReport.monthReport.add(oneElement);//добавим текущую запись в месячный отчёт
                }
                //добавим месячный отчёт в таблицу со всеми отчётами
                monthReports.put(file.getName().split("\\.")[1], thisMonthReport);
                System.out.println("Файл " + file.getName()+" успешно считан");
            }
        }
    }

    public static void readYearReports(HashMap<String, YearReport> yearReports) {
        ArrayList<File> yearData=new ArrayList<>();
        GetAllYearFiles(yearData);
        if (yearData.size()==0) {//если нет файлов с месячными отчётами
            System.out.println("Невозможно прочитать файл сгодовым отчётом. Возможно, файл не находится в нужной директории.");
        }
        for (File file : yearData) { //поочереди открываем каждый файл
            String fileDAta=readDataFromFile(file.getPath());
            if (fileDAta==null) {
                System.out.println("Не удалось прочитать файл "+ file.getName());
            } else {
                String[] allLines = fileDAta.split("\\r\\n");//разделяем на строки
                YearReport thisYearReport = new YearReport();
                for (int i = 1; i < allLines.length; i++) {
                    String[] dataArr = allLines[i].split(",");//разделяем на столбцы
                    //если этот месяц уже добавлен в годовой отчёт
                    if (thisYearReport.containMonth(Integer.parseInt(dataArr[0]))) {
                        thisYearReport.addDataToMonth(Integer.parseInt(dataArr[0]), Integer.parseInt(dataArr[1]),
                                Boolean.parseBoolean(dataArr[2]));//добавляем запись в тот же отчёт
                    } else {
                        //создаём новую запись
                        YearReportElement oneElement = new YearReportElement(Integer.parseInt(dataArr[0]),
                                Integer.parseInt(dataArr[1]), Boolean.parseBoolean(dataArr[2]));
                        thisYearReport.yearReport.add(oneElement);
                    }

                }
                //добавляем годовой отчёт ко всем отчётам
                yearReports.put(file.getName().split("\\.")[1], thisYearReport);
                System.out.println("Файл " + file.getName()+" успешно считан");
            }
        }
    }

    public static void compareReports(HashMap<String, YearReport> yearReports,HashMap<String, MonthReport> monthReports) {
        if (yearReports.size()==0|| monthReports.size()==0) {
            System.out.println("Вы забыли ввести данные");
            return;
        }
        for (String year : yearReports.keySet()) {
            int errors=0;//счётчик ошибок
            for (YearReportElement oneMonth : yearReports.get(year).yearReport) {
                int monthExpensesInYearReport= oneMonth.expenses;//траты за месяц в годовом отчёте
                int monthProfitInYearReport = oneMonth.profit; //прибыль за месяц в годовом отчёте
                String monthReportName;
                //создадим ключ для хэш таблицы месячных отчётов
                if (oneMonth.monthNum<10) {
                    monthReportName=year+"0"+oneMonth.monthNum;
                } else {
                    monthReportName=year+oneMonth.monthNum;
                }
                //получим месячный отчёт за нужный месяц
                MonthReport thisMonthReport = monthReports.get(monthReportName);
                //траты за месяц в месячном отчёте
                int monthExpensesInMonthReport = thisMonthReport.calculateExpensesInMonth();
                //прибыль за месяц в месячном отчёте
                int monthProfitInMonthReport = thisMonthReport.calculateProfitInMonth();

                if (!(monthExpensesInMonthReport==monthExpensesInYearReport)) {//если ошибка по тратам в отчётах
                    errors++;
                    System.out.println("Ошибка в отчёте по расходам год: "+year+" месяц: "+oneMonth.monthNum);
                }
                if (!(monthProfitInYearReport==monthProfitInMonthReport)) {//если ошибка по прибыли в отчётах
                    errors++;
                    System.out.println("Ошибка в отчёте по доходам год: "+year+" месяц: "+oneMonth.monthNum);
                }
            }
            if (errors==0) {//если во всех очтётах нет ошибок
                System.out.println("Ошибок в отчётах нет");
            }
        }
    }

    public static void printMonthReports(HashMap<String, MonthReport> monthReports) {
        if (monthReports.size()==0) {
            System.out.println("Вы забыли ввести данные");
            return;
        }
        for (String name: monthReports.keySet()) {
            monthReports.get(name).printInfoForOneMonth();//печатаем месячный отчёт
        }
    }

    public  static void printYearReports(HashMap<String, YearReport> yearReports) {
        if (yearReports.size()==0) {
            System.out.println("Вы забыли ввести данные");
            return;
        }
        for (String name: yearReports.keySet()) {
            yearReports.get(name).printInfoForOneYear(name);//печатаем годовой отчёт
        }
    }

    private static void GetAllMonthFiles(List<File> monthData) {
        File folder = new File("resources");
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles!=null) {
            for (File file : listOfFiles) {
                if (file.getName().startsWith("m.")) {
                    monthData.add(file);
                }
            }
        }
    }

    private static String readDataFromFile(String path) {
        String fileDAta="";
        try {
            fileDAta= Files.readString(Path.of(path));//получим содержимое текущего отчёта
        } catch (IOException e) {
            //если не получается открыть файл
            return null;
        }
        return fileDAta;
    }

    private static void GetAllYearFiles(List<File> yearData) {
        File folder = new File("resources");
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles!=null) {
            for (File file : listOfFiles) {
                if (file.getName().startsWith("y.")) {
                    yearData.add(file);
                }
            }
        }
    }

}