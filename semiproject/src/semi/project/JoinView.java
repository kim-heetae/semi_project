package semi.project;

import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class JoinView extends JFrame implements ItemListener{

	//선언부
	JTextField 		jtf_id 			= new JTextField();
	JPasswordField 	jpf_pw 			= new JPasswordField();
	JPasswordField 	jpf_pw_confirm 	= new JPasswordField();
	JTextField 		jtf_name		= new JTextField();
	JTextField 		jtf_birth 		= new JTextField();
	JButton			jbtn_check		= new JButton("중복확인");
	JButton			jbtn_join		= new JButton("회원가입");
	JButton			jbtn_back		= new JButton("뒤로가기");
	JLabel			jlb_id			= new JLabel("ID");
	JLabel			jlb_pw			= new JLabel("PW");
	JLabel			jlb_pw_confirm	= new JLabel("PW Confirm");
	JLabel			jlb_name		= new JLabel("이름");
	JLabel			jlb_birth		= new JLabel("생년월일");
	JLabel			jlb_mem			= new JLabel("멤버십 가입");
	JCheckBox		jcb				= new JCheckBox();
	JTextArea		jta				= new JTextArea("멤버십에 가입하시면 적립과 할인 혜택을 받을 수 있습니다.");
	JComboBox		jcb_year		= new JComboBox();
	JComboBox		jcb_month		= new JComboBox();
	JComboBox		jcb_date		= new JComboBox();
	Font			myfont			= new Font("휴먼모음T", 0, 14);
	//데이터 처리 관련 선언
	DBConnectionMgr dbMgr = null;
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	//생성자
	public JoinView() {
		initDisplay();
	}
	
	//화면처리부
	public void initDisplay() {
		this.setLayout(null);
		jcb_year.addItemListener(this);
		jcb_month.addItemListener(this);
		jlb_id			.setFont(myfont);
		jlb_pw			.setFont(myfont);
		jlb_pw_confirm  .setFont(myfont);
		jlb_name		.setFont(myfont);
		jlb_birth		.setFont(myfont);
		jtf_id			.setFont(myfont);
		jpf_pw			.setFont(myfont);
		jpf_pw_confirm	.setFont(myfont);
		jtf_name		.setFont(myfont);
		jbtn_check		.setFont(myfont);
		jbtn_join		.setFont(myfont);
		jbtn_back		.setFont(myfont);
		jcb				.setFont(myfont);
		jlb_id			.setBounds(50, 30, 80, 20);
		jlb_id.setHorizontalAlignment(JLabel.RIGHT);
		jlb_pw			.setBounds(50, 70, 80, 20);
		jlb_pw.setHorizontalAlignment(JLabel.RIGHT);
		jlb_pw_confirm	.setBounds(50, 110, 80, 20);
		jlb_pw_confirm.setHorizontalAlignment(JLabel.RIGHT);
		jlb_name		.setBounds(50, 150, 80, 20);
		jlb_name.setHorizontalAlignment(JLabel.RIGHT);
		jlb_birth		.setBounds(50, 190, 80, 20);
		jlb_birth.setHorizontalAlignment(JLabel.RIGHT);
		jlb_mem			.setBounds(50, 230, 80, 20);
		jlb_mem.setHorizontalAlignment(JLabel.RIGHT);
		jtf_id			.setBounds(150, 30, 130, 20);
		jpf_pw			.setBounds(150, 70, 130, 20);
		jpf_pw_confirm	.setBounds(150, 110, 130, 20);
		jtf_name		.setBounds(150, 150, 130, 20);
		jbtn_check		.setBounds(295, 30, 100, 20);
		jcb_year		.setBounds(150, 190, 100, 20);
		jcb_month		.setBounds(260, 190, 60, 20);
		jcb_date		.setBounds(330, 190, 60, 20);
		jcb				.setBounds(150,230, 20, 20);
		jta				.setBounds(180,230, 220, 30);		
		jta.setLineWrap(true);
		jta.setEditable(false);
		jbtn_join		.setBounds(145, 285, 80, 30);
		jbtn_back		.setBounds(235, 285, 80, 30);
		this.add(jlb_id);
		this.add(jlb_pw);
		this.add(jlb_pw_confirm);
		this.add(jlb_name);
		this.add(jlb_birth);
		this.add(jtf_id);
		this.add(jpf_pw);
		this.add(jpf_pw_confirm);
		this.add(jtf_name);
		this.add(jbtn_check);
		this.add(jbtn_join);
		this.add(jbtn_back);
		this.add(jcb);
		this.add(jlb_mem);
		this.setSize(480, 390);
		this.setVisible(true);
		this.add(jcb_year);
		this.add(jcb_month);
		this.add(jcb_date);
		this.add(jta);
	}
	
	public void birth() {
		String sql_year = "SELECT J_YEAR FROM J_BIRTH";
		String sql_month = "SELECT J_MONTH FROM J_BIRTH";
		String sql_date = "SELECT J_DATE FROM J_BIRTH";
		try {
			dbMgr = DBConnectionMgr.getInstance();
			con = dbMgr.getConnection();
			pstmt = con.prepareStatement(sql_year);
			rs = pstmt.executeQuery();
			int i = 0;
			while(rs.next()) {
				jcb_year.insertItemAt(rs.getInt("j_year"), i);
				i++;
			}
			pstmt = con.prepareStatement(sql_month);
			rs = pstmt.executeQuery();
			i=0;
			while(rs.next()) {
				if(rs.getInt("j_month") > 0) {
					jcb_month.insertItemAt(rs.getInt("j_month"), i);
				}
				i++;
			}
		} catch (SQLException se) {

		}
		dbMgr.freeConnection(con, pstmt, rs);
	}
	public static void main(String[] args) {
		new JoinView().birth();
	}

	@Override
	public void itemStateChanged(ItemEvent ie) {
//		if(ie.getItem() == jcb_year) {
//			System.out.println("출력");
//		}
		Object obj = ie.getSource();
		if(obj == jcb_month) {
			if(ie.getStateChange() == ItemEvent.SELECTED) {
				if(jcb_month.getSelectedIndex() < 7) {//1월부터 7월
					if(jcb_month.getSelectedIndex() % 2 == 0) {//1,3,5,7월
						System.out.println("1,3,5,7");
					}
					else {//2,4,6월
						System.out.println("2,4,6");
					}
				}
				else {//8월부터 12월
					if(jcb_month.getSelectedIndex() % 2 == 1) {//8,10,12월
						System.out.println("8,10,12");
					}
					else {//9,11월
						System.out.println("9,11");
					}
				}
			}
		}
	}

}
