package org.example.databaseQueryService.sqlAnswreClass;


public class FindLongestProject {
    private String name;
    private int monthCount;

    public FindLongestProject(String name, int monthCount) {
        this.name = name;
        this.monthCount = monthCount;
    }

    public String getName() {
        return name;
    }

    public int getMonthCount() {
        return monthCount;
    }
}
