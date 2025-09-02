package TanenbaumSolution;

/*
 * Clase Nodo: Contendra la informacion del nodo de la lista enlazada 
 * @clsNode: nextNode - Siguiente nodo de la lista
 * @String: nodeData - Informacion del nodo
 */

public class clsNode {
	
	private clsNode nextNode; 
	private String nodeData;
	
	clsNode(String nodeData, clsNode next) {
		this.nodeData = nodeData;
		this.nextNode = nextNode;
	}
	
	clsNode(String nodeData) {
		this(nodeData, null);
	}
	
	//Setters & Getters
	public void setDataNode(String dataNode) {
		this.nodeData = dataNode;
	}
	
	public void setNextNode(clsNode next) {
		this.nextNode = next;
	}
	
	public String getNodeInfo() {
		return this.nodeData;
	}
	
	public clsNode getNextNode() {
		return this.nextNode;
	}
}
