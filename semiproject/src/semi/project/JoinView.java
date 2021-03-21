package semi.project;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class JoinView extends JFrame implements ItemListener, ActionListener, KeyListener {

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
	
	boolean			is_checkbox_selected = false;
	boolean			is_year_selected = false;
	boolean			is_month_selected = false;
	boolean			is_date_selected = false;
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
		jcb_date.addItemListener(this);
		jbtn_check.addActionListener(this);
		jbtn_join.addActionListener(this);
		jcb.addItemListener(this);
		jtf_id.addKeyListener(this);
		jlb_id			.setFont(myfont);
		jlb_pw			.setFont(myfont);
		jlb_pw_confirm  .setFont(myfont);
		jlb_name		.setFont(myfont);
		jlb_birth		.setFont(myfont);
		jtf_id			.setFont(myfont);
//		jpf_pw			.setFont(myfont);
//		jpf_pw_confirm	.setFont(myfont);
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
	
	public void birth(boolean isYearSelected) {
		String sql_year = "SELECT J_YEAR FROM J_BIRTH";
		String sql_month = "SELECT J_MONTH FROM J_BIRTH";
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
			if(isYearSelected == true) {
				while(rs.next()) {
					if(rs.getInt("j_month") > 0) {
						jcb_month.insertItemAt(rs.getInt("j_month"), i);
					}
					i++;
				}
			}
		} catch (SQLException se) {

		} finally {
			dbMgr.freeConnection(con, pstmt, rs);
		}
	}
	public static void main(String[] args) {
		new JoinView().birth(false);
	}

	public void initDate(ResultSet rs, int maxDate) {
		is_date_selected = false;
		int i = 0;
		jcb_date.removeAllItems();
		try {
			while(rs.next()) {
				if(rs.getInt("j_date") > 0 && rs.getInt("j_date") <= maxDate) {
					jcb_date.insertItemAt(rs.getInt("j_date"), i);
					i++;
				}
			}
		} catch (Exception e) { }
	}
	
	public boolean idCheck() {
		boolean isExistedID = false;
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT MEM_ID FROM CUSTBL WHERE MEM_ID = ?");
		try {
			dbMgr = DBConnectionMgr.getInstance();
			con = dbMgr.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, jtf_id.getText());
			rs = pstmt.executeQuery();
			if(rs.next()) {
				isExistedID = true;
			} else {
				isExistedID = false;
			}
		} catch (SQLException se) {
			System.out.println(se.toString());
		} finally {
			dbMgr.freeConnection(con, pstmt, rs);
		}
		return isExistedID;
	}
	
	
	
	@Override
	public void itemStateChanged(ItemEvent ie) {
//		if(ie.getItem() == jcb_year) {
//			System.out.println("출력");
//		}
		Object obj = ie.getSource();
		if(obj == jcb_year) {
			if(ie.getStateChange() == ItemEvent.SELECTED) {
				is_year_selected = true;
				birth(true);
			}
		}
		if(obj == jcb_month) {
			String sql_date = "SELECT J_DATE FROM J_BIRTH";
			if(ie.getStateChange() == ItemEvent.SELECTED) {
				is_month_selected = true;
				try{
					dbMgr = DBConnectionMgr.getInstance();
					con = dbMgr.getConnection();
					pstmt = con.prepareStatement(sql_date);
					rs = pstmt.executeQuery();
					if(jcb_month.getSelectedIndex() < 7) {//1월부터 7월
						if(jcb_month.getSelectedIndex() % 2 == 0) {//1,3,5,7월
							initDate(rs, 31);
						}
						else {//2,4,6월
							if(jcb_month.getSelectedIndex() == 1) {
								initDate(rs, 28);
							}
							else {
								initDate(rs, 30);
							}
						}
					}
					else {//8월부터 12월
						if(jcb_month.getSelectedIndex() % 2 == 1) {//8,10,12월
							initDate(rs, 31);
						}
						else {//9,11월
							initDate(rs, 30);
						}
					}
				}
				catch(SQLException se) {
					System.out.println(se.toString());
				}
				finally {
					dbMgr.freeConnection(con, pstmt, rs);
				}
			}
		}
		else if(obj == jcb_date) {
			if(ie.getStateChange() == ItemEvent.SELECTED) {
				is_date_selected = true;
			}
		}
		
		else if(obj == jcb) {
			if(ie.getStateChange() == ItemEvent.SELECTED) {
				is_checkbox_selected = true;
			}
			else if(ie.getStateChange() == ItemEvent.DESELECTED) {
				is_checkbox_selected = false;
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		Object obj = ae.getSource();
		if(obj == jbtn_check) {
			if(idCheck() == true) {	//아이디가 이미 있는 경우임.
				JOptionPane.showMessageDialog(this, "중복된 아이디입니다.");
			} else {
				JOptionPane.showMessageDialog(this, "사용가능한 아이디입니다.");
			}
		}
		else if(obj == jbtn_join) {
			if(idCheck() == true) {
				JOptionPane.showMessageDialog(this, "중복된 아이디입니다.");
			} else {
				if(jtf_id.getText().length() > 0
				&& jpf_pw.getText().equals(jpf_pw_confirm.getText())
				&& jtf_name.getText().length() > 0) {
					StringBuffer sql = new StringBuffer();
					sql.append("INSERT into custbl values(?,?,?,?,?,?,?)");
					try {
						dbMgr = DBConnectionMgr.getInstance();
						con = dbMgr.getConnection();
						pstmt = con.prepareStatement(sql.toString());
						pstmt.setString(1, jtf_id.getText());
						pstmt.setString(2, jpf_pw.getText());
						pstmt.setString(3, jtf_name.getText());
						if(!(is_year_selected || is_month_selected || is_date_selected))
						{
							pstmt.setString(4, null);
						}
						else {
								pstmt.setString(4, jcb_year.getSelectedItem().toString() + "-" 
								+ jcb_month.getSelectedItem().toString() + "-" 
								+ jcb_date.getSelectedItem().toString());
						}
						if(is_checkbox_selected == true) {
							pstmt.setString(5, "0");
							pstmt.setString(6, "0");
							pstmt.setString(7, "welcome");
						} else {
							pstmt.setString(5, null);
							pstmt.setString(6, null);
							pstmt.setString(7, null);
						}
						pstmt.executeUpdate();
					} catch (SQLException se) {
						System.out.println(se.toString());
					} finally {
						dbMgr.freeConnection(con, pstmt, rs);
					}
					JOptionPane.showMessageDialog(this, "가입되었습니다.");					
				} else {
					JOptionPane.showMessageDialog(this, "누락된 항목이 있습니다.");
				}
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent ke) {
		Object obj = ke.getSource();
		if(obj == jtf_id) {
			if(ke.getKeyCode() == KeyEvent.VK_ENTER) {
				if(idCheck() == true) {	//아이디가 이미 있는 경우임.
					JOptionPane.showMessageDialog(this, "중복된 아이디입니다.");
					System.out.println(jpf_pw.getText() + ", " + jpf_pw_confirm.getText());
				} else {
					JOptionPane.showMessageDialog(this, "사용가능한 아이디입니다.");
				}
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
