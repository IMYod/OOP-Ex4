package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gameData.FilterOption;
import gameData.SQLPull;
/**
 * This class allows to know which option of statics the user want
 * by the listener.
 * @author Elad and Yoav.
 *
 */
public class ListenerStatics implements ActionListener {

	FilterOption option;
	
////////////////////////***Constructor****///////////////////////////////////////////

	public ListenerStatics(FilterOption option) {
		this.option = option;
	}
	
	@Override
	/**
	 * This method make the Action event alive
	 */
	public void actionPerformed(ActionEvent e) {
		SQLPull sql = new SQLPull(new StaticsWindow());
		sql.connect(option);
	}

}
