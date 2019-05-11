/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;
import java.util.*;
import java.sql.*;
/**
 *
 * @author fakhryan
 */
public class pengembalian {
    private int idtransaksi, noorder;
    private Penerimaan penerimaan = new Penerimaan();
    private Customer cus = new Customer();
    private String tglterima;
    private String tglkembali, nama;
    private int bayar, kembalian;
    
    public pengembalian(){
        
    }
    
    public pengembalian(Penerimaan penerimaan, String tglterima, String tglkembali, int bayar, int kembalian){
        this.penerimaan = penerimaan;
        this.tglterima = tglterima;
        this.tglkembali = tglkembali;
        this.bayar = bayar;
        this.kembalian = kembalian;
    }

    public int getIdtransaksi() {
        return idtransaksi;
    }

    public void setIdtransaksi(int idtransaksi) {
        this.idtransaksi = idtransaksi;
    }

    public int getNoorder() {
        return noorder;
    }

    public void setNoorder(int noorder) {
        this.noorder = noorder;
    }

    public Penerimaan getPenerimaan() {
        return penerimaan;
    }

    public void setPenerimaan(Penerimaan penerimaan) {
        this.penerimaan = penerimaan;
    }

    public String getTglterima() {
        return tglterima;
    }

    public void setTglterima(String tglterima) {
        this.tglterima = tglterima;
    }

    public String getTglkembali() {
        return tglkembali;
    }

    public void setTglkembali(String tglkembali) {
        this.tglkembali = tglkembali;
    }

    public int getBayar() {
        return bayar;
    }

    public void setBayar(int bayar) {
        this.bayar = bayar;
    }

    public int getKembalian() {
        return kembalian;
    }

    public void setKembalian(int kembalian) {
        this.kembalian = kembalian;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Customer getCus() {
        return cus;
    }

    public void setCus(Customer cus) {
        this.cus = cus;
    }
    
    

    public pengembalian getById(int id){
        pengembalian pem = new pengembalian();
        ResultSet rs = DBHelper.selectQuery("Select "
                + "p.idtransaksi as idtransaksi, "
                + "p.tglterima as tglterima, "
                + "p.tglkembali as tglkembali, "
                + "p.bayar as bayar, "
                + "p.kembalian AS kembalian, "
                + "a.noorder as noorder "
                + "from pengembalian p left join penerimaan a on p.noorder = a.noorder "
                + "where p.idtransaksi = '" + id + "'");
        try{
            while(rs.next()){
                pem = new pengembalian();
                pem.setIdtransaksi(rs.getInt("idtransaksi"));
                pem.setNoorder(rs.getInt("noorder"));
                pem.setTglterima(rs.getString("tglterima"));
                pem.setTglkembali(rs.getString("tglkembali"));
                pem.setBayar(rs.getInt("bayar"));
                pem.setKembalian(rs.getInt("kembalian"));
                pem.setPenerimaan(new Penerimaan().getById(pem.getNoorder()));
                
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return pem;
    }
    
    public ArrayList<pengembalian> getAll(){
        ArrayList<pengembalian> ListPengembalian = new ArrayList();
        ResultSet rs = DBHelper.selectQuery("Select "
                + "p.idtransaksi as idtransaksi, "
                + "p.tglterima as tglterima, "
                + "p.tglkembali as tglkembali, "
                + "p.bayar as bayar, "
                + "p.kembalian AS kembalian, "
                + "a.noorder as noorder "
                + "from pengembalian p left join penerimaan a on p.noorder = a.noorder "
                + " ");
        try{
            while(rs.next()){
                pengembalian pem = new pengembalian();
                pem.setIdtransaksi(rs.getInt("idtransaksi"));
                pem.setNoorder(rs.getInt("noorder"));
                pem.setTglterima(rs.getString("tglterima"));
                pem.setTglkembali(rs.getString("tglkembali"));
                pem.setBayar(rs.getInt("bayar"));
                pem.setKembalian(rs.getInt("kembalian"));
                pem.setPenerimaan(new Penerimaan().getById(pem.getNoorder()));
                
                ListPengembalian.add(pem);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return ListPengembalian;
    }
    
    public void save(){
        if(getById(idtransaksi).getIdtransaksi()== 0){
            String sql = "Insert into pengembalian (noorder, tglterima, tglkembali, bayar, kembalian) "
                    + "values ("
                    + "'" + this.getNoorder()+ "', "
                    + "'" + this.tglterima + "', "
                    + "'" +this.tglkembali + "', "
                    + "" +this.bayar + ", "
                    + "" +this.kembalian + " "
                    + ")";
            this.idtransaksi = DBHelper.insertQueryGetId(sql);
        }else{
            int a =this.getPenerimaan().getNoorder();
            String sql = "Update pengembalian set "
                    + " noorder = '" + this.noorder+ "', "
                    + " tglterima = '" + this.tglterima + "', "
                    + " tglkembali = '" + this.tglkembali + "',"
                    + " bayar = " + this.bayar + ", "
                    + " kembalian = " + this.kembalian + ""
                    + " WHERE idtransaksi = "+this.idtransaksi+""; 
            DBHelper.executeQuery(sql);
        }
    }
    
    public void cariPenerimaan(int id){
        ResultSet rs = DBHelper.selectQuery("SELECT nama FROM customer "
                + "WHERE idcus = ANY (SELECT idcus FROM penerimaan WHERE noorder = "+id+")");
//        ResultSet rs = DBHelper.selectQuery("Select * from penerimaan where noorder = '" + id + "'");
        try{
            while(rs.next()){
                getCus().setNama(rs.getString("nama"));
//                getPenerimaan().setNoorder(rs.getInt("noorder"));
//                getPenerimaan().setKeterangan(rs.getString("keterangan"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void cariHarga(int id){
        ResultSet rs = DBHelper.selectQuery("Select total from penerimaan where noorder = '" + id + "'");
        try{
            while(rs.next()){
                getPenerimaan().setTotal(rs.getInt("total"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void delete(){
        String sql = "Delete from pengembalian where idtransaksi = '" + this.idtransaksi + "'";
        DBHelper.executeQuery(sql);
    }
}
