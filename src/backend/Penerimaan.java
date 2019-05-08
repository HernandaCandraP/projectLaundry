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
public class Penerimaan {
    private int noorder, idcus, idjenis;
    private Customer cus = new Customer();
    private JenisCucian jenis = new JenisCucian();
    private String keterangan;
    private int berat, total;
    
    public Penerimaan(){
        
    }
    
    public Penerimaan(Customer cus, JenisCucian jenis, String keterangan, int berat, int total){
        this.cus = cus;
        this.jenis = jenis;
        this.keterangan = keterangan;
        this.berat = berat;
        this.total = total;
    }

    public int getNoorder() {
        return noorder;
    }

    public void setNoorder(int noorder) {
        this.noorder = noorder;
    }

    public int getIdcus() {
        return idcus;
    }

    public void setIdcus(int idcus) {
        this.idcus = idcus;
    }

    public int getIdjenis() {
        return idjenis;
    }

    public void setIdjenis(int idjenis) {
        this.idjenis = idjenis;
    }

    public Customer getCus() {
        return cus;
    }

    public void setCus(Customer cus) {
        this.cus = cus;
    }

    public JenisCucian getJenis() {
        return jenis;
    }

    public void setJenis(JenisCucian jenis) {
        this.jenis = jenis;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public int getBerat() {
        return berat;
    }

    public void setBerat(int berat) {
        this.berat = berat;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
        
    public Penerimaan getById(int id){
        Penerimaan pem = new Penerimaan();
        ResultSet rs = DBHelper.selectQuery("Select "
                + "p.noorder as noorder, "
                + "p.keterangan as keterangan, "
                + "p.berat as berat, "
                + "p.total AS total, "
                + "a.idcus as idcus, "
                + "b.idjenis as idjenis "
                + "from penerimaan p left join customer a on p.idcus = a.idcus "
                + "left join jeniscucian b on p.idjenis = b.idjenis where p.noorder = '" + id + "'");
        try{
            while(rs.next()){
                pem = new Penerimaan();
                pem.setNoorder(rs.getInt("noorder"));
                pem.setIdcus(rs.getInt("idcus"));
                pem.setIdjenis(rs.getInt("idjenis"));
                pem.setKeterangan(rs.getString("keterangan"));
                pem.setBerat(rs.getInt("berat"));
                pem.setTotal(rs.getInt("total"));
                pem.setCus(new Customer().getById(pem.getIdcus()));
                pem.setJenis(new JenisCucian().getById(pem.getIdjenis()));
                
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return pem;
    }
    
    public ArrayList<Penerimaan> getAll(){
        ArrayList<Penerimaan> ListPinjam = new ArrayList();
        ResultSet rs = DBHelper.selectQuery("Select "
                + "p.noorder as noorder, "
                + "p.keterangan as keterangan, "
                + "p.berat as berat, "
                + "p.total AS total, "
                + "a.idcus as idcus, "
                + "b.idjenis as idjenis "
                + "from penerimaan p left join customer a on p.idcus = a.idcus "
                + "left join jeniscucian b on p.idjenis = b.idjenis");
        try{
            while(rs.next()){
                Penerimaan pem = new Penerimaan();
                pem.setNoorder(rs.getInt("noorder"));
                pem.setIdcus(rs.getInt("idcus"));
                pem.setIdjenis(rs.getInt("idjenis"));
                pem.setKeterangan(rs.getString("keterangan"));
                pem.setBerat(rs.getInt("berat"));
                pem.setTotal(rs.getInt("total"));
                pem.setCus(new Customer().getById(pem.getIdcus()));
                pem.setJenis(new JenisCucian().getById(pem.getIdjenis()));
                
                ListPinjam.add(pem);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return ListPinjam;
    }
    
    public void save(){
        if(getById(noorder).getNoorder()== 0){
            String sql = "Insert into penerimaan (idcus, idjenis, keterangan, berat, total) "
                    + "values ("
                    + "'" + this.getCus().getIdCus()+ "', "
                    + "'" + this.getJenis().getIdJenis()+ "', "
                    + "'" + this.keterangan + "', "
                    + "" +this.berat + ", "
                    + "" +this.total + " "
                    + ")";
            this.noorder = DBHelper.insertQueryGetId(sql);
        }else{
            String sql = "Update penerimaan set "
                    + " idcus = '" + this.getCus().getIdCus()+ "', "
                    + " idjenis = '" + this.getJenis().getIdJenis()+ "', "
                    + " keterangan = '" + this.keterangan + "', "
                    + " berat = " + this.berat + ","
                    + " total = " + this.total + ""
                    + " WHERE noorder = "+this.noorder +"";
            DBHelper.executeQuery(sql);
        }
    }
        
    public void cariCustomer(int id){
        ResultSet rs = DBHelper.selectQuery("Select * from customer where idcus = '" + id + "'");
        try{
            while(rs.next()){
                getCus().setIdCus(rs.getInt("idcus"));
                getCus().setNama(rs.getString("nama"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public ArrayList<Penerimaan> search(String keyword){
        ArrayList<Penerimaan> List = new ArrayList();
        
        String sql = "SELECT * FROM penerimaan WHERE"
                + " noorder LIKE '%" + keyword + "%'"
                + " OR idjenis LIKE '%" +keyword+ "%' "
                + " OR keterangan LIKE '%" +keyword+ "%' ";
        
        ResultSet rs = DBHelper.selectQuery(sql);
        
        try{
            while(rs.next()){
                Penerimaan kat = new Penerimaan();
                kat.setNoorder(rs.getInt("noorder"));
                kat.getCus().setIdCus(rs.getInt("idcus"));
                //kat.getJenis().setJenisCucian(rs.getString("idjenis"));
                kat.setBerat(rs.getInt("berat"));
                kat.setTotal(rs.getInt("total"));
                kat.setKeterangan(rs.getString("keterangan"));
                
                List.add(kat);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return List;
    }
    
    public void delete(){
        String sql = "Delete from penerimaan where noorder = '" + this.noorder + "'";
        DBHelper.executeQuery(sql);
    }
}
