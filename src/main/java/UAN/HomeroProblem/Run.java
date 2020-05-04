package UAN.HomeroProblem;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import UAN.HomeroProblem.model.Interseccion;
import UAN.HomeroProblem.model.NaryTreeNode;

public class Run {

	private static boolean estadoProfun = true;
	private static boolean estadoAmplit = true;
	static ArrayList<NaryTreeNode> nodosHijo = new ArrayList<NaryTreeNode>();
	static int tiempoProfundidad = 0;
	static int tiempoAmplitud = 0;

	public static void main(String[] args) {

		ArrayList<String> rutaProfundidad = new ArrayList<String>();
		ArrayList<String> rutaAmplitud = new ArrayList<String>();
		ArrayList<NaryTreeNode> nodosHijo = new ArrayList<NaryTreeNode>();

		ArrayList<Interseccion> ruta = getCalles();
		NaryTreeNode arbol = getArbolConstruir(ruta);
		NaryTreeNode.print(arbol);
		System.out.println("");
		System.out.println(" -- RUTA PROFUNDIDAD  -- ");
		System.out.println("");
		rutaProfundidad = busqProfundidad(arbol, 0, "Moes_Tavern", rutaProfundidad);
		System.out.println();
		System.out.println(rutaProfundidad);
		imprimirArchivo("Profundidad",rutaProfundidad);
		System.out.println("");
		System.out.println(" -- RUTA AMPLITUD  -- ");
		System.out.println("");
		rutaAmplitud = busqAmplitud(arbol, 0, "Moes_Tavern", rutaAmplitud);
		System.out.println();
		System.out.println(rutaAmplitud);
		imprimirArchivo("Amplitud",rutaAmplitud);
		System.out.println();
		System.out.println("Tiempo Amplitud: " + tiempoAmplitud);
		System.out.println("Tiempo Profundidad: " + tiempoProfundidad);

	}
	
	public static ArrayList<Interseccion> getCalles() {
		System.out.println(" -- DATOS ARCHIVO --");
		System.out.println();
		ArrayList<Interseccion> listInterseccion = new ArrayList<Interseccion>();
		try {
			Scanner input = new Scanner(new File("src/main/resources/Input/Amplitud.txt"));
			while (input.hasNextLine()) {
				String line = input.nextLine();
				Interseccion calle = new Interseccion(line.split(" ")[0], line.split(" ")[1]);
				listInterseccion.add(calle);
				System.out.println(line);
			}
			input.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return listInterseccion;
	}
	
	public static void imprimirArchivo(String nombre, ArrayList<String> ruta) {
		FileWriter fichero = null;
		PrintWriter pw = null;
		try {
			fichero = new FileWriter("src/main/resources/Output/"+nombre+".txt");
			pw = new PrintWriter(fichero);
			for (int i = 0; i < ruta.size(); i++)
				pw.println(ruta.get(i));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != fichero)
					fichero.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	private static ArrayList<String> busqAmplitud(NaryTreeNode node, int depth, String destino,
			ArrayList<String> ruta) {
		if (depth == 0) {
			nodosHijo.add(node);
		}
		for (int i = 0; i < nodosHijo.size(); i++) {
			if (nodosHijo.get(i).getLABEL().equals(destino)) {
				ruta.add(nodosHijo.get(i).getLABEL());
				tiempoAmplitud++;
				estadoAmplit = false;
				System.out.println("Nodo buscado: " + nodosHijo.get(i).getLABEL());
				return ruta;
			}
			if (estadoAmplit) {
				ruta.add(nodosHijo.get(i).getLABEL());
				System.out.println("Nodo :" + nodosHijo.get(i).getLABEL());
				tiempoAmplitud++;
				for (NaryTreeNode child : nodosHijo.get(i).getChildren()) {
					System.out.println("Hijo: " + child.getLABEL());
					nodosHijo.add(child);
				}
				nodosHijo.remove(i);
				i--;
			}
		}
		return ruta;
	}

	private static ArrayList<String> busqProfundidad(NaryTreeNode node, int depth, String destino,
			ArrayList<String> ruta) {
		if (node.getLABEL().equals(destino)) {
			ruta.add(node.getLABEL());
			System.out.println(depth + "  " + node.getLABEL());
			tiempoProfundidad++;
			estadoProfun = false;
			return ruta;
		}
		if (estadoProfun) {
			ruta.add(node.getLABEL());
			System.out.println(depth + "  " + node.getLABEL());
			tiempoProfundidad++;
			for (NaryTreeNode child : node.getChildren()) {
				busqProfundidad(child, depth + 1, destino, ruta);
			}
		}
		return ruta;
	}

	public static NaryTreeNode getArbolConstruir(ArrayList<Interseccion> ruta) {
		NaryTreeNode root = new NaryTreeNode(ruta.get(0).getCalle1());
		buscaHijo(root, 0, ruta);
		return root;
	}

	private static void buscaHijo(NaryTreeNode node, int depth, ArrayList<Interseccion> ruta) {
		for (int i = 0; i < ruta.size(); i++) {
			if (node.getLABEL().equals(ruta.get(i).getCalle1())) {
				node.addChild(ruta.get(i).getCalle2());
				ruta.get(i).setCalle1("");
				ruta.get(i).setCalle2("");
			} else if (node.getLABEL().equals(ruta.get(i).getCalle2())) {
				node.addChild(ruta.get(i).getCalle1());
				ruta.get(i).setCalle1("");
				ruta.get(i).setCalle2("");
			}
		}
		for (NaryTreeNode child : node.getChildren()) {
			buscaHijo(child, depth + 1, ruta);
		}
	}
}
