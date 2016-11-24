package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import dao.ConfigDao;
import entity.Config;
import frame.ConfigDialog;

public class ConfigListener implements ActionListener {
	private JTextField jtfName, jtfBg;
	private JButton choose, cancel;

	public ConfigListener(JTextField jtfName, 
		 JTextField jtfBg,
			JButton choose, JButton cancel) {
		super();
		this.jtfName = jtfName;
		this.jtfBg = jtfBg;
		this.choose = choose;
		this.cancel = cancel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == cancel) {
			ConfigDialog.instance().dispose();
		} else if (e.getSource() == choose) {
			JFileChooser filechooser = new JFileChooser(jtfBg.getText().equals(
					"") ? "./images/" : jtfBg.getText());
			FileNameExtensionFilter filter = new FileNameExtensionFilter("图片文件", "jpg", "jpeg", "gif", "bmp", "png");
			filechooser.setFileFilter(filter);
			if (filechooser.showOpenDialog(ConfigDialog.instance()) == JFileChooser.OPEN_DIALOG) {
				jtfBg.setText(filechooser.getSelectedFile().getAbsolutePath());
			}
		} else {
			if (jtfName.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "请填写完整信息！");
				return;
			}
			Config config = new Config(jtfName.getText(), jtfBg.getText());
			ConfigDao.instance().setConfig(config);
			ConfigDialog.instance().dispose();
			JOptionPane.showMessageDialog(null, "设置成功！");
		}
	}
}
