import java.util.ArrayList;
public class MonthReport {
    ArrayList<MonthReportElement> monthReport;
    String month;
    int year;

    MonthReport(){
        monthReport = new ArrayList<>();
    }
    MonthReport(String data){
        String[] monthName={"Январь","Февраль","Март","Апрель","Май","Июнь","Июль","Август","Сентябрь","Октябрь","Ноябрь","Декабрь",};
        monthReport = new ArrayList<>();
        month=monthName[Integer.parseInt(data.substring(4))-1];
        year=Integer.parseInt(data.substring(0,4));
    }
    void printInfoForOneMonth(){
        int maxProfit=0;
        String maxProfitCategory="";
        int maxExpenses=0;
        String maxExpensesCategory="";
        for (MonthReportElement oneElement : monthReport){
            if(oneElement.isExpense){
                if(oneElement.multiplyQuntitySumOfOne()>maxExpenses){
                    maxExpenses=oneElement.multiplyQuntitySumOfOne();
                    maxExpensesCategory=oneElement.itemName;
                }
            }else{
                if(oneElement.multiplyQuntitySumOfOne()>maxProfit){
                    maxProfit=oneElement.multiplyQuntitySumOfOne();
                    maxProfitCategory= oneElement.itemName;
                }
            }
        }
        System.out.println(month);
        System.out.println("Самый прибыльный товар: "+ maxProfitCategory);
        System.out.println("Самая большая трата: "+ maxExpensesCategory);
    }
    public int calculateExpensesInMonth(){
        int sum=0;
        for(MonthReportElement oneElement: monthReport){
            if(oneElement.isExpense){
                sum+=oneElement.sumOfOne*oneElement.quantity;
            }
        }
        return sum;
    }
    public int calculateProfitInMonth(){
        int sum=0;
        for(MonthReportElement oneElement: monthReport){
            if(!oneElement.isExpense){
                sum+=oneElement.sumOfOne*oneElement.quantity;
            }
        }
        return sum;
    }
}
