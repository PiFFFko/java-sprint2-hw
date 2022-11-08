public class Item {
    String itemName;
    boolean isExpense;
    int quantity;
    int sumOfOne;
    int worth;

    public Item(String itemName, boolean expense, int quantity, int sumOfOne) {
        this.itemName = itemName;
        this.isExpense = expense;
        this.quantity = quantity;
        this.sumOfOne = sumOfOne;
        worth = quantity*sumOfOne;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setExpense(boolean expense) {
        isExpense = expense;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setSumOfOne(int sumOfOne) {
        this.sumOfOne = sumOfOne;
    }

    public boolean getIsExpense(){
        return isExpense;
    }

    public int calculateWorth(){
        return worth;
    }

}
