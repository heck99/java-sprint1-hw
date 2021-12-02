public class YearReportElement {
int monthNum;
int expenses=0;
int profit=0;

    YearReportElement(int month,int amount,boolean is_Expense) {
        String[] monthName={"Январь","Февраль","Март","Апрель","Май","Июнь","Июль","Август","Сентябрь","Октябрь","Ноябрь","Декабрь"};
        this.monthNum=month;;
        if (is_Expense) {
            expenses=amount;
        } else {
            profit=amount;
        }
    }

    //добавляем трату или прибыль
    public void addDataToMonth(int amountValue, boolean isExpenseValue) {
        if (isExpenseValue) {
            expenses=amountValue;
        } else {
            profit=amountValue;
        }
    }

    //печатаем прибыль за месяц
    public void PrintProfitOneMonth() {
        String[] monthName={"Январь","Февраль","Март","Апрель","Май","Июнь","Июль","Август","Сентябрь","Октябрь","Ноябрь","Декабрь"};
        System.out.println("Прибыль в месяце "+ monthName[monthNum-1]+" составила: " + (profit-expenses));
    }
}
