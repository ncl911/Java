package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import frame.ConfigDialog;
import frame.ImageButton;
import frame.LoginFrame;
import frame.MainFrame;
import frame.MainPanel;

public class OptionListener implements ActionListener{
	JButton jbtRefresh;
	JButton jbtConfig;
	JButton jbtLock;
	JButton jbtExit;
	JButton jbtMin;


	public OptionListener(JButton jbtMin,JButton jbtRefresh, JButton jbtConfig, JButton jbtLock,
			JButton jbtExit) {
		super();
		this.jbtMin=jbtMin;
		this.jbtRefresh = jbtRefresh;
		this.jbtConfig = jbtConfig;
		this.jbtLock = jbtLock;
		this.jbtExit = jbtExit;
	
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	    if(e.getSource() == jbtRefresh){
			MainPanel.instance().refresh();
		}else if(e.getSource() == jbtConfig){
			ConfigDialog.instance().open();
		}else if(e.getSource() == jbtLock){
			LoginFrame.instance().open();
			MainFrame.instance().dispose();
		}else if(e.getSource() == jbtExit){
			System.exit(0);
		}else if(e.getSource()==jbtMin){
			MainFrame.instance().setExtendedState(MainFrame.instance().ICONIFIED);
		}
	}
}
