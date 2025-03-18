package comp.nodosAST;

public class NodoLeerComplex extends NodoBase {
	private NodoBase identificador;

	public NodoLeerComplex(NodoBase identificador){
		super();
		this.identificador = identificador;
	}

	public NodoLeerComplex() {
		super();
		identificador=null;
	}

	public NodoBase getIdentificador() {
		return identificador;
	}

	public void setIdentificador(NodoBase identificador) {
		this.identificador = identificador;
	}

}
