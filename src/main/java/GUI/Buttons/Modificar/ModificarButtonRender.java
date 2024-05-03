package GUI.Buttons.Modificar;

import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class ModificarButtonRender extends DefaultTableCellRenderer {

    private JButton button;

    public ModificarButtonRender() {
        button = new JButton();
        button.setName("Modificar");
        button.setText("Modificar");
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        return button;
    }
}
