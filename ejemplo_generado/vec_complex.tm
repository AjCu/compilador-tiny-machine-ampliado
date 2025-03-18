0:       LD       6,0(0)      cargar la maxima direccion desde la localidad 0
1:       ST       0,0(0)      limpio el registro de la localidad 0
2:       LDA       0,5(5)      cargar direccion de identificador: b
*      -> constante
3:       LDC       0,2(0)      cargar constante: 2
*      <- constante
4:       ADD       0,0,1      cargar direccion de identificador: b
*      -> constante
5:       LDC       0,2(0)      cargar constante: 2
*      <- constante
6:       ST       0,0(1)      asignacion: almaceno el valor para el id b
7:       LDA       0,0(5)      cargar direccion de identificador: a
*      -> Operacion: mas
*      -> constante
8:       LDC       0,1(0)      cargar constante: 1
*      <- constante
9:       ST       0,0(6)      op: push en la pila tmp el resultado expresion izquierda
*      -> constante
10:       LDC       0,2(0)      cargar constante: 2
*      <- constante
11:       LD       1,0(6)      op: pop o cargo de la pila el valor izquierdo en AC1
12:       ADD       0,1,0      op: +
*      <- Operacion: mas
13:       ADD       0,0,1      cargar direccion de identificador: a
*      -> vector: b
14:       LDA       0,5(5)      cargar direccion de la variable: b
*      -> Operacion: mas
*      -> constante
15:       LDC       0,1(0)      cargar constante: 1
*      <- constante
16:       ST       0,0(6)      op: push en la pila tmp el resultado expresion izquierda
*      -> Operacion: por
*      -> constante
17:       LDC       0,1(0)      cargar constante: 1
*      <- constante
18:       ST       0,-1(6)      op: push en la pila tmp el resultado expresion izquierda
*      -> constante
19:       LDC       0,1(0)      cargar constante: 1
*      <- constante
20:       LD       1,-1(6)      op: pop o cargo de la pila el valor izquierdo en AC1
21:       MUL       0,1,0      op: *
*      <- Operacion: por
22:       LD       1,0(6)      op: pop o cargo de la pila el valor izquierdo en AC1
23:       ADD       0,1,0      op: +
*      <- Operacion: mas
24:       ADD       0,0,1      cargar la direccion de la posicion del vector: b
25:       LD       0,0(0)      cargar el valor de la direccion anterior
*      -> vector: b
26:       ST       0,0(1)      asignacion: almaceno el valor para el id a
*      -> escribir
*      -> vector: a
27:       LDA       0,0(5)      cargar direccion de la variable: a
*      -> constante
28:       LDC       0,3(0)      cargar constante: 3
*      <- constante
29:       ADD       0,0,1      cargar la direccion de la posicion del vector: a
30:       LD       0,0(0)      cargar el valor de la direccion anterior
*      -> vector: a
31:       OUT       0,0,0      escribir: genero la salida de la expresion
*      <- escribir
*      Fin de la ejecucion.
32:       HALT       0,0,0