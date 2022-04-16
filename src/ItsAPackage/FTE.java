package ItsAPackage;

public class FTE extends EmployeeInfo {
    private double yearlySalary;

    public FTE(int empNumber, String fn, String ln, Gender gender, WorkLocation workLocation, int deductionRate, double yearlySalary) {
        super(empNumber, fn, ln, gender, workLocation, deductionRate);
        this.yearlySalary = yearlySalary;
    }

    public double calcAnnualGrossIncome() {return this.yearlySalary;}
    public double calcAnnualNetIncome() {return this.yearlySalary * (1-(double)deductionRate/100);}

    //getters and setters

    public double getYearlySalary() {
        return this.yearlySalary;
    }

}

