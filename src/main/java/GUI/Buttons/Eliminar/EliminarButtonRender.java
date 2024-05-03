package GUI.Buttons.Eliminar;

import static interfaz.iCargaImagenes.url_imagen_borrar;
import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class EliminarButtonRender extends DefaultTableCellRenderer {

    private JButton button;

    public EliminarButtonRender() {
        button = new JButton();
        button.setName("eliminar");
        button.setText("Eliminar");
        String dirActual = System.getProperty("user.dir");
        button.setIcon(new javax.swing.ImageIcon(dirActual + url_imagen_borrar));
        
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        return button;

    }

}
