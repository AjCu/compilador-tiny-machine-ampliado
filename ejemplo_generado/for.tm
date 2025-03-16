0:       LD       6,0(0)      cargar la maxima direccion desde la localidad 0
1:       ST       0,0(0)      limpio el registro de la localidad 0
*      -> asignacion
*      -> Operacion: mas
*      -> identificador
2:       LD       0,0(5)      cargar valor de identificador: y
*      -> identificador
3:       ST       0,0(6)      op: push en la pila tmp el resultado expresion izquierda
*      -> constante
4:       LDC       0,1(0)      cargar constante: 1
*      <- constante
5:       LD       1,0(6)      op: pop o cargo de la pila el valor izquierdo en AC1
6:       ADD       0,1,0      op: +
*      <- Operacion: mas
7:       ST       0,0(5)      asignacion: almaceno el valor para el id y
*      <- asignacion
*      Fin de la ejecucion.
8:       HALT       0,0,0