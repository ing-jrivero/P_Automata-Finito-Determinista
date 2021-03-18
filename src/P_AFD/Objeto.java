package P_AFD;

public class Objeto {
	
	public char[] cadena;
	public String estado;
	
	Objeto()
	{
		
	}
	Objeto(char[] cad,String est_ini)
	{
		cadena=cad;
		estado=est_ini;
	}
	
	public char get_char_cadena(int i) 
	{
		char caracter=cadena[i];
		return caracter;
	}

	public char[] getCadena() {
		return cadena;
	}

	public void setCadena(char[] cadena) {
		this.cadena = cadena;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	

}
