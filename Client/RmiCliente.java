package RmiGUISimple.Client;
import RmiGUISimple.Server.*;
import java.rmi.*;
import java.rmi.registry.*;
import javax.swing.*;
import java.awt.*;import java.awt.event.*;

public class RmiCliente extends JFrame implements ActionListener {
    private JTextField cajaEnviar;
    private JButton botonEnviar;
    private JLabel estado;
    private static InterfazReceptorMensajes rmiServidor;
    private static Registry registro;
    private static String direccionServidor = "Cesar"; 
    //private static String direccionServidor = "127.0.0.1"; 
    //private static String direccionServidor = "192.168.1.78";
    private static String puertoServidor = "3232";

    public RmiCliente() {
        super("Cliente RMI");
        getContentPane().setLayout(new BorderLayout());
        cajaEnviar = new JTextField();
        cajaEnviar.addActionListener(this);
        botonEnviar = new JButton("Enviar");
        botonEnviar.addActionListener(this);
        estado = new JLabel("Estado...");

        getContentPane().add(cajaEnviar);
        getContentPane().add(botonEnviar, BorderLayout.EAST);
        getContentPane().add(estado, BorderLayout.SOUTH);

        setSize(300, 100);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (!cajaEnviar.getText().equals("")) {
            enviarMensaje(cajaEnviar.getText());
            cajaEnviar.setText("");
        } 
    }

    private static void conectarseAlServidor() {
        try {
            // obtener el registro
            registro = LocateRegistry.getRegistry(direccionServidor,
            (new Integer(puertoServidor)).intValue());
            // creando el objeto remoto
            rmiServidor = (InterfazReceptorMensajes) (registro.lookup("rmiServidor"));
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }

    private void enviarMensaje(String mensaje) {
        estado.setText("Enviando " + mensaje + " a " + direccionServidor + ":" + puertoServidor);
        try {
            // llamando el metodo remoto
            rmiServidor.recibirMensaje(mensaje);
            estado.setText("El mensaje se ha enviado!!!");
        } catch (RemoteException re) {
            re.printStackTrace();
        }
    }

    static public void main(String args[]) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        conectarseAlServidor();
        RmiCliente ventana = new RmiCliente();
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}    
