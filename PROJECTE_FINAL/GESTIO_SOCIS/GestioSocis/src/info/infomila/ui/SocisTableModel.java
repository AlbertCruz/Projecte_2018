/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.infomila.ui;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import info.infomila.appbolos.models.Soci;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 *
 * @author alber
 */
public class SocisTableModel extends AbstractTableModel{
    
    private List<Soci> socis = new ArrayList<>();
    private String[] columnes;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    public SocisTableModel(List<Soci> clients){
        super();
        this.socis = clients;
        columnes = new String[] {"DNI","Nom","Cognom1","Cognom2"};
    }

    @Override
    public int getRowCount() {        
        return socis.size();
    }

    @Override
    public int getColumnCount() {
        return columnes.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Soci soci = socis.get(rowIndex);
        switch(columnIndex){
            case 0: return soci.getNif();
            case 1: return soci.getNom();
            case 2: return soci.getCognom1();
            case 3: return soci.getCognom2();
            //case 4: return soci.getCognom2();                        
            default: return null;
        
        }
    }
    
    public String getColumnName(int col){
        return columnes[col];
    }
    
    
    
}
