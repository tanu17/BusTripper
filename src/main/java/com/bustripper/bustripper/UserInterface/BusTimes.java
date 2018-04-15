package com.bustripper.bustripper.UserInterface;

public class BusTimes {
    private String num;
    private int t1;
    private int t2;
    private int t3;

    public BusTimes(String no, int time1, int time2, int time3){
        num = no;
        t1 = time1;
        t2 = time2;
        t3 = time3;
    }

    public void setNum(String no){num = no;}
    public void setTime1(int time1){t1 = time1;}
    public void setTime2(int time2){t2 = time2;}
    public void setTime3(int time3){t3 = time3;}
    public String getNum(){return num;}
    public int getT1(){return t1;}
    public int getT2(){return t2;}
    public int getT3(){return t3;}
}
