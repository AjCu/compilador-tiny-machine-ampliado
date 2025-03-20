0:       LD       6,0(0)      cargar la maxima direccion desde la localidad 0
1:       ST       0,0(0)      limpio el registro de la localidad 0
*      -> Operacion: menos
*      -> constante
2:       LDC       0,2(0)      cargar constante: 2
*      <- constante
3:       ST       0,0(6)      op: push en la pila tmp el resultado expresion izquierda
*      -> constante
4:       LDC       0,1(0)      cargar constante: 1
*      <- constante
5:       LD       1,0(6)      op: pop o cargo de la pila el valor izquierdo en AC1
6:       SUB       0,1,0      op: -
*      <- Operacion: menos
7:       ST       0,0(5)      asignacion compleja: almaceno el valor para el id x
*      -> Operacion: entre
*      -> constante
8:       LDC       0,4(0)      cargar constante: 4
*      <- constante
9:       ST       0,0(6)      op: push en la pila tmp el resultado expresion izquierda
*      -> constante
10:       LDC       0,2(0)      cargar constante: 2
*      <- constante
11:       LD       1,0(6)      op: pop o cargo de la pila el valor izquierdo en AC1
12:       DIV       0,1,0      op: /
*      <- Operacion: entre
13:       ST       0,1(5)      asignacion compleja: almaceno el valor para el id y
*      -> if
*      -> Operacion: diferente
*      -> Operacion: mod
*      -> identificador
14:       LD       0,0(5)      cargar valor de identificador: x
*      -> identificador
15:       ST       0,0(6)      op: push en la pila tmp el resultado expresion izquierda
*      -> constante
16:       LDC       0,2(0)      cargar constante: 2
*      <- constante
17:       LD       1,0(6)      op: pop o cargo de la pila el valor izquierdo en AC1
18:       ST       1,0(5)      guardo el valor AC1 (opizq) en la dirMem 0
19:       ST       0,1(5)      guardo el valor AC (opder) en la dirMem 1
20:       DIV       0,1,0      operacion division y guarda en registro 0
21:       ST       0,2(5)      guardo el resultado que esta en reg 0 en la dirMem 2
22:       LD       1,1(5)      cargo en reg 1 (AC1) el valor en la dirMem 1
23:       MUL       0,1,0      multiplicacion de valor de division por divisor
24:       LD       1,0(5)      cargo valor AC1 en la dirMem 0
25:       SUB       0,1,0      operacion resta de multiplicacion y valor anterior
*      <- Operacion: mod
26:       ST       0,0(6)      op: push en la pila tmp el resultado expresion izquierda
*      -> constante
27:       LDC       0,0(0)      cargar constante: 0
*      <- constante
28:       LD       1,0(6)      op: pop o cargo de la pila el valor izquierdo en AC1
29:       SUB       0,1,0      op: <>
30:       JNE       0,2(7)      voy dos instrucciones mas alla if verdadero (AC<>0)
31:       LDC       0,0(0)      caso de falso (AC=0)
32:       LDA       7,1(7)      Salto incodicional a direccion: PC+1 (es falso evito colocarlo verdadero)
33:       LDC       0,1(0)      caso de verdadero (AC=1)
*      <- Operacion: diferente
*      If: el salto hacia el else debe estar aqui
*      -> escribir
*      -> constante
35:       LDC       0,1(0)      cargar constante: 1
*      <- constante
36:       OUT       0,0,0      escribir: genero la salida de la expresion
*      <- escribir
*      If: el salto hacia el final debe estar aqui
34:       JEQ       0,3(7)      if: jmp hacia else
*      -> escribir
*      -> constante
38:       LDC       0,0(0)      cargar constante: 0
*      <- constante
39:       OUT       0,0,0      escribir: genero la salida de la expresion
*      <- escribir
37:       LDA       7,2(7)      if: jmp hacia el final
*      <- if
*      Fin de la ejecucion.
40:       HALT       0,0,0