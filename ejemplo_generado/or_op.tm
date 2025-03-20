0:       LD       6,0(0)      cargar la maxima direccion desde la localidad 0
1:       ST       0,0(0)      limpio el registro de la localidad 0
*      -> asignacion
*      -> constante
2:       LDC       0,0(0)      cargar constante: 0
*      <- constante
3:       ST       0,0(5)      asignacion simple: almaceno el valor para el id factx
*      <- asignacion
*      -> asignacion
*      -> constante
4:       LDC       0,0(0)      cargar constante: 0
*      <- constante
5:       ST       0,1(5)      asignacion simple: almaceno el valor para el id facty
*      <- asignacion
*      -> if
*      -> Operacion: or
*      -> Operacion: igual
*      -> identificador
6:       LD       0,0(5)      cargar valor de identificador: factx
*      -> identificador
7:       ST       0,0(6)      op: push en la pila tmp el resultado expresion izquierda
*      -> constante
8:       LDC       0,1(0)      cargar constante: 1
*      <- constante
9:       LD       1,0(6)      op: pop o cargo de la pila el valor izquierdo en AC1
10:       SUB       0,1,0      op: ==
11:       JEQ       0,2(7)      voy dos instrucciones mas alla if verdadero (AC==0)
12:       LDC       0,0(0)      caso de falso (AC=0)
13:       LDA       7,1(7)      Salto incodicional a direccion: PC+1 (es falso evito colocarlo verdadero)
14:       LDC       0,1(0)      caso de verdadero (AC=1)
*      <- Operacion: igual
15:       ST       0,0(6)      op: push en la pila tmp el resultado expresion izquierda
*      -> Operacion: igual
*      -> identificador
16:       LD       0,1(5)      cargar valor de identificador: facty
*      -> identificador
17:       ST       0,-1(6)      op: push en la pila tmp el resultado expresion izquierda
*      -> constante
18:       LDC       0,1(0)      cargar constante: 1
*      <- constante
19:       LD       1,-1(6)      op: pop o cargo de la pila el valor izquierdo en AC1
20:       SUB       0,1,0      op: ==
21:       JEQ       0,2(7)      voy dos instrucciones mas alla if verdadero (AC==0)
22:       LDC       0,0(0)      caso de falso (AC=0)
23:       LDA       7,1(7)      Salto incodicional a direccion: PC+1 (es falso evito colocarlo verdadero)
24:       LDC       0,1(0)      caso de verdadero (AC=1)
*      <- Operacion: igual
25:       LD       1,0(6)      op: pop o cargo de la pila el valor izquierdo en AC1
26:       ADD       0,1,0      operacion logica suma entre AC y AC1
27:       JNE       0,2(7)      verificar que AC sea diferente de 0
28:       LDC       0,0(0)      caso de falso (AC=0)
29:       LDA       7,1(7)      Salto incodicional a direccion: PC+1 (es falso evito colocarlo verdadero)
30:       LDC       0,1(0)      caso de verdadero (AC=1)
*      <- Operacion: or
*      If: el salto hacia el else debe estar aqui
*      -> escribir
*      -> constante
32:       LDC       0,1(0)      cargar constante: 1
*      <- constante
33:       OUT       0,0,0      escribir: genero la salida de la expresion
*      <- escribir
*      If: el salto hacia el final debe estar aqui
31:       JEQ       0,3(7)      if: jmp hacia else
*      -> escribir
*      -> constante
35:       LDC       0,0(0)      cargar constante: 0
*      <- constante
36:       OUT       0,0,0      escribir: genero la salida de la expresion
*      <- escribir
34:       LDA       7,2(7)      if: jmp hacia el final
*      <- if
*      Fin de la ejecucion.
37:       HALT       0,0,0