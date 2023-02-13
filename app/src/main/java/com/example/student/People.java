package com.example.student;

//定义People类
public class People {
    public int ID = -1;
    public String Name;
    public String Banji;
    public String Xuehao;
    public String xueyuan;
    public String zhengzhi;

    @Override
    public String toString() {
        String result = "";
        result += "ID：" + this.ID + "，";
        result += "姓名：" + this.Name + "，";
        result += "班级：" + this.Banji + "， ";
        result += "学号：" + this.Xuehao + "， ";
        result += "学院：" + this.xueyuan + "， ";
        result += "政治：" + this.zhengzhi;
        return result;
    }
}

