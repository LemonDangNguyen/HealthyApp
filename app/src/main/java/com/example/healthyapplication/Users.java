package com.example.healthyapplication;

public class Users {
    private String username;
    private int age;
    private String sex;
    private int height;
    private int weight;
    private String heartRate;
    private String bloodPressure;
    private int steps;

    public Users(String username, int age, String sex, String height, String weight, String heartRate, String bloodPressure, String steps) {
    }

    public Users(String username, int age, String sex, int height, int weight, String heartRate, String bloodPressure, int steps) {
        this.username = username;
        this.age = age;
        this.sex = sex;
        this.height = height;
        this.weight = weight;
        this.heartRate = heartRate;
        this.bloodPressure = bloodPressure;
        this.steps = steps;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(String heartRate) {
        this.heartRate = heartRate;
    }

    public String getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(String bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }
}
