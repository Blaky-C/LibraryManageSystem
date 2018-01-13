package frame;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import interfaces.AddCallback;

public class ManageFrame extends JDialog implements AddCallback{

	public static final int WIDTH = 900;
	public static final int HEIGHT = 645;
	
	private Container contentPane;
	private JPanel p1;
	private JPanel p2;
	private JPanel p3;
	
	private JLabel label;
	private JScrollPane sp;
	private JTable table;
	private JButton b1;
	private JButton b2;
	private JButton b3;

	private XYLayout layout = new XYLayout();
	
	public ManageFrame() {
		this.setSize(WIDTH, HEIGHT);
		this.setTitle("图书管理系统");
		//this.setResizable(false);
		this.setLocation(100, 100);
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setModal(true);
		contentPane = getContentPane();
		/*layout = new XYLayout();
		contentPane.setLayout(layout);*/
		
		initPanel1();
		initPanel2();
		initPanel3();
	
		setButtonAction();
	}
	
	public void initPanel1() {
		p1 = new JPanel();
		p1.setLayout(layout);
		
		label = new JLabel("图书详情：");
		p1.add(label, new XYConstraints(10, 0, 100, 40));
		
		contentPane.add(p1, BorderLayout.NORTH);
	}
	
	private String[] caption = {"序号", "图书编号", "图书名称", "作者", "图书状态", "借书人", "借书时间", "应还时间", "是否超期"};
	private Object[][] data = null;
	private DefaultTableModel model = null;
	private ConOra conOra = null;
	private List<BookInfo> bookList;
	
	public void initPanel2() {
		p2 = new JPanel();
		p2.setLayout(new BorderLayout());
		
		/*Object[][] data = {
				{ 1, "TP303/205", "计算机组成原理", "任国林", "借出", "葛阳晨", "2018/1/4", "2018/2/2", "否" },
				{ 2, "TP303/205", "计算机组成原理", "任国林", "借出", "葛阳晨", "2018/1/4", "2018/2/2", "否" },
				{ 3, "TP303/205", "计算机组成原理", "任国林", "借出", "葛阳晨", "2018/1/4", "2018/2/2", "否" }
		};*/
		
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

	public void initPanel3() {
		p3 = new JPanel();
		
		b1 = new JButton("入库");
		b1.setMargin(new Insets(10, 50, 10, 50));
		b2 = new JButton("出库");
		b2.setMargin(new Insets(10, 50, 10, 50));
		b3 = new JButton("返回");
		b3.setMargin(new Insets(10, 50, 10, 50));
		 
		FlowLayout layout = new FlowLayout();
		layout.setHgap(100);
		p3.setLayout(layout);
		
		p3.add(b1);
		p3.add(b2);
		p3.add(b3);
		
		contentPane.add(p3, BorderLayout.SOUTH);
	}
	
	public void setButtonAction() {
		
		//入库
		b1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new AddBookFrame(ManageFrame.this).show();
			}
		});
		
		//出库
		b2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(ManageFrame.this, "请选择出库书目！", "错误", JOptionPane.WARNING_MESSAGE);
				}else {
					int result = JOptionPane.showConfirmDialog(ManageFrame.this, "确认出库？", "确认", JOptionPane.YES_NO_OPTION);
					if (result == JOptionPane.YES_OPTION) {
						int[] selectedRows = table.getSelectedRows();
						
						for (int s: selectedRows) {
							System.out.println(bookList.get(s).getBook_id());
							String sql = "update book_info set "
									+ "if_in_store = 0, if_lended = 0, borrower = '/', "
									+ "lend_date = '/', return_date = '/', if_exceed = 0 "
									+ "where book_id = '"+bookList.get(s).getBook_id()+"'"; 
							updateFromDB(sql);
						}
						queryFromDB();
					}
				}
			}
		});
		
		b3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ManageFrame.this.dispose();
			}
		});
	}
	
	public void queryFromDB() {
		bookList.clear();
		model.getDataVector().clear();
		
		connectDB();
		String sql = "select * from book_info where if_in_store = 1";
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
				(book.isIf_lended()?"借出":"未借"), book.getBorrower(), 
				book.getLend_date(), book.getReturn_date(), (book.isIf_exceed()?"超期":"未超期")};
		return o;
	}
	
	//用于子页面添加数据后回调更新
	@Override
	public void update(String sql) {
		updateFromDB(sql);
		queryFromDB();
	}

	public static void main(String[] args) {
		new ManageFrame().show();
	}
}
