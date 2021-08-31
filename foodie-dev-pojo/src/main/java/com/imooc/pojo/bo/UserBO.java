package com.imooc.pojo.bo;

/**
 * @author qingtian
 * @description:
 * @Package com.imooc.pojo.bo
 * @date 2021/8/31 19:46
 */
public class UserBO {
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    private String username;
    private String password;
    private String confirmPassword;

}
