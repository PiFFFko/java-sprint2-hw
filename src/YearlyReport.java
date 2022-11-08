import java.util.ArrayList;
import java.util.Arrays;

public class YearlyReport {

    ArrayList<Month> months;
    ArrayList<Month> incomeMonths;
    ArrayList<Month> expenseMonths;
    YearlyReport(String Data){
        //преобразовываем массив строк в список, чтобы убрать заголовочную строку
        ArrayList<String> lines = new ArrayList<>(Arrays.asList(Data.split(System.lineSeparator())));
        lines.remove(0);
        months = new ArrayList<>();
        for (String line: lines){
            Month month = new Month();
            String[] lineContents = line.split(",");
            month.setMonth(Integer.parseInt(lineContents[0]));
            month.setAmount(Integer.parseInt(lineContents[1]));
            month.setExpense(Boolean.parseBoolean(lineContents[2]));
            months.add(month);
        }
        divideForIncomeAndExpense();
    }

    public int calculateIncomeForMonth(int month){
        int income = incomeMonths.get(month-1).getAmount();
        return income;
    }

    public int calculateAverageIncome(){
        int sum=0;
        for (Month month: incomeMonths){
            sum+=month.getAmount();
        }
        return sum/incomeMonths.size();
    }

    public int calculateAverageExpense(){
        int sum=0;
        for (Month month: expenseMonths){
            sum+=month.getAmount();
        }
        return sum/expenseMonths.size();
    }

    public int calculateExpenseForMonth(int month){
        int expense = expenseMonths.get(month-1).getAmount();
        return expense;
    }

    public int calculateProfitForMonth(int month){
        int profit;
        profit = calculateIncomeForMonth(month) - calculateExpenseForMonth(month);
        return profit;
    }

    //разделить по доходам и расходам, чтобы легче считать
    private void divideForIncomeAndExpense(){
        expenseMonths = new ArrayList<>();
        incomeMonths = new ArrayList<>();
        for (Month month: months){
            if (month.getIsExpense()){
                expenseMonths.add(month);
            } else incomeMonths.add(month);
        }
    }



}
