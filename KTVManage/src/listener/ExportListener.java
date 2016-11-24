package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import dao.MyUtil;
import frame.ConfigDialog;
import frame.ExportDialog;

public class ExportListener implements ActionListener {
	JButton choose, sure, cancel;
	private String path;
	private String filePath;
	JFileChooser filechooser;
	JTextField fileTextField, fileName;

	public String getFilePath() {

		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public ExportListener(JButton choose, JButton sure, JButton cancel,
			JTextField fileTextField, JTextField fileName) {
		super();
		this.choose = choose;
		this.sure = sure;
		this.cancel = cancel;
		this.fileTextField = fileTextField;
		this.fileName = fileName;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == cancel) {
			ConfigDialog.instance().dispose();
		} else if (e.getSource() == sure) {
			String name = fileName.getText();
			if (name.endsWith(".xls"))
			       ;
			else
				name = name + ".xls";
			if (filePath.endsWith("/"))
				filePath = filePath + name;
			else
				filePath = filePath + "/" + name;

			try {
				HSSFWorkbook wb = new HSSFWorkbook();
				wb = MyUtil.exportForxls();
				wb.write(new FileOutputStream(new File(filePath)));
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String message = "保存的路径为" + path;
			ExportDialog.instance().dispose();
			JOptionPane.showMessageDialog(null, message);

		} else if (e.getSource() == choose) {
			filechooser = new JFileChooser(
					fileTextField.getText().equals("") ? "./Myxls/"
							: fileTextField.getText());
			filechooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			if (filechooser.showSaveDialog(ExportDialog.instance()) == JFileChooser.APPROVE_OPTION) {
				path = filechooser.getSelectedFile().getAbsolutePath();
				filePath = filechooser.getSelectedFile().getAbsolutePath();
				fileTextField.setText(filePath);
			}
		}

	}
}
