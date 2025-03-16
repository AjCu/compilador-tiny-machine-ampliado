0:       LD       6,0(0)      cargar la maxima direccion desde la localidad 0
1:       ST       0,0(0)      limpio el registro de la localidad 0
*      -> for
*      -> asignacion
*      -> constante
2:       LDC       0,0(0)      cargar constante: 0
*      <- constante
3:       ST       0,0(5)      asignacion: almaceno el valor para el id y
*      <- asignacion
*      -> Operacion: menor
*      -> identificador
4:       LD       0,0(5)      cargar valor de identificador: y
*      -> identificador
5:       ST       0,0(6)      op: push en la pila tmp el resultado expresion izquierda
*      -> constante
6:       LDC       0,3(0)      cargar constante: 3
*      <- constante
7:       LD       1,0(6)      op: pop o cargo de la pila el valor izquierdo en AC1
8:       SUB       0,1,0      op: <
9:       JLT       0,2(7)      voy dos instrucciones mas alla if verdadero (AC<0)
10:       LDC       0,0(0)      caso de falso (AC=0)
11:       LDA       7,1(7)      Salto incodicional a direccion: PC+1 (es falso evito colocarlo verdadero)
12:       LDC       0,1(0)      caso de verdadero (AC=1)
*      <- Operacion: menor
*      -> escribir
*      -> identificador
14:       LD       0,0(5)      cargar valor de identificador: y
*      -> identificador
15:       OUT       0,0,0      escribir: genero la salida de la expresion
*      <- escribir
*      -> asignacion
*      -> Operacion: mas
*      -> identificador
16:       LD       0,0(5)      cargar valor de identificador: y
*      -> identificador
17:       ST       0,0(6)      op: push en la pila tmp el resultado expresion izquierda
*      -> constante
18:       LDC       0,1(0)      cargar constante: 1
*      <- constante
19:       LD       1,0(6)      op: pop o cargo de la pila el valor izquierdo en AC1
20:       ADD       0,1,0      op: +
*      <- Operacion: mas
21:       ST       0,0(5)      asignacion: almaceno el valor para el id y
*      <- asignacion
22:       LDA       7,-19(7)      for: jmp hacia el inicio
13:       JEQ       0,9(7)      for: jmp hacia el final
*      Fin de la ejecucion.
23:       HALT       0,0,0