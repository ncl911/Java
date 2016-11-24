package frame;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import listener.ExportListener;
import java.awt.Color;
import java.awt.Font;
@SuppressWarnings("serial")
public class ExportDialog extends JDialog{
	private static ExportDialog exportDialog;
	public static ExportDialog instance(){
		if(exportDialog==null){
			exportDialog=new ExportDialog();
		}
		return exportDialog;
	}
	public ExportDialog() {
		super(MainFrame.instance(), "选择保存路径", true);
		getContentPane().setBackground(Color.LIGHT_GRAY);
		getContentPane().setLayout(null);
		setSize(300, 320);
		setLocationRelativeTo(null);
		exportDialog = this;
		JLabel JlbName = new JLabel("保存为");
		JTextField fileName=new JTextField();
		JTextField fileTextField=new JTextField();
	    JButton choose=new JButton("浏览");
	    choose.setFont(new Font("宋体", Font.PLAIN, 12));
	    JButton sure=new JButton("确定");
	    JButton cancel=new JButton("取消");
	    JlbName.setBounds(10, 118, 85, 30);
	    fileName.setBounds(58, 119, 211, 30);
	    fileTextField.setBounds(10, 51, 211, 30);
		choose.setBounds(223, 51, 59, 30);
		sure.setBounds(44, 202, 70, 30);
		cancel.setBounds(188, 202, 70, 30);
		ExportListener ex=new ExportListener(choose, sure, cancel,fileTextField,fileName);
		sure.addActionListener(ex);
		cancel.addActionListener(ex);
		choose.addActionListener(ex);
		getContentPane().add(choose);
		getContentPane().add(sure);
		getContentPane().add(cancel);
		getContentPane().add(fileTextField);
		getContentPane().add(JlbName);
		getContentPane().add(fileName);
	}
	public void open(){
		exportDialog.setVisible(true);
	}
	
}
