/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Robin
 */
public class PartTimeEmployee extends EmployeeInfo {

    private double hourlyWage;
    private double hoursPerWeek;
    private double weeksPerYear;

    public PartTimeEmployee(int eNumber, String fName, String lName, double dRate, double hWage, double hWeek, double wYear) {
        super(eNumber, fName, lName, dRate);
        hourlyWage = hWage;
        hoursPerWeek = hWeek;
        weeksPerYear = wYear;
    }

    public double getHourlyWage() {
        return (hourlyWage);
    }

    public double getHoursPerWeek() {
        return (hoursPerWeek);
    }

    public double getWeeksPerYear() {
        return (weeksPerYear);
    }

    public double getSalary() {
        return (hourlyWage * hoursPerWeek * weeksPerYear);
    }

    public double getNetIncome() {
        return (getSalary() * (1 - getDeductionsRate()));
    }

}
