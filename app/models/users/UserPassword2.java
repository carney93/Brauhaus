package models.users;

import java.util.*;
import javax.persistence.*;

import io.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import models.shopping.*;
import models.users.*;
import models.*;

public class UserPassword2 extends Customer {
    private String password2;


    public UserPassword2(){

    }

    public UserPassword2(String email, String role, String name, 
    String password, Date dateOfBirth,String street1, String street2, String town, String postCode, String password2) {
        super(email, role,name, password,dateOfBirth,street1,street2,town,postCode);
        this.password2 = password2;
    }

    public String getPassword2(){
        return password2;
    }

    public void setPassword2(String p){
        this.password2 = p;
    }

}