package GUI;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import Logica.Celda;
import Logica.Juego;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.Timer;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JFileChooser;

public class GUI extends JFrame {
	private JButton [][] botones_tablero1,botones_tablero2,botones_tablero3,botones_tablero4,botones_tablero5,botones_tablero6;
	private JButton [][] botones_tablero7,botones_tablero8,botones_tablero9;
	private String informacion;
	private JButton [] botones_numeros;
	private String [] icono = new String[] {"/imagenes/1.png","/imagenes/2.png","/imagenes/3.png","/imagenes/4.png","/imagenes/5.png",
											"/imagenes/6.png","/imagenes/7.png","/imagenes/8.png","/imagenes/9.png","/imagenes/eliminar.png"};
	
	private String [] numeros_cronometro = new String[] {"/imagenesCrono/0.png","/imagenesCrono/1.png","/imagenesCrono/2.png","/imagenesCrono/3.png",
														 "/imagenesCrono/4.png","/imagenesCrono/5.png","/imagenesCrono/6.png","/imagenesCrono/7.png",
														 "/imagenesCrono/8.png","/imagenesCrono/9.png","/imagenesCrono/dospuntos.png"};
	private JPanel contentPane;
	private JPanel tablero;
	private JPanel panel_timer,panel_1,panel_2,panel_3,panel_4,panel_5,panel_6,panel_7,panel_8,panel_9;
	private Juego juego;
	private JButton iniciar_button;
	private JButton pausar_button;
	private Timer timer;
	private int h,m,s;
	private JLabel [] arreglo_tiempo;
	private JButton deleteButton;
	private JLabel estadoPartida;
	/**
	 * Launch the application.
	 */

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	

	/**
	 * Create the frame.
	 */
	public GUI() {
		super("Sudoku");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 613, 346);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		armarBotones();
		armarTablero();
		juego=new Juego();
		
		
		iniciar_button = new JButton("Comenzar");
		iniciar_button.setEnabled(false);
		iniciar_button.setBounds(358, 260, 109, 23);
		contentPane.add(iniciar_button);
		iniciar_button.addActionListener(new OyenteBotonIniciar());
		
		pausar_button = new JButton("Pausar");
		pausar_button.setEnabled(false);
		pausar_button.setBounds(477, 260, 89, 23);
		contentPane.add(pausar_button);
		pausar_button.addActionListener(new OyenteBotonPausar());
		
		estadoPartida = new JLabel("En proceso");
		estadoPartida.setFont(new Font("Tahoma", Font.BOLD, 11));
		estadoPartida.setBackground(Color.WHITE);
		estadoPartida.setForeground(Color.WHITE);
		estadoPartida.setBounds(452, 56, 103, 33);
		contentPane.add(estadoPartida);
		
