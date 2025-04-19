import java.util.*;

public class Employee {

    // Should these be private?
    Person personalDetails;
    int employeeNum;
    int yearEmployed;
    double salaryMonth;
    double taxPercent;

    java.util.GregorianCalendar calendar = new java.util.GregorianCalendar();


    public Employee(Person personalDetails, int employeeNum, int yearEmployed, double salaryMonth, double taxPercent) {
        this.personalDetails = personalDetails;
        this.employeeNum = employeeNum;
        this.yearEmployed = yearEmployed;
        this.salaryMonth = salaryMonth;
        this.taxPercent = taxPercent / 100;
    }

    // Getters
    public int getEmployeeNum() {
        return employeeNum;
    }

    public int getYearEmployed() {
        return yearEmployed;
    }

    public double getSalaryMonth() {
        return salaryMonth;
    }

    public double getTaxPercent() {
        return taxPercent;
    }

    // Setters

    public void setSalaryMonth(double salaryMonth) {
        this.salaryMonth = salaryMonth;
    }

    public void setTaxPercent(double taxPercent) {
        this.taxPercent = taxPercent / 100;
    }

    // Methods for obtaining relevant information


    /**
     * Advance tax (tax in advance, i.e. "forskuddsskatt") can be skipped for June, if the employee want to.
     * It's also customary to only deduct half tax in december, again if the employee want to.
     * This method reflects the elective reduced advance tax, and gives a total advance tax paid per year.
     * Half december + june = 1,5 months "less" tax per year. I.e. effectively 10,5 months of tax.
     * @return total tax deducted in a fiscal year
     */
    public double taxDeductedYear(){
        return (this.salaryMonth * 10.5) * (this.taxPercent);
    }

    /**
     * Calculate yearly salary, then deduct tax paid per year (10,5 months).
     * @return Salary per year, before tax.
     */
    public double netSalaryYear() {
        return (this.salaryMonth * 12) - taxDeductedYear();
    }

    /**
     * Calculate how many years employee has been in company.
     * Using current year minus year employed.
     * @return Number of years in company.
     */
    public int yearsOfService() {
        java.util.GregorianCalendar calendar = new java.util.GregorianCalendar();
        int year = calendar.get(Calendar.YEAR);
        return (year - yearEmployed);
    }

    /**
     * Calculate age of person.
     * @return age of person.
     */
    public int getAge() {
        java.util.GregorianCalendar calendar = new java.util.GregorianCalendar();
        int year = calendar.get(Calendar.YEAR);
        return year - personalDetails.getDateOfBirth();
    }
    
    /**
     * Method to check if an employee has been employed for more than X years.
     * checkMoreThan is the number of years ("x") which we want to check if the employee has been
     * employed more than.
     * It makes sense to check if the number X is more than *or equal to*, but the assignment
     * says "more than a given number of years".
     * Returns true or false, accordingly.
     * @param checkMoreThan
     * @param employee
     * @return true or false. True if years in company is more than the number you check against.
     */
    public boolean employedMoreThanXYears(int checkMoreThan){
        return (checkMoreThan < yearsOfService());
        }

    public String getFullName(){
        return this.personalDetails.getLastName() + ", " + this.personalDetails.getFirstName();
    }

    /**
     * Sending data into string, to be called on main method menu.
     * @return toString prints for data.
     */
    @Override
    public String toString() {
        return "Employee: " + this.getFullName() + "\n" +
        "Employee ID: " + this.employeeNum + "\n" +
        "Date of birth: " + this.personalDetails.getDateOfBirth() + "\n" +
        "Age: " + getAge() + "\n" +
        "Employed since: " + this.yearEmployed + "\n" +
        "Current year: " + calendar.get(Calendar.YEAR) + "\n" +
        "Years of service in company: " + this.yearsOfService() + "\n" +
        "Gross salary per month: " + this.getSalaryMonth() + "\n" +
        "Gross salary per year: " + (this.getSalaryMonth() * 12) + "\n" +
        "Tax paid per year: " + this.taxDeductedYear() + " (Tax percent: " + (this.getTaxPercent() * 100) + ")" + "\n" +
        "Net salary per year: " + this.netSalaryYear() + "\n";
        }
}


