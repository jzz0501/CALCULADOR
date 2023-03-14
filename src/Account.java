import javax.swing.*;
import java.util.ArrayList;

public class Account {

    static ArrayList<Account> accountsList = new ArrayList<>();
    private String username;
    private String password;

    public Account(){

    }

    public Account(String user, String password){

        this.username = user;
        this.password = password;

        accountsList.add(this);

    }

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

    //Metodo llamado por register para verificar el username si esta usado
    public static boolean UsernameIsExist(String username){
        for (Account account:Account.accountsList) {
            if(account.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }
    //Metodo llamado por login para verificar la cuenta
    public static boolean verifyAccount(String username, String password, JLabel prompt) {

        for (Account account : Account.accountsList) {
            if (account.getUsername().equals(username)) {
                if (account.getPassword().equals(password)) {
                    return true;
                } else {
                    prompt.setText("password incorrect");
                    return false;
                }
            }
        }
        prompt.setText("this username is not registered");
        return false;
    }
}
