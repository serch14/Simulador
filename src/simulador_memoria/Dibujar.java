package simulador_memoria;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Dibujar extends Canvas{
    
    int tamPart, totalProc,totalM,ban=0,posy,poscy,procs[];
    private Particiones parts[];
    private String datos;
    JFrame ventana= new JFrame("Formulario");
    
    public Dibujar(){
        setBackground(Color.WHITE);
    }
    
    public void dibCad(String cad,int posx, int posy,int tam, Graphics g){
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.black);
        g2.setFont(new Font("Arial", Font.ITALIC, tam));
        g2.drawString(cad, posx, posy);
    }
    
    public boolean compara(){
        boolean mayor=false;
        int suma=0;
        for(int i=0;i<tamPart;i++){
            suma=suma+parts[i].tamano();
        }
        if(suma>totalM){
            mayor=true;
        }
        return mayor;
    }
    
    public void particionar(int x,int y){
        if(x>10){
            ban=1;
            JOptionPane.showMessageDialog(ventana,
            "Excedio el limite de particiones",
            "ERROR",JOptionPane.ERROR_MESSAGE);
        }else{
            ban=0;
            tamPart=x;
            totalM=y;
            parts=new Particiones[x];
            try{
                for(int i=0;i<x;i++){
                    datos=JOptionPane.showInputDialog("Ingrese valor a "
                    + "la particion "+(i+1));
                    parts[i]=new Particiones(Integer.parseInt(datos));
                    if(parts[i].tamano()<0){
                        i--;
                        JOptionPane.showMessageDialog(ventana,
                        "Ingrese numeros positivos",
                        "ERROR",JOptionPane.ERROR_MESSAGE);
                    }
                }
                if(compara()==false){
                    repaint();
                }else{
                    ban=1;
                    JOptionPane.showMessageDialog(ventana,
                    "Excedio el limite de memoria",
                    "ERROR",JOptionPane.ERROR_MESSAGE); 
                }
            }catch(Exception e){
                ban=1;
                JOptionPane.showMessageDialog(ventana,
                "Solo se aceptan numeros ",
                "ERROR",JOptionPane.ERROR_MESSAGE);
            };
        }
    }
    
    public void agregarProc(int x){
        totalProc=x;
        if(totalProc>10){
            ban=1;
            JOptionPane.showMessageDialog(ventana,
            "Excedio el limite de procesos",
            "ERROR",JOptionPane.ERROR_MESSAGE);
        }else{
            procs=new int[totalProc];
            try{
                for(int i=0;i<totalProc;i++){
                    datos=JOptionPane.showInputDialog("Ingrese valor al "
                    + "proceso "+(i+1));
                    procs[i]=Integer.parseInt(datos);
                    if(procs[i]<0){
                        i--;
                        JOptionPane.showMessageDialog(ventana,
                        "Ingrese numeros positivos",
                        "ERROR",JOptionPane.ERROR_MESSAGE);
                    }
                }
                ban=2;
                repaint();
            }catch(Exception e){
                ban=1;
                JOptionPane.showMessageDialog(ventana,
                "Solo se aceptan numeros ",
                "ERROR",JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    void desocupar(){
        for(int i=0;i<tamPart;i++){
            parts[i].setOcupado(false);
        }
    }
    
    void PrimerAjuste(Graphics g){
        int py,p2y,j,ban,fi;
        g.setColor(Color.RED);
        desocupar();
        for(int i=0;i<totalProc;i++){
            j=0;
            ban=0;
            py=500;
            p2y=530;
            while(j<tamPart&&ban==0){
                if(parts[j].isOcupado()==false){
                    if(procs[i]<=parts[j].tamano()){
                         fi=parts[j].tamano()-procs[i];
                         g.setColor(Color.RED);
                         g.fillRect(20, py, 120, 55);
                         g.setColor(Color.BLACK);
                         dibCad("P"+(i+1)+"= "+Integer.toString(procs[i])+"K FI="+Integer.toString(fi),20,p2y,12,g);
                         parts[j].setOcupado(true);
                         ban=1;
                    }
                }
                py-=55;
                p2y-=55;
                j++;
            }
            if(ban==0){
                JOptionPane.showMessageDialog(ventana,
                "Error en el proceso "+(i+1),
                "ERROR",JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public void dibparts(Graphics g){
        posy=500;
            poscy=550;
            g.setColor(Color.BLACK);
            for(int i=0;i<tamPart;i++){
                g.drawRect(20, posy, 120, 55);
                g.drawRect(350, posy, 120, 55);
                g.drawRect(700, posy, 120, 55);
                dibCad("PA"+(i+1)+"= "+Integer.toString(parts[i].tamano())+"K",20,poscy,12,g);
                dibCad("PA"+(i+1)+"= "+Integer.toString(parts[i].tamano())+"K",350,poscy,12,g);
                dibCad("PA"+(i+1)+"= "+Integer.toString(parts[i].tamano())+"K",700,poscy,12,g);
                posy-=55;
                poscy-=55;
            }
    }
    
    public void paint(Graphics g){
        if(ban!=1){
            dibparts(g);
            if(ban==2){
                PrimerAjuste(g);
                dibparts(g);
            }
        }
        dibCad("Primer Ajuste",20,600,20,g);
        dibCad("Mejor Ajuste",350,600,20,g);
        dibCad("Peor Ajuste",700,600,20,g);
    }
}
