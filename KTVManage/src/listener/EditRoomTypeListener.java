package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import entity.RoomType;
import frame.AddRoomTypeDialog;
import frame.EditRoomTypeDialog;
import frame.MainPanel;
import service.RoomTypeService;

public class EditRoomTypeListener implements ActionListener {
	private JTextField jtfTypeId, jtfRoomType, jtfRoomPrice, jtfPriceAdded;
	private JButton cancel;
	private static RoomTypeService rts=null;
	static {
		if(rts==null){
			rts=new RoomTypeService();
		}
	}

	public EditRoomTypeListener(JTextField jtfTypeId, JTextField jtfRoomType,
			JTextField jtfRoomPrice, JTextField jtfPriceAdded, JButton cancel) {
		this.jtfTypeId = jtfTypeId;
		this.jtfRoomType = jtfRoomType;
		this.jtfRoomPrice = jtfRoomPrice;
		this.jtfPriceAdded = jtfPriceAdded;
		this.cancel = cancel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == cancel) {
			AddRoomTypeDialog.instance().dispose();
		} else {
			if (jtfRoomType.getText().equals("")
					|| jtfRoomPrice.getText().equals("")
					|| jtfPriceAdded.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "请填写完整信息！");
				return;
			}
			try {
				rts.EditRoomTypeInfo(
						Integer.parseInt(jtfTypeId.getText()),
						new RoomType(Integer.parseInt(jtfTypeId.getText()),jtfRoomType.getText(), Integer
								.parseInt(jtfRoomPrice.getText()), Integer
								.parseInt(jtfPriceAdded.getText())));
			} catch (NumberFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
			MainPanel.instance().refresh();
			EditRoomTypeDialog.instance().dispose();
			JOptionPane.showMessageDialog(null, "修改成功！");
		}
	}
}
