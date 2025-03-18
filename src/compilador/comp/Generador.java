package comp;


import comp.nodosAST.*;

public class Generador {
	/* Ilustracion de la disposicion de la memoria en
	 * este ambiente de ejecucion para el lenguaje Tiny
	 *
	 * |t1	|<- mp (Maxima posicion de memoria de la TM
	 * |t1	|<- desplazamientoTmp (tope actual)
	 * |free|
	 * |free|
	 * |...	|
	 * |x	|
	 * |y	|<- gp
	 * 
	 * */
	
	
	
	/* desplazamientoTmp es una variable inicializada en 0
	 * y empleada como el desplazamiento de la siguiente localidad
	 * temporal disponible desde la parte superior o tope de la memoria
	 * (la que apunta el registro MP).
	 * 
	 * - Se decrementa (desplazamientoTmp--) despues de cada almacenamiento y
	 * 
	 * - Se incrementa (desplazamientoTmp++) despues de cada eliminacion/carga en 
	 *   otra variable de un valor de la pila.
	 * 
	 * Pudiendose ver como el apuntador hacia el tope de la pila temporal
	 * y las llamadas a la funcion emitirRM corresponden a una inserccion 
	 * y extraccion de esta pila
	 */
	private static int desplazamientoTmp = 0;
	private static TablaSimbolos tablaSimbolos = null;
	
	public static void setTablaSimbolos(TablaSimbolos tabla){
		tablaSimbolos = tabla;
	}
	
	public static void generarCodigoObjeto(NodoBase raiz){
		System.out.println();
		System.out.println();
		System.out.println("------ CODIGO OBJETO DEL LENGUAJE TINY GENERADO PARA LA TM ------");
		System.out.println();
		System.out.println();
		generarPreludioEstandar();
		generar(raiz);
		/*Genero el codigo de finalizacion de ejecucion del codigo*/   
		UtGen.emitirComentario("Fin de la ejecucion.");
		UtGen.emitirRO("HALT", 0, 0, 0, "");
		System.out.println();
		System.out.println();
		System.out.println("------ FIN DEL CODIGO OBJETO DEL LENGUAJE TINY GENERADO PARA LA TM ------");
	}
	
	//Funcion principal de generacion de codigo
	//prerequisito: Fijar la tabla de simbolos antes de generar el codigo objeto 
	private static void generar(NodoBase nodo){
	if(tablaSimbolos!=null){
		if (nodo instanceof  NodoIf){
			generarIf(nodo);
		}else if (nodo instanceof  NodoRepeat){
			generarRepeat(nodo);
		}else if (nodo instanceof  NodoFor){
			generarFor(nodo);
		}else if (nodo instanceof  NodoAsignacionSimple){
			generarAsignacionSimple(nodo);
		}else if (nodo instanceof  NodoAsignacionComplex){
			generarAsignacionComplex(nodo);
		}else if (nodo instanceof  NodoLeerSimple){
			generarLeerSimple(nodo);
		}else if (nodo instanceof  NodoLeerComplex){
			generarAsignacionComplex(nodo);
		}else if (nodo instanceof  NodoEscribir){
			generarEscribir(nodo);
		}else if (nodo instanceof NodoValor){
			generarValor(nodo);
		}else if (nodo instanceof NodoVector){
			if(!((NodoVector)nodo).isDeclaracion())
			generarVector(nodo);
		}else if (nodo instanceof NodoIdentificador){
			generarIdentificador(nodo);
		}else if (nodo instanceof NodoOperacion){
			generarOperacion(nodo);
		}else{
			System.out.println("BUG: Tipo de nodo a generar desconocido");
		}
		/*Si el hijo de extrema izquierda tiene hermano a la derecha lo genero tambien*/
		if(nodo.TieneHermano())
			generar(nodo.getHermanoDerecha());
	}else
		System.out.println("���ERROR: por favor fije la tabla de simbolos a usar antes de generar codigo objeto!!!");
}

