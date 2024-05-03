package GUI.Buttons.Alta;

import interfaz.UpdateListener;

import javax.swing.*;
import javax.swing.JTable;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class AltaButtonEditor extends DefaultCellEditor {

    private JButton button;
    private String label;
    private boolean isPushed;
    private int clickedRow;
    private int clickedColumn;
    private JTable table;
    private UpdateListener update;

    public AltaButtonEditor() {
        super(new JCheckBox());
        button = new JButton();
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
                alta();
               
            }
        });
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
    }

    public abstract void alta();
    

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

    public JTable getTable() {
        return table;
    }

    public int getClickedRow() {
        return clickedRow;
    }

    public UpdateListener getUpdate() {
        return update;
    }
}
