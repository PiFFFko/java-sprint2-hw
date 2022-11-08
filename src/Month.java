import javax.xml.namespace.QName;

public class Month {
    private int month;
    private int amount;
    private boolean isExpense;

    Month() {
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setExpense(boolean expense) {
        isExpense = expense;
    }

    public boolean getIsExpense() {
        return isExpense;
    }

    public String getMonthName() {
        switch (month) {
            case 1:
                return "Январь";
            case 2:
                return "Февраль";
            case 3:
                return "Март";
            case 4:
                return "Апрель";
            case 5:
                return "Май";
            case 6:
                return "Июнь";
            case 7:
                return "Июль";
            case 8:
                return "Август";
            case 9:
                return "Сентябрь";
            case 10:
                return "Октябрь";
            case 11:
                return "Ноябрь";
            case 12:
                return "Декабрь";
            default:
                return "";
        }
    }

    public int getAmount() {
        return amount;
    }
}
