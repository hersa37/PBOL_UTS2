package pbol.uts2.guiComponents.tables;

import pbol.uts2.database.Inventory;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.ListIterator;

public class InventoryTable extends JTable {
	Object[][] data;
	String[] columnNames;

	public InventoryTable(String[] columnNames, Object[][] data, int dateCol) {
		super(new DefaultTableModel(data, columnNames));
		this.columnNames = columnNames;

		setDefaultRenderer(Object.class, tableCellRenderer(dateCol));
	}

	private DefaultTableCellRenderer tableCellRenderer(int dateCol) {
		return new DefaultTableCellRenderer() {
			public Component getTableCellRendererComponent(JTable table, Object object, boolean isSelected, boolean hasFocus, int row, int column) {
				super.getTableCellRendererComponent(table, object, isSelected, hasFocus, row, column);

				Date returnDate = null;
				Date now = null;
				if (dateCol != 0) {
					returnDate = (Date) table.getModel().getValueAt(row, dateCol);
					now = new Date(System.currentTimeMillis());
				}
				if ((returnDate != null) && (now.compareTo(returnDate) == 1)) {
					setBackground(new Color(255, 119, 119));
					setForeground(Color.black);
				} else if (isSelected) {
					setBackground(new Color(37, 150, 190));
					setForeground(Color.black);
				} else {
					setBackground(table.getBackground());
					setForeground(table.getForeground());
				}
				return this;
			}
		};
	}

	public void setData(LinkedList<Inventory> inventories) {
		data = new Object[inventories.size()][columnNames.length];
		ListIterator<Inventory> listIterator = inventories.listIterator();
		int i = 0;
		switch (columnNames.length) {
			case 3:
				while (listIterator.hasNext()) {
					Inventory iterator = listIterator.next();
					Object[] objects = {
							iterator.getSKU(),
							iterator.getNama(),
							iterator.getSatuan()
					};
					data[i++] = objects;
				}
				break;
			case 5:
				while (listIterator.hasNext()) {
					Inventory iterator = listIterator.next();
					Object[] objects = {
							iterator.getSKU(),
							iterator.getNama(),
							iterator.getTanggal_keluar(),
							iterator.getTanggal_kembali(),
							iterator.getSatuan()
					};
					data[i++] = objects;
				}
				break;
			case 8:
				while (listIterator.hasNext()) {
					Inventory iterator = listIterator.next();
					Object[] objects = {
							iterator.getSKU(),
							iterator.getNama(),
							iterator.getHarga(),
							iterator.getTanggal_masuk(),
							iterator.getTanggal_keluar(),
							iterator.getTanggal_kembali(),
							iterator.getSatuan(),
							iterator.getPeminjam()
					};
					data[i++] = objects;
				}
				break;
		}
		this.setModel(new DefaultTableModel(data, columnNames));
	}

	public int getSKUValue() {
		return (int) getModel().getValueAt(getSelectedRow(), 0);
	}

	public void resetTable() {
		setData(new LinkedList<>());
	}

}
