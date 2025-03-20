0:       LD       6,0(0)      cargar la maxima direccion desde la localidad 0
1:       ST       0,0(0)      limpio el registro de la localidad 0
*      -> constante
2:       LDC       0,8(0)      cargar constante: 8
*      <- constante
3:       ST       0,0(5)      asignacion compleja: almaceno el valor para el id z
*      -> constante
4:       LDC       0,8(0)      cargar constante: 8
*      <- constante
5:       ST       0,1(5)      asignacion compleja: almaceno el valor para el id x
*      -> if
*      -> Operacion: menor
*      -> identificador
6:       LD       0,0(5)      cargar valor de identificador: z
*      -> identificador
7:       ST       0,0(6)      op: push en la pila tmp el resultado expresion izquierda
*      -> Operacion: mas
*      -> identificador
8:       LD       0,1(5)      cargar valor de identificador: x
*      -> identificador
9:       ST       0,-1(6)      op: push en la pila tmp el resultado expresion izquierda
*      -> constante
10:       LDC       0,3(0)      cargar constante: 3
*      <- constante
11:       LD       1,-1(6)      op: pop o cargo de la pila el valor izquierdo en AC1
12:       ADD       0,1,0      op: +
*      <- Operacion: mas
13:       LD       1,0(6)      op: pop o cargo de la pila el valor izquierdo en AC1
14:       SUB       0,1,0      op: <
15:       JLT       0,2(7)      voy dos instrucciones mas alla if verdadero (AC<0)
16:       LDC       0,0(0)      caso de falso (AC=0)
17:       LDA       7,1(7)      Salto incodicional a direccion: PC+1 (es falso evito colocarlo verdadero)
18:       LDC       0,1(0)      caso de verdadero (AC=1)
*      <- Operacion: menor
*      If: el salto hacia el else debe estar aqui
*      -> if
*      -> Operacion: igual
*      -> identificador
20:       LD       0,0(5)      cargar valor de identificador: z
*      -> identificador
21:       ST       0,0(6)      op: push en la pila tmp el resultado expresion izquierda
*      -> constante
22:       LDC       0,0(0)      cargar constante: 0
*      <- constante
23:       LD       1,0(6)      op: pop o cargo de la pila el valor izquierdo en AC1
24:       SUB       0,1,0      op: ==
25:       JEQ       0,2(7)      voy dos instrucciones mas alla if verdadero (AC==0)
26:       LDC       0,0(0)      caso de falso (AC=0)
27:       LDA       7,1(7)      Salto incodicional a direccion: PC+1 (es falso evito colocarlo verdadero)
28:       LDC       0,1(0)      caso de verdadero (AC=1)
*      <- Operacion: igual
*      If: el salto hacia el else debe estar aqui
*      -> constante
30:       LDC       0,8(0)      cargar constante: 8
*      <- constante
31:       ST       0,0(5)      asignacion compleja: almaceno el valor para el id z
*      -> if
*      -> Operacion: igual
*      -> identificador
32:       LD       0,0(5)      cargar valor de identificador: z
*      -> identificador
33:       ST       0,0(6)      op: push en la pila tmp el resultado expresion izquierda
*      -> constante
34:       LDC       0,8(0)      cargar constante: 8
*      <- constante
35:       LD       1,0(6)      op: pop o cargo de la pila el valor izquierdo en AC1
36:       SUB       0,1,0      op: ==
37:       JEQ       0,2(7)      voy dos instrucciones mas alla if verdadero (AC==0)
38:       LDC       0,0(0)      caso de falso (AC=0)
39:       LDA       7,1(7)      Salto incodicional a direccion: PC+1 (es falso evito colocarlo verdadero)
40:       LDC       0,1(0)      caso de verdadero (AC=1)
*      <- Operacion: igual
*      If: el salto hacia el else debe estar aqui
*      -> Operacion: por
*      -> constante
42:       LDC       0,5(0)      cargar constante: 5
*      <- constante
43:       ST       0,0(6)      op: push en la pila tmp el resultado expresion izquierda
*      -> constante
44:       LDC       0,9(0)      cargar constante: 9
*      <- constante
45:       LD       1,0(6)      op: pop o cargo de la pila el valor izquierdo en AC1
46:       MUL       0,1,0      op: *
*      <- Operacion: por
47:       ST       0,0(5)      asignacion compleja: almaceno el valor para el id z
43:       ST       0,0(6)      op: push en la pila tmp el resultado expresion izquierda
*      -> constante
44:       LDC       0,9(0)      cargar constante: 9
*      <- constante
45:       LD       1,0(6)      op: pop o cargo de la pila el valor izquierdo en AC1
46:       MUL       0,1,0      op: *
*      <- Operacion: por
47:       ST       0,0(5)      asignacion compleja: almaceno el valor para el id z
*      -> constante
44:       LDC       0,9(0)      cargar constante: 9
*      <- constante
45:       LD       1,0(6)      op: pop o cargo de la pila el valor izquierdo en AC1
46:       MUL       0,1,0      op: *
*      <- Operacion: por
47:       ST       0,0(5)      asignacion compleja: almaceno el valor para el id z
*      <- constante
45:       LD       1,0(6)      op: pop o cargo de la pila el valor izquierdo en AC1
46:       MUL       0,1,0      op: *
*      <- Operacion: por
47:       ST       0,0(5)      asignacion compleja: almaceno el valor para el id z
*      <- Operacion: por
47:       ST       0,0(5)      asignacion compleja: almaceno el valor para el id z
47:       ST       0,0(5)      asignacion compleja: almaceno el valor para el id z
*      If: el salto hacia el final debe estar aqui
41:       JEQ       0,7(7)      if: jmp hacia else
*      -> Operacion: entre
*      -> constante
49:       LDC       0,2(0)      cargar constante: 2
*      <- constante
50:       ST       0,0(6)      op: push en la pila tmp el resultado expresion izquierda
*      -> constante
51:       LDC       0,2(0)      cargar constante: 2
*      <- constante
52:       LD       1,0(6)      op: pop o cargo de la pila el valor izquierdo en AC1
53:       DIV       0,1,0      op: /
*      <- Operacion: entre
54:       ST       0,0(5)      asignacion compleja: almaceno el valor para el id z
48:       LDA       7,6(7)      if: jmp hacia el final
*      <- if
*      If: el salto hacia el final debe estar aqui
29:       JEQ       0,26(7)      if: jmp hacia else
*      <- if
*      If: el salto hacia el final debe estar aqui
19:       JEQ       0,37(7)      if: jmp hacia else
*      -> constante
57:       LDC       0,0(0)      cargar constante: 0
*      <- constante
58:       ST       0,0(5)      asignacion compleja: almaceno el valor para el id z
56:       LDA       7,2(7)      if: jmp hacia el final
*      <- if
*      Fin de la ejecucion.
59:       HALT       0,0,0