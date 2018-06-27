package com.example.zujianhuaapp.manager;
/*
 *  包名: com.example.zujianhuaapp.manager
 * Created by ASUS on 2018/6/16.
 *  描述: TODO
 */

import com.example.zujianhuaapp.module.user.User;

public class UserManager {

    private static UserManager userManager=null;

    private User user=null;

    public static UserManager getInstance(){
        if(userManager==null){
            synchronized (UserManager.class){
                if(userManager==null){
                    userManager=new UserManager();
                }
                return userManager;
            }
        }else {
            return userManager;
        }
    }

    public void setUser(User user){
        this.user=user;
    }

    public User getUser(){

        return this.user;
    }

    public boolean hasLogined(){
        return user==null?false:true;
    }

    public void removeUser() {

        this.user = null;
    }



}
