package comp.nodosAST;

public class NodoFor extends NodoBase {
    private NodoBase cuerpo;
    private NodoBase prueba;
    private NodoBase asignaP;
    private NodoBase asignaT;

    public NodoFor(NodoBase asignaP, NodoBase prueba, NodoBase asignaT, NodoBase cuerpo) {
        this.cuerpo = cuerpo;
        this.prueba = prueba;
        this.asignaP = asignaP;
        this.asignaT = asignaT;
    }

    public NodoFor() {
        this.cuerpo = null;
        this.prueba = null;
        this.asignaP = null;
        this.asignaT = null;
    }

    public NodoBase getCuerpo() {
        return this.cuerpo;
    }

    public NodoBase getAsignaP() {
        return this.asignaP;
    }

    public void setAsignaP(NodoBase asignaP) {
        this.asignaP = asignaP;
    }

    public NodoBase getAsignaT() {
        return this.asignaT;
    }

    public void setAsignaT(NodoBase asignaT) {
        this.asignaT = asignaT;
    }

    public void setCuerpo(NodoBase cuerpo) {
        this.cuerpo = cuerpo;
    }

    public NodoBase getPrueba() {
        return this.prueba;
    }

    public void setPrueba(NodoBase prueba) {
        this.prueba = prueba;
    }
}
