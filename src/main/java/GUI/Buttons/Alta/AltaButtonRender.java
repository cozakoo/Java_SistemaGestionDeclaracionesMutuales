package GUI.Buttons.Alta;

import static interfaz.iCargaImagenes.url_imagen_flecha_arriba;
import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class AltaButtonRender extends DefaultTableCellRenderer {

    private JButton button;

    public AltaButtonRender() {
        button = new JButton();
        button.setName("Alta");
        button.setText("Dar de Alta");
        String dirActual = System.getProperty("user.dir");
        button.setIcon(new javax.swing.ImageIcon(dirActual + url_imagen_flecha_arriba));
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        return button;

    }

}
