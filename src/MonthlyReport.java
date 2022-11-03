import javax.lang.model.type.ArrayType;
import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Arrays;

public class MonthlyReport {

    private ArrayList<Item> incomeAndExpenses;
    private ArrayList<Item> incomeItems;
    private ArrayList<Item> expenseItems;
    private String name;

    MonthlyReport(String Data, int i) {
        //преобразовываем массив строк в список, чтобы убрать заголовочную строку
        ArrayList<String> lines = new ArrayList<>(Arrays.asList(Data.split(System.lineSeparator())));
        lines.remove(0);
        incomeAndExpenses = new ArrayList<>();
        for (String line : lines) {
            Item item = new Item();
            String[] lineContents = line.split(",");
            item.setItemName(lineContents[0]);
            item.setExpense(Boolean.parseBoolean(lineContents[1]));
            item.setQuantity(Integer.parseInt(lineContents[2]));
            item.setSumOfOne(Integer.parseInt(lineContents[3]));
            setName(i);
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
            income += item.sumOfOne * item.quantity;
        }
        return income;
    }

    public int getExpense() {
        int expense = 0;
        for (Item item : expenseItems) {
            expense += item.sumOfOne * item.quantity;
        }
        return expense;
    }

    public String getName(){
        return this.name;
    }
    private void setName(int number) {
        switch (number) {
            case 1:
                this.name = "Январь";
                break;
            case 2:
                this.name = "Февраль";
                break;
            case 3:
                this.name = "Март";
                break;
            case 4:
                this.name = "Апрель";
                break;
            case 5:
                this.name = "Май";
                break;
            case 6:
                this.name = "Июнь";
                break;
            case 7:
                this.name = "Июль";
                break;
            case 8:
                this.name = "Август";
                break;
            case 9:
                this.name = "Сентябрь";
                break;
            case 10:
                this.name = "Октябрь";
                break;
            case 11:
                this.name = "Ноябрь";
                break;
            case 12:
                this.name = "Декабрь";
                break;
        }
    }
}
