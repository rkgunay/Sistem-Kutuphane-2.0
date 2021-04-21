import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import java.awt.Color;
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Toolkit;

@SuppressWarnings("serial")
public class PersonelPanel extends JFrame {

	private JPanel contentPane;
	private JTextField jtxtKitapID;
	private JTextField jtxtKitapAd;
	private JTextField jtxtKitapYazar;
	private JTextField jtxtOgrenciID;
	private JTextField jtxtOgrenciAd;
	private JTextField jtxtOgrenciEmail;
	private JTable kitapTablo;
	private JTable ogrenciTablo;
	private JTable islemTablo;
	private JTextField jtxtOgrenciFiltre;
	private JTextField jtxtKitapFiltre;
	private JTextField jtxtIslemFiltre;
	private JTextField jtxtID;
	private JDateChooser dateAlis;
	private JDateChooser dateTeslim;
	
	VeritabaniFonksiyonlari fonk = new VeritabaniFonksiyonlari();
	FiltrelemeFonksiyonu fil = new FiltrelemeFonksiyonu();
	private JTextField txtYenile;
	
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
	
	// JDateChooser boþ iken getDate() fonksiyonu çaðýrýldýðýnda hata verdiðinden bu iþlemi fonksiyona atadým. 
	private String tarihKontrol(JDateChooser date){
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				String tarih = sdf.format(((JDateChooser) date).getDate()).toString();
				return tarih;
			} catch (Exception e) {
				return "1";
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
					PersonelPanel frame = new PersonelPanel();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PersonelPanel() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(PersonelPanel.class.getResource("/logo.png")));
		setTitle("Personel - Ana Sayfa");
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
				    PersonelGirisPanel personelGiris = new PersonelGirisPanel();
				    personelGiris.setVisible(true);
				} else {
				    
				}
			}
		});
		btnCikis.setBounds(668, 10, 108, 21);
		contentPane.add(btnCikis);
		
		JButton btnOgrenciEkle = new JButton("\u00D6\u011Frenci Ekle");
		btnOgrenciEkle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PersonelOgrenciEkle ogrenciEkle = new PersonelOgrenciEkle();
				ogrenciEkle.ogrenciListele(jtxtID.getText());
				ogrenciEkle.setVisible(true);
				ogrenciEkle.setJtxtGizliKutID(jtxtID.getText());
			}
		});
		btnOgrenciEkle.setBounds(128, 10, 108, 21);
		contentPane.add(btnOgrenciEkle);
		
		JButton btnPersonelEkle = new JButton("Personel Ekle");
		btnPersonelEkle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PersonelPersonelEkle personelEkle = new PersonelPersonelEkle();
				personelEkle.personelListele(jtxtID.getText());
				personelEkle.setVisible(true);
				personelEkle.setJtxtGizliKutID(jtxtID.getText());
			}
		});
		btnPersonelEkle.setBounds(246, 10, 118, 21);
		contentPane.add(btnPersonelEkle);
		
		JButton btnKitapEkle = new JButton("Kitap Ekle");
		btnKitapEkle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PersonelKitapEkle kitapEkle = new PersonelKitapEkle();
				kitapEkle.kitapListele(jtxtID.getText());
				kitapEkle.setJtxtGizliKutID(jtxtID.getText());
				kitapEkle.setVisible(true);
			}
		});
		btnKitapEkle.setBounds(10, 10, 108, 21);
		contentPane.add(btnKitapEkle);
		
		jtxtKitapID = new JTextField();
		jtxtKitapID.setDisabledTextColor(Color.BLACK);
		jtxtKitapID.setEditable(false);
		jtxtKitapID.setColumns(10);
		jtxtKitapID.setBounds(142, 201, 108, 19);
		contentPane.add(jtxtKitapID);
		
		jtxtKitapAd = new JTextField();
		jtxtKitapAd.setEditable(false);
		jtxtKitapAd.setDisabledTextColor(Color.BLACK);
		jtxtKitapAd.setColumns(10);
		jtxtKitapAd.setBounds(142, 227, 108, 19);
		contentPane.add(jtxtKitapAd);
		
		jtxtKitapYazar = new JTextField();
		jtxtKitapYazar.setEditable(false);
		jtxtKitapYazar.setDisabledTextColor(Color.BLACK);
		jtxtKitapYazar.setColumns(10);
		jtxtKitapYazar.setBounds(142, 253, 108, 19);
		contentPane.add(jtxtKitapYazar);
		
		jtxtOgrenciID = new JTextField();
		jtxtOgrenciID.setDisabledTextColor(Color.BLACK);
		jtxtOgrenciID.setEditable(false);
		jtxtOgrenciID.setColumns(10);
		jtxtOgrenciID.setBounds(378, 201, 108, 19);
		contentPane.add(jtxtOgrenciID);
		
		jtxtOgrenciAd = new JTextField();
		jtxtOgrenciAd.setEditable(false);
		jtxtOgrenciAd.setDisabledTextColor(Color.BLACK);
		jtxtOgrenciAd.setColumns(10);
		jtxtOgrenciAd.setBounds(378, 227, 108, 19);
		contentPane.add(jtxtOgrenciAd);
		
		jtxtOgrenciEmail = new JTextField();
		jtxtOgrenciEmail.setDisabledTextColor(Color.BLACK);
		jtxtOgrenciEmail.setEditable(false);
		jtxtOgrenciEmail.setColumns(10);
		jtxtOgrenciEmail.setBounds(378, 253, 108, 19);
		contentPane.add(jtxtOgrenciEmail);
		
		JLabel jlblKitapID = new JLabel("Kitap ID:");
		jlblKitapID.setHorizontalAlignment(SwingConstants.TRAILING);
		jlblKitapID.setBounds(50, 201, 82, 19);
		contentPane.add(jlblKitapID);
		
		JLabel jlblKitapAd = new JLabel("Kitap Ad\u0131:");
		jlblKitapAd.setHorizontalAlignment(SwingConstants.TRAILING);
		jlblKitapAd.setBounds(50, 227, 82, 19);
		contentPane.add(jlblKitapAd);
		
		JLabel jlblKitapYazar = new JLabel("Kitap Yazar\u0131:");
		jlblKitapYazar.setHorizontalAlignment(SwingConstants.TRAILING);
		jlblKitapYazar.setBounds(50, 253, 82, 19);
		contentPane.add(jlblKitapYazar);
		
		JLabel jlblOgrenciID = new JLabel("\u00D6\u011Frenci ID:");
		jlblOgrenciID.setHorizontalAlignment(SwingConstants.TRAILING);
		jlblOgrenciID.setBounds(286, 201, 82, 19);
		contentPane.add(jlblOgrenciID);
		
		JLabel jlblOgrenciAd = new JLabel("\u00D6\u011Frenci Ad\u0131:");
		jlblOgrenciAd.setHorizontalAlignment(SwingConstants.TRAILING);
		jlblOgrenciAd.setBounds(286, 227, 82, 19);
		contentPane.add(jlblOgrenciAd);
		
		JLabel jlblOgrenciEmail = new JLabel("\u00D6\u011Frenci Email:");
		jlblOgrenciEmail.setHorizontalAlignment(SwingConstants.TRAILING);
		jlblOgrenciEmail.setBounds(286, 253, 82, 19);
		contentPane.add(jlblOgrenciEmail);
		
		JScrollPane scrollPaneKitap = new JScrollPane();
		scrollPaneKitap.setBounds(10, 87, 395, 104);
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
		scrollPaneOgrenci.setBounds(415, 87, 361, 104);
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
				String kutID = jtxtID.getText();
				
				
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
		btnOdunc.setBounds(186, 321, 124, 21);
		contentPane.add(btnOdunc);
		
		JButton btnTeslim = new JButton("Teslim Al");
		btnTeslim.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String kutID = jtxtID.getText();
				
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
		btnTeslim.setBounds(320, 321, 124, 21);
		contentPane.add(btnTeslim);
		
		JButton btnGuncelle = new JButton("G\u00FCncelle");
		btnGuncelle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String ogrID = jtxtOgrenciID.getText();
				String ogrAd = jtxtOgrenciAd.getText();
				String ogrEmail = jtxtOgrenciEmail.getText();
				String kutID = jtxtID.getText();
				
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
		btnGuncelle.setBounds(454, 321, 130, 21);
		contentPane.add(btnGuncelle);
		
		JScrollPane scrollPaneIslem = new JScrollPane();
		scrollPaneIslem.setBounds(10, 381, 766, 172);
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
		jtxtOgrenciFiltre.setBounds(415, 57, 361, 19);
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
		jtxtKitapFiltre.setBounds(10, 57, 395, 19);
		contentPane.add(jtxtKitapFiltre);
		
		jtxtIslemFiltre = new JTextField();
		jtxtIslemFiltre.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				fil.filtre(jtxtIslemFiltre.getText(), islemTablo);
			}
		});
		jtxtIslemFiltre.setColumns(10);
		jtxtIslemFiltre.setBounds(10, 352, 766, 19);
		contentPane.add(jtxtIslemFiltre);
		
		jtxtID = new JTextField();
		jtxtID.setText("AA");
		jtxtID.setHorizontalAlignment(SwingConstants.TRAILING);
		jtxtID.setEnabled(false);
		jtxtID.setEditable(false);
		jtxtID.setDisabledTextColor(Color.DARK_GRAY);
		jtxtID.setColumns(10);
		jtxtID.setBorder(null);
		jtxtID.setBounds(568, 11, 90, 21);
		contentPane.add(jtxtID);
		
		JButton btnTalepler = new JButton("Talepler");
		btnTalepler.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PersonelTalepler talepler = new PersonelTalepler();
				talepler.setJtxtGizliKutID(jtxtID.getText());
				talepler.kitapListele(jtxtID.getText());
				talepler.talepListele(jtxtID.getText());
				talepler.setVisible(true);
			}
		});
		btnTalepler.setBounds(374, 10, 118, 21);
		contentPane.add(btnTalepler);
		
		JLabel jlblAlisTarih = new JLabel("Al\u0131\u015F Tarihi:");
		jlblAlisTarih.setHorizontalAlignment(SwingConstants.TRAILING);
		jlblAlisTarih.setBounds(534, 201, 82, 19);
		contentPane.add(jlblAlisTarih);
		
		dateAlis = new JDateChooser();
		dateAlis.setDateFormatString("dd/MM/yyyy");
		dateAlis.setBounds(626, 201, 108, 19);
		contentPane.add(dateAlis);
		
		dateTeslim = new JDateChooser();
		dateTeslim.setDateFormatString("dd/MM/yyyy");
		dateTeslim.setBounds(626, 227, 108, 19);
		contentPane.add(dateTeslim);
		
		JLabel jlblTeslimTarih = new JLabel("Teslim Tarihi:");
		jlblTeslimTarih.setHorizontalAlignment(SwingConstants.TRAILING);
		jlblTeslimTarih.setBounds(534, 227, 82, 19);
		contentPane.add(jlblTeslimTarih);
		
		JButton btnTemizle = new JButton("Temizle");
		btnTemizle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jtxtTemizle();
				
			}
		});
		btnTemizle.setBounds(626, 252, 108, 21);
		contentPane.add(btnTemizle);
		
		txtYenile = new JTextField();
		txtYenile.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				txtYenile.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 10));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				txtYenile.setFont(new Font("Tahoma", Font.ITALIC, 10));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				txtYenile.setFont(new Font("Tahoma", Font.ITALIC, 10));
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				txtYenile.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 10));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				String kutID = jtxtID.getText();
				kitapListele(kutID);
				ogrenciListele(kutID);
				oduncListele(kutID);
			}
		});
		txtYenile.setFont(new Font("Tahoma", Font.ITALIC, 10));
		txtYenile.setText("TAZELE");
		txtYenile.setHorizontalAlignment(SwingConstants.CENTER);
		txtYenile.setEnabled(false);
		txtYenile.setEditable(false);
		txtYenile.setDisabledTextColor(Color.DARK_GRAY);
		txtYenile.setColumns(10);
		txtYenile.setBorder(null);
		txtYenile.setBounds(502, 10, 56, 21);
		contentPane.add(txtYenile);
		
		

	}
	public JTextField setJtxtID(String persID) {
		jtxtID.setText(persID);
		return null;
	}
	public JTextField getJtxtID() {
		return jtxtID;
		
	}
}
