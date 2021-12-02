import java.util.ArrayList;
public class MonthReport {
    ArrayList<MonthReportElement> monthReport;
    String month;
    int year;

    MonthReport(){
        monthReport = new ArrayList<>();
    }

    MonthReport(String data) {
        String[] monthName={"Январь","Февраль","Март","Апрель","Май","Июнь","Июль","Август","Сентябрь","Октябрь","Ноябрь","Декабрь",};
        monthReport = new ArrayList<>();
        month=monthName[Integer.parseInt(data.substring(4))-1];
        year=Integer.parseInt(data.substring(0,4));
    }

    //печатаем информацию из месячного отчёта
    void printInfoForOneMonth() {
        int maxProfit=0;
        String maxProfitCategory="";
        int maxExpenses=0;
        String maxExpensesCategory="";
        for (MonthReportElement oneElement : monthReport){
            if(oneElement.isExpense){
                if(oneElement.multiplyQuantitySumOfOne()>maxExpenses){
                    maxExpenses=oneElement.multiplyQuantitySumOfOne();
                    maxExpensesCategory=oneElement.itemName;
                }
            }else{
                if(oneElement.multiplyQuantitySumOfOne()>maxProfit){
                    maxProfit=oneElement.multiplyQuantitySumOfOne();
                    maxProfitCategory= oneElement.itemName;
                }
            }
        }
        System.out.println(month);
        System.out.println("Самый прибыльный товар: "+ maxProfitCategory +" " + maxProfit);
        System.out.println("Самая большая трата: "+ maxExpensesCategory+" " + maxExpenses);
    }

    //считаем все расходы за месяц
    public int calculateExpensesInMonth() {
        int sum=0;
        for(MonthReportElement oneElement: monthReport){
            if(oneElement.isExpense){
                sum+=oneElement.sumOfOne*oneElement.quantity;
            }
        }
        return sum;
    }

    //считаем всю прибыль за месяц
    public int calculateProfitInMonth() {
        int sum=0;
        for(MonthReportElement oneElement: monthReport){
            if(!oneElement.isExpense){
                sum+=oneElement.sumOfOne*oneElement.quantity;
            }
        }
        return sum;
    }
}
