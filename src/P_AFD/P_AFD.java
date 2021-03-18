package P_AFD;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;


public class P_AFD {
	//variables de la aplicacion escritura y lectura
	static FileWriter fichero = null;
    static PrintWriter pw = null;
	static int n_estados;
	static int n_lenguaje;
	static String inicial;

	static String [] f_A;
	static String [] estados;
	static String [] lenguaje ;
	static String [][] matriz_esc;
	static String [] linea_escritura = new String [100]; 
//	static String lineaLeidaO;
	static String lineaLeida;
	//static String []lineatem2;
	static int d;
	static File archivo;
    static FileReader fr;
    static BufferedReader br;
    //fin de las variables lec / esc
    //variables para escribir AFD en grupos
   static String tem="";
    
    //fin de variables para escribir AFD en grupos
   
   //nuevas variables
   static String [][]matriz_S;
   static String [] matriz_d;
   static boolean est_aut;
   
   //---------------
    //variables de los metodos para validar el lenguaje con la cadena
   static int tam;
   static char[] cadena_A;
	static char[] len_char;
	static Objeto obj;
//	static String f;
   //-----------------------
	//Variables para la minimizacion
	static String[] estadosNoAseptacion;
	static String[] estadosAseptacion;
	static String[][] m1=new String[100][100];
	static String[][] conjuntos1=new String [100][100];
	//--------------------------------
	static String[] v_qs;
	static String v_q;
	static String[] v_ls;
	static String v_l;
	static String[] v_ms;
	static String v_m;
	////////////////////////////////////
	//variables para Determinizar
	static String [][] matriz_determinizar;
	static boolean regresar=false;
	static String[] qs_d;
	static String[] fs_d; 
	
	
    
    static Scanner sc = new Scanner (System.in);

