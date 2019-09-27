import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

public class Userlist extends JFrame implements ActionListener{
	private DBcon db=new DBcon("localhost","root","","library");
	private JTable reservationstable;
	private DefaultTableModel setup;
	public Userlist() {
		super("this is userlist");
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
		
		JPanel north = new JPanel();
		JPanel center = new JPanel();
		JPanel south = new JPanel();
		back.addActionListener(this);
		center.add(userlist());
		north.add(back);
		add(BorderLayout.NORTH,north);
		add(BorderLayout.CENTER,center);
		add(BorderLayout.SOUTH,south);
		pack();
		setVisible(true);
	}
	private JScrollPane userlist() {
		Object[]fields = {"Lname","Fname","Socialsecurity"};
		Object[][] rows = new Object[0][3];
		setup = new DefaultTableModel(rows,fields);
		reservationstable = new JTable(setup);
		reservationstable.setPreferredScrollableViewportSize(new Dimension(1000,500));
		JScrollPane list = new JScrollPane(reservationstable);
		

		
		
		Object[][] datas = db.getData("select * from user");
		
		for(int i=0;i<datas.length;i++) {
			
			String Lname = datas[i][2].toString();
			String Fname = datas[i][1].toString();
			String UserID = datas[i][0].toString();
			
			setup.addRow(new Object[]{Lname,Fname,UserID});
			
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
		
	}
}