		JButton cargarButton = new JButton("Cargar Archivo");
		cargarButton.addActionListener(new OyenteBotonCargar());
		cargarButton.setBounds(439, 9, 148, 23);
		contentPane.add(cargarButton);
		
		
		JLabel statusLabel = new JLabel("Estado partida:");
		statusLabel.setForeground(Color.WHITE);
		statusLabel.setFont(statusLabel.getFont().deriveFont(statusLabel.getFont().getStyle() | Font.BOLD));
		statusLabel.setBounds(357, 61, 89, 23);
		contentPane.add(statusLabel);
		timer = new Timer(1000,new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				++s;
				if(s==60) {
					s=0;
					++m;
				}
				if(m==60) {
					m=0;
					++h;
				}
				actualizarTimer();
			}
		});
		armarPanelTimer();
	}
	
	private void armarPanelTimer() {
		panel_timer = new JPanel();
		panel_timer.setBackground(Color.BLACK);
		panel_timer.setBounds(378, 222, 125, 29);
		contentPane.add(panel_timer);
		panel_timer.setLayout(new GridLayout(1, 0, 0, 0));
		
		arreglo_tiempo = new JLabel[8];
		for(int i=0; i<arreglo_tiempo.length;i++) {
			JLabel etiqueta = new JLabel("");
			arreglo_tiempo[i]=etiqueta;
			ImageIcon icon = new ImageIcon(this.getClass().getResource(numeros_cronometro[0]));
			Icon icono_redimensionado = new ImageIcon(icon.getImage().getScaledInstance(panel_timer.getWidth()/11,panel_timer.getHeight()/2,Image.SCALE_SMOOTH));
			etiqueta.setIcon(icono_redimensionado);
			panel_timer.add(etiqueta);
		}
		
		ImageIcon icono_dospuntos1 = new ImageIcon(this.getClass().getResource(numeros_cronometro[numeros_cronometro.length-1]));
		Icon icono_dospuntos1_redimensionado = new ImageIcon(icono_dospuntos1.getImage().getScaledInstance(panel_timer.getWidth()/11,panel_timer.getHeight()/2,Image.SCALE_SMOOTH));
		arreglo_tiempo[arreglo_tiempo.length-3].setIcon(icono_dospuntos1_redimensionado);
		
		ImageIcon icono_dospuntos2 = new ImageIcon(this.getClass().getResource(numeros_cronometro[numeros_cronometro.length-1]));
		Icon icono_dospuntos2_redimensionado = new ImageIcon(icono_dospuntos2.getImage().getScaledInstance(panel_timer.getWidth()/11,panel_timer.getHeight()/2,Image.SCALE_SMOOTH));
		arreglo_tiempo[arreglo_tiempo.length-6].setIcon(icono_dospuntos2_redimensionado);
		
	}
	private void actualizarTimer() {
		
		ImageIcon icono_s1 = new ImageIcon(this.getClass().getResource(numeros_cronometro[s%10]));
		Icon icono_s1_redimensionado1 = new ImageIcon(icono_s1.getImage().getScaledInstance(panel_timer.getWidth()/11,panel_timer.getHeight()/2,Image.SCALE_SMOOTH));
		arreglo_tiempo[arreglo_tiempo.length-1].setIcon(icono_s1_redimensionado1);
		
		ImageIcon icono_s2 = new ImageIcon(this.getClass().getResource(numeros_cronometro[s/10]));
		Icon icono_s2_redimensionado1 = new ImageIcon(icono_s2.getImage().getScaledInstance(panel_timer.getWidth()/11,panel_timer.getHeight()/2,Image.SCALE_SMOOTH));
		arreglo_tiempo[arreglo_tiempo.length-2].setIcon(icono_s2_redimensionado1);
		
		ImageIcon icono_m1 = new ImageIcon(this.getClass().getResource(numeros_cronometro[m%10]));
		Icon icono_m1_redimensionado1 = new ImageIcon(icono_m1.getImage().getScaledInstance(panel_timer.getWidth()/11,panel_timer.getHeight()/2,Image.SCALE_SMOOTH));
		arreglo_tiempo[arreglo_tiempo.length-4].setIcon(icono_m1_redimensionado1);
		
		ImageIcon icono_m2 = new ImageIcon(this.getClass().getResource(numeros_cronometro[m/10]));
		Icon icono_m2_redimensionado1 = new ImageIcon(icono_m2.getImage().getScaledInstance(panel_timer.getWidth()/11,panel_timer.getHeight()/2,Image.SCALE_SMOOTH));
		arreglo_tiempo[arreglo_tiempo.length-5].setIcon(icono_m2_redimensionado1);
		
		ImageIcon icono_h1 = new ImageIcon(this.getClass().getResource(numeros_cronometro[h%10]));
		Icon icono_h1_redimensionado1 = new ImageIcon(icono_h1.getImage().getScaledInstance(panel_timer.getWidth()/11,panel_timer.getHeight()/2,Image.SCALE_SMOOTH));
		arreglo_tiempo[arreglo_tiempo.length-7].setIcon(icono_h1_redimensionado1);
		
		ImageIcon icono_h2 = new ImageIcon(this.getClass().getResource(numeros_cronometro[h/10]));
		Icon icono_h2_redimensionado1 = new ImageIcon(icono_h2.getImage().getScaledInstance(panel_timer.getWidth()/11,panel_timer.getHeight()/2,Image.SCALE_SMOOTH));
		arreglo_tiempo[arreglo_tiempo.length-8].setIcon(icono_h2_redimensionado1);
		
	}
	
	/**
	 * Metodo en el que inicializo el panel de botones a insertar.
	 */
	private void armarBotones() {
		JPanel botones = new JPanel();
		botones.setBounds(314, 9, 33, 274);
		contentPane.add(botones);
		botones.setLayout(new GridLayout(9, 1, 0, 0));
		botones_numeros = new JButton[9];
		for(int i=0;i<botones_numeros.length;i++) {
			botones_numeros[i]=new JButton();
			botones_numeros[i].setActionCommand(Integer.toString(i));
			botones_numeros[i].addActionListener(new OyenteBotonImagenes());
			botones_numeros[i].setEnabled(false);
			botones.add(botones_numeros[i]);
			ImageIcon icon = new ImageIcon(this.getClass().getResource(icono[i]));
			Icon icono_redimensionado = new ImageIcon(icon.getImage().getScaledInstance(20,17,Image.SCALE_DEFAULT));
			botones_numeros[i].setIcon(icono_redimensionado);
			botones_numeros[i].setBackground(Color.white);
		}
		deleteButton = new JButton("");
		deleteButton.setBounds(351, 9, 36, 28);
		contentPane.add(deleteButton);
		deleteButton.setEnabled(false);
		deleteButton.addActionListener(new OyenteBotonBorrar());
		ImageIcon icon = new ImageIcon(this.getClass().getResource(icono[9]));
		Icon icono_redimensionado = new ImageIcon(icon.getImage().getScaledInstance(20,17,Image.SCALE_DEFAULT));
		deleteButton.setIcon(icono_redimensionado);
		deleteButton.setBackground(Color.white);



	}
	
	private void setEnabledPanel(JButton[][] panel,boolean b) {
		for(int i = 0; i<panel.length ; i++) {
			for(int j = 0; j<panel.length ; j++) {
				panel[i][j].setEnabled(b);
			}
		}
	}
	
	/**
	 * Metodo privado en el cual inicializo el tablero que contiene a los paneles.
	 */
	private void armarTablero() {
		tablero = new JPanel();
		tablero.setBackground(new Color(0, 0, 0));
		tablero.setBounds(2, 11, 300, 268);
		contentPane.add(tablero);
		tablero.setLayout(null);
		
		panel_1 = new JPanel();
		panel_1.setBackground(Color.BLACK);
		panel_1.setBounds(0, 0, 97, 86);
		tablero.add(panel_1);
		panel_1.setLayout(new GridLayout(3, 3, 0, 0));
		
		panel_2 = new JPanel();
		panel_2.setBackground(Color.BLACK);
		panel_2.setBounds(101, 0, 97, 86);
		tablero.add(panel_2);
		panel_2.setLayout(new GridLayout(3, 3, 0, 0));
		
		panel_3 = new JPanel();
		panel_3.setBackground(Color.BLACK);
		panel_3.setBounds(202, 0, 97, 86);
		tablero.add(panel_3);
		panel_3.setLayout(new GridLayout(3, 3, 0, 0));
		
		panel_4 = new JPanel();
		panel_4.setBackground(Color.BLACK);
		panel_4.setBounds(0, 91, 97, 86);
		tablero.add(panel_4);
		panel_4.setLayout(new GridLayout(3, 3, 0, 0));
		
		panel_5 = new JPanel();
		panel_5.setBackground(Color.BLACK);
		panel_5.setBounds(101, 91, 97, 86);
		tablero.add(panel_5);
		panel_5.setLayout(new GridLayout(3, 3, 0, 0));
		
		panel_6 = new JPanel();
		panel_6.setBounds(202, 91, 97, 86);
		tablero.add(panel_6);
		panel_6.setBackground(Color.BLACK);
		panel_6.setLayout(new GridLayout(3, 3, 0, 0));
		
		panel_7 = new JPanel();
		panel_7.setBackground(Color.BLACK);
		panel_7.setBounds(0, 182, 97, 86);
		tablero.add(panel_7);
		panel_7.setLayout(new GridLayout(3, 3, 0, 0));
		
		panel_8 = new JPanel();
		panel_8.setBackground(Color.BLACK);
		panel_8.setBounds(101, 182, 97, 86);
		tablero.add(panel_8);
		panel_8.setLayout(new GridLayout(3, 3, 0, 0));
		
		panel_9 = new JPanel();
		panel_9.setBounds(202, 182, 97, 86);
		tablero.add(panel_9);
		panel_9.setBackground(Color.BLACK);
		panel_9.setLayout(new GridLayout(3, 3, 0, 0));
		
		botones_tablero1= new JButton[3][3];
		botones_tablero2= new JButton[3][3];
		botones_tablero3= new JButton[3][3];
		botones_tablero4= new JButton[3][3];
		botones_tablero5= new JButton[3][3];
		botones_tablero6= new JButton[3][3];
		botones_tablero7= new JButton[3][3];
		botones_tablero8= new JButton[3][3];
		botones_tablero9= new JButton[3][3];
		
		crearPanel(botones_tablero1,panel_1,0);
		setEnabledPanel(botones_tablero1,false);
		crearPanel(botones_tablero2,panel_2,1);
		setEnabledPanel(botones_tablero2,false);
		crearPanel(botones_tablero3,panel_3,2);
		setEnabledPanel(botones_tablero3,false);
		crearPanel(botones_tablero4,panel_4,3);
		setEnabledPanel(botones_tablero4,false);
		crearPanel(botones_tablero5,panel_5,4);
		setEnabledPanel(botones_tablero5,false);
		crearPanel(botones_tablero6,panel_6,5);
		setEnabledPanel(botones_tablero6,false);
		crearPanel(botones_tablero7,panel_7,6);
		setEnabledPanel(botones_tablero7,false);
		crearPanel(botones_tablero8,panel_8,7);
		setEnabledPanel(botones_tablero8,false);
		crearPanel(botones_tablero9,panel_9,8);
		setEnabledPanel(botones_tablero9,false);
		
	
	}
	
	
	
	/**
	 * Metodo en el que inicializo un panel
	 * @param panel panel a inicializar.
	 * @param panel_contenedor panel en donde se agrega el tablero inicializado.
	 * @param pos entero para referenciar al tablero.
	 */
	private void crearPanel(JButton[][] panel,JPanel panel_contenedor,int pos) {
		for(int i=0;i<panel.length;i++) {
			for(int j=0;j<panel[0].length;j++) {
				panel[i][j]=new JButton();
				panel[i][j].setActionCommand(i+","+j+","+pos);
				panel[i][j].addActionListener(new OyenteBotonTablero()); 
				panel[i][j].setEnabled(true);
				panel[i][j].setBackground(Color.white);
				panel_contenedor.add(panel[i][j]);
			}
		}
	}
	
	private void actualizar_imagenes() {
		Celda celda = null;
		Icon resized_icon=null;
		for(int i =0 ; i<botones_tablero1.length;i++) {
			for(int j =0 ; j<botones_tablero1[0].length;j++) {
				celda = juego.getCelda(i,j,0);
				ImageIcon imagen= celda.getEntidadGrafica().getGrafico();
				resized_icon = new ImageIcon(imagen.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT));
				botones_tablero1[i][j].setIcon(resized_icon);
				botones_tablero1[i][j].setBackground(Color.white);
				
			}
		}
		for(int i =0 ; i<botones_tablero2.length;i++) {
			for(int j =0 ; j<botones_tablero2[0].length;j++) {
				celda = juego.getCelda(i, j,1);
				ImageIcon imagen= celda.getEntidadGrafica().getGrafico();
				resized_icon = new ImageIcon(imagen.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT));
				botones_tablero2[i][j].setIcon(resized_icon);
				botones_tablero2[i][j].setBackground(Color.white);
			}
		}
		for(int i =0 ; i<botones_tablero3.length;i++) {
			for(int j =0 ; j<botones_tablero3[0].length;j++) {
				celda = juego.getCelda(i, j,2);
				ImageIcon imagen= celda.getEntidadGrafica().getGrafico();
				resized_icon = new ImageIcon(imagen.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT));
				botones_tablero3[i][j].setIcon(resized_icon);
				botones_tablero3[i][j].setBackground(Color.white);
			}
		}
		for(int i =0 ; i<botones_tablero4.length;i++) {
			for(int j =0 ; j<botones_tablero4[0].length;j++) {
				celda = juego.getCelda(i, j,3);
				ImageIcon imagen= celda.getEntidadGrafica().getGrafico();
				resized_icon = new ImageIcon(imagen.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT));
				botones_tablero4[i][j].setIcon(resized_icon);
				botones_tablero4[i][j].setBackground(Color.white);
			}
		}
		for(int i =0 ; i<botones_tablero5.length;i++) {
			for(int j =0 ; j<botones_tablero5[0].length;j++) {
				celda = juego.getCelda(i, j,4);
				ImageIcon imagen= celda.getEntidadGrafica().getGrafico();
				resized_icon = new ImageIcon(imagen.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT));
				botones_tablero5[i][j].setIcon(resized_icon);
				botones_tablero5[i][j].setBackground(Color.white);
			}
		}
		for(int i =0 ; i<botones_tablero6.length;i++) {
			for(int j =0 ; j<botones_tablero6[0].length;j++) {
				celda = juego.getCelda(i, j,5);
				ImageIcon imagen= celda.getEntidadGrafica().getGrafico();
				resized_icon = new ImageIcon(imagen.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT));
				botones_tablero6[i][j].setIcon(resized_icon);
				botones_tablero6[i][j].setBackground(Color.white);
			}
		}
		for(int i =0 ; i<botones_tablero7.length;i++) {
			for(int j =0 ; j<botones_tablero7[0].length;j++) {
				celda = juego.getCelda(i, j,6);
				ImageIcon imagen= celda.getEntidadGrafica().getGrafico();
				resized_icon = new ImageIcon(imagen.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT));
				botones_tablero7[i][j].setIcon(resized_icon);
				botones_tablero7[i][j].setBackground(Color.white);
			}
		}
		for(int i =0 ; i<botones_tablero8.length;i++) {
			for(int j =0 ; j<botones_tablero8[0].length;j++) {
				celda = juego.getCelda(i, j,7);
				ImageIcon imagen= celda.getEntidadGrafica().getGrafico();
				resized_icon = new ImageIcon(imagen.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT));
				botones_tablero8[i][j].setIcon(resized_icon);
				botones_tablero8[i][j].setBackground(Color.white);
			}
		}
		for(int i =0 ; i<botones_tablero9.length;i++) {
			for(int j =0 ; j<botones_tablero9[0].length;j++) {
				celda = juego.getCelda(i, j,8);
				ImageIcon imagen= celda.getEntidadGrafica().getGrafico();
				resized_icon = new ImageIcon(imagen.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT));
				botones_tablero9[i][j].setIcon(resized_icon);
				botones_tablero9[i][j].setBackground(Color.white);
			}
		}
	}

	private class OyenteBotonTablero implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			Icon icono=null;
			Celda celda =null;
			String[] codigo= e.getActionCommand().split(",");
			int i = Integer.parseInt(codigo[0]);
			int j = Integer.parseInt(codigo[1]);
			int pos = Integer.parseInt(codigo[2]);
			switch(pos){
			case 0: {
				celda = juego.getCelda(i, j,pos);
				if(informacion!=null && !celda.getValidez()) {
					juego.colocar(e.getActionCommand(),Integer.parseInt(informacion));
					ImageIcon imagen = celda.getEntidadGrafica().getGrafico();
					icono = new ImageIcon(imagen.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT));
					if(celda.getValor()>=0) {
						botones_tablero1[i][j].setIcon(icono);
						botones_tablero1[i][j].setBackground(Color.white);
					}else {
						botones_tablero1[i][j].setIcon(icono);
						botones_tablero1[i][j].setBackground(Color.RED);
					}
					//si presione el boton de borrar	
				}else {
					if(informacion ==null ) {
						celda.setValor(null);
						if(celda.getValidez()) {
							juego.colocar(e.getActionCommand(),0);
						}
						celda.setValidez(false);
						botones_tablero1[i][j].setIcon(icono);
						botones_tablero1[i][j].setBackground(Color.white);
					}
				}
				break;
			}
			case 1: {
				celda = juego.getCelda(i, j,pos);
				if(informacion!=null && !celda.getValidez()) {
					juego.colocar(e.getActionCommand(),Integer.parseInt(informacion));
					ImageIcon imagen= celda.getEntidadGrafica().getGrafico();
					icono = new ImageIcon(imagen.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT));
					if(celda.getValor()>=0) {
						botones_tablero2[i][j].setIcon(icono);
						botones_tablero2[i][j].setBackground(Color.white);
					}else {
						botones_tablero2[i][j].setIcon(icono);
						botones_tablero2[i][j].setBackground(Color.RED);
					}
					//si presione el boton de borrar	
				}else {
					if(informacion ==null ) {
						celda.setValor(null);
						if(celda.getValidez()) {
							juego.colocar(e.getActionCommand(),0);
						}
						celda.setValidez(false);
						botones_tablero2[i][j].setIcon(icono);
						botones_tablero2[i][j].setBackground(Color.white);
					}
				}
				break;
			}
			case 2: {
				celda = juego.getCelda(i, j,pos);
				if(informacion!=null && !celda.getValidez()) {
					juego.colocar(e.getActionCommand(),Integer.parseInt(informacion));
					ImageIcon imagen= celda.getEntidadGrafica().getGrafico();
					icono = new ImageIcon(imagen.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT));
					if(celda.getValor()>=0) {
						botones_tablero3[i][j].setIcon(icono);
						botones_tablero3[i][j].setBackground(Color.white);
					}else {
						botones_tablero3[i][j].setIcon(icono);
						botones_tablero3[i][j].setBackground(Color.RED);
					}
					//si presione el boton de borrar	
				}else {
					if(informacion ==null ) {
						celda.setValor(null);
						if(celda.getValidez()) {
							juego.colocar(e.getActionCommand(),0);
						}
						celda.setValidez(false);
						botones_tablero3[i][j].setIcon(icono);
						botones_tablero3[i][j].setBackground(Color.white);
					}
				}
				break;
			}
			case 3: {
				celda = juego.getCelda(i, j,pos);
				if(informacion!=null && !celda.getValidez()) {
					juego.colocar(e.getActionCommand(),Integer.parseInt(informacion));
					ImageIcon imagen= celda.getEntidadGrafica().getGrafico();
					icono = new ImageIcon(imagen.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT));
					if(celda.getValor()>=0) {
						botones_tablero4[i][j].setIcon(icono);
						botones_tablero4[i][j].setBackground(Color.white);
					}else {
						botones_tablero4[i][j].setIcon(icono);
						botones_tablero4[i][j].setBackground(Color.RED);
					}
					//si presione el boton de borrar	
				}else {
					if(informacion ==null ) {
						celda.setValor(null);
						if(celda.getValidez()) {
							juego.colocar(e.getActionCommand(),0);
						}
						celda.setValidez(false);
						botones_tablero4[i][j].setIcon(icono);
						botones_tablero4[i][j].setBackground(Color.white);
					}
				}
				break;
			}
			case 4: {
				celda = juego.getCelda(i, j,pos);
				if(informacion!=null && !celda.getValidez()) {
					juego.colocar(e.getActionCommand(),Integer.parseInt(informacion));
					ImageIcon imagen= celda.getEntidadGrafica().getGrafico();
					icono = new ImageIcon(imagen.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT));
					if(celda.getValor()>=0) {
						botones_tablero5[i][j].setIcon(icono);
						botones_tablero5[i][j].setBackground(Color.white);
					}else {
						botones_tablero5[i][j].setIcon(icono);
						botones_tablero5[i][j].setBackground(Color.RED);
					}
					//si presione el boton de borrar	
				}else {
					if(informacion ==null ) {
						celda.setValor(null);
						if(celda.getValidez()) {
							juego.colocar(e.getActionCommand(),0);
						}
						celda.setValidez(false);
						botones_tablero5[i][j].setIcon(icono);
						botones_tablero5[i][j].setBackground(Color.white);
					}
				}
				break;
			}
			case 5: {
				celda = juego.getCelda(i, j,pos);
				if(informacion!=null && !celda.getValidez()) {
					juego.colocar(e.getActionCommand(),Integer.parseInt(informacion));
					ImageIcon imagen= celda.getEntidadGrafica().getGrafico();
					icono = new ImageIcon(imagen.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT));
					if(celda.getValor()>=0) {
						botones_tablero6[i][j].setIcon(icono);
						botones_tablero6[i][j].setBackground(Color.white);
					}else {
						botones_tablero6[i][j].setIcon(icono);
						botones_tablero6[i][j].setBackground(Color.RED);
					}
					//si presione el boton de borrar	
				}else {
					if(informacion ==null ) {
						celda.setValor(null);
						if(celda.getValidez()) {
							juego.colocar(e.getActionCommand(),0);
						}
						celda.setValidez(false);
						botones_tablero6[i][j].setIcon(icono);
						botones_tablero6[i][j].setBackground(Color.white);
					}
				}
				break;
			}
			case 6: {
				celda = juego.getCelda(i, j,pos);
				if(informacion!=null && !celda.getValidez()) {
					juego.colocar(e.getActionCommand(),Integer.parseInt(informacion));
					ImageIcon imagen= celda.getEntidadGrafica().getGrafico();
					icono = new ImageIcon(imagen.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT));
					if(celda.getValor()>=0) {
						botones_tablero7[i][j].setIcon(icono);
						botones_tablero7[i][j].setBackground(Color.white);
					}else {
						botones_tablero7[i][j].setIcon(icono);
						botones_tablero7[i][j].setBackground(Color.RED);
					}
					//si presione el boton de borrar	
				}else {
					if(informacion ==null ) {
						celda.setValor(null);
						if(celda.getValidez()) {
							juego.colocar(e.getActionCommand(),0);
						}
						celda.setValidez(false);
						botones_tablero7[i][j].setIcon(icono);
						botones_tablero7[i][j].setBackground(Color.white);
					}
				}
				break;
			}
			case 7: {
				celda = juego.getCelda(i, j,pos);
				if(informacion!=null && !celda.getValidez()) {
					juego.colocar(e.getActionCommand(),Integer.parseInt(informacion));
					ImageIcon imagen= celda.getEntidadGrafica().getGrafico();
					icono = new ImageIcon(imagen.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT));
					if(celda.getValor()>=0) {
						botones_tablero8[i][j].setIcon(icono);
						botones_tablero8[i][j].setBackground(Color.white);
					}else {
						botones_tablero8[i][j].setIcon(icono);
						botones_tablero8[i][j].setBackground(Color.RED);
					}
					//si presione el boton de borrar	
				}else {
					if(informacion ==null ) {
						celda.setValor(null);
						if(celda.getValidez()) {
							juego.colocar(e.getActionCommand(),0);
						}
						celda.setValidez(false);
						botones_tablero8[i][j].setIcon(icono);
						botones_tablero8[i][j].setBackground(Color.white);
					}
				}
				break;
			}
			case 8: {
				celda = juego.getCelda(i, j,pos);
				if(informacion!=null && !celda.getValidez()) {
					juego.colocar(e.getActionCommand(),Integer.parseInt(informacion));
					ImageIcon imagen= celda.getEntidadGrafica().getGrafico();
					icono = new ImageIcon(imagen.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT));
					if(celda.getValor()>=0) {
						botones_tablero9[i][j].setIcon(icono);
						botones_tablero9[i][j].setBackground(Color.white);
					}else {
						botones_tablero9[i][j].setIcon(icono);
						botones_tablero9[i][j].setBackground(Color.RED);
					}
					//si presione el boton de borrar	
				}else {
					if(informacion ==null ) {
						celda.setValor(null);
						if(celda.getValidez()) {
							juego.colocar(e.getActionCommand(),0);
						}
						celda.setValidez(false);
						botones_tablero9[i][j].setIcon(icono);
						botones_tablero9[i][j].setBackground(Color.white);
					}
				}
				break;
			}
			}
			if(juego.getVictoria() && juego.getValidezSolucion()) {
				estadoPartida.setText("VICTORIA");
				timer.stop();
				iniciar_button.setEnabled(false);
				pausar_button.setEnabled(false);
			}	
		}
		
	}
	
	private class OyenteBotonBorrar implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			informacion=null;
		}
	}

	private class OyenteBotonImagenes implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			informacion=e.getActionCommand();
		}

	}
	
	private class OyenteBotonIniciar implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			iniciar_button.setEnabled(false);
			pausar_button.setEnabled(true);
			for(int i = 0;i<botones_numeros.length; i++) {
				botones_numeros[i].setEnabled(true);
			}
			deleteButton.setEnabled(true);
			setEnabledPanel(botones_tablero1,true);
			setEnabledPanel(botones_tablero2,true);
			setEnabledPanel(botones_tablero3,true);
			setEnabledPanel(botones_tablero4,true);
			setEnabledPanel(botones_tablero5,true);
			setEnabledPanel(botones_tablero6,true);
			setEnabledPanel(botones_tablero7,true);
			setEnabledPanel(botones_tablero8,true);
			setEnabledPanel(botones_tablero9,true);
			timer.start();
			if(iniciar_button.getText().equals("Comenzar")) {
				iniciar_button.setText("Reanudar");
			}
			
			
		}
	}
	
	private void restartTimer() {
		s=0;
		m=0;
		h=0;
	}
	
	private class OyenteBotonPausar implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			for(int i = 0;i<botones_numeros.length; i++) {
				botones_numeros[i].setEnabled(false);
			}
			pausar_button.setEnabled(false);
			deleteButton.setEnabled(false);
			setEnabledPanel(botones_tablero1,false);
			setEnabledPanel(botones_tablero2,false);
			setEnabledPanel(botones_tablero3,false);
			setEnabledPanel(botones_tablero4,false);
			setEnabledPanel(botones_tablero5,false);
			setEnabledPanel(botones_tablero6,false);
			setEnabledPanel(botones_tablero7,false);
			setEnabledPanel(botones_tablero8,false);
			setEnabledPanel(botones_tablero9,false);
			timer.stop();
			iniciar_button.setEnabled(true);
		}
	}
	
	private class OyenteBotonCargar implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			Boolean esValido=false;
			JFileChooser fc=new JFileChooser();
			//Creao el filtro
			FileNameExtensionFilter filtro = new FileNameExtensionFilter("*.TXT", "txt");
			//le aplico el filtro
			fc.setFileFilter(filtro);
			
			
			int seleccion=fc.showOpenDialog(contentPane);
			if(seleccion==JFileChooser.APPROVE_OPTION){
				restartTimer();
				actualizarTimer();
				iniciar_button.setText("Comenzar");
				//Seleccionamos el fichero
				File fichero=fc.getSelectedFile();
				esValido = juego.cargarSolucion(fichero.getAbsolutePath());
				
				juego.generarEstadoini();
				if(esValido && juego.getValidezSolucion() ) {
					actualizar_imagenes();
				}else {
					estadoPartida.setText("Solucion Invalida");
				}
				iniciar_button.setEnabled(true);
				pausar_button.setEnabled(true);
				
			}
			}
		}
}

