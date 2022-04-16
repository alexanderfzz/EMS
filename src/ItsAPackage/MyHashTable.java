package ItsAPackage;

import java.util.*;

public class MyHashTable {
    private ArrayList<EmployeeInfo>[] bucket;
    private int bucketSize;
    private int tableSize;

    public MyHashTable(int size) {
        bucket = new ArrayList[size];
        if (size==0)
            this.bucketSize = 10;
        else
            this.bucketSize = Math.abs(size);
        for (int i=0; i<size; i++)
            bucket[i] = new ArrayList<>();
    }

    public int calcBucket(int keyValue) {return keyValue % this.bucketSize;}

    public void addToTable(EmployeeInfo thing) {
        if (thing!=null) {
            bucket[calcBucket(thing.getEmpNumber())].add(thing);
            tableSize++;
        }
    }

    public EmployeeInfo removeFromTable(int number) {
        for (EmployeeInfo i : bucket[calcBucket(number)])
            if (i.getEmpNumber() == number) {
                bucket[calcBucket(number)].remove(i);
                tableSize--;
                return i;
            }
        return null;
    }

    public EmployeeInfo getFromTable(int number) {
        for (EmployeeInfo i : bucket[calcBucket(number)])
            if (i.getEmpNumber() == number)
                return i;
        return null;
    }

    public void displayTable() {
        for (int i=0; i<this.bucketSize; i++) {
            System.out.printf("bucket %d:%n", i);
            if (bucket[i].size()==0) {
                System.out.println("\tNone");
                continue;
            }
            for (EmployeeInfo j : bucket[i])
                System.out.println("\t"+j.getEmpNumber() +" "+ j.getFirstName() +" "+ j.getLastName());
        }
        System.out.println();
    }

    public int getTableSize() {
        return tableSize;
    }

    public EmployeeInfo[] returnAll() {
        EmployeeInfo[] arr = new EmployeeInfo[tableSize];
        int index = 0;
        for (ArrayList<EmployeeInfo> i : bucket)
            for (EmployeeInfo j : i) {
                arr[index] = j;
                index++;
            }
        return arr;
    }

}
