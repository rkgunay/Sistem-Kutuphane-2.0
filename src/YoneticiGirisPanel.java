import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

@SuppressWarnings("serial")
public class YoneticiGirisPanel extends JFrame {

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
					YoneticiGirisPanel frame = new YoneticiGirisPanel();
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
	public YoneticiGirisPanel() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(YoneticiGirisPanel.class.getResource("/logo.png")));
		setTitle("Y\u00F6netici Giri\u015Fi");
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
		
		jpassSifre = new JPasswordField();
		jpassSifre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = jtxtKadi.getText();
				String password =  new String(jpassSifre.getPassword());
				   @SuppressWarnings("static-access")
				   
				boolean  result = fonk.yoneticiGiris(id, password);
			        if(result == true){
			            YoneticiPanel yonetici = new YoneticiPanel();
			            setVisible(false);
			            yonetici.kutuphaneListele();
			            yonetici.setVisible(true);

			        }
			        else{
			         
			            jpassSifre.setText("");
			            JOptionPane.showMessageDialog(null, "Kullanýcý adý veya þifre yanlýþ!", "SÝSTEM MESAJI", JOptionPane.OK_OPTION);
			        }
			}
		});
		jpassSifre.setToolTipText("");
		jpassSifre.setBounds(130, 41, 120, 20);
		panel.add(jpassSifre);
		
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
				   
				boolean  result = fonk.yoneticiGiris(id, password);
			        if(result == true){
			            YoneticiPanel yonetici = new YoneticiPanel();
			            setVisible(false);
			            yonetici.kutuphaneListele();
			            yonetici.setVisible(true);

			        }
			        else{
			         
			            jpassSifre.setText("");
			            JOptionPane.showMessageDialog(null, "Kullanýcý adý veya þifre yanlýþ!", "SÝSTEM MESAJI", JOptionPane.OK_OPTION);
			        }
			}
		});
		btnGiris.setBounds(139, 71, 111, 21);
		panel.add(btnGiris);
	}
}
