import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Menu {
    private static final String NO_SUCH_COMMAND_MESSAGE = "Такой команды не существует.";
    private static final String ERROR_MESSAGE = "Произошла ошибка ввода. Возможно Вы вводите символы вместо цифр.";
    private static final String INCOME_RECONCILATION_ERROR_PATTERN = "В %s месяце ошибка при сверке доходов.";
    private static final String EXPENSE_RECONCILATION_ERROR_PATTERN = "В %s месяце ошибка при сверке расходов.";
    private static final String SUCCESS_MESSAGE = "Сверка доходов и расходов пройдена без ошибок.";
    private static final String MONTH_PATTERN = "Отчет за %s:";
    private static final String MOST_PROFIT_ITEM_PATTERN = "Самый прибыльный товар - %s. Продали на сумму - %s рублей.";
    private static final String MOST_EXPENSIVE_ITEM_PATTERN = "Потратили больше всего на - %s. Сумма - %s. рублей";
    private static final String YEAR_PATTERN = "Отчет за %s год:";
    private static final String MONTH_PROFIT_PATTERN = "Прибыль за %s месяц составила %s рублей.";
    private static final String AVERAGE_INCOME_PATTERN = "Средняя прибыль за %s год составила - %s рублей.";
    private static final String AVERAGE_EXPENSE_PATTERN = "Средний расход за %s год составил - %s рублей.";
    private static final String YEAR_FILE = "y.2021.csv";
    private static final String YEAR = "2021";
    private static final String READING_ERROR_MESSAGE = "Отчеты не были считаны.";

    public static String[] monthNames = {"Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь"
            , "Ноябрь", "Декабрь"};


    public enum Command {
        READ_ALL_MONTHS_REPORTS_COMMAND(1, " Считать все месячные отчеты."),
        READ_YEARS_REPORTS_COMMAND(2, " Считать годовой отчет."),
        REPORT_RECONCILATION_COMMAND(3, " Сверка отчетов."),
        WRITE_ALL_MONTHS_REPORTS_COMMAND(4, " Вывести информацию о всех месячных отчетах."),
        WRITE_YEARS_REPORTS_COMMAND(5, " Вывести информацию и годовом отчете."),
        EXIT_COMMAND(6, " Выход.");
        public final int number;
        public String message;

        Command(int number, String message) {
            this.number = number;
            this.message = message;
        }

        private static final Map<Integer, Command> map;

        static {
            map = new HashMap<Integer, Command>();
            for (Command v : Command.values()) {
                map.put(v.number, v);
            }
        }

        public static Command findByKey(int i) {
            return map.get(i);
        }

    }

    FileReader reader = new FileReader();
    static HashMap<Integer, MonthlyReport> monthlyReports = new HashMap<>();
    static YearlyReport yearlyReport;

    private void printMenu() {
        System.out.println(Command.READ_ALL_MONTHS_REPORTS_COMMAND.number + Command.READ_ALL_MONTHS_REPORTS_COMMAND.message);
        System.out.println(Command.READ_YEARS_REPORTS_COMMAND.number + Command.READ_YEARS_REPORTS_COMMAND.message);
        System.out.println(Command.REPORT_RECONCILATION_COMMAND.number + Command.REPORT_RECONCILATION_COMMAND.message);
        System.out.println(Command.WRITE_ALL_MONTHS_REPORTS_COMMAND.number + Command.WRITE_ALL_MONTHS_REPORTS_COMMAND.message);
        System.out.println(Command.WRITE_YEARS_REPORTS_COMMAND.number + Command.WRITE_YEARS_REPORTS_COMMAND.message);
        System.out.println(Command.EXIT_COMMAND.number + Command.EXIT_COMMAND.message);
    }

    private int getCommand() {
        Scanner scanner = new Scanner(System.in);
        int command = -1;
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
        switch (Command.findByKey(command)) {
            case READ_ALL_MONTHS_REPORTS_COMMAND:
                executeReadingMonthReports();
                break;
            case READ_YEARS_REPORTS_COMMAND:
                executeReadingYearReports();
                break;
            case REPORT_RECONCILATION_COMMAND:
                executeReconcilation();
                break;
            case WRITE_ALL_MONTHS_REPORTS_COMMAND:
                executeWriteMonthReports();
                break;
            case WRITE_YEARS_REPORTS_COMMAND:
                executeWriteYearsReports();
                break;
            case EXIT_COMMAND:
                break;
            default:
                System.out.println(NO_SUCH_COMMAND_MESSAGE);
        }
    }

    private void executeWriteYearsReports() {
        //считаем отчеты на случай если пользователь забыл это сделать.
        if ((yearlyReport) != null && (monthlyReports.size() != 0)) {
            System.out.println(YEAR);
            int profit;
            for (int i = 1; i <= monthlyReports.size(); i++) {
                profit = yearlyReport.calculateProfitForMonth(i);
                System.out.println(String.format(MONTH_PROFIT_PATTERN, monthNames[i], profit));
            }
            System.out.println(String.format(AVERAGE_INCOME_PATTERN, YEAR, yearlyReport.calculateAverageIncome()));
            System.out.println(String.format(AVERAGE_EXPENSE_PATTERN, YEAR, yearlyReport.calculateAverageExpense()));
        } else {
            System.out.println(READING_ERROR_MESSAGE);
        }
    }

    private void executeWriteMonthReports() {
        if (monthlyReports.size() != 0) {
            for (MonthlyReport month : monthlyReports.values()) {
                System.out.println(String.format(MONTH_PATTERN, month.getName()));
                Item mostProfitItem = month.getMostProfitableItem();
                Item mostExpensiveItem = month.getMostExpensiveItem();
                System.out.println(String.format(MOST_PROFIT_ITEM_PATTERN, mostProfitItem.itemName,
                        mostProfitItem.calculateWorth()));
                System.out.println(String.format(MOST_EXPENSIVE_ITEM_PATTERN, mostExpensiveItem.itemName,
                        mostExpensiveItem.calculateWorth()));
            }
        } else {
            System.out.println(READING_ERROR_MESSAGE);
        }
    }

    private void executeReadingMonthReports() {
        String report;
        for (int i = 1; i <= 12; i++) {
            String path = "m.20210" + i + ".csv";
            report = reader.readFileContentsOrNull(path);
            if (report != null) {
                monthlyReports.put(i, new MonthlyReport(report, i));
            } else break;
        }
    }

    private void executeReadingYearReports() {
        String path = YEAR_FILE;
        String report = reader.readFileContentsOrNull(path);
        if (report != null) {
            yearlyReport = new YearlyReport(report);
        }
    }

    private void executeReconcilation() {
        if ((yearlyReport) != null && (monthlyReports.size() != 0)) {
            boolean successFlag;
            int incomeYearReport;
            int incomeMonthReport;
            int expenseYearReport;
            int expenseMonthReport;
            successFlag = true;
            for (int i = 1; i <= monthlyReports.size(); i++) {
                MonthlyReport month = monthlyReports.get(i);
                incomeYearReport = yearlyReport.calculateIncomeForMonth(i);
                incomeMonthReport = month.getIncome();
                expenseYearReport = yearlyReport.calculateExpenseForMonth(i);
                expenseMonthReport = month.getExpense();
                String monthName = yearlyReport.months.get(i).getMonthName();
                if (incomeYearReport != incomeMonthReport) {
                    System.out.println(String.format(INCOME_RECONCILATION_ERROR_PATTERN, monthName));
                    successFlag = false;
                }
                if (expenseYearReport != expenseMonthReport) {
                    System.out.println(String.format(EXPENSE_RECONCILATION_ERROR_PATTERN, monthName));
                    successFlag = false;
                }
            }
            if (successFlag) {
                System.out.println(String.format(SUCCESS_MESSAGE));
            }
        } else {
            System.out.println(READING_ERROR_MESSAGE);
        }
    }


    public void startMenu() {
        int command = 0;
        while (command != -1) {
            printMenu();
            command = getCommand();
            runCommand(command);
        }
    }


}