	public static void main(String[] args) {
	
		boolean ciclo=true;
		while(ciclo) 
		{
			int op=0;
			System.out.println("===========================================================");
			System.out.println("1.-Escribir un automata");
			System.out.println("2.-Leer un automata");
			System.out.println("3.-Terminar Programa");
			System.out.println("===========================================================");
			op=sc.nextInt();
			switch(op) 
			{
			case 1:

				llenar_linea();
		//		System.out.println("introduce el numero de estados del automata");
				System.out.println("Introduce los estados del automata separados por una coma");
				tem=sc.next();
				estados=tem.split(",");
				n_estados=estados.length;
		//		n_estados=sc.nextInt();
		//		estados = new String [n_estados];
			//	pedir_datos(n_estados, estados, "Estados");
				
		//		System.out.println("introduce el numero de caracteres del lenguaje");
				System.out.println("Introduce el lenguaje del automata separados por una coma");
				tem=sc.next();
				lenguaje=tem.split(",");
				n_lenguaje=lenguaje.length;
	//			n_lenguaje=sc.nextInt();
		//		lenguaje = new String [n_lenguaje];
		//		pedir_datos(n_lenguaje, lenguaje, "Caracter");
				
				matriz_esc = new String [n_estados][n_lenguaje];
				System.out.println("Introduce el estado inicial");
				inicial=sc.next();
				
				System.out.println("Introduce los estados F separados por una coma");
				tem=sc.next();
				f_A=tem.split(",");
				
				
				llenar_matriz(n_estados,n_lenguaje, matriz_esc, estados, lenguaje);
				
				System.out.println("-----------------------------------------------------------");
				mostrar_vector("Q", estados);
				mostrar_vector("E", lenguaje);
				System.out.println("Estado inicial = "+inicial);
				mostrar_vector("F", f_A);
				mostrar_matriz(n_estados, n_lenguaje, matriz_esc);
				System.out.println("-----------------------------------------------------------");
				pasarDatosLinea_V("Q", estados);
				pasarDatosLinea_V("E", lenguaje);
				pasarDatosLinea_i("I", inicial);
				pasarDatosLinea_V("F", f_A);
				pasarDatosLinea_M("x", n_estados, n_lenguaje, matriz_esc);
				//System.out.println(linea_escritura.toString());
				escribir();
			
				break;
			case 2:
				
				
				
				est_aut=true;
				leer();
	//			lineaLeidaO=lineaLeida;
				String[] vec_q=sacar_datos_S(lineaLeida, 'Q').split(",");
				estados=vec_q;
				String[] vec_l=sacar_datos_S(lineaLeida, 'E').split(",");
				lenguaje=vec_l;
				f_A=sacar_datos_S(lineaLeida, 'F').split(",");
			//	String f_tem=f_A[0];
		//		f=f_tem;
				String[] matriz_leida=sacar_datos_S(lineaLeida, 'x').split("/");
				matriz_d=matriz_leida;
				inicial=sacar_datos_S(lineaLeida, 'I');
				String[] ini_tem=inicial.split(",");
				inicial=ini_tem[0];
				
				
			//	System.out.println(lineatem[0]+lineatem[1]);
				comprobar_afd_I();
				comprobar_afd_F();
				comprobar_afd_Qs();
				System.out.println("-----------------------------------------------------------");
				
				System.out.println("Q = "+sacar_datos_S(lineaLeida, 'Q'));
				System.out.println("E = "+sacar_datos_S(lineaLeida, 'E'));
				System.out.println("q0 = "+sacar_datos_S(lineaLeida, 'I'));
				System.out.println("F = "+sacar_datos_S(lineaLeida, 'F'));
			//	System.out.println("X = "+sacar_datos_chilo(lineaLeida, 'x'));
				
				/////
				/*String*/ v_q="";
				/*String*/ v_l="";
				/*String*/ v_m="";
				
				/////
				 d=0;
				for(int i=0;i<vec_q.length;i++) 
				{
					
					for(int j=0;j<vec_l.length;j++) 
					{
					//	System.out.println(d);
					//	System.out.println("mateiz "+matriz_leida.length);
						System.out.println(vec_q[i]+" X "+vec_l[j]+" = "+matriz_leida[d]);
						v_q+=vec_q[i]+",";
						v_l+=vec_l[j]+",";
						v_m+=matriz_leida[d]+"/";
						
						d++;
					}
				}
				
				while(est_aut) 
				{
					int op2=0;
					System.out.println("===========================================================");
					System.out.println("                     AUTOMATA VALIDO");
					System.out.println("===========================================================");
					System.out.println("1.-Introducir Cadena");
					System.out.println("2.-Minimizar Automata");
					System.out.println("3.-Determinizar");
					System.out.println("4.-Regresar Al Menu");
					System.out.println("===========================================================");
					op2=sc.nextInt();
					switch(op2) 
					{
					case 1:
				//		est_aut=true;
						String cadena;
						System.out.println("Introduzca la cadena que va a procesar el automata");
						cadena=sc.next();
						
						if(validar_cadena(cadena)) 
						{
							/*String[]*/ v_qs=v_q.split(",");
							/*String[]*/ v_ls=v_l.split(",");
							/*String[]*/ v_ms=v_m.split("/");
					//		System.out.println("Es valido");
							//vamos a evaluar la cadena en el automata
						
							obj=new Objeto(cadena_A,inicial);
							Crear_matriz_S(v_qs,v_ls,v_ms);
					//		System.out.println("columnas: "+matriz_S[0].length);
					//		System.out.println("Reglones: "+matriz_S.length);
					//		mostrar_matriz(matriz_S);
							revicion_AFD(obj);
						}
						else 
						{
							System.out.println("-----------------------------------------------------------");
							System.out.println("La cadena no coincide con el alfabeto establecido");
						}

						break;
					
					case 2:
						iniciar_vectores_estados();
						System.out.println("estados de No aceptacion");
						for(int i=0;i<estadosNoAseptacion.length;i++) 
						{
							System.out.println(estadosNoAseptacion[i]);
						}
						System.out.println("estados de aceptacion");
						for(int i=0;i<estadosAseptacion.length;i++) 
						{
							System.out.println(estadosAseptacion[i]);
						}
						llenar_M_conjuntos();
						Minimizacion();
						System.out.println("===========================================================");
						mostrar_m1();
						
						break;
					
					case 3:
						v_qs=v_q.split(",");
						v_ls=v_l.split(",");
						v_ms=v_m.split("/");
				//		System.out.println(v_ms[0]+v_ms[1]+v_ms[2]);
						Crear_matriz_S(v_qs,v_ls,v_ms);
						matriz_determinizar=new String [25][lenguaje.length+1];
						inicia_M_determinizar();
					//	mostrar_m_determinizar();
						determinizar();
						determinizar_qs();
						iniciar_fs_d();
					//	determinizar_f();
						System.out.println("-----------------------------------------------------------");
						System.out.println("AFN Convertido en AFD");
						System.out.println("===========================================================");
						mostrar_x_d(qs_d, "Q");
						System.out.println("E = "+sacar_datos_S(lineaLeida, 'E'));
						System.out.println("q0 = "+sacar_datos_S(lineaLeida, 'I'));
						mostrar_x_d(fs_d, "F");
						mostrar_m_determinizar();
						break;
					case 4:
						est_aut=false;
						
						break;
				}
				
				}
				break;
			case 3:
				System.out.println("Programa Terminado");
				System.out.println("===========================================================");
				ciclo=false;
				
				break;
			
			
			}
			
			

			
		}
				
		
	}

	
	public static String sacar_datos_S(String linea,char revicion) 
	{
		String sal="";
		char [] lin_part= linea.toCharArray();
		int p1=0;
		int p2=0;
		boolean est=false;
		for(int i=0;i<lin_part.length;i++) 
		{
			if(lin_part[i]==revicion&& est==false) 
			{
				p1=i;
				est=true;
			}
			else if(lin_part[i]==revicion && est==true) 
			{
				p2=i;
			}
		}
		for(int j=p1+1;j<p2;j++) 
		{
			sal+=lin_part[j];
		}
		return sal;
		}
	
