package comp.nodosAST;

public class NodoOperacionUnaria {
    
	private NodoBase opDerecho;
	private tipoOp operacion;

	public NodoOperacionUnaria(tipoOp tipoOperacion, NodoBase opDerecho) {
		super();
		this.opDerecho = opDerecho;
		this.operacion = tipoOperacion;
	}

	public NodoOperacionUnaria(tipoOp tipoOperacion) {
		super();
		this.opDerecho = null;
		this.operacion = tipoOperacion;
	}


	public NodoBase getOpDerecho() {
		return opDerecho;
	}

	public void setOpDerecho(NodoBase opDerecho) {
		this.opDerecho = opDerecho;
	}

	public tipoOp getOperacion() {
		return operacion;
	}


}
