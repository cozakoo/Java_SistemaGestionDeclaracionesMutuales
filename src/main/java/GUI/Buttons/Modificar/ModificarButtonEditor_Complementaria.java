package GUI.Buttons.Modificar;

import GUI.Complementaria.ModificarComplementaria;
import com.mycompany.mutuales.InfoComplementaria;
import com.mycompany.mutuales.Utilidades;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ModificarButtonEditor_Complementaria extends ModificarButtonEditor {

    public ModificarButtonEditor_Complementaria() {

    }

    @Override
    public void modificar() {

        String nombreMutual = getTable().getValueAt(getClickedRow(), 0).toString();
        String tipo = getTable().getValueAt(getClickedRow(), 1).toString();
        int concepto = Integer.parseInt(getTable().getValueAt(getClickedRow(), 4).toString());

        try {
            int id = Utilidades.obtenerIdComplementaria(nombreMutual, tipo, concepto);

            InfoComplementaria complementaria = new InfoComplementaria(id);
            complementaria.completarInformacion();
            new ModificarComplementaria(complementaria, getUpdate()).setVisible(true);

        } catch (SQLException ex) {
            Logger.getLogger(ModificarButtonEditor_Complementaria.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
