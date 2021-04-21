import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Toolkit;

@SuppressWarnings("serial")
public class YoneticiPanel extends JFrame {

	private JPanel contentPane;
	private JTextField jtxtKutuphaneID;
	private JTextField jtxtKitapID;
	private JTextField jtxtKitapAd;
	private JTextField jtxtKitapYazar;
	private JTextField jtxtOgrenciID;
	private JTextField jtxtOgrenciAd;
	private JTextField jtxtOgrenciEmail;
	private JTable kutuphaneTablo;
	private JTable kitapTablo;
	private JTable ogrenciTablo;
	private JTable islemTablo;
	private JTextField jtxtOgrenciFiltre;
	private JTextField jtxtKitapFiltre;
	private JTextField jtxtKutuphaneFiltre;
	private JTextField jtxtIslemFiltre;
	private JDateChooser dateAlis;
	private JDateChooser dateTeslim;
	
	VeritabaniFonksiyonlari fonk = new VeritabaniFonksiyonlari();
	FiltrelemeFonksiyonu fil = new FiltrelemeFonksiyonu();
	
	/**
	 * Launch the application.
	 * @param adet 
	 */
	
	//kitap teslim alma iþleminde kullanýlan fonksiyon. 
	public String tablodanAdetGetir(DefaultTableModel kiTab, String kitapID, String adet) {
		   for (int i = kiTab.getRowCount() - 1; i >= 0; --i) {	   
	            if (kiTab.getValueAt(i, 0).equals(kitapID)) {
	              int adetSayi = Integer.parseInt(kiTab.getValueAt(i, 3).toString())+1;
	              adet = String.valueOf(adetSayi);
	            } 
	    } return adet;
	}
	
