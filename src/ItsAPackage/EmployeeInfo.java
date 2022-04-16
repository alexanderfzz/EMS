package ItsAPackage;

public class EmployeeInfo {
    protected int empNumber;
    protected String FirstName, LastName;
    public enum Gender {MALE, FEMALE, OTHER}
    protected Gender gender;
    public enum WorkLocation {MISSISSAUGA, OTTAWA, CHICAGO}
    protected WorkLocation workLocation;
    protected int deductionRate;

    public EmployeeInfo(int empNumber, String fn, String ln, Gender gender, WorkLocation workLocation, int deductionRate) {
        this.empNumber = empNumber;
        this.FirstName = fn;
        this.LastName = ln;
        this.gender = gender;
        this.workLocation = workLocation;
        this.deductionRate = deductionRate;
    }

    //getters and setters

    public int getEmpNumber() {
        return empNumber;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public Gender getGender() {
        return gender;
    }

    public WorkLocation getWorkLocation() {
        return workLocation;
    }

    public int getDeductionRate() {
        return deductionRate;
    }
}
