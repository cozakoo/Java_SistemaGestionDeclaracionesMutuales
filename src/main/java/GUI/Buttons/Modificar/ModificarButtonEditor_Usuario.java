package GUI.Buttons.Modificar;

import GUI.Usuario.ModificarUsuario;

public class ModificarButtonEditor_Usuario extends ModificarButtonEditor {

    public ModificarButtonEditor_Usuario() {
    }

    @Override
    public void modificar() {
        String userName = getTable().getValueAt(getClickedRow(), 0).toString();
        new ModificarUsuario(userName, getUpdate()).setVisible(true);
    }
}
