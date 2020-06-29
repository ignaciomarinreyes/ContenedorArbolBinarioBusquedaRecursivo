package practica3;

// Se importan las librerías necesarias
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

public class PruebaContenedor {
	public static void main(String[] args) {
	    // Se realizan las pruebas de los tiempos promedios
		System.out.println("######## Pruebas #####\n");				
		ContenedorDeEnteros contenedorPruebas = new ContenedorDeEnteros();
		test(contenedorPruebas);			
		System.out.println("\n######## Pruebas con ficheros #####\n");		
		int[] vector = new int[100000]; 	
		int[] vector_no = new int[20000]; 
		leerFicheros(vector, vector_no);
		ContenedorDeEnteros contenedor = new ContenedorDeEnteros();
		FileWriter escritura = null;
		try {
			escritura=new FileWriter("salida3.txt");
			escritura.write("Práctica 3: Desarrollo de un contenedor con un árbol binario de búsqueda\n\n ######## Pruebas con ficheros #####\n Tiempos promedios en milisegundos por cada 1000 operaciones\n");
			System.out.println("######## Tiempo promedio de insercion #####"); 
			escritura.write("######## Tiempo promedio de insercion #####\n");
			tiempoPromedioInsercion(vector, contenedor, escritura); 
			System.out.println("######## Tiempo promedio de extraccion#####");
			escritura.write("\n######## Tiempo promedio de extraccion#####\n"); 
			tiempoPromedioExtraccion(vector ,contenedor, escritura); 
			System.out.println("######## Tiempo promedio de busqueda exitosa#####");
			escritura.write("\n######## Tiempo promedio de busqueda exitosa#####\n");
			tiempoPromedioBusquedaExitosa(vector, contenedor, escritura); 
			System.out.println("######## Tiempo promedio de busqueda infructuosa#####");
			escritura.write("\n######## Tiempo promedio de busqueda infructuosa#####\n");
			contenedor.vaciar();
			tiempoPromedioBusquedaInfructuosa(vector, vector_no, contenedor, escritura); 
			escritura.close(); 
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	// Comprueba el correcto funcionamiento de todas las operaciones de ContenedorDeEnteros
	// Métodos para los test
	public static void test(ContenedorDeEnteros contenedorPruebas) {
		testVaciar(contenedorPruebas);
		testElementos(contenedorPruebas);
		testBuscar(contenedorPruebas);
		testExtraer(contenedorPruebas);
		testInsertar(contenedorPruebas);
		testCardinal(contenedorPruebas);
	}
	
	public static void testElementos(ContenedorDeEnteros contenedorPruebas) {
		int exito = 0;
		contenedorPruebas.insertar(8);
		contenedorPruebas.extraer(8);
		exito = exito + miAssertArrayEquals(contenedorPruebas.elementos(), new int[]{}); // un solo elemento en la raiz
		contenedorPruebas.insertar(4);
		contenedorPruebas.insertar(2);
		contenedorPruebas.insertar(6);
		contenedorPruebas.insertar(1);
		contenedorPruebas.insertar(3);
		contenedorPruebas.insertar(5);
		contenedorPruebas.insertar(7);
		contenedorPruebas.insertar(15);
		contenedorPruebas.insertar(9);
		contenedorPruebas.insertar(8);
		contenedorPruebas.insertar(19);
		contenedorPruebas.insertar(20);
		contenedorPruebas.insertar(18);
		contenedorPruebas.insertar(16);
		contenedorPruebas.insertar(17);
		exito = exito + miAssertArrayEquals(contenedorPruebas.elementos(), new int[]{1,2,3,4,5,6,7,8,9,15,16,17,18,19,20});
		contenedorPruebas.extraer(15); // sucesor simétrico
		exito = exito + miAssertArrayEquals(contenedorPruebas.elementos(), new int[]{1,2,3,4,5,6,7,8,9,16,17,18,19,20});
		contenedorPruebas.extraer(1); // nulos los dos
		exito = exito + miAssertArrayEquals(contenedorPruebas.elementos(), new int[]{2,3,4,5,6,7,8,9,16,17,18,19,20});
		contenedorPruebas.extraer(9); // nulo a la derecha
		exito = exito + miAssertArrayEquals(contenedorPruebas.elementos(), new int[]{2,3,4,5,6,7,8,16,17,18,19,20});
		contenedorPruebas.extraer(7); // nulo a la izquierda
		exito = exito + miAssertArrayEquals(contenedorPruebas.elementos(), new int[]{2,3,4,5,6,8,16,17,18,19,20});	
		if(exito == 6) {
			System.out.println("TestElementos superado");
			return;
		} else {
			System.out.println("TestElementos no superado");
			System.exit(0); 
		}
	}
	
	public static void testVaciar(ContenedorDeEnteros contenedorPruebas) {
		if(contenedorPruebas.size == 0 && contenedorPruebas.root == null) {
			System.out.println("TestVaciar superado");
			return;
		} else {
			System.out.println("TestVaciar no superado");
			System.exit(0); 
		}
	}
	
	public static void testBuscar(ContenedorDeEnteros contenedorPruebas) {
		// new int[]{2,3,4,5,6,8,16,17,18,19,20}
		if(contenedorPruebas.buscar(3) && contenedorPruebas.buscar(19) && !contenedorPruebas.buscar(50)) {
			System.out.println("TestBuscar superado");
			return;
		} else {
			System.out.println("TestBuscar no superado");
			System.exit(0); 
		}
	}
	
	public static void testExtraer (ContenedorDeEnteros contenedorPruebas) {
		// new int[]{2,3,4,5,6,8,16,17,18,19,20}
		// extraer 16 es susesor simétrico, extraer  20 es los dos nulos, extraer 2 es con nulo a la izquierda, extraer 19 es con nulo a la derecha, extraer 50 que no está
		if(contenedorPruebas.extraer(16) && contenedorPruebas.extraer(20) && contenedorPruebas.extraer(2) && contenedorPruebas.extraer(19) && !contenedorPruebas.extraer(50)) {
			System.out.println("TestExtraer superado");
			return;
		} else {
			System.out.println("TestExtraer no superado");
			System.exit(0); 
		}
	}
	
	public static void testInsertar (ContenedorDeEnteros contenedorPruebas) {
		// new int[]{3,4,5,6,8,17,18}
		// inserta el 21 e inserta el 17 que si está.
		if(contenedorPruebas.insertar(21) && !contenedorPruebas.insertar(17)) {
			System.out.println("TestInsertar superado");
			return;
		} else {
			System.out.println("TestInsertar no superado");
			System.exit(0); 
		}
	}
	
	public static void testCardinal (ContenedorDeEnteros contenedorPruebas) {
		// new int[]{3,4,5,6,8,17,18, 21}
		// el tamaño sería 8
		if(contenedorPruebas.cardinal() == 8) {
			System.out.println("TestCardinal superado");
			return;
		} else {
			System.out.println("TestCardinal no superado");
			System.exit(0); 
		}
	}

	public static int miAssertArrayEquals(int [] real, int [] esperado) {
		if (real.length != esperado.length) {
			return 0;
		} else {
			for (int i = 0; i < real.length; i++) {
				if(real[i] != esperado[i]) {
					return 0;
				}
			}
			return 1;
		}	
	}
	
	// Método para leer ficheros
	public static void leerFicheros(int[] vector, int[]vector_no) {
		try {
			RandomAccessFile lectura = new RandomAccessFile("datos.dat", "r");
			for(int i = 0; lectura.getFilePointer() < lectura.length();i++){
				vector[i]=lectura.readInt();
			}
			lectura.close();
			RandomAccessFile lectura_no = new RandomAccessFile("datos_no.dat", "r");
			for(int j = 0; lectura_no.getFilePointer()< lectura_no.length(); j++) {
				vector_no[j]=lectura_no.readInt();
			}
			lectura_no.close();
		} catch (IOException e) {			
			e.printStackTrace(); 
		}
	}
	
	// Métodos para calcular los tiempos promedios de inserción, extracción, busqueda exitosa y búsqueda infructuosa
	public static ContenedorDeEnteros tiempoPromedioInsercion(int[] array, ContenedorDeEnteros contenedorEntrada, FileWriter escritura) throws IOException {		
		double tInicio, tFin;
		double tiempo = 0;
		int i = 0;
		for (int j = 0; j < 100000; j = j + 10000) {	
			tInicio = System.nanoTime();
			for (i = j; i < j + 10000; i++) {
				contenedorEntrada.insertar(array[i]);	
			}
			tFin = System.nanoTime();
			tiempo = tFin - tInicio;
			tiempo = tiempo/10.E+6;
			System.out.println(tiempo);
			escritura.write(tiempo + "\n");
			tiempo = 0;
		}
		return contenedorEntrada;
	}
	
	public static void tiempoPromedioExtraccion(int[] vector ,ContenedorDeEnteros contenedorEntrada, FileWriter escritura) throws IOException {			
		double tInicio, tFin;
		double tiempo = 0;
		int i = 0;
		for (int j = 0; j < 100000; j = j + 10000) {	
			tInicio = System.nanoTime();
			for (i = j; i < j + 10000; i++) {			
				contenedorEntrada.extraer(vector[i]);			
			}
			tFin = System.nanoTime();
			tiempo = tFin - tInicio;
			tiempo = tiempo/10.E+6;
			System.out.println(tiempo);
			escritura.write(tiempo + "\n");
			tiempo = 0;
		}
	}
	
	public static void tiempoPromedioBusquedaExitosa(int[] vector ,ContenedorDeEnteros contenedorEntrada, FileWriter escritura) throws IOException {			
		double tInicio, tFin;
		double tiempo = 0;
		int i = 0;
		int contador = 6000;
		for (int j = 0; j < 100000; j = j + 10000) {		
			for (i = j; i < j + 10000; i++) {				
				contenedorEntrada.insertar(vector[i]);							
			}
			tInicio = System.currentTimeMillis();
			for (int x = 0; x < 600; x++) {
				for (int k = contenedorEntrada.cardinal() -1; k >= 0; k--) { 	
					contenedorEntrada.buscar(vector[k]);
				}
			}
			tFin = System.currentTimeMillis();
			tiempo = tFin - tInicio;		
			tiempo = tiempo/contador;
			contador+= 6000;
			System.out.println(tiempo);
			escritura.write(tiempo + "\n");
			tiempo = 0;
		}
	}
	
	public static void tiempoPromedioBusquedaInfructuosa(int[] vector, int[] vector_no ,ContenedorDeEnteros contenedorEntrada, FileWriter escritura) throws IOException {			
		double tInicio, tFin;
		double tiempo = 0;
		int i = 0;
		for (int j = 0; j < 100000; j = j + 10000) {		
			for (i = j; i < j + 10000; i++) {				
				contenedorEntrada.insertar(vector[i]);							
			}
			tInicio = System.currentTimeMillis();
			for (int x = 0; x < 500 ; x++) {
				for (int k = 0; k < vector_no.length; k++) { 	
					contenedorEntrada.buscar(vector_no[k]);
				}
			}
			tFin = System.currentTimeMillis();
			tiempo = tFin - tInicio;
			tiempo = tiempo/10000;
			System.out.println(tiempo);
			escritura.write(tiempo + "\n");
			tiempo = 0;
		}
	}	
}