package acp.db.service;

import java.sql.*;
import java.util.ArrayList;

import acp.db.DbConnect;
import acp.db.domain.ToptionClass;
import acp.utils.DialogUtils;

public class ToptionManagerEdit {

  private Connection dbConn;

  private String path;
  private ArrayList<String> attrs;
  private int attrSize;
  private int attrMax = 5;
  private String attrPrefix;

  public ToptionManagerEdit(String path, ArrayList<String> attrs) {
    dbConn = DbConnect.getConnection();
    this.path = path;
    this.attrs = attrs;
    this.attrSize = attrs.size();
    String[] pathArray = path.split("/");
    this.attrPrefix = pathArray[pathArray.length - 1];
  }

  public String getPath() {
    return path;
  }

  public ArrayList<String> getAttrs() {
    return attrs;
  }

  public int getAttrSize() {
    return attrSize;
  }

  public int getAttrMax() {
    return attrMax;
  }

  public String getAttrPrefix() {
    return attrPrefix;
  }

  public ToptionClass select(int objId) {
    // ------------------------------------------------------
    StringBuilder sbQuery = new StringBuilder();
    sbQuery.append("select t.* from table(mss.spr_option_id(?,?,?,?,?,?,?)) t");
    String query = sbQuery.toString();
    // ------------------------------------------------------
    ToptionClass toptObj = null;
    int j = 0;
    try {
      PreparedStatement ps = dbConn.prepareStatement(query);
      // ----------------------------------
      j++;
      ps.setInt(j, objId);
      j++;
      ps.setString(j, path);
      // ----------------------------------
      for (int i = 0; i < attrSize; i++) {
        j++;
        ps.setString(j, attrs.get(i));
      }
      for (int i = attrSize; i < attrMax; i++) {
        j++;
        ps.setString(j, "");
      }
      // ----------------------------------
      ResultSet rsq = ps.executeQuery();
      // ----------------------------------
      if (rsq.next()) {
        int rsqId = rsq.getInt("config_id");
        // -------------------------
        ArrayList<String> pArr = new ArrayList<>();
        for (int i = 0; i < attrSize; i++) {
          j = i + 1;
          String pj = rsq.getString("p" + j);
          pArr.add(pj);
        }
        // -------------------------
        Date rsqDateBegin = rsq.getDate("date_begin");
        Date rsqDateEnd = rsq.getDate("date_end");
        // ---------------------
        toptObj = new ToptionClass();
        toptObj.setId(rsqId);
        toptObj.setArrayP(pArr);
        toptObj.setDateBegin(rsqDateBegin);
        toptObj.setDateEnd(rsqDateEnd);
        // ---------------------
      }
      rsq.close();
      ps.close();
    } catch (SQLException e) {
      DialogUtils.errorPrint(e);
    }
    // ------------------------------------------------------
    return toptObj;
  }

  public int updateStr(ToptionClass objOld, ToptionClass objNew) {
    int res = -1;
    int objId = objOld.getId();
    ArrayList<String> recOldValue = objOld.getPArray();
    ArrayList<String> recNewValue = objNew.getPArray();
    // -----------------------------------------
    String where = "";
    for (int i = 0; i < attrSize; i++) {
      if (recOldValue.get(i) != null) {
        where += "[@" + attrs.get(i) + "=\"" + recOldValue.get(i) + "\"]";
      }
    }
    // ----------
    StringBuilder sbQuery = new StringBuilder();
    sbQuery.append("update mss_options set ");
    sbQuery.append("msso_config = updateXml(msso_config");
    for (int i = 0; i < attrSize; i++) {
      if (!recNewValue.get(i).equals("")) {
        sbQuery.append(",'" + path + where + "/@" + attrs.get(i) + "'");
        sbQuery.append(",'" + recNewValue.get(i) + "'");
      }
    }
    sbQuery.append(") where msso_id=?");
    String query = sbQuery.toString();
    // System.out.println(query);
    // -----------------------------------------
    try {
      PreparedStatement ps = dbConn.prepareStatement(query);
      ps.setInt(1, objId);
      // --------------------------
      int ret = ps.executeUpdate();
      // --------------------------
      ps.close();
      res = ret;
    } catch (SQLException e) {
      DialogUtils.errorPrint(e);
    }
    // -----------------------------------------------------
    return res;
  }

  public int updatePar(ToptionClass objOld, ToptionClass objNew) {
    int res = -1;
    int objId = objOld.getId();
    ArrayList<String> recOldValue = objOld.getPArray();
    ArrayList<String> recNewValue = objNew.getPArray();
    // -----------------------------------------
    String where = "";
    for (int i = 0; i < attrSize; i++) {
      if (recOldValue.get(i) != null) {
        where += "[@" + attrs.get(i) + "=\"" + recOldValue.get(i) + "\"]";
      }
    }
    // System.out.println(where);
    // -----------------------------------------
    StringBuilder sbQuery = new StringBuilder();
    sbQuery.append("update mss_options set ");
    sbQuery.append("msso_config=updateXml(msso_config");
    for (int i = 0; i < attrSize; i++) {
      if (!recNewValue.get(i).equals("")) {
        String param = path + where + "/@" + attrs.get(i);
        sbQuery.append(",'" + param + "',?");
      }
    }
    sbQuery.append(") where msso_id=?");
    String query = sbQuery.toString();
    // System.out.println(query);
    // -----------------------------------------
    try {
      PreparedStatement ps = dbConn.prepareStatement(query);
      int j = 0;
      for (int i = 0; i < attrSize; i++) {
        if (!recNewValue.get(i).equals("")) {
          ps.setString(++j, recNewValue.get(i));
        }
      }
      ps.setInt(++j, objId);
      // --------------------------
      int ret = ps.executeUpdate();
      // --------------------------
      ps.close();
      res = ret;
    } catch (SQLException e) {
      DialogUtils.errorPrint(e);
    }
    // -----------------------------------------------------
    return res;
  }

}
