
package com.buckleit.imhotel.requestModel;

public class LoginDetails {
    String email;
    String password;


    //For LOGIN
    public LoginDetails(String email, String password) {
        this.email = email;
        this.password = password;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LoginDetails(){

    }

}
