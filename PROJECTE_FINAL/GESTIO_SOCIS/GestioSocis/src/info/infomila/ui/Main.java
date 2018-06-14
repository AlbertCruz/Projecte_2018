/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.infomila.ui;

import info.infomila.ComponentSGBD;
import info.infomila.IComponentSGBD;
import info.infomila.ComponentSGBDException;
import info.infomila.ConectorBD;
import info.infomila.appbolos.models.Soci;
import java.awt.event.ItemEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import static javax.swing.WindowConstants.HIDE_ON_CLOSE;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;

/**
 *
 * @author alber
 */
public class Main extends javax.swing.JFrame {
    private IComponentSGBD dbConnector;
    private JFrame me;
    private List<Soci> socis;
    private List<Soci> socisFiltrats = new ArrayList<>();
    private TableModel socisModel;
    /**
     * Creates new form Main
     */
    public Main() {
        Properties p = new Properties();
        try {
            p.load(new FileInputStream("Configuracio.properties"));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage(), "Error al llegir el fitxer de propietats!", HIDE_ON_CLOSE);
            System.exit(1);
        }
        String nomClasse = p.getProperty("className");
        if (nomClasse==null || nomClasse.length()==0) {
            JOptionPane.showMessageDialog(rootPane, "Es obligatóri indicar el nom de la classe [className] al fitxer de propietats 'Configuracio.properties'!", "Falten dades al fitxer de configuració", HIDE_ON_CLOSE);
            System.exit(1);
        }
        
