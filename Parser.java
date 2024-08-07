/*
    Laboratorio No. 3 - Recursive Descent Parsing
    CC4 - Compiladores

    Clase que representa el parser

    Actualizado: agosto de 2021, Luis Cu
*/

import java.util.LinkedList;
import java.util.Stack;

public class Parser {

    // Puntero next que apunta al siguiente token
    private int next;
    // Stacks para evaluar en el momento
    private Stack<Double> operandos;
    private Stack<Token> operadores;
    // LinkedList de tokens
    private LinkedList<Token> tokens;

    // Funcion que manda a llamar main para parsear la expresion
    public boolean parse(LinkedList<Token> tokens) {
        this.tokens = tokens;
        this.next = 0;
        this.operandos = new Stack<Double>();
        this.operadores = new Stack<Token>();

        // Recursive Descent Parser
        // Imprime si el input fue aceptado
        System.out.println("Aceptada? " + S());

        // Shunting Yard Algorithm
        // Imprime el resultado de operar el input
        // System.out.println("Resultado: " + this.operandos.peek());

        // Verifica si terminamos de consumir el input
        if(this.next != this.tokens.size()) {
            return false;
        }
        return true;
    }

    // Verifica que el id sea igual que el id del token al que apunta next
    // Si si avanza el puntero es decir lo consume.
    private boolean term(int id) {
        if(this.next < this.tokens.size() && this.tokens.get(this.next).equals(id)) {
            
            // Codigo para el Shunting Yard Algorithm
            /*
            if (id == Token.NUMBER) {
				// Encontramos un numero
				// Debemos guardarlo en el stack de operandos
				operandos.push( this.tokens.get(this.next).getVal() );

			} else if (id == Token.SEMI) {
				// Encontramos un punto y coma
				// Debemos operar todo lo que quedo pendiente
				while (!this.operadores.empty()) {
					popOp();
				}
				
			} else {
				// Encontramos algun otro token, es decir un operador
				// Lo guardamos en el stack de operadores
				// Que pushOp haga el trabajo, no quiero hacerlo yo aqui
				pushOp( this.tokens.get(this.next) );
			}
			*/

            this.next++;
            return true;
        }
        return false;
    }

    // Funcion que verifica la precedencia de un operador
    private int pre(Token op) {
        /* TODO: Su codigo aqui */

        /* El codigo de esta seccion se explicara en clase */

        switch(op.getId()) {
        	case Token.PLUS:
        		return 1;
        	case Token.MULT:
        		return 2;
        	default:
        		return -1;
        }
    }

    private void popOp() {
        Token op = this.operadores.pop();

        /* TODO: Su codigo aqui */

        /* El codigo de esta seccion se explicara en clase */

        if (op.equals(Token.PLUS)) {
        	double a = this.operandos.pop();
        	double b = this.operandos.pop();
        	// print para debug, quitarlo al terminar
        	System.out.println("suma " + a + " + " + b);
        	this.operandos.push(a + b);
        } else if (op.equals(Token.MULT)) {
        	double a = this.operandos.pop();
        	double b = this.operandos.pop();
        	// print para debug, quitarlo al terminar
        	System.out.println("mult " + a + " * " + b);
        	this.operandos.push(a * b);
        }
    }

    private void pushOp(Token op) {
        /* TODO: Su codigo aqui */

        /* Casi todo el codigo para esta seccion se vera en clase */
    	
    	// Si no hay operandos automaticamente ingresamos op al stack

    	// Si si hay operandos:
    		// Obtenemos la precedencia de op
        	// Obtenemos la precedencia de quien ya estaba en el stack
        	// Comparamos las precedencias y decidimos si hay que operar
        	// Es posible que necesitemos un ciclo aqui, una vez tengamos varios niveles de precedencia
        	// Al terminar operaciones pendientes, guardamos op en stack

    }

    private boolean S() {
        return (E() && term(Token.SEMI) && V() ||term(Token.LPAREN) && E() && term(Token.RPAREN) && term(Token.SEMI) && V());
    }

    private boolean V() {
        if(E() || TOKENS() && E()|| E() && TOKENS()|| TOKENS() && E() && TOKENS()){
            return false;
        }
        return true;
    }

    private boolean TOKENS() {
        return term(Token.PLUS);
    }

    private boolean E() {
        int save = this.next;

        this.next = save;
        if(E1()){return true;}

        this.next = save;
        if(E2()){return true;}

        return false;
    }

    private boolean E1() {
        return M() && term(Token.PLUS) && E();
    }
    private boolean E2() {
        return M()||term(Token.LPAREN) && M() && term(Token.RPAREN);
    }

    private boolean M() {
        int save = this.next;

        this.next = save;
        if(M1()){return true;}

        this.next = save;
        if(M2()){return true;}

        return false;
    }

    private boolean M1() {
        return N() && term(Token.MINUS) && M();
    }
    private boolean M2() {
        return N()||term(Token.LPAREN) && N() && term(Token.RPAREN);
    }

    private boolean N() {
        int save = this.next;

        this.next = save;
        if(N1()){return true;}

        this.next = save;
        if(N2()){return true;}

        return false;
    }

    private boolean N1() {
        return O() && term(Token.MULT) && N();
    }
    private boolean N2() {
        return O()||term(Token.LPAREN) && O() && term(Token.RPAREN);
    }
    
    private boolean O() {
        int save = this.next;

        this.next = save;
        if(O1()){return true;}

        this.next = save;
        if(O2()){return true;}

        return false;
    }

    private boolean O1() {
        return P() && term(Token.DIV) && O();
    }
    private boolean O2() {
        return P()||term(Token.LPAREN) && P() && term(Token.RPAREN);
    }

    private boolean P() {
        int save = this.next;

        this.next = save;
        if(P1()){return true;}

        this.next = save;
        if(P2()){return true;}

        return false;
    }

    private boolean P1() {
        return Q() && term(Token.MOD) && P();
    }
    private boolean P2() {
        return Q()||term(Token.LPAREN) && Q() && term(Token.RPAREN);
    }

    private boolean Q() {
        int save = this.next;

        this.next = save;
        if(Q1()){return true;}

        this.next = save;
        if(Q2()){return true;}

        return false;
    }

    private boolean Q1() {
        return T() && term(Token.EXP) && Q();
    }
    private boolean Q2() {
        return T();
    }

    private boolean T() {
        int save = this.next;

        this.next = save;
        if(T1()){return true;}

        this.next = save;
        if(T2()){return true;}

        this.next = save;
        if(T3()){return true;}


        return false;
    }

    private boolean T1() {
        return term(Token.LPAREN) && I() && term(Token.RPAREN);
    }
    private boolean T2() {
        return term(Token.UNARY) && I();
    }
    private boolean T3() {
        return I();
    }

    private boolean I(){
        return term(Token.NUMBER);
    }

    /* TODO: sus otras funciones aqui */
}
