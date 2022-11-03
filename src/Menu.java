import java.util.HashMap;
import java.util.Scanner;

public class Menu {
    private static final String READ_ALL_MONTHS_REPORTS_MESSAGE = " Считать все месячные отчеты.";
    private static final String READ_YEARS_REPORTS_MESSAGE = " Считать годовой отчет.";
    private static final String REPORT_RECONCILATION_MESSAGE = " Сверка отчетов.";
    private static final String WRITE_ALL_MONTHS_REPORTS_MESSAGE = " Вывести информацию о всех месячных отчетах.";
    private static final String WRITE_YEARS_REPORT_MESSAGE = " Вывести информацию и годовом отчете.";
    private static final String EXIT_MESSAGE = " Выход.";
    private static final String NO_SUCH_COMMAND_MESSAGE = "Такой команды не существует.";
    private static final String ERROR_MESSAGE = "Произошла ошибка ввода. Возможно Вы вводите символы вместо цифр.";
    private static final String INCOME_RECONCILATION_ERROR_PATTERN = "В %s месяце ошибка при сверке доходов.";
    private static final String EXPENSE_RECONCILATION_ERROR_PATTERN = "В %s месяце ошибка при сверке расходов.";
    private static final String SUCCESS_MESSAGE = "Сверка доходов и расходов в %s месяце успешно пройдена. " +
            "Ошибок не обнаружено.";
    private static final String MONTH_PATTERN = "Отчет за %s:";
    private static final String MOST_PROFIT_ITEM_PATTERN = "Самый прибыльный товар - %s. Продали на сумму - %s рублей.";
    private static final String MOST_EXPENSIVE_ITEM_PATTERN = "Потратили больше всего на - %s. Сумма - %s. рублей";
    private static final String YEAR_PATTERN = "Отчет за %s год:";
    private static final String MONTH_PROFIT_PATTERN = "Прибыль за %s месяц составила %s рублей.";
    private static final String AVERAGE_INCOME_PATTERN = "Средняя прибыль за %s год составила - %s рублей.";
    private static final String AVERAGE_EXPENSE_PATTERN = "Средний расход за %s год составил - %s рублей.";
    private static final String YEAR_FILE = "y.2021.csv";


    private static final int READ_ALL_MONTHS_REPORTS_COMMAND = 1;
    private static final int READ_YEARS_REPORTS_COMMAND = 2;
    private static final int REPORT_RECONCILATION_COMMAND = 3;
    private static final int WRITE_ALL_MONTHS_REPORTS_COMMAND = 4;
    private static final int WRITE_YEARS_REPORTS_COMMAND = 5;
    private static final int EXIT_COMMAND = 6;


    static FileReader reader = new FileReader();
    static HashMap<Integer, MonthlyReport> monthlyReports = new HashMap<>();
    static HashMap<String, YearlyReport> yearlyReports = new HashMap<>();

    private void printMenu() {
        System.out.println(READ_ALL_MONTHS_REPORTS_COMMAND + READ_ALL_MONTHS_REPORTS_MESSAGE);
        System.out.println(READ_YEARS_REPORTS_COMMAND + READ_YEARS_REPORTS_MESSAGE);
        System.out.println(REPORT_RECONCILATION_COMMAND + REPORT_RECONCILATION_MESSAGE);
        System.out.println(WRITE_ALL_MONTHS_REPORTS_COMMAND + WRITE_ALL_MONTHS_REPORTS_MESSAGE);
        System.out.println(WRITE_YEARS_REPORTS_COMMAND + WRITE_YEARS_REPORT_MESSAGE);
        System.out.println(EXIT_COMMAND + EXIT_MESSAGE);
    }

    private int getCommand() {
        Scanner scanner = new Scanner(System.in);
        //Изначально команду на выход, если вдруг будет ошибка ввода
        int command = EXIT_COMMAND;
        try {
            command = scanner.nextInt();
            //Если введут не циферки, а какие-нибудь символы отловим и выдадим ошибку
        } catch (Exception e) {
            System.err.println(ERROR_MESSAGE);
            e.printStackTrace();
        }
        return command;
    }

