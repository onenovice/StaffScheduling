package com.team.service;

import com.team.domain.*;

import static com.team.service.Data.*;
/**
 * @Description:
 * @Author: Jay
 * @Date: Create in 16:56 2021/3/30
 * @Version:
 */
public class NameListService {
    private Employee[] employees;

    //构造器:给employee及数组元素进行初始化

    public NameListService() {
//        1. 根据项目提供的Data类构建相应大小的employees数组
//        2. 再根据Data类中的数据构建不同的对象，包括Employee、Programmer、Designer和Architect对象，以及相关联的Equipment子类的对象
//           将对象存于数组中
        employees = new Employee[EMPLOYEES.length];

        for (int i = 0; i < employees.length; i++) {
            int type = Integer.parseInt(EMPLOYEES[i][0]);
            //获取Employee的4个基本信息
            int id = Integer.parseInt(EMPLOYEES[i][1]);
            String name = EMPLOYEES[i][2];
            int age = Integer.parseInt(EMPLOYEES[i][3]);
            double salary = Double.parseDouble(EMPLOYEES[i][4]);
            Equipment equipment;//声明在外部,免得重复声明,或声明多个变量
            double bonus;
            int stock;

            switch(type){
                case EMPLOYEE:
                    employees[i] = new Employee(id,name,age,salary);
                    break;
                case PROGRAMMER:
                    equipment = createEquipment(i);//取设备
                    employees[i] = new Programmer(id,name,age,salary,equipment);
                    break;
                case DESIGNER:
                    equipment = createEquipment(i);//取设备
                    bonus = Double.parseDouble(EMPLOYEES[i][5]);
                    employees[i] = new Designer(id,name,age,salary,equipment,bonus);
                    break;
                case ARCHITECT:
                    equipment = createEquipment(i);
                    bonus = Double.parseDouble(EMPLOYEES[i][5]);
                    stock = Integer.parseInt(EMPLOYEES[i][6]);
                    employees[i] = new Architect(id,name,age,salary,equipment,bonus,stock);
                    break;
            }
        }

    }
    /**
     *获取数组EQUIPMENTS指定index的员工设备
     * @author Jay
     * @date 2021/3/30
     *  * @param index
     * @return
     */
    private Equipment createEquipment(int index) {
        int key = Integer.parseInt(EQUIPMENTS[index][0]);

        String modelOrName = EQUIPMENTS[index][1];

        switch (key){
            case PC://21,常量名可读性强,方便修改
                String display = EQUIPMENTS[index][2];
                return new PC(modelOrName,display);
            case NOTEBOOK://22
                double price = Double.parseDouble(EQUIPMENTS[index][2]);
                return new NoteBook(modelOrName,price);
            case PRINTER://23
                String type = EQUIPMENTS[index][2];
                return new Printer(modelOrName,type);//name,type
        }
        return null;
    }
    /**
     *功能描述 :获取当前所有员工
     * @author Jay
     * @date 2021/3/31
     *  * @param
     * @return com.team.domain.Employee[]
     */
    public Employee[] getAllEmployees() {
        return employees;
    }
    /**
     *功能描述 获取指定id员工
     * @author Jay
     * @date 2021/3/31
     *  * @param id
     * @return com.team.domain.Employee
     */
    public Employee getEmployee(int id) throws TeamException {//在view内处理
        for (int i = 0; i <employees.length; i++) {
            if(employees[i].getId() == id){
                return employees[i];
            }
        }
        throw new TeamException("找不到指员工!");
    }


}

