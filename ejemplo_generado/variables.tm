0:       LD       6,0(0)      cargar la maxima direccion desde la localidad 0
1:       ST       0,0(0)      limpio el registro de la localidad 0
*      -> asignacion
*      -> constante
2:       LDC       0,100(0)      cargar constante: 100
*      <- constante
3:       ST       0,0(5)      asignacion simple: almaceno el valor para el id a
*      <- asignacion
*      -> escribir
*      -> identificador
4:       LD       0,0(5)      cargar valor de identificador: a
*      -> identificador
5:       OUT       0,0,0      escribir: genero la salida de la expresion
*      <- escribir
*      Fin de la ejecucion.
6:       HALT       0,0,0