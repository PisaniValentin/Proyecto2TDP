package Logica;

public class Celda {
	protected Integer valor;
	protected String imagen;
	protected boolean esValido;
	protected EntidadGrafica entidadGrafica;
	
	public Celda() {
		esValido=false;
		valor=null;
		imagen=null;
		entidadGrafica=new EntidadGrafica();
	}
	
	public void setValidez(boolean validez) {
		esValido=validez;
	}
	
	public boolean getValidez(){
		return esValido;
	}
	
	public void actualizar(Integer valor) {
		this.valor=valor;
		if(valor>0) {
			entidadGrafica.actualizar(valor);
		}else {
			entidadGrafica.actualizar(Math.abs(valor));
		}
	}
	
	public EntidadGrafica getEntidadGrafica() {
		return entidadGrafica;
	}
	
	public Integer getValor() {
		return valor;
	}
	
	public void setEntidadGrafica(EntidadGrafica imagen) {
		entidadGrafica=imagen;
	}
	
	public void setValor(Integer valor) {
		this.valor=valor;
	}
	
	
}


