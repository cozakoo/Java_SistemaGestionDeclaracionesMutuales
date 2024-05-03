package GUI.Buttons.Modificar;

import GUI.Mutual.ModificarMutual;
import com.mycompany.mutuales.DataBase;
import com.mycompany.mutuales.Mutual;
import com.mycompany.mutuales.Utilidades;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ModificarButtonEditor_Mutual extends ModificarButtonEditor {

    public ModificarButtonEditor_Mutual() {
    }

    @Override
    public void modificar() {

        String descripcion = getTable().getValueAt(getClickedRow(), 0).toString();
        try {
            int id = Utilidades.obtenerIdMutual(descripcion);
            Mutual mutual = new Mutual(
                    id,
                    descripcion,
                    obtenerEstado(id)
            );

            new ModificarMutual(mutual, getUpdate()).setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(ModificarButtonEditor_Mutual.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private Boolean obtenerEstado(int id) {
        try {
            ResultSet consulta = DataBase.getInstance(true).consulta("SELECT activo FROM Mutual WHERE id = " + id);
            consulta.next();
            return consulta.getBoolean(1);
        } catch (SQLException ex) {
            Logger.getLogger(ModificarButtonEditor_Mutual.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

}
