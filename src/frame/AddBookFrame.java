package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.borland.jbcl.layout.XYConstraints;
import com.borland.jbcl.layout.XYLayout;

import interfaces.AddCallback;

public class AddBookFrame extends JDialog{
	
	public static final int WIDTH = 600;
	public static final int HEIGHT = 400;
	
	private Container contentPane;
	private JPanel p1;
	private JPanel p2;
	
	private JLabel label;
	
	private JLabel l1;
	private JLabel l2;
	private JLabel l3;
	
	private JTextField t1;
	private JTextField t2;
	private JTextField t3;
	
	private JButton b1;
	private JButton b2;
	private JButton b3;

	private XYLayout layout = new XYLayout();
	private AddCallback back;
	
	public AddBookFrame(AddCallback c) {
		this.setSize(WIDTH, HEIGHT);
		this.setTitle("入库");
		//this.setResizable(false);
		this.setLocation(200, 200);
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setModal(true);
		contentPane = getContentPane();
		/*layout = new XYLayout();
		contentPane.setLayout(layout);*/
		
		initPanel1();
		initPanel2();
		
		this.back = c;
	
		setButtonAction();
	}
	
	public void initPanel1() {
		p1 = new JPanel();
		p1.setLayout(layout);
		
		label = new JLabel("新增图书：");
		p1.add(label, new XYConstraints(10, 0, 100, 40));
		
		contentPane.add(p1, BorderLayout.NORTH);
	}
	
	public void initPanel2() {
		p2 = new JPanel();
		p2.setBorder(BorderFactory.createEmptyBorder(60, 50, 100, 50));
		
		GridLayout gl = new GridLayout(2, 3);
		gl.setVgap(50);
		gl.setHgap(40);
		
		GridLayout gl2 = new GridLayout(2, 1);
		
		JPanel ip1 = new JPanel();
		ip1.setLayout(gl2);
		l1 = new JLabel("图书编号", JLabel.CENTER);
		
		t1 = new JTextField();
		ip1.add(l1);
		ip1.add(t1);
		
		JPanel ip2 = new JPanel();
		ip2.setLayout(gl2);
		l2 = new JLabel("图书名称", JLabel.CENTER);
		t2 = new JTextField();
		ip2.add(l2);
		ip2.add(t2);
		
		JPanel ip3 = new JPanel();
		ip3.setLayout(gl2);
		l3 = new JLabel("作者", JLabel.CENTER);
		t3 = new JTextField();
		ip3.add(l3);
		ip3.add(t3);
		
		b1 = new JButton("增加");
		b2 = new JButton("重置");
		b3 = new JButton("返回");
		
		p2.setLayout(gl);
		p2.add(ip1);
		p2.add(ip2);
		p2.add(ip3);
		p2.add(b1);
		p2.add(b2);
		p2.add(b3);
		
		contentPane.add(p2, BorderLayout.CENTER);
	}
	
	public void setButtonAction() {
		
		//新增图书
		b1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String id = t1.getText();
				String name = t2.getText();
				String author = t3.getText();
				String sql = "insert into book_info values('"+id+"','"+name+"','"+author+"',1,0,'/','/','/',0)";
				back.update(sql);
				
				JOptionPane.showMessageDialog(AddBookFrame.this, "入库成功！", "结果", JOptionPane.PLAIN_MESSAGE);
				
				t1.setText("");
				t2.setText("");
				t3.setText("");
			}
		});
		
		b2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				t1.setText("");
				t2.setText("");
				t3.setText("");
			}
		});
		
		b3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AddBookFrame.this.dispose();
			}
		});
	}
	
	public static void main(String[] args) {
		//new AddBookFrame().show();
	}

}
