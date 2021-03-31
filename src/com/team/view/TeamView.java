package com.team.view;

import com.team.domain.Employee;
import com.team.domain.Programmer;
import com.team.service.NameListService;
import com.team.service.TeamException;
import com.team.service.TeamService;

/**
 * @ Description:
 * @ Author: Jay
 * @ Date: Create in 13:14 2021/3/31
 * @ Version:
 */
public class TeamView {
    private NameListService listSvc = new NameListService();
    private TeamService teamSvc = new TeamService();


    public void enterMainMenu() {
        boolean loopFlag = true;
        char menu = 0;
        while (loopFlag) {

            if (menu != '1') {
                listAllEmployees();
            }
            System.out.println("1-团队列表 2-添加团队成员 3-删除团队成员 4-退出 请选择(1-4):");
            menu = TSUtility.readMenuSelection();
            switch (menu) {
                case '1':
                    getTeam();
                    break;
                case '2':
                    addMember();
                    break;
                case '3':
                    deleteMember();
                    break;
                case '4':
                    System.out.println("确认是否要退出?(Y/N):");
                    char isExit = TSUtility.readConfirmSelection();
                    if (isExit == 'Y')
                        loopFlag = false;
                    break;
            }
            System.out.println("--------------------------------------------------------------------------");
        }
    }

    /**
     * @return void
     * @ Description 显示所有员工信息
     * @author Jay
     * @date 2021/3/31
     * * @param
     */
    private void listAllEmployees() {
        System.out.println("\n-------------------------------开发团队调度软件--------------------------------\n");
        Employee[] emps = listSvc.getAllEmployees();
        if (emps == null || emps.length == 0) {
            System.out.println("没有客户记录！");
        } else {
            System.out.println("ID\t姓名\t年龄\t工资\t职位\t状态\t奖金\t股票\t领用设备");
        }
        for (Employee e : emps) {
            System.out.println(" " + e);
        }
        System.out.println("-------------------------------------------------------------------------------");
    }

    /**
     * @return void
     * @ Description 显示团队成员
     * @author Jay
     * @date 2021/3/31
     * * @param
     */
    private void getTeam() {
        System.out.println("\n--------------------团队成员列表---------------------\n");
        Programmer[] team = teamSvc.getTeam();
        if (team ==null || team.length==0){
            System.out.println("开发团队目前没有成员!");
        }else{
            System.out.println("TID/ID\t姓名\t年龄\t工资\t职位\t奖金\t股票");
            for (Programmer p : team) {
                System.out.println(p.getDetailsForTeam());
            }
        }
    }

    /**
     * @return void
     * @ Description 向新团队添加成员
     * @author Jay
     * @date 2021/3/31
     * * @param
     */
    private void addMember() {
        System.out.println("---------------------添加成员---------------------");
        System.out.print("请输入要添加的员工ID：");
        int id = TSUtility.readInt();

        try {
            Employee emp = listSvc.getEmployee(id);
            teamSvc.addMember(emp);
            System.out.println("添加成功");

        } catch (TeamException e) {
            System.out.println("添加失败,原因:"+e.getMessage());
        }
        TSUtility.readReturn();
    }

    /**
     * @return void
     * @ Description 删除新团队成员
     * @author Jay
     * @date 2021/3/31
     * * @param
     */
    private void deleteMember() {
        System.out.println("---------------------删除成员---------------------");
        System.out.print("请输入要删除员工的TID：");
        int id = TSUtility.readInt();
        System.out.print("确认是否删除(Y/N)：");
        char yn = TSUtility.readConfirmSelection();
        if(yn == 'N')return;
        try {
            teamSvc.removeMember(id);
            System.out.println("删除成功");
        } catch (TeamException e) {
            System.out.println("删除失败,原因:"+ e.getMessage());
        }
        //按回车继续
        TSUtility.readReturn();
    }

    public static void main(String[] args) {
        TeamView view = new TeamView();
        view.enterMainMenu();
    }
}
