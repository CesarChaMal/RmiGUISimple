package RmiGUISimple.Server;
import java.net.InetAddress;
import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.*;
import javax.swing.JFrame;
public class RmiServidor extends UnicastRemoteObject implements InterfazReceptorMensajes {
    private static GUIServidor ventana;
    private int estePuerto;
    private String estaIP;
    private Registry registro;

    public RmiServidor() throws RemoteException {
        try {
            // obtener la direccion de este host.
            estaIP = (InetAddress.getLocalHost()).toString();
        } catch (Exception e) {
            throw new RemoteException("No se puede obtener la direccion IP.");
        }
        estePuerto = 3232; // asignar el puerto que se registra
        ventana.anadirEntradas("Conexion establecida por...\nEsta direccion=" + estaIP + ", y puerto=" + estePuerto);
        try {
            // crear el registro y ligar el nombre y objeto.
            registro = LocateRegistry.createRegistry(estePuerto);
            registro.rebind("rmiServidor", this);
        } catch (RemoteException e) {
            throw e;
        } 
    }
    
    public void recibirMensaje(String texto) throws RemoteException {
        ventana.anadirEntradas(texto);
    }
       
    public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        ventana = new GUIServidor();
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            new RmiServidor();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        } 
    }
}
    
