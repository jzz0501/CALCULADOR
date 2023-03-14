import javax.swing.*;
import java.awt.*;

public class LogIn extends JFrame {

    static JLabel TITLE = new JLabel("LOG IN");
    static JTextField USERNAME_INPUT = new JTextField();
    static JTextField PASSWORD_INPUT = new JTextField();
    static JLabel WARNING = new JLabel();
    static JLabel USERNAME_PROMPT = new JLabel("username:");
    static JLabel PASSWORD_PROMPT = new JLabel("password:");

    public LogIn(){

        initJFrame();

        setText();

        setButton();

        this.setVisible(true);

    }

    private void initJFrame() {

        initText();
        this.setSize(400,375);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("Log In");
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

    }

    private void setText() {

        setTitle();
        setInputText();
        setWarning();
        setPrompt();

    }

    private void setPrompt() {
        USERNAME_PROMPT.setBounds(50,80,70,25);
        this.getContentPane().add(USERNAME_PROMPT);
        PASSWORD_PROMPT.setBounds(50,130,70,25);
        this.getContentPane().add(PASSWORD_PROMPT);
    }

    private void setWarning() {
        WARNING.setBounds(110,165,200,20);
        this.getContentPane().add(WARNING);
    }

    private void setInputText() {
        USERNAME_INPUT.setBounds(125,80,180,25);
        this.getContentPane().add(USERNAME_INPUT);
        PASSWORD_INPUT.setBounds(125,130,180,25);
        this.getContentPane().add(PASSWORD_INPUT);
    }

    private void setTitle() {
        TITLE.setBounds(150,15,100,40);
        TITLE.setFont(new Font("arial",Font.BOLD,25));
        this.getContentPane().add(TITLE);
    }

    private void setButton() {

        JButton LogInButton = new JButton("LOG IN");
        JButton RegisterButton = new JButton("GO TO REGISTER");
        /*
        Cuando tocar el boton resgistrar, mostrar la pantalla de registrar y cerrar la ventana (aqui primero hay que llamar
        LogIn.this porque esta dentro de una clase anonimo (clase interna) y por eso el this en aqui se refiere a esta clase interna no
        a clase LogIn), dispose es mejor que visible porque puede librerar parte de memoria
        */
        RegisterButton.addActionListener(e -> { new Register(); LogIn.this.dispose(); });
        //Cuando tocar el boton log in, chequear la cuenta si esta bien
        LogInButton.addActionListener(e -> checkLogIn());

        LogInButton.setBounds(60,210,125,50);
        RegisterButton.setBounds(215,210,125,50);

        this.getContentPane().add(RegisterButton);
        this.getContentPane().add(LogInButton);

    }

    private void checkLogIn(){

        if (Account.verifyAccount(USERNAME_INPUT.getText(), PASSWORD_INPUT.getText(), WARNING)) {
            this.dispose();
            new Calculador();
        }
    }
    //Para que cuando cierra y abre de nuevo limpia todo el texto escribido
    private void initText(){
        WARNING.setText("");
        PASSWORD_INPUT.setText("");
        USERNAME_INPUT.setText("");
    }
}
