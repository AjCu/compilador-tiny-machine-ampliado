0:       LD       6,0(0)      cargar la maxima direccion desde la localidad 0
1:       ST       0,0(0)      limpio el registro de la localidad 0
*      -> Operacion: por
*      -> constante
2:       LDC       0,2(0)      cargar constante: 2
*      <- constante
3:       ST       0,0(6)      op: push en la pila tmp el resultado expresion izquierda
*      -> constante
4:       LDC       0,2(0)      cargar constante: 2
*      <- constante
5:       LD       1,0(6)      op: pop o cargo de la pila el valor izquierdo en AC1
6:       MUL       0,1,0      op: *
*      <- Operacion: por
7:       ST       0,0(5)      asignacion compleja: almaceno el valor para el id b
*      -> constante
8:       LDC       0,1(0)      cargar constante: 1
*      <- constante
9:       ST       0,1(5)      asignacion compleja: almaceno el valor para el id c
*      -> escribir
*      -> identificador
10:       LD       0,1(5)      cargar valor de identificador: c
*      -> identificador
11:       OUT       0,0,0      escribir: genero la salida de la expresion
*      <- escribir
*      -> escribir
*      -> identificador
12:       LD       0,0(5)      cargar valor de identificador: b
*      -> identificador
13:       OUT       0,0,0      escribir: genero la salida de la expresion
*      <- escribir
*      Fin de la ejecucion.
14:       HALT       0,0,0