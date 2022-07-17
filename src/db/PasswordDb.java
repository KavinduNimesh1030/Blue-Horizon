package db;

public class PasswordDb {
    private  static String password;
    public void setPassword(String Password){
       this.password =Password;
        System.out.println(password);
    }
    public String getPassword(){
        return this.password;
    }

}
