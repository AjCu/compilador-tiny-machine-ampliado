0:       LD       6,0(0)      cargar la maxima direccion desde la localidad 0
1:       ST       0,0(0)      limpio el registro de la localidad 0
2:       LDA       0,0(5)      cargar direccion de identificador: a
*      -> Operacion: mas
*      -> constante
3:       LDC       0,1(0)      cargar constante: 1
*      <- constante
4:       ST       0,0(6)      op: push en la pila tmp el resultado expresion izquierda
*      -> constante
5:       LDC       0,2(0)      cargar constante: 2
*      <- constante
6:       LD       1,0(6)      op: pop o cargo de la pila el valor izquierdo en AC1
7:       ADD       0,1,0      op: +
*      <- Operacion: mas
8:       ADD       0,0,1      cargar direccion de identificador: a
*      -> constante
9:       LDC       0,1(0)      cargar constante: 1
*      <- constante
10:       ST       0,0(1)      asignacion: almaceno el valor para el id a
*      -> escribir
11:       LDA       0,0(5)      cargar direccion de la variable: a
*      -> constante
12:       LDC       0,3(0)      cargar constante: 3
*      <- constante
13:       ADD       0,0,1      cargar la direccion de la posicion del vector: a
14:       LD       0,0(0)      cargar el valor de la direccion anterior
15:       OUT       0,0,0      escribir: genero la salida de la expresion
*      <- escribir
*      Fin de la ejecucion.
16:       HALT       0,0,0