package GUI.Buttons.Alta;

import com.mycompany.mutuales.Mutual;
import com.mycompany.mutuales.Utilidades;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AltaButtonEditor_Mutual extends AltaButtonEditor {

    public AltaButtonEditor_Mutual() {
    }

    @Override
    public void alta() {
        try {
// Correcto
            if (Utilidades.mensajeConfirmar("¿Seguro que deseas dar de alta la mutual?",
                    "Confirmar alta")) {
                String descripcion = getTable().getValueAt(getClickedRow(), 0).toString();
                int id_mutual = Utilidades.obtenerIdMutual(descripcion);

                Mutual m = new Mutual(id_mutual);

                try {
                    m.activar();
                } catch (SQLException ex) {
                    Logger.getLogger(AltaButtonEditor.class.getName()).log(Level.SEVERE, null, ex);
                }
                getUpdate().updateTableData();
            }
        } catch (SQLException ex) {
            Logger.getLogger(AltaButtonEditor_Mutual.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
