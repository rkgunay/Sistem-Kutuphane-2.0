import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Toolkit;

@SuppressWarnings("serial")
public class OgrenciBilgilerimiGuncelle extends JFrame {

	private JPanel contentPane;
	private JTextField jtxtAd;
	private JTextField jtxtEmail;
	private JTextField jtxtKadi;
	private JButton btnGuncelle;
	private JButton btnTemizle;
	private JTable ogrenciTablo;
	private JScrollPane scrollPaneOgrenci;

	VeritabaniFonksiyonlari fonk = new VeritabaniFonksiyonlari();
	private JTextField jtxtSifre;
	
	/**
	 * Launch the application.
	 */
	
	public void bilgilerimiListele(String id) {
		try {
			@SuppressWarnings("static-access")
			Statement st = fonk.conn.createStatement();
			String query = "SELECT * FROM ogrenci WHERE ogrenci_id= '"+id+"'";
			ResultSet rs = st.executeQuery(query);
			DefaultTableModel ogrTab= (DefaultTableModel)ogrenciTablo.getModel();
			ogrTab.setRowCount(0);
		
			while (rs.next()) {
				   
				    String o[] = {rs.getString("ogrenci_id"), rs.getString("ogrenci_ad"), rs.getString("ogrenci_email"),
				    		rs.getString("ogrenci_kullanici_adi"),rs.getString("ogrenci_sifre"), rs.getString("kutuphane_id")};
				    
				    	ogrTab.addRow(o);
				    	
				    
				   
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void jtxtTemizle() {
		jtxtAd.setText("");
		jtxtEmail.setText("");
		jtxtKadi.setText("");
		jtxtSifre.setText("");
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
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OgrenciBilgilerimiGuncelle frame = new OgrenciBilgilerimiGuncelle();
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
	public OgrenciBilgilerimiGuncelle() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(OgrenciBilgilerimiGuncelle.class.getResource("/logo.png")));
		setTitle("\u00D6\u011Frenci - Bilgilerimi G\u00FCncelle");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel jlblAd = new JLabel("\u00D6\u011Frenci Ad\u0131:");
		jlblAd.setHorizontalAlignment(SwingConstants.TRAILING);
		jlblAd.setBounds(66, 41, 141, 19);
		contentPane.add(jlblAd);
		
		jtxtAd = new JTextField();
		jtxtAd.setColumns(10);
		jtxtAd.setBounds(217, 41, 108, 19);
		contentPane.add(jtxtAd);
		
		JLabel jlblEmail = new JLabel("\u00D6\u011Frenci Emaili:");
		jlblEmail.setHorizontalAlignment(SwingConstants.TRAILING);
		jlblEmail.setBounds(66, 67, 141, 19);
		contentPane.add(jlblEmail);
		
		jtxtEmail = new JTextField();
		jtxtEmail.setColumns(10);
		jtxtEmail.setBounds(217, 67, 108, 19);
		contentPane.add(jtxtEmail);
		
		JLabel jlblKadi = new JLabel("Kullan\u0131c\u0131 Ad\u0131:");
		jlblKadi.setHorizontalAlignment(SwingConstants.TRAILING);
		jlblKadi.setBounds(66, 96, 141, 19);
		contentPane.add(jlblKadi);
		
		jtxtKadi = new JTextField();
		jtxtKadi.setColumns(10);
		jtxtKadi.setBounds(217, 96, 108, 19);
		contentPane.add(jtxtKadi);
		
		JLabel jlblSifre = new JLabel("\u015Eifre:");
		jlblSifre.setHorizontalAlignment(SwingConstants.TRAILING);
		jlblSifre.setBounds(66, 125, 141, 19);
		contentPane.add(jlblSifre);
		
		btnGuncelle = new JButton("G\u00FCncelle");
		btnGuncelle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String ad = jtxtAd.getText();
				String email = jtxtEmail.getText();
				String kadi = jtxtKadi.getText();
				String sifre = jtxtSifre.getText();
				int secilenSatir= ogrenciTablo.getSelectedRow();
				DefaultTableModel ogTab = (DefaultTableModel)ogrenciTablo.getModel();
				if(secilenSatir==-1){
			            if(ogTab.getRowCount()==0){
			            	JOptionPane.showMessageDialog(null, "Liste boþ!", "SÝSTEM MESAJI", JOptionPane.OK_OPTION);
			            }else { JOptionPane.showMessageDialog(null, "Seçim yapýnýz!", "SÝSTEM MESAJI", JOptionPane.OK_OPTION);}
			        }else {
			        	if((ad.equals("")||email.equals("")||kadi.equals("")||sifre.equals(""))) {
							JOptionPane.showMessageDialog(null, "Tüm Alanlarý Doldurunuz.", "SÝSTEM MESAJI", JOptionPane.OK_OPTION);
						}else {
							if(ogrenciKadiKontrol(kadi)<=1) {
								int id = Integer.parseInt(ogTab.getValueAt(secilenSatir, 0).toString());
								fonk.ogrenciBilgilerimiGuncelle(id, ad, email, kadi, sifre);
								JOptionPane.showMessageDialog(null, "Güncelleme Baþarýlý", "SÝSTEM MESAJI", JOptionPane.OK_OPTION);
								bilgilerimiListele(ogTab.getValueAt(secilenSatir, 0).toString());
								jtxtTemizle();
							}else {
								jtxtKadi.setText("");
								JOptionPane.showMessageDialog(null, "Kullanýcý Adý Uygun Deðil.", "SÝSTEM MESAJI", JOptionPane.OK_OPTION);
							}
							
						}
									        	
			        }
			}
		});
		btnGuncelle.setBounds(240, 154, 85, 21);
		contentPane.add(btnGuncelle);
		
		btnTemizle = new JButton("Temizle");
		btnTemizle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jtxtTemizle();
			}
		});
		btnTemizle.setBounds(145, 154, 85, 21);
		contentPane.add(btnTemizle);
		
		scrollPaneOgrenci = new JScrollPane();
		scrollPaneOgrenci.setBounds(10, 200, 416, 53);
		contentPane.add(scrollPaneOgrenci);
		
		ogrenciTablo = new JTable();
		ogrenciTablo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int secilenSatir = ogrenciTablo.getSelectedRow();
				DefaultTableModel ogrTab = (DefaultTableModel)ogrenciTablo.getModel();
				jtxtAd.setText(ogrTab.getValueAt(secilenSatir, 1).toString());
				jtxtEmail.setText(ogrTab.getValueAt(secilenSatir, 2).toString());
				jtxtKadi.setText(ogrTab.getValueAt(secilenSatir, 3).toString());
				jtxtSifre.setText(ogrTab.getValueAt(secilenSatir, 4).toString());
			}
		});
		ogrenciTablo.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Ad", "Email", "Kullan\u0131c\u0131 Ad\u0131", "\u015Eifre"
			}
		));
		scrollPaneOgrenci.setViewportView(ogrenciTablo);
		
		jtxtSifre = new JTextField();
		jtxtSifre.setColumns(10);
		jtxtSifre.setBounds(217, 125, 108, 19);
		contentPane.add(jtxtSifre);
	}
	public JTable setOgrenciTablo() {
		ogrenciTablo.setRowSelectionInterval(0, 0);
		return null;
	}
}
