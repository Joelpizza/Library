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

public class LoanList extends JFrame implements ActionListener{
	private DBcon db=new DBcon("localhost","root","","library");
	private JTable Loantable;
	private DefaultTableModel setup;
	public LoanList() {
		super("this is loanlist");
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			}
		catch(Exception missfall) {
			System.out.print(missfall);
			}
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(1200,700));
		setLayout(new BorderLayout());
		JButton addmedia = new JButton("Back");
		
		JPanel north = new JPanel();
		JPanel center = new JPanel();
		JPanel south = new JPanel();
		addmedia.addActionListener(this);
		center.add(medialist());
		north.add(addmedia);
		add(BorderLayout.NORTH,north);
		add(BorderLayout.CENTER,center);
		add(BorderLayout.SOUTH,south);
		pack();
		setVisible(true);
	}
	private JScrollPane medialist() {
		Object[]fields = {"Title","ID","loaner"};
		Object[][] rows = new Object[0][4];
		setup = new DefaultTableModel(rows,fields);
		Loantable = new JTable(setup);
		Loantable.setPreferredScrollableViewportSize(new Dimension(1000,500));
		JScrollPane list = new JScrollPane(Loantable);
		

		
		
		Object[][] datas = db.getData("select * from loans left join dvd ON loans.MediaID = dvd.MediaID left join book ON loans.MediaID = book.MediaID left join cd ON loans.MediaID = cd.MediaID left join media ON loans.MediaID = media.MediaID ORDER by Date");
		
		for(int i=0;i<datas.length;i++) {
			
			String MediaID = datas[i][0].toString();
			String UsrID = datas[i][1].toString();
			String title = null;
			String MType = datas[i][17].toString();
			if(MType.equals("dvd") ){
//				String Creator = datas[i][3].toString();
				title = datas[i][5].toString();
				
			}
			if(MType.equals("cd")) {
//				String Voice=datas[i][14].toString();
//				String Creator = datas[i][11].toString();
				title = datas[i][13].toString();
				
			}
			if(MType.equals("book")){
//				String Creator = datas[i][7].toString();
				title = datas[i][9].toString();
				
			}
			setup.addRow(new Object[]{title,MediaID,UsrID});
			
		}
		return list;
}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String command=arg0.getActionCommand();
		if(command=="Back") {
			new Login();
			this.dispose();
		}
		
	}
}
