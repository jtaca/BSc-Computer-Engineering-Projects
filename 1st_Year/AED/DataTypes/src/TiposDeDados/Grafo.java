package TiposDeDados;

import java.util.Map;
import java.util.List;

import java.util.Stack;       // Pilha
import java.util.HashMap;     // Tabela
import java.util.LinkedList;  // Lista ligada

import java.util.Arrays;
import java.util.Collections;

public class Grafo {
    
    private static final int MAX = 1000;
    
    private double[][] m = new double[MAX][MAX]; // Matriz de adjacência
    private int Ultimo_vertice = -1;
    
    private Map<String, Integer> vertice_para_indice = new HashMap<String, Integer>();
    private Map<Integer, String> indice_para_vertice = new HashMap<Integer, String>();
    
    public Grafo() {
        inicial();
    }
    
    public void inicial() {
        for(int i = 0; i != MAX; ++i) {
            for(int j = 0; j != MAX; ++j) {
                m[i][j] = 0.0;
            }
        }
        
        Ultimo_vertice = -1;
        
        vertice_para_indice.clear();
        indice_para_vertice.clear();
    }
    
    public void acrv(String v) {
        if(Ultimo_vertice + 1 == MAX)
            throw new IllegalStateException("NUmero maximo de vertices atingidos!");
        
        if(!vertice_para_indice.containsKey(v)) {
            Ultimo_vertice++;
            vertice_para_indice.put(v, Ultimo_vertice);
            indice_para_vertice.put(Ultimo_vertice, v);
        }
    }
    
    public void acra(String v1, String v2) {
        if(vertice_para_indice.containsKey(v1) && vertice_para_indice.containsKey(v2)) {
            m[vertice_para_indice.get(v1)][vertice_para_indice.get(v2)] = 1.0;
            m[vertice_para_indice.get(v2)][vertice_para_indice.get(v1)] = 1.0; // Não orientado!
        }
    }
    
    public void acra(String v1, String v2, double peso) {
        if(vertice_para_indice.containsKey(v1) && vertice_para_indice.containsKey(v2)) {
            m[vertice_para_indice.get(v1)][vertice_para_indice.get(v2)] = peso;
            m[vertice_para_indice.get(v2)][vertice_para_indice.get(v1)] = peso; // Não orientado!
        }
    }
    
    public boolean adjacentes(String v1, String v2) {
        if(vertice_para_indice.containsKey(v1) && vertice_para_indice.containsKey(v2)) {
            return m[vertice_para_indice.get(v1)][vertice_para_indice.get(v2)] != 0
            || m[vertice_para_indice.get(v2)][vertice_para_indice.get(v1)] != 0; // Orientado
        }
        return false;
    }
    
}
