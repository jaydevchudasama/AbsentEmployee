package com.david.timaandattandace.model;

public class EmpBean {

    int id;
    int empid;
    String from_time;
    String to_time;

    public String getFrom_time() {
        return from_time;
    }

    public String getTo_time() {
        return to_time;
    }

    public String getEmplname() {
        return emplname;
    }

    public void setEmplname(String emplname) {
        this.emplname = emplname;
    }

    String emplname;

    public String getDate1() {
        return date1;
    }

    public void setDate1(String date1) {
        this.date1 = date1;
    }

    public String getTime1() {
        return time1;
    }

    public void setTime1(String time1) {
        this.time1 = time1;
    }

    int vacation_hours;
    int sick_days;
    int holydays;
    int shifted;
    int type1;
    String name, pin;
    boolean isdelete;
    String insertdate, update_date, delete_date, date1, time1, from_date, to_date;

    public int getType1() {
        return type1;
    }

    public void setTo_time(String to_time) {
        this.to_time = to_time;
    }

    public void setType1(int type1) {
        this.type1 = type1;
    }

    public String getFrom_date() {
        return from_date;
    }

    public void setFrom_date(String from_date) {
        this.from_date = from_date;
    }

    public void setFrom_time(String from_time) {
        this.from_time = from_time;
    }


    public String getTo_date() {
        return to_date;
    }

    public void setTo_date(String to_date) {
        this.to_date = to_date;
    }

    public int getEmpid() {
        return empid;
    }

    public void setEmpid(int empid) {
        this.empid = empid;
    }

    public int getVacation_hours() {
        return vacation_hours;
    }

    public void setVacation_hours(int vacation_hours) {
        this.vacation_hours = vacation_hours;
    }

    public int getSick_days() {
        return sick_days;
    }

    public void setSick_days(int sick_days) {
        this.sick_days = sick_days;
    }

    public int getHolydays() {
        return holydays;
    }

    public void setHolydays(int holydays) {
        this.holydays = holydays;
    }

    public int getShifted() {
        return shifted;
    }

    public void setShifted(int shifted) {
        this.shifted = shifted;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public boolean isIsdelete() {
        return isdelete;
    }

    public void setIsdelete(boolean isdelete) {
        this.isdelete = isdelete;
    }

    public String getInsertdate() {
        return insertdate;
    }

    public void setInsertdate(String insertdate) {
        this.insertdate = insertdate;
    }

    public String getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(String update_date) {
        this.update_date = update_date;
    }

    public String getDelete_date() {
        return delete_date;
    }

    public void setDelete_date(String delete_date) {
        this.delete_date = delete_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
