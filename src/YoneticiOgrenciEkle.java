import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Toolkit;

@SuppressWarnings("serial")
public class YoneticiOgrenciEkle extends JFrame {

	private JPanel contentPane;
	private JTextField jtxtKutuphaneID;
	private JTextField jtxtOgrenciEmail;
	private JTextField jtxtOgrenciFiltre;
	private JTextField jtxtOgrenciSifre;
	private JTable ogrenciTablo;
	private JLabel jlblOgrenciKadi;
	private JTextField jtxtOgrenciKadi;
	private JButton btnEkle;
	private JButton btnGuncelle;
	private JButton btnSil;
	private JTextField jtxtKutuphaneFiltre;
	private JTable kutuphaneTablo;
	private JScrollPane scrollPaneKutuphane;
	private JTextField jtxtOgrenciAd;

	VeritabaniFonksiyonlari fonk = new VeritabaniFonksiyonlari();
	FiltrelemeFonksiyonu fil = new FiltrelemeFonksiyonu();
	
	
	/**
	 * Launch the application.
	 */
	
	public void kutuphaneListele(){
		try {
			@SuppressWarnings("static-access")
			Statement st = fonk.conn.createStatement();
			String query = "SELECT * FROM kutuphane";
			ResultSet rs = st.executeQuery(query);
			
			DefaultTableModel kuTab = (DefaultTableModel)kutuphaneTablo.getModel();
			kuTab.setRowCount(0);
			

			while (rs.next()) {
			    String o[] = {rs.getString("kutuphane_id".toString()), rs.getString("kutuphane_ad"),
			    		rs.getString("kutuphane_tel")};
			    kuTab.addRow(o);
			   
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
	}
	
	public void ogrenciListele(String kutuphaneID){
		try {
			@SuppressWarnings("static-access")
			Statement st = fonk.conn.createStatement();
			String query = "SELECT * FROM ogrenci WHERE kutuphane_id= '"+kutuphaneID+"'";
			ResultSet rs = st.executeQuery(query);
			
			DefaultTableModel ogTab = (DefaultTableModel)ogrenciTablo.getModel();
			ogTab.setRowCount(0);
			

			while (rs.next()) {
			    String o[] = {rs.getString("ogrenci_id".toString()), rs.getString("kutuphane_id"), rs.getString("ogrenci_ad"), 
			    		rs.getString("ogrenci_email"), rs.getString("ogrenci_kullanici_adi"), rs.getString("ogrenci_sifre")};
			    ogTab.addRow(o);
			   
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
	}
	
	//Güncelleme butonunda minik bir sorun var. 2 tane ayný kullanýcý adýna izin veriyor. Düzeltmek için veritabanýndan ogrenci_id'sini de alýp
	//eþleþme bulunan kullanýcý adýnýn öðrenci tablosundaki seçilen satýrdaki öðrenci_id'sine ait olup olmadýðý kontrol edilmesi gerekiyor.
	//Uzun iþ. Daha kolay bir çözüm bulana kadar erteliyeceðim.
	public int ogrenciKadiKontrol(String kadi){
        int label = 0;
        String sorgu = "SELECT COUNT(*) FROM ogrenci WHERE ogrenci_kullanici_adi='"+kadi+"'";
        try {
        	@SuppressWarnings("static-access")
			PreparedStatement pst = fonk.conn.prepareStatement(sorgu);
			ResultSet rs = pst.executeQuery(sorgu);
            rs.next();
            label = rs.getInt(1);
        } catch (SQLException ex) {
            return 0;
        }
        return label;
    }
	
	public void jtxtTemizle() {
		jtxtOgrenciAd.setText("");
		jtxtOgrenciEmail.setText("");
		jtxtOgrenciKadi.setText("");
		jtxtOgrenciSifre.setText("");
	}
	
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					YoneticiOgrenciEkle frame = new YoneticiOgrenciEkle();
					frame.setVisible(true);
					frame.kutuphaneListele();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public YoneticiOgrenciEkle() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(YoneticiOgrenciEkle.class.getResource("/logo.png")));
		setTitle("Y\u00F6netici - \u00D6\u011Frenci Ekle");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 10, 566, 343);
		contentPane.add(panel);
		panel.setLayout(null);
		
		jtxtKutuphaneID = new JTextField();
		jtxtKutuphaneID.setEnabled(false);
		jtxtKutuphaneID.setColumns(10);
		jtxtKutuphaneID.setBounds(230, 10, 94, 19);
		panel.add(jtxtKutuphaneID);
		
		JLabel jlblOgrenciAd = new JLabel("\u00D6\u011Frenci Ad\u0131:");
		jlblOgrenciAd.setHorizontalAlignment(SwingConstants.TRAILING);
		jlblOgrenciAd.setBounds(10, 10, 82, 19);
		panel.add(jlblOgrenciAd);
		
		jtxtOgrenciAd = new JTextField();
		jtxtOgrenciAd.setColumns(10);
		jtxtOgrenciAd.setBounds(102, 10, 108, 19);
		panel.add(jtxtOgrenciAd);
		
		jtxtOgrenciEmail = new JTextField();
		jtxtOgrenciEmail.setColumns(10);
		jtxtOgrenciEmail.setBounds(102, 36, 108, 19);
		panel.add(jtxtOgrenciEmail);
		
		JLabel jllblOgrenciEmail = new JLabel("\u00D6\u011Frenci Emaili:");
		jllblOgrenciEmail.setHorizontalAlignment(SwingConstants.TRAILING);
		jllblOgrenciEmail.setBounds(10, 36, 82, 19);
		panel.add(jllblOgrenciEmail);
		
		JScrollPane scrollPaneOgrenci = new JScrollPane();
		scrollPaneOgrenci.setBounds(10, 202, 546, 131);
		panel.add(scrollPaneOgrenci);
		
		ogrenciTablo = new JTable();
		ogrenciTablo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int secilenSatir= ogrenciTablo.getSelectedRow();
				DefaultTableModel ogTab = (DefaultTableModel)ogrenciTablo.getModel();
				jtxtKutuphaneID.setText(ogTab.getValueAt(secilenSatir, 1).toString());
				jtxtOgrenciAd.setText(ogTab.getValueAt(secilenSatir, 2).toString());
				jtxtOgrenciEmail.setText(ogTab.getValueAt(secilenSatir, 3).toString());
				jtxtOgrenciKadi.setText(ogTab.getValueAt(secilenSatir, 4).toString());
				jtxtOgrenciSifre.setText(ogTab.getValueAt(secilenSatir, 5).toString());
			}
		});
		scrollPaneOgrenci.setViewportView(ogrenciTablo);
		
		ogrenciTablo.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"\u00D6\u011Frenci ID", "K\u00FCt\u00FCphane ID", "\u00D6\u011Frenci Ad\u0131", "\u00D6\u011Frenci Emaili", "Kullan\u0131c\u0131 Ad\u0131", "\u015Eifre"
			}
		));
		
		jtxtOgrenciFiltre = new JTextField();
		jtxtOgrenciFiltre.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				fil.filtre(jtxtOgrenciFiltre.getText(), ogrenciTablo);
			}
		});
		jtxtOgrenciFiltre.setColumns(10);
		jtxtOgrenciFiltre.setBounds(230, 173, 326, 19);
		panel.add(jtxtOgrenciFiltre);
		
		JLabel jlblOgrenciSifre = new JLabel("\u015Eifre:");
		jlblOgrenciSifre.setHorizontalAlignment(SwingConstants.TRAILING);
		jlblOgrenciSifre.setBounds(10, 91, 82, 19);
		panel.add(jlblOgrenciSifre);
		
		jtxtOgrenciSifre = new JTextField();
		jtxtOgrenciSifre.setColumns(10);
		jtxtOgrenciSifre.setBounds(102, 91, 108, 19);
		panel.add(jtxtOgrenciSifre);
		
		jlblOgrenciKadi = new JLabel("Kullan\u0131c\u0131 Ad\u0131:");
		jlblOgrenciKadi.setHorizontalAlignment(SwingConstants.TRAILING);
		jlblOgrenciKadi.setBounds(10, 62, 82, 19);
		panel.add(jlblOgrenciKadi);
		
		jtxtOgrenciKadi = new JTextField();
		jtxtOgrenciKadi.setColumns(10);
		jtxtOgrenciKadi.setBounds(102, 62, 108, 19);
		panel.add(jtxtOgrenciKadi);
		
		btnEkle = new JButton("Ekle");
		btnEkle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int secilenSatir= kutuphaneTablo.getSelectedRow();
				if(kutuphaneTablo.isRowSelected(secilenSatir)) {
					String ad = jtxtOgrenciAd.getText();
					String email = jtxtOgrenciEmail.getText();
					String kadi = jtxtOgrenciKadi.getText();
					String sifre = jtxtOgrenciSifre.getText();
					String kutID = jtxtKutuphaneID.getText();
					if((ad.equals("")||email.equals("")||kadi.equals("")||sifre.equals(""))) {
						JOptionPane.showMessageDialog(null, "Tüm Alanlarý Doldurunuz.", "SÝSTEM MESAJI", JOptionPane.OK_OPTION);
					}else {
						if(ogrenciKadiKontrol(kadi)<=0) {
							fonk.ogrenciEkle(ad, email, kadi, sifre, kutID);
							ogrenciListele(kutID);
							jtxtTemizle();													
						}else {
							jtxtOgrenciKadi.setText("");
							JOptionPane.showMessageDialog(null, "Kullanýcý Adý Uygun Deðil.", "SÝSTEM MESAJI", JOptionPane.OK_OPTION);
						}
					}
				}else {
					JOptionPane.showMessageDialog(null, "Öðrenciyi Ekleyeceðiniz Kütüphaneyi Seçiniz.", "SÝSTEM MESAJI", JOptionPane.OK_OPTION);
				}
			}
		});
		btnEkle.setBounds(32, 141, 82, 21);
		panel.add(btnEkle);
		
		btnGuncelle = new JButton("G\u00FCncelle");
		btnGuncelle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String kutID = jtxtKutuphaneID.getText();
				String ad = jtxtOgrenciAd.getText();
				String email = jtxtOgrenciEmail.getText();
				String kadi = jtxtOgrenciKadi.getText();
				String sifre = jtxtOgrenciSifre.getText();
				int secilenSatir= ogrenciTablo.getSelectedRow();
				DefaultTableModel ogTab = (DefaultTableModel)ogrenciTablo.getModel();
				if(secilenSatir==-1){
			            if(ogTab.getRowCount()==0){
			            	JOptionPane.showMessageDialog(null, "Liste boþ!", "SÝSTEM MESAJI", JOptionPane.OK_OPTION);
			            }else { JOptionPane.showMessageDialog(null, "Seçim yapýnýz!", "SÝSTEM MESAJI", JOptionPane.OK_OPTION);}
			        }else {	
			        	if(ogrenciKadiKontrol(kadi)<=1) {
			        		int id = Integer.parseInt(ogTab.getValueAt(secilenSatir, 0).toString());
			        		fonk.ogrenciGuncelle(id, ad, email, kadi, sifre, kutID);
			        		JOptionPane.showMessageDialog(null, "Güncelleme Baþarýlý", "SÝSTEM MESAJI", JOptionPane.OK_OPTION);
			        		ogrenciListele(kutID);
			        		jtxtTemizle();			        		
			        	}else {
			        		jtxtOgrenciKadi.setText("");
			        		JOptionPane.showMessageDialog(null, "Kullanýcý Adý Uygun Deðil.", "SÝSTEM MESAJI", JOptionPane.OK_OPTION);
			        	}
						
			        	
			        }
			}
		});
		btnGuncelle.setBounds(125, 141, 85, 21);
		panel.add(btnGuncelle);
		
		btnSil = new JButton("Sil");
		btnSil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int secilenSatir= ogrenciTablo.getSelectedRow();
				DefaultTableModel ogTab = (DefaultTableModel)ogrenciTablo.getModel();
				
				if(secilenSatir==-1){
		            if(ogTab.getRowCount()==0){
		            	JOptionPane.showMessageDialog(null, "Liste boþ!", "SÝSTEM MESAJI", JOptionPane.OK_OPTION);
		            }else { JOptionPane.showMessageDialog(null, "Seçim yapýnýz!", "SÝSTEM MESAJI", JOptionPane.OK_OPTION);}
		        }else {
		        	int id = Integer.parseInt(ogTab.getValueAt(secilenSatir, 0).toString());
		        	String kutID = ogTab.getValueAt(secilenSatir, 1).toString();
		        	fonk.ogrenciSil(id);
		        	ogrenciListele(kutID);
		        }
			}
		});
		btnSil.setBounds(125, 172, 85, 21);
		panel.add(btnSil);
		
		jtxtKutuphaneFiltre = new JTextField();
		jtxtKutuphaneFiltre.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				fil.filtre(jtxtKutuphaneFiltre.getText(), kutuphaneTablo);
			}
		});
		jtxtKutuphaneFiltre.setColumns(10);
		jtxtKutuphaneFiltre.setBounds(334, 10, 222, 19);
		panel.add(jtxtKutuphaneFiltre);
		
		scrollPaneKutuphane = new JScrollPane();
		scrollPaneKutuphane.setBounds(230, 39, 326, 124);
		panel.add(scrollPaneKutuphane);
		
		kutuphaneTablo = new JTable();
		kutuphaneTablo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int secilenSatir = kutuphaneTablo.getSelectedRow();
				DefaultTableModel kuTab = (DefaultTableModel)kutuphaneTablo.getModel();
				String kutID = kuTab.getValueAt(secilenSatir, 0).toString();
				jtxtKutuphaneID.setText(kutID);
				ogrenciListele(kutID);
			}
		});
		kutuphaneTablo.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"K\u00FCt\u00FCphane ID", "K\u00FCt\u00FCphane Ad\u0131", "K\u00FCt\u00FCphane Tel"
			}
		));
		scrollPaneKutuphane.setViewportView(kutuphaneTablo);
	}
}
