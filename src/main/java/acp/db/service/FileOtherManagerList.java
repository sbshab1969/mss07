package acp.db.service;

import acp.db.utils.*;
import acp.utils.*;

public class FileOtherManagerList extends ManagerListStr {
  private int fileId;

  public FileOtherManagerList(int file_id) {
    fields = new String[] { "mssl_id",
      "to_char(mssl_dt_event,'dd.mm.yyyy hh24:mi:ss') mssl_dt_event",
      "mssl_desc" };

    fieldnames = new String[] { "ID", Messages.getString("Column.Time"),
      Messages.getString("Column.Desc") };

    cntColumns = fields.length;
    
    fileId = file_id;

    tableName = "mss_logs";
    pkColumn = "mssl_id";
    strAwhere = "mssl_ref_id=" + fileId;
    seqId = 1000;

    strFields = DbUtils.buildSelectFields(fields, null);
    strFrom = tableName;
    strWhere = strAwhere;
    strOrder = pkColumn;
    // ------------
    prepareQuery();
    // ------------
  }

}
