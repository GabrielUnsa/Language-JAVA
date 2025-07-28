package filosofosTanenbaum;

/* 
 * Clase Node:
 * 	Atributo next: clsNode apuntando al siguiente nodo de la lista
 * 	Atributo nodeData: String contiene la informacion a alamacenar 
*/

public class clsNode {
	
	private clsNode next;
	private String nodeData;
	
	clsNode(String nodeData){
		this(nodeData, null);
	}
	
	clsNode(String nodeData, clsNode next){
		this.nodeData = nodeData;
		this.next = next;
	}
	
	public void setDataNode(String dataNode) {
		this.nodeData = dataNode;
	}
	
	public void setNextNode(clsNode next) {
		this.next = next;
	}
	
	public String getDataNode() {
		return this.nodeData;
	}
	
	public clsNode getNextNode() {
		return this.next;
	}
}