	private static void generarIf(NodoBase nodo){
    	NodoIf n = (NodoIf)nodo;
		int localidadSaltoElse,localidadSaltoEnd,localidadActual;
		if(UtGen.debug)	UtGen.emitirComentario("-> if");
		/*Genero el codigo para la parte de prueba del IF*/
		generar(n.getPrueba());
		localidadSaltoElse = UtGen.emitirSalto(1);
		UtGen.emitirComentario("If: el salto hacia el else debe estar aqui");
		/*Genero la parte THEN*/
		generar(n.getParteThen());
		localidadSaltoEnd = UtGen.emitirSalto(1);
		UtGen.emitirComentario("If: el salto hacia el final debe estar aqui");
		localidadActual = UtGen.emitirSalto(0);
		UtGen.cargarRespaldo(localidadSaltoElse);
		UtGen.emitirRM_Abs("JEQ", UtGen.AC, localidadActual, "if: jmp hacia else");
		UtGen.restaurarRespaldo();
		/*Genero la parte ELSE*/
		if(n.getParteElse()!=null){
			generar(n.getParteElse());
			localidadActual = UtGen.emitirSalto(0);
			UtGen.cargarRespaldo(localidadSaltoEnd);
			UtGen.emitirRM_Abs("LDA", UtGen.PC, localidadActual, "if: jmp hacia el final");
			UtGen.restaurarRespaldo();
    	}
		
		if(UtGen.debug)	UtGen.emitirComentario("<- if");
	}
	
	private static void generarRepeat(NodoBase nodo){
    	NodoRepeat n = (NodoRepeat)nodo;
		int localidadSaltoInicio;
		if(UtGen.debug)	UtGen.emitirComentario("-> repeat");
			localidadSaltoInicio = UtGen.emitirSalto(0);
			UtGen.emitirComentario("repeat: el salto hacia el final (luego del cuerpo) del repeat debe estar aqui");
			/* Genero el cuerpo del repeat */
			generar(n.getCuerpo());
			/* Genero el codigo de la prueba del repeat */
			generar(n.getPrueba());
			UtGen.emitirRM_Abs("JEQ", UtGen.AC, localidadSaltoInicio, "repeat: jmp hacia el inicio del cuerpo");
		if(UtGen.debug)	UtGen.emitirComentario("<- repeat");
	}		

	private static void generarFor(NodoBase nodo){
		NodoFor n = (NodoFor)nodo;
		int localidadSaltoInicio = 0;
		int localidadSaltoFinal = 0;
		int localidadActual= 0;
		if(UtGen.debug)	UtGen.emitirComentario("-> for");
		/* Genero el codigo de asignacion inicial del for */
		generar(n.getAsignaP());

		localidadSaltoInicio = UtGen.emitirSalto(0);
		/* Genero el codigo de la prueba del for */
		generar(n.getPrueba());
		localidadSaltoFinal = UtGen.emitirSalto(1);
		/* Genero el cuerpo del for */
		generar(n.getCuerpo());
		/* Genero el codigo de asignacion final del for*/
		generar(n.getAsignaT());
		UtGen.emitirRM_Abs("LDA", UtGen.PC, localidadSaltoInicio, "for: jmp hacia el inicio");
		localidadActual = UtGen.emitirSalto(0);
		UtGen.cargarRespaldo(localidadSaltoFinal);
		UtGen.emitirRM_Abs("JEQ", UtGen.AC, localidadActual, "for: jmp hacia el final");

		UtGen.restaurarRespaldo();
	}		
	private static void generarAsignacionSimple(NodoBase nodo){
		NodoAsignacionSimple n = (NodoAsignacionSimple)nodo;
		int direccion;
		if(UtGen.debug)	UtGen.emitirComentario("-> asignacion");		
		/* Genero el codigo para la expresion a la derecha de la asignacion */
		generar(n.getExpresion());
		/* Ahora almaceno el valor resultante */
		direccion = tablaSimbolos.getDireccion(n.getIdentificador());
		UtGen.emitirRM("ST", UtGen.AC, direccion, UtGen.GP, "asignacion simple: almaceno el valor para el id "+n.getIdentificador());
		if(UtGen.debug)	UtGen.emitirComentario("<- asignacion");
	}
	private static void generarAsignacionComplex(NodoBase nodo){
        NodoAsignacionComplex n = (NodoAsignacionComplex)nodo;
        if (n.getIdentificador() instanceof NodoIdentificador){
            NodoIdentificador variable = (NodoIdentificador)n.getIdentificador();
            int direccion = tablaSimbolos.getDireccion(variable.getNombre());
            generar(n.getExpresion());
			UtGen.emitirRM("ST", UtGen.AC, direccion, UtGen.GP, "asignacion compleja: almaceno el valor para el id "+variable.getNombre());
        }
        else if(n.getIdentificador()  instanceof  NodoVector){
            NodoVector nVector = (NodoVector)n.getIdentificador();
            NodoIdentificador identificador_vector = (NodoIdentificador)nVector.getIdentificador();
            int direccion = tablaSimbolos.getDireccion(identificador_vector.getNombre());
			UtGen.emitirRM("LDA", UtGen.AC, direccion, UtGen.GP, "cargar direccion de identificador: " + identificador_vector.getNombre());
			
			if (nVector.getExpresion() instanceof NodoOperacion) {
				generarOperacion(nVector.getExpresion());
			} else {
				generar(nVector.getExpresion());
			}
			
			UtGen.emitirRO("ADD", UtGen.AC, UtGen.AC, 1, "cargar direccion de identificador: " + identificador_vector.getNombre());
			generar(n.getExpresion());
			UtGen.emitirRM("ST", UtGen.AC, 0, UtGen.AC1, "asignacion: almaceno el valor para el id " + identificador_vector.getNombre());
        }
    }
	
