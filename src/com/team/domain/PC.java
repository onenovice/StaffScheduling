package com.team.domain;

public class PC implements Equipment{
    private String model;//机器型号
    private String display;//显示器名称

    //IDEA:快捷键 Alt + Insert

    public PC() {
    }
    public PC(String model, String display) {
        this.model = model;
        this.display = display;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getModel() {
        return model;
    }

    public String getDisplay() {
        return display;
    }

    //重写接口中的抽象方法
    @Override
    public String getDescription() {
        return model + "(" + display + ")";
    }
}
