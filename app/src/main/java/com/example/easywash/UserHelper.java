package com.example.easywash;

public class UserHelper {

    String date, time, name, phone,car;

    public UserHelper() {
    }

    public UserHelper(String date, String time, String name, String phone, String car) {
        this.date = date;
        this.time = time;
        this.name = name;
        this.phone = phone;
        this.car = car;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }
}
