/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Robin
 */
//Hash Table (open hashing/close addressing)
//By Robin Cheng
import java.util.*;

public class MyHT {

    // Buckets is an array of ArrayList. Each item in an ArrayList is an
    // EmployeeInfo object.
    private ArrayList<EmployeeInfo>[] buckets;


    // Constructor
    public MyHT(int howManyBuckets) {

        buckets = new ArrayList[howManyBuckets];

        for (int i = 0; i < howManyBuckets; i++) {
            buckets[i] = new ArrayList();
        }

    }

    // Get the hashtable
    public ArrayList<EmployeeInfo>[] getArrayList() {
        return (buckets);
    }

    // Get the array list from specific bucket
    public ArrayList<EmployeeInfo> getBuckets(int bucketNumber) {
        return (buckets[bucketNumber]);
    }

    // Clear array list
    public void clearArrayList() {
        for (int i = 0; i < buckets.length; i++) {
            buckets[i].clear();
        }
    }

    // Method for determining which bucket the object belongs to
    public int calcBucket(int empNum) {
        return (empNum % buckets.length);
    }

    // Method for adding an employee
    public boolean addToTable(EmployeeInfo theEmployee) {
        // return true if adding is successful
        return (buckets[calcBucket(theEmployee.getEmployeeNumber())].add(theEmployee));
    }

    // Method for searching the position of the employee within certain bucket
    public int searchTable(int empNum) {
        for (int i = 0; i < buckets[calcBucket(empNum)].size(); i++) {
            if (buckets[calcBucket(empNum)].get(i).getEmployeeNumber() == empNum) {
                return (i); //Return specific position
            }
        }
        // If such employee does not exist then return -1
        return (-1);
    }

    // Method for removing an employee
    public EmployeeInfo removeFromTable(int empNum) {
        if (searchTable(empNum) == -1) {
            return (null); // return null if doesnt exist
        }
        // remove the employee from array list and return the employee
        return (buckets[calcBucket(empNum)]).remove(searchTable(empNum));
    }

/*    public String[][] searchDisplayEmployee(int empNum) {

        if (searchTable(empNum) != -1) {
            for (int x = 0;; x++) {
                if (Objects.equals(employeeListArray2D[x][0], "") || employeeListArray2D[x][0] == null) {
                    break;
                }
                for (int y = 0; y <= 4; y++) {
                    employeeListArray2D[x][y] = "";
                }
            }
            employeeListArray2D[0][0] = (Integer.toString(buckets[calcBucket(empNum)].get(searchTable(empNum)).getEmployeeNumber()));
            employeeListArray2D[0][1] = buckets[calcBucket(empNum)].get(searchTable(empNum)).getFirstName();
            employeeListArray2D[0][2] = buckets[calcBucket(empNum)].get(searchTable(empNum)).getLastName();
            if (buckets[calcBucket(empNum)].get(searchTable(empNum)) instanceof FullTimeEmployee) {
                employeeListArray2D[0][3] = "Full-Time";
                employeeListArray2D[0][4] = Double.toString(((FullTimeEmployee) buckets[calcBucket(empNum)].get(searchTable(empNum))).getNetIncome());

            } else if (buckets[calcBucket(empNum)].get(searchTable(empNum)) instanceof PartTimeEmployee) {
                employeeListArray2D[0][3] = "Part-Time";
                employeeListArray2D[0][4] = Double.toString(((PartTimeEmployee) buckets[calcBucket(empNum)].get(searchTable(empNum))).getNetIncome());

            }
        }
        return (employeeListArray2D);
    }
*/
    
    //Construct an arraylist for searching employee purpose
    public ArrayList<EmployeeInfo>[] searchAndDisplayEmployee(int empNum) {

        ArrayList<EmployeeInfo>[] searchResult = new ArrayList[1];
        searchResult[0] = new ArrayList();     
                
        searchResult[0].clear();

        if (searchTable(empNum) != -1) {

            searchResult[0].add(buckets[calcBucket(empNum)].get(searchTable(empNum)));
        }
        return (searchResult);
    }
/*
    public String[][] displayTableModel() {

        for (int x = 0;; x++) {
            if (Objects.equals(employeeListArray2D[x][0], "") || employeeListArray2D[x][0] == null) {
                break;
            }
            for (int y = 0; y <= 4; y++) {
                employeeListArray2D[x][y] = "";
            }
        }
        int aaa = 0;

        for (int i = 0; i < buckets.length; i++) {
            for (int j = 0; j < buckets[i].size(); j++) {
                employeeListArray2D[aaa][0] = Integer.toString(buckets[i].get(j).getEmployeeNumber());
                employeeListArray2D[aaa][1] = buckets[i].get(j).getFirstName();
                employeeListArray2D[aaa][2] = buckets[i].get(j).getLastName();
                if ((buckets[i].get(j)) instanceof FullTimeEmployee) {
                    employeeListArray2D[aaa][3] = "Full-Time";
                    employeeListArray2D[aaa][4] = Double.toString(((FullTimeEmployee) buckets[i].get(j)).getNetIncome());
                } else if ((buckets[i].get(j)) instanceof PartTimeEmployee) {
                    employeeListArray2D[aaa][3] = "Part-Time";
                    employeeListArray2D[aaa][4] = Double.toString(((PartTimeEmployee) buckets[i].get(j)).getNetIncome());
                }

                aaa++;
            }
        }

        return (employeeListArray2D);

    }
*/
}
