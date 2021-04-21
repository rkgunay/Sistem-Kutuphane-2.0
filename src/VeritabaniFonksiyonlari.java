import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

public class VeritabaniFonksiyonlari {
	static Connection conn = null;
    Statement sta = null;
    static PreparedStatement pst= null;
    
    // ******** FONKSÝYONLAR KISMI BAÞLANGIÇ *********//

    
    public static boolean yoneticiGiris(String id, String password){
        String sorgu = "Select * from yonetici where yonetici_id= ? and yonetici_sifre= ?";
       
        try {
            pst = conn.prepareStatement(sorgu);
            pst.setString(1,id);
            pst.setString(2, password);
            ResultSet rs= pst.executeQuery();
            return rs.next();
            
        } catch (SQLException ex) {
            Logger.getLogger(VeritabaniFonksiyonlari.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
            return false;
        }
     
    }
    
    public static boolean ogrenciGiris(String id, String password){
        String sorgu = "Select * from ogrenci where ogrenci_kullanici_adi= ? and ogrenci_sifre= ?";
       
        try {
            pst = conn.prepareStatement(sorgu);
            pst.setString(1,id);
            pst.setString(2, password);
            ResultSet rs= pst.executeQuery();
            return rs.next();
            
        } catch (SQLException ex) {
            Logger.getLogger(VeritabaniFonksiyonlari.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
            return false;
        }
     
    }

    public static boolean personelGiris(String id, String password){
        String sorgu = "Select * from personel where personel_kullanici_adi= ? and personel_sifre= ?";
       
        try {
            pst = conn.prepareStatement(sorgu);
            pst.setString(1,id);
            pst.setString(2, password);
            ResultSet rs= pst.executeQuery();
            return rs.next();
            
        } catch (SQLException ex) {
            Logger.getLogger(VeritabaniFonksiyonlari.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
            return false;
        }
     
    }
   
    public void kutuphaneEkle(String ad, String email, String tel, String adres) {
    	String sorgu = "INSERT into kutuphane(kutuphane_ad, kutuphane_email, kutuphane_tel, kutuphane_adres) VALUES (?,?,?,?)";
    	try {
			pst = conn.prepareStatement(sorgu);
			pst.setString(1, ad);
			pst.setString(2, email);
			pst.setString(3, tel);
			pst.setString(4, adres);
			pst.executeUpdate();
		} catch (SQLException ex) {
			Logger.getLogger(VeritabaniFonksiyonlari.class.getName()).log(Level.SEVERE, null, ex);
		}
    }
    
    public void kutuphaneGuncelle(int id, String ad, String email, String tel, String adres){
        String sorgu = "UPDATE kutuphane SET  kutuphane_ad=?,kutuphane_email=?,kutuphane_tel=?, kutuphane_adres=? WHERE kutuphane_id=?";
        
        try {
            pst = conn.prepareStatement(sorgu);
            pst.setString(1, ad);
            pst.setString(2, email);
            pst.setString(3, tel);
            pst.setString(4, adres);
            pst.setInt(5, id);
            pst.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(VeritabaniFonksiyonlari.class.getName()).log(Level.SEVERE, null, ex);
        }
             
    }
    
    //kutuphaneSil fonksiyonu için
    public void sorguKutuphaneOptimizasyon(String sorgu, int kutuphaneid) {
    	try {
            pst= conn.prepareStatement(sorgu);
            pst.setInt(1,kutuphaneid);
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(VeritabaniFonksiyonlari.class.getName()).log(Level.SEVERE, null, ex);
        }
   }
   
  	public void kutuphaneSil(int id){
       String sorgu = "DELETE FROM kutuphane WHERE kutuphane_id=?";
       String sorguKitap = "DELETE FROM kitap WHERE kutuphane_id=?";
       String sorguKitapDurum = "DELETE FROM kitap_durum WHERE kutuphane_id=?";
       String sorguKitapTalep =  "DELETE FROM kitap_talep WHERE kutuphane_id=?";
       String sorguOgrenci =  "DELETE FROM ogrenci WHERE kutuphane_id=?";
       String sorguPersonel =  "DELETE FROM personel WHERE kutuphane_id=?";
     
       sorguKutuphaneOptimizasyon(sorgu, id);
       sorguKutuphaneOptimizasyon(sorguKitap, id);
       sorguKutuphaneOptimizasyon(sorguKitapDurum,id);
       sorguKutuphaneOptimizasyon(sorguKitapTalep, id);
       sorguKutuphaneOptimizasyon(sorguOgrenci,id);
       sorguKutuphaneOptimizasyon(sorguPersonel, id);
   }
    
    public void kitapEkle(String kutID, String ad, String yazar, String yayimci, String adet) {
    	String sorgu = "INSERT into kitap(kutuphane_id, kitap_ad, kitap_yazar, kitap_yayimci, kitap_adet) VALUES (?,?,?,?,?)";
    	try {
			pst = conn.prepareStatement(sorgu);
			pst.setString(1, kutID);
			pst.setString(2, ad);
			pst.setString(3, yazar);
			pst.setString(4, yayimci);
			pst.setString(5,adet);
			pst.executeUpdate();
		} catch (SQLException ex) {
			Logger.getLogger(VeritabaniFonksiyonlari.class.getName()).log(Level.SEVERE, null, ex);
		}
    }
    
    public void kitapGuncelle(int id, String kutID, String ad, String yazar, String yayimci, String adet){
        String sorgu = "UPDATE kitap SET  kutuphane_id=?,kitap_ad=?,kitap_yazar=?, kitap_yayimci=?, kitap_adet=? WHERE kitap_id=?";
        
        try {
            pst = conn.prepareStatement(sorgu);
            pst.setString(1, kutID);
            pst.setString(2, ad);
            pst.setString(3, yazar);
            pst.setString(4, yayimci);
            pst.setString(5, adet);
            pst.setInt(6, id);
            pst.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(VeritabaniFonksiyonlari.class.getName()).log(Level.SEVERE, null, ex);
        }
             
    }
    
    public void kitapSil(int id){
        String sorgu = "DELETE FROM kitap WHERE kitap_id=?";
        try {
            pst= conn.prepareStatement(sorgu);
            pst.setInt(1,id);
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(VeritabaniFonksiyonlari.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    public void kitapAdetGuncelle(int id, String adet) {
    	String sorgu = "UPDATE kitap SET kitap_adet=? WHERE kitap_id=?";
    	 try {
             pst = conn.prepareStatement(sorgu);
             pst.setString(1, adet);
             pst.setInt(2, id);
             pst.executeUpdate();
             
         } catch (SQLException ex) {
             Logger.getLogger(VeritabaniFonksiyonlari.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
    
    public void ogrenciEkle(String ad, String email, String kadi, String sifre, String kutID) {
    	String sorgu = "INSERT into ogrenci(ogrenci_ad, ogrenci_email, ogrenci_kullanici_adi, ogrenci_sifre, kutuphane_id) VALUES (?,?,?,?,?)";
    	try {
			pst = conn.prepareStatement(sorgu);
			pst.setString(1, ad);
			pst.setString(2, email);
			pst.setString(3, kadi);
			pst.setString(4, sifre);
			pst.setString(5, kutID);
			pst.executeUpdate();
		} catch (SQLException ex) {
			Logger.getLogger(VeritabaniFonksiyonlari.class.getName()).log(Level.SEVERE, null, ex);
		}
    }
    
    public void ogrenciGuncelle(int id, String ad, String email, String kadi, String sifre, String kutID){
        String sorgu = "UPDATE ogrenci SET  ogrenci_ad=?,ogrenci_email=?,ogrenci_kullanici_adi=?, ogrenci_sifre=?, kutuphane_id=? WHERE ogrenci_id=?";
        
        try {
            pst = conn.prepareStatement(sorgu);
            pst.setString(1, ad);
            pst.setString(2, email);
            pst.setString(3, kadi);
            pst.setString(4, sifre);
            pst.setString(5, kutID);
            pst.setInt(6, id);
            pst.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(VeritabaniFonksiyonlari.class.getName()).log(Level.SEVERE, null, ex);
        }
             
    }
    
    public void ogrenciBilgilerimiGuncelle(int id, String ad, String email, String kadi, String sifre){
        String sorgu = "UPDATE ogrenci SET  ogrenci_ad=?,ogrenci_email=?,ogrenci_kullanici_adi=?, ogrenci_sifre=? WHERE ogrenci_id=?";
        
        try {
            pst = conn.prepareStatement(sorgu);
            pst.setString(1, ad);
            pst.setString(2, email);
            pst.setString(3, kadi);
            pst.setString(4, sifre);
            pst.setInt(5, id);
            pst.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(VeritabaniFonksiyonlari.class.getName()).log(Level.SEVERE, null, ex);
        }
             
    }
    
    public static boolean ogrenciSifreGonder(String id, String email){
        String sorgu = "Select * from ogrenci where ogrenci_kullanici_adi= ? and ogrenci_email=?"; 
       
        try {
            pst = conn.prepareStatement(sorgu);
            pst.setString(1,id);
            pst.setString(2, email);
            ResultSet rs= pst.executeQuery();
            return rs.next();
            
        } catch (SQLException ex) {
            Logger.getLogger(VeritabaniFonksiyonlari.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
            return false;
        }
     
    }
    
    public void ogrenciSil(int id){
        String sorgu = "DELETE FROM ogrenci WHERE ogrenci_id=?";
        try {
            pst= conn.prepareStatement(sorgu);
            pst.setInt(1,id);
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(VeritabaniFonksiyonlari.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    public void personelEkle(String kutID, String ad, String kadi, String sifre) {
    	String sorgu = "INSERT into personel(kutuphane_id, personel_ad, personel_kullanici_adi, personel_sifre) VALUES (?,?,?,?)";
    	try {
			pst = conn.prepareStatement(sorgu);
			pst.setString(1, kutID);
			pst.setString(2, ad);
			pst.setString(3, kadi);
			pst.setString(4, sifre);
			pst.executeUpdate();
		} catch (SQLException ex) {
			Logger.getLogger(VeritabaniFonksiyonlari.class.getName()).log(Level.SEVERE, null, ex);
		}
    }
    
    public void personelGuncelle(int id, String kutID, String ad, String kadi, String sifre){
        String sorgu = "UPDATE personel SET  kutuphane_id=?,personel_ad=?,personel_kullanici_adi=?, personel_sifre=? WHERE personel_id=?";
        
        try {
            pst = conn.prepareStatement(sorgu);
            pst.setString(1, kutID);
            pst.setString(2, ad);
            pst.setString(3, kadi);
            pst.setString(4, sifre);
            pst.setInt(5, id);
            pst.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(VeritabaniFonksiyonlari.class.getName()).log(Level.SEVERE, null, ex);
        }
             
    }
    
    public void personelSil(int id){
        String sorgu = "DELETE FROM personel WHERE personel_id=?";
        try {
            pst= conn.prepareStatement(sorgu);
            pst.setInt(1,id);
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(VeritabaniFonksiyonlari.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    public void talepEkle(String kutID, String ogrID, String ad, String yazar, String yayimci) {
    	String sorgu = "INSERT into kitap_talep(kutuphane_id, ogrenci_id, kitap_ad, kitap_yazar, kitap_yayimci) VALUES (?,?,?,?,?)";
    	try {
			pst = conn.prepareStatement(sorgu);
			pst.setString(1, kutID);
			pst.setString(2, ogrID);
			pst.setString(3, ad);
			pst.setString(4, yazar);
			pst.setString(5,yayimci);
			pst.executeUpdate();
		} catch (SQLException ex) {
			Logger.getLogger(VeritabaniFonksiyonlari.class.getName()).log(Level.SEVERE, null, ex);
		}
    }
    
    public void talepGuncelle(int id, String kutID, String ogrID, String ad, String yazar, String yayimci){
        String sorgu = "UPDATE kitap_talep SET  kutuphane_id=?, ogrenci_id=?, kitap_ad=?, kitap_yazar=?, kitap_yayimci=? WHERE kitap_talep_id=?";
        
        try {
            pst = conn.prepareStatement(sorgu);
            pst.setString(1, kutID);
            pst.setString(2, ogrID);
            pst.setString(3, ad);
            pst.setString(4, yazar);
            pst.setString(5, yayimci);
            pst.setInt(6, id);
            pst.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(VeritabaniFonksiyonlari.class.getName()).log(Level.SEVERE, null, ex);
        }
             
    }
    
    public void talepSil(int id){
        String sorgu = "DELETE FROM kitap_talep WHERE kitap_talep_id=?";
        try {
            pst= conn.prepareStatement(sorgu);
            pst.setInt(1,id);
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(VeritabaniFonksiyonlari.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    public void oduncVer(String kutID, String kitapID, String kitapAd, String kitapYazar, String alisTarih, String teslimTarih,
    					String ogrID, String ogrAd, String ogrEmail) {
    	String sorgu = "INSERT into kitap_durum(kutuphane_id, kitap_id, kitap_ad, kitap_yazar, alis_tarihi, teslim_tarihi,"
    			+ "ogrenci_id, ogrenci_ad, ogrenci_email) VALUES (?,?,?,?,?,?,?,?,?)";
    	
    	try {
			pst = conn.prepareStatement(sorgu);
			pst.setString(1, kutID);
			pst.setString(2, kitapID);
			pst.setString(3, kitapAd);
			pst.setString(4, kitapYazar);
			pst.setString(5, alisTarih);
			pst.setString(6, teslimTarih);
			pst.setString(7, ogrID);
			pst.setString(8, ogrAd);
			pst.setString(9, ogrEmail);
			pst.executeUpdate();
		} catch (SQLException ex) {
			Logger.getLogger(VeritabaniFonksiyonlari.class.getName()).log(Level.SEVERE, null, ex);
		}
    }
    
    public void oduncGuncelle(int id, String kutID,String alisTarih, String teslimTarih,
			String ogrID, String ogrAd, String ogrEmail){
        String sorgu = "UPDATE kitap_durum SET  kutuphane_id=?,  alis_tarihi=?, teslim_tarihi=?,"
        		+ "ogrenci_id=?, ogrenci_ad=?, ogrenci_email=? WHERE kitap_durum_id=?";
        
        try {
            pst = conn.prepareStatement(sorgu);
        	pst.setString(1, kutID);
			pst.setString(2, alisTarih);
			pst.setString(3, teslimTarih);
			pst.setString(4, ogrID);
			pst.setString(5, ogrAd);
			pst.setString(6, ogrEmail);;
            pst.setInt(7, id);
            pst.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(VeritabaniFonksiyonlari.class.getName()).log(Level.SEVERE, null, ex);
        }
             
    }
    
    public void teslimAl(int id){
        String sorgu = "DELETE FROM kitap_durum WHERE kitap_durum_id=?";
        try {
            pst= conn.prepareStatement(sorgu);
            pst.setInt(1,id);
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(VeritabaniFonksiyonlari.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    // ******** FONKSÝYONLAR KISMI BÝTÝÞ *********//
    
    public VeritabaniFonksiyonlari() {
        String url = "jdbc:mysql://"+VeritabaniBaglantiBilgileri.host+":"+VeritabaniBaglantiBilgileri.port+"/"+VeritabaniBaglantiBilgileri.db_name+"?useUnicode=true&characterEncoding=UTF-8";
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url,VeritabaniBaglantiBilgileri.id,VeritabaniBaglantiBilgileri.password);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(VeritabaniFonksiyonlari.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Driver çalýþmadý :/");
        } catch (SQLException ex) {
            Logger.getLogger(VeritabaniFonksiyonlari.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Connection çalýþmadý :/");
        }
        
        
    }

    public static void main(String[] args) {
    	
}

}
