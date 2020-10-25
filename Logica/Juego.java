package Logica;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class Juego {
	
	private Celda [][] panel1;
	private Celda [][] panel2;
	private Celda [][] panel3;
	private Celda [][] panel4;
	private Celda [][] panel5;
	private Celda [][] panel6;
	private Celda [][] panel7;
	private Celda [][] panel8;
	private Celda [][] panel9;
	private int contador_celdas_correctas;
	private int contador_celdas_incorrectas;
	private Boolean respetaFormato;
	
	public Juego() {
		contador_celdas_correctas=0;
		contador_celdas_incorrectas=81;
		panel1 = new Celda[3][3];
		inicializarPanel(panel1);
		panel2 = new Celda[3][3];
		inicializarPanel(panel2);
		panel3 = new Celda[3][3];
		inicializarPanel(panel3);
		panel4 = new Celda[3][3];
		inicializarPanel(panel4);
		panel5 = new Celda[3][3];
		inicializarPanel(panel5);
		panel6 = new Celda[3][3];
		inicializarPanel(panel6);
		panel7 = new Celda[3][3];
		inicializarPanel(panel7);
		panel8 = new Celda[3][3];
		inicializarPanel(panel8);
		panel9 = new Celda[3][3];
		inicializarPanel(panel9);
		
	}
	
	public boolean getValidezSolucion() {
		return respetaFormato;
	}
	
	public void generarEstadoini() {
		Random r = new Random();
		while(contador_celdas_correctas>20) {
			int random_paneles= r.nextInt(9);
			int random_fila= r.nextInt(3);
			int random_columna= r.nextInt(3);
			String codigo = random_fila+","+random_columna+","+random_paneles;
			colocar(codigo,0);
		}
	}
	
	public void vaciarTablero() {
		String codigo="";
		for(int tablero=0;tablero<9;tablero++) {
			for(int i = 0;i<3;i++) {
				for(int j=0;j<3;j++) {
					codigo=i+","+j+","+tablero;
					colocar(codigo,0);
				}
			}
		}
	}
	
	public boolean cargarSolucion(String ruta){
		File archivo = new File(ruta);
		Celda [][] matriz_archivo = new Celda[9][9];
		inicializarPanel(matriz_archivo);
		String linea;
		String codigo = null;
		String [] arreglo_linea;
		int valor;
		vaciarTablero();
		try {
			FileReader 	fr = new FileReader(archivo);
			BufferedReader br= new BufferedReader(fr);
			linea=br.readLine();
			arreglo_linea = linea.split(" ");
			
			respetaFormato= arreglo_linea.length==9;
			for(int i=0;i<matriz_archivo.length && linea!=null && respetaFormato==true;i++) {
				arreglo_linea = linea.split(" ");
				if(arreglo_linea.length!=9) {
					respetaFormato=false;
				}else {
					for(int j=0;j<matriz_archivo[0].length;j++) {
						valor=Integer.parseInt(arreglo_linea[j]);
						matriz_archivo[i][j].actualizar(valor);
				}
				}
				linea=br.readLine();
			}
			fr.close();
			if(respetaFormato) {
				for(int i=0;i<panel1.length;i++) {
					for(int j=0;j<panel1[0].length;j++) {
						codigo=i+","+j+","+0;
						this.colocar(codigo,matriz_archivo[i][j].getValor());
					}
				}
				for(int i=0;i<panel2.length;i++) {
					for(int j=0;j<panel2[0].length;j++) {
						codigo=i+","+j+","+1;
						this.colocar(codigo,matriz_archivo[i][j+3].getValor());
					}
				}
				for(int i=0;i<panel3.length;i++) {
					for(int j=0;j<panel3[0].length;j++) {
						codigo=i+","+j+","+2;
						this.colocar(codigo,matriz_archivo[i][j+6].getValor());
					}
				}
				for(int i=0;i<panel4.length;i++) {
					for(int j=0;j<panel4[0].length;j++) {
						codigo=i+","+j+","+3;
						this.colocar(codigo,matriz_archivo[i+3][j].getValor());
					}
				}
				for(int i=0;i<panel5.length;i++) {
					for(int j=0;j<panel1[0].length;j++) {
						codigo=i+","+j+","+4;
						this.colocar(codigo,matriz_archivo[i+3][j+3].getValor());
					}
				}
				for(int i=0;i<panel6.length;i++) {
					for(int j=0;j<panel1[0].length;j++) {
						codigo=i+","+j+","+5;
						this.colocar(codigo,matriz_archivo[i+3][j+6].getValor());
					}
				}
				for(int i=0;i<panel7.length;i++) {
					for(int j=0;j<panel1[0].length;j++) {
						codigo=i+","+j+","+6;
						this.colocar(codigo,matriz_archivo[i+6][j].getValor());
					}
				}
				for(int i=0;i<panel8.length;i++) {
					for(int j=0;j<panel1[0].length;j++) {
						codigo=i+","+j+","+7;
						this.colocar(codigo,matriz_archivo[i+6][j+3].getValor());
					}
				}
				for(int i=0;i<panel9.length;i++) {
					for(int j=0;j<panel1[0].length;j++) {
						codigo=i+","+j+","+8;
						this.colocar(codigo,matriz_archivo[i+6][j+6].getValor());
					}
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return getVictoria();
	}
	
	
	public boolean getVictoria() {
		return contador_celdas_correctas==81 && contador_celdas_incorrectas==0 ;
	}
	
	private boolean recorrerFilas(int i,int valor,Celda[][] celda) {
		boolean encontre=false;
		for(int j=0;j<celda.length && !encontre;j++) {
			if(celda[i][j].getValor()!=null) {
				if(celda[i][j].getValor()==valor) {
					encontre = true;
				}
			}
		}
		return encontre;
	}
	
	private boolean recorrerColumnas(int j,int valor,Celda[][] celda) {
		boolean encontre=false;
		for(int i=0;i<celda.length && !encontre;i++) {
			if(celda[i][j].getValor()!=null) {
				if(celda[i][j].getValor()==valor) {
					encontre = true;
				}
			}
		}
		return encontre;
	}
	
	private boolean perteneceFilaOColumna(int i,int j,int valor,int pos_panel) {
		boolean encontreEnFila = false;
		boolean encontreEnColumna = false;
		switch(pos_panel) {
		case 0:{
			if(!encontreEnFila && !encontreEnColumna) {
				encontreEnFila=recorrerFilas(i,valor,panel2);	
				encontreEnColumna=recorrerColumnas(j,valor,panel4);
			}
			if(!encontreEnFila && !encontreEnColumna) {
				encontreEnFila=recorrerFilas(i,valor,panel3);
				encontreEnColumna=recorrerColumnas(j,valor,panel7);
			}
			break;
		}
		case 1:{
			if(!encontreEnFila && !encontreEnColumna) {
				encontreEnFila=recorrerFilas(i,valor,panel1);
				encontreEnColumna=recorrerColumnas(j,valor,panel5);
			}
			if(!encontreEnFila && !encontreEnColumna) {
				encontreEnFila=recorrerFilas(i,valor,panel3);
				encontreEnColumna=recorrerColumnas(j,valor,panel8);
			}
			break;
		}
		case 2:{
			if(!encontreEnFila && !encontreEnColumna) {
				encontreEnFila=recorrerFilas(i,valor,panel1);
				encontreEnColumna=recorrerColumnas(j,valor,panel6);
			}
			if(!encontreEnFila && !encontreEnColumna) {
				encontreEnFila=recorrerFilas(i,valor,panel2);
				encontreEnColumna=recorrerColumnas(j,valor,panel9);
			}
			break;
		}
		case 3:{
			if(!encontreEnFila && !encontreEnColumna) {
				encontreEnFila=recorrerFilas(i,valor,panel5);
				encontreEnColumna=recorrerColumnas(j,valor,panel1);
			}
			if(!encontreEnFila && !encontreEnColumna) {
				encontreEnFila=recorrerFilas(i,valor,panel6);
				encontreEnColumna=recorrerColumnas(j,valor,panel7);
			}
			break;

		}
		case 4:{
			if(!encontreEnFila && !encontreEnColumna) {
				encontreEnFila=recorrerFilas(i,valor,panel4);
				encontreEnColumna=recorrerColumnas(j,valor,panel2);
			}
			if(!encontreEnFila && !encontreEnColumna) {
				encontreEnFila=recorrerFilas(i,valor,panel6);
				encontreEnColumna=recorrerColumnas(j,valor,panel8);
			}
			break;
		}
		case 5:{
			if(!encontreEnFila && !encontreEnColumna) {
				encontreEnFila=recorrerFilas(i,valor,panel4);
				encontreEnColumna=recorrerColumnas(j,valor,panel3);
			}
			if(!encontreEnFila && !encontreEnColumna) {
				encontreEnFila=recorrerFilas(i,valor,panel5);
				encontreEnColumna=recorrerColumnas(j,valor,panel9);
			}
			break;
		}
		case 6:{
			if(!encontreEnFila && !encontreEnColumna) {
				encontreEnFila=recorrerFilas(i,valor,panel8);
				encontreEnColumna=recorrerColumnas(j,valor,panel1);
			}
			if(!encontreEnFila && !encontreEnColumna) {
				encontreEnFila=recorrerFilas(i,valor,panel9);
				encontreEnColumna=recorrerColumnas(j,valor,panel4);
			}
			break;
		}
		case 7:{
			if(!encontreEnFila && !encontreEnColumna) {
				encontreEnFila=recorrerFilas(i,valor,panel7);
				encontreEnColumna=recorrerColumnas(j,valor,panel2);
			}
			if(!encontreEnFila && !encontreEnColumna) {
				encontreEnFila=recorrerFilas(i,valor,panel9);
				encontreEnColumna=recorrerColumnas(j,valor,panel5);
			}
			break;
		}

		case 8:{
			if(!encontreEnFila && !encontreEnColumna) {
				encontreEnFila=recorrerFilas(i,valor,panel7);
				encontreEnColumna=recorrerColumnas(j,valor,panel3);
			}
			if(!encontreEnFila && !encontreEnColumna) {
				encontreEnFila=recorrerFilas(i,valor,panel8);
				encontreEnColumna=recorrerColumnas(j,valor,panel6);
			}
			break;
		}
		}
		return (encontreEnFila || encontreEnColumna);
	}

	private boolean pertenecePanel(Celda[][] panel, int valor) {
		boolean encontre=false;
		for(int i=0; i<panel.length && !encontre;i++) {
			for(int j=0; j<panel[0].length && !encontre;j++) {
				if(panel[i][j].getValor()!=null) {
					if(panel[i][j].getValor()==valor) {
						encontre=true;
					}
				}
			}
		}
		return encontre;
	}
	
	public void colocar(String codigo,int valor) {
		String [] informacion= codigo.split(",");
		int i = Integer.parseInt(informacion[0]);
		int j = Integer.parseInt(informacion[1]);
		int pos_panel = Integer.parseInt(informacion[2]);
		switch(pos_panel) {
		case 0:{
			//Compruebo que el valor que voy a colocar en la celda no pertenezca al panel y no pertenezca a esa fila o a la columna
			if(!pertenecePanel(panel1,valor) && !perteneceFilaOColumna(i,j,valor,pos_panel) && valor!=0) {
				if(panel1[i][j].getValidez() && panel1[i][j].getValor()!=null) {
					panel1[i][j].actualizar(valor);
				}else {
					if(panel1[i][j].getValor()!=null) {
						panel1[i][j].actualizar(valor);
						panel1[i][j].setValidez(true);
						contador_celdas_correctas++;
						contador_celdas_incorrectas--;
					}else {
						panel1[i][j].actualizar(valor);
						panel1[i][j].setValidez(true);
						contador_celdas_correctas++;
						contador_celdas_incorrectas--;
					}
				}
			}else {
				//si no cumple con la propiedad de no pertenecer a fila o columna
				if(valor!=0) {
					panel1[i][j].actualizar(-valor);
					panel1[i][j].setValidez(false);
				//si valor es 0 quiere decir que es el boton eliminar	
				}else {
					if(panel1[i][j].getValidez()) {
						panel1[i][j].actualizar(valor);
						panel1[i][j].setValidez(false);
						contador_celdas_correctas--;
						contador_celdas_incorrectas++;
					}else {
						panel1[i][j].actualizar(valor);
						panel1[i][j].setValidez(false);
					}
				}
			}
			break;
		}
		case 1:{
			//Compruebo que el valor que voy a colocar en la celda no pertenezca al panel y no pertenezca a esa fila o a la columna
			if(!pertenecePanel(panel2,valor) && !perteneceFilaOColumna(i,j,valor,pos_panel) && valor!=0) {
				if(panel2[i][j].getValidez() && panel2[i][j].getValor()!=null) {
					panel2[i][j].actualizar(valor);
				}else {
					if(panel2[i][j].getValor()!=null) {
						panel2[i][j].actualizar(valor);
						panel2[i][j].setValidez(true);
						contador_celdas_correctas++;
						contador_celdas_incorrectas--;
					}else {
						panel2[i][j].actualizar(valor);
						panel2[i][j].setValidez(true);
						contador_celdas_correctas++;
						contador_celdas_incorrectas--;
					}
				}
			}else {
				//si no cumple con la propiedad de no pertenecer a fila o columna
				if(valor!=0) {
					panel2[i][j].actualizar(-valor);
					panel2[i][j].setValidez(false);
				//si valor es 0 quiere decir que es el boton eliminar	
				}else {
					if(panel2[i][j].getValidez()) {
						panel2[i][j].actualizar(valor);
						panel2[i][j].setValidez(false);
						contador_celdas_correctas--;
						contador_celdas_incorrectas++;
					}else {
						panel2[i][j].actualizar(valor);
						panel2[i][j].setValidez(false);
					}
				}
			}
			break;
		}
		case 2:{
			//Compruebo que el valor que voy a colocar en la celda no pertenezca al panel y no pertenezca a esa fila o a la columna
			if(!pertenecePanel(panel3,valor) && !perteneceFilaOColumna(i,j,valor,pos_panel) && valor!=0) {
				if(panel3[i][j].getValidez() && panel3[i][j].getValor()!=null) {
					panel3[i][j].actualizar(valor);
				}else {
					if(panel3[i][j].getValor()!=null) {
						panel3[i][j].actualizar(valor);
						panel3[i][j].setValidez(true);
						contador_celdas_correctas++;
						contador_celdas_incorrectas--;
					}else {
						panel3[i][j].actualizar(valor);
						panel3[i][j].setValidez(true);
						contador_celdas_correctas++;
						contador_celdas_incorrectas--;
					}
				}
			}else {
				//si no cumple con la propiedad de no pertenecer a fila o columna
				if(valor!=0) {
					panel3[i][j].actualizar(-valor);
					panel3[i][j].setValidez(false);
				//si valor es 0 quiere decir que es el boton eliminar	
				}else {
					if(panel3[i][j].getValidez()) {
						panel3[i][j].actualizar(valor);
						panel3[i][j].setValidez(false);
						contador_celdas_correctas--;
						contador_celdas_incorrectas++;
					}else {
						panel3[i][j].actualizar(valor);
						panel3[i][j].setValidez(false);
					}
				}
			}
			break;
		}
		case 3:{
			//Compruebo que el valor que voy a colocar en la celda no pertenezca al panel y no pertenezca a esa fila o a la columna
			if(!pertenecePanel(panel4,valor) && !perteneceFilaOColumna(i,j,valor,pos_panel) && valor!=0) {
				if(panel4[i][j].getValidez() && panel4[i][j].getValor()!=null) {
					panel4[i][j].actualizar(valor);
				}else {
					if(panel4[i][j].getValor()!=null) {
						panel4[i][j].actualizar(valor);
						panel4[i][j].setValidez(true);
						contador_celdas_correctas++;
						contador_celdas_incorrectas--;
					}else {
						panel4[i][j].actualizar(valor);
						panel4[i][j].setValidez(true);
						contador_celdas_correctas++;
						contador_celdas_incorrectas--;
					}
				}
			}else {
				//si no cumple con la propiedad de no pertenecer a fila o columna
				if(valor!=0) {
					panel4[i][j].actualizar(-valor);
					panel4[i][j].setValidez(false);
				//si valor es 0 quiere decir que es el boton eliminar	
				}else {
					if(panel4[i][j].getValidez()) {
						panel4[i][j].actualizar(valor);
						panel4[i][j].setValidez(false);
						contador_celdas_correctas--;
						contador_celdas_incorrectas++;
					}else {
						panel4[i][j].actualizar(valor);
						panel4[i][j].setValidez(false);
					}
				}
			}
			break;
		}
		case 4:{
			//Compruebo que el valor que voy a colocar en la celda no pertenezca al panel y no pertenezca a esa fila o a la columna
			if(!pertenecePanel(panel5,valor) && !perteneceFilaOColumna(i,j,valor,pos_panel) && valor!=0) {
				if(panel5[i][j].getValidez() && panel5[i][j].getValor()!=null) {
					panel5[i][j].actualizar(valor);
				}else {
					if(panel5[i][j].getValor()!=null) {
						panel5[i][j].actualizar(valor);
						panel5[i][j].setValidez(true);
						contador_celdas_correctas++;
						contador_celdas_incorrectas--;
					}else {
						panel5[i][j].actualizar(valor);
						panel5[i][j].setValidez(true);
						contador_celdas_correctas++;
						contador_celdas_incorrectas--;
					}
				}
			}else {
				//si no cumple con la propiedad de no pertenecer a fila o columna
				if(valor!=0) {
					panel5[i][j].actualizar(-valor);
					panel5[i][j].setValidez(false);
				//si valor es 0 quiere decir que es el boton eliminar	
				}else {
					if(panel5[i][j].getValidez()) {
						panel5[i][j].actualizar(valor);
						panel5[i][j].setValidez(false);
						contador_celdas_correctas--;
						contador_celdas_incorrectas++;
					}else {
						panel5[i][j].actualizar(valor);
						panel5[i][j].setValidez(false);
					}
				}
			}
			break;
		}
		case 5:{
			//Compruebo que el valor que voy a colocar en la celda no pertenezca al panel y no pertenezca a esa fila o a la columna
			if(!pertenecePanel(panel6,valor) && !perteneceFilaOColumna(i,j,valor,pos_panel) && valor!=0) {
				if(panel6[i][j].getValidez() && panel6[i][j].getValor()!=null) {
					panel6[i][j].actualizar(valor);
				}else {
					if(panel6[i][j].getValor()!=null) {
						panel6[i][j].actualizar(valor);
						panel6[i][j].setValidez(true);
						contador_celdas_correctas++;
						contador_celdas_incorrectas--;
					}else {
						panel6[i][j].actualizar(valor);
						panel6[i][j].setValidez(true);
						contador_celdas_correctas++;
						contador_celdas_incorrectas--;
					}
				}
			}else {
				//si no cumple con la propiedad de no pertenecer a fila o columna
				if(valor!=0) {
					panel6[i][j].actualizar(-valor);
					panel6[i][j].setValidez(false);
				//si valor es 0 quiere decir que es el boton eliminar	
				}else {
					if(panel6[i][j].getValidez()) {
						panel6[i][j].actualizar(valor);
						panel6[i][j].setValidez(false);
						contador_celdas_correctas--;
						contador_celdas_incorrectas++;
					}else {
						panel6[i][j].actualizar(valor);
						panel6[i][j].setValidez(false);
					}
				}
			}
			break;
		}
		case 6:{
			//Compruebo que el valor que voy a colocar en la celda no pertenezca al panel y no pertenezca a esa fila o a la columna
			if(!pertenecePanel(panel7,valor) && !perteneceFilaOColumna(i,j,valor,pos_panel) && valor!=0) {
				if(panel7[i][j].getValidez() && panel7[i][j].getValor()!=null) {
					panel1[i][j].actualizar(valor);
				}else {
					if(panel7[i][j].getValor()!=null) {
						panel7[i][j].actualizar(valor);
						panel7[i][j].setValidez(true);
						contador_celdas_correctas++;
						contador_celdas_incorrectas--;
					}else {
						panel7[i][j].actualizar(valor);
						panel7[i][j].setValidez(true);
						contador_celdas_correctas++;
						contador_celdas_incorrectas--;
					}
				}
			}else {
				//si no cumple con la propiedad de no pertenecer a fila o columna
				if(valor!=0) {
					panel7[i][j].actualizar(-valor);
					panel7[i][j].setValidez(false);
				//si valor es 0 quiere decir que es el boton eliminar	
				}else {
					if(panel7[i][j].getValidez()) {
						panel7[i][j].actualizar(valor);
						panel7[i][j].setValidez(false);
						contador_celdas_correctas--;
						contador_celdas_incorrectas++;
					}else {
						panel7[i][j].actualizar(valor);
						panel7[i][j].setValidez(false);
					}
				}
			}
			break;
		}
		case 7:{
			//Compruebo que el valor que voy a colocar en la celda no pertenezca al panel y no pertenezca a esa fila o a la columna
			if(!pertenecePanel(panel8,valor) && !perteneceFilaOColumna(i,j,valor,pos_panel) && valor!=0) {
				if(panel8[i][j].getValidez() && panel8[i][j].getValor()!=null) {
					panel8[i][j].actualizar(valor);
				}else {
					if(panel8[i][j].getValor()!=null) {
						panel8[i][j].actualizar(valor);
						panel8[i][j].setValidez(true);
						contador_celdas_correctas++;
						contador_celdas_incorrectas--;
					}else {
						panel8[i][j].actualizar(valor);
						panel8[i][j].setValidez(true);
						contador_celdas_correctas++;
						contador_celdas_incorrectas--;
					}
				}
			}else {
				//si no cumple con la propiedad de no pertenecer a fila o columna
				if(valor!=0) {
					panel8[i][j].actualizar(-valor);
					panel8[i][j].setValidez(false);
				//si valor es 0 quiere decir que es el boton eliminar	
				}else {
					if(panel8[i][j].getValidez()) {
						panel8[i][j].actualizar(valor);
						panel8[i][j].setValidez(false);
						contador_celdas_correctas--;
						contador_celdas_incorrectas++;
					}else {
						panel8[i][j].actualizar(valor);
						panel8[i][j].setValidez(false);
					}
				}
			}
			break;
		}
		case 8:{
			//Compruebo que el valor que voy a colocar en la celda no pertenezca al panel y no pertenezca a esa fila o a la columna
			if(!pertenecePanel(panel9,valor) && !perteneceFilaOColumna(i,j,valor,pos_panel) && valor!=0) {
				if(panel9[i][j].getValidez() && panel9[i][j].getValor()!=null) {
					panel9[i][j].actualizar(valor);
				}else {
					if(panel9[i][j].getValor()!=null) {
						panel9[i][j].actualizar(valor);
						panel9[i][j].setValidez(true);
						contador_celdas_correctas++;
						contador_celdas_incorrectas--;
					}else {
						panel9[i][j].actualizar(valor);
						panel9[i][j].setValidez(true);
						contador_celdas_correctas++;
						contador_celdas_incorrectas--;
					}
				}
			}else {
				//si no cumple con la propiedad de no pertenecer a fila o columna
				if(valor!=0) {
					panel9[i][j].actualizar(-valor);
					panel9[i][j].setValidez(false);
				//si valor es 0 quiere decir que es el boton eliminar	
				}else {
					if(panel9[i][j].getValidez()) {
						panel9[i][j].actualizar(valor);
						panel9[i][j].setValidez(false);
						contador_celdas_correctas--;
						contador_celdas_incorrectas++;
					}else {
						panel9[i][j].actualizar(valor);
						panel9[i][j].setValidez(false);
					}
				}
			}
			break;
		}
		}
	}
	public Celda getCelda(int i,int j,int panel) {
		Celda toReturn = null;
		
		switch(panel) {
		case 0:{
			toReturn=panel1[i][j];
			break;
		}
		case 1:{
			toReturn=panel2[i][j];
			break;
		}
		case 2:{
			toReturn=panel3[i][j];
			break;
		}
		case 3:{
			toReturn=panel4[i][j];
			break;
		}
		case 4:{
			toReturn=panel5[i][j];
			break;
		}
		case 5:{
			toReturn=panel6[i][j];
			break;
		}
		case 6:{
			toReturn=panel7[i][j];
			break;
		}
		case 7:{
			toReturn=panel8[i][j];
			break;
		}
		case 8:{
			toReturn=panel9[i][j];
			break;
		}
		}
		return toReturn;
	}
	
	protected void inicializarPanel(Celda[][] panel) {
		for (int i =0; i<panel.length; i++) {
			for (int j =0; j<panel[0].length; j++) {
				panel[i][j]= new Celda();
			}
		}
	}
	
	
}
