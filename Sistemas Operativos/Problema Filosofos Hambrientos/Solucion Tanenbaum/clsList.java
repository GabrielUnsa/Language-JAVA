package TanenbaumSolution;

/*
 * Clase Lista: Lista enlazada 
 * @clsNode: start - Inicio de la lista (nodo inicial)
 * @clsNode: end - Fin de la lista (nodo final)
 */

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class clsList {
	
	protected clsNode start;
	private clsNode end;
	
	public clsList() {
		createList();
	}
	
	public void createList() {
		this.start=this.end=null;
	}
	
	public boolean isClear() {
		return (this.start==null);
	}
	
	public clsNode getStart() {
		return start;
	}
	
	public String getEndInfo() {
		return this.end.getNodeInfo();
	}
	
	public void addNode(String data) {
		if( isClear() ) {
			this.start=this.end=new clsNode(data);
		} else {
			this.end.setNextNode(new clsNode(data));
			this.end = end.getNextNode();
		}
	}
	
	public int getValue(String valueString) {
		if( valueString.indexOf(" ") == -1 ) return Integer.valueOf(valueString.substring(0,2));
		else return Integer.valueOf(valueString.substring(0,1));
	}
	
	public void saveExecutionData( int quantityPhilosophers ) {
		try {
			JFileChooser auxLog = new JFileChooser();
			int index = 0;
			String values = null;
			int valuesLen = 0;
			auxLog.showSaveDialog(null);
			File log = auxLog.getSelectedFile();
			if(log != null) {
				FileWriter save = new FileWriter( log + ".txt");
				BufferedWriter bufferFile = new BufferedWriter(save);
				bufferFile.write("Resultados de las Pruebas Realizadas");
				bufferFile.newLine();
				bufferFile.newLine();
				while( index < quantityPhilosophers) {
					int total = 0;
					int eat = 0;
					int think = 0;
					bufferFile.write("*******Philosopher: " + index + "*********" );
					bufferFile.newLine();
					for(clsNode tmp=this.start; tmp != null; tmp=tmp.getNextNode()) {
						values = tmp.getNodeInfo();
						valuesLen = values.length();
						if( values.substring(valuesLen-1 , valuesLen).compareTo(String.valueOf(index)) == 0 ) {
							bufferFile.write(values.replace("\t","").substring(0, 13));
							bufferFile.newLine();
							total += getValue(values.replace("\t","").substring(11,13));
							if(values.substring(2,3).compareTo("a") == 0) {
								eat += getValue(values.replace("\t","").substring(11,13));
							}else {
								think += getValue(values.replace("\t","").substring(11,13));
							}
						}
					}
					bufferFile.write("Total: \t\t\t"+total);
					bufferFile.newLine();
					bufferFile.write("Total Comiendo: \t\t\t"+eat);
					bufferFile.newLine();
					bufferFile.write("Total Pensando: \t\t\t"+think);
					bufferFile.newLine();
					bufferFile.newLine();
					index++;
				}
				bufferFile.close();
				JOptionPane.showMessageDialog(null, "El archivo se guardo exitosamente", "Información", JOptionPane.INFORMATION_MESSAGE);
			}
			
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, "El archivo se guardo exitosamente", "Información", JOptionPane.INFORMATION_MESSAGE);
			e.printStackTrace();
		}
	}
}
