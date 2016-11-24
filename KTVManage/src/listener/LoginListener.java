package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import frame.ImageButton;
import frame.LoginFrame;
import frame.MainFrame;
import service.ManagerService;

public class LoginListener extends MouseAdapter implements ActionListener {

	private JTextField jtfUserName;
	private JPasswordField jpfPassword;
	private ImageButton ensure;
	private ImageButton cancel;
	
	private static ManagerService ms=null;
	static {
		if(ms==null){
			ms=new ManagerService();
		}
	}

	public LoginListener(JTextField jtfUserName, JPasswordField jpfPassword,
			ImageButton ensure, ImageButton cancel) {
		this.jtfUserName = jtfUserName;
		this.jpfPassword = jpfPassword;
		this.ensure = ensure;
		this.cancel = cancel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jtfUserName || e.getSource() == jpfPassword
				|| e.getSource() == ensure) {
			if (jtfUserName.getText().equals("")
					|| jpfPassword.getPassword().length == 0) {
				JOptionPane.showMessageDialog(null, "请输入用户名密码！", "提示",
						JOptionPane.ERROR_MESSAGE);

			} else if (ms.check(jtfUserName.getText(),
					String.valueOf(jpfPassword.getPassword()))) {
				 LoginFrame.instance().setVisible(false);
				 MainFrame.instance().open();
			} else {
				JOptionPane.showMessageDialog(null, "用户名或密码错误！", "提示ʾ",
						JOptionPane.ERROR_MESSAGE);
			}
		} else if (e.getSource() == cancel) {
			 System.exit(0);
		}
	}
}
