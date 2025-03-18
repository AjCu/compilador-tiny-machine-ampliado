package comp;


import java.util.*;

import comp.nodosAST.*;




public class TablaSimbolos {
	private HashMap<String, RegistroSimbolo> tabla;
	private int direccion;  //Contador de las localidades de memoria asignadas a la tabla
	
	public TablaSimbolos() {
		super();
		tabla = new HashMap<String, RegistroSimbolo>();
		direccion=0;
	}

	public void cargarTabla(NodoBase raiz) throws Exception{
		while (raiz != null) {
	    if (raiz instanceof NodoIdentificador){
	    	InsertarSimbolo(((NodoIdentificador)raiz).getNombre(),-1);
	    	//TODO: A�adir el numero de linea y localidad de memoria correcta
	    }
		else if(raiz instanceof NodoVector){
			NodoVector vector = (NodoVector)raiz;
			boolean inserto = false;
			if(vector.isDeclaracion()){
				cargarTabla(vector.getExpresion());
				int direccionesReservadas = ((NodoValor)vector.getExpresion()).getValor();
				if(BuscarSimbolo(((NodoIdentificador)vector.getIdentificador()).getNombre()) == null){
					InsertarSimbolo(((NodoIdentificador)vector.getIdentificador()).getNombre(), -1);
					this.direccion += direccionesReservadas-1;
				}else{
					throw new Exception("El vector "+((NodoIdentificador)vector.getIdentificador()).getNombre()+" ya esta declarado");
				}
			}
			else{
				String identificador = ((NodoIdentificador)vector.getIdentificador()).getNombre();
				RegistroSimbolo simbolo = BuscarSimbolo(identificador);
				if (simbolo == null){ throw new Exception("El vector "+((NodoIdentificador)vector.getIdentificador()).getNombre()+" no esta esta declarado");}
				cargarTabla(vector.getExpresion());
			}
			//
		}

	    /* Hago el recorrido recursivo */
	    if (raiz instanceof  NodoIf){
	    	cargarTabla(((NodoIf)raiz).getPrueba());
	    	cargarTabla(((NodoIf)raiz).getParteThen());
	    	if(((NodoIf)raiz).getParteElse()!=null){
	    		cargarTabla(((NodoIf)raiz).getParteElse());
	    	}
	    }
	    else if (raiz instanceof  NodoRepeat){
	    	cargarTabla(((NodoRepeat)raiz).getCuerpo());
	    	cargarTabla(((NodoRepeat)raiz).getPrueba());
	    }
		else if (raiz instanceof  NodoFor){
			cargarTabla(((NodoFor)raiz).getAsignaP());
			cargarTabla(((NodoFor)raiz).getPrueba());
			cargarTabla(((NodoFor)raiz).getCuerpo());
			cargarTabla(((NodoFor)raiz).getAsignaT());
		}
	    else if (raiz instanceof  NodoAsignacionSimple)
	    	cargarTabla(((NodoAsignacionSimple)raiz).getExpresion());
		else if (raiz instanceof  NodoAsignacionComplex){
	    	cargarTabla(((NodoAsignacionComplex)raiz).getExpresion());	
			if (((NodoAsignacionComplex) raiz).getIdentificador() instanceof NodoIdentificador) {
				InsertarSimbolo(((NodoIdentificador) ((NodoAsignacionComplex) raiz).getIdentificador()).getNombre(), -1);
			} else {
				cargarTabla(((NodoAsignacionComplex) raiz).getIdentificador());
			}
		}
	    else if (raiz instanceof  NodoEscribir)
	    	cargarTabla(((NodoEscribir)raiz).getExpresion());
	    else if (raiz instanceof NodoOperacion){
	    	cargarTabla(((NodoOperacion)raiz).getOpIzquierdo());
	    	cargarTabla(((NodoOperacion)raiz).getOpDerecho());
	    }
	    raiz = raiz.getHermanoDerecha();
	  }
	}
	
	//true es nuevo no existe se insertara, false ya existe NO se vuelve a insertar 
	public boolean InsertarSimbolo(String identificador, int numLinea){
		RegistroSimbolo simbolo;
		if(tabla.containsKey(identificador)){
			return false;
		}else{
			simbolo= new RegistroSimbolo(identificador,numLinea,direccion++);
			tabla.put(identificador,simbolo);
			return true;			
		}
	}
	
	public RegistroSimbolo BuscarSimbolo(String identificador){
		RegistroSimbolo simbolo=(RegistroSimbolo)tabla.get(identificador);
		return simbolo;
	}
	
	public void ImprimirClaves(){
		System.out.println("*** Tabla de Simbolos ***");
		for( Iterator <String>it = tabla.keySet().iterator(); it.hasNext();) { 
            String s = (String)it.next();
	    System.out.println("Consegui Key: "+s+" con direccion: " + BuscarSimbolo(s).getDireccionMemoria());
		}
	}

	public int getDireccion(String Clave){
		return BuscarSimbolo(Clave).getDireccionMemoria();
	}
	
	/*
	 * TODO:
	 * 1. Crear lista con las lineas de codigo donde la variable es usada.
	 * */
}
