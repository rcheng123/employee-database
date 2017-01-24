/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Robin
 */
//EmployeeInfo.java by Robin Cheng
//List of employee information, getter and setter methods
public class EmployeeInfo {
	// Attributes
	private int empNum;
	private String firstName;
	private String lastName;
	private double deductionsRate;

	// Constructors for employee numbers, names and deduction rate
	public EmployeeInfo(int eNumber, String fName, String lName, double deduct) {
		empNum = eNumber;
		firstName = fName;
		lastName = lName;
		deductionsRate = deduct;
	}

	// Getter method to return employee number
	public int getEmployeeNumber() {
		return (empNum);
	}

	// Getter method to return first name
	public String getFirstName() {
		return (firstName);
	}

	// Getter method to return last name
	public String getLastName() {
		return (lastName);
	}

	// Getter method to return deduction rate
	public double getDeductionsRate() {
		return (deductionsRate);
	}

} // End of Class EmployeeInfo
