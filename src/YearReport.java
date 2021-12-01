import java.util.ArrayList;

public class YearReport {
    ArrayList<YearReportElement> yearReport;//список всех месяцев
    YearReport(){
        yearReport=new ArrayList<>();
    }
    //содержит ли годовой отчёт элемент с месяцем monthNum
   public boolean containMonth(int monthNum){
        for(YearReportElement oneElement: yearReport){
            if(oneElement.monthNum==monthNum){
                return true;
            }
        }
        return false;
    }
//возвращаем элемент списка по номеру месяца
    YearReportElement getMonthReportElement(int monthNum){
        for(YearReportElement oneElement: yearReport){
            if(oneElement.monthNum==monthNum){
                return oneElement;
            }
        }
        return null;
    }
    //добавляем данные в существующий элемент отчёта
    public void addDataToMonth(int monthNum, int ammountValue,boolean isExpenseValue) {
        YearReportElement elementToChange=this.getMonthReportElement(monthNum);
        elementToChange.addDataToMonth(ammountValue,isExpenseValue);
    }
    //считаем средние траты в месяц
    double findAvarageExpenses(){
        int sumExpenses =0;
        for(YearReportElement oneElement : yearReport){
            sumExpenses+=oneElement.expenses;
        }
        return sumExpenses/yearReport.size();
    }
    //считаем среднюю прибыль в месяц
    double findAvarageProfit(){
        int sumProfit =0;
        for(YearReportElement oneElement : yearReport){
            sumProfit+=oneElement.profit;
        }
        return sumProfit/yearReport.size();
    }
//выводим информацию годового отчёта
    public void printInfoForOneYear(String year) {
        System.out.println(year+" год:");
        for(YearReportElement oneElement: yearReport){
            oneElement.PrintProfitOneMonth();
        }
        System.out.println("Средний расход за год составил: "+findAvarageExpenses());
        System.out.println("Средний доход за год составил: "+findAvarageProfit());
    }
}
