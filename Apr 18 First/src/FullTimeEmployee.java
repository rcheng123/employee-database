/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Robin
 */
public class FullTimeEmployee extends EmployeeInfo {

    private double salary;

    public FullTimeEmployee(int eNumber, String fName, String lName, double dRate, double s) {
        super(eNumber, fName, lName, dRate);
        salary = s;
    }

    public double getSalary() {
        return (salary);
    }

    public double getNetIncome() {
        return (salary * (1 - getDeductionsRate()));
    }

}
