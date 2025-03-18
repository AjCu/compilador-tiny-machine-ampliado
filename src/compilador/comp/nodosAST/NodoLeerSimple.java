package comp.nodosAST;

public class NodoLeerSimple extends NodoBase {
	private String id;

	public NodoLeerSimple(String identificador) {
		super();
		this.id = identificador;
	}

	public NodoLeerSimple() {
		super();
		id="";
	}

	public String getIdentificador() {
		return id;
	}

	public void setExpresion(String identificador) {
		this.id = identificador;
	}

}
