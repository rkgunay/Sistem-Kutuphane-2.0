import java.awt.EventQueue;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

@SuppressWarnings("serial")
public class YoneticiTalepler extends JFrame {

	private JPanel contentPane;
	private JTextField jtxtKutuphaneID;
	private JTextField jtxtKitapAd;
	private JTextField jtxtKitapYazar;
	private JTextField jtxtKitapFiltre;
	private JTextField jtxtKitapYayimci;
	private JTable kitapTablo;
	private JLabel jlblKitapAdet;
	private JTextField jtxtKitapAdet;
	private JButton btnEkle;
	private JButton btnSil;
	private JTextField jtxtKutuphaneFiltre;
	private JTable kutuphaneTablo;
	private JScrollPane scrollPaneKutuphane;
	private JTextField jtxtTalepFiltre;
	private JTable talepTablo;

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
	
	public void kitapListele(String kutuphaneID){
		try {
			@SuppressWarnings("static-access")
			Statement st = fonk.conn.createStatement();
			String query = "SELECT * FROM kitap WHERE kutuphane_id= '"+kutuphaneID+"'";
			ResultSet rs = st.executeQuery(query);
			
			DefaultTableModel kiTab = (DefaultTableModel)kitapTablo.getModel();
			kiTab.setRowCount(0);
			

			while (rs.next()) {
			    String o[] = {rs.getString("kitap_id".toString()), rs.getString("kutuphane_id"), rs.getString("kitap_ad"), 
			    		rs.getString("kitap_yazar"), rs.getString("kitap_yayimci"), rs.getString("kitap_adet")};
			    kiTab.addRow(o);
			   
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
	}
	
	public void talepListele(String kutuphaneID){
		try {
			@SuppressWarnings("static-access")
			Statement st = fonk.conn.createStatement();
			String query = "SELECT * FROM kitap_talep WHERE kutuphane_id= '"+kutuphaneID+"'";
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
		jtxtKitapAdet.setText("");
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					YoneticiTalepler frame = new YoneticiTalepler();
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
	public YoneticiTalepler() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(YoneticiTalepler.class.getResource("/logo.png")));
		setTitle("Y\u00F6netici - Talepler");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 700, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 10, 666, 543);
		contentPane.add(panel);
		panel.setLayout(null);
		
		jtxtKutuphaneID = new JTextField();
		jtxtKutuphaneID.setEnabled(false);
		jtxtKutuphaneID.setColumns(10);
		jtxtKutuphaneID.setBounds(230, 10, 94, 19);
		panel.add(jtxtKutuphaneID);
		
		JLabel jlblKitapAd = new JLabel("Kitap Ad\u0131:");
		jlblKitapAd.setHorizontalAlignment(SwingConstants.TRAILING);
		jlblKitapAd.setBounds(10, 10, 82, 19);
		panel.add(jlblKitapAd);
		
		jtxtKitapAd = new JTextField();
		jtxtKitapAd.setColumns(10);
		jtxtKitapAd.setBounds(102, 10, 108, 19);
		panel.add(jtxtKitapAd);
		
		jtxtKitapYazar = new JTextField();
		jtxtKitapYazar.setColumns(10);
		jtxtKitapYazar.setBounds(102, 36, 108, 19);
		panel.add(jtxtKitapYazar);
		
		JLabel jlblKitapYazar = new JLabel("Kitap Yazar\u0131:");
		jlblKitapYazar.setHorizontalAlignment(SwingConstants.TRAILING);
		jlblKitapYazar.setBounds(10, 36, 82, 19);
		panel.add(jlblKitapYazar);
		
		JScrollPane scrollPaneKitap = new JScrollPane();
		scrollPaneKitap.setBounds(10, 402, 646, 131);
		panel.add(scrollPaneKitap);
		
		kitapTablo = new JTable();
		scrollPaneKitap.setViewportView(kitapTablo);
		
		kitapTablo.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Kitap ID", "K\u00FCt\u00FCphane ID", "Kitap Ad\u0131", "Kitap Yazar\u0131", "Yay\u0131mc\u0131s\u0131", "Kitap Adedi"
			}
		));
		
		jtxtKitapFiltre = new JTextField();
		jtxtKitapFiltre.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				fil.filtre(jtxtKitapFiltre.getText(), kitapTablo);
			}
		});
		jtxtKitapFiltre.setColumns(10);
		jtxtKitapFiltre.setBounds(10, 373, 646, 19);
		panel.add(jtxtKitapFiltre);
		
		JLabel jlblKitapYayimci = new JLabel("Kitap Yay\u0131mc\u0131s\u0131:");
		jlblKitapYayimci.setHorizontalAlignment(SwingConstants.TRAILING);
		jlblKitapYayimci.setBounds(0, 91, 92, 19);
		panel.add(jlblKitapYayimci);
		
		jtxtKitapYayimci = new JTextField();
		jtxtKitapYayimci.setColumns(10);
		jtxtKitapYayimci.setBounds(102, 91, 108, 19);
		panel.add(jtxtKitapYayimci);
		
		jlblKitapAdet = new JLabel("Kitap Adedi:");
		jlblKitapAdet.setHorizontalAlignment(SwingConstants.TRAILING);
		jlblKitapAdet.setBounds(10, 62, 82, 19);
		panel.add(jlblKitapAdet);
		
		jtxtKitapAdet = new JTextField();
		jtxtKitapAdet.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				if (!Character.isDigit(evt.getKeyChar())) {
                    evt.consume();
                    jtxtKitapAdet.setToolTipText("Sadece Say\u0131 Girebilirsiniz");
                }else if(Character.isDigit(evt.getKeyChar())){
                	jtxtKitapAdet.setToolTipText(null);
                }
			}
		});
		jtxtKitapAdet.setColumns(10);
		jtxtKitapAdet.setBounds(102, 62, 108, 19);
		panel.add(jtxtKitapAdet);
		
		btnEkle = new JButton("Kitaplara Ekle");
		btnEkle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String kutID = jtxtKutuphaneID.getText();
				String ad = jtxtKitapAd.getText();
				String yazar = jtxtKitapYazar.getText();
				String yayimci = jtxtKitapYayimci.getText();
				String adet = jtxtKitapAdet.getText();
				
				int secilenSatir= kutuphaneTablo.getSelectedRow();
				DefaultTableModel talTab = (DefaultTableModel)talepTablo.getModel();
				int secilenSatirTalep = talepTablo.getSelectedRow();
				if(kutuphaneTablo.isRowSelected(secilenSatir)) {
					if(talepTablo.isRowSelected(secilenSatirTalep)) {
						if(!adet.equals("")) {
							int adetSayi = Integer.parseInt(adet);
							if(adetSayi==0) {
								JOptionPane.showMessageDialog(null, "Kitap Adedi 0 Olamaz!", "SÝSTEM MESAJI", JOptionPane.OK_OPTION);
							}else{
								int id = Integer.parseInt(talTab.getValueAt(secilenSatirTalep, 0).toString());
								fonk.talepSil(id);
								fonk.kitapEkle(kutID, ad, yazar, yayimci, adet);
								kitapListele(kutID);
								talepListele(kutID);
								jtxtTemizle();
							}						
						}else {
							JOptionPane.showMessageDialog(null, "Kitap Adedi Giriniz!", "SÝSTEM MESAJI", JOptionPane.OK_OPTION);
						}
					}else {
						JOptionPane.showMessageDialog(null, "Talep Seçimi Yapýnýz!", "SÝSTEM MESAJI", JOptionPane.OK_OPTION);
					}
				}else {
					JOptionPane.showMessageDialog(null, "Kitabý Ekleyeceðiniz Kütüphaneyi Seçiniz!", "SÝSTEM MESAJI", JOptionPane.OK_OPTION);
				}
			}
		});
		btnEkle.setBounds(30, 141, 180, 21);
		panel.add(btnEkle);
		
		btnSil = new JButton("Talebi Sil");
		btnSil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int secilenSatir= talepTablo.getSelectedRow();
				DefaultTableModel talTab = (DefaultTableModel)talepTablo.getModel();
				
				if(secilenSatir==-1){
		            if(talTab.getRowCount()==0){
		            	JOptionPane.showMessageDialog(null, "Liste boþ!", "SÝSTEM MESAJI", JOptionPane.OK_OPTION);
		            }else { JOptionPane.showMessageDialog(null, "Seçim yapýnýz!", "SÝSTEM MESAJI", JOptionPane.OK_OPTION);}
		        }else {
		        	int id = Integer.parseInt(talTab.getValueAt(secilenSatir, 0).toString());
		        	String kutID = talTab.getValueAt(secilenSatir, 1).toString();
		        	fonk.talepSil(id);
		        	talepListele(kutID);
		        }
			}
		});
		btnSil.setBounds(30, 172, 180, 21);
		panel.add(btnSil);
		
		jtxtKutuphaneFiltre = new JTextField();
		jtxtKutuphaneFiltre.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				fil.filtre(jtxtKutuphaneFiltre.getText(), kutuphaneTablo);
			}
		});
		jtxtKutuphaneFiltre.setColumns(10);
		jtxtKutuphaneFiltre.setBounds(334, 10, 322, 19);
		panel.add(jtxtKutuphaneFiltre);
		
		scrollPaneKutuphane = new JScrollPane();
		scrollPaneKutuphane.setBounds(230, 39, 426, 124);
		panel.add(scrollPaneKutuphane);
		
		kutuphaneTablo = new JTable();
		kutuphaneTablo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int secilenSatir = kutuphaneTablo.getSelectedRow();
				DefaultTableModel kuTab = (DefaultTableModel)kutuphaneTablo.getModel();
				String kutID = kuTab.getValueAt(secilenSatir, 0).toString();
				jtxtKutuphaneID.setText(kutID);
				kitapListele(kutID);
			    talepListele(kutID);
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
		scrollPaneTalep.setBounds(10, 203, 646, 160);
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
		jtxtTalepFiltre.setBounds(230, 173, 426, 19);
		panel.add(jtxtTalepFiltre);
	}
}
