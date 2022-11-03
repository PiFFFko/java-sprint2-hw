public class Item {
    String itemName;
    boolean isExpense;
    int quantity;
    int sumOfOne;

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
        return this.isExpense;
    }

    public int calculateWorth(){
        return this.sumOfOne*this.quantity;
    }
    public int getQuantity() {
        return quantity;
    }

    public int getSumOfOne() {
        return sumOfOne;
    }
}
