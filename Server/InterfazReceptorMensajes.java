package RmiGUISimple.Server;
import java.rmi.*;
public interface InterfazReceptorMensajes extends Remote
{
    //Este es el metodo que implementar� el servidor
	void recibirMensaje(String texto) throws RemoteException;
}
