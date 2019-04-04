package com.gj.cpe.bean;

/**
 * @author guojie
 * <p>
 */
public class UserBean {

    private String userName;
    private int age;

    public UserBean(String userName, int age) {
        this.userName = userName;
        this.age = age;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object obj) {
        UserBean userBean = (UserBean) obj;

        return userName.equals(userBean.getUserName());
    }

    @Override
    public String toString() {
        return "userName:" + userName + "  age:" + age;
    }
}