	private static void generarLeerSimple(NodoBase nodo){
		NodoLeerSimple n = (NodoLeerSimple)nodo;
		int direccion;
		if(UtGen.debug)	UtGen.emitirComentario("-> leer");
		UtGen.emitirRO("IN", UtGen.AC, 0, 0, "leer: lee un valor entero ");
		direccion = tablaSimbolos.getDireccion(n.getIdentificador());
		UtGen.emitirRM("ST", UtGen.AC, direccion, UtGen.GP, "leer: almaceno el valor entero leido en el id "+n.getIdentificador());
		if(UtGen.debug)	UtGen.emitirComentario("<- leer");
	}

	private static void generarLeerComplex(NodoBase nodo){
        NodoLeerComplex n = (NodoLeerComplex)nodo;
        if(UtGen.debug)	UtGen.emitirComentario("-> leer");
        if(n.getIdentificador() instanceof NodoIdentificador){
            NodoIdentificador id = (NodoIdentificador)n.getIdentificador();
			int direccion = tablaSimbolos.getDireccion(id.getNombre());
			UtGen.emitirRM("LDA", UtGen.AC, direccion, UtGen.GP, "cargar direccion de identificador: " + id.getNombre());
			UtGen.emitirRO("IN", UtGen.AC, 0, 0, "leer el valor para el identificador " + id.getNombre());
			UtGen.emitirRM("RDI", UtGen.AC, direccion, UtGen.GP, "almacenar el valor leido en el identificador " + id.getNombre());
		} else if (n.getIdentificador() instanceof NodoVector) {
			NodoVector vector = (NodoVector) n.getIdentificador();
			NodoIdentificador id = (NodoIdentificador) vector.getIdentificador();
			int direccion = tablaSimbolos.getDireccion(id.getNombre());
			UtGen.emitirRM("LDA", UtGen.AC, direccion, UtGen.GP, "cargar direccion de la variable: " + id.getNombre());
			
			if (vector.getExpresion() instanceof NodoOperacion) {
				generarOperacion(vector.getExpresion());
			} else {
				generar(vector.getExpresion());
			}
			UtGen.emitirRO("ADD", UtGen.AC, UtGen.AC, 1, "cargar la direccion de la posicion del vector: " + id.getNombre());
			UtGen.emitirRO("IN", UtGen.AC1, 0, 0, "leer el valor para el identificador " + id.getNombre());
			UtGen.emitirRM("RDI", UtGen.AC1, 0, UtGen.AC, "almacenar el valor leido en el vector " + id.getNombre());
        }
        if(UtGen.debug)	UtGen.emitirComentario("<- leer");
    }
	
