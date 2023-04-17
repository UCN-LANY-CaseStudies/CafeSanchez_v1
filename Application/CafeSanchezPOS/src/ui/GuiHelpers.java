package ui;

import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ListModel;

public class GuiHelpers {

	public static <T> ListModel<T> mapToListModel(List<T> list) {

		DefaultListModel<T> listModel = new DefaultListModel<>();
		for (int idx = 0; idx < list.size(); idx++) {
			listModel.addElement(list.get(idx));
		}
		return listModel;
	}

	public static <T> ComboBoxModel<T> mapToComboBoxModel(List<T> list) {

		DefaultComboBoxModel<T> comboBoxModel = new DefaultComboBoxModel<>();
		if (list != null) {
			for (int idx = 0; idx < list.size(); idx++) {
				comboBoxModel.addElement(list.get(idx));
			}
		}
		return comboBoxModel;
	}
}
