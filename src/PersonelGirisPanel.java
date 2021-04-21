import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

@SuppressWarnings("serial")
public class PersonelGirisPanel extends JFrame {

	private JPanel contentPane;
	private JPanel panel;
	private JTextField jtxtKadi;
	private JPasswordField jpassSifre;
	
	VeritabaniFonksiyonlari fonk = new VeritabaniFonksiyonlari();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PersonelGirisPanel frame = new PersonelGirisPanel();
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
	public PersonelGirisPanel() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(PersonelGirisPanel.class.getResource("/logo.png")));
		setTitle("Personel Giri\u015Fi");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(90, 65, 261, 134);
		contentPane.add(panel);
		panel.setLayout(null);
		
		jtxtKadi = new JTextField();
		jtxtKadi.setColumns(10);
		jtxtKadi.setBounds(130, 11, 120, 20);
		panel.add(jtxtKadi);
		
		JLabel jlblKadi = new JLabel("Kullan\u0131c\u0131 Ad\u0131:");
		jlblKadi.setBounds(10, 10, 110, 20);
		panel.add(jlblKadi);
		
		JLabel jlblSifre = new JLabel("\u015Eifre:");
		jlblSifre.setBounds(10, 40, 110, 20);
		panel.add(jlblSifre);
		
		JButton btnGiris = new JButton("Giri\u015F");
		btnGiris.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = jtxtKadi.getText();
				String password =  new String(jpassSifre.getPassword());
				   @SuppressWarnings("static-access")
				   
				boolean  result = fonk.personelGiris(id, password);
			        if(result == true){
			        	try {
							@SuppressWarnings("static-access")
							Statement st = fonk.conn.createStatement();
							String query = "SELECT kutuphane_id FROM personel WHERE personel_kullanici_adi='"+id+"' AND personel_sifre='"+password+"'";
							ResultSet rs = st.executeQuery(query);
							rs.beforeFirst();
							rs.next();
							String kutID =rs.getString(1);
							
							PersonelPanel personel = new PersonelPanel();
							personel.setJtxtID(kutID);
							setVisible(false);
							personel.kitapListele(kutID);
							personel.ogrenciListele(kutID);
							personel.setVisible(true);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}

			        }
			        else{
			         
			            jpassSifre.setText("");
			            JOptionPane.showMessageDialog(null, "Kullanýcý adý veya þifre yanlýþ!", "SÝSTEM MESAJI", JOptionPane.OK_OPTION);
			        }
			}
		});
		btnGiris.setBounds(140, 71, 111, 21);
		panel.add(btnGiris);
		
		jpassSifre = new JPasswordField();
		jpassSifre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = jtxtKadi.getText();
				String password =  new String(jpassSifre.getPassword());
				   @SuppressWarnings("static-access")
				   
				boolean  result = fonk.personelGiris(id, password);
			        if(result == true){
			        	try {
							@SuppressWarnings("static-access")
							Statement st = fonk.conn.createStatement();
							String query = "SELECT kutuphane_id FROM personel WHERE personel_kullanici_adi='"+id+"' AND personel_sifre='"+password+"'";
							ResultSet rs = st.executeQuery(query);
							rs.beforeFirst();
							rs.next();
							String kutID =rs.getString(1);
							
							PersonelPanel personel = new PersonelPanel();
							personel.setJtxtID(kutID);
							setVisible(false);
							personel.kitapListele(kutID);
							personel.ogrenciListele(kutID);
							personel.oduncListele(kutID);
							personel.setVisible(true);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}

			        }
			        else{
			         
			            jpassSifre.setText("");
			            JOptionPane.showMessageDialog(null, "Kullanýcý adý veya þifre yanlýþ!", "SÝSTEM MESAJI", JOptionPane.OK_OPTION);
			        }
			}
		});
		jpassSifre.setBounds(129, 41, 121, 19);
		panel.add(jpassSifre);
	}

}