	private static void generarEscribir(NodoBase nodo){
		NodoEscribir n = (NodoEscribir)nodo;
		if(UtGen.debug)	UtGen.emitirComentario("-> escribir");
		/* Genero el codigo de la expresion que va a ser escrita en pantalla */
		generar(n.getExpresion());
		/* Ahora genero la salida */
		UtGen.emitirRO("OUT", UtGen.AC, 0, 0, "escribir: genero la salida de la expresion");
		if(UtGen.debug)	UtGen.emitirComentario("<- escribir");
	}
	
	private static void generarValor(NodoBase nodo){
    	NodoValor n = (NodoValor)nodo;
    	if(UtGen.debug)	UtGen.emitirComentario("-> constante");
    	UtGen.emitirRM("LDC", UtGen.AC, n.getValor(), 0, "cargar constante: "+n.getValor());
    	if(UtGen.debug)	UtGen.emitirComentario("<- constante");
	}
	
	private static void generarIdentificador(NodoBase nodo){
		NodoIdentificador n = (NodoIdentificador)nodo;
		int direccion;
		if(UtGen.debug)	UtGen.emitirComentario("-> identificador");
		direccion = tablaSimbolos.getDireccion(n.getNombre());
		UtGen.emitirRM("LD", UtGen.AC, direccion, UtGen.GP, "cargar valor de identificador: "+n.getNombre());
		if(UtGen.debug)	UtGen.emitirComentario("-> identificador");
	}

