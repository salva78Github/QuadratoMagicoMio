package it.polito.tdp.quadrato_magico;

import java.util.ArrayList;
import java.util.List;

public class QuadratoMagico {

	private int magicNumber;
	private int size;
	List<int[][]> solutions = null;


	/**
	 * @param quadrato
	 * @param magicNumber
	 */
	public QuadratoMagico(int size) {
		this.size = size;
		this.magicNumber = (size * ((size * size) + 1)) / 2;

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		QuadratoMagico qm = new QuadratoMagico(3);

		qm.risolvi();

	}

	private List<int[][]> risolvi() {
		solutions = new ArrayList<int [][]>();

		// soluzione madre di livello 0
		int[][] quadratoParziale = new int[size][size];
		scegli(quadratoParziale, 0);

		for(int [][] quadrato : solutions){
			stampaSoluzione(quadrato);
		}

	
		return solutions;

	}

	private void scegli(int[][] quadratoParziale, int livello) {

		// livello = size^2 e somma colonne, righe e diagonali = numero magico
		// salva in quadrato best
		// stampa soluzione
		if (livello == (size*size)) {
			if (sommeRCDMagiche(quadratoParziale)) {
				int[][] solution = new int [size][size];
				for(int i = 0; i< size; i++){
					for(int j = 0; j< size; j++){
						solution [i][j] = quadratoParziale[i][j];
					}							
				}
//				stampaSoluzione(solution);
				solutions.add(solution);
			}
		}
		else {
		//	stampaSoluzione(quadratoParziale);

			// quadrato parziale non pieno
			for (int numero = 1; numero <= (size*size); numero++) {
				if (!giaInserito(quadratoParziale, numero)) {
					// il numero i non c'è ancora, provo a metterlo
					// se somme righe, colonne e diagonali non superno numero
					// magico
					if (!sommeRCDSforate(quadratoParziale)) {
					
						//System.out.println("<i - livello> " + numero + " - " + livello);
						
						// prova a mettere i nel quadrato
						quadratoParziale[(livello) / (size)][((livello) % (size))] = numero;
						// e delegare la ricerca al livello successivo
						scegli(quadratoParziale, livello + 1);
						// poi rimetti le cose a posto (togli i)
						quadratoParziale[(livello) / (size)][((livello) % (size))] = 0;
					}
				}
			}
		}

	}


	private void stampaSoluzione(int[][] quadrato) {
		StringBuffer sb;
		System.out.println("*********************");
		for (int i = 0; i < size; i++) {
			sb = new StringBuffer();
			for (int j = 0; j < size; j++) {
				sb.append(quadrato[i][j]).append(" ");
			}
			System.out.println(sb.toString());

		}

	}

	private boolean sommeRCDMagiche(int[][] quadratoParziale) {
		int[] parziali = sommeRCD(quadratoParziale);

		for (int i = 0; i < (size*2)+2; i++) {
//			System.out.println("<sommeRCDMagiche> parziali[i]="+parziali[i]);
			if (parziali[i] != magicNumber) {
				return false;
			}
		}

		return true;

	}

	private int[] sommeRCD(int[][] quadratoParziale) {
		int[] parziali = new int[(size * 2) + 2];

		// righe
		int sommaR;
		// colonne
		int sommaC;
		// diagonale principale
		int sommaD;
		// diagonale inversa
		int sommaI;

		for (int i = 0; i < size; i++) {
			sommaR = 0;
			sommaC = 0;
			for (int j = 0; j < size; j++) {
				sommaR += quadratoParziale[i][j];
				sommaC += quadratoParziale[j][i];
			}
			parziali[2 * i] = sommaR;
			parziali[(2 * i) + 1] = sommaC;

		}

		// diagonali
		sommaD = 0;
		sommaI = 0;
		for (int i = 0; i < size; i++) {
			sommaD += quadratoParziale[i][i];
			sommaI += quadratoParziale[i][size - i - 1];
		}
		parziali[(size * 2)] = sommaD;
		parziali[(size * 2)+1] = sommaI;

		return parziali;
	}

	/**
	 * verifica se la somma dei numeri sulle diagonali, righe e colonne
	 * aggiungendo il numero passato in input non ha superato il numero magico
	 * 
	 * @param quadratoParziale
	 * @param numero
	 * @return
	 */
	private boolean sommeRCDSforate(int[][] quadratoParziale) {

		int[] parziali = sommeRCD(quadratoParziale);

		for (int i = 0; i < (size*2)+2; i++) {
//			System.out.println("<sommeRCDSforate> parziali[i]="+parziali[i]);
			if (parziali[i] > magicNumber) {
				return true;
			}
		}

		return false;

	}

	private boolean giaInserito(int[][] quadratoParziale, int numero) {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (quadratoParziale[i][j] == numero) {
					return true;
				}
			}
		}
		return false;
	}

}
