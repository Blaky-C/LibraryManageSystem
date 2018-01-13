package frame;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.borland.jbcl.layout.XYConstraints;
import com.borland.jbcl.layout.XYLayout;

import bean.BookInfo;
import condb.ConOra;

public class ReturnBookFrame extends JDialog 
{
	public static final int WIDTH = 900;
	public static final int HEIGHT = 645;
	
	private Container contentPane;
	private JPanel p1;
	private JPanel p2;
	private JPanel p3;
	
	private JLabel label;
	private JTable table;
	private JButton b1;
	private JButton b2;
	
	private XYLayout layout = new XYLayout();
	
	public ReturnBookFrame()
	{
		this.setSize(WIDTH,HEIGHT);
		this.setTitle("图书管理系统");
		this.setLocation(100, 100);
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = getContentPane();
		this.setVisible(true);
		this.setModal(true);
		
		initPanel1();
		initPanel2();
		initPanel3();
		
		setButtonAction();
	}
	
	public void initPanel1()
	{
		p1 = new JPanel();
		p1.setLayout(layout);
		
		label = new JLabel("还书模块");
		p1.add(label,new XYConstraints(10, 0, 100, 40));
		contentPane.add(p1, BorderLayout.NORTH);
	}
	
	private String[] caption = {"序号","图书编号","图书名称","作者","借书人","借书时间","应还时间","是否超期"};
	private Object[][] data = null;
	private DefaultTableModel model = null;
	private ConOra conOra = null;
	private List<BookInfo> bookList;
	private JScrollPane sp;
	
	public void initPanel2() {
		p2 = new JPanel();
		p2.setLayout(new BorderLayout());
		
		bookList = new ArrayList<>();
		model = new DefaultTableModel(data, caption) {
			@Override
			public boolean isCellEditable(int row, int column) {
				//更改celldata属性无法直接更改
				return false;
			}
		};
		table = new JTable(model);
		table.setRowHeight(20);
		
		p2.add(table.getTableHeader(), BorderLayout.NORTH);
		
		sp = new JScrollPane(table);
		p2.add(sp, BorderLayout.CENTER);
		contentPane.add(p2, BorderLayout.CENTER);
		
		queryFromDB();
	}

	public void initPanel3()
	{
		p3 = new JPanel();
		
		b1 = new JButton("还书");
		b1.setMargin(new Insets(10,50,10,50));
		b2 = new JButton("返回");
		b2.setMargin(new Insets(10,50,10,50));
		
		FlowLayout layout = new FlowLayout();
		layout.setHgap(100);
		p3.setLayout(layout);
		
		p3.add(b1);
		p3.add(b2);
		
		contentPane.add(p3,BorderLayout.SOUTH);
	}
	
	public void setButtonAction()
	{
		b1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() == -1) {
					//弹出对话框
					System.out.println("请选择归还图书！");
				}else {
					int[] selectedRows = table.getSelectedRows();
					
					boolean if_exceed = false;
					
					for (int s: selectedRows) {
						System.out.println(bookList.get(s).getBook_id());
						String sql = "update book_info set "
								+ "if_lended = 0, borrower = '/', "
								+ "lend_date = '/', return_date = '/', if_exceed = 0 "
								+ "where book_id = '"+bookList.get(s).getBook_id()+"'"; 
						
						if (bookList.get(s).isIf_exceed()==true) {
							if_exceed = true;
						}
						
						updateFromDB(sql);
					}
					queryFromDB();
					
					if (if_exceed) {
						JOptionPane.showMessageDialog(ReturnBookFrame.this, "还书成功！\n已超期！", "结果", JOptionPane.WARNING_MESSAGE);
					}else {
						JOptionPane.showMessageDialog(ReturnBookFrame.this, "还书成功！", "结果", JOptionPane.PLAIN_MESSAGE);
					}
					
				}
			}
		});
		
		b2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ReturnBookFrame.this.dispose();	
			}
		});
	}
	
	public void queryFromDB() {
		bookList.clear();
		model.getDataVector().clear();
		
		connectDB();
		String sql = "select * from book_info where if_lended = 1";
		conOra.query(sql);
		
		ResultSet result = conOra.getResult();
		
		try {
			while(result.next()) {
				bookList.add(new BookInfo(result));
			}
			System.out.println(bookList.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for (int i=0;i<bookList.size();i++) {
			model.addRow(transRowData(i+1, bookList.get(i)));
		}
		table.invalidate();
		sp.invalidate();
		conOra.close();
	}
	
	public void updateFromDB(String sql) {
		connectDB();
		conOra.update(sql);
		
		if (conOra.getNum() != -1) {
			System.out.println("更新数据库成功。");
		}
		
		conOra.close();
	}
	
	public void connectDB() {
		conOra = new ConOra();
		conOra.getCon();
		conOra.getStatement();
	}
	
	//将Bean对象转化为Object[]方便放入JTable
	public Object[] transRowData(int i, BookInfo book) {
		Object[] o = {i, book.getBook_id(), book.getBook_name(), book.getAuthor(),
				book.getBorrower(), book.getLend_date(), book.getReturn_date(), 
				(book.isIf_exceed()?"超期":"未超期")};
		return o;
	}
	
	public static void main(String[] args) 
	{
		//new ReturnBookFrame().show();
	}
}
