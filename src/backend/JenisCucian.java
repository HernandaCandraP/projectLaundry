/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

import java.util.ArrayList;
import java.sql.*;
/**
 *
 * @author ASUS
 */
public class JenisCucian {
    private int idjenis;
    private String jeniscucian;
    private int harga;
    
    public JenisCucian(){
        
    }
    
    public JenisCucian(String jeniscucian, int harga){
        this.jeniscucian = jeniscucian;
        this.harga = harga;
    }
    
    public String toString(){
        return jeniscucian;
    }
    
    public JenisCucian getById(int id){
        JenisCucian kat = new JenisCucian();
        ResultSet rs = DBHelper.selectQuery("SELECT * FROM jeniscucian"
                + " WHERE idjenis = '" +id+ "'");
        
        try{
            while(rs.next()){
                kat = new JenisCucian();
                kat.setIdJenis(rs.getInt("idjenis"));
                kat.setJenisCucian(rs.getString("jeniscucian"));
                kat.setHarga(rs.getInt("harga"));
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return kat;
    }
    
        public JenisCucian getByNama(String jenis){
        JenisCucian kat = new JenisCucian();
        ResultSet rs = DBHelper.selectQuery("SELECT * FROM jeniscucian"
                + " WHERE jeniscucian = '" +jenis+ "'");
        
        try{
            while(rs.next()){
                kat = new JenisCucian();
                kat.setIdJenis(rs.getInt("idjenis"));
                kat.setJenisCucian(rs.getString("jeniscucian"));
                kat.setHarga(rs.getInt("harga"));
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return kat;
    }
    
    public ArrayList<JenisCucian> getAll(){
        ArrayList<JenisCucian> ListJenis = new ArrayList();
        String keyword = null;
        
        String sql = "SELECT * FROM jeniscucian";
        
        ResultSet rs = DBHelper.selectQuery(sql);
        
        try{
            while(rs.next()){
                JenisCucian kat = new JenisCucian();
                kat.setIdJenis(rs.getInt("idjenis"));
                kat.setJenisCucian(rs.getString("jeniscucian"));
                kat.setHarga(rs.getInt("harga"));
                
                ListJenis.add(kat);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return ListJenis;
    }
    
        public ArrayList<JenisCucian> search(String keyword){
        ArrayList<JenisCucian> ListJenis = new ArrayList();
        
        String sql = "SELECT * FROM jeniscucian WHERE"
                + " jeniscucian LIKE '%" + keyword + "%'"
                + " OR harga LIKE '%" + keyword + "%'";
        
        ResultSet rs = DBHelper.selectQuery(sql);
            System.out.println(sql);
        
        try{
            while(rs.next()){
                JenisCucian kat = new JenisCucian();
                kat.setIdJenis(rs.getInt("idjenis"));
                kat.setJenisCucian(rs.getString("jeniscucian"));
                kat.setHarga(rs.getInt("harga"));
                
                ListJenis.add(kat);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return ListJenis;
    }
    
    public void save(){
        if(getById(idjenis).getIdJenis()== 0){
            String SQL = "INSERT INTO jeniscucian (jeniscucian, harga) VALUES("
                    +"  '" +this.jeniscucian+ "',"
                    +"  '" +this.harga+ "'"
                    +"  )";
            this.idjenis = DBHelper.insertQueryGetId(SQL);
        }
        else{
            String SQL = "UPDATE jeniscucian SET"
                    +"  jeniscucian = '" +this.jeniscucian+ "',"
                    +"  harga = " +this.harga+ " "
                    +"  WHERE idjenis = '" +this.idjenis+ "'";
            DBHelper.executeQuery(SQL);
            System.out.println(SQL);
        }
    }
    
    public void delete(){
        String SQL = "DELETE FROM jeniscucian WHERE idjenis = '" +this.idjenis+ "'";
        DBHelper.executeQuery(SQL);
    }

    public int getIdJenis() {
        return idjenis;
    }

    public void setIdJenis(int idjenis) {
        this.idjenis = idjenis;
    }

    public String getJenisCucian() {
        return jeniscucian;
    }

    public void setJenisCucian(String jeniscucian) {
        this.jeniscucian = jeniscucian;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }
}
