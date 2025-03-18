package comp.nodosAST;

public class NodoAsignacionComplex extends NodoBase {
	private NodoBase identificador;
	private NodoBase expresion;
	
	public NodoAsignacionComplex(String identificador) {
		super();
		this.identificador = new NodoIdentificador(identificador);
		this.expresion = null;
	}
	
	public NodoAsignacionComplex(NodoBase identificador, NodoBase expresion) {
		super();
		this.identificador = identificador;
		this.expresion = expresion;
	}

	public NodoBase getIdentificador() {
		return this.identificador;
	}

	public void setIdentificador(NodoBase identificador) {
		this.identificador = identificador;
	}

	public NodoBase getExpresion() {
		return expresion;
	}

	public void setExpresion(NodoBase expresion) {
		this.expresion = expresion;
	}
	
	
	
}
