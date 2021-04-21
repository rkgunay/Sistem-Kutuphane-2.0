import java.awt.EventQueue;
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Toolkit;

@SuppressWarnings("serial")
public class YoneticiPersonelEkle extends JFrame {

	private JPanel contentPane;
	private JTextField jtxtKutuphaneID;
	private JTextField jtxtAd;
	private JTextField jtxtKadi;
	private JTable personelTablo;
	private JLabel jlblSifre;
	private JTextField jtxtSifre;
	private JButton btnGuncelle;
	private JButton btnSil;
	private JTextField jtxtKutuphaneFiltre;
	private JTable kutuphaneTablo;
	private JScrollPane scrollPaneKutuphane;
	private JTextField jtxtPersonelFiltre;

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
	
	public void personelListele(String kutuphaneID){
		try {
			@SuppressWarnings("static-access")
			Statement st = fonk.conn.createStatement();
			String query = "SELECT * FROM personel WHERE kutuphane_id= '"+kutuphaneID+"'";
			ResultSet rs = st.executeQuery(query);
			
			DefaultTableModel perTab = (DefaultTableModel)personelTablo.getModel();
			perTab.setRowCount(0);
			

			while (rs.next()) {
			    String o[] = {rs.getString("personel_id".toString()), rs.getString("kutuphane_id"), rs.getString("personel_ad"), 
			    		rs.getString("personel_kullanici_adi"), rs.getString("personel_sifre")};
			    perTab.addRow(o);
			   
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
	}
	
	//Güncelleme butonunda minik bir sorun var. 2 tane ayný kullanýcý adýna izin veriyor. Düzeltmek için veritabanýndan personel_id'sini de alýp
	//eþleþme bulunan kullanýcý adýnýn personel tablosundaki seçilen satýrdaki personel_id'sine ait olup olmadýðý kontrol edilmesi gerekiyor.
	//Uzun iþ. Daha kolay bir çözüm bulana kadar erteliyeceðim.
	public int personelKadiKontrol(String kadi){
        int label = 0;
        String sorgu = "SELECT COUNT(*) FROM personel WHERE personel_kullanici_adi='"+kadi+"'";
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
		jtxtAd.setText("");
		jtxtKadi.setText("");
		jtxtSifre.setText("");
	}
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					YoneticiPersonelEkle frame = new YoneticiPersonelEkle();
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
	public YoneticiPersonelEkle() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(YoneticiPersonelEkle.class.getResource("/logo.png")));
		setTitle("Y\u00F6netici - Personel Ekle");
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
		
		JLabel jlblAd = new JLabel("Personel Ad\u0131:");
		jlblAd.setHorizontalAlignment(SwingConstants.TRAILING);
		jlblAd.setBounds(10, 39, 82, 19);
		panel.add(jlblAd);
		
		jtxtAd = new JTextField();
		jtxtAd.setColumns(10);
		jtxtAd.setBounds(102, 39, 108, 19);
		panel.add(jtxtAd);
		
		jtxtKadi = new JTextField();
		jtxtKadi.setColumns(10);
		jtxtKadi.setBounds(102, 65, 108, 19);
		panel.add(jtxtKadi);
		
		JLabel jlblKadi = new JLabel("Kullan\u0131c\u0131 Ad\u0131:");
		jlblKadi.setHorizontalAlignment(SwingConstants.TRAILING);
		jlblKadi.setBounds(10, 65, 82, 19);
		panel.add(jlblKadi);
		
		JScrollPane scrollPanePersonel = new JScrollPane();
		scrollPanePersonel.setBounds(10, 202, 546, 131);
		panel.add(scrollPanePersonel);
		
		personelTablo = new JTable();
		personelTablo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int secilenSatir= personelTablo.getSelectedRow();
				DefaultTableModel perTab = (DefaultTableModel)personelTablo.getModel();
				jtxtKutuphaneID.setText(perTab.getValueAt(secilenSatir, 1).toString());
				jtxtAd.setText(perTab.getValueAt(secilenSatir, 2).toString());
				jtxtKadi.setText(perTab.getValueAt(secilenSatir, 3).toString());
				jtxtSifre.setText(perTab.getValueAt(secilenSatir, 4).toString());
			}
		});
		scrollPanePersonel.setViewportView(personelTablo);
		
		personelTablo.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Personel ID", "K\u00FCt\u00FCphane ID", "Personel Ad\u0131", "Kullan\u0131c\u0131 Ad\u0131", "\u015Eifre"
			}
		));
		
		jtxtPersonelFiltre = new JTextField();
		jtxtPersonelFiltre.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				fil.filtre(jtxtPersonelFiltre.getText(), personelTablo);
			}
		});
		jtxtPersonelFiltre.setColumns(10);
		jtxtPersonelFiltre.setBounds(230, 173, 326, 19);
		panel.add(jtxtPersonelFiltre);
		
		jlblSifre = new JLabel("\u015Eifre:");
		jlblSifre.setHorizontalAlignment(SwingConstants.TRAILING);
		jlblSifre.setBounds(10, 91, 82, 19);
		panel.add(jlblSifre);
		
		jtxtSifre = new JTextField();
		jtxtSifre.setColumns(10);
		jtxtSifre.setBounds(102, 91, 108, 19);
		panel.add(jtxtSifre);
		
		JButton btnEkle = new JButton("Ekle");
		btnEkle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int secilenSatir= kutuphaneTablo.getSelectedRow();
				if(kutuphaneTablo.isRowSelected(secilenSatir)) {
					String kutID = jtxtKutuphaneID.getText();
					String ad = jtxtAd.getText();
					String kadi = jtxtKadi.getText();
					String sifre = jtxtSifre.getText();
					if((ad.equals("")||kadi.equals("")||sifre.equals(""))) {
						JOptionPane.showMessageDialog(null, "Tüm Alanlarý Doldurunuz.", "SÝSTEM MESAJI", JOptionPane.OK_OPTION);
					}else {
						if(personelKadiKontrol(kadi)<=0) {
							fonk.personelEkle(kutID,ad, kadi, sifre);
							personelListele(kutID);
							jtxtTemizle();													
						}else {
							jtxtKadi.setText("");
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
				String ad = jtxtAd.getText();
				String kadi = jtxtKadi.getText();
				String sifre = jtxtSifre.getText();
				int secilenSatir= personelTablo.getSelectedRow();
				DefaultTableModel perTab = (DefaultTableModel)personelTablo.getModel();
				if(secilenSatir==-1){
			            if(perTab.getRowCount()==0){
			            	JOptionPane.showMessageDialog(null, "Liste boþ!", "SÝSTEM MESAJI", JOptionPane.OK_OPTION);
			            }else { JOptionPane.showMessageDialog(null, "Seçim yapýnýz!", "SÝSTEM MESAJI", JOptionPane.OK_OPTION);}
			        }else {	
			        	if(personelKadiKontrol(kadi)<=1) {
			        		int id = Integer.parseInt(perTab.getValueAt(secilenSatir, 0).toString());
			        		fonk.personelGuncelle(id, kutID, ad, kadi, sifre);
			        		JOptionPane.showMessageDialog(null, "Güncelleme Baþarýlý", "SÝSTEM MESAJI", JOptionPane.OK_OPTION);
			        		personelListele(kutID);
			        		jtxtTemizle();			        		
			        	}else {
			        		jtxtKadi.setText("");
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
				int secilenSatir= personelTablo.getSelectedRow();
				DefaultTableModel perTab = (DefaultTableModel)personelTablo.getModel();
				
				if(secilenSatir==-1){
		            if(perTab.getRowCount()==0){
		            	JOptionPane.showMessageDialog(null, "Liste boþ!", "SÝSTEM MESAJI", JOptionPane.OK_OPTION);
		            }else { JOptionPane.showMessageDialog(null, "Seçim yapýnýz!", "SÝSTEM MESAJI", JOptionPane.OK_OPTION);}
		        }else {
		        	int id = Integer.parseInt(perTab.getValueAt(secilenSatir, 0).toString());
		        	String kutID = perTab.getValueAt(secilenSatir, 1).toString();
		        	fonk.personelSil(id);
		        	personelListele(kutID);
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
				personelListele(kutID);
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