		public static String sacar_Datos(String linea,String revicion) 
	{
		
		String []lineatem;
		String salida="";
		lineatem= lineaLeida.split(revicion);
		lineaLeida="";
		System.out.println("Partido por "+revicion);
		for(int i=1;i<lineatem.length;i++) 
		{
			lineaLeida+=lineatem[i];
		}
		
		
	/*	for(int i=0;i<lineatem.length;i++) 
		{
			salida+=lineatem[i];
		}*/
		String []algo=lineaLeida.split(",");
		salida=algo[0];
		
		
		return salida;
	}
	
	public static void pasarDatosLinea_i(String ct,String dato) 
	{
		int n=ubicar_indice(linea_escritura);
		boolean t=false;
		for(int i=0;i<100;i++) 
		{
			i=i+n;
			if(i<100 && t==false) 
			{
				linea_escritura[i]=ct;
				i++;
				
					linea_escritura[i]=dato+",";
					i++;
				t=true;
				linea_escritura[i]=ct;
				i++;
				
			}
		}
	}
	
	public static void pasarDatosLinea_V(String ct,String [] vector) 
	{
		int n=ubicar_indice(linea_escritura);
		boolean t=false;
		for(int i=0;i<100;i++) 
		{
			i=i+n;
			if(i<100 && t==false) 
			{
		//		System.out.println("entramos al if "+i);
				linea_escritura[i]=ct;
				i++;
	//			System.out.println("pusimos letra de inicio "+i);
		
				for(int j=0;j<vector.length;j++) 
				{
		//			System.out.println("vamos a escribir un chilo "+i);
					linea_escritura[i]=vector[j]+",";
					i++;
		//			System.out.println("terminamos de escribir un chilo "+i);
					
		
				}
				t=true;
				
	//			System.out.println("vamos a poner letra de cierre "+i);
				linea_escritura[i]=ct;
				i++;
	//			System.out.println("pusimos a poner letra de cierre "+i);
		
				
			}
		}
	}
	
	
	public static void pasarDatosLinea_M(String ct,int e,int l,String [][] matriz) 
	{
		int n=ubicar_indice(linea_escritura);
		boolean t=false;
		for(int i=0;i<100;i++) 
		{
			i=i+n;
			if(i<100 && t==false) 
			{
		//		System.out.println("entramos al if "+i);
				linea_escritura[i]=ct;
				i++;
	//			System.out.println("pusimos letra de inicio "+i);
		
				for(int j=0;j<e;j++) 
				{
					for(int k=0;k<l;k++) 
					{

						//			System.out.println("vamos a escribir un chilo "+i);
									linea_escritura[i]=matriz[j][k]+"/";
									i++;
						//			System.out.println("terminamos de escribir un chilo "+i);
										
					}
		
				}
				t=true;
				
	//			System.out.println("vamos a poner letra de cierre "+i);
				linea_escritura[i]=ct;
				i++;
	//			System.out.println("pusimos a poner letra de cierre "+i);
		
				
			}
		}
	}
	
