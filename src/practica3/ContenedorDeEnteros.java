package practica3;

public class ContenedorDeEnteros{
	int size = 0;
	Nodo root;
	boolean resultado = false;

	private class Nodo{
		int value;
		Nodo[] hijos = new Nodo[2];
	}

	public ContenedorDeEnteros(){
		root = null;
	}

	public int cardinal(){
		return size;
	}

	public boolean insertar(int val){
		resultado = false;
		root= insertar(root, val);
		return resultado;
	}
	
	private Nodo insertar(Nodo nodo, int val){
		if (nodo == null) {
			size++;
			resultado = true;
			nodo = new Nodo();
			nodo.value = val;
		} else {
			if (val != nodo.value) {
				if (val < nodo.value) {
					nodo.hijos[0] = insertar(nodo.hijos[0], val);
				} else {
					nodo.hijos[1] = insertar (nodo.hijos[1], val);
				}
			}
		}
		return nodo;
	}

	public boolean extraer(int val){
		resultado = false;
		root= extraer(root, val);
		return resultado;
	}
	
	private Nodo extraer (Nodo nodo, int val) {
		if(nodo != null) {
			if (val == nodo.value) { 
				if ((nodo.hijos[0] == null || nodo.hijos[1] == null)) {
					size--;
					resultado = true;
					if (nodo.hijos[0] == null) {
						return nodo.hijos[1];
					} else {
						return nodo.hijos[0];
					}
				} else {
					nodo.hijos[1]= extraerSucesor (nodo, nodo.hijos[1]);
				}
			} else {
				if (val < nodo.value) {
					nodo.hijos[0] = extraer(nodo.hijos[0], val);
				} else {
					nodo.hijos[1] = extraer(nodo.hijos[1], val);
				}
			}
		}
		return nodo;
	}
	
	private Nodo extraerSucesor(Nodo nodoExtraer, Nodo nodo) {
		if (nodo.hijos[0]==null) {
			nodoExtraer.value = nodo.value;
			size--;
			resultado = true;
			nodo = nodo.hijos[1];
		} else {
			nodo.hijos[0] = extraerSucesor(nodoExtraer, nodo.hijos[0]);			
		}
		return nodo;
	}

	public boolean buscar(int val){
		return buscar(root, val);
	}
	
	private boolean buscar(Nodo nodo ,int val){
		while (nodo != null) {
			if (val == nodo.value) {
				return true;
			} else if (val < nodo.value) {
				nodo = nodo.hijos[0];
			} else {
				nodo = nodo.hijos[1];
			}
		}
		return false;
	}

	public void vaciar(){
		root = null;
		size = 0;
	}

	public int[] elementos(){
		int[] vector = new int[size];
		int i = 0;
		Nodo actual = root;
		inorden(actual, i, vector);
		return vector;
	}
	
	private int inorden(Nodo actual, int i, int[] vector) {
		if (actual != null) {
			i = inorden(actual.hijos[0], i, vector);			
			vector[i] = actual.value;
			i++;
			i= inorden(actual.hijos[1], i, vector);			
		}
		return i;
	}
}



