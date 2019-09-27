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

public class reservations extends JFrame implements ActionListener {
	private DBcon db=new DBcon("localhost","root","","library");
	private JTable reservationstable;
	private DefaultTableModel setup;
	public reservations() {
		super("this is reservations");
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			}
		catch(Exception missfall) {
			System.out.print(missfall);
			}
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(1200,700));
		setLayout(new BorderLayout());
		JButton back = new JButton("Back");
		JButton reserve = new JButton("reserve a book");
		
		JPanel north = new JPanel();
		JPanel center = new JPanel();
		JPanel south = new JPanel();
		back.addActionListener(this);
		reserve.addActionListener(this);
		center.add(reservelist());
		north.add(back);
		north.add(reserve);
		add(BorderLayout.NORTH,north);
		add(BorderLayout.CENTER,center);
		add(BorderLayout.SOUTH,south);
		pack();
		setVisible(true);
	}
	private JScrollPane reservelist() {
		Object[]fields = {"Title","MediaID","Reserver","queue spot"};
		Object[][] rows = new Object[0][4];
		setup = new DefaultTableModel(rows,fields);
		reservationstable = new JTable(setup);
		reservationstable.setPreferredScrollableViewportSize(new Dimension(1000,500));
		JScrollPane list = new JScrollPane(reservationstable);
		

		
		
		Object[][] datas = db.getData("select * from reservation left join dvd ON reservation.MediaID = dvd.MediaID left join book ON reservation.MediaID = book.MediaID left join cd ON reservation.MediaID = cd.MediaID left join media ON reservation.MediaID = media.MediaID ORDER by row");
		
		for(int i=0;i<datas.length;i++) {
			
			String MediaID = datas[i][1].toString();
			String Queue = datas[i][3].toString();
			String title = null;
			String UserID = datas[i][2].toString();
			String MType = datas[i][18].toString();
			if(MType.equals("dvd") ){
				title = datas[i][6].toString();
				
			}
			if(MType.equals("cd")) {
				title = datas[i][14].toString();
				
			}
			if(MType.equals("book")){
				title = datas[i][10].toString();
				
			}
			setup.addRow(new Object[]{title,MediaID,UserID,Queue});
			
		}
		return list;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		String command=e.getActionCommand();
		if(command=="Back") {
			new Login();
			this.dispose();
		}
		if(command=="reserve a book") {
			Object[][] reservdata = db.getData("select * from reservation");
			String MediaID = JOptionPane.showInputDialog("Type ID of media");
			String Socialsec = JOptionPane.showInputDialog("Type social security number of reserving user");
			int row=1;
			for(int i=0;i<reservdata.length;i++) {
				String pastmedia=reservdata[i][1].toString();
				if(pastmedia.equals(MediaID)) {
					row++;
				}
			}
			if(Socialsec.length()==10) {
			String SQL=String.format("Insert into reservation(UserID,MediaID,row) VALUES ('%s','%s','%s')",Socialsec,MediaID,row);
			db.execute(SQL);
			JOptionPane.showMessageDialog(null, "reservation added");
			new reservations();
			this.dispose();
			}
		}
		
	}
}
