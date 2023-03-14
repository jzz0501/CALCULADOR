import java.util.ArrayList;

public class Calc {

    public static String CheckAndCalc(String calcText){
        //Verificar el formato del texto de calculo
        if(Format.CalcTextIsValid(calcText)){
            //Si el formato esta valido, hacer metodo calc
            return calc(calcText);
        } else {
            return "SYNTAX ERROR";
        }
    }

    /*
    Parte logica de calculadora, toda la parte de calculadora hago sin ayuda de internet ni copiar, intentar a hacer
    yo solo con la cosa que yo lo sepa ahora y pensar las cosas
    Unica cosa buscado en internet es la preferencia de multiplicacion y division (mas delante tiene preferencia)
    */
    private static String calc(String calcText){

        /*
        Mayoria errores esta controlado (creo que si hay algo que no he encontrado), en el parte de parentesis hago con
        pensamiento de recursion, el algoritmo merge sort y quick sort puede ver esta algoritmo
        Hago esto porque la parte de parentesis pienso que si puede hacer con dividir y venceras, que yo aprendido en
        alguna algoritmo
        */

        //No sabemos cuanto veces recursion va a hacer pues, un bucle infinito
        while(true) {
            //en primero, combinamos -+ +- -- ++ en cada vez itinerario
            while(calcText.contains("+-")||calcText.contains("-+")||calcText.contains("--")||calcText.contains("++")){
                calcText = calcText.replaceAll("\\+-|-\\+","-");
                calcText = calcText.replaceAll("\\+\\+|--","+");
            }
            //en segundo, hacemos calcular de parte a parte, con la preferencia de () > */ > +-
            if (calcText.contains("(")) {
                calcText = calcParentesis(calcText);
            } else if (calcText.contains("*")||calcText.contains("/")) {
                calcText = calcMultandDiv(calcText);
            } else if (calcText.contains("+")||calcText.contains("-")) {
                calcText = calcSumAndRest(calcText);
            }

            //Despues de calcular una parte a ver si tenemos el resultado final, si tenemos pues salir el bucle
            if(calcText.matches("[\\-+]?\\d+(\\.\\d+)?")){
                break;
            }
            //Hay un caso especial que es el numero flotante dividir a cero se devuelve un "Infinity"
            if(calcText.equals("Infinity")){
                calcText = "SYNTAX ERROR";
                break;
            }
        }
        //Despues de saltar el bucle verificamos el decimal si es cero, si es cero pues quitamos con substring
        if(calcText.matches("[-]?\\d+\\.[0]+")){
            calcText = calcText.substring(0,calcText.indexOf("."));
        }
        //Devolver el resultado calculado, en caso de parentesis pues, devolver con recursion
        return calcText;
    }

    private static String calcParentesis(String calcText){
        /*
        En el calculo de parentesis, hago con una forma que yo lo pienso, primero buscamos la primera posicion de ( y
        contarlo, luego a buscar la posicion de ), durante este periodo cuando descubre un ( contar con contIzquierda,
        y cuando descubre un ) contar con contDerecha, si el numero de ocurrencias de ( y ) no es igual significa que
        no son pareja, pues sigue recorrer con bucle hasta que sea la pareja y salir de bucle
        */
        int contIzquierda = 1;
        int contDerecha = 0;

        int from = calcText.indexOf("(");
        int to = calcText.indexOf("(")+1;

        while(contIzquierda!=contDerecha){
            if(calcText.charAt(to)=='('){
                contIzquierda++;
            }
            if(calcText.charAt(to)==')'){
                contDerecha++;
            }
            to++;
        }
        /*
        Salimos el bucle y sabemos la posicion de parentesis emparejado, con substring obtener su contenido para calcular
        Ej: 1. 5(5+5) parentesisContenido = (5+5)
        */
        String parentesisContenido = calcText.substring(from+1,to-1);
        /*
        Para calcular parentesis hacemos con recursion, dividir y venceras hasta que esta calculado y devolver parte a parte,
        si se puede hacer varias capas de parentesis con recursion Ej: ((5+6)(4/2)+2)
        Ej: 1. 5(5+5) parentesisContenido = (5+5)
            2. calculado = 10
        */
        String calculado = calc(parentesisContenido);
        /*
        Si el delante de parentesis es un numero, pues es un multiplicacion, añadir un * en delante de calculado
        Ej: 1. 5(5+5) parentesisContenido = (5+5)
            2. calculado = 10
            3. calculado = *10
        */
        if(from>0) {
            if (Character.isDigit(calcText.charAt(from - 1))) {
                calculado = "*" + calculado;
            }
        }
        /*
        Devolver el parentesis calculado y modificado en calcText a la capa anterior
        Ej: 1. 5(5+5) parentesisContenido = (5+5)
            2. calculado = 10
            3. calculado = *10
            4. calcText = 5(5+5) calculado = *10 despues de modifyCalcText calcText = 5*10
        */
        return modifyCalcText(calcText,calculado,from,to);

    }

