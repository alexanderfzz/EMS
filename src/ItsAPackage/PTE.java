package ItsAPackage;

public class PTE extends EmployeeInfo{
    private double hourlyWage;
    private int hoursPerWeek;
    private int weeksPerYear;

    public PTE(int empNumber, String fn, String ln, Gender gender, WorkLocation workLocation, int deductionRate, double HW, int HPW, int WPY) {
        super(empNumber, fn, ln, gender, workLocation, deductionRate);
        this.hourlyWage = HW;
        this.hoursPerWeek = HPW;
        this.weeksPerYear = WPY;
    }

    public double calcAnnualGrossIncome() {return this.hourlyWage * this.hoursPerWeek * this.weeksPerYear;}
    public double calcAnnualNetIncome() {return this.calcAnnualGrossIncome() * (1-(double)deductionRate/100);}

    //getters and setter

    public double getHourlyWage() {
        return hourlyWage;
    }

    public int getHoursPerWeek() {
        return hoursPerWeek;
    }

    public int getWeeksPerYear() {
        return weeksPerYear;
    }
}
