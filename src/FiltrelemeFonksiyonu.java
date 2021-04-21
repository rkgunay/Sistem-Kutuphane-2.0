import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class FiltrelemeFonksiyonu {
	@SuppressWarnings("unchecked")
	public void filtre(String sorgu, JTable tablo) {
		@SuppressWarnings("rawtypes")
		TableRowSorter<DefaultTableModel> trs = new TableRowSorter(tablo.getModel());
		tablo.setRowSorter(trs);
		trs.setRowFilter(RowFilter.regexFilter("(?i)"+sorgu));
	}
	    
}
