import java.util.ArrayList;

public class YearReport {
    ArrayList<YearReportElement> yearReport;//список всех месяцев

    YearReport() {
        yearReport=new ArrayList<>();
    }

    //содержит ли годовой отчёт элемент с месяцем monthNum
   public boolean containMonth(int monthNum) {
        for (YearReportElement oneElement: yearReport) {
            if (oneElement.monthNum==monthNum) {
                return true;
            }
        }
        return false;
    }

    //возвращаем элемент списка по номеру месяца
    private YearReportElement getMonthReportElement(int monthNum) {
        for (YearReportElement oneElement: yearReport) {
            if (oneElement.monthNum==monthNum) {
                return oneElement;
            }
        }
        return null;
    }

    //добавляем данные в существующий элемент отчёта
    public void addDataToMonth(int monthNum, int amountValue, boolean isExpenseValue) {
        YearReportElement elementToChange=this.getMonthReportElement(monthNum);
        elementToChange.addDataToMonth(amountValue, isExpenseValue);
    }

    //считаем средние траты в месяц
    private double findAverageExpenses() {
        int sumExpenses =0;
        for (YearReportElement oneElement : yearReport) {
            sumExpenses+=oneElement.expenses;
        }
        return (double)sumExpenses/yearReport.size();
    }

    //считаем среднюю прибыль в месяц
    private double findAverageProfit() {
        int sumProfit =0;
        for (YearReportElement oneElement : yearReport) {
            sumProfit+=oneElement.profit;
        }
        return (double) sumProfit/yearReport.size();
    }

    //выводим информацию годового отчёта
    public void printInfoForOneYear(String year) {
        System.out.println(year+" год:");
        for (YearReportElement oneElement: yearReport) {
            oneElement.PrintProfitOneMonth();
        }
        System.out.println("Средний расход за год составил: "+findAverageExpenses());
        System.out.println("Средний доход за год составил: "+findAverageProfit());
    }
}
