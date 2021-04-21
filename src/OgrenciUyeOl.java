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
public class OgrenciUyeOl extends JFrame {

	private JPanel contentPane;
	private JTextField jtxtAd;
	private JTextField jtxtEmail;
	private JTextField jtxtKadi;
	private JButton btnUyeOl;
	private JButton btnTemizle;
	private JLabel jlblKutID;
	private JTextField jtxtKutID;
	private JTextField jtxtSifre;
	private JTable kutuphaneTablo;
	private JScrollPane scrollPaneKutuphane;
	private JLabel lblKutuphane;

	VeritabaniFonksiyonlari fonk = new VeritabaniFonksiyonlari();

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
			    		rs.getString("kutuphane_email"), rs.getString("kutuphane_adres"), rs.getString("kutuphane_tel")};
			    kuTab.addRow(o);
			   
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
					OgrenciUyeOl frame = new OgrenciUyeOl();
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
	public OgrenciUyeOl() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(OgrenciUyeOl.class.getResource("/logo.png")));
		setTitle("\u00D6\u011Frenci - \u00DCye Ol");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel jlblAd = new JLabel("\u00D6\u011Frenci Ad\u0131:");
		jlblAd.setHorizontalAlignment(SwingConstants.TRAILING);
		jlblAd.setBounds(10, 85, 117, 19);
		contentPane.add(jlblAd);
		
		jtxtAd = new JTextField();
		jtxtAd.setColumns(10);
		jtxtAd.setBounds(137, 85, 108, 19);
		contentPane.add(jtxtAd);
		
		JLabel jlblEmail = new JLabel("\u00D6\u011Frenci Emaili:");
		jlblEmail.setHorizontalAlignment(SwingConstants.TRAILING);
		jlblEmail.setBounds(10, 111, 117, 19);
		contentPane.add(jlblEmail);
		
		jtxtEmail = new JTextField();
		jtxtEmail.setColumns(10);
		jtxtEmail.setBounds(137, 111, 108, 19);
		contentPane.add(jtxtEmail);
		
		JLabel jlblKadi = new JLabel("Kullan\u0131c\u0131 Ad\u0131:");
		jlblKadi.setHorizontalAlignment(SwingConstants.TRAILING);
		jlblKadi.setBounds(10, 140, 117, 19);
		contentPane.add(jlblKadi);
		
		jtxtKadi = new JTextField();
		jtxtKadi.setColumns(10);
		jtxtKadi.setBounds(137, 140, 108, 19);
		contentPane.add(jtxtKadi);
		
		JLabel jlblSifre = new JLabel("\u015Eifre:");
		jlblSifre.setHorizontalAlignment(SwingConstants.TRAILING);
		jlblSifre.setBounds(10, 169, 117, 19);
		contentPane.add(jlblSifre);
		
		btnUyeOl = new JButton("\u00DCye Ol");
		btnUyeOl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int secilenSatir= kutuphaneTablo.getSelectedRow();
				if(kutuphaneTablo.isRowSelected(secilenSatir)) {
					String ad = jtxtAd.getText();
					String email = jtxtEmail.getText();
					String kadi = jtxtKadi.getText();
					String sifre = jtxtSifre.getText();
					String kutID = jtxtKutID.getText();
					if((ad.equals("")||email.equals("")||kadi.equals("")||sifre.equals(""))) {
						JOptionPane.showMessageDialog(null, "Tüm Alanlarý Doldurunuz.", "SÝSTEM MESAJI", JOptionPane.OK_OPTION);
					}else {
						if(ogrenciKadiKontrol(kadi)<=0) {
							fonk.ogrenciEkle(ad, email, kadi, sifre, kutID);
							JOptionPane.showMessageDialog(null, "Baþarýyla Üye Olundu.", "SÝSTEM MESAJI", JOptionPane.OK_OPTION);
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
		btnUyeOl.setBounds(160, 218, 85, 21);
		contentPane.add(btnUyeOl);
		
		btnTemizle = new JButton("Temizle");
		btnTemizle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jtxtTemizle();
			}
		});
		btnTemizle.setBounds(65, 218, 85, 21);
		contentPane.add(btnTemizle);
		
		jlblKutID = new JLabel("K\u00FCt\u00FCphane ID:");
		jlblKutID.setHorizontalAlignment(SwingConstants.TRAILING);
		jlblKutID.setBounds(10, 56, 117, 19);
		contentPane.add(jlblKutID);
		
		jtxtKutID = new JTextField();
		jtxtKutID.setEnabled(false);
		jtxtKutID.setColumns(10);
		jtxtKutID.setBounds(137, 56, 108, 19);
		contentPane.add(jtxtKutID);
		
		jtxtSifre = new JTextField();
		jtxtSifre.setColumns(10);
		jtxtSifre.setBounds(137, 169, 108, 19);
		contentPane.add(jtxtSifre);
		
		scrollPaneKutuphane = new JScrollPane();
		scrollPaneKutuphane.setBounds(255, 54, 307, 183);
		contentPane.add(scrollPaneKutuphane);
		
		kutuphaneTablo = new JTable();
		kutuphaneTablo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int secilenSatir= kutuphaneTablo.getSelectedRow();
				DefaultTableModel kuTab = (DefaultTableModel)kutuphaneTablo.getModel();
				jtxtKutID.setText(kuTab.getValueAt(secilenSatir, 0).toString());
			}
		});
		kutuphaneTablo.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"K\u00FCt\u00FCphane ID", "Ad\u0131", "Email", "Adres", "Tel"
			}
		));
		scrollPaneKutuphane.setViewportView(kutuphaneTablo);
		
		lblKutuphane = new JLabel("\u00DCye Olunacak K\u00FCt\u00FCphaneyi Se\u00E7in:");
		lblKutuphane.setBounds(255, 33, 217, 13);
		contentPane.add(lblKutuphane);
	}

}