	private static void generarOperacion(NodoBase nodo){
		NodoOperacion n = (NodoOperacion) nodo;
		if(UtGen.debug)	UtGen.emitirComentario("-> Operacion: " + n.getOperacion());
		/* Genero la expresion izquierda de la operacion */
		generar(n.getOpIzquierdo());
		/* Almaceno en la pseudo pila de valor temporales el valor de la operacion izquierda */
		UtGen.emitirRM("ST", UtGen.AC, desplazamientoTmp--, UtGen.MP, "op: push en la pila tmp el resultado expresion izquierda");
		/* Genero la expresion derecha de la operacion */
		generar(n.getOpDerecho());
		/* Ahora cargo/saco de la pila el valor izquierdo */
		UtGen.emitirRM("LD", UtGen.AC1, ++desplazamientoTmp, UtGen.MP, "op: pop o cargo de la pila el valor izquierdo en AC1");
		switch(n.getOperacion()){
			case	mas:	UtGen.emitirRO("ADD", UtGen.AC, UtGen.AC1, UtGen.AC, "op: +");		
							break;
			case	menos:	UtGen.emitirRO("SUB", UtGen.AC, UtGen.AC1, UtGen.AC, "op: -");
							break;
			case	por:	UtGen.emitirRO("MUL", UtGen.AC, UtGen.AC1, UtGen.AC, "op: *");
							break;
			case	entre:	UtGen.emitirRO("DIV", UtGen.AC, UtGen.AC1, UtGen.AC, "op: /");
							break;		
			case	menor:	UtGen.emitirRO("SUB", UtGen.AC, UtGen.AC1, UtGen.AC, "op: <");
							UtGen.emitirRM("JLT", UtGen.AC, 2, UtGen.PC, "voy dos instrucciones mas alla if verdadero (AC<0)");
							UtGen.emitirRM("LDC", UtGen.AC, 0, UtGen.AC, "caso de falso (AC=0)");
							UtGen.emitirRM("LDA", UtGen.PC, 1, UtGen.PC, "Salto incodicional a direccion: PC+1 (es falso evito colocarlo verdadero)");
							UtGen.emitirRM("LDC", UtGen.AC, 1, UtGen.AC, "caso de verdadero (AC=1)");
							break;
			case	mayor:	
							UtGen.emitirRO("SUB", UtGen.AC, UtGen.AC, UtGen.AC1, "op: >");
							UtGen.emitirRM("JLT", UtGen.AC, 2, UtGen.PC, "voy dos instrucciones mas alla if verdadero (AC>0)");
							UtGen.emitirRM("LDC", UtGen.AC, 0, UtGen.AC, "caso de falso (AC=0)");
							UtGen.emitirRM("LDA", UtGen.PC, 1, UtGen.PC, "Salto incodicional a direccion: PC+1 (es falso evito colocarlo verdadero)");
							UtGen.emitirRM("LDC", UtGen.AC, 1, UtGen.AC, "caso de verdadero (AC=1)");
							break;	
			case	menorigual:	
							UtGen.emitirRO("SUB", UtGen.AC, UtGen.AC1, UtGen.AC, "op: <=");
							UtGen.emitirRM("JLE", UtGen.AC, 2, UtGen.PC, "voy dos instrucciones mas alla if verdadero (AC<=0)");
							UtGen.emitirRM("LDC", UtGen.AC, 0, UtGen.AC, "caso de falso (AC=0)");
							UtGen.emitirRM("LDA", UtGen.PC, 1, UtGen.PC, "Salto incodicional a direccion: PC+1 (es falso evito colocarlo verdadero)");
							UtGen.emitirRM("LDC", UtGen.AC, 1, UtGen.AC, "caso de verdadero (AC=1)");
							break;
			case	mayorigual:	
							UtGen.emitirRO("SUB", UtGen.AC, UtGen.AC, UtGen.AC1, "op: >=");
							UtGen.emitirRM("JLE", UtGen.AC, 2, UtGen.PC, "voy dos instrucciones mas alla if verdadero (AC>=0)");
							UtGen.emitirRM("LDC", UtGen.AC, 0, UtGen.AC, "caso de falso (AC=0)");
							UtGen.emitirRM("LDA", UtGen.PC, 1, UtGen.PC, "Salto incodicional a direccion: PC+1 (es falso evito colocarlo verdadero)");
							UtGen.emitirRM("LDC", UtGen.AC, 1, UtGen.AC, "caso de verdadero (AC=1)");
							break;
			case	diferente:	
							UtGen.emitirRO("SUB", UtGen.AC, UtGen.AC1, UtGen.AC, "op: <>");
							UtGen.emitirRM("JNE", UtGen.AC, 2, UtGen.PC, "voy dos instrucciones mas alla if verdadero (AC<>0)");
							UtGen.emitirRM("LDC", UtGen.AC, 0, UtGen.AC, "caso de falso (AC=0)");
							UtGen.emitirRM("LDA", UtGen.PC, 1, UtGen.PC, "Salto incodicional a direccion: PC+1 (es falso evito colocarlo verdadero)");
							UtGen.emitirRM("LDC", UtGen.AC, 1, UtGen.AC, "caso de verdadero (AC=1)");
							break;															
			case	igual:	
							UtGen.emitirRO("SUB", UtGen.AC, UtGen.AC1, UtGen.AC, "op: ==");
							UtGen.emitirRM("JEQ", UtGen.AC, 2, UtGen.PC, "voy dos instrucciones mas alla if verdadero (AC==0)");
							UtGen.emitirRM("LDC", UtGen.AC, 0, UtGen.AC, "caso de falso (AC=0)");
							UtGen.emitirRM("LDA", UtGen.PC, 1, UtGen.PC, "Salto incodicional a direccion: PC+1 (es falso evito colocarlo verdadero)");
							UtGen.emitirRM("LDC", UtGen.AC, 1, UtGen.AC, "caso de verdadero (AC=1)");
							break;	
			case 	and:
							UtGen.emitirRM("JEQ", UtGen.AC1, 2, UtGen.PC, "voy dos instrucciones mas alla si hay corto circuito (AC1 == 0)"); //  significa que la exp logica es falsa
							UtGen.emitirRO("MUL", UtGen.AC, UtGen.AC1, UtGen.AC, "operacion logica AND, se Multiplica AC * AC1");
							UtGen.emitirRM("JNE", UtGen.AC, 2, UtGen.PC, "voy dos instrucciones mas alla si es verdadero (AC != 0)");
							UtGen.emitirRM("LDC", UtGen.AC, 0, UtGen.AC, "caso de falso (AC=0)");
							UtGen.emitirRM("LDA", UtGen.PC, 1, UtGen.PC, "Salto incodicional a direccion: PC+1 (es falso evito colocarlo verdadero)");
							UtGen.emitirRM("LDC", UtGen.AC, 1, UtGen.AC, "caso de verdadero (AC=1)");
							break;
			case	or:	
							UtGen.emitirRO("ADD", UtGen.AC, UtGen.AC1, UtGen.AC, "operacion logica suma entre AC y AC1");
							UtGen.emitirRM("JNE", UtGen.AC, 2, UtGen.PC, "verificar que AC sea diferente de 0");
							UtGen.emitirRM("LDC", UtGen.AC, 0, UtGen.AC, "caso de falso (AC=0)");
							UtGen.emitirRM("LDA", UtGen.PC, 1, UtGen.PC, "Salto incodicional a direccion: PC+1 (es falso evito colocarlo verdadero)");
							UtGen.emitirRM("LDC", UtGen.AC, 1, UtGen.AC, "caso de verdadero (AC=1)");
				break;
			case	not:	UtGen.emitirRM("LDC", UtGen.AC1, 1, 0, "Carga constante 1 en AC1");
							UtGen.emitirRO("SUB", UtGen.AC, UtGen.AC1, UtGen.AC, "op NOT");
							UtGen.emitirRM("JNE", UtGen.AC, 2, UtGen.PC, "Salto 2 instrucciones si es True (ACC==0)");
							UtGen.emitirRM("LDC", UtGen.AC, 0, UtGen.AC, "caso de falso (AC=0)");
							UtGen.emitirRM("LDA", UtGen.PC, 1, UtGen.PC, "Salto incodicional a direccion: PC+1 (es falso evito colocarlo verdadero)");
							UtGen.emitirRM("LDC", UtGen.AC, 1, UtGen.AC, "caso de verdadero (AC=1)");
						break;	
			default:
							UtGen.emitirComentario("BUG: tipo de operacion desconocida");
		}
		if(UtGen.debug)	UtGen.emitirComentario("<- Operacion: " + n.getOperacion());
		}

