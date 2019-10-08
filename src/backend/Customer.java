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
public class Customer {
    private int idcus;
    private String nama, alamat, telp;
    
    public Customer(){
        
    }
    
    public Customer(String nama, String alamat, String telp){
        this.nama = nama;
        this.alamat = alamat;
        this.telp = telp;
    }
    
    public Customer getById(int id){
        Customer cus = new Customer();
        ResultSet rs = DBHelper.selectQuery("SELECT * FROM customer"
                + " WHERE idcus = '" +id+ "'");
        
        try{
            while(rs.next()){
                cus = new Customer();
                cus.setIdCus(rs.getInt("idcus"));
                cus.setNama(rs.getString("nama"));
                cus.setAlamat(rs.getString("alamat"));
                cus.setTelp(rs.getString("telp"));
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return cus;
    }
    
    public ArrayList<Customer> getAll(){
        ArrayList<Customer> ListCustomer = new ArrayList();
        String keyword = null;
        
        String sql = "SELECT * FROM customer";
        
        ResultSet rs = DBHelper.selectQuery(sql);
        
        try{
            while(rs.next()){
                Customer cus = new Customer();
                cus.setIdCus(rs.getInt("idcus"));
                cus.setNama(rs.getString("nama"));
                cus.setAlamat(rs.getString("alamat"));
                cus.setTelp(rs.getString("telp"));
                
                ListCustomer.add(cus);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return ListCustomer;
    }
    
    public ArrayList<Customer> search(String keyword){
        ArrayList<Customer> ListCustomer = new ArrayList();
        
        String sql = "SELECT * FROM customer WHERE"
                + " nama LIKE '%" + keyword + "%'"
                + " OR alamat LIKE '%" +keyword+ "%' "
                + " OR telp LIKE '%" +keyword+ "%' ";
        
        ResultSet rs = DBHelper.selectQuery(sql);
        
        try{
            while(rs.next()){
                Customer kat = new Customer();
                kat.setIdCus(rs.getInt("idcus"));
                kat.setNama(rs.getString("nama"));
                kat.setAlamat(rs.getString("alamat"));
                kat.setTelp(rs.getString("telp"));
                
                ListCustomer.add(kat);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return ListCustomer;
    }
    
    public void save(){
        if(getById(idcus).getIdCus() == 0){
            
            System.out.println("costumer baru");
            
            String SQL = "INSERT INTO customer (nama, alamat, telp) VALUES("
                    +"  '" +this.nama+ "',"
                    +"  '" +this.alamat+ "',"
                    +"  '" +this.telp+ "'"
                    +"  )";
            this.idcus = DBHelper.insertQueryGetId(SQL);
        }
        else{
            
            System.out.println("customer yang sudah ada");
            
            String SQL = "UPDATE customer SET"
                    +"  nama = '" +this.nama+ "',"
                    +"  alamat = '" +this.alamat+ "',"
                    +"  telp = '" +this.telp+ "'"
                    +"  WHERE idcus = '" +this.idcus+ "'";
            DBHelper.executeQuery(SQL);
        }
    }
    
    public void delete(){
        String SQL = "DELETE FROM customer WHERE idcus = '" +this.idcus+ "'";
        DBHelper.executeQuery(SQL);
    }
    
    public int getIdCus() {
        return idcus;
    }

    public void setIdCus(int idcus) {
        this.idcus = idcus;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
    
    public String getTelp() {
        return telp;
    }

    public void setTelp(String telp) {
        this.telp = telp;
    }    
}
