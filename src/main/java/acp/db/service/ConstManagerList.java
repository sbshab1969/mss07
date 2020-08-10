package acp.db.service;

import java.util.*;

import acp.db.utils.*;
import acp.utils.*;

public class ConstManagerList extends ManagerListStr {

  public ConstManagerList() {
    fields = new String[] { "mssc_id", "mssc_name", "mssc_value" };

    fieldnames = new String[] {"ID", Messages.getString("Column.Name"),
                 Messages.getString("Column.Value") };

    cntColumns = fields.length;

    tableName = "mss_const";
    pkColumn = "mssc_id";
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
      str = "upper(mssc_name) like upper('" + vName + "%')";
      phWhere = DbUtils.strAddAnd(phWhere, str);
    }
    strWhere = DbUtils.strAddAnd(strAwhere, phWhere);
  }

}
