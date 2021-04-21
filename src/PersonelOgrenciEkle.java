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
public class PersonelOgrenciEkle extends JFrame {

	private JPanel contentPane;
	private JTextField jtxtAd;
	private JTextField jtxtEmail;
	private JTextField jtxtOgrenciFiltre;
	private JTable ogrenciTablo;
	private JLabel jlblKadi;
	private JTextField jtxtKadi;
	private JButton btnEkle;
	private JButton btnGuncelle;
	private JButton btnSil;
	private JTextField jtxtGizliKutID;

	VeritabaniFonksiyonlari fonk = new VeritabaniFonksiyonlari();
	FiltrelemeFonksiyonu fil = new FiltrelemeFonksiyonu();
	private JTextField jtxtSifre;
	
	/**
	 * Launch the application.
	 */
	
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
	
	//G�ncelleme butonunda minik bir sorun var. 2 tane ayn� kullan�c� ad�na izin veriyor. D�zeltmek i�in veritaban�ndan ogrenci_id'sini de al�p
	//e�le�me bulunan kullan�c� ad�n�n ��renci tablosundaki se�ilen sat�rdaki ��renci_id'sine ait olup olmad��� kontrol edilmesi gerekiyor.
	//Uzun i�. Daha kolay bir ��z�m bulana kadar erteliyece�im.
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
		jtxtAd.setText("");
		jtxtEmail.setText("");
		jtxtKadi.setText("");
		jtxtSifre.setText("");
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PersonelOgrenciEkle frame = new PersonelOgrenciEkle();
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
	public PersonelOgrenciEkle() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(PersonelOgrenciEkle.class.getResource("/logo.png")));
		setTitle("Personel - \u00D6\u011Frenci Ekle");
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
		
		JLabel jlblAd = new JLabel("\u00D6\u011Frenci Ad\u0131:");
		jlblAd.setHorizontalAlignment(SwingConstants.TRAILING);
		jlblAd.setBounds(10, 10, 141, 19);
		panel.add(jlblAd);
		
		jtxtAd = new JTextField();
		jtxtAd.setColumns(10);
		jtxtAd.setBounds(161, 10, 108, 19);
		panel.add(jtxtAd);
		
		jtxtEmail = new JTextField();
		jtxtEmail.setColumns(10);
		jtxtEmail.setBounds(161, 36, 108, 19);
		panel.add(jtxtEmail);
		
		JLabel jlblEmail = new JLabel("\u00D6\u011Frenci Emaili:");
		jlblEmail.setHorizontalAlignment(SwingConstants.TRAILING);
		jlblEmail.setBounds(10, 36, 141, 19);
		panel.add(jlblEmail);
		
		JScrollPane scrollPaneOgrenci = new JScrollPane();
		scrollPaneOgrenci.setBounds(10, 170, 546, 163);
		panel.add(scrollPaneOgrenci);
		
		ogrenciTablo = new JTable();
		ogrenciTablo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int secilenSatir= ogrenciTablo.getSelectedRow();
				DefaultTableModel ogTab = (DefaultTableModel)ogrenciTablo.getModel();
				jtxtAd.setText(ogTab.getValueAt(secilenSatir, 1).toString());
				jtxtEmail.setText(ogTab.getValueAt(secilenSatir, 2).toString());
				jtxtKadi.setText(ogTab.getValueAt(secilenSatir, 3).toString());
				jtxtSifre.setText(ogTab.getValueAt(secilenSatir, 4).toString());
			}
		});
		scrollPaneOgrenci.setViewportView(ogrenciTablo);
		
		ogrenciTablo.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"\u00D6\u011Frenci ID", "\u00D6\u011Frenci Ad\u0131", "\u00D6\u011Frenci Email", "Kullan\u0131c\u0131 Ad\u0131", "\u015Eifre"
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
		jtxtOgrenciFiltre.setBounds(10, 141, 546, 19);
		panel.add(jtxtOgrenciFiltre);
		
		JLabel jlblSifre = new JLabel("\u015Eifre:");
		jlblSifre.setHorizontalAlignment(SwingConstants.TRAILING);
		jlblSifre.setBounds(10, 94, 141, 19);
		panel.add(jlblSifre);
		
		jlblKadi = new JLabel("Kullan\u0131c\u0131 Ad\u0131:");
		jlblKadi.setHorizontalAlignment(SwingConstants.TRAILING);
		jlblKadi.setBounds(10, 65, 141, 19);
		panel.add(jlblKadi);
		
		jtxtKadi = new JTextField();
		jtxtKadi.setColumns(10);
		jtxtKadi.setBounds(161, 65, 108, 19);
		panel.add(jtxtKadi);
		
		btnEkle = new JButton("Ekle");
		btnEkle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String ad = jtxtAd.getText();
				String email = jtxtEmail.getText();
				String kadi = jtxtKadi.getText();
				String sifre = jtxtSifre.getText();
				String kutID = jtxtGizliKutID.getText();
				if((ad.equals("")||email.equals("")||kadi.equals("")||sifre.equals(""))) {
					JOptionPane.showMessageDialog(null, "T�m Alanlar� Doldurunuz.", "S�STEM MESAJI", JOptionPane.OK_OPTION);
				}else {
					if(ogrenciKadiKontrol(kadi)<=0) {
						fonk.ogrenciEkle(ad, email, kadi, sifre, kutID);
						ogrenciListele(kutID);
						jtxtTemizle();													
					}else {
						jtxtKadi.setText("");
						JOptionPane.showMessageDialog(null, "Kullan�c� Ad� Uygun De�il.", "S�STEM MESAJI", JOptionPane.OK_OPTION);
					}
				}
			}
		});
		btnEkle.setBounds(314, 36, 85, 21);
		panel.add(btnEkle);
		
		btnGuncelle = new JButton("G\u00FCncelle");
		btnGuncelle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String kutID = jtxtGizliKutID.getText();
				String ad = jtxtAd.getText();
				String email = jtxtEmail.getText();
				String kadi = jtxtKadi.getText();
				String sifre = jtxtSifre.getText();
				int secilenSatir= ogrenciTablo.getSelectedRow();
				DefaultTableModel ogTab = (DefaultTableModel)ogrenciTablo.getModel();
				if(secilenSatir==-1){
			            if(ogTab.getRowCount()==0){
			            	JOptionPane.showMessageDialog(null, "Liste bo�!", "S�STEM MESAJI", JOptionPane.OK_OPTION);
			            }else { JOptionPane.showMessageDialog(null, "Se�im yap�n�z!", "S�STEM MESAJI", JOptionPane.OK_OPTION);}
			        }else {	
			        	if(ogrenciKadiKontrol(kadi)<=1) {
			        		int id = Integer.parseInt(ogTab.getValueAt(secilenSatir, 0).toString());
			        		fonk.ogrenciGuncelle(id, ad, email, kadi, sifre, kutID);
			        		JOptionPane.showMessageDialog(null, "G�ncelleme Ba�ar�l�", "S�STEM MESAJI", JOptionPane.OK_OPTION);
			        		ogrenciListele(kutID);
			        		jtxtTemizle();			        		
			        	}else {
			        		jtxtKadi.setText("");
			        		JOptionPane.showMessageDialog(null, "Kullan�c� Ad� Uygun De�il.", "S�STEM MESAJI", JOptionPane.OK_OPTION);
			        	}
						
			        	
			        }
			}
		});
		btnGuncelle.setBounds(314, 65, 85, 21);
		panel.add(btnGuncelle);
		
		btnSil = new JButton("Sil");
		btnSil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int secilenSatir= ogrenciTablo.getSelectedRow();
				DefaultTableModel ogTab = (DefaultTableModel)ogrenciTablo.getModel();
				
				if(secilenSatir==-1){
		            if(ogTab.getRowCount()==0){
		            	JOptionPane.showMessageDialog(null, "Liste bo�!", "S�STEM MESAJI", JOptionPane.OK_OPTION);
		            }else { JOptionPane.showMessageDialog(null, "Se�im yap�n�z!", "S�STEM MESAJI", JOptionPane.OK_OPTION);}
		        }else {
		        	int id = Integer.parseInt(ogTab.getValueAt(secilenSatir, 0).toString());
		        	String kutID = jtxtGizliKutID.getText();
		        	fonk.ogrenciSil(id);
		        	ogrenciListele(kutID);
		        }
			}
		});
		btnSil.setBounds(314, 94, 85, 21);
		panel.add(btnSil);
		
		jtxtGizliKutID = new JTextField();
		jtxtGizliKutID.setVisible(false);
		jtxtGizliKutID.setEnabled(false);
		jtxtGizliKutID.setEditable(false);
		jtxtGizliKutID.setColumns(10);
		jtxtGizliKutID.setBounds(549, 0, 7, 19);
		panel.add(jtxtGizliKutID);
		
		jtxtSifre = new JTextField();
		jtxtSifre.setColumns(10);
		jtxtSifre.setBounds(161, 94, 108, 19);
		panel.add(jtxtSifre);
	}
	public JTextField setJtxtGizliKutID(String kutID) {
		 jtxtGizliKutID.setText(kutID);
		 return null;
	}
}