    private static String modifyCalcText(String calcText,String calculado,int from,int to){
        //Metodo para modificar resultado con calcText y parte calculado, from y to se refiere a la posicion que tiene que modificar
        StringBuilder sb = new StringBuilder(calcText);
        sb.delete(from,to).insert(from,calculado);
        calcText = sb.toString();
        return calcText;
    }

    private static String calcSumAndRest(String calcText){
        /*
        Aqui hago con recorrer y capturar una vez y calcular, cuando se ejecuta esta metodo significa que en esta parte solo
        tiene sumar y restar pues no hay que considerar el orden ni preferencia ni otro, puede ser mas eficiencia que la forma
        de multiplicacion y division que lo hice, porque solo recorre en un bucle y no tiene que hacer recorrer, devolver, recorrer,
        devolver... (caso de multiplicacion y division que yo hice)
        Multiplicacion y division tambien puede hacer con un bucle solo para que sea mas eficiente (rendimiento) pero ya dejo asi
        */
        double result = 0;

        ArrayList<Double> numList = new ArrayList<>();

        int from = 0;
        int to = 1;

        while(true) {

            if(calcText.charAt(to)=='+'||calcText.charAt(to)=='-'){
                numList.add(Double.valueOf(calcText.substring(from,to)));
                from = to;
            }
            to++;

            if(to>=calcText.length()){
                numList.add(Double.valueOf(calcText.substring(from)));
                break;
            }
        }

        for (Double num:numList) {
            result+=num;
        }

        return String.valueOf(result);

    }

    private static String calcMultandDiv(String calcText){
        /*
        Metodo para calcular multiplicacion y division, la multiplicacion y division se calcula con preferencia de orden,
        la division o multiplicacion que esta mas delante tiene preferencia de calcular, pues hago primero es calcular
        donde esta la primera posicion de * y /, primero a calcular la mas delante
        */
        int multPos = calcText.indexOf("*");
        int divPos = calcText.indexOf("/");
        /*
        Cuando la multiplicacion si existe (indexOf != -1) y index posicion menor que division (preferencia de orden)
        o division no existe pues buscar y calcular multiplicacion si no pues division
        */
        if(multPos!=-1&&multPos<divPos||divPos==-1){
            return findAndCalc(calcText,multPos,"*");
        } else {
            return findAndCalc(calcText,divPos,"/");
        }
    }

    private static String findAndCalc(String calcText, int pos, String operador){

        /*
        Ya sabemos la posicion de * o / que tenemos que calcular, aqui hago con esta forma, obtener la posicion de * o /,
        a recorrer delante y despues de esta posicion a buscar los dos numero habitual y calcularlo
        */

        //Buscar el numero habitual de delante
        int from = pos-1;
        int to = pos;

        while(true){
            if(calcText.charAt(from)=='+'||calcText.charAt(from)=='-'){
                from++;
                break;
            } else if (from==0) {
                break;
            } else {
                from--;
            }
        }
        //Obtener el numero delante
        int subsStringFrom = from;
        double preNum = Double.parseDouble(calcText.substring(from,to));

        //Buscar el numero habitual de despues
        from = pos+1;
        to = from;

        while(true){
            if(from!=to&&(calcText.charAt(to)=='+'||calcText.charAt(to)=='-'||calcText.charAt(to)=='*'||calcText.charAt(to)=='/')){
                break;
            } else if (to>=calcText.length()-1) {
                to++;
                break;
            } else {
                to++;
            }
        }
        //Obtener el numero despues
        double nextNum = Double.parseDouble(calcText.substring(from,to));
        int subsStringTo = to;

        //Calcular estos dos numero
        String calculado;
        if(operador.equals("*")){
            calculado = String.valueOf(preNum*nextNum);
        } else {
            calculado = String.valueOf(preNum/nextNum);
        }

        //A modificar calcText despues de calculado
        return modifyCalcText(calcText,calculado,subsStringFrom,subsStringTo);

    }
}
