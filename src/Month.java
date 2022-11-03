import javax.xml.namespace.QName;

public class Month {
    private int month;
    private String name;
    private int amount;
    private boolean isExpense;

    Month(){

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

    public String getName(){
        return this.getName();
    }
    public boolean getIsExpense(){
        return this.isExpense;
    }


    public int getAmount() {
        return amount;
    }
}
