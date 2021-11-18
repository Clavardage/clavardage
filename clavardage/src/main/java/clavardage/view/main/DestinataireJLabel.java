package clavardage.view.main;

import java.awt.Color;
import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class DestinataireJLabel extends JLabel implements ListCellRenderer {
  ImageIcon icon; 
  ImageIcon selectIcon;
  Color selectCouleur = Color.RED;
  public  DestinataireJLabel(){
     icon = new ImageIcon(getClass().getResource("img1.gif"));
     selectIcon  = new ImageIcon(getClass().getResource("img2.gif"));
  }
  public Component getListCellRendererComponent(JList list, 
       Object value, // valeur à afficher
       int index, // indice d'item
       boolean isSelected, // l'item est-il sélectionné
       boolean cellHasFocus) // La liste a-t-elle le focus
  {
     String s = value.toString();
     if (isSelected) {
        setBackground(list.getSelectionBackground());
        setForeground(selectCouleur);
        setText(s+"  "+index);
        setIcon(selectIcon);
     }else{
        setBackground(list.getBackground());
        setForeground(list.getForeground());
        setText(s);
        setIcon(icon);
     }
     setEnabled(list.isEnabled());
     setFont(list.getFont());
     setOpaque(true);
     return this;
  }
}

