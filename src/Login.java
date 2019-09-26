import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

public class Login extends JFrame implements ActionListener {
	
	private DBcon db=new DBcon("localhost","root","","library");
	private JTable mediatable;
	private DefaultTableModel setup;
	
	public Login() {
		super("this is library");
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			}
		catch(Exception missfall) {
			System.out.print(missfall);
			}
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(1200,700));
		setLayout(new BorderLayout());
		JButton addmedia = new JButton("add media");
		JButton delmedia = new JButton("remove media");
		JButton loanmedia = new JButton("Register loan");
		JButton loanshower = new JButton("list of loans");
		
		JPanel north = new JPanel();
		JPanel center = new JPanel();
		JPanel south = new JPanel();
		delmedia.addActionListener(this);
		addmedia.addActionListener(this);
		loanmedia.addActionListener(this);
		loanshower.addActionListener(this);
		center.add(medialist());
		north.add(addmedia);
		north.add(delmedia);
		south.add(loanmedia);
		south.add(loanshower);
		add(BorderLayout.NORTH,north);
		add(BorderLayout.CENTER,center);
		add(BorderLayout.SOUTH,south);
		pack();
		setVisible(true);
	}
	private JScrollPane medialist() {
		Object[]fields = {"Mediatype","Title","ID","Author/director","Helpers/voice"};
		Object[][] rows = new Object[0][4];
		setup = new DefaultTableModel(rows,fields);
		mediatable = new JTable(setup);
		mediatable.setPreferredScrollableViewportSize(new Dimension(1000,500));
		JScrollPane list = new JScrollPane(mediatable);
		

		
		
		Object[][] datas = db.getData("select * from media left join dvd ON media.MediaID = dvd.MediaID left join book ON media.MediaID = book.MediaID left join cd ON media.MediaID = cd.MediaID ORDER by MType");
		
		for(int i=0;i<datas.length;i++) {
			
			String MediaID = datas[i][0].toString();
			String MType = datas[i][1].toString();
			if(MType.equals("dvd") ){
				String Creator = datas[i][3].toString();
				String title = datas[i][4].toString();
				setup.addRow(new Object[]{MType,title,MediaID,Creator});
			}
			if(MType.equals("cd")) {
				String Voice=datas[i][14].toString();
				String Creator = datas[i][11].toString();
				String title = datas[i][12].toString();
				setup.addRow(new Object[]{MType,title,MediaID,Creator,Voice});
			}
			if(MType.equals("book")){
				String Creator = datas[i][7].toString();
				String title = datas[i][8].toString();
				setup.addRow(new Object[]{MType,title,MediaID,Creator});
			}
			
		}
		return list;
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
				JOptionPane.showMessageDialog(null,"Added media");
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
				JOptionPane.showMessageDialog(null,"Added media");
				break;
			case "Book":
				Author = JOptionPane.showInputDialog(null,"Write authors name");
				ID = JOptionPane.showInputDialog(null,"Write or scan ID");
				title = JOptionPane.showInputDialog(null,"write the title of the Book");
				SQL = String.format("Insert into book(MediaID,Author,Title)"+"VALUES ('%s','%s','%s')",ID,Author,title);
				SQL2 = String.format("Insert into media(MediaID,MType)"+"VALUES ('%s','book')",ID);
				db.execute(SQL);
				db.execute(SQL2);
				JOptionPane.showMessageDialog(null,"Added media");
				break;
			default: 
				JOptionPane.showMessageDialog(null, "fel skrivet");
				break;
			}
		}
		if(command=="remove media") {
			String RID = JOptionPane.showInputDialog("Type the id of the media you want to remove");
			Object[][] remdata = db.getData(String.format("SELECT MType from media where MediaID='%s'",RID));
				String SQL = String.format("Delete from media WHERE MediaID='%s'",RID);
				String SQL2 = String.format("Delete from %s WHERE MediaID='%s'",remdata[0][0],RID);
				db.execute(SQL);
				db.execute(SQL2);
			JOptionPane.showMessageDialog(null,"Deleted media");
		}
		if(command=="Register loan") {
			Object[][] dataloan = db.getData("Select * from loans");
			Object[][] mediadata = db.getData("Select * from media");
			String LoanID = JOptionPane.showInputDialog("Enter ID of loaned media");
			boolean isloaned=false;
			for(int i=0;i<dataloan.length;i++) {
				if(LoanID.equals(dataloan[i][2].toString())){
					JOptionPane.showMessageDialog(null, "This media is already loaned");
					isloaned=true;
				}
			}
			if(isloaned==false) {
				for(int i=0;i<mediadata.length;i++) {
					if(LoanID.equals(mediadata[i][0])){
						String Socialsec = JOptionPane.showInputDialog("enter social security number of loaner");
						String Date = JOptionPane.showInputDialog("Enter date of loan");
						String SQL=String.format("Insert into loans(Socialsecurity,Date,MediaID) VALUES ('%s','%s','%s')",Socialsec,Date,LoanID);
						db.execute(SQL);
						JOptionPane.showMessageDialog(null, "Added loan");
					}
					
				}
				
				
			}
			
		}
		if(command=="list of loans") {
			
		}
		
	}

}
