package acp.db.service;

import java.util.*;

import acp.db.utils.*;
import acp.utils.*;

public class ConfigManagerList extends ManagerListStr {

  public ConfigManagerList() {
    fields = new String[] { "msso_id", "msso_name", "msss_name",
      "to_char(msso_dt_begin,'dd.mm.yyyy')",
      "to_char(msso_dt_end,'dd.mm.yyyy')", "msso_comment", "msso_owner" };

    fieldnames = new String[] { "ID", Messages.getString("Column.Name"),
      Messages.getString("Column.SourceName"),
      Messages.getString("Column.DateBegin"),
      Messages.getString("Column.DateEnd"),
      Messages.getString("Column.Comment"), Messages.getString("Column.Owner") };

    cntColumns = fields.length;

    tableName = "mss_options";
    pkColumn = "msso_id";
    strAwhere = "msso_msss_id=msss_id";
    seqId = 1000;

    strFields = DbUtils.buildSelectFields(fields, null);
    strFrom = "mss_options, mss_source";
    strWhere = strAwhere;
    strOrder = pkColumn;
    // ------------
    prepareQuery();
    // ------------
  }

  public void setWhere(Map<String,String> mapFilter) {
    // ----------------------------------
    String vName = mapFilter.get("name"); 
    String vOwner = mapFilter.get("owner"); 
    String vSource = mapFilter.get("source"); 
    // ----------------------------------
    String phWhere = null;
    String str = null;
    // ---
    if (!DbUtils.emptyString(vName)) {
      str = "upper(msso_name) like upper('" + vName + "%')";
      phWhere = DbUtils.strAddAnd(phWhere, str);
    }
    // ---
    if (!DbUtils.emptyString(vOwner)) {
      str = "upper(msso_owner) like upper('" + vOwner + "%')";
      phWhere = DbUtils.strAddAnd(phWhere, str);
    }
    // ---
    if (!DbUtils.emptyString(vSource)) {
      str = "msso_msss_id=" + vSource;
      phWhere = DbUtils.strAddAnd(phWhere, str);
    }
    // ---
    strWhere = DbUtils.strAddAnd(strAwhere, phWhere);
  }

}
