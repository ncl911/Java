package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.table.DefaultTableModel;

import service.BookingService;
import service.RoomService;
import service.RoomTypeService;
import entity.Charge;
import entity.Room;
import entity.RoomType;
import frame.AddHoursDialog;
import frame.AddRoomDialog;
import frame.AddRoomTypeDialog;
import frame.BookRoomDialog;
import frame.DataTable;
import frame.EditRoomTypeDialog;
import frame.ExportDialog;
import frame.MainPanel;

public class TableListener extends MouseAdapter implements ActionListener {
	private DataTable table;
	private JPopupMenu menu;
	private ExportDialog exportDialog;
	private static RoomTypeService rts = null;
	private static RoomService roomService = null;
	private static BookingService bs = null;
	static {
		if (rts == null) {
			rts = new RoomTypeService();
		}
		if (roomService == null) {
			roomService = new RoomService();
		}
		if (bs == null) {
			bs = new BookingService();
		}
	}

	public TableListener(JPopupMenu menu) {
		this.table = MainPanel.instance().getTable();
		this.menu = menu;
	}

	public void mousePressed(MouseEvent e) {
		if (table.getSelectedRow() < 0) {
			int modifiers = e.getModifiers();
			modifiers -= MouseEvent.BUTTON3_MASK;
			modifiers |= MouseEvent.BUTTON1_MASK;
			MouseEvent ne = new MouseEvent(e.getComponent(), e.getID(), e.getWhen(), modifiers, e.getX(), e.getY(),
					e.getClickCount(), false);
			table.dispatchEvent(ne);
		}
	}

	public void mouseReleased(MouseEvent e) {
		if ((e.getModifiers() & MouseEvent.BUTTON3_MASK) != 0 && !e.isControlDown() && !e.isShiftDown()) {
			menu.show(table, e.getX(), e.getY());
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (table.getSelectedRow() < 0)
			return;
		String strAction = ((JMenuItem) e.getSource()).getText().trim();
		if (!isSure("<html>您确定要进行<b><font size=6> " + strAction + " </font></b>操作吗？"))
			return;
		if (strAction.equals("房间结算")) {
			Date startTime=null;
			Date endTime=null;
		    try {
				startTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(getValue(table.getSelectedRow(), 2));
				endTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(getValue(table.getSelectedRow(), 3));
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
		    Charge c=roomService.charge(Integer.parseInt(getValue(table.getSelectedRow(), 0)));
		    int money=(endTime.getHours()-startTime.getHours())*c.getPriveAdded()+c.getPrice();
			JOptionPane.showMessageDialog(null, "<html>结算成功！总消费<b><font size=8>  " + String.valueOf(money) + " </font></b>元");
			roomService.endRoom(Integer.parseInt(getValue(table.getSelectedRow(), 0)));
			removeSelectedRow();
			return;
		} else if (strAction.equals("房间加钟")) {
			Room room=roomService.getRoomById(Integer.parseInt(getValue(table.getSelectedRow(), 0)));
			AddHoursDialog.instance().open(room);
			return;	
		} else if (strAction.equals("开通房间")) {
			roomService.takenRoom(Integer.parseInt(getValue(table.getSelectedRow(), 0)), "Y");
			Date startDate=new Date();
			Date endDate=new Date();
			String takenTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(startDate);//得到开房时间
			int bookHours=Integer.parseInt(getValue(table.getSelectedRow(), 3));//获取预定时长
			endDate.setHours(startDate.getHours()+bookHours);//计算出房间过期的时间（开放时间+预定时长）
			String endTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(endDate);
			roomService.setTime(Integer.parseInt(getValue(table.getSelectedRow(), 0)),takenTime,endTime);
			roomService.updateBooked(Integer.parseInt(getValue(table.getSelectedRow(), 0)), 0);
			bs.deleteBookingById(Integer.parseInt(getValue(table.getSelectedRow(), 0)));
			JOptionPane.showMessageDialog(null,
					"<html>开通房间成功！房间号：<b><font size=8>  " +getValue(table.getSelectedRow(), 0) + " </font></b>");
			removeSelectedRow();
			return;
		} else if (strAction.equals("删除订单")) {
			bs.deleteBookingById(Integer.parseInt(getValue(table.getSelectedRow(), 0)));
			roomService.updateBooked(Integer.parseInt(getValue(table.getSelectedRow(), 0)), 0);
			removeSelectedRow();

		} else if (strAction.equals("预定房间")) {
			if (Integer.parseInt(getValue(table.getSelectedRow(), 2)) == 0&&"N".equals(getValue(table.getSelectedRow(), 6))) {
				String roomNum = getValue(table.getSelectedRow(), 0);
				BookRoomDialog.instance().open(roomNum);
			} else if(Integer.parseInt(getValue(table.getSelectedRow(), 2)) == 1){
				JOptionPane.showMessageDialog(null, "该房间已被预定！");
			}else{
				JOptionPane.showMessageDialog(null, "该房间已在使用！");
			}
			return;
		} else if (strAction.equals("添加房间")) {
			AddRoomDialog.instance().open();
			return;
		} else if (strAction.equals("删除房间")) {
			roomService.deleteRoomById(Integer.parseInt(getValue(table.getSelectedRow(), 0)));
			removeSelectedRow();
		} else if (strAction.equals("添加类型")) {
			AddRoomTypeDialog.instance().open();
			return;
		} else if (strAction.equals("编辑类型")) {
			RoomType roomType = rts.getRoomTypeInfoById(Integer.parseInt(getValue(table.getSelectedRow(), 0)));
			if (roomType == null)
				return;
			EditRoomTypeDialog.instance().open(roomType);
			return;
		} else if (strAction.equals("删除类型")) {
			rts.deleteRoomTypeById(Integer.parseInt(getValue(table.getSelectedRow(), 0)));
			removeSelectedRow();
		}else if(strAction.equals("导出表格数据")){
			exportDialog.instance().open();
			return;
			
		}
		JOptionPane.showMessageDialog(null, strAction + "成功！");
	}

	private boolean isSure(String msg) {
		return (JOptionPane.showConfirmDialog(null, msg, "消息", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION);
	}

	private String getValue(int row, int column) {
		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		return tableModel.getValueAt(row, column).toString();
	}

	private String getSelectedValue(int column) {
		if (table.getSelectedRow() < 0)
			return null;
		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		return tableModel.getValueAt(table.getSelectedRow(), column).toString();
	}

	private void removeSelectedRow() {
		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		tableModel.removeRow(table.getSelectedRow());
	}

	private void removeSelectedRows() {
		if (table.getSelectedRow() < 0)
			return;
		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		boolean isEndSelect = false;
		if (table.getSelectedRows()[table.getSelectedRows().length - 1] == tableModel.getRowCount() - 1) {
			isEndSelect = true;
		}
		while (table.getSelectedRow() >= 0) {
			tableModel.removeRow(table.getSelectedRow());
		}
		if (isEndSelect && tableModel.getRowCount() > 0) {
			tableModel.removeRow(tableModel.getRowCount() - 1);
		}
		if (tableModel.getRowCount() <= 0) {
			MainPanel.instance().refresh();
		}
	}

	
}
