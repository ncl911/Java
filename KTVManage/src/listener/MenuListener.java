package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import frame.MainPanel;

public class MenuListener implements ActionListener {

	private JButton jbtRoomsBooked, jbtRoomsTaken, jbtRooms,
			jbtRoomTypes;

	public MenuListener(JButton jbtRoomsBooked, JButton jbtRoomsTaken,
		 JButton jbtRooms, JButton jbtRoomTypes) {
		this.jbtRoomsBooked = jbtRoomsBooked;
		this.jbtRoomsTaken = jbtRoomsTaken;
		this.jbtRooms = jbtRooms;
		this.jbtRoomTypes = jbtRoomTypes;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jbtRoomsBooked) {
			MainPanel.instance().showRoomsBookedData();
		} else if (e.getSource() == jbtRoomsTaken) {
			MainPanel.instance().showRoomTakenData();
		} else if (e.getSource() == jbtRooms) {
			MainPanel.instance().showAllRoomsData();
		} else if (e.getSource() == jbtRoomTypes) {
			MainPanel.instance().showRoomTypesData();
		} 
	}

}
