package acp.db.service;

import java.util.*;

import acp.db.utils.*;
import acp.utils.*;

public class FileLoadManagerList extends ManagerListStr {

  public FileLoadManagerList() {
    fields = new String[] { "mssf_id", "mssf_name", "mssf_md5", "mssf_owner",
      "to_char(mssf_dt_work,'dd.mm.yyyy hh24:mi:ss') mssf_dt_work"
       ,"mssf_rec_all"
//      ,"extract(mssf_statistic,'statistic/records/all/text()').getStringval() rec_count"
    };

    fieldnames = new String[] { "ID", Messages.getString("Column.FileName"),
      "MD5", Messages.getString("Column.Owner"),
      Messages.getString("Column.DateWork"),
      Messages.getString("Column.RecordCount") };

    cntColumns = fields.length;

    tableName = "mss_files";
    pkColumn = "mssf_id";
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
    String vFileName = mapFilter.get("file_name");
    String vOwner = mapFilter.get("owner");
    String vDtBegin = mapFilter.get("dt_begin");
    String vDtEnd = mapFilter.get("dt_end");
    String vRecBegin = mapFilter.get("rec_begin");
    String vRecEnd = mapFilter.get("rec_end");
    // ----------------------------------
    String phWhere = null;
    String str = null;
    // ---
    if (!DbUtils.emptyString(vFileName)) {
      str = "upper(mssf_name) like upper('" + vFileName + "%')";
      phWhere = DbUtils.strAddAnd(phWhere, str);
    }
    // ---
    if (!DbUtils.emptyString(vOwner)) {
      str = "upper(mssf_owner) like upper('" + vOwner + "%')";
      phWhere = DbUtils.strAddAnd(phWhere, str);
    }
    //---
    String vField = "";
    String valueBeg = "";
    String valueEnd = "";
    //---
    vField = "trunc(mssf_dt_work)";
    valueBeg = "to_date('" + vDtBegin + "','dd.mm.yyyy')";
    valueEnd = "to_date('" + vDtEnd + "','dd.mm.yyyy')";
    if (!DbUtils.emptyString(vDtBegin) || !DbUtils.emptyString(vDtEnd)) {
      if (!DbUtils.emptyString(vDtBegin) && !DbUtils.emptyString(vDtEnd)) {
        str = vField + " between " + valueBeg + " and " + valueEnd;
      } else if (!DbUtils.emptyString(vDtBegin) && DbUtils.emptyString(vDtEnd)) {
        str = vField + " >= " + valueBeg;
      } else if (DbUtils.emptyString(vDtBegin) && !DbUtils.emptyString(vDtEnd)) {
        str = vField + " <= " + valueEnd;
      }
      phWhere = DbUtils.strAddAnd(phWhere, str);
    }
    //---
    vField = "mssf_rec_all";
//    vField="to_number(extract(mssf_statistic,'statistic/records/all/text()').getstringval())";
    valueBeg = vRecBegin;
    valueEnd = vRecEnd;
    if (!DbUtils.emptyString(vRecBegin) || !DbUtils.emptyString(vRecEnd)) {
      if (!DbUtils.emptyString(vRecBegin) && !DbUtils.emptyString(vRecEnd)) {
        str = vField + " between " + valueBeg + " and " + valueEnd;
      } else if (!DbUtils.emptyString(vRecBegin) && DbUtils.emptyString(vRecEnd)) {
        str = vField + " >= " + valueBeg;
      } else if (DbUtils.emptyString(vRecBegin) && !DbUtils.emptyString(vRecEnd)) {
        str = vField + " <= " + valueEnd;
      }
      phWhere = DbUtils.strAddAnd(phWhere, str);
    }
    // ---
    strWhere = DbUtils.strAddAnd(strAwhere, phWhere);
  }

}
