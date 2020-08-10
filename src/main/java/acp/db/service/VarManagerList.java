package acp.db.service;

import java.util.*;

import acp.db.utils.*;
import acp.utils.*;

public class VarManagerList extends ManagerListStr {

  public VarManagerList() {
    fields = new String[] { "mssv_id", "mssv_name", "mssv_type", "mssv_valuen",
        "mssv_valuev", "to_char(mssv_valued,'dd.mm.yyyy') mssv_valued" };

    fieldnames = new String[] { "ID", Messages.getString("Column.Name"),
        Messages.getString("Column.Type"), Messages.getString("Column.Number"),
        Messages.getString("Column.Varchar"), Messages.getString("Column.Date") };
    
    cntColumns = fields.length;

    tableName = "mss_vars";
    pkColumn = "mssv_id";
    strAwhere = null;
    seqId = 1000;

    strFields = DbUtils.buildSelectFields(fields, null);
    strFrom = tableName;
    strWhere = strAwhere;
    strOrder = pkColumn;
    // ------------
    prepareQuery();
    // ------------
  }

  public void setWhere(Map<String,String> mapFilter) {
    // ----------------------------------
    String vName = mapFilter.get("name"); 
    // ----------------------------------
    String phWhere = null;
    String str = null;
    // ---
    if (!DbUtils.emptyString(vName)) {
      str = "upper(mssv_name) like upper('" + vName + "%')";
      phWhere = DbUtils.strAddAnd(phWhere, str);
    }
    // ---
    strWhere = DbUtils.strAddAnd(strAwhere, phWhere);
  }

}
