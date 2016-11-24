package frame;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import listener.ConfigListener;
import dao.ConfigDao;
import entity.Config;

public class ConfigDialog extends JDialog {
	private static final long serialVersionUID = 5016876596940564305L;
	private static ConfigDialog configDialog;
	private JTextField jtfName, jtfHoursAdded, jtfBg;

	public static ConfigDialog instance() {
		if (configDialog == null)
			configDialog = new ConfigDialog();
		return configDialog;
	}

	public ConfigDialog() {
		super(MainFrame.instance(), "系统设置", true);
		getContentPane().setLayout(null);
		setSize(350, 300);
		setLocationRelativeTo(null);
		configDialog = this;
		JLabel jlbName = new JLabel("KTV名称：");
		JLabel jlbHoursAdded = new JLabel("开房时长");
		JLabel jlbBg = new JLabel("背景图片");
		JButton choose = new JButton("浏览");
		JButton ensure = new JButton("确定");
		JButton cancel = new JButton("取消");

		jtfName = new JTextField();
		jtfHoursAdded = new JTextField();
		jtfBg = new JTextField();

		jlbName.setBounds(60, 30, 85, 30);
		jlbBg.setBounds(60, 115, 85, 30);
		jtfName.setBounds(130, 26, 190, 40);
		jtfHoursAdded.setBounds(130, 206, 190, 40);
		jtfBg.setBounds(132, 111, 130, 40);
		choose.setBounds(272, 111, 60, 40);
		ensure.setBounds(99, 204, 70, 40);
		cancel.setBounds(212, 204, 70, 40);
		ConfigListener configListener=new ConfigListener(jtfName,jtfBg, choose, cancel);
		jtfName.addActionListener(configListener);
		jtfBg.addActionListener(configListener);
		choose.addActionListener(configListener);
		ensure.addActionListener(configListener);
		cancel.addActionListener(configListener);

		getContentPane().add(jlbName);
		getContentPane().add(jlbBg);
		getContentPane().add(jtfName);
		getContentPane().add(jtfBg);
		getContentPane().add(choose);
		getContentPane().add(ensure);
		getContentPane().add(cancel);
	}

	public void open() {
		Config config = ConfigDao.instance().getConfig();
		jtfName.setText(config.getName());
		jtfBg.setText(config.getBackground());
		setVisible(true);
	}
}