	private String tarihKontrol(JDateChooser date){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String tarih = sdf.format(((JDateChooser) date).getDate()).toString();
			return tarih;
		} catch (Exception e) {
			return "1";
		}
	}



	
	public void kutuphaneListele(){
		try {
			@SuppressWarnings("static-access")
			Statement st = fonk.conn.createStatement();
			String query = "SELECT * FROM kutuphane";
			ResultSet rs = st.executeQuery(query);
			
			DefaultTableModel kuTab = (DefaultTableModel)kutuphaneTablo.getModel();
			kuTab.setRowCount(0);
			

			while (rs.next()) {
			    String o[] = {rs.getString("kutuphane_id".toString()), rs.getString("kutuphane_ad")};
			    kuTab.addRow(o);
			   
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
	}
	
	public void oduncListele(String kutuphaneID){
		try {
			@SuppressWarnings("static-access")
			Statement st = fonk.conn.createStatement();
			String query = "SELECT * FROM kitap_durum WHERE kutuphane_id= '"+kutuphaneID+"'";
			ResultSet rs = st.executeQuery(query);
			
			DefaultTableModel isTab = (DefaultTableModel)islemTablo.getModel();
			isTab.setRowCount(0);
			

			while (rs.next()) {
			    String o[] = {rs.getString("kitap_durum_id"),rs.getString("kutuphane_id"),rs.getString("kitap_id".toString()), 
			    		rs.getString("kitap_ad"), rs.getString("kitap_yazar"), rs.getString("alis_tarihi"), rs.getString("teslim_tarihi"),
			    		rs.getString("ogrenci_id"), rs.getString("ogrenci_ad"), rs.getString("ogrenci_email")};
			    isTab.addRow(o);
			   
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
	}
	
	public void kitapListele(String kutuphaneID){
		try {
			@SuppressWarnings("static-access")
			Statement st = fonk.conn.createStatement();
			String query = "SELECT * FROM kitap WHERE kutuphane_id= '"+kutuphaneID+"'";
			ResultSet rs = st.executeQuery(query);
			
			DefaultTableModel kiTab = (DefaultTableModel)kitapTablo.getModel();
			kiTab.setRowCount(0);
			

			while (rs.next()) {
			    String o[] = {rs.getString("kitap_id".toString()), rs.getString("kitap_ad"), 
			    		rs.getString("kitap_yazar"), rs.getString("kitap_adet")};
			    kiTab.addRow(o);
			   
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
			    String o[] = {rs.getString("ogrenci_id".toString()), rs.getString("ogrenci_ad"), 
			    		rs.getString("ogrenci_email"), rs.getString("ogrenci_kullanici_adi"), rs.getString("ogrenci_sifre")};
			    ogTab.addRow(o);
			   
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
	}
	
		
	
	public void jtxtTemizle() {
		jtxtKitapID.setText("");
		jtxtKitapAd.setText("");
		jtxtKitapYazar.setText("");
		jtxtOgrenciID.setText("");
		jtxtOgrenciAd.setText("");
		jtxtOgrenciEmail.setText("");
		dateAlis.setDate(null);
		dateTeslim.setDate(null);
	}
	
	
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					YoneticiPanel frame = new YoneticiPanel();
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
	public YoneticiPanel() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(YoneticiPanel.class.getResource("/logo.png")));
		addWindowFocusListener(new WindowFocusListener() {
			public void windowGainedFocus(WindowEvent e) {
				kutuphaneListele();
			}
			public void windowLostFocus(WindowEvent e) {
			}
		});
		setTitle("Y\u00F6netici - Ana Sayfa");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnCikis = new JButton("\u00C7IKI\u015E YAP");
		btnCikis.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(null, "Çýkmak Ýstediðinize Emin Misiniz?", "UYARI",
				        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				    setVisible(false);
				    YoneticiGirisPanel yoneticiGiris = new YoneticiGirisPanel();
				    yoneticiGiris.setVisible(true);
				} else {
				    
				}
			}
		});
		btnCikis.setBounds(668, 10, 108, 21);
		contentPane.add(btnCikis);
		
		JButton btnKutuphaneEkle = new JButton("K\u00FCt\u00FCphane Ekle");
		btnKutuphaneEkle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				YoneticiKutuphaneEkle kutuphaneEkle = new YoneticiKutuphaneEkle();
				kutuphaneEkle.kutuphaneListele();
				kutuphaneEkle.setVisible(true);
			}
		});
		btnKutuphaneEkle.setBounds(10, 10, 137, 21);
		contentPane.add(btnKutuphaneEkle);
		
		JButton btnOgrenciEkle = new JButton("\u00D6\u011Frenci Ekle");
		btnOgrenciEkle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				YoneticiOgrenciEkle ogrenciEkle = new YoneticiOgrenciEkle();
				ogrenciEkle.kutuphaneListele();
				ogrenciEkle.setVisible(true);
				
			}
		});
		btnOgrenciEkle.setBounds(275, 10, 108, 21);
		contentPane.add(btnOgrenciEkle);
		
		JButton btnPersonelEkle = new JButton("Personel Ekle");
		btnPersonelEkle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				YoneticiPersonelEkle personelEkle = new YoneticiPersonelEkle();
				personelEkle.kutuphaneListele();
				personelEkle.setVisible(true);
			}
		});
		btnPersonelEkle.setBounds(393, 10, 118, 21);
		contentPane.add(btnPersonelEkle);
		
		JButton btnKitapEkle = new JButton("Kitap Ekle");
		btnKitapEkle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				YoneticiKitapEkle kitapEkle = new YoneticiKitapEkle();
				kitapEkle.kutuphaneListele();
				kitapEkle.setVisible(true);
			}
		});
		btnKitapEkle.setBounds(157, 10, 108, 21);
		contentPane.add(btnKitapEkle);
		
		JLabel jlblKutuphaneID = new JLabel("K\u00FCt\u00FCphane ID:");
		jlblKutuphaneID.setHorizontalAlignment(SwingConstants.TRAILING);
		jlblKutuphaneID.setBounds(10, 74, 82, 19);
		contentPane.add(jlblKutuphaneID);
		
		jtxtKutuphaneID = new JTextField();
		jtxtKutuphaneID.setDisabledTextColor(Color.BLACK);
		jtxtKutuphaneID.setEditable(false);
		jtxtKutuphaneID.setColumns(10);
		jtxtKutuphaneID.setBounds(102, 74, 108, 19);
		contentPane.add(jtxtKutuphaneID);
		
		jtxtKitapID = new JTextField();
		jtxtKitapID.setEditable(false);
		jtxtKitapID.setDisabledTextColor(Color.BLACK);
		jtxtKitapID.setColumns(10);
		jtxtKitapID.setBounds(102, 103, 108, 19);
		contentPane.add(jtxtKitapID);
		
		jtxtKitapAd = new JTextField();
		jtxtKitapAd.setEditable(false);
		jtxtKitapAd.setDisabledTextColor(Color.BLACK);
		jtxtKitapAd.setColumns(10);
		jtxtKitapAd.setBounds(102, 129, 108, 19);
		contentPane.add(jtxtKitapAd);
		
		jtxtKitapYazar = new JTextField();
		jtxtKitapYazar.setDisabledTextColor(Color.BLACK);
		jtxtKitapYazar.setEditable(false);
		jtxtKitapYazar.setColumns(10);
		jtxtKitapYazar.setBounds(102, 155, 108, 19);
		contentPane.add(jtxtKitapYazar);
		
		jtxtOgrenciID = new JTextField();
		jtxtOgrenciID.setEditable(false);
		jtxtOgrenciID.setDisabledTextColor(Color.BLACK);
		jtxtOgrenciID.setColumns(10);
		jtxtOgrenciID.setBounds(102, 233, 108, 19);
		contentPane.add(jtxtOgrenciID);
		
		jtxtOgrenciAd = new JTextField();
		jtxtOgrenciAd.setEditable(false);
		jtxtOgrenciAd.setDisabledTextColor(Color.BLACK);
		jtxtOgrenciAd.setColumns(10);
		jtxtOgrenciAd.setBounds(102, 259, 108, 19);
		contentPane.add(jtxtOgrenciAd);
		
		jtxtOgrenciEmail = new JTextField();
		jtxtOgrenciEmail.setEditable(false);
		jtxtOgrenciEmail.setDisabledTextColor(Color.BLACK);
		jtxtOgrenciEmail.setColumns(10);
		jtxtOgrenciEmail.setBounds(102, 285, 108, 19);
		contentPane.add(jtxtOgrenciEmail);
		
		JLabel jlblKitapID = new JLabel("Kitap ID:");
		jlblKitapID.setHorizontalAlignment(SwingConstants.TRAILING);
		jlblKitapID.setBounds(10, 103, 82, 19);
		contentPane.add(jlblKitapID);
		
		JLabel jlblKitapAd = new JLabel("Kitap Ad\u0131:");
		jlblKitapAd.setHorizontalAlignment(SwingConstants.TRAILING);
		jlblKitapAd.setBounds(10, 129, 82, 19);
		contentPane.add(jlblKitapAd);
		
		JLabel jlblKitapYazar = new JLabel("Kitap Yazar\u0131:");
		jlblKitapYazar.setHorizontalAlignment(SwingConstants.TRAILING);
		jlblKitapYazar.setBounds(10, 155, 82, 19);
		contentPane.add(jlblKitapYazar);
		
		JLabel jlblAlis = new JLabel("Al\u0131\u015F Tarihi:");
		jlblAlis.setHorizontalAlignment(SwingConstants.TRAILING);
		jlblAlis.setBounds(10, 181, 82, 19);
		contentPane.add(jlblAlis);
		
		JLabel jlblTeslim = new JLabel("Teslim Tarihi:");
		jlblTeslim.setHorizontalAlignment(SwingConstants.TRAILING);
		jlblTeslim.setBounds(10, 207, 82, 19);
		contentPane.add(jlblTeslim);
		
		JLabel jlblOgrenciID = new JLabel("\u00D6\u011Frenci ID:");
		jlblOgrenciID.setHorizontalAlignment(SwingConstants.TRAILING);
		jlblOgrenciID.setBounds(10, 233, 82, 19);
		contentPane.add(jlblOgrenciID);
		
		JLabel jlblOgrenciAd = new JLabel("\u00D6\u011Frenci Ad\u0131:");
		jlblOgrenciAd.setHorizontalAlignment(SwingConstants.TRAILING);
		jlblOgrenciAd.setBounds(10, 259, 82, 19);
		contentPane.add(jlblOgrenciAd);
		
		JLabel jlblOgrenciEmail = new JLabel("\u00D6\u011Frenci Email:");
		jlblOgrenciEmail.setHorizontalAlignment(SwingConstants.TRAILING);
		jlblOgrenciEmail.setBounds(10, 285, 82, 19);
		contentPane.add(jlblOgrenciEmail);
		
		JScrollPane scrollPaneKutuphane = new JScrollPane();
		scrollPaneKutuphane.setBounds(220, 100, 185, 178);
		contentPane.add(scrollPaneKutuphane);
		
		kutuphaneTablo = new JTable();
		kutuphaneTablo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int secilenSatir = kutuphaneTablo.getSelectedRow();
				DefaultTableModel kuTab = (DefaultTableModel)kutuphaneTablo.getModel();
				String kutID = kuTab.getValueAt(secilenSatir, 0).toString();
				jtxtTemizle();
				jtxtKutuphaneID.setText(kutID);
				ogrenciListele(kutID);
				oduncListele(kutID);
				kitapListele(kutID);
			}
		});
		kutuphaneTablo.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"K\u00FCt\u00FCphane ID", "K\u00FCt\u00FCphane Ad\u0131"
			}
		));
		scrollPaneKutuphane.setViewportView(kutuphaneTablo);
		
		JScrollPane scrollPaneKitap = new JScrollPane();
		scrollPaneKitap.setBounds(415, 100, 361, 123);
		contentPane.add(scrollPaneKitap);
		
		kitapTablo = new JTable();
		kitapTablo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int secilenSatir= kitapTablo.getSelectedRow();
				DefaultTableModel kiTab = (DefaultTableModel)kitapTablo.getModel();
				jtxtKitapID.setText(kiTab.getValueAt(secilenSatir, 0).toString());
				jtxtKitapAd.setText(kiTab.getValueAt(secilenSatir, 1).toString());
				jtxtKitapYazar.setText(kiTab.getValueAt(secilenSatir, 2).toString());
			}
		});
		kitapTablo.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Kitap ID", "Kitap Ad\u0131", "Kitap Yazar\u0131", "Kitap Adedi"
			}
		));
		scrollPaneKitap.setViewportView(kitapTablo);
		
		JScrollPane scrollPaneOgrenci = new JScrollPane();
		scrollPaneOgrenci.setBounds(415, 256, 361, 133);
		contentPane.add(scrollPaneOgrenci);
		
		ogrenciTablo = new JTable();
		ogrenciTablo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int secilenSatir= ogrenciTablo.getSelectedRow();
				DefaultTableModel ogTab = (DefaultTableModel)ogrenciTablo.getModel();
				jtxtOgrenciID.setText(ogTab.getValueAt(secilenSatir, 0).toString());
				jtxtOgrenciAd.setText(ogTab.getValueAt(secilenSatir, 1).toString());
				jtxtOgrenciEmail.setText(ogTab.getValueAt(secilenSatir, 2).toString());
			}
		});
		ogrenciTablo.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"\u00D6\u011Frenci ID", "\u00D6\u011Frenci Ad\u0131", "\u00D6\u011Frenci Emaili"
			}
		));
		scrollPaneOgrenci.setViewportView(ogrenciTablo);
		
		JButton btnOdunc = new JButton("\u00D6d\u00FCn\u00E7 Ver");
		btnOdunc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String kitapID = jtxtKitapID.getText();
				String kitapAd = jtxtKitapAd.getText();
				String kitapYazar = jtxtKitapYazar.getText();
				String ogrID = jtxtOgrenciID.getText();
				String ogrAd = jtxtOgrenciAd.getText();
				String ogrEmail = jtxtOgrenciEmail.getText();
				String kutID = jtxtKutuphaneID.getText();
				
				String alis = tarihKontrol(dateAlis);
				String teslim = tarihKontrol(dateTeslim);
				
				
				
				int secilenSatir= kitapTablo.getSelectedRow();				
				int secilenSatirTalep = ogrenciTablo.getSelectedRow();
				
				
					if(kitapTablo.isRowSelected(secilenSatir)) {
						DefaultTableModel kiTab = (DefaultTableModel)kitapTablo.getModel();
						if(ogrenciTablo.isRowSelected(secilenSatirTalep)) {
							if(!ogrID.equals("")&&!kitapID.equals("")) {
									if(!alis.equals("1")&&!teslim.equals("1")){
										String adet = kiTab.getValueAt(secilenSatir, 3).toString();
										int adetSayi = Integer.parseInt(adet);
										if(adetSayi!=1) {
											adetSayi--;
											fonk.oduncVer(kutID, kitapID, kitapAd, kitapYazar, alis, teslim, ogrID, ogrAd, ogrEmail);
											fonk.kitapAdetGuncelle(Integer.parseInt(kitapID), String.valueOf(adetSayi));
										kitapListele(kutID);
										oduncListele(kutID);
										jtxtTemizle();
									
										}else {
											JOptionPane.showMessageDialog(null, "Son Kitabý Ödünç Veremezsiniz!", "SÝSTEM MESAJI", JOptionPane.OK_OPTION);
										}						
								}else {
									JOptionPane.showMessageDialog(null, "Tarihleri Seçmelisiniz!", "SÝSTEM MESAJI", JOptionPane.OK_OPTION);
									}
								}else {
								JOptionPane.showMessageDialog(null, "Tüm Alanlarý Doldurunuz!", "SÝSTEM MESAJI", JOptionPane.OK_OPTION);
							}
						}else {
							JOptionPane.showMessageDialog(null, "Ödünç Vermek Ýstediðiniz Öðrenciyi Seçiniz!", "SÝSTEM MESAJI", JOptionPane.OK_OPTION);
						}
					}else {
						JOptionPane.showMessageDialog(null, "Ödünç Vermek Ýstediðiniz Kitabý Seçiniz!", "SÝSTEM MESAJI", JOptionPane.OK_OPTION);
					}
		
			}
		});
		btnOdunc.setBounds(10, 339, 121, 21);
		contentPane.add(btnOdunc);
		
		JButton btnTeslim = new JButton("Teslim Al");
		btnTeslim.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String kutID = jtxtKutuphaneID.getText();
				
				int secilenSatir= islemTablo.getSelectedRow();
				DefaultTableModel isTab = (DefaultTableModel)islemTablo.getModel();
				DefaultTableModel kiTab = (DefaultTableModel)kitapTablo.getModel();
				if(secilenSatir==-1){
		            if(isTab.getRowCount()==0){
		            	JOptionPane.showMessageDialog(null, "Liste boþ!", "SÝSTEM MESAJI", JOptionPane.OK_OPTION);
		            }else { JOptionPane.showMessageDialog(null, "Seçim yapýnýz!", "SÝSTEM MESAJI", JOptionPane.OK_OPTION);}
		        }else {	        		
		        		int id = Integer.parseInt(isTab.getValueAt(secilenSatir, 0).toString());
		        		String kitapID = isTab.getValueAt(secilenSatir, 2).toString();
		        		int KitapIDSayi = Integer.parseInt(kitapID);
		        		fonk.teslimAl(id);
		        		String adet= tablodanAdetGetir(kiTab,kitapID, "");
		        		if(adet.equals("")) {  //Eðer teslim almak istediðin kitap silinmiþse naparsýn? Yeni kitap olarak sisteme eklersin. 
		        			fonk.kitapEkle(kutID, isTab.getValueAt(secilenSatir, 3).toString(), isTab.getValueAt(secilenSatir, 4).toString(), "Bilinmiyor", "1");
		        		}else {  //Kitap yerinde duruyorsa adetini arttýrýrsýn. 
		        			 fonk.kitapAdetGuncelle(KitapIDSayi, adet);
		        		}
		       
		        	    kitapListele(kutID);
		        		oduncListele(kutID);
		        		jtxtTemizle();					
		       
		        }

			}
		});
		btnTeslim.setBounds(141, 339, 124, 21);
		contentPane.add(btnTeslim);
		
		JButton btnGuncelle = new JButton("G\u00FCncelle");
		btnGuncelle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String ogrID = jtxtOgrenciID.getText();
				String ogrAd = jtxtOgrenciAd.getText();
				String ogrEmail = jtxtOgrenciEmail.getText();
				String kutID = jtxtKutuphaneID.getText();
				
				String alis = tarihKontrol(dateAlis);
				String teslim = tarihKontrol(dateTeslim);
				
				int secilenSatir= islemTablo.getSelectedRow();
				DefaultTableModel isTab = (DefaultTableModel)islemTablo.getModel();
				if(secilenSatir==-1){
		            if(isTab.getRowCount()==0){
		            	JOptionPane.showMessageDialog(null, "Liste boþ!", "SÝSTEM MESAJI", JOptionPane.OK_OPTION);
		            }else { JOptionPane.showMessageDialog(null, "Seçim yapýnýz!", "SÝSTEM MESAJI", JOptionPane.OK_OPTION);}
		        }else {
		        	if(!ogrID.equals("")) {
		        		if(!alis.equals("1")&&!teslim.equals("1")){
		        			int id = Integer.parseInt(isTab.getValueAt(secilenSatir, 0).toString());
							fonk.oduncGuncelle(id,kutID, alis, teslim, ogrID, ogrAd, ogrEmail);
							oduncListele(kutID);
							jtxtTemizle();
		        		}else {
		        			JOptionPane.showMessageDialog(null, "Tarihleri Seçmelisiniz!", "SÝSTEM MESAJI", JOptionPane.OK_OPTION);
		        		}
		        						
		        	}else {
		        		JOptionPane.showMessageDialog(null, "Öðrenci Bilgilerini Doldurunuz!", "SÝSTEM MESAJI", JOptionPane.OK_OPTION);
		        	}
		        }
				
			}
		});
		btnGuncelle.setBounds(275, 339, 130, 21);
		contentPane.add(btnGuncelle);
		
		JScrollPane scrollPaneIslem = new JScrollPane();
		scrollPaneIslem.setBounds(10, 399, 766, 154);
		contentPane.add(scrollPaneIslem);
		
		islemTablo = new JTable();
		islemTablo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int secilenSatir = islemTablo.getSelectedRow();
				DefaultTableModel isTab = (DefaultTableModel)islemTablo.getModel();
				
				try {
					String tarihDegeri1 = isTab.getValueAt(secilenSatir,5).toString(); 
					java.util.Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(tarihDegeri1);
					dateAlis.setDate(date1);
				
					String tarihDegeri2 = isTab.getValueAt(secilenSatir,6).toString(); 
					java.util.Date date2 = new SimpleDateFormat("dd/MM/yyyy").parse(tarihDegeri2);
					dateTeslim.setDate(date2);
					
				} catch (ParseException e1) {
					e1.printStackTrace();
				}	

				jtxtKutuphaneID.setText(isTab.getValueAt(secilenSatir, 1).toString());
				jtxtKitapID.setText(isTab.getValueAt(secilenSatir, 2).toString());
				jtxtKitapAd.setText(isTab.getValueAt(secilenSatir, 3).toString());
				jtxtKitapYazar.setText(isTab.getValueAt(secilenSatir,4 ).toString());
				jtxtOgrenciID.setText(isTab.getValueAt(secilenSatir, 7).toString());
				jtxtOgrenciAd.setText(isTab.getValueAt(secilenSatir, 8).toString());
				jtxtOgrenciEmail.setText(isTab.getValueAt(secilenSatir,9 ).toString());
			
			}
			
		});
		islemTablo.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"\u0130\u015Flem ID", "K\u00FCt\u00FCphane ID", "Kitap ID", "Kitap Ad\u0131", "Kitap Yazar\u0131", "Al\u0131\u015F Tarihi", "Teslim Tarihi", "\u00D6\u011Frenci ID", "\u00D6\u011Frenci Ad\u0131", "\u00D6\u011Frenci Email"
			}
		));
		scrollPaneIslem.setViewportView(islemTablo);
		
		jtxtOgrenciFiltre = new JTextField();
		jtxtOgrenciFiltre.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				fil.filtre(jtxtOgrenciFiltre.getText(), ogrenciTablo);
			}
		});
		jtxtOgrenciFiltre.setBounds(415, 230, 361, 19);
		contentPane.add(jtxtOgrenciFiltre);
		jtxtOgrenciFiltre.setColumns(10);
		
		jtxtKitapFiltre = new JTextField();
		jtxtKitapFiltre.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				fil.filtre(jtxtKitapFiltre.getText(), kitapTablo);
			}
		});
		jtxtKitapFiltre.setColumns(10);
		jtxtKitapFiltre.setBounds(415, 74, 361, 19);
		contentPane.add(jtxtKitapFiltre);
		
		jtxtKutuphaneFiltre = new JTextField();
		jtxtKutuphaneFiltre.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				fil.filtre(jtxtKutuphaneFiltre.getText(), kutuphaneTablo);
			}
		});
		jtxtKutuphaneFiltre.setColumns(10);
		jtxtKutuphaneFiltre.setBounds(220, 74, 185, 19);
		contentPane.add(jtxtKutuphaneFiltre);
		
		jtxtIslemFiltre = new JTextField();
		jtxtIslemFiltre.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				fil.filtre(jtxtIslemFiltre.getText(), islemTablo);
			}
		});
		jtxtIslemFiltre.setColumns(10);
		jtxtIslemFiltre.setBounds(10, 370, 395, 19);
		contentPane.add(jtxtIslemFiltre);
		
		dateAlis = new JDateChooser();
		dateAlis.setDateFormatString("dd/MM/yyyy");
		dateAlis.setBounds(102, 181, 108, 19);
		contentPane.add(dateAlis);
		
		dateTeslim = new JDateChooser();
		dateTeslim.setDateFormatString("dd/MM/yyyy");
		dateTeslim.setBounds(102, 207, 108, 19);
		contentPane.add(dateTeslim);
		
		JButton btnTalepler = new JButton("Talepler");
		btnTalepler.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				YoneticiTalepler talepler = new YoneticiTalepler();
				talepler.kutuphaneListele();
				talepler.setVisible(true);
			}
		});
		btnTalepler.setBounds(521, 10, 118, 21);
		contentPane.add(btnTalepler);
		
		JButton btnTemizle = new JButton("Temizle");
		btnTemizle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jtxtTemizle();
			}
		});
		btnTemizle.setBounds(220, 284, 82, 21);
		contentPane.add(btnTemizle);
	}
}
