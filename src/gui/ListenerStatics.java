package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gameData.FilterOption;
import gameData.SQLPull;

public class ListenerStatics implements ActionListener {

	FilterOption option;
	
	public ListenerStatics(FilterOption option) {
		this.option = option;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		SQLPull sql = new SQLPull(new StaticsWindow());
		sql.connect(option);
	}

}