	public static int ubicar_indice(String[] l_e) 
	{
		int n=0;
		for(int i=0;i<100;i++) 
		{
			if(l_e[i]=="/") 
			{
				n=i;
				break;
		
				
			}
		
		}
		return n;
		
		
	}
	
	public static void llenar_linea() 
	{
		for(int i=0; i<100;i++) 
		{
			linea_escritura[i]="/";
		}
	}
	
	public static void llenar_matriz(int e,int l,String [][] matriz,String[] est,String [] len) 
	{
		String dato="";
		System.out.println("introduzca la tabla de trancicion en caso de que el dato sea lamda introduzca -");
		for(int i=0;i<e;i++) 
		{
			for(int j=0;j<l;j++) 
			{
				System.out.println("De "+est[i]+" x "+len[j]+" = ");
				dato=sc.next();
				matriz[i][j]=dato;
			}
		}
		
	}
	
	public static void mostrar_vector(String text,String [] vector) 
	{
		System.out.print(text+" = ");
		for(int i=0;i<vector.length;i++) 
		{
			System.out.print(vector[i]+",");
			
		}
		System.out.println("");
	} 
	
	public static void mostrar_matriz(int e, int l, String [][]matriz) 
	{
		
		
		for(int i=0;i<e;i++) 
		{
			for(int j=0;j<l;j++) 
			{
				System.out.println(estados[i]+" x "+lenguaje[j]+" = "+matriz[i][j]);
				
			}
		}
		
	}
	
	/*
	public static void pedir_datos(int n, String[] m,String nom) 
	{
		String dato="";
		//System.out.println("n es = "+n);
	//	System.out.println("length de m[] = "+m.length);
		for(int i=0; i<n;i++) 
		{
			System.out.println("Introduce el "+nom+" numero "+(i+1));
			dato=sc.next();
			m[i]=dato;
	//		System.out.println("length de m[] = "+m.length);
			
		}
		
	}*/
	
	public static void leer() 
	{

	      try {
	    	  // Apertura del fichero y creacion de BufferedReader para poder
	    	    // hacer una lectura comoda (disponer del metodo readLine()).
	    	    archivo = new File ("archivo.txt");
	    	    fr = new FileReader (archivo);
	    	    br = new BufferedReader(fr);

	         // Lectura del fichero
	         String linea;
	         while((linea=br.readLine())!=null)
	         //   System.out.println(linea);
	       lineaLeida=linea;  
	         
	      }
	      catch(Exception e){
	         e.printStackTrace();
	      }finally{
	         // En el finally cerramos el fichero, para asegurarnos
	         // que se cierra tanto si todo va bien como si salta 
	         // una excepcion.
	         try{                    
	            if( null != fr ){   
	               fr.close();     
	            }                  
	         }catch (Exception e2){ 
	            e2.printStackTrace();
	         }
	      }
	   }
	