    private void runCommand(int command) {
        switch (command) {
            case (READ_ALL_MONTHS_REPORTS_COMMAND):
                executeReadingMonthReports();
                break;
            case (READ_YEARS_REPORTS_COMMAND):
                executeReadingYearReports();
                break;
            case (REPORT_RECONCILATION_COMMAND):
                executeReconcilation();
                break;
            case (WRITE_ALL_MONTHS_REPORTS_COMMAND):
                executeWriteMonthReports();
                break;
            case (WRITE_YEARS_REPORTS_COMMAND):
                executeWriteYearsReports();
                break;
            case (EXIT_COMMAND):
                break;
            default:
                System.out.println(NO_SUCH_COMMAND_MESSAGE);
        }
    }

    private void executeWriteYearsReports() {
        //считаем отчеты на случай если пользователь забыл это сделать.
        executeReadingMonthReports();
        executeReadingYearReports();
        for (String year: yearlyReports.keySet()){
            System.out.println(String.format(YEAR_PATTERN,year));
            YearlyReport yearReport = yearlyReports.get(year);
            int profit;
            for (int i=1; i<=monthlyReports.size(); i++){
                profit = yearReport.calculateProfitForMonth(i);
                System.out.println(String.format(MONTH_PROFIT_PATTERN, i, profit));
            }
            System.out.println(String.format(AVERAGE_INCOME_PATTERN,year, yearReport.calculateAverageIncome()));
            System.out.println(String.format(AVERAGE_EXPENSE_PATTERN,year, yearReport.calculateAverageExpense()));
        }
    }

    private void executeWriteMonthReports() {
        //считаем отчеты на случай если пользователь забыл это сделать.
        executeReadingMonthReports();
        executeReadingYearReports();
        for (MonthlyReport month : monthlyReports.values()) {
            System.out.println(String.format(MONTH_PATTERN, month.getName()));
            Item mostProfitItem = month.getMostProfitableItem();
            Item mostExpensiveItem = month.getMostExpensiveItem();
            System.out.println(String.format(MOST_PROFIT_ITEM_PATTERN, mostProfitItem.itemName,
                    mostProfitItem.calculateWorth()));
            System.out.println(String.format(MOST_EXPENSIVE_ITEM_PATTERN, mostExpensiveItem.itemName,
                    mostExpensiveItem.calculateWorth()));
        }
    }

    private void executeReadingMonthReports() {
        String report;
        int i = 1;
        while (true) {
            String path = "m.20210" + i + ".csv";
            report = reader.readFileContentsOrNull(path);
            if (report != null) {
                monthlyReports.put(i, new MonthlyReport(report, i));
                i++;
            } else break;
        }
    }

    private void executeReadingYearReports() {
        String path = YEAR_FILE;
        String report = reader.readFileContentsOrNull(path);
        if (report != null) {
            yearlyReports.put(path, new YearlyReport(report));
        }
    }

    private void executeReconcilation() {
        //считаем отчеты на случай если пользователь забыл это сделать.
        executeReadingMonthReports();
        executeReadingYearReports();
        boolean successFlag;
        int incomeYearReport;
        int incomeMonthReport;
        int expenseYearReport;
        int expenseMonthReport;
        YearlyReport year;
        MonthlyReport month;
        for (int i = 1; i <= monthlyReports.size(); i++) {
            successFlag = true;
            year = yearlyReports.get(YEAR_FILE);
            month = monthlyReports.get(i);
            incomeYearReport = year.calculateIncomeForMonth(i);
            incomeMonthReport = month.getIncome();
            expenseYearReport = year.calculateExpenseForMonth(i);
            expenseMonthReport = month.getExpense();
            if (incomeYearReport != incomeMonthReport) {
                System.out.println(String.format(INCOME_RECONCILATION_ERROR_PATTERN, i));
                successFlag = false;
            }
            if (expenseYearReport != expenseMonthReport) {
                System.out.println(String.format(EXPENSE_RECONCILATION_ERROR_PATTERN, i));
                successFlag = false;
            }
            if (successFlag) {
                System.out.println(String.format(SUCCESS_MESSAGE, i));
            }
        }
    }


    public void startMenu() {
        int command = 0;
        while (command != EXIT_COMMAND) {
            printMenu();
            command = getCommand();
            runCommand(command);
        }
    }


}


