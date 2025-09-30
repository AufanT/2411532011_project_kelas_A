package src.table;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import src.model.Service;

public class TableService extends AbstractTableModel {
    List<Service> ls;
    private String[] columnNames = {"ID", "Jenis Layanan", "Harga", "Status"};

    public TableService(List<Service> ls) {
        this.ls = ls;
    }

    @Override
    public int getRowCount() {
        return ls.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return ls.get(rowIndex).getId();
            case 1:
                return ls.get(rowIndex).getJenis();
            case 2:
                return ls.get(rowIndex).getHarga();
            case 3:
                return ls.get(rowIndex).getStatus();
            default:
                return null;
        }
    }
}