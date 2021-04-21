import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;


@SuppressWarnings("serial")
public class OgrenciSifreUnuttum extends JFrame {

	private JPanel contentPane;
	private JTextField jtxtKadi;
	private JTextField jtxtEmail;

	VeritabaniFonksiyonlari fonk = new VeritabaniFonksiyonlari();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OgrenciSifreUnuttum frame = new OgrenciSifreUnuttum();
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
	public OgrenciSifreUnuttum() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(OgrenciSifreUnuttum.class.getResource("/logo.png")));
		setTitle("\u00D6\u011Frenci - \u015Eifremi Unuttum");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(90, 65, 261, 134);
		contentPane.add(panel);
		
		jtxtKadi = new JTextField();
		jtxtKadi.setColumns(10);
		jtxtKadi.setBounds(130, 11, 120, 20);
		panel.add(jtxtKadi);
		
		JLabel jlblKadi = new JLabel("Kullan\u0131c\u0131 Ad\u0131:");
		jlblKadi.setBounds(10, 10, 110, 20);
		panel.add(jlblKadi);
		
		JLabel jlblEmail = new JLabel("Email:");
		jlblEmail.setBounds(10, 40, 110, 20);
		panel.add(jlblEmail);
		
		JButton btnSifreGonder = new JButton("\u015Eifre G\u00F6nder");
		btnSifreGonder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String kadi = jtxtKadi.getText();
		        String email = jtxtEmail.getText();
		        boolean  result = VeritabaniFonksiyonlari.ogrenciSifreGonder(kadi, email);
		        
		        if(result == true){
		            try {
						@SuppressWarnings("static-access")
						Statement st = fonk.conn.createStatement();
						String query = "SELECT ogrenci_sifre FROM ogrenci WHERE ogrenci_kullanici_adi='"+kadi+"' AND ogrenci_email='"+email+"'";
						ResultSet rs = st.executeQuery(query);
						rs.beforeFirst();
						rs.next();
						String musteri_sifre =rs.getString(1);
						JOptionPane.showMessageDialog(null, "Þifreniz: "+ musteri_sifre, "SÝSTEM MESAJI", JOptionPane.OK_OPTION);
						
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
		        }
		        else{
		         
		            jtxtKadi.setText("");
		            jtxtEmail.setText("");
		            JOptionPane.showMessageDialog(null, "Kullanýcý adý veya email yanlýþ!", "SÝSTEM MESAJI", JOptionPane.OK_OPTION);
		        }
			}
		});
		btnSifreGonder.setBounds(130, 89, 120, 21);
		panel.add(btnSifreGonder);
		
		jtxtEmail = new JTextField();
		jtxtEmail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String kadi = jtxtKadi.getText();
		        String email = jtxtEmail.getText();
		        boolean  result = VeritabaniFonksiyonlari.ogrenciSifreGonder(kadi, email);
		        
		        if(result == true){
		            try {
						@SuppressWarnings("static-access")
						Statement st = fonk.conn.createStatement();
						String query = "SELECT ogrenci_sifre FROM ogrenci WHERE ogrenci_kullanici_adi='"+kadi+"' AND ogrenci_email='"+email+"'";
						ResultSet rs = st.executeQuery(query);
						rs.beforeFirst();
						rs.next();
						String musteri_sifre =rs.getString(1);
						JOptionPane.showMessageDialog(null, "Þifreniz: "+ musteri_sifre, "SÝSTEM MESAJI", JOptionPane.OK_OPTION);
						
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
		        }
		        else{
		         
		            jtxtKadi.setText("");
		            jtxtEmail.setText("");
		            JOptionPane.showMessageDialog(null, "Kullanýcý adý veya email yanlýþ!", "SÝSTEM MESAJI", JOptionPane.OK_OPTION);
		        }
			}
		});
		jtxtEmail.setColumns(10);
		jtxtEmail.setBounds(130, 41, 120, 20);
		panel.add(jtxtEmail);
		
	}
}
