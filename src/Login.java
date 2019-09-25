import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class Login extends JFrame implements ActionListener {
	
	public Login() {
		super("Login");
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			}
		catch(Exception missfall) {
			System.out.print(missfall);
			}
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(1200,700));
		setLayout(new BorderLayout());
		JButton addmedia= new JButton("add media");
		addmedia.addActionListener(this);
		add(addmedia);
		pack();
		setVisible(true);
		setLocationRelativeTo(null);
	}
	
	
	public static void main(String[] args) {
		
		new Login();

	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		String command=arg0.getActionCommand();
		if(command=="add media") {
			DBcon db=new DBcon("localhost","root","","library");
			String MType = JOptionPane.showInputDialog(null,"DVD, CD or Book?");
			switch(MType){
			case "DVD":
				String Director = JOptionPane.showInputDialog(null,"Write directors name");;
				String ID = JOptionPane.showInputDialog(null,"Write or scan ID");;
				String title = JOptionPane.showInputDialog(null,"write the title of the DVD");;
				String SQL = String.format("Insert into dvd(MediaID,Director,Title)"+"VALUES ('%s','%s','%s')",Director,ID,title);
				String SQL2 = String.format("Insert into media(MediaID,MType)"+"VALUES ('%s','dvd')",ID);
				db.execute(SQL);
				db.execute(SQL2);
				break;
			case "CD":
				break;
			case "Book":
				break;
			default: JOptionPane.showMessageDialog(null, "fel skrivet");	
			}
		}
		
	}

}
