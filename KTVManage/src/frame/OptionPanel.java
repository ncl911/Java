package frame;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JPanel;

import listener.OptionListener;

public class OptionPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1613590806361203632L;
	private static OptionPanel optionPanel;
	
	static public OptionPanel instance(){
		if(optionPanel == null)
			optionPanel = new OptionPanel();
		return optionPanel;
	}

	public OptionPanel() {
		setLayout(new FlowLayout(FlowLayout.RIGHT, 30, 10));
		setSize(1366, 150);
		setOpaque(false);
		optionPanel = this;
		ImageButton jbtMin = new ImageButton("Min");
		ImageButton jbtRefresh = new ImageButton("refresh");
		ImageButton jbtConfig = new ImageButton("config");
		ImageButton jbtLock = new ImageButton("lock");
		ImageButton jbtExit = new ImageButton("exit");
		

		jbtRefresh.setToolTipText("刷新");
		jbtConfig.setToolTipText("设置");
		jbtLock.setToolTipText("注销");
		jbtExit.setToolTipText("退出");
		jbtMin.setToolTipText("最小化");
		

		jbtMin.setPreferredSize(new Dimension(80, 80));
		jbtRefresh.setPreferredSize(new Dimension(80, 80));
		jbtConfig.setPreferredSize(new Dimension(80, 80));
		jbtLock.setPreferredSize(new Dimension(80, 80));
		jbtExit.setPreferredSize(new Dimension(80, 80));
	

		OptionListener optionListener = new OptionListener(jbtMin,jbtRefresh, jbtConfig, jbtLock, jbtExit);
		jbtRefresh.addActionListener(optionListener);
		jbtConfig.addActionListener(optionListener);
		jbtLock.addActionListener(optionListener);
		jbtExit.addActionListener(optionListener);
		jbtMin.addActionListener(optionListener);
	
        add(jbtMin);
		add(jbtRefresh);
		add(jbtConfig);
		add(jbtLock);
		add(jbtExit);

	}
}
