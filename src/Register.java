import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Register extends JFrame {

    static JLabel TITLE = new JLabel("REGISTER");
    static JTextField USERNAME_INPUT = new JTextField();
    static JTextField PASSWORD_INPUT = new JTextField();
    static JTextField CODE_INPUT = new JTextField();
    static JLabel WARNING = new JLabel();
    static JLabel FORMAT_WARNING = new JLabel();
    static JLabel USERNAME_PROMPT = new JLabel("username:");
    static JLabel PASSWORD_PROMPT = new JLabel("password:");
    static JLabel CODE_PROMPT = new JLabel("verification code:");
    static JLabel CODE_GENERATOR = new JLabel();

    public Register(){
        //Inicializacion de pantalla
        initJFrame();
        //Poner textfield
        setTextField();
        //Poner boton
        setButton();
        //Generar codigo de verificacion
        generateCode();

        this.setVisible(true);

    }

    private void generateCode() {
        Random r = new Random();
        String code = "";
        for (int i = 1; i <= 4; i++) {
            int random = r.nextInt(2);
            char ch;
            if(random==0) {
                int randomNum = r.nextInt(10) + 48;
                ch = (char) randomNum;
            } else {
                int randomLetter = r.nextInt(26) + 65;
                ch = (char) randomLetter;
            }
            code+=ch;
        }
        CODE_GENERATOR.setText(code);
    }

    private void initJFrame() {

        initText();
        this.setSize(500,500);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("Regist");
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

    }

    private void setTextField() {

        setTitle();
        setInputText();
        setWarningText();
        setPromptText();
        setCodeGeneratorText();

    }

    private void setCodeGeneratorText() {
        CODE_GENERATOR.setBounds(300,220,120,50);
        CODE_GENERATOR.setFont(new Font("arial",Font.BOLD,20));
        this.getContentPane().add(CODE_GENERATOR);
    }

    private void setPromptText() {
        USERNAME_PROMPT.setBounds(50,105,70,20);
        this.getContentPane().add(USERNAME_PROMPT);
        PASSWORD_PROMPT.setBounds(50,165,70,20);
        this.getContentPane().add(PASSWORD_PROMPT);
        CODE_PROMPT.setBounds(40,230,100,20);
        this.getContentPane().add(CODE_PROMPT);
    }

    private void setWarningText() {
        WARNING.setBounds(20,400,250,20);
        this.getContentPane().add(WARNING);
        FORMAT_WARNING.setBounds(20,430,250,20);
        this.getContentPane().add(FORMAT_WARNING);
    }

    private void setInputText() {
        USERNAME_INPUT.setBounds(125,100,250,30);
        this.getContentPane().add(USERNAME_INPUT);
        PASSWORD_INPUT.setBounds(125,160,250,30);
        this.getContentPane().add(PASSWORD_INPUT);
        CODE_INPUT.setBounds(150,220,120,40);
        CODE_INPUT.setFont(new Font("arial",Font.BOLD,25));
        this.getContentPane().add(CODE_INPUT);
    }

    private void setTitle() {
        TITLE.setBounds(175,25,150,50);
        TITLE.setFont(new Font("arial",Font.BOLD,25));
        this.getContentPane().add(TITLE);
    }

    private void setButton() {

        JButton LogInButton = new JButton("BACK TO LOG IN");
        JButton RegisterButton = new JButton("CLICK TO REGISTER");
        JButton RefreshButton = new JButton("refresh");

        /*
        Cuando tocar el boton login, mostrar la pantalla de login y cerrar la ventana (aqui primero hay que llamar
        Register.this porque esta dentro de una clase anonimo (clase interna) y por eso el this en aqui se refiere a esta clase interna no
        a clase LogIn) dispose es mejor que visible porque puede librerar parte de memoria
        */
        LogInButton.addActionListener(e -> { Register.this.dispose(); new LogIn(); });
        //Cuando tocar boton de registrar, verificar su validacion
        RegisterButton.addActionListener(e -> { addAccount(); generateCode(); });
        //Cuando tocar boton de refresh, llamar el metodo de generacion de codigo
        RefreshButton.addActionListener(e -> generateCode());

        LogInButton.setBounds(250,325,150,50);
        RegisterButton.setBounds(100,325,150,50);
        RefreshButton.setBounds(285,275,80,20);

        this.getContentPane().add(LogInButton);
        this.getContentPane().add(RegisterButton);
        this.getContentPane().add(RefreshButton);
    }

    private void addAccount(){
        //filtrar uno a uno hasta que sea valido
        if(!CODE_INPUT.getText().equalsIgnoreCase(CODE_GENERATOR.getText())){
            WARNING.setText("verification code incorrect");
            FORMAT_WARNING.setText("");
            return;
        }
        if(!Format.UsernameIsValid(USERNAME_INPUT.getText())){
            WARNING.setText("username is not valid");
            FORMAT_WARNING.setText("format: 6-20letranumero_@3-5letra.2-3letra");
            return;
        }
        if(!Format.PasswordIsValid(PASSWORD_INPUT.getText())){
            WARNING.setText("password is not valid");
            FORMAT_WARNING.setText("format: 8-15letranumero_");
            return;
        }
        if(Account.UsernameIsExist(USERNAME_INPUT.getText())){
            WARNING.setText("username is already exist");
            FORMAT_WARNING.setText("");
            return;
        }
        new Account(USERNAME_INPUT.getText(), PASSWORD_INPUT.getText());
        initText();
        WARNING.setText("account created");
    }
    //Para que cuando cierra y abre de nuevo limpia todo el texto escribido
    private void initText(){
        FORMAT_WARNING.setText("");
        WARNING.setText("");
        PASSWORD_INPUT.setText("");
        USERNAME_INPUT.setText("");
        CODE_INPUT.setText("");
    }
}
