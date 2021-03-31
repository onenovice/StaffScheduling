package com.team.service;

/**
 * @Description: 自定义异常类
 * @Author: Jay
 * @Date: Create in 10:29 2021/3/31
 * @Version:
 */
public class TeamException extends Exception{
    static final long serialVersionUID = -3387514229948L;

    public TeamException() {

    }

    public TeamException(String message) {
        super(message);
    }
}
