import java.awt.EventQueue;
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
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Toolkit;

@SuppressWarnings("serial")
public class OgrenciPanel extends JFrame {

	private JPanel contentPane;
	private JTable kutuphaneTablo;
	private JTable kitapTablo;
	private JTable oduncKitaplarTablo;
	private JTextField jtxtKitapFiltre;
	private JTextField jtxtKutuphaneFiltre;
	private JTextField jtxtOduncKitaplarFiltre;
	private JTextField jtxtID;

	FiltrelemeFonksiyonu fil = new FiltrelemeFonksiyonu();
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
			    		rs.getString("kutuphane_adres")};
			    kuTab.addRow(o);
			   
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
			    		rs.getString("kitap_yazar"), rs.getString("kitap_yayimci"), rs.getString("kitap_adet")};
			    kiTab.addRow(o);
			   
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
	}
	
	public void oduncListele(String ogrID) {
		try {
			@SuppressWarnings("static-access")
			Statement st = fonk.conn.createStatement();
			String query = "SELECT * FROM kitap_durum WHERE ogrenci_id= '"+ogrID+"'";
			ResultSet rs = st.executeQuery(query);
			
			DefaultTableModel oduncTab = (DefaultTableModel)oduncKitaplarTablo.getModel();
			oduncTab.setRowCount(0);
			

			while (rs.next()) {
			    String o[] = {rs.getString("kitap_durum_id".toString()), rs.getString("kutuphane_id"), 
			    		rs.getString("kitap_id"),rs.getString("kitap_ad"), rs.getString("kitap_yazar"), rs.getString("alis_tarihi"),
			    		rs.getString("teslim_tarihi")};
			    oduncTab.addRow(o);
			   
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OgrenciPanel frame = new OgrenciPanel();
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
	public OgrenciPanel() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(OgrenciPanel.class.getResource("/logo.png")));
		setTitle("\u00D6\u011Frenci - Ana Sayfa");
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
				    OgrenciGirisPanel ogrenciGiris = new OgrenciGirisPanel();
				    ogrenciGiris.setVisible(true);
				} else {
				    
				}
			}
		});
		btnCikis.setBounds(668, 10, 108, 21);
		contentPane.add(btnCikis);
		
		JScrollPane scrollPaneKutuphane = new JScrollPane();
		scrollPaneKutuphane.setBounds(10, 119, 322, 175);
		contentPane.add(scrollPaneKutuphane);
		
		kutuphaneTablo = new JTable();
		kutuphaneTablo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int secilenSatir = kutuphaneTablo.getSelectedRow();
				DefaultTableModel kuTab = (DefaultTableModel)kutuphaneTablo.getModel();
				String kutID = kuTab.getValueAt(secilenSatir, 0).toString();
				kitapListele(kutID);
			}
		});
		kutuphaneTablo.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"K\u00FCt\u00FCphane ID", "Kutuphane Ad", "Kutuphane Adres"
			}
		));
		scrollPaneKutuphane.setViewportView(kutuphaneTablo);
		
		JScrollPane scrollPaneKitap = new JScrollPane();
		scrollPaneKitap.setBounds(342, 119, 434, 175);
		contentPane.add(scrollPaneKitap);
		
		kitapTablo = new JTable();
		kitapTablo.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Kitap ID", "Kitap Ad\u0131", "Kitap Yazar\u0131", "Kitap Yay\u0131mc\u0131", "Kitap Adedi"
			}
		));
		scrollPaneKitap.setViewportView(kitapTablo);
		
		JScrollPane scrollPaneOduncKitaplar = new JScrollPane();
		scrollPaneOduncKitaplar.setBounds(10, 399, 766, 154);
		contentPane.add(scrollPaneOduncKitaplar);
		
		oduncKitaplarTablo = new JTable();
		oduncKitaplarTablo.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"\u0130\u015Flem ID", "K\u00FCt\u00FCphane ID", "Kitap ID", "Kitap Ad\u0131", "Kitap Yazar\u0131", "Al\u0131\u015F Tarihi", "Teslim Tarihi"
			}
		));
		scrollPaneOduncKitaplar.setViewportView(oduncKitaplarTablo);
		
		jtxtKitapFiltre = new JTextField();
		jtxtKitapFiltre.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				fil.filtre(jtxtKitapFiltre.getText(), kitapTablo);
			}
		});
		jtxtKitapFiltre.setColumns(10);
		jtxtKitapFiltre.setBounds(342, 93, 434, 19);
		contentPane.add(jtxtKitapFiltre);
		
		
		
		jtxtKutuphaneFiltre = new JTextField();
		jtxtKutuphaneFiltre.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				fil.filtre(jtxtKutuphaneFiltre.getText(), kutuphaneTablo);
				
			}
		});
		jtxtKutuphaneFiltre.setColumns(10);
		jtxtKutuphaneFiltre.setBounds(10, 93, 322, 19);
		contentPane.add(jtxtKutuphaneFiltre);
		
		jtxtOduncKitaplarFiltre = new JTextField();
		jtxtOduncKitaplarFiltre.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				fil.filtre(jtxtOduncKitaplarFiltre.getText(), oduncKitaplarTablo);
			}
		});
		jtxtOduncKitaplarFiltre.setColumns(10);
		jtxtOduncKitaplarFiltre.setBounds(10, 370, 766, 19);
		contentPane.add(jtxtOduncKitaplarFiltre);
		
		JLabel lblKitapAra = new JLabel("Kitap Ara:");
		lblKitapAra.setBounds(342, 70, 78, 13);
		contentPane.add(lblKitapAra);
		
		JLabel jlblOduncKitaplar = new JLabel("\u00D6d\u00FCn\u00E7 Ald\u0131\u011F\u0131m Kitaplar:");
		jlblOduncKitaplar.setBounds(10, 347, 154, 13);
		contentPane.add(jlblOduncKitaplar);
		
		JButton btnKitapTalepEkle = new JButton("Kitap Talep Ekle");
		btnKitapTalepEkle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OgrenciTalepler taleplerim = new OgrenciTalepler();
				taleplerim.taleplerimiListele(jtxtID.getText());
				taleplerim.kutuphaneListele();
				taleplerim.setJtxtOgrID(jtxtID.getText());
				taleplerim.setVisible(true);
			}
		});
		btnKitapTalepEkle.setBounds(10, 10, 154, 21);
		contentPane.add(btnKitapTalepEkle);
		
		JButton btnBilgilerimiGncelle = new JButton("Bilgilerimi G\u00FCncelle");
		btnBilgilerimiGncelle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OgrenciBilgilerimiGuncelle bilgilerimiGuncelle = new OgrenciBilgilerimiGuncelle();
				bilgilerimiGuncelle.bilgilerimiListele(jtxtID.getText());
				bilgilerimiGuncelle.setOgrenciTablo();
				bilgilerimiGuncelle.setVisible(true);
			}
		});
		btnBilgilerimiGncelle.setBounds(174, 10, 158, 21);
		contentPane.add(btnBilgilerimiGncelle);
		
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
		
		JLabel jlblKutSec = new JLabel("K\u00FCt\u00FCphane Se\u00E7:");
		jlblKutSec.setBounds(10, 70, 129, 13);
		contentPane.add(jlblKutSec);
	}
	public JTextField setJtxtID(String id) {
		jtxtID.setText(id);
		return null;
	}
}
