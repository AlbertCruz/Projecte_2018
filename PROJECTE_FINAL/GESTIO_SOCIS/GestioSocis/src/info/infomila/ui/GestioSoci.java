/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.infomila.ui;

import info.infomila.IComponentSGBD;
import info.infomila.ComponentSGBDException;
import info.infomila.exceptions.SociException;
import info.infomila.appbolos.models.EstadisticaModalitat;
import info.infomila.appbolos.models.Modalitat;
import info.infomila.appbolos.models.Soci;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ItemEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.*;
import java.security.*;
import java.security.MessageDigest;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Iterator;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import org.apache.commons.codec.binary.Base64;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;
/**
 *
 * @author alber
 */
public class GestioSoci extends javax.swing.JFrame {

    JFileChooser selected = new JFileChooser();
    File file;
    byte[] bytesFile;
    GestioFotos managerImg = new GestioFotos();
    private JFrame parentFrame;   
    private IComponentSGBD dbConnector;
    private Soci soci;
    private Boolean pswdModificat;
    private List<Modalitat> modalitats;
    private EstadisticaModalitat estSel;
    private Boolean init = false;
    private boolean trobatEst = false;
    /**
     * Creates new form GestioSoci
     */    

    GestioSoci(JFrame parent, IComponentSGBD dbConnector) {
        initComponents();
        this.parentFrame = parent;
        this.dbConnector = dbConnector;
        initComponentsCorrectly();
    }
    
    GestioSoci(JFrame parent, IComponentSGBD dbConnector, Soci soci) {
        initComponents();
        this.parentFrame = parent;
        this.dbConnector = dbConnector;
        this.soci = soci;
        initComponentsCorrectly();
    }

