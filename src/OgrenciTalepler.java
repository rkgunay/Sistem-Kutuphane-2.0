import java.awt.EventQueue;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Toolkit;

@SuppressWarnings("serial")
public class OgrenciTalepler extends JFrame {

	private JPanel contentPane;
	private JTextField jtxtKutuphaneID;
	private JTextField jtxtKitapAd;
	private JTextField jtxtKitapYazar;
	private JTextField jtxtKitapYayimci;
	private JButton btnEkle;
	private JButton btnGuncelle;
	private JButton btnSil;
	private JTextField jtxtKutuphaneFiltre;
	private JTable kutuphaneTablo;
	private JScrollPane scrollPaneKutuphane;
	private JTextField jtxtTalepFiltre;
	private JTable talepTablo;

	FiltrelemeFonksiyonu fil = new FiltrelemeFonksiyonu();
	VeritabaniFonksiyonlari fonk = new VeritabaniFonksiyonlari();
	private JLabel jlblKutID;
	private JTextField jtxtOgrID;
	
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
	
	public void taleplerimiListele(String ogrID){
		try {
			@SuppressWarnings("static-access")
			Statement st = fonk.conn.createStatement();
			String query = "SELECT * FROM kitap_talep WHERE ogrenci_id= '"+ogrID+"'";
			ResultSet rs = st.executeQuery(query);
			
			DefaultTableModel talTab = (DefaultTableModel)talepTablo.getModel();
			talTab.setRowCount(0);
			

			while (rs.next()) {
			    String o[] = {rs.getString("kitap_talep_id".toString()), rs.getString("kutuphane_id"), rs.getString("ogrenci_id"), 
			    		rs.getString("kitap_ad"), rs.getString("kitap_yazar"), rs.getString("kitap_yayimci")};
			    talTab.addRow(o);
			   
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
	}
	
	public void jtxtTemizle() {
		jtxtKitapAd.setText("");
		jtxtKitapYazar.setText("");
		jtxtKitapYayimci.setText("");
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OgrenciTalepler frame = new OgrenciTalepler();
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
	public OgrenciTalepler() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(OgrenciTalepler.class.getResource("/logo.png")));
		setTitle("\u00D6\u011Frenci - Talepler");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 700, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 10, 666, 393);
		contentPane.add(panel);
		panel.setLayout(null);
		
		jtxtKutuphaneID = new JTextField();
		jtxtKutuphaneID.setEnabled(false);
		jtxtKutuphaneID.setColumns(10);
		jtxtKutuphaneID.setBounds(102, 10, 108, 19);
		panel.add(jtxtKutuphaneID);
		
		JLabel jlblKitapAd = new JLabel("Kitap Ad\u0131:");
		jlblKitapAd.setHorizontalAlignment(SwingConstants.TRAILING);
		jlblKitapAd.setBounds(10, 39, 82, 19);
		panel.add(jlblKitapAd);
		
		jtxtKitapAd = new JTextField();
		jtxtKitapAd.setColumns(10);
		jtxtKitapAd.setBounds(102, 39, 108, 19);
		panel.add(jtxtKitapAd);
		
		jtxtKitapYazar = new JTextField();
		jtxtKitapYazar.setColumns(10);
		jtxtKitapYazar.setBounds(102, 65, 108, 19);
		panel.add(jtxtKitapYazar);
		
		JLabel jlblKitapYazar = new JLabel("Kitap Yazar\u0131:");
		jlblKitapYazar.setHorizontalAlignment(SwingConstants.TRAILING);
		jlblKitapYazar.setBounds(10, 65, 82, 19);
		panel.add(jlblKitapYazar);
		
		JLabel jlblKitapYayimci = new JLabel("Kitap Yay\u0131mc\u0131s\u0131:");
		jlblKitapYayimci.setHorizontalAlignment(SwingConstants.TRAILING);
		jlblKitapYayimci.setBounds(0, 94, 92, 19);
		panel.add(jlblKitapYayimci);
		
		jtxtKitapYayimci = new JTextField();
		jtxtKitapYayimci.setColumns(10);
		jtxtKitapYayimci.setBounds(102, 94, 108, 19);
		panel.add(jtxtKitapYayimci);
		
		btnEkle = new JButton("Ekle");
		btnEkle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String ad = jtxtKitapAd.getText();
				String yazar = jtxtKitapYazar.getText();
				String yayimci = jtxtKitapYayimci.getText();
				String ogrID = jtxtOgrID.getText();
				int secilenSatir= kutuphaneTablo.getSelectedRow();
				
				if(kutuphaneTablo.isRowSelected(secilenSatir)) {
					String kutID = jtxtKutuphaneID.getText();
					if((ad.equals("")||yazar.equals("")||yayimci.equals(""))){
						JOptionPane.showMessageDialog(null, "Tüm Alanlarý Doldurunuz.", "SÝSTEM MESAJI", JOptionPane.OK_OPTION);
					}else {
							fonk.talepEkle(kutID, ogrID, ad, yazar, yayimci);
							taleplerimiListele(ogrID);
							jtxtTemizle();
					}
				}else {
					JOptionPane.showMessageDialog(null, "Talebi Ekleyeceðiniz Kütüphaneyi Seçiniz!", "SÝSTEM MESAJI", JOptionPane.OK_OPTION);
				}
			}
		});
		btnEkle.setBounds(32, 141, 82, 21);
		panel.add(btnEkle);
		
		btnGuncelle = new JButton("G\u00FCncelle");
		btnGuncelle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String ad = jtxtKitapAd.getText();
				String yazar = jtxtKitapYazar.getText();
				String yayimci = jtxtKitapYayimci.getText();
				String ogrID = jtxtOgrID.getText();
				String kutID = jtxtKutuphaneID.getText();
				
				int secilenSatir= talepTablo.getSelectedRow();
				DefaultTableModel talTab = (DefaultTableModel)talepTablo.getModel();
				
				if(secilenSatir==-1){
		            if(talTab.getRowCount()==0){
		            	JOptionPane.showMessageDialog(null, "Liste boþ!", "SÝSTEM MESAJI", JOptionPane.OK_OPTION);
		            }else { JOptionPane.showMessageDialog(null, "Seçim yapýnýz!", "SÝSTEM MESAJI", JOptionPane.OK_OPTION);}
		        }else {	
		        	if((ad.equals("")||yazar.equals("")||yayimci.equals(""))){
						JOptionPane.showMessageDialog(null, "Tüm Alanlarý Doldurunuz.", "SÝSTEM MESAJI", JOptionPane.OK_OPTION);
					}else {
						int id = Integer.parseInt(talTab.getValueAt(secilenSatir, 0).toString());
						fonk.talepGuncelle(id, kutID, ogrID, ad, yazar, yayimci);
						taleplerimiListele(ogrID);
						jtxtTemizle();
						JOptionPane.showMessageDialog(null, "Güncelleme Baþarýlý", "SÝSTEM MESAJI", JOptionPane.OK_OPTION);
					}
		        }
			}
		});
		btnGuncelle.setBounds(125, 141, 85, 21);
		panel.add(btnGuncelle);
		
		btnSil = new JButton("Sil");
		btnSil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int secilenSatir= talepTablo.getSelectedRow();
				DefaultTableModel talTab = (DefaultTableModel)talepTablo.getModel();
				String ogrID = jtxtOgrID.getText();
				if(secilenSatir==-1){
		            if(talTab.getRowCount()==0){
		            	JOptionPane.showMessageDialog(null, "Liste boþ!", "SÝSTEM MESAJI", JOptionPane.OK_OPTION);
		            }else { JOptionPane.showMessageDialog(null, "Seçim yapýnýz!", "SÝSTEM MESAJI", JOptionPane.OK_OPTION);}
		        }else {
		        	int id = Integer.parseInt(talTab.getValueAt(secilenSatir, 0).toString());
		        	fonk.talepSil(id);
		        	taleplerimiListele(ogrID);
		        	jtxtTemizle();
		        	JOptionPane.showMessageDialog(null, "Silme Baþarýlý", "SÝSTEM MESAJI", JOptionPane.OK_OPTION);
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
		jtxtKutuphaneFiltre.setBounds(230, 10, 426, 19);
		panel.add(jtxtKutuphaneFiltre);
		
		scrollPaneKutuphane = new JScrollPane();
		scrollPaneKutuphane.setBounds(230, 39, 426, 154);
		panel.add(scrollPaneKutuphane);
		
		kutuphaneTablo = new JTable();
		kutuphaneTablo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int secilenSatir = kutuphaneTablo.getSelectedRow();
				DefaultTableModel kuTab = (DefaultTableModel)kutuphaneTablo.getModel();
				jtxtKutuphaneID.setText(kuTab.getValueAt(secilenSatir, 0).toString());
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
		
		JScrollPane scrollPaneTalep = new JScrollPane();
		scrollPaneTalep.setBounds(10, 261, 646, 122);
		panel.add(scrollPaneTalep);
		
		talepTablo = new JTable();
		talepTablo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int secilenSatir= talepTablo.getSelectedRow();
				DefaultTableModel talTab = (DefaultTableModel)talepTablo.getModel();
				jtxtKutuphaneID.setText(talTab.getValueAt(secilenSatir, 1).toString());
				jtxtKitapAd.setText(talTab.getValueAt(secilenSatir, 3).toString());
				jtxtKitapYazar.setText(talTab.getValueAt(secilenSatir, 4).toString());
				jtxtKitapYayimci.setText(talTab.getValueAt(secilenSatir, 5).toString());
			}
		});
		talepTablo.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Talep ID", "Kutuphane ID", "Ogrenci ID", "Kitap Ad\u0131", "Kitap Yazar\u0131", "Kitap Yay\u0131mc\u0131s\u0131"
			}
		));
		scrollPaneTalep.setViewportView(talepTablo);
		
		jtxtTalepFiltre = new JTextField();
		jtxtTalepFiltre.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				fil.filtre(jtxtTalepFiltre.getText(), talepTablo);
			}
		});
		jtxtTalepFiltre.setColumns(10);
		jtxtTalepFiltre.setBounds(10, 232, 646, 19);
		panel.add(jtxtTalepFiltre);
		
		JLabel jlblTaleplerim = new JLabel("Taleplerim");
		jlblTaleplerim.setHorizontalAlignment(SwingConstants.LEFT);
		jlblTaleplerim.setBounds(10, 203, 92, 19);
		panel.add(jlblTaleplerim);
		
		jlblKutID = new JLabel("K\u00FCt\u00FCphane ID:");
		jlblKutID.setHorizontalAlignment(SwingConstants.TRAILING);
		jlblKutID.setBounds(0, 10, 92, 19);
		panel.add(jlblKutID);
		
		jtxtOgrID = new JTextField();
		jtxtOgrID.setEnabled(false);
		jtxtOgrID.setEditable(false);
		jtxtOgrID.setVisible(false);
		jtxtOgrID.setBounds(636, 203, 20, 19);
		panel.add(jtxtOgrID);
		jtxtOgrID.setColumns(10);
	}
	public JTextField setJtxtOgrID(String ogrID) {
		jtxtOgrID.setText(ogrID);
		return null;
	}
}
