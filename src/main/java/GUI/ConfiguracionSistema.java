package GUI;

import GUI.Complementaria.ListComplementaria;
import GUI.Usuario.FormUsuarioAdmin;
import GUI.Usuario.ModificarUsuario;
import GUI.Mutual.ListMutuales;
import com.mycompany.mutuales.Session;
import enumeradores.RolUsuario;
import interfaz.iCargaImagenes;
import static interfaz.iCargaImagenes.url_imagen_mutuales;
import static interfaz.iCargaImagenes.url_imagen_perfil_usuario;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class ConfiguracionSistema extends javax.swing.JFrame {

    /**
     * Creates new form FormPrincipal
     */
    public ConfiguracionSistema() {
        initComponents();
        cargarImagenes();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.JLabel jLabel4 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel5 = new javax.swing.JLabel();
        javax.swing.JPanel jPanel1 = new javax.swing.JPanel();
        javax.swing.JPanel jPanel2 = new javax.swing.JPanel();
        javax.swing.JLabel jLabel2 = new javax.swing.JLabel();
        gestionUjLabel = new javax.swing.JLabel();
        gestionMjLabel = new javax.swing.JLabel();
        javax.swing.JLabel jLabel11 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel12 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel3 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel1 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel13 = new javax.swing.JLabel();
        declaracionJjLabel = new javax.swing.JLabel();
        javax.swing.JLabel jLabel7 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel14 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel15 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel16 = new javax.swing.JLabel();
        javax.swing.JTabbedPane jTabbedPane1 = new javax.swing.JTabbedPane();

        jLabel4.setText("jLabel4");

        jLabel5.setText("jLabel5");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(0, 153, 204));
        jPanel1.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(new javax.swing.border.MatteBorder(null));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 153, 204));
        jLabel2.setText("Configuracion del Sistema");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 40, 290, 30));

        gestionUjLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                gestionUjLabelMouseClicked(evt);
            }
        });
        jPanel2.add(gestionUjLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 200, -1, -1));

        gestionMjLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                gestionMjLabelMouseClicked(evt);
            }
        });
        jPanel2.add(gestionMjLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 200, -1, -1));

        jLabel11.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 153, 204));
        jLabel11.setText("Gestion");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 300, -1, -1));

        jLabel12.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(102, 102, 102));
        jLabel12.setText("Seleccione su configuracion:");
        jPanel2.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 130, -1, -1));

        jLabel3.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 153, 204));
        jLabel3.setText("Mutuales ");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 320, -1, -1));

        jLabel1.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 153, 204));
        jLabel1.setText("Usuarios");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 320, -1, -1));

        jLabel13.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 153, 204));
        jLabel13.setText("Gestion");
        jPanel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 300, -1, -1));

        declaracionJjLabel.setIcon(new javax.swing.ImageIcon("C:\\Users\\dgc06\\Documents\\nuevo repo\\mutuales\\images\\declaracion.png")); // NOI18N
        declaracionJjLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                declaracionJjLabelMouseClicked(evt);
            }
        });
        jPanel2.add(declaracionJjLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 410, -1, -1));

        jLabel7.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 153, 204));
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 563, -1, -1));

        jLabel14.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 153, 204));
        jLabel14.setText(" reclamos");
        jPanel2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(408, 540, -1, -1));

        jLabel15.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 153, 204));
        jLabel15.setText("informacion");
        jPanel2.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 500, -1, -1));

        jLabel16.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 153, 204));
        jLabel16.setText("Prestamos y ");
        jPanel2.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 520, -1, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 690, 690));

        jTabbedPane1.setBackground(new java.awt.Color(0, 0, 0));
        jTabbedPane1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jTabbedPane1.setForeground(new java.awt.Color(0, 0, 0));
        jPanel1.add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 170, 600, 160));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 690, 600));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void gestionUjLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_gestionUjLabelMouseClicked
        if (Session.getInstance().getRol() == RolUsuario.administrador) {
            new FormUsuarioAdmin().setVisible(true);
        } else {
            new ModificarUsuario(Session.getInstance().getUserName()).setVisible(true);
        }
    }//GEN-LAST:event_gestionUjLabelMouseClicked

    private void gestionMjLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_gestionMjLabelMouseClicked
        try {
            new ListMutuales().setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(ConfiguracionSistema.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_gestionMjLabelMouseClicked

    private void declaracionJjLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_declaracionJjLabelMouseClicked

        try {
            new ListComplementaria().setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(ConfiguracionSistema.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_declaracionJjLabelMouseClicked

    private void cargarImagenes() {
        String dirActual = System.getProperty("user.dir");
        gestionUjLabel.setIcon(new javax.swing.ImageIcon(dirActual + url_imagen_perfil_usuario));
        gestionMjLabel.setIcon(new javax.swing.ImageIcon(dirActual + url_imagen_mutuales));
        declaracionJjLabel.setIcon(new javax.swing.ImageIcon(dirActual + iCargaImagenes.url_imagen_declaracion));
        try {
            BufferedImage iconImage = ImageIO.read(new File(dirActual + "\\images\\icono.png"));

            // Establecer la imagen como ícono de la aplicación
            this.setIconImage(iconImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    javax.swing.JLabel declaracionJjLabel;
    javax.swing.JLabel gestionMjLabel;
    javax.swing.JLabel gestionUjLabel;
    // End of variables declaration//GEN-END:variables

}
