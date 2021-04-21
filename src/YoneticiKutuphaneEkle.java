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
public class YoneticiKutuphaneEkle extends JFrame {

	private JPanel contentPane;
	private JTextField jtxtAd;
	private JTextField jtxtEmail;
	private JTextField jtxtKutuphaneFiltre;
	private JTextField jtxtTel;
	private JTable kutuphaneTablo;
	private JLabel jlblAdres;
	private JTextField jtxtAdres;
	private JButton btnEkle;
	private JButton btnGuncelle;
	private JButton btnSil;
	private JButton btnTemizle;

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
			    		rs.getString("kutuphane_email"), rs.getString("kutuphane_adres"), rs.getString("kutuphane_tel"),};
			    kuTab.addRow(o);
			   
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void jtxtTemizle() {
		jtxtAd.setText("");
		jtxtEmail.setText("");
		jtxtTel.setText("");
		jtxtAdres.setText("");
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					YoneticiKutuphaneEkle frame = new YoneticiKutuphaneEkle();
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
	public YoneticiKutuphaneEkle() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(YoneticiKutuphaneEkle.class.getResource("/logo.png")));
		setTitle("Y\u00F6netici - K\u00FCt\u00FCphane Ekle");
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
		
		JLabel jlblAd = new JLabel("K\u00FCt\u00FCphane Ad\u0131:");
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
		
		JLabel jlblEmail = new JLabel("K\u00FCt\u00FCphane Email:");
		jlblEmail.setHorizontalAlignment(SwingConstants.TRAILING);
		jlblEmail.setBounds(10, 36, 141, 19);
		panel.add(jlblEmail);
		
		JScrollPane scrollPaneKutuphane = new JScrollPane();
		scrollPaneKutuphane.setBounds(10, 170, 546, 163);
		panel.add(scrollPaneKutuphane);
		
		kutuphaneTablo = new JTable();
		kutuphaneTablo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int secilenSatir= kutuphaneTablo.getSelectedRow();
				DefaultTableModel kuTab = (DefaultTableModel)kutuphaneTablo.getModel();
				
				
				jtxtAd.setText(kuTab.getValueAt(secilenSatir,1).toString());
				jtxtEmail.setText(kuTab.getValueAt(secilenSatir,2).toString());
				jtxtAdres.setText(kuTab.getValueAt(secilenSatir,3).toString());
				jtxtTel.setText(kuTab.getValueAt(secilenSatir,4).toString());
			}
		});
		scrollPaneKutuphane.setViewportView(kutuphaneTablo);
		
		kutuphaneTablo.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"K\u00FCt\u00FCphane ID", "K\u00FCt\u00FCphane Ad\u0131", "K\u00FCt\u00FCphane Email", "K\u00FCt\u00FCphane Adres", "K\u00FCt\u00FCphane Tel"
			}
		));
		
		jtxtKutuphaneFiltre = new JTextField();
		jtxtKutuphaneFiltre.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				fil.filtre(jtxtKutuphaneFiltre.getText(), kutuphaneTablo);
			}
		});
		jtxtKutuphaneFiltre.setColumns(10);
		jtxtKutuphaneFiltre.setBounds(10, 141, 546, 19);
		panel.add(jtxtKutuphaneFiltre);
		
		JLabel jlblTel = new JLabel("K\u00FCt\u00FCphane Tel:");
		jlblTel.setHorizontalAlignment(SwingConstants.TRAILING);
		jlblTel.setBounds(10, 94, 141, 19);
		panel.add(jlblTel);
		
		jtxtTel = new JTextField();
		jtxtTel.setColumns(10);
		jtxtTel.setBounds(161, 94, 108, 19);
		jtxtTel.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				if (!Character.isDigit(evt.getKeyChar())) {
                    evt.consume();
                    jtxtTel.setToolTipText("Sadece Say\u0131 Girebilirsiniz");
                }else if(Character.isDigit(evt.getKeyChar())){
                	jtxtTel.setToolTipText(null);
                }
			}
		});
		panel.add(jtxtTel);
		
		jlblAdres = new JLabel("K\u00FCt\u00FCphane Adres:");
		jlblAdres.setHorizontalAlignment(SwingConstants.TRAILING);
		jlblAdres.setBounds(10, 65, 141, 19);
		panel.add(jlblAdres);
		
		jtxtAdres = new JTextField();
		jtxtAdres.setColumns(10);
		jtxtAdres.setBounds(161, 65, 108, 19);
		panel.add(jtxtAdres);
		
		btnEkle = new JButton("Ekle");
		btnEkle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String ad = jtxtAd.getText();
				String email = jtxtEmail.getText();
				String adres = jtxtAdres.getText();
				String tel = jtxtTel.getText();
				if((ad.equals("")||email.equals("")||adres.equals("")||tel.equals(""))) {
					JOptionPane.showMessageDialog(null, "Tüm Alanlarý Doldurunuz.", "SÝSTEM MESAJI", JOptionPane.OK_OPTION);
				}else {
					fonk.kutuphaneEkle(ad, email, tel, adres);
					
					jtxtTemizle();
					kutuphaneListele();
					
				}
				
			}
		});
		btnEkle.setBounds(315, 65, 85, 19);
		panel.add(btnEkle);
		
		btnGuncelle = new JButton("G\u00FCncelle");
		btnGuncelle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String ad = jtxtAd.getText();
				String email = jtxtEmail.getText();
				String adres = jtxtAdres.getText();
				String tel = jtxtTel.getText();
				
				DefaultTableModel kuTab = (DefaultTableModel)kutuphaneTablo.getModel();
				int secilenSatir= kutuphaneTablo.getSelectedRow();
			
		        if(secilenSatir==-1){
		            if(kuTab.getRowCount()==0){
		            	JOptionPane.showMessageDialog(null, "Liste boþ!", "SÝSTEM MESAJI", JOptionPane.OK_OPTION);
		            }else { JOptionPane.showMessageDialog(null, "Seçim yapýnýz!", "SÝSTEM MESAJI", JOptionPane.OK_OPTION);}
		        }else {
		        	
		        	int id = Integer.parseInt(kuTab.getValueAt(secilenSatir,0).toString()); 
		            fonk.kutuphaneGuncelle(id, ad, email, tel, adres);
		            kutuphaneListele();
		            jtxtTemizle();
		            JOptionPane.showMessageDialog(null, "Güncelleme Baþarýlý!", "SÝSTEM MESAJI", JOptionPane.OK_OPTION);
		        
		        }
				
			}
		});
		btnGuncelle.setBounds(315, 94, 85, 19);
		panel.add(btnGuncelle);
		
		btnSil = new JButton("Sil");
		btnSil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int secilenSatir= kutuphaneTablo.getSelectedRow();
				DefaultTableModel kuTab = (DefaultTableModel)kutuphaneTablo.getModel();
		        if(secilenSatir==-1){
		            if(kuTab.getRowCount()==0){
		            	JOptionPane.showMessageDialog(null, "Liste boþ!", "SÝSTEM MESAJI", JOptionPane.OK_OPTION);
		            }else { JOptionPane.showMessageDialog(null, "Seçim yapýnýz!", "SÝSTEM MESAJI", JOptionPane.OK_OPTION);}
		        }else {
		        	if (JOptionPane.showConfirmDialog(null, "Kütüphane'ye ait tüm bilgiler silinecektir. Devam etmek istediðinizi emin misiniz?", "UYARI",
					        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
		        		int id = Integer.parseInt(kuTab.getValueAt(secilenSatir,0).toString()); 
			            fonk.kutuphaneSil(id);
			            kutuphaneListele();
			            jtxtTemizle();
			            JOptionPane.showMessageDialog(null, "Silme Baþarýlý!", "SÝSTEM MESAJI", JOptionPane.OK_OPTION);
					} else {
					    
					}
		        }
			}
		});
		btnSil.setBounds(410, 65, 85, 19);
		panel.add(btnSil);
		
		btnTemizle = new JButton("Temizle");
		btnTemizle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jtxtTemizle();
				try {
					kutuphaneTablo.removeRowSelectionInterval(kutuphaneTablo.getSelectedRow(), kutuphaneTablo.getSelectedRow());
				} catch (Exception e1) {
					
				}
			}
		});
		btnTemizle.setBounds(410, 94, 85, 19);
		panel.add(btnTemizle);
		
	}
}
