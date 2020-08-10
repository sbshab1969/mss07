package acp.forms.dm;

import java.util.ArrayList;
import java.util.List;

import acp.db.service.*;
import acp.forms.frame.DmPanel;

public class DmListStr extends DmPanel {
  private static final long serialVersionUID = 1L;

  private ManagerListStr tableManager;
  private List<String[]> cacheStr = new ArrayList<>();

  public DmListStr(ManagerListStr tblMng) {
    tableManager = tblMng;
    setHeaders();
  }

  public ManagerListStr getManager() {
    return tableManager;
  }

  public void setManager(ManagerListStr tblMng) {
    tableManager = tblMng;
    setHeaders();
  }
  
  private void setHeaders() {
    if (tableManager != null) {
      headers = tableManager.getHeaders();
      colCount = headers.length;
    } else {
      headers = new String[] {};
      colCount = 0;
    }
  }
  
  // --- AbstractTableModel ---
//  public String getColumnName(int i) {
//    return headers[i];
//  }

  public Class<?> getColumnClass(int columnIndex) {
    return String.class;
  }

  public boolean isCellEditable(int row, int col) {
    return false;
  }

  // --- TableModel ---
//  public int getColumnCount() {
//    return colCount;
//  }

  public int getRowCount() {
    return cacheStr.size();
  }

  public Object getValueAt(int row, int col) {
    return cacheStr.get(row)[col];
  }
  // --------------------------------------
  
  public void calcRecCount() {
    recCount = tableManager.countRecords();
  }

  public void fetchPage(int page) {
    int startRec = calcStartRec(page);
    cacheStr = tableManager.queryPartMove(startRec,recPerPage);
    fireTableChanged(null);
  }

  public void queryPage() {
    calcPageCount();
    if (currPage > pageCount) {
      currPage = pageCount;
    }  
    int startRec = calcStartRec(currPage);
    cacheStr = tableManager.queryPartOpen(startRec,recPerPage);
    fireTableChanged(null);
  }

  public void queryAll() {
    cacheStr = tableManager.queryAll();
    fireTableChanged(null);
  }

  public void queryClose() {
    tableManager.queryClose();
  }

}
