
package simulador_memoria;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Simulador_memoria extends JFrame{
    private JPanel panelIzquierdo, panelSuperior, panelCentro;
    private JButton aceptar;
    JLabel etiquetaMem = new JLabel("  Memoria Total:  ");
    JLabel etiquetaProc = new JLabel("No. de Procesos:  ");
    JLabel etiquetaPart = new JLabel("No. de Particiones:  ");
    JLabel etiquetaInfo = new JLabel("   Informacion:       ");
    JTextField campoMem = new JTextField("", 5);
    JTextField campoProc = new JTextField("", 5);
    JTextField campoPart = new JTextField("", 5);
    Dibujar dibujo = new Dibujar();
    JFrame ventana= new JFrame("Formulario");
    int numProc, numPart,tamT,ban=0;

    
    public void contruyePanelSuperior(){
        panelSuperior = new JPanel();
        aceptar=new JButton("Aceptar");
        
        panelSuperior.add(etiquetaMem); panelSuperior.add(campoMem);
        panelSuperior.add(etiquetaProc); panelSuperior.add(campoProc);
        panelSuperior.add(etiquetaPart); panelSuperior.add(campoPart);
        
        aceptar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                if (campoMem.getText().length()==0 || 
                     campoProc.getText().length()==0 || 
                     campoPart.getText().length()==0 ){
                    JOptionPane.showMessageDialog(ventana,
                    "Todos los campos son requeridos",
                    "ERROR",JOptionPane.ERROR_MESSAGE);
                }else{
                    try{ 
                        tamT=Integer.parseInt(campoMem.getText());
                        numProc=Integer.parseInt(campoProc.getText());
                        numPart=Integer.parseInt(campoPart.getText());
                        if(tamT<0 || numProc<0 || numPart<0){
                            JOptionPane.showMessageDialog(ventana,
                            "Ingrese numeros positivos",
                            "ERROR",JOptionPane.ERROR_MESSAGE);
                        }else{
                            dibujo.particionar(numPart,tamT);
                            if(dibujo.compara()==false)
                                dibujo.agregarProc(numProc);
                        }
                    }catch(Exception e){ //tratamiento a errores
                        JOptionPane.showMessageDialog(ventana,
                        "Solo se aceptan numeros ",
                        "ERROR",JOptionPane.ERROR_MESSAGE);
                    }                    
                }
            }});
        
        panelSuperior.add(aceptar);
        panelSuperior.setBackground(Color.cyan);
 
    }

    public void contruyePanelIzquierdo(){
        panelIzquierdo = new JPanel();
        panelIzquierdo.add(etiquetaInfo);
       
        panelIzquierdo.setLayout(new BoxLayout(panelIzquierdo,BoxLayout.X_AXIS));
        panelIzquierdo.setBackground(Color.orange);
    }
    
    /*public void construyePanelCentro(){
        panelCentro= new JPanel();
        panelCentro.add(lienzo);
    }
    */

    public void contruyeVentana(){
        JFrame frame = new JFrame();
        
        //agregamos los paneles al frame principal
        frame.add(dibujo);
        frame.add(panelIzquierdo,BorderLayout.WEST);
        frame.add(panelSuperior,BorderLayout.NORTH);
        
        //Configuramos el frame
        pack();
        frame.setTitle("Simulador");
        frame.setSize(1000, 700);           // tamano a la ventana (ancho, alto)
        frame.setLocationRelativeTo(null);  // centrar la ventana en la pantalla 
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public Simulador_memoria(){
        super();
        contruyePanelSuperior();
        contruyePanelIzquierdo();
        contruyeVentana();
        
    }
    
    public static void main (String [] inforux){
        Simulador_memoria simulador=new Simulador_memoria();
        
    }
}
