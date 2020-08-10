package acp.db.service;

import java.util.ArrayList;

import acp.db.utils.*;
import acp.utils.*;

public class ToptionManagerList extends ManagerListStr {

  private String path;
  private ArrayList<String> attrs;
  private int attrSize;
  private int attrMax = 5;
  private String attrPrefix;

  public ToptionManagerList(String path, ArrayList<String> attrs) {
    this.path = path;
    this.attrs = attrs;
    this.attrSize = attrs.size();
    String[] pathArray = path.split("/");
    this.attrPrefix = pathArray[pathArray.length - 1];

    createFields();
    cntColumns = fields.length;
    
    strFields = DbUtils.buildSelectFields(fields, null);
    // strFrom = null;
    createTable(-1);  
    strWhere = strAwhere;
    strOrder = pkColumn;
    // -------------
    prepareQuery();
    // -------------
  }

  private void createFields() {
    fields = new String[attrSize + 3];
    fieldnames = new String[attrSize + 3];
    // ---
    int j = 0;
    fields[j] = "CONFIG_ID";
    fieldnames[j] = "ID";
    pkColumn = fields[j];
    // ---
    for (int i = 0; i < attrSize; i++) {
      j++;
      fields[j] = "P" + j;
      fieldnames[j] = FieldConfig.getString(attrPrefix + "." + attrs.get(i));
    }
    // ---
    j++;
    fields[j] = "to_char(DATE_BEGIN,'dd.mm.yyyy') DATE_BEGIN";
    fieldnames[j] = Messages.getString("Column.DateBegin");
    // ---
    j++;
    fields[j] = "to_char(DATE_END,'dd.mm.yyyy') DATE_END";
    fieldnames[j] = Messages.getString("Column.DateEnd");
    // ---
  }

  public void createTable(long src) {
    String res = "table(mss.spr_options(" + src + ",'" + path + "'";
    for (int i = 0; i < attrSize; i++) {
      res += ",'" + attrs.get(i) + "'";
    }
    for (int i = attrSize; i < attrMax; i++) {
      res += ",null";
    }
    res += "))";
    strFrom = res;
  }

  public String selectSources() {
    String query = "select msss_id, msss_name from mss_source order by msss_name";
    return query;
  }

}