	public static void escribir() 
	{
		FileWriter fichero = null;
        PrintWriter pw = null;
        try
        {
            fichero = new FileWriter("archivo.txt");
            pw = new PrintWriter(fichero);

            for (int i = 0; i < 100; i++)
                pw.print(linea_escritura[i]);
            
         //   pw.println(linea_escritura);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
           // Nuevamente aprovechamos el finally para 
           // asegurarnos que se cierra el fichero.
           if (null != fichero)
              fichero.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
    
	}
	
	
	//----------------------------------------------------------------------------
/*	public static void generar_matriz()
	{
		String[][] matriz_S=new String[estados.length+1][lenguaje.length+1];
		matriz_S[0][0]="QXE";
		//llenado de nombres de coumnas
		for(int i=1;i<(lenguaje.length+1);i++) 
		{
			matriz_S[0][i]=lenguaje[i-1];
		}
		for(int i=1;i<(estados.length+1);i++) 
		{
			matriz_S[i][0]=estados[i-1];
		}
		int d=0;
		for(int n=1;n<(estados.length+1);n++) 
		{
			
			for(int m=1;m<(lenguaje.length+1);m++) 
			{
				matriz_S[n][m]=matriz_d[d];
				d++;
			}
		}
		
	//	matriz_S=matriz;
	}
	*/
	public static void mostrar_matriz(String [][] mz) 
	{
		
		for(int i=0;i<mz.length;i++) 
		{
			for(int j=0;j<mz[0].length;j++) 
			{
				System.out.print(mz[i][j]+"   ");
			}
			System.out.println();
		}
		
	}
	
	public static void comprobar_afd_I() 
	{
		boolean est_aut=false;
		for(int i=0;i<estados.length;i++) 
		{
			if(estados[i].equals(inicial)) 
			{
				est_aut=true;
				return;
			}
			else 
			{
				
			}
			
		}
		System.out.println("-----------------------------------------------------------");
		System.out.println("ERROR el estado incial no se encuentra en los estados Q");
		
		return;
	}

	
	public static void comprobar_afd_F() 
	{
		est_aut=false;
		int f_S=0;
		//comprobando si f esta en Q
		for(int i=0;i<estados.length;i++) 
		{
			for(int j=0;j<f_A.length;j++) 
			{
				if(estados[i].equals(f_A[j])) 
				{
					f_S++;
		//			System.out.println("encontramos "+f_S+" f's");
				}
			else 
			{	
			}
			}
			if(f_S==f_A.length) 
			{
				est_aut=true;
				return;
				
			}
		
		}
	//	System.out.println(f_S);
		System.out.println("-----------------------------------------------------------");
		System.out.println("ERROR el estado F no se encuentra en los estados Q");
		
	}
	
	public static void comprobar_afd_Qs() 
	{
		est_aut=false;
		int m_d=0;
		int n_est=estados.length;
		String [] estados_lamda =new String [n_est+1];
		for(int i=0;i<estados_lamda.length;i++) 
		{
			if(i==n_est) 
			{
				estados_lamda[i]="-";
			}
			else 
			{
				estados_lamda[i]=estados[i];
			}
		}
		
		//comprobando si Qs esta en Q
		for(int i=0;i<estados_lamda.length;i++) 
		{
			boolean coma=false;
			for(int j=0;j<matriz_d.length;j++) 
			{
				
				char[]temC;
				temC=matriz_d[j].toCharArray();
				for(int q=0;q<temC.length;q++) 
				{
					if(temC[q]==',') 
					{
						coma=true;
					}
					else 
					{
						
					}
					
				}
			if(coma) 
			{
				int est_lam_n=0;
				String[] temS=matriz_d[j].split(",");
				for(int z=0;z<temS.length;z++) 
				{
					for(int y=0;y<estados_lamda.length;y++) 
					{
						if(temS[z].equals(estados_lamda[y])) 
						{
							est_lam_n++;
						}
						else
						{
							
						}
					}
					
				}
			//	System.out.println("est_lam = "+est_lam_n+" temS.length = "+temS.length);
				if(est_lam_n==temS.length) 
				{
				//	System.out.println("sumamos por "+matriz_d[j]);
					m_d++;
				}
				
			}
			else 
			{
				if(estados_lamda[i].equals(matriz_d[j])) 
				{
				//	System.out.println("sumamos por ="+matriz_d[j]+ " estado ="+estados_lamda[i] );
					m_d++;
		//			System.out.println("encontramos "+f_S+" f's");
				}
			else 
			{
				
			}
			
			}	
			}
		//	System.out.println(matriz_d.length+" m_d "+m_d);
			
			if(m_d==matriz_d.length) 
			{
				est_aut=true;
				return;
			}
		}
	//	System.out.println(f_S);
		System.out.println("-----------------------------------------------------------");
		System.out.println("ERROR la matriz de estados no concuerdan con los estados Q");
		
	}
	
	public static boolean validar_cadena(String cad) 
	{
		est_aut=true;
		String tem_cad="";
		for(int i=0;i<lenguaje.length;i++) 
		{
	//		System.out.println(lenguaje[i]);
			tem_cad+=lenguaje[i];
		}
		
		cadena_A=cad.toCharArray();
		len_char=tem_cad.toCharArray();
		revisar_cad();
		if(tam==cadena_A.length) 
		{
		//	System.out.println("-----------------------------------------------------------");
		//	System.out.println("La cadena SI coincide con el alfabeto establecido");
			return true;
		}
		else 
		{
			
			return false;
		}
	
	}
	
	
	
	public static boolean revisar_char(char n) 
	{
		for(int i=0;i<len_char.length;i++) 
		{
			if(len_char[i]==n) 
			{
				return true;
			}
			
		}
		return false;
	}
	
	public static void revisar_cad() 
	{
		tam=0;
		est_aut=true;
		
		for(int i=0;i<cadena_A.length;i++) 
		{
			if(revisar_char(cadena_A[i])) 
			{
				tam++;
			}
			else 
			{
				
			}
				
			
			
		}
	} 
	
	public static void Crear_matriz_S(String[] qs,String[] ls,String[] ms) 
	{
	//	System.out.println("qs= "+qs.length);
		matriz_S=new String[qs.length][3];
	//	System.out.println("qs= "+qs.length);
	//	int d=0;
		for(int i=0;i<ms.length;i++) 
		{
			
			matriz_S[i][0]=qs[i];
			matriz_S[i][1]=ls[i];
			matriz_S[i][2]=ms[i];
			//System.out.println("qs= "+matriz_S[i][0]+" Ls= "+matriz_S[i][1]+" Ms= "+matriz_S[i][2]);
			
		}
		
	}
	
	public static void revicion_AFD(Objeto obj) 
	{
		char[]cad=obj.getCadena();
		String est=obj.getEstado();
		
	for(int i=0;i<cad.length;i++) 
		{
		
		String c=""+cad[i];
//		System.out.println("letra= "+c);
		
		R_afd(c,est);
		est=obj.getEstado();
//		System.out.println("estado= "+est);
		
	}
	if(Verificar_F(est, f_A))
	{
		System.out.println("-----------------------------------------------------------");
		System.out.println("El Automata Ha Terminado en el Estado "+est+ " Cadena Aceptada");	
	}
	else 
	{
		System.out.println("-----------------------------------------------------------");
		System.out.println("El Automata Ha Terminado en el Estado "+est+ " Cadena No Aceptada");
	}
	
	
	}
	
	public static void R_afd(String c,String est) 
	{
		
		for(int j=0;j<matriz_S.length;j++) 
		{
			
//		System.out.println("estado matriz= "+matriz_S[j][0]+" letra matriz= "+matriz_S[j][1]);
		if(matriz_S[j][0].equals(est)&&matriz_S[j][1].equals(c)) 
		{
//			System.out.println("entramos al if");
			est=matriz_S[j][2];
			obj.setEstado(est);
		
			return;
		}
		}
	}
	
	public static boolean Verificar_F(String est,String[] f) 
	{
		for(int i=0;i<f.length;i++) 
		{
			if(est.equals(f[i])) 
			{
				return true;
			}
		}
		return false;
	}
	
	public static void iniciar_vectores_estados() 
	{
		String[]tem=new String [((estados.length)-(f_A.length))];
		estadosAseptacion=f_A;
		
		boolean est;
		int p=0;
		for(int i=0;i<estados.length;i++) 
		{
			est=true;
			for(int j=0;j<f_A.length;j++) 
			{
				if(estados[i].equals(f_A[j])) 
				{
					est=false;
				}
			}
			if(est) 
			{
				tem[p]=estados[i];
				p++;
			}
		}
		estadosNoAseptacion=tem;
	}
	
	public static void llenar_M_conjuntos() 
	{
		for(int i=0; i<m1.length;i++) 
		{
			for(int j=0;j<m1[0].length;j++) 
			{
				m1[i][j]="/";
				conjuntos1[i][j]="/";
				
			}
		}
	}
	
	public static boolean esta_en(String q,String[] conjunto) 
	{
		for(int i=0;i<conjunto.length;i++) 
		{
			if(conjunto[i].equals(q)) 
			{
				return true;
			}
		}
		return false;
	}
	
	
	public static  void inicia_M_determinizar() 
	{
		matriz_determinizar[0][0]="QXE";
		for(int i=1;i<lenguaje.length+1;i++) 
		{
			matriz_determinizar[0][i]=lenguaje[i-1];
		}
		for(int i=1;i<matriz_determinizar.length;i++) 
		{
			for(int j =0;j<matriz_determinizar[0].length;j++) 
			{
				matriz_determinizar[i][j]="/";
			}
		}
		
	}
	
	public static void mostrar_m_determinizar() 
	{
		for(int i=0; i<matriz_determinizar.length;i++) 
		{
			for(int j=0;j<matriz_determinizar[0].length;j++) 
			{
				if(matriz_determinizar[i][j].equals("/")) 
				{
					return;
				}
				System.out.print(matriz_determinizar[i][j]+"   |   ");
			}
			System.out.println("");
		}
	}
	
	public static String mover_a(String q,String l) 
	{
		String R="";
		for(int j=0;j<matriz_S.length;j++) 
		{
			
//		System.out.println("estado matriz= "+matriz_S[j][0]+" letra matriz= "+matriz_S[j][1]);
		if(matriz_S[j][0].equals(q)&&matriz_S[j][1].equals(l)) 
		{
//			System.out.println("entramos al if");
			R=matriz_S[j][2];
			
		
			
		}
     //	return R;
		}
		return R;
	}
	
	
	public static void determinizar()
	{
		regresar=false;
//		System.out.println(inicial);
		matriz_determinizar[1][0]=inicial;
		String [] v_tem;
		String temd="";
		boolean ult=false;
		int d=2;
		for(int i=1; i<matriz_determinizar.length;i++) 
		{
	
			if(regresar) 
			{
				return;
			}
			for(int j=1; j<matriz_determinizar[0].length;j++) 
			{
				if(j==matriz_determinizar[0].length-1) 
				{
					ult=true;
				}
				else 
				{
					ult=false;
				}
				v_tem=matriz_determinizar[i][0].split(",");
				String ext;
				if(v_tem.length>1) 
				{
					ext=",";
				}
				else 
				{
					ext="";
				}
		
				if(ult) 
				{
			//		System.out.println(matriz_determinizar[i-1][0]+"   "+matriz_determinizar[i-1][1]+"   "+matriz_determinizar[i-1][2]);
						
				}
					temd=QXE_determinizar(v_tem,lenguaje[j-1],ext);
					char[] est_char=temd.toCharArray();
					String est="";
					
					if(temd.endsWith(",")) 
					{
						for(int a=0;a<est_char.length-1;a++) 
						{
			//				if(est_char[a]!=',') 
				//			{
								est+=est_char[a];	
					//		}
							
						}
					}
					else 
					{
						for(int a=0;a<est_char.length;a++) 
						{
							est+=est_char[a];
						}
					}
					
					temd=est;
					matriz_determinizar[i][j]=temd;
					if(estan_en_C(temd)) 
					{

					}
					else 
					{
						matriz_determinizar[d][0]=temd;
						
						d++;

					}
				
				
				//revisar
					if(ult) {
					if(seguir(i)) 
					{
	//				return;
					}
					else 
					{
				return;		
					}
					
					}
			}
			
			
			
		}
	}
	
	public static boolean estan_en_C(String t) 
	{
	
		for(int i=1;i<matriz_determinizar.length;i++) 
		{
			if(matriz_determinizar[i][0].equals(t)) 
			{
				return true;
			}
		}
		return false;
	}
	
	public static String QXE_determinizar(String[] v,String l,String ext) 
	{
		String t="";
		String m="";
		for(int z=0;z<v.length;z++)
		{
			m=mover_a(v[z], l);
			if(m.equals("-")) 
			{
				
			}
			else 
			{
				t+=m+ext;	
			}

			
			
		}
		
		return t;
	} 
	
	public static boolean seguir(int q) 
	{
	
		int n=q+1;
	//	for(int i=0;i<matriz_determinizar[0].length;i++) 
	//	{
	//	System.out.println(matriz_determinizar[q][0]+" ==>  "+matriz_determinizar[n][0]);
			if(matriz_determinizar[n][0].equals("/")) 
			{
				regresar=true;
//				return true;
				if(!estan_en_C(matriz_determinizar[n][0])) 
				{
					return false;	
				}
				
			}
			
	//	}
		return true;
	}
	
	public static void determinizar_qs() 
	{
		
		int o=0;
		for(int i=0;i<matriz_determinizar.length;i++) 
		{
			if(matriz_determinizar[i][0].contains("q")) 
			{
				o++;
			}
		}
		qs_d=new String [o];
		for(int i=1; i<matriz_determinizar.length;i++) 
		{
			if(matriz_determinizar[i][0].equals("/")) 
			{
				
			}
			else
			{
				qs_d[i-1]=matriz_determinizar[i][0];
		//		System.out.println(qs_d[i-1]);
			}
		}
		
	}
	public static void determinizar_f() 
	{
		
		for(int i=0; i<qs_d.length;i++) 
		{
			for(int j=0;j<f_A.length;j++) 
			{
				if(qs_d[i].equals(f_A[j])) 
				{
					fs_d[i]=qs_d[i];
				}
				else
				{
					
				}
			}
		}
		
	}
	
	public static  void iniciar_fs_d() 
	{
		int o=1;
		for(int i=0;i<qs_d.length;i++) 
		{
			for(int j=0;j<f_A.length;j++) 
			{
				if(qs_d[i].contains(f_A[j])) 
				{
					o++;
				}	
			}
			
		}
		fs_d=new String [o-1];
		int p=0;
		for(int i=0;i<qs_d.length;i++) 
		{
			for(int j=0;j<f_A.length;j++) 
			{
	//		System.out.println("qs_d = "+qs_d[i]+" f_A = "+f_A[j]+" = "+qs_d[i].contains(f_A[j]));
				if(qs_d[i].contains(f_A[j])) 
				{
					fs_d[p]=qs_d[i];
					p++;
				}	
			}
			
		}
		
	}
	
	public static void mostrar_x_d(String [] x,String nom) 
	{
		String p=nom+"=";
		for(int i=0;i<x.length;i++) 
		{
			p+=x[i]+"  ";
		}
		
		System.out.println(p);
	}
	
	///////////////////////
	public static void Minimizacion() 
	{
		int p=1;
		int t=0;
		for(int i=0;i<m1.length;i++) 
		{
			m1[t][0]="QXE "+p;
			//llenamos el reglon del lenguaje
			for(int l=1;l<lenguaje.length+1;l++) 
			{
				m1[t][l]="   "+lenguaje[l-1];
			}
			for(int q=1;q<estados.length+1;q++) 
			{
				//llenamos los estaodos de no aceptacion
				if(esta_en(estados[q-1],estadosNoAseptacion)) 
				{
					m1[q][t]=estados[q-1];
					
				}
			}
			for(int q=1;q<estados.length+1;q++) 
			{
				//llenamos los estados de aceptacion
				if(esta_en(estados[q-1],estadosAseptacion)) 
				{
					m1[q][t]=estados[q-1];
					
				}
			}
		}
	}
	
	public static void mostrar_m1() 
	{
		for(int i=0;i<m1.length;i++) 
		{
			for(int j=0;j<m1[0].length;j++) 
			{
				System.out.print(m1[i][j]);
				
			}
			System.out.println("");
		}
	}

}
