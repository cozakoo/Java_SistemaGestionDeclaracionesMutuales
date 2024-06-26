package GUI.Usuario;

import GUI.Buttons.Eliminar.EliminarButtonEditor_Usuario;
import GUI.Buttons.Eliminar.EliminarButtonRender;
import GUI.Buttons.Modificar.ModificarButtonEditor_Usuario;
import GUI.Buttons.Modificar.ModificarButtonRender;
import interfaz.UpdateListener;
import com.mycompany.mutuales.DataBase;
import static interfaz.IConsultaSql.consulta_usuario_Listado;
import static interfaz.iCargaImagenes.url_imagen_agregar;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.table.DefaultTableModel;

public class FormUsuarioAdmin extends javax.swing.JFrame implements UpdateListener {

    private DefaultTableModel model;

    public FormUsuarioAdmin() {
        initComponents();
        cargarImagenes();
        cabeceraModel();
        cargarUsuarios();
        cargarBotones();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.JPanel jPanel1 = new javax.swing.JPanel();
        javax.swing.JLabel jLabel4 = new javax.swing.JLabel();
        addrUser = new javax.swing.JLabel();
        javax.swing.JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
        jTable = new javax.swing.JTable();
        javax.swing.JPanel jPanel2 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 153, 204));
        jLabel4.setText("Gestion  de Usuarios");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 40, 178, -1));

        addrUser.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        addrUser.setText("Crear Usuario");
        addrUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addrUserMouseClicked(evt);
            }
        });
        jPanel1.add(addrUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 35, 110, 30));

        jTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 680, 420));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 700, 530));

        jPanel2.setBackground(new java.awt.Color(0, 153, 204));
        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 530, 700, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void addrUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addrUserMouseClicked
        // TODO add your handling code here:
        FormCrearUsuario fUser = new FormCrearUsuario();
        fUser.addListener(this);
        fUser.setVisible(true);
    }//GEN-LAST:event_addrUserMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FormUsuarioAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormUsuarioAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormUsuarioAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormUsuarioAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormUsuarioAdmin().setVisible(true);
            }
        });
    }

    private void cabeceraModel() {
        model = new DefaultTableModel();
        model.addColumn("userName");
        model.addColumn("");
        model.addColumn("");
    }

    private void cargarUsuarios() {
        try {
            ResultSet usuarios = DataBase.getInstance(true).consulta(consulta_usuario_Listado + "WHERE es_administrador = false");
            while (usuarios.next()) {
                Object[] row = {usuarios.getObject(2)};
                model.addRow(row);
            }

            jTable.setModel(model);
        } catch (SQLException ex) {
            Logger.getLogger(FormUsuarioAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    javax.swing.JLabel addrUser;
    javax.swing.JTable jTable;
    // End of variables declaration//GEN-END:variables

    private void cargarBotones() {

        jTable.getColumnModel().getColumn(1).setCellRenderer(new ModificarButtonRender() {
        });
        jTable.getColumnModel().getColumn(2).setCellRenderer(new EliminarButtonRender());

        ModificarButtonEditor_Usuario modifBoton = new ModificarButtonEditor_Usuario();
        EliminarButtonEditor_Usuario elimBoton = new EliminarButtonEditor_Usuario();
        modifBoton.addlistener(this);
        elimBoton.addlistener(this);
        jTable.getColumnModel().getColumn(1).setCellEditor(modifBoton);
        jTable.getColumnModel().getColumn(2).setCellEditor(elimBoton);
    }

    @Override
    public void updateTableData() {
        cabeceraModel();
        cargarUsuarios();
        cargarBotones();
    }

    private void cargarImagenes() {
        String dirActual = System.getProperty("user.dir");
        addrUser.setIcon(new javax.swing.ImageIcon(dirActual + url_imagen_agregar));
        try {
            BufferedImage iconImage = ImageIO.read(new File(dirActual + "\\images\\icono.png"));

            // Establecer la imagen como ícono de la aplicación
            this.setIconImage(iconImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
