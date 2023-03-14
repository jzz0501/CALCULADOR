import javax.swing.*;
import java.awt.*;

public class Calculador extends JFrame {

    static JTextField calcText = new JTextField();

    public Calculador(){
        //Inicializar la pantalla
        initJFrame();
        //Poner menu
        setMenu();
        //Poner textfield
        setCalcText();
        //Poner boton
        setButton();
        //Poner background (dibujo yo en paint)
        background();

        this.setVisible(true);

    }

    private void background() {

        ImageIcon imageIcon = new ImageIcon("img\\background.png");
        JLabel jLabel = new JLabel(imageIcon);
        jLabel.setBounds(0,0,800,800);
        this.getContentPane().add(jLabel);

    }

    private void initJFrame() {

        calcText.setText("");
        this.setSize(800,800);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("Calculador");
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

    }

    private void setCalcText(){

        calcText.setBounds(180,195,440,50);
        calcText.setFont(new Font("arial",Font.BOLD,40));
        calcText.setBorder(BorderFactory.createRaisedBevelBorder());
        calcText.setBackground(Color.green);
        this.getContentPane().add(calcText);

    }

    private void setButton(){

        String[][] matriz = {
                {"1","2","3","DL","CL"},
                {"4","5","6","(",")"},
                {"7","8","9","/","*"},
                {"0",".","=","+","-"}
        };

        int x = 165;
        int y = 285;

        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[0].length; j++) {
                JButton button = new JButton(matriz[i][j]);
                button.setBorder(BorderFactory.createRaisedBevelBorder());
                button.setBounds(x,y,75,75);
                button.setFont(new Font("arial",Font.BOLD,50));
                button.setBackground(Color.YELLOW);
                setActionListener(button);
                this.getContentPane().add(button);
                x+=100;
            }
            x=165;
            y+=100;
        }
    }

    private void setActionListener(JButton button){

        button.addActionListener(e -> {
                //Si tocar el boton "=" realizar calculo
                if (button.getText().equals("=")) {
                    calcText.setText(Calc.CheckAndCalc(calcText.getText()));
                /*
                Si tocar el boton "DL" eliminar ultimo letra con metodo substring, aqui hago una condicion (si longitud
                es 0 poner texto "" si no eliminar) para controlar un error de indexofbounds
                 */
                } else if (button.getText().equals("DL")) {
                    calcText.setText(calcText.getText().length()==0?"":calcText.getText().substring(0, calcText.getText().length()-1));
                } else if (button.getText().equals("CL")) {
                //Para eliminar todo el texto
                    calcText.setText("");
                //En el resto caso poner el texto de boton
                } else {
                    calcText.setText(calcText.getText()+button.getText());
                }
            }
        );
    }

    private void setMenu(){

        JMenuBar menuBar = new JMenuBar();

        JMenu option = new JMenu("Option");

        JMenuItem exit = new JMenuItem("EXIT");
        JMenuItem logout = new JMenuItem("LOG OUT");
        //Cuando tocar el menuitem exit ejecuta System.exit(0) 0 significa JMV se cierra normal
        exit.addActionListener(e -> System.exit(0));
        /*
        Cuando tocar el boton logout, mostrar la pantalla de login y cerrar la ventana (aqui primero hay que llamar
        Calculador.this porque esta dentro de una clase anonimo (clase interna) y por eso el this en aqui se refiere a esta clase interna no
        a clase LogIn) dispose es mejor que visible porque puede librerar parte de memoria
        */
        logout.addActionListener(e -> { new LogIn(); Calculador.this.dispose(); });

        option.add(exit);
        option.add(logout);

        menuBar.add(option);

        this.setJMenuBar(menuBar);
    }
}
