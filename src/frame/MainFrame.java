package frame;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.borland.jbcl.layout.XYConstraints;
import com.borland.jbcl.layout.XYLayout;

public class MainFrame extends JFrame {

	public static final int WIDTH = 900;
	public static final int HEIGHT = 645;
	
	private Container contentPane;
	private JPanel p1;
	
	private JButton leftButton;
	private JButton b1;
	private JButton b2;
	private JButton b3;
	private JButton b4;
	private JButton b5;
	private JButton b6;

	private XYLayout layout;
	
	public MainFrame() {
		this.setSize(WIDTH, HEIGHT);
		this.setTitle("图书管理系统");
		//this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// 背景图片  
        ImageIcon background = new ImageIcon("D:\\Jack\\Workspace\\eclipse\\LibraryManageSystem\\img\\bg.png");  
        // 把背景图片显示在一个标签里面  
        JLabel label = new JLabel(background);  
        // 把标签的大小位置设置为图片刚好填充整个面板  
        label.setBounds(0, 5,900,645);  
        // 把内容窗格转化为JPanel，否则不能用方法setOpaque()来使内容窗格透明  
        JPanel imagePanel = (JPanel) this.getContentPane();  
        imagePanel.setOpaque(false);  
        // 把背景图片添加到分层窗格的最底层作为背景  
        this.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));  
        //设置可见  
        setVisible(true); 
		//对应为Window
		/*UIManager.LookAndFeelInfo info = UIManager.getInstalledLookAndFeels()[3];
		try {
		    UIManager.setLookAndFeel(info.getClassName());
		    SwingUtilities.updateComponentTreeUI(this);
		} catch (Exception e) {
		    e.printStackTrace();
		}*/

		contentPane = getContentPane();
		layout = new XYLayout();
		contentPane.setLayout(layout);
		
		initLeftButton();
		initRightPanel();
		
		addRightListener();
	}
	
	public void initLeftButton() {
		leftButton = new JButton("");
		leftButton.setContentAreaFilled(false);
		contentPane.add(leftButton, new XYConstraints(5, 5, 590, 590));
	}
	
	public void initRightPanel() {
		p1 = new JPanel();
		p1.setBackground(null);
		p1.setOpaque(false);
		b1 = new JButton("图书入库");
		b1.setContentAreaFilled(false);//按钮虚化，透明
		b1.setBorder(BorderFactory.createRaisedBevelBorder());//凸形按钮
		b1.setFont(new  java.awt.Font("华文新魏",1,40));
		
		b2 = new JButton("图书出库");
		b2.setContentAreaFilled(false);
		b2.setBorder(BorderFactory.createRaisedBevelBorder());
		b2.setFont(new  java.awt.Font("华文新魏",1,40));
		
		b3 = new JButton("借书模块");
		b3.setBorder(BorderFactory.createRaisedBevelBorder());
		b3.setContentAreaFilled(false);
		b3.setFont(new  java.awt.Font("华文新魏",1,40));
		
		b4 = new JButton("还书模块");
		b4.setBorder(BorderFactory.createRaisedBevelBorder());
		b4.setContentAreaFilled(false);
		b4.setFont(new  java.awt.Font("华文新魏",1,40));
		
		b5 = new JButton("超期记录");
		b5.setBorder(BorderFactory.createRaisedBevelBorder());
		b5.setContentAreaFilled(false);
		b5.setFont(new  java.awt.Font("华文新魏",1,40));
		
		b6 = new JButton("退出系统");
		b6.setBorder(BorderFactory.createRaisedBevelBorder());
		b6.setContentAreaFilled(false);
		b6.setFont(new  java.awt.Font("华文新魏",1,40));
		
		GridLayout gl = new GridLayout(6, 1, 10, 10);
		p1.setLayout(gl);
		p1.add(b1);
		p1.add(b2);
		p1.add(b3);
		p1.add(b4);
		p1.add(b5);
		p1.add(b6);
		
		contentPane.add(p1, new XYConstraints(600, 5, 275, 590));
	}
	
	public void addRightListener() {
		
		//入库
		b1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ManageFrame().show();
			}
		});
		
		//出库
		b2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new RemoveBookFrame().show();
			}
		});

		//借书模块
		b3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new LendBookFrame().show();
			}
		});
		
		//还书模块
		b4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ReturnBookFrame().show();
			}
		});
		
		//超期记录
		b5.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ExceedRecords().show();
			}
		});
		
		//退出系统
		b6.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}
	
	public static void main(String[] args) {
		new MainFrame().show();
	}
}
