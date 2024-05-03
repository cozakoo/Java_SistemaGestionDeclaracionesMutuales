package GUI.Buttons.Eliminar;

import interfaz.UpdateListener;
import com.mycompany.mutuales.DataBase;
import com.mycompany.mutuales.Utilidades;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;

public class EliminarButtonEditor_Usuario extends DefaultCellEditor {

    private JButton button;
    private String label;
    private boolean isPushed;
    private int clickedRow;
    private int clickedColumn;
    private JTable table;
    private UpdateListener update;

    public EliminarButtonEditor_Usuario() {
        super(new JCheckBox());
        button = new JButton();
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
                eliminar();

            }
        });
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
    }

    private void eliminar() {
        String userName = table.getValueAt(clickedRow, 0).toString();

        if (Utilidades.mensajeConfirmar("¿Seguro de que quiere eliminar al usuario " + userName + "?", "Confirmar Baja")) {
            eliminarEnbase(userName);
        }
        update.updateTableData();

    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.table = table;
        label = (String) value;
        button.setText(label);
        isPushed = true;
        clickedRow = row;
        clickedColumn = column;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        isPushed = false;
        return label;
    }

    @Override
    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }

    @Override
    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }

    public void addlistener(UpdateListener listener) {
        this.update = listener;
    }

    private void eliminarEnbase(String userName) {
        DataBase.getInstance(true).consulta("DELETE FROM usuario WHERE username = '" + userName + "'");
    }
}
