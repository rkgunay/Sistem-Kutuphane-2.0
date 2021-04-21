import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

@SuppressWarnings("serial")
public class OgrenciGirisPanel extends JFrame {

	private JPanel contentPane;
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
					OgrenciGirisPanel frame = new OgrenciGirisPanel();
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
	public OgrenciGirisPanel() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(OgrenciGirisPanel.class.getResource("/logo.png")));
		setTitle("\u00D6\u011Frenci Giri\u015Fi");
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
		
		JLabel jlblSifre = new JLabel("\u015Eifre:");
		jlblSifre.setBounds(10, 40, 110, 20);
		panel.add(jlblSifre);
		
		JButton btnGiris = new JButton("Giri\u015F");
		btnGiris.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = jtxtKadi.getText();
				String password =  new String(jpassSifre.getPassword());
				   @SuppressWarnings("static-access")
				   
				boolean  result = fonk.ogrenciGiris(id, password);
			        if(result == true){
			        	try {
							@SuppressWarnings("static-access")
							Statement st = fonk.conn.createStatement();
							String query = "SELECT ogrenci_id FROM ogrenci WHERE ogrenci_kullanici_adi='"+id+"' AND ogrenci_sifre='"+password+"'";
							ResultSet rs = st.executeQuery(query);
							rs.beforeFirst();
							rs.next();
							String ogrID =rs.getString(1);
							OgrenciPanel ogrenci = new OgrenciPanel();
							ogrenci.setJtxtID(ogrID);
							setVisible(false);
							ogrenci.setVisible(true);
							
						
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
		btnGiris.setBounds(139, 89, 111, 21);
		panel.add(btnGiris);
		
		JButton btnSifreUnuttum = new JButton("\u015Eifremi Unuttum ");
		btnSifreUnuttum.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OgrenciSifreUnuttum ogrenciSifre = new OgrenciSifreUnuttum();
				ogrenciSifre.setVisible(true);
			}
		});
		btnSifreUnuttum.setIconTextGap(0);
		btnSifreUnuttum.setHorizontalTextPosition(SwingConstants.RIGHT);
		btnSifreUnuttum.setHorizontalAlignment(SwingConstants.RIGHT);
		btnSifreUnuttum.setFont(new Font("Tahoma", Font.ITALIC, 10));
		btnSifreUnuttum.setContentAreaFilled(false);
		btnSifreUnuttum.setBorderPainted(false);
		btnSifreUnuttum.setBorder(null);
		btnSifreUnuttum.setAlignmentY(0.0f);
		btnSifreUnuttum.setBounds(139, 66, 112, 13);
		panel.add(btnSifreUnuttum);
		
		JButton btnUyeOl = new JButton("\u00DCye Ol");
		btnUyeOl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OgrenciUyeOl ogrenciUye = new OgrenciUyeOl();
				ogrenciUye.setVisible(true);
			}
		});
		btnUyeOl.setBounds(18, 89, 111, 21);
		panel.add(btnUyeOl);
		
		jpassSifre = new JPasswordField();
		jpassSifre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = jtxtKadi.getText();
				String password =  new String(jpassSifre.getPassword());
				   @SuppressWarnings("static-access")
				   
				boolean  result = fonk.ogrenciGiris(id, password);
			        if(result == true){
			        	try {
							@SuppressWarnings("static-access")
							Statement st = fonk.conn.createStatement();
							String query = "SELECT ogrenci_id FROM ogrenci WHERE ogrenci_kullanici_adi='"+id+"' AND ogrenci_sifre='"+password+"'";
							ResultSet rs = st.executeQuery(query);
							rs.beforeFirst();
							rs.next();
							String ogrID =rs.getString(1);
							OgrenciPanel ogrenci = new OgrenciPanel();
							ogrenci.setJtxtID(ogrID);
							setVisible(false);
							ogrenci.setVisible(true);
							ogrenci.kutuphaneListele();
							ogrenci.oduncListele(ogrID);
						
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
		jpassSifre.setBounds(130, 41, 120, 19);
		panel.add(jpassSifre);
		btnSifreUnuttum.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnSifreUnuttum.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 10));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnSifreUnuttum.setFont(new Font("Tahoma", Font.ITALIC, 10));
			}
		});
		
	}
}
