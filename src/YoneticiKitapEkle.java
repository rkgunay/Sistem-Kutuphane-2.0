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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Toolkit;

@SuppressWarnings("serial")
public class YoneticiKitapEkle extends JFrame {

	private JPanel contentPane;
	private JTextField jtxtKutuphaneID;
	private JTextField jtxtKitapAd;
	private JTextField jtxtKitapYazar;
	private JTextField jtxtKitapFiltre;
	private JTextField jtxtKitapYayimci;
	private JTable kitapTablo;
	private JLabel jlblKitapAdet;
	private JButton btnEkle;
	private JButton btnGuncelle;
	private JButton btnSil;
	private JTextField jtxtKutuphaneFiltre;
	private JTable kutuphaneTablo;
	private JScrollPane scrollPaneKutuphane;
	private JTextField jtxtKitapAdet;
	
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
					YoneticiKitapEkle frame = new YoneticiKitapEkle();
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
	
	public YoneticiKitapEkle() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(YoneticiKitapEkle.class.getResource("/logo.png")));
		setTitle("Y\u00F6netici - Kitap Ekle");
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
		jtxtKutuphaneID.setHorizontalAlignment(SwingConstants.CENTER);
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
		scrollPaneKitap.setBounds(10, 202, 546, 131);
		panel.add(scrollPaneKitap);
		
		kitapTablo = new JTable();
		kitapTablo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int secilenSatir= kitapTablo.getSelectedRow();
				DefaultTableModel kiTab = (DefaultTableModel)kitapTablo.getModel();
				jtxtKutuphaneID.setText(kiTab.getValueAt(secilenSatir, 1).toString());
				jtxtKitapAd.setText(kiTab.getValueAt(secilenSatir, 2).toString());
				jtxtKitapYazar.setText(kiTab.getValueAt(secilenSatir, 3).toString());
				jtxtKitapYayimci.setText(kiTab.getValueAt(secilenSatir, 4).toString());
				jtxtKitapAdet.setText(kiTab.getValueAt(secilenSatir, 5).toString());
				
			}
		});
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
		jtxtKitapFiltre.setBounds(230, 173, 326, 19);
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
		
		btnEkle = new JButton("Ekle");
		btnEkle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String kutID = jtxtKutuphaneID.getText();
				String ad = jtxtKitapAd.getText();
				String yazar = jtxtKitapYazar.getText();
				String yayimci = jtxtKitapYayimci.getText();
				String adet = jtxtKitapAdet.getText();
				
				int secilenSatir= kutuphaneTablo.getSelectedRow();
				if(kutuphaneTablo.isRowSelected(secilenSatir)) {
					if((ad.equals("")||yazar.equals("")||yayimci.equals(""))) {
						JOptionPane.showMessageDialog(null, "Tüm Alanlarý Doldurunuz.", "SÝSTEM MESAJI", JOptionPane.OK_OPTION);
					}else {
						if(!adet.equals("")) {
							int adetSayi = Integer.parseInt(adet);
							if(adetSayi==0) {
								JOptionPane.showMessageDialog(null, "Kitap Adedi 0 Olamaz!", "SÝSTEM MESAJI", JOptionPane.OK_OPTION);
							}else{
								fonk.kitapEkle(kutID, ad, yazar, yayimci, adet);
								kitapListele(kutID);
								jtxtTemizle();
							}						
						}else {
							JOptionPane.showMessageDialog(null, "Kitap Adedi Giriniz!", "SÝSTEM MESAJI", JOptionPane.OK_OPTION);
						}
					}
				}else {
					JOptionPane.showMessageDialog(null, "Kitabý Ekleyeceðiniz Kütüphaneyi Seçiniz!", "SÝSTEM MESAJI", JOptionPane.OK_OPTION);
				}
				
				
			}
		});
		btnEkle.setBounds(32, 141, 82, 21);
		panel.add(btnEkle);
		
		btnGuncelle = new JButton("G\u00FCncelle");
		btnGuncelle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String kutID = jtxtKutuphaneID.getText();
				String ad = jtxtKitapAd.getText();
				String yazar = jtxtKitapYazar.getText();
				String yayimci = jtxtKitapYayimci.getText();
				String adet = jtxtKitapAdet.getText();
				
				int secilenSatir= kitapTablo.getSelectedRow();
				DefaultTableModel kiTab = (DefaultTableModel)kitapTablo.getModel();
				if(secilenSatir==-1){
			            if(kiTab.getRowCount()==0){
			            	JOptionPane.showMessageDialog(null, "Liste boþ!", "SÝSTEM MESAJI", JOptionPane.OK_OPTION);
			            }else { JOptionPane.showMessageDialog(null, "Seçim yapýnýz!", "SÝSTEM MESAJI", JOptionPane.OK_OPTION);}
			        }else {
			        	if(!adet.equals("")) {
			        		int adetSayi = Integer.parseInt(adet);
			        		if(adetSayi==0) {
			        			JOptionPane.showMessageDialog(null, "Kitap Adedi 0 Olamaz!", "SÝSTEM MESAJI", JOptionPane.OK_OPTION);
			        		}else {
			        			
			        			int id = Integer.parseInt(kiTab.getValueAt(secilenSatir, 0).toString());
			        			fonk.kitapGuncelle(id, kutID, ad, yazar, yayimci, adet);
			        			JOptionPane.showMessageDialog(null, "Güncelleme Baþarýlý", "SÝSTEM MESAJI", JOptionPane.OK_OPTION);
			        			kitapListele(kutID);
			        			jtxtTemizle();						
			        		}			        		
			        	}else {
			        		JOptionPane.showMessageDialog(null, "Kitap Adedi Giriniz!", "SÝSTEM MESAJI", JOptionPane.OK_OPTION);
			        	}
			        }
				
			}
		});
		btnGuncelle.setBounds(125, 141, 85, 21);
		panel.add(btnGuncelle);
		
		btnSil = new JButton("Sil");
		btnSil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int secilenSatir= kitapTablo.getSelectedRow();
				DefaultTableModel kiTab = (DefaultTableModel)kitapTablo.getModel();
				
				if(secilenSatir==-1){
		            if(kiTab.getRowCount()==0){
		            	JOptionPane.showMessageDialog(null, "Liste boþ!", "SÝSTEM MESAJI", JOptionPane.OK_OPTION);
		            }else { JOptionPane.showMessageDialog(null, "Seçim yapýnýz!", "SÝSTEM MESAJI", JOptionPane.OK_OPTION);}
		        }else {
		        	int id = Integer.parseInt(kiTab.getValueAt(secilenSatir, 0).toString());
		        	String kutID = kiTab.getValueAt(secilenSatir, 1).toString();
		        	int adet = Integer.parseInt(kiTab.getValueAt(secilenSatir, 5).toString())-1;
		        	if(adet>=1) {
		        		fonk.kitapAdetGuncelle(id, String.valueOf(adet));
		        	}else if(adet==0) {
		        		fonk.kitapSil(id);
		        	}
		        	JOptionPane.showMessageDialog(null, "Silme Baþarýlý", "SÝSTEM MESAJI", JOptionPane.OK_OPTION);
		        	kitapListele(kutID);
		        	
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
				kitapListele(kutID);
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
	}
}