        try {
            String nomFitxerPropietats = p.getProperty("configHibernateFile");
            if (nomFitxerPropietats==null || nomFitxerPropietats.length()==0) {
                dbConnector = ComponentSGBD.getInstance(nomClasse);
            } else {
                dbConnector = ComponentSGBD.getInstance(nomClasse, nomFitxerPropietats);
            }
                        
        } catch (ComponentSGBDException ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage(), "Error!", HIDE_ON_CLOSE);
            try {
                if (dbConnector!=null) dbConnector.close();
            } catch (ComponentSGBDException ex1) {
                JOptionPane.showMessageDialog(rootPane, ex.getMessage(), "Error!", HIDE_ON_CLOSE);
            }
            System.exit(1);
        }        
        this.setResizable(false);                   
        me = this;
        initComponents();
        
        try{
            initComponentsCorrectly();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(this, "Problemes al connectar amb la BD.\nL'aplicació es tancarà. Posis en contacte amb sistemes.");
            System.exit(1);
        }        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jtSocis = new javax.swing.JTable();
        lblGestioSocis = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtCognom1 = new javax.swing.JTextField();
        txtDNI = new javax.swing.JTextField();
        txtNom = new javax.swing.JTextField();
        txtCognom2 = new javax.swing.JTextField();
        btnBuscarFiltre = new javax.swing.JButton();
        btnBorrarFiltre = new javax.swing.JButton();
        btnAfegirSoci = new javax.swing.JButton();
        btnModificarSoci = new javax.swing.JButton();
        btnElimiarSoci = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jtSocis.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jtSocis);

        lblGestioSocis.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lblGestioSocis.setText("Llistat de Socis: ");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel1.setText("Filtres:");

        jLabel2.setText("DNI:");

        jLabel3.setText("Nom:");

        jLabel4.setText("Cognom 1:");

        jLabel5.setText("Cognom 2:");

        txtCognom1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCognom1KeyReleased(evt);
            }
        });

        txtDNI.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDNIKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDNIKeyReleased(evt);
            }
        });

        txtNom.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNomKeyReleased(evt);
            }
        });

        txtCognom2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCognom2KeyReleased(evt);
            }
        });

        btnBuscarFiltre.setText("Buscar");
        btnBuscarFiltre.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBuscarFiltreMouseClicked(evt);
            }
        });

        btnBorrarFiltre.setText("Borrar");
        btnBorrarFiltre.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBorrarFiltreMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(txtCognom2))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2))
                                .addGap(37, 37, 37))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtDNI)
                            .addComponent(txtNom)
                            .addComponent(txtCognom1, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnBuscarFiltre)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 91, Short.MAX_VALUE)
                        .addComponent(btnBorrarFiltre)))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtDNI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(txtNom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCognom1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCognom2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBuscarFiltre)
                    .addComponent(btnBorrarFiltre))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnAfegirSoci.setText("Agregar");
        btnAfegirSoci.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAfegirSociMouseClicked(evt);
            }
        });

        btnModificarSoci.setText("Modificar");
        btnModificarSoci.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnModificarSociMouseClicked(evt);
            }
        });

        btnElimiarSoci.setText("Eliminar");
        btnElimiarSoci.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnElimiarSociMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAfegirSoci)
                        .addGap(18, 18, 18)
                        .addComponent(btnModificarSoci)
                        .addGap(18, 18, 18)
                        .addComponent(btnElimiarSoci))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 855, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblGestioSocis))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblGestioSocis)
                .addGap(10, 10, 10)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnAfegirSoci)
                        .addComponent(btnModificarSoci)
                        .addComponent(btnElimiarSoci)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarFiltreMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBuscarFiltreMouseClicked
        try{
           if(!txtDNI.getText().equals("")){  
                socisFiltrats.clear();
                //socisFiltrats.addAll(socis);
                socisFiltrats = dbConnector.getSociPerDni(txtDNI.getText());
                socis = socisFiltrats;
                socisModel = new SocisTableModel(socisFiltrats);
                jtSocis.setModel(socisModel);
            }else{                
                socisFiltrats.clear();
                //socisFiltrats.addAll(socis);
                socisFiltrats = dbConnector.getSociPerNomCognoms(txtNom.getText(), txtCognom1.getText(), txtCognom2.getText());   
                socis = socisFiltrats;
                socisModel = new SocisTableModel(socisFiltrats);
                jtSocis.setModel(socisModel);
            } 
        }catch(ComponentSGBDException ex){
            JOptionPane.showMessageDialog(null, "Impossible filtrar els clients... disculpi", "ERROR", JOptionPane.ERROR_MESSAGE);
        }                
    }//GEN-LAST:event_btnBuscarFiltreMouseClicked

    private void txtDNIKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDNIKeyPressed
        enabled_desabled((txtDNI.getText().equals("")));
    }//GEN-LAST:event_txtDNIKeyPressed

    private void btnBorrarFiltreMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBorrarFiltreMouseClicked
        //socis = dbConnector.getLlistatSocis();         
        socis = dbConnector.getLlistatSocis();
        socisFiltrats.clear();
        socisFiltrats.addAll(socis);
        socisModel = new SocisTableModel(socisFiltrats);
        jtSocis.setModel(socisModel);
        btnBuscarFiltre.setEnabled(false);
        btnElimiarSoci.setEnabled(false);
        btnModificarSoci.setEnabled(false);
        txtCognom1.setText("");
        txtCognom2.setText("");
        txtNom.setText("");
        txtDNI.setText("");   
        enabled_desabled((txtDNI.getText().equals("")));
    }//GEN-LAST:event_btnBorrarFiltreMouseClicked

    private void txtDNIKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDNIKeyReleased
        enabled_desabled((txtDNI.getText().equals("")));
        habiliarButtonFiltre();
    }//GEN-LAST:event_txtDNIKeyReleased

    private void txtNomKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomKeyReleased
        habiliarButtonFiltre();
    }//GEN-LAST:event_txtNomKeyReleased

    private void txtCognom1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCognom1KeyReleased
        habiliarButtonFiltre();
    }//GEN-LAST:event_txtCognom1KeyReleased

    private void txtCognom2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCognom2KeyReleased
        habiliarButtonFiltre();
    }//GEN-LAST:event_txtCognom2KeyReleased

    private void btnAfegirSociMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAfegirSociMouseClicked
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {                               
                new GestioSoci(me, dbConnector,null);

            }
        });
        this.setEnabled(false);
    }//GEN-LAST:event_btnAfegirSociMouseClicked

    private void btnModificarSociMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModificarSociMouseClicked
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                int row = jtSocis.getSelectedRow();
                Soci soci = socis.get(row);
                new GestioSoci(me, dbConnector,soci);
            }
        });
        this.setEnabled(false);
    }//GEN-LAST:event_btnModificarSociMouseClicked

    private void btnElimiarSociMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnElimiarSociMouseClicked
        int id;
        int row = jtSocis.getSelectedRow();
        id = socis.get(row).getId(); 
        
        int res = JOptionPane.showConfirmDialog(this, "Aquest soci s'eliminarà... Estàs segur?","CONFIRMACIÓ",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
        if(res==0){             
            if(dbConnector.borrarSoci(id) ==1){
                socis = dbConnector.getLlistatSocis(); 
                socisFiltrats.clear();
                socisFiltrats.addAll(socis);
                socisModel = new SocisTableModel(socisFiltrats);
                jtSocis.setModel(socisModel);
            }else{
                JOptionPane.showMessageDialog(this, "Aquest soci no es pot borrar... Té dependencies dins la BD.");
            }                                           
        }                
    }//GEN-LAST:event_btnElimiarSociMouseClicked

    /**
     * @param args the command line arguments
     */
    
    public void proba(){
        
    
    }
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
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

      
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });

        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAfegirSoci;
    private javax.swing.JButton btnBorrarFiltre;
    private javax.swing.JButton btnBuscarFiltre;
    private javax.swing.JButton btnElimiarSoci;
    private javax.swing.JButton btnModificarSoci;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jtSocis;
    private javax.swing.JLabel lblGestioSocis;
    private javax.swing.JTextField txtCognom1;
    private javax.swing.JTextField txtCognom2;
    private javax.swing.JTextField txtDNI;
    private javax.swing.JTextField txtNom;
    // End of variables declaration//GEN-END:variables
    private void initComponentsCorrectly() {       
        //socis = dbConnector.getLlistatSocis(); 
        this.setLocationRelativeTo(null);        
        socis = dbConnector.getLlistatSocis();
        socisFiltrats.addAll(socis);
        socisModel = new SocisTableModel(socisFiltrats);
        jtSocis.setModel(socisModel);
        btnBuscarFiltre.setEnabled(false);
        btnElimiarSoci.setEnabled(false);
        btnModificarSoci.setEnabled(false);
        jtSocis.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ListSelectionModel s = jtSocis.getSelectionModel();
        s.addListSelectionListener(new GestioTaulaSocis());
    }

    private void enabled_desabled(boolean b) {
        txtNom.setEnabled(b);
        txtCognom1.setEnabled(b);
        txtCognom2.setEnabled(b);        
    }

    private void habiliarButtonFiltre() {
        btnBuscarFiltre.setEnabled(!txtNom.getText().equals("")||!txtCognom1.getText().equals("")||!txtCognom2.getText().equals("")||!txtDNI.getText().equals(""));
    }

    class GestioTaulaSocis implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (jtSocis.getSelectedRow() != -1) {
                btnElimiarSoci.setEnabled(true);
                btnModificarSoci.setEnabled(true);
            } else {
                btnElimiarSoci.setEnabled(false);
                btnModificarSoci.setEnabled(false);
            }
        }                
    }
}
