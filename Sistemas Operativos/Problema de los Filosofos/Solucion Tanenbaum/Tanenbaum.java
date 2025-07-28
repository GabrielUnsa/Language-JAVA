package filosofosTanenbaum;

import java.util.concurrent.Semaphore;

//Solucion Propuesta por A. Tanenbaum en su libro Sistemas Operativos: Diseño e Implementaciones 7th. Edicion.
public class Tanenbaum implements Runnable{

	private Thread Thread; //Hilo principal donde se ejecutara la aplicacion
	private int id; //Nro de filosofo asoaciado al hilo principal
	
	final private static int N = 5; //Cantidad de Filosofos
	final private static int THINKING = 0; //Valor 0 para referenciar que el filosofo esta pensando
	final private static int HUNGRY= 1; //Valor 1 para referenciar que el filosofo esta hambriendo
	final private static int EATING = 2; //Valor 2 para referenciar que el filosofo esta comiendo
	final private static int LEFT(int i) { return ( i + (N-1) ) % N ; } //Funcion devuelve el nro del filosofo que esta a mi izquierda
	final private static int RIGHT(int i) { return ( i + 1 ) % N ; } //Funcion devuelve el nro del filosofo que esta a mi derecha
	final private static Semaphore mutex = new Semaphore(1); //Monitor que nos permite ingresar a la region critica para tomar los tenedores y cambiar los estados
	final private static Semaphore mutex2 = new Semaphore(1); //Monitor que permite acceder a mi lista enlazada (region critica) donde puedo ir agregando informacion
	
	private static int[] State = new int[N]; //Vector donde guarda el estado del filosofo i
	private static Semaphore[] s = new Semaphore[N]; //Vector de semaforos para cada uno de los filosofos, sirve para que esperen su turno para comer
	private static clsList list = new clsList(); //Lista enlazada para guardar la informacion estadistica
	private static long StartProcess; //Variable donde guardara el tiempo de inicio de ejecucion
	
	
	//Vamos creando hilos cuando se convoca la funcion con el numero de id igual al nro del filosofo
	public Tanenbaum(int id) {
		this.Thread = new Thread(Tanenbaum.this);
		this.id=id;
	}
	
	//Devuelve el hilo en ejecucion
	public Thread getThread() {
		return this.Thread;
	}
	
	//Condicion de Parada: Pasaron 5 minutos de ejecucion? Si es asi entonces paramos
	public boolean control() {
		if( (System.currentTimeMillis() - StartProcess) / 1000 <= 300 ) return true;
		else return false;
	}
	
	//Muestra el nro del hilo (filosofo) que esta por pensar y duerme el hilo x cantidad de seg (rand)
	public void Think() {
		System.out.println("I am thinking: "+id);
		try {
			Thread.sleep((int) (Math.random() * 10000));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//Muestra el nro del hilo (filosofo) que esta por comer y duerme el hilo x cantidad de seg (rand)
	public void Eat() {
		System.out.println("I am eating: "+id);
		try {
			Thread.sleep((int) (Math.random() * 10000));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//Pruebo preguntando estoy hambriento y mis compañeros de izq y derecha no estan comiendo? Si es asi pongo a comer al filosofo
	//Despierto al filosofos si estaba durmiendo
	public void Test(int i) {
		if(State[i] == HUNGRY && State[LEFT(i)] != EATING && State[RIGHT(i)] != EATING) {
			State[i]=EATING;
			s[i].release();
		}
	}
	
	//Intento tomar los tenedores, si paso la prueba entonces estoy comiendo sino el filosofo se duerme a la espera que algun vecino lo despierte para comer
	public void TakeForks() {
		try { mutex.acquire(); } 
		catch (InterruptedException e) { e.printStackTrace(); }
		State[id] = HUNGRY;
		Test(id);
		mutex.release();
		try{ s[id].acquire(); }
		catch(InterruptedException e){ e.printStackTrace(); }
	}
	
	//Coloco los tenedores a la mesa y aviso a mis vecinos que termine de comer (despertandolos)
	public void PutForks() {
		try { mutex.acquire(); } 
		catch(Exception e) { e.printStackTrace(); }
		State[id] = THINKING;
		Test(LEFT(id));
		Test(RIGHT(id));
		mutex.release();
	}
	
	public void run() {
		while( control() ) {
			long StartTime = System.currentTimeMillis();
			long EatTime;
			long ThinkingTime;
			String text;
			Think();
			ThinkingTime = (System.currentTimeMillis() - StartTime) / 1000;
			text = "I thought: \t\t\t" + ThinkingTime + " \t\t\tsecond " + "->" + " I am\t\t\t " + id;
			System.out.println(text);
			try {
				mutex2.acquire();
				list.addNode(text);
			} catch(InterruptedException e){ e.printStackTrace(); }
			mutex2.release();
			TakeForks();
			Eat();
			PutForks();
			EatTime = ((System.currentTimeMillis() - StartTime) / 1000) - ThinkingTime;
			text = "I ate:     \t\t\t" + EatTime + " \t\t\tsecond " + "->" + " I am\t\t\t " + id;
			System.out.println(text);
			try {
				mutex2.acquire();
				list.addNode(text);
			} catch(InterruptedException e){ e.printStackTrace(); }
			mutex2.release();
		}
	}

	public static void main(String[] args) {
		StartProcess = System.currentTimeMillis();
		Tanenbaum[] p = new Tanenbaum[N];
		for( int i = 0; i < N ;i++) {
			p[i] = new Tanenbaum(i);
			s[i] = new Semaphore(0);
			p[i].getThread().start();
		}
		for( int i = 0; i < N ;i++) {
			try {
				p[i].getThread().join();
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
		list.saveExecutionData(N);
	}
}
