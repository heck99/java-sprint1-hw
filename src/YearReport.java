import java.util.ArrayList;

public class YearReport {
    ArrayList<YearReportElement> yearReport;
    YearReport(){
        yearReport=new ArrayList<>();
    }
   public boolean containMonth(int monthNum){
        for(YearReportElement oneElement: yearReport){
            if(oneElement.monthNum==monthNum){
                return true;
            }
        }
        return false;
    }

    YearReportElement getMonthReportElement(int monthNum){
        for(YearReportElement oneElement: yearReport){
            if(oneElement.monthNum==monthNum){
                return oneElement;
            }
        }
        return null;
    }
    public void addDataToMonth(int monthNum, int ammountValue,boolean isExpenseValue) {
        YearReportElement elementToChange=this.getMonthReportElement(monthNum);
        elementToChange.addDataToMonth(ammountValue,isExpenseValue);
    }
    double findAvarageExpenses(){
        int sumExpenses =0;
        for(YearReportElement oneElement : yearReport){
            sumExpenses+=oneElement.expenses;
        }
        return sumExpenses/yearReport.size();
    }

    double findAvarageProfit(){
        int sumProfit =0;
        for(YearReportElement oneElement : yearReport){
            sumProfit+=oneElement.profit;
        }
        return sumProfit/yearReport.size();
    }

    public void printInfoForOneYear(String year) {
        System.out.println(year+" год:");
        for(YearReportElement oneElement: yearReport){
            oneElement.PrintProfitOneMonth();
        }
        System.out.println("Средний расход за год составил: "+findAvarageExpenses());
        System.out.println("Средний доход за год составил: "+findAvarageProfit());
    }
}
