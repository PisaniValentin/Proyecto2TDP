package Logica;

import javax.swing.ImageIcon;

public class EntidadGrafica {
	private ImageIcon grafico;
	private String[] imagenes;
	
	public EntidadGrafica() {
		this.grafico = new ImageIcon();
		this.imagenes = new String[]{"/imagenes/0.png","/imagenes/1.png", "/imagenes/2.png", "/imagenes/3.png","/imagenes/4.png","/imagenes/5.png","/imagenes/6.png","/imagenes/7.png","/imagenes/8.png","/imagenes/9.png"};
	}
	
	public ImageIcon getGrafico() {
		return this.grafico;
	}
	
	public void setGrafico(ImageIcon grafico) {
		this.grafico = grafico;
	}
	
	public String[] getImagenes() {
		return this.imagenes;
	}
	
	public void setImagenes(String[] imagenes) {
		this.imagenes = imagenes;
	}
	
	public void actualizar(Integer valor) {
		ImageIcon imageIcon = new ImageIcon(this.getClass().getResource(this.imagenes[valor]));
		this.grafico.setImage(imageIcon.getImage());
	}
	
}
