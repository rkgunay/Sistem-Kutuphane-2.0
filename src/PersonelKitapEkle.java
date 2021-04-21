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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Toolkit;

@SuppressWarnings("serial")
public class PersonelKitapEkle extends JFrame {

	private JPanel contentPane;
	private JTextField jtxtAd;
	private JTextField jtxtKitapFiltre;
	private JTextField jtxtAdet;
	private JTable kitapTablo;
	private JLabel jlblYayimci;
	private JTextField jtxtYayimci;
	private JButton btnEkle;
	private JButton btnGuncelle;
	private JButton btnSil;
	private JTextField jtxtYazar;
	private static JTextField jtxtGizliKutID;

	FiltrelemeFonksiyonu fil = new FiltrelemeFonksiyonu();
	VeritabaniFonksiyonlari fonk = new VeritabaniFonksiyonlari();
	
	/**
	 * Launch the application.
	 */
	

	public void jtxtTemizle() {
		jtxtAd.setText("");
		jtxtYazar.setText("");
		jtxtYayimci.setText("");
		jtxtAdet.setText("");
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
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PersonelKitapEkle frame = new PersonelKitapEkle();
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
	public PersonelKitapEkle() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(PersonelKitapEkle.class.getResource("/logo.png")));
		setTitle("Personel - Kitap Ekle");
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
		
		JLabel jlblAd = new JLabel("Kitap Ad\u0131:");
		jlblAd.setHorizontalAlignment(SwingConstants.TRAILING);
		jlblAd.setBounds(10, 10, 141, 19);
		panel.add(jlblAd);
		
		jtxtAd = new JTextField();
		jtxtAd.setColumns(10);
		jtxtAd.setBounds(161, 10, 108, 19);
		panel.add(jtxtAd);
		
		jtxtYazar = new JTextField();
		jtxtYazar.setColumns(10);
		jtxtYazar.setBounds(161, 36, 108, 19);
		panel.add(jtxtYazar);
		
		JLabel jlblYazar = new JLabel("Kitap Yazar\u0131:");
		jlblYazar.setHorizontalAlignment(SwingConstants.TRAILING);
		jlblYazar.setBounds(10, 36, 141, 19);
		panel.add(jlblYazar);
		
		JScrollPane scrollPaneKitap = new JScrollPane();
		scrollPaneKitap.setBounds(10, 170, 546, 163);
		panel.add(scrollPaneKitap);
		
		kitapTablo = new JTable();
		kitapTablo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int secilenSatir= kitapTablo.getSelectedRow();
				DefaultTableModel kiTab = (DefaultTableModel)kitapTablo.getModel();
				jtxtAd.setText(kiTab.getValueAt(secilenSatir, 1).toString());
				jtxtYazar.setText(kiTab.getValueAt(secilenSatir, 2).toString());
				jtxtYayimci.setText(kiTab.getValueAt(secilenSatir, 3).toString());
				jtxtAdet.setText(kiTab.getValueAt(secilenSatir, 4).toString());
			}
		});
		scrollPaneKitap.setViewportView(kitapTablo);
		
		kitapTablo.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Kitap ID", "Kitap Ad\u0131", "Kitap Yazar\u0131", "Kitap Yay\u0131mc\u0131s\u0131", "Kitap Adedi"
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
		jtxtKitapFiltre.setBounds(10, 141, 546, 19);
		panel.add(jtxtKitapFiltre);
		
		JLabel jlblAdet = new JLabel("Kitap Adedi:");
		jlblAdet.setHorizontalAlignment(SwingConstants.TRAILING);
		jlblAdet.setBounds(10, 94, 141, 19);
		panel.add(jlblAdet);
		
		
		jtxtAdet = new JTextField();
		jtxtAdet.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				if (!Character.isDigit(evt.getKeyChar())) {
                    evt.consume();
                    jtxtAdet.setToolTipText("Sadece Say\u0131 Girebilirsiniz");
                }else if(Character.isDigit(evt.getKeyChar())){
                	jtxtAdet.setToolTipText(null);
                }
			}
		});
		jtxtAdet.setColumns(10);
		jtxtAdet.setBounds(161, 94, 108, 19);
		panel.add(jtxtAdet);
		
		jlblYayimci = new JLabel("Kitap Yay\u0131mc\u0131s\u0131:");
		jlblYayimci.setHorizontalAlignment(SwingConstants.TRAILING);
		jlblYayimci.setBounds(10, 65, 141, 19);
		panel.add(jlblYayimci);
		
		jtxtYayimci = new JTextField();
		jtxtYayimci.setColumns(10);
		jtxtYayimci.setBounds(161, 65, 108, 19);
		panel.add(jtxtYayimci);
		
		btnEkle = new JButton("Ekle");
		btnEkle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String kutID = jtxtGizliKutID.getText();
				String ad = jtxtAd.getText();
				String yazar = jtxtYazar.getText();
				String yayimci = jtxtYayimci.getText();
				String adet = jtxtAdet.getText();
				if((ad.equals("")||ad.equals("")||yazar.equals("")||yayimci.equals(""))) {
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
				
			}
		});
		btnEkle.setBounds(314, 36, 85, 21);
		panel.add(btnEkle);
		
		btnGuncelle = new JButton("G\u00FCncelle");
		btnGuncelle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String kutID = jtxtGizliKutID.getText();
				String ad = jtxtAd.getText();
				String yazar = jtxtYazar.getText();
				String yayimci = jtxtYayimci.getText();
				String adet = jtxtAdet.getText();
				
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
		btnGuncelle.setBounds(314, 65, 85, 21);
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
		        	String kutID = jtxtGizliKutID.getText();
		        	int adet = Integer.parseInt(kiTab.getValueAt(secilenSatir, 4).toString())-1;
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
		btnSil.setBounds(314, 94, 85, 21);
		panel.add(btnSil);
		
		jtxtGizliKutID = new JTextField();
		jtxtGizliKutID.setVisible(false);
		jtxtGizliKutID.setEnabled(false);
		jtxtGizliKutID.setEditable(false);
		jtxtGizliKutID.setColumns(10);
		jtxtGizliKutID.setBounds(549, 10, 7, 19);
		panel.add(jtxtGizliKutID);
	}
	public JTextField setJtxtGizliKutID(String kutID) {
		 jtxtGizliKutID.setText(kutID);
		 return null;
	}
}
