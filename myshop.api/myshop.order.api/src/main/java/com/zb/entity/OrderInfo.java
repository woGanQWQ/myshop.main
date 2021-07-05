package com.zb.entity;
import java.io.Serializable;
import java.util.Date;
/***
*   
*/
public class OrderInfo implements Serializable {
    //主键
    private Long id;
    //留言
    private String message;
    //总金额
    private Integer money;
    //get set 方法
    public void setId (Long  id){
        this.id=id;
    }
    public  Long getId(){
        return this.id;
    }
    public void setMessage (String  message){
        this.message=message;
    }
    public  String getMessage(){
        return this.message;
    }
    public void setMoney (Integer  money){
        this.money=money;
    }
    public  Integer getMoney(){
        return this.money;
    }
}