   private void initComponentsCorrectly() {
        this.setVisible(true);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        getModalitats();
        if(soci!=null){
            //Modifiquem el Soci
            txtDNI.setText(soci.getNif());
            txtNom.setText(soci.getNom());
            txtCognom1.setText(soci.getCognom1());
            txtCognom2.setText(soci.getCognom2());
            try{
                txtPsswd.setText(Desencriptar(soci.getPasswordHash()));                
            }catch(Exception ex){
            }             
            Image img = null;
            if (soci.getFoto()!=null) img = BlobToImage(soci.getFoto());                        
            if (img!=null) imgFoto.setIcon(new ImageIcon(img));            
            pswdModificat = false;
            txtPsswd.setEchoChar('*');
            txtPsswd.setEnabled(false);
            /*if(soci.getEstadistiquesMod().hasNext()){
                estSel = soci.getEstadistiquesMod().next();
                cmbModalitat.setSelectedItem(estSel.getModalitat());
                txtCoeficientBase.setText(estSel.getCoeficientBase().toString());
                trobatEst = true;
            }else{
                //estSel = new EstadisticaModalitat();
                //estSel.setModalitat(modalitats.get(cmbModalitat.getSelectedIndex()));
            }*/            
        }else{
            btnModificarPswd.setVisible(false);
            txtPsswd.setEchoChar((char) 0);  
            //estSel = new EstadisticaModalitat();
            //estSel.setModalitat(modalitats.get(cmbModalitat.getSelectedIndex()));
        }
        init = true;
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
        imgFoto = new javax.swing.JLabel();
        btnAfegir = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtDNI = new javax.swing.JTextField();
        txtNom = new javax.swing.JTextField();
        txtCognom1 = new javax.swing.JTextField();
        txtCognom2 = new javax.swing.JTextField();
        btnModificarPswd = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        txtPsswd = new javax.swing.JPasswordField();
        jLabel7 = new javax.swing.JLabel();
        cmbModalitat = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        txtCoeficientBase = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jScrollPane1.setViewportView(imgFoto);

        btnAfegir.setText("Afegir");
        btnAfegir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAfegirMouseClicked(evt);
            }
        });

        btnSave.setText("Save");
        btnSave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSaveMouseClicked(evt);
            }
        });

        jLabel1.setText("DNI:");

        jLabel2.setText("Nom:");

        jLabel3.setText("Cognom 1:");

        jLabel4.setText("Cognom 2:");

        jLabel5.setText("Contrasenya:");

        btnModificarPswd.setText("Modificar");
        btnModificarPswd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnModificarPswdMouseClicked(evt);
            }
        });

        btnEliminar.setText("Eliminar");
        btnEliminar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEliminarMouseClicked(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Gestió de Socis:");

        jButton1.setText("Cancelar");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });

        jLabel7.setText("Modalitat:");

        cmbModalitat.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbModalitat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbModalitatActionPerformed(evt);
            }
        });

        jLabel8.setText("Coeficient Base: ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnAfegir, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnEliminar))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(33, 33, 33)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel1)
                                                .addComponent(jLabel2)
                                                .addComponent(jLabel3)
                                                .addComponent(jLabel4))
                                            .addGap(38, 38, 38)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(txtNom, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                                                .addComponent(txtCognom1, javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(txtCognom2, javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(txtDNI)))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel5)
                                            .addGap(25, 25, 25)
                                            .addComponent(txtPsswd)))
                                    .addGap(9, 9, 9)
                                    .addComponent(btnModificarPswd))
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel8)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(cmbModalitat, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtCoeficientBase, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton1))))
                    .addComponent(jLabel6))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtDNI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtNom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtCognom1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtCognom2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(btnModificarPswd, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPsswd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(cmbModalitat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txtCoeficientBase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAfegir)
                    .addComponent(btnSave)
                    .addComponent(btnEliminar)
                    .addComponent(jButton1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAfegirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAfegirMouseClicked
        if(selected.showDialog(null, "Find File")==JFileChooser.APPROVE_OPTION){
            file = selected.getSelectedFile();
            
            if(file.getName().endsWith("png") || file.getName().endsWith("jpg")){
                bytesFile = managerImg.openFile(file);
                imgFoto.setIcon(new ImageIcon(bytesFile));
            }else{
                JOptionPane.showMessageDialog(null, "Nomes es poden seleccionar imatges");
            }
            
        }
    }//GEN-LAST:event_btnAfegirMouseClicked

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        parentFrame.setEnabled(true);
        //parentFrame.getAccessibleContext().setAccessibleName("test");
        this.dispose();
    }//GEN-LAST:event_jButton1MouseClicked

    private void btnSaveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSaveMouseClicked
        if(soci!=null){
            updateObjeteSoci();
        }else{
            creaObjecteSoci(); 
        }
        
    }//GEN-LAST:event_btnSaveMouseClicked

    private void btnModificarPswdMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModificarPswdMouseClicked
        pswdModificat = true;
        txtPsswd.setEnabled(true);
        txtPsswd.setEchoChar((char) 0);
    }//GEN-LAST:event_btnModificarPswdMouseClicked

    private void btnEliminarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMouseClicked
        bytesFile = null;
        imgFoto.setIcon(null);       
    }//GEN-LAST:event_btnEliminarMouseClicked

    private void cmbModalitatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbModalitatActionPerformed
        int row = cmbModalitat.getSelectedIndex();
        if (row >= 0 && soci != null) {
            EstadisticaModalitat em = null;
             /*Boolean trobat = false;                
                for(Iterator<EstadisticaModalitat> iter = soci.iteEstadistiques(); iter.hasNext(); ){
                    em = iter.next();                   
                    if(em.getModalitat()!=null && em.getModalitat().getId() == modalitats.get(cmbModalitat.getSelectedIndex()).getId()){
                        txtCoeficientBase.setText(em.getCoeficientBase() + "");
                        trobat = true;
                        //trobatEst = true;
                        //estSel = est;
                    }
                }      
                if(!trobat){
                    txtCoeficientBase.setText("");
                    
                    //trobatEst = false;
                    //estSel = new EstadisticaModalitat();
                    //estSel.setModalitat(modalitats.get(cmbModalitat.getSelectedIndex()));
                    //estSel.setSoci(soci);
                }*/
            try{
               em = soci.getEstadistica(row);
               txtCoeficientBase.setText(em.getCoeficientBase() + "");
            }catch(Exception ex){
               txtCoeficientBase.setText("");
            }                        
            //tbTotalCaramboles.setText(em.getCarambolesTemporadaActual() + "");
            //tbTotalEntrades.setText(em.getEntradesTemporadaActual() + "");
        } else {
            txtCoeficientBase.setText("");
            //tbTotalCaramboles.setText("");
            //tbTotalEntrades.setText("");
        }
    }//GEN-LAST:event_cmbModalitatActionPerformed
   
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAfegir;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnModificarPswd;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox<String> cmbModalitat;
    private javax.swing.JLabel imgFoto;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtCoeficientBase;
    private javax.swing.JTextField txtCognom1;
    private javax.swing.JTextField txtCognom2;
    private javax.swing.JTextField txtDNI;
    private javax.swing.JTextField txtNom;
    private javax.swing.JPasswordField txtPsswd;
    // End of variables declaration//GEN-END:variables

    private Soci creaObjecteSoci() {
        try {
            

            Date d = new Date();
            String nif = txtDNI.getText();
            String nom = txtNom.getText();
            String cog1 = txtCognom1.getText();
            String cog2 = txtCognom2.getText();
            String paswd = Encriptar(String.valueOf(txtPsswd.getPassword()));   
            Icon icon = imgFoto.getIcon();
            Blob foto = null;
            if (icon != null) {
                foto = ImageToBlob(iconToImage(icon));
            }
            soci = new Soci(nif,nom,cog1,cog2,paswd,foto,true);
            
            /*if(!txtCoeficientBase.getText().equals("")){     
               Modalitat mod = modalitats.get(cmbModalitat.getSelectedIndex());
               EstadisticaModalitat est = new EstadisticaModalitat(soci, mod, new Double(txtCoeficientBase.getText().toString()),0,0);
               soci.addEstadistica(est);
            }*/
                       
            dbConnector.crearNouSoci(soci);
            dbConnector.commit();
            
            //soci = s;          
            deshabilitarPswd();
            JOptionPane.showMessageDialog(null, "Soci processat correctament","OK",JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            if(ex.getClass().equals(SociException.class)){
                JOptionPane.showMessageDialog(null, ex.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(null, "Problemes en enregistrar el Soci","ERROR",JOptionPane.ERROR_MESSAGE);
            }
        }
        return null;
    }
    
    private void deshabilitarPswd(){
            txtPsswd.setEchoChar('*');
            pswdModificat = false;
            txtPsswd.setEnabled(false);
            btnModificarPswd.setVisible(true);
    }
    private Soci updateObjeteSoci() {
        try {
            //Soci s = new Soci();
            
            //s.setId(soci.getId());
            soci.setNif(txtDNI.getText());
            soci.setNom(txtNom.getText());
            soci.setCognom1(txtCognom1.getText());
            soci.setCognom2(txtCognom2.getText()); 
            if(pswdModificat) {
                soci.setPasswordHash(Encriptar(String.valueOf(txtPsswd.getPassword())));
            }     
            Icon icon = imgFoto.getIcon();
            Blob foto = null;
            if (icon != null) {
                foto = ImageToBlob(iconToImage(icon));
            }
            soci.setFoto(foto);
            int row = cmbModalitat.getSelectedIndex();
            if (row >= 0 && soci != null) {
                EstadisticaModalitat em = null;
                try{
                   em = soci.getEstadistica(row);
                   em.setCoeficientBase(new Double(txtCoeficientBase.getText().toString()));
                }catch(Exception ex){

                }
            }
        
            dbConnector.modificarSoci(soci);  
            dbConnector.commit();
            deshabilitarPswd();
            JOptionPane.showMessageDialog(null, "Soci processat correctament","OK",JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            if(ex.getClass().equals(SociException.class)){
                JOptionPane.showMessageDialog(null, ex.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(null, "Problemes en enregistrar el Soci","ERROR",JOptionPane.ERROR_MESSAGE);
            }
           
        }
        return null;
    }
    
    public static String Encriptar(String texto) {
 
        String secretKey = "claveEncriptar"; //llave para encriptar datos
        String base64EncryptedString = "";
 
        try {
 
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
 
            SecretKey key = new SecretKeySpec(keyBytes, "DESede");
            Cipher cipher = Cipher.getInstance("DESede");
            cipher.init(Cipher.ENCRYPT_MODE, key);
 
            byte[] plainTextBytes = texto.getBytes("utf-8");
            byte[] buf = cipher.doFinal(plainTextBytes);
            byte[] base64Bytes = Base64.encodeBase64(buf);
            base64EncryptedString = new String(base64Bytes);
 
        } catch (Exception ex) {
        }
        return base64EncryptedString;
    }
 
    public static String Desencriptar(String textoEncriptado) throws Exception {
 
        String secretKey = "claveEncriptar"; //llave para encriptar datos
        String base64EncryptedString = "";
 
        try {
            byte[] message = Base64.decodeBase64(textoEncriptado.getBytes("utf-8"));
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
            SecretKey key = new SecretKeySpec(keyBytes, "DESede");
 
            Cipher decipher = Cipher.getInstance("DESede");
            decipher.init(Cipher.DECRYPT_MODE, key);
 
            byte[] plainText = decipher.doFinal(message);
 
            base64EncryptedString = new String(plainText, "UTF-8");
 
        } catch (Exception ex) {
        }
        return base64EncryptedString;
    }

    private void getModalitats() {
         /*try{
            modalitats = dbConnector.getLlistaModalitats();
            cmbModalitat.removeAllItems();
            for(Modalitat s:modalitats){
                cmbModalitat.addItem(s.getDescripcio());
            }
            //cmbModalitat.setModel(new DefaultComboBoxModel(modalitats.toArray()));
        }catch(Exception ex){
        
        } */
       cmbModalitat.removeAllItems();
        Iterator<EstadisticaModalitat> itEM = soci.iteEstadistiques();
        while(itEM.hasNext()) {
            cmbModalitat.addItem(itEM.next().toString());
        }
        
    }
    
    public Image FileToImage(File file)
    {
        Image dimg = null;
        
        BufferedImage img = null;
        try {
            img = ImageIO.read(file);
        } catch (IOException e) { }
        
        if (img != null) {
            dimg = img.getScaledInstance(125, 125, Image.SCALE_SMOOTH);
        }
        
        return dimg;
    }

    public Image BlobToImage(Blob blob)
    {
        Image dimg = null;
        try {
            InputStream in;

            in = blob.getBinaryStream();
            BufferedImage img = ImageIO.read(in);
            if (img != null) {
                dimg = img.getScaledInstance(125, 125, Image.SCALE_SMOOTH);
            }
        } catch (IOException | SQLException ex) { }
        
        return dimg;
    }

    public Blob ImageToBlob(Image img)
    {
        SerialBlob blob = null;

        try {
            BufferedImage o = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

            Graphics2D bGr = o.createGraphics();
            bGr.drawImage(img, 0, 0, null);
            bGr.dispose();

            ByteArrayOutputStream b = new ByteArrayOutputStream();
            ImageIO.write(o, "jpg", b);
            b.flush();
            byte[] byts = b.toByteArray();
            b.close();
            blob = new SerialBlob(byts);
        } catch (IOException | SQLException ex) { }

        return blob;
    }

    private Image iconToImage(Icon icon)
    {
        if (icon instanceof ImageIcon) {
            return ((ImageIcon) icon).getImage();
        } else {
            BufferedImage image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_RGB);
            icon.paintIcon(null, image.getGraphics(), 0, 0);
            return image;
        }
    }
}
