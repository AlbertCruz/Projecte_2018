/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.infomila.gestio;

import info.infomila.exceptions.UserOnlineException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Javi
 */
@NamedQueries({
    @NamedQuery(name="UserOnline.getConnectionCodes", 
            query="select u.connectionCode from UserOnline u"),

    @NamedQuery(name = "UserOnline.validaConnectionCode",
            query = "select count(u.connectionCode) from UserOnline u where u.connectionCode = ?1")})

@Entity
@Table(name = "user_online")
public class UserOnline implements Serializable{
    
    @Column(name = "connection_code")
    private long connectionCode;
    @Column(name = "user_id", nullable = true)
    private int userId;
    @Id
    @Column(name = "user_name")
    private String userName;    
    @Column(name = "connection_time")
    private Date connectionTime;
    
    @Transient
    List<Long> connectionCodes = new ArrayList<>();    
    
    
    protected UserOnline(){}
    
    public UserOnline(int userId, String userName, List<Long> connectionCodes){    
        setUserId(userId);
        setUserName(userName);
        setConnectionOptions(connectionCodes);        
    }

    public long getConnectionCode() {
        return connectionCode;
    }

    private void setConnectionOptions(List<Long> connectionCodes) {
        long connectionCode = 0;
        boolean codiCorrecte = false;
        
        if(connectionCodes.size() >0){
            while(connectionCode <= 0 || connectionCodes.contains(connectionCode)){
                connectionCode= new Random().nextLong();
            }
        
        }else {
            while(connectionCode <= 0){
                connectionCode= new Random().nextLong();
            }        
        }
        this.connectionCode = connectionCode;
        this.connectionTime = new Date();
        
    }
    
    public void setConnectonCode(long connectionCode){
        this.connectionCode = connectionCode;
    }

    public int getUserId() {
        return userId;
    }

    private void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    private void setUserName(String userName) {
        if(userName != null && ! userName.isEmpty())
        this.userName = userName;
        else throw new UserOnlineException("userName invàlid (valor null o cadena buida no permés");
    }

    public Date getConnectionTime() {
        return (Date) connectionTime.clone();
    }
    
}
