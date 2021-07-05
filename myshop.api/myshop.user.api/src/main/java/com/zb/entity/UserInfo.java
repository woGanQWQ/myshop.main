package com.zb.entity;
import java.io.Serializable;
import java.util.Date;
/***
*   
*/
public class UserInfo implements Serializable {
    //账号
    private String account;
    //
    private Integer id;
    //金额
    private Integer money;
    //密码
    private String name;
    //get set 方法
    public void setAccount (String  account){
        this.account=account;
    }
    public  String getAccount(){
        return this.account;
    }
    public void setId (Integer  id){
        this.id=id;
    }
    public  Integer getId(){
        return this.id;
    }
    public void setMoney (Integer  money){
        this.money=money;
    }
    public  Integer getMoney(){
        return this.money;
    }
    public void setName (String  name){
        this.name=name;
    }
    public  String getName(){
        return this.name;
    }
}
