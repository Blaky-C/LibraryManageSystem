package frame;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.borland.jbcl.layout.XYConstraints;
import com.borland.jbcl.layout.XYLayout;

import condb.ConOra;

public class RegisterFrame extends JFrame{
	public static final int  WIDTH = 900;
	public static final int HEIGHT = 645;
	private Container contentPane;
	private JPanel p1;
	private JPanel p2;
	private JPanel p3;
	private JButton leftButton;
	private JButton b1;
	private JLabel l1;
	private JLabel l2;
	private JTextField t1;
	private JTextField t2;
	private ConOra conOra = null;
	private XYLayout layout;
	public RegisterFrame(){
		this.setSize(WIDTH, HEIGHT);
		this.setResizable(false);
		this.setTitle("图书管理登录界面");
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
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contentPane = getContentPane();	        
        layout = new XYLayout();
        contentPane.setLayout(layout);                                
        initLeftButton();
        initRightPanel();        
        //监听器
        addRightListener();        
	}	
	
	public void initLeftButton(){
		leftButton = new JButton();
		leftButton.setContentAreaFilled(false);
		contentPane.add(leftButton,new XYConstraints(5, 5, 590, 590));		
	}	
	
	public void initRightPanel(){
		p1 = new JPanel();//右边的大面板
		p1.setBackground(null);
		p1.setOpaque(false);
		p1.setLayout(layout);
		l1 = new JLabel("账号");
		l1.setFont(new  java.awt.Font("华文行楷",1,30));
		t1 = new JTextField();
		l2 = new JLabel("密码");
		l2.setFont(new  java.awt.Font("华文行楷",1,30));
		t2 = new JPasswordField();
		b1 = new JButton("登录");
		b1.setFont(new  java.awt.Font("华文新魏",1,20));
		
		p1.add(l1,new XYConstraints(30, 130,150,90));
		p1.add(t1,new XYConstraints(100, 160,160,30));
		p1.add(l2,new XYConstraints(30,180,150,90));
		p1.add(t2,new XYConstraints(100,210,160,30));
		p1.add(b1,new XYConstraints(140,260,80,30));
		
		contentPane.add(p1,new XYConstraints(600, 5, 275, 590));
	}
	
	//监听各个按钮
	public void addRightListener(){
		b1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				login();
			}
		});
	}
	
	public void login(){
		String id = t1.getText();
		String pw = t2.getText();
		conOra = new ConOra();
		conOra.getCon();
		conOra.getStatement();
		
		String sql = "select * from people_list where people_id='"+id+"' and people_pw='"+pw+"'";
		conOra.query(sql);
		ResultSet result = conOra.getResult();
		try {
			if (result.next()) {
				JOptionPane.showMessageDialog(RegisterFrame.this, "登录成功！！！", "提示消息", JOptionPane.WARNING_MESSAGE);
				new MainFrame().show();
			}else {
				JOptionPane.showMessageDialog(RegisterFrame.this, "您输入的账号或者密码有误，请重新输入!", "提示消息", JOptionPane.WARNING_MESSAGE);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.dispose();
		this.t1.setText("");
		this.t2.setText("");
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new RegisterFrame().show();
	}

}
