public class Format {

    //Validacion de username con regex
    public static boolean UsernameIsValid(String username){
        return username.matches("\\w{6,20}@[a-z]{3,5}[.][a-z]{2,3}");
    }

    //Validacion de password con regex
    public static boolean PasswordIsValid(String password){
        return password.matches("\\w{8,15}");
    }

    public static boolean CalcTextIsValid(String calcText){
        return SyntaxIsValid(calcText)&&ParentesisIsValid(calcText)&&MultAndDivIsValid(calcText);
    }

    /*
    Validacion de sintaxis de calcular
    1. +-( puede no existir o existir varias veces en principio
    2. \\d+([.]\\d+)? puede ser decimar o entero
    3. [()+\\-/*]+ entre dos numero puede tener uno o mucho signo o parentesis
    4. En la final debe de ser un numero
    */
    public static boolean SyntaxIsValid(String calcText){
        return calcText.matches("[+\\-(]*(\\d+([.]\\d+)?[()+\\-/*]+)*\\d+([.]\\d+)?[)]*");
    }

    //Controlar el formato de parentesis, porque con regex no es suficiente para controlarlo (posible que si puede pero todavia no he aprendido completamente el regex)
    public static boolean ParentesisIsValid(String calcText){

        //Dos variable para contar ( y ), porque tienen que ser igual cantidad
        int izquierda = 0;
        int derecha = 0;
        //Recorrer texto de calcular
        for (int i = 0; i < calcText.length(); i++) {
            //Despues una posicion de ( no puede tener / * )
            if(calcText.charAt(i)=='('){
                if(calcText.charAt(i+1)=='/'||calcText.charAt(i+1)=='*'||calcText.charAt(i+1)==')'){
                    return false;
                }
                izquierda++;
            }
            //Delante una posicion de ) no puede tener / * + -
            if(calcText.charAt(i)==')'){
                if(calcText.charAt(i-1)=='/'||calcText.charAt(i-1)=='*'||calcText.charAt(i-1)=='+'||calcText.charAt(i-1)=='-'){
                    return false;
                }
                derecha++;
            }
            //Si durante el periodo de recorrer la cantidad de ) > cantidad de ( significa que salio alguna esta cosa Ej: (1+2))2+3(
            if(derecha>izquierda){
                return false;
            }
        }
        //Si al final de recorrer la cantidad de ( != cantidad de ) significa que esta error de poner texto de calcular
        if(izquierda!=derecha){
            return false;
        }
        return true;
    }

    public static boolean MultAndDivIsValid(String calcText){
        //Si hay 2 * o / continua esta error, si delante de * o / tiene + o - esta error
        for (int i = 0; i < calcText.length(); i++) {
            if(calcText.charAt(i)=='*'||calcText.charAt(i)=='/'){
                if(calcText.charAt(i+1)=='*'||calcText.charAt(i+1)=='/'||calcText.charAt(i-1)=='+'||calcText.charAt(i-1)=='-'){
                    return false;
                }
            }
        }
        return true;
    }

}
