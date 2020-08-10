package acp.db.service;

import java.util.*;

import acp.db.utils.*;
import acp.utils.*;

public class SourceManagerList extends ManagerListStr {

  public SourceManagerList() {
    fields = new String[] { "msss_id", "msss_name", "msss_owner" };

    fieldnames = new String[] { "ID", Messages.getString("Column.Name"),
        Messages.getString("Column.Owner") };

    cntColumns = fields.length;

    tableName = "mss_source";
    pkColumn = "msss_id";
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
    String vOwner = mapFilter.get("owner");;
    // ----------------------------------
    String phWhere = null;
    String str = null;
    // ---
    if (!DbUtils.emptyString(vName)) {
      str = "upper(msss_name) like upper('" + vName + "%')";
      phWhere = DbUtils.strAddAnd(phWhere, str);
    }
    // ---
    if (!DbUtils.emptyString(vOwner)) {
      str = "upper(msss_owner) like upper('" + vOwner + "%')";
      phWhere = DbUtils.strAddAnd(phWhere, str);
    }
    // ---
    strWhere = DbUtils.strAddAnd(strAwhere, phWhere);
  }

}
