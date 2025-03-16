0:       LD       6,0(0)      cargar la maxima direccion desde la localidad 0
1:       ST       0,0(0)      limpio el registro de la localidad 0
*      -> asignacion
*      -> constante
2:       LDC       0,0(0)      cargar constante: 99
*      <- constante
3:       ST       0,0(5)      asignacion: almaceno el valor para el id x
*      <- asignacion
*      -> if
*      -> Operacion: and
*      -> Operacion: menorigual
*      -> identificador
4:       LD       0,0(5)      cargar valor de identificador: x
*      -> identificador
5:       ST       0,0(6)      op: push en la pila tmp el resultado expresion izquierda
*      -> constante
6:       LDC       0,100(0)      cargar constante: 100
*      <- constante
7:       LD       1,0(6)      op: pop o cargo de la pila el valor izquierdo en AC1
8:       SUB       0,1,0      op: <=
9:       JLE       0,2(7)      voy dos instrucciones mas alla if verdadero (AC<=0)
10:       LDC       0,0(0)      caso de falso (AC=0)
11:       LDA       7,1(7)      Salto incodicional a direccion: PC+1 (es falso evito colocarlo verdadero)
12:       LDC       0,1(0)      caso de verdadero (AC=1)
*      <- Operacion: menorigual
13:       ST       0,0(6)      op: push en la pila tmp el resultado expresion izquierda
*      -> Operacion: mayor
*      -> identificador
14:       LD       0,0(5)      cargar valor de identificador: x
*      -> identificador
15:       ST       0,-1(6)      op: push en la pila tmp el resultado expresion izquierda
*      -> constante
16:       LDC       0,1(0)      cargar constante: 1
*      <- constante
17:       LD       1,-1(6)      op: pop o cargo de la pila el valor izquierdo en AC1
18:       SUB       0,0,1      op: >
19:       JLT       0,2(7)      voy dos instrucciones mas alla if verdadero (AC>0)
20:       LDC       0,0(0)      caso de falso (AC=0)
21:       LDA       7,1(7)      Salto incodicional a direccion: PC+1 (es falso evito colocarlo verdadero)
22:       LDC       0,1(0)      caso de verdadero (AC=1)
*      <- Operacion: mayor
23:       LD       1,0(6)      op: pop o cargo de la pila el valor izquierdo en AC1
24:       JEQ       1,2(7)      voy dos instrucciones mas alla si hay corto circuito (AC1 == 0)
25:       MUL       0,1,0      operacion logica AND, se Multiplica AC * AC1
26:       JNE       0,2(7)      voy dos instrucciones mas alla si es verdadero (AC != 0)
27:       LDC       0,0(0)      caso de falso (AC=0)
28:       LDA       7,1(7)      Salto incodicional a direccion: PC+1 (es falso evito colocarlo verdadero)
29:       LDC       0,1(0)      caso de verdadero (AC=1)
*      <- Operacion: and
*      If: el salto hacia el else debe estar aqui
*      -> escribir
*      -> constante
31:       LDC       0,100(0)      cargar constante: 100
*      <- constante
32:       OUT       0,0,0      escribir: genero la salida de la expresion
*      <- escribir
*      If: el salto hacia el final debe estar aqui
30:       JEQ       0,3(7)      if: jmp hacia else
*      -> escribir
*      -> constante
34:       LDC       0,200(0)      cargar constante: 200
*      <- constante
35:       OUT       0,0,0      escribir: genero la salida de la expresion
*      <- escribir
33:       LDA       7,2(7)      if: jmp hacia el final
*      <- if
*      Fin de la ejecucion.
36:       HALT       0,0,0