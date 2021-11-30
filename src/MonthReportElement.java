public class MonthReportElement {
    String itemName;
    boolean isExpense;
    int quantity;
    int sumOfOne;
    MonthReportElement(String item_name,boolean is_expense,int quantity,int sum_of_one){
        itemName=item_name;
        isExpense=is_expense;
        this.quantity=quantity;
        sumOfOne=sum_of_one;
    }
    int multiplyQuntitySumOfOne(){
        return quantity*sumOfOne;
    }
}
