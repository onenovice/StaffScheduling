package com.team.service;

import com.team.domain.Architect;
import com.team.domain.Designer;
import com.team.domain.Employee;
import com.team.domain.Programmer;

/**
 * @ Description: 新团队成员的添加,删除
 * @ Author: Jay
 * @ Date: Create in 12:02 2021/3/31
 * @ Version:
 */
public class TeamService {
    private static int counter = 1;//给memberId赋值
    private final int MAX_MEMBER = 5;//限制开发团队人数
    private Programmer[] team = new Programmer[MAX_MEMBER];//保存开发团队
    private int total;//实际人数

    public TeamService() {
    }

    /**
     * @return com.team.domain.Programmer[]
     * @ Description 获取团队成员数组
     * @author Jay
     * @date 2021/3/31
     * * @param
     */
    public Programmer[] getTeam() {
        Programmer[] team = new Programmer[total];//注意区分这里的局部变量team
        for (int i = 0; i < team.length; i++) {
            team[i] = this.team[i];
        }
        return team;
    }
    /**
     * @ Description 添加成员到开发团队
     * @author Jay
     * @date 2021/3/31
     *  * @param e
     * @return void
     */
    public void addMember(Employee e) throws TeamException {
//        成员已满，无法添加
        if (total >= MAX_MEMBER) {
            throw new TeamException("成员已满，无法添加");
        }
//        该成员不是开发人员，无法添加
        if (!(e instanceof Programmer)) {
            throw new TeamException("该成员不是开发人员，无法添加");
        }
//        该员工已在本开发团队中
        if (isExist(e)) {
            throw new TeamException("该员工已在本开发团队中");
        }
//      该员工已是某团队成员
//      该员正在休假，无法添加
        Programmer p = (Programmer) e;
        if ("BUSY".equals(p.getStatus().getNAME())) {//一定可以强转,前面已经判断是Programmer实例
            throw new TeamException("该员工已是某团队成员");
        } else if ("VOCATION".equals(p.getStatus().getNAME())) {
            throw new TeamException("该员正在休假，无法添加");
        }
//      团队中至多只能有一名架构师
//      团队中至多只能有两名设计师
//      团队中至多只能有三名程序员
        //获取team已有成员架构师\设计师\程序员数量
        int numOfArch = 0, numOfDes = 0, numOfPro = 0;
        for (int i = 0; i < total; i++) {
            if (team[i] instanceof Architect) {
                numOfArch++;
            } else if (team[i] instanceof Designer) {
                numOfDes++;
            } else if (team[i] instanceof Programmer) {//恒为真
                numOfPro++;
            }
        }
        if (p instanceof Architect) {
            if (numOfArch >= 1) {
                throw new TeamException("团队中至多只能有一名架构师");
            }
        } else if (p instanceof Designer) {
            if (numOfDes >= 2) {
                throw new TeamException("团队中至多只能有两名设计师");
            }
        } else if (p instanceof Programmer) {//恒为真
            if (numOfPro >= 3) {
                throw new TeamException("团队中至多只能有三名程序员");
            }
        }
        //将p添加到现有team
        team[total++] = p;
        //更新属性,状态
        p.setStatus(Status.BUSY);
        p.setMemberId(counter++);
    }

    /**
     * @return boolean
     * @ Description 判断指定员工是否在现有开发团队中
     * @author Jay
     * @date 2021/3/31
     * * @param e
     */
    private boolean isExist(Employee e) {
        for (int i = 0; i < total; i++) {
            if (team[i].getId() == e.getId()) {
                return true;
            }
        }
        return false;
    }
    /**
     * @ Description 将成员移除team团队
     * @author Jay
     * @date 2021/3/31
     *  * @param id
     * @return void
     */
    public void removeMember(int memberId) throws TeamException {
        int i;
        for (i = 0; i < total; i++) {
            if(team[i].getMemberId() == memberId) {
                team[i].setStatus(Status.FREE);//状态
                break;
            }
        }
        if(i == total){
            throw new TeamException("找不到指定MemberId的员工,删除失败");
        }

        for (int j = i+1; j < total; j++) {
            team[j-1] = team[j];
        }
        team[--total] = null;//末尾置空

    }
}
