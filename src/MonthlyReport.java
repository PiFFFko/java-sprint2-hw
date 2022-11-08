import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class MonthlyReport {

    private ArrayList<Item> incomeAndExpenses;
    private ArrayList<Item> incomeItems;
    private ArrayList<Item> expenseItems;
    private String name;

    MonthlyReport(String data, int i) {
        //преобразовываем массив строк в список, чтобы убрать заголовочную строку
        ArrayList<String> lines = new ArrayList<>(Arrays.asList(data.split(System.lineSeparator())));
        lines.remove(0);
        incomeAndExpenses = new ArrayList<>();
        for (String line : lines) {
            String[] lineContents = line.split(",");
            String itemName = lineContents[0];
            boolean expense =  Boolean.parseBoolean(lineContents[1]);
            int quantity = Integer.parseInt(lineContents[2]);
            int sumOfOne = Integer.parseInt(lineContents[3]);
            Item item = new Item(itemName,expense,quantity,sumOfOne);
            name = Menu.monthNames[i];
            incomeAndExpenses.add(item);
        }
        divideForIncomeAndExpenses();
    }

    //Разделим на два массива доходов и расходов для удобства дальнейших расчетов
    private void divideForIncomeAndExpenses() {
        incomeItems = new ArrayList<>();
        expenseItems = new ArrayList<>();
        for (Item item : incomeAndExpenses) {
            if (item.getIsExpense()) {
                expenseItems.add(item);
            } else incomeItems.add(item);
        }
    }

    public Item getMostProfitableItem() {
        int max = 0;
        int worth;
        Item resultItem = null;
        for (Item item : incomeItems) {
            worth = item.calculateWorth();
            if (worth > max) {
                max = worth;
                resultItem = item;
            }
        }
        return resultItem;
    }

    public Item getMostExpensiveItem(){
        int max = 0;
        int worth;
        Item resultItem = null;
        for (Item item : expenseItems) {
            worth = item.calculateWorth();
            if (worth > max) {
                max = worth;
                resultItem = item;
            }
        }
        return resultItem;
    }

    public int getIncome() {
        int income = 0;
        for (Item item : incomeItems) {
            income += item.worth;
        }
        return income;
    }

    public int getExpense() {
        int expense = 0;
        for (Item item : expenseItems) {
            expense += item.worth;
        }
        return expense;
    }

    public String getName(){
        return name;
    }

}
