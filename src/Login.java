import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class Login extends JFrame implements ActionListener {
	DBcon db=new DBcon("localhost","root","","library");
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
	}
	
	
	public static void main(String[] args) {
		
		new Login();

	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		String command=arg0.getActionCommand();
		if(command=="add media") {
			String Author;
			String ID;
			String title;
			String SQL;
			String SQL2;
			
			String MType = JOptionPane.showInputDialog(null,"DVD, CD or Book?");
			switch(MType){
			case "DVD":
				String Director = JOptionPane.showInputDialog(null,"Write directors name");
				ID = JOptionPane.showInputDialog(null,"Write or scan ID");
				title = JOptionPane.showInputDialog(null,"write the title of the DVD");
				SQL = String.format("Insert into dvd(MediaID,Director,Title)"+"VALUES ('%s','%s','%s')",ID,Director,title);
				SQL2 = String.format("Insert into media(MediaID,MType)"+"VALUES ('%s','dvd')",ID);
				db.execute(SQL);
				db.execute(SQL2);
				break;
			case "CD":
				Author = JOptionPane.showInputDialog("Write authors name");
				String Voice = JOptionPane.showInputDialog("Write the name of the voice actor");
				ID = JOptionPane.showInputDialog("Write or scan ID");
				title = JOptionPane.showInputDialog("write the title of the CD");;
				SQL = String.format("Insert into cd(MediaID,Author,Title,Voice)"+"VALUES ('%s','%s','%s','%s')",ID,Author,title,Voice);
				SQL2 = String.format("Insert into media(MediaID,MType)"+"VALUES ('%s','cd')",ID);
				db.execute(SQL);
				db.execute(SQL2);
				break;
			case "Book":
				Author = JOptionPane.showInputDialog(null,"Write authors name");
				ID = JOptionPane.showInputDialog(null,"Write or scan ID");
				title = JOptionPane.showInputDialog(null,"write the title of the Book");
				SQL = String.format("Insert into book(MediaID,Author,Title)"+"VALUES ('%s','%s','%s')",ID,Author,title);
				SQL2 = String.format("Insert into media(MediaID,MType)"+"VALUES ('%s','book')",ID);
				db.execute(SQL);
				db.execute(SQL2);
				break;
			default: JOptionPane.showMessageDialog(null, "fel skrivet");	
			}
		}
		
	}

}
