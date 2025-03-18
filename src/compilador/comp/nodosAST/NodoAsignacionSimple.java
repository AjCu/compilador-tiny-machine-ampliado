package comp.nodosAST;

public class NodoAsignacionSimple extends NodoBase {
	private String identificador;
	private NodoBase expresion;
	
	public NodoAsignacionSimple(String identificador) {
		super();
		this.identificador = identificador;
		this.expresion = null;
	}
	
	public NodoAsignacionSimple(String identificador, NodoBase expresion) {
		super();
		this.identificador = identificador;
		this.expresion = expresion;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public NodoBase getExpresion() {
		return expresion;
	}

	public void setExpresion(NodoBase expresion) {
		this.expresion = expresion;
	}
	
	
	
}
