package GUI.Buttons.Baja;

import interfaz.UpdateListener;
import com.mycompany.mutuales.Mutual;
import com.mycompany.mutuales.Utilidades;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;

public class BajaButtonEditor_Mutual extends DefaultCellEditor {

    private JButton button;
    private String label;
    private boolean isPushed;
    private int clickedRow;
    private int clickedColumn;
    private JTable table;
    private UpdateListener update;

    public BajaButtonEditor_Mutual() {
        super(new JCheckBox());
        button = new JButton();
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
                baja();

            }
        });
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
    }

    private void baja() {
        String descripcion = table.getValueAt(clickedRow, 0).toString();
        try {
            int id_mutual = Utilidades.obtenerIdMutual(descripcion);

            if (Utilidades.mensajeConfirmar("¿Seguro que deseas dar de baja la mutual?",
                    "Confirmar baja")) {
                // El usuario ha confirmado la operación
                Mutual m = new Mutual(id_mutual);
                m.eliminarLogicaMutualEnBaseDeDatos();
                update.updateTableData();
            }

        } catch (SQLException ex) {
            Logger.getLogger(BajaButtonEditor_Mutual.class.getName()).log(Level.SEVERE, null, ex);
        }
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

}