		private static void generarVector(NodoBase nodo){
			NodoVector n = (NodoVector) nodo;
			NodoIdentificador ni = (NodoIdentificador) n.getIdentificador();
			if(UtGen.debug)	UtGen.emitirComentario("-> vector: " + ni.getNombre());
			int direccion = tablaSimbolos.getDireccion(ni.getNombre());
			UtGen.emitirRM("LDA", UtGen.AC, direccion, UtGen.GP, "cargar direccion de la variable: " + ni.getNombre());
			
			if (n.getExpresion() instanceof NodoOperacion){
				generarOperacion(n.getExpresion());
			} else {
				generar(n.getExpresion());
			}
			
			UtGen.emitirRO("ADD", UtGen.AC, UtGen.AC, 1, "cargar la direccion de la posicion del vector: " + ni.getNombre());
			UtGen.emitirRM("LD", UtGen.AC, 0, UtGen.AC, "cargar el valor de la direccion anterior");
			if(UtGen.debug)	UtGen.emitirComentario("-> vector: " + ni.getNombre());
		}
	//TODO: enviar preludio a archivo de salida, obtener antes su nombre
	private static void generarPreludioEstandar(){
		UtGen.emitirComentario("Compilacion TINY para el codigo objeto TM");
		UtGen.emitirComentario("Archivo: "+ "NOMBRE_ARREGLAR");
		/*Genero inicializaciones del preludio estandar*/
		/*Todos los registros en tiny comienzan en cero*/
		UtGen.emitirComentario("Preludio estandar:");
		UtGen.emitirRM("LD", UtGen.MP, 0, UtGen.AC, "cargar la maxima direccion desde la localidad 0");
		UtGen.emitirRM("ST", UtGen.AC, 0, UtGen.AC, "limpio el registro de la localidad 0");
	}

}
