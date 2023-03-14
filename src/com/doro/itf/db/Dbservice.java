package com.doro.itf.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.sql.PreparedStatement;

import com.doro.itf.data.Badloaddata;
import com.doro.itf.log.LogMgr;

//DB CONNECTION &INSERT&UPDATAE 
public class Dbservice {
	private Connection dbConn = null;
	private DbConnectionManager dbconnction = null;;
	private LogMgr log = null;
	private PreparedStatement pstmt = null;
	private Badloaddata badloaddata = null;

	public Dbservice() {

		log = LogMgr.getInstance();
		dbconnction = DbConnectionManager.getInstance();
		badloaddata = Badloaddata.getInstance();
	}
   //DBconnection
	public void dbstart() throws IOException {
		try {
			dbConn = dbconnction.getConnection();
			String str = "DB connetcion Success";
			log.WriteLog(str, true);
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}
    //DBdisconnection
	public void dbclose() throws SQLException, IOException {

		dbConn.close();
		String str = "DB disconnetcion Success";
		log.WriteLog(str, true);

	}
    //insert BNDS
	public boolean insertbnds() {
		String sql = "";

		pstmt = null;
		try {
			dbstart();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Date sqlDate = new Date(System.currentTimeMillis());

		sql = "INSERT INTO T_ITAC_LDNG_BNDS_MSRMT01D1";
		sql += " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";

		try {
			pstmt = dbConn.prepareStatement(sql);

			int i = 1;
			pstmt.setString(i++, badloaddata.Ic_code);
			pstmt.setString(i++, badloaddata.DetectionDate);
			pstmt.setString(i++, badloaddata.DetectionRoad);
			pstmt.setString(i++, badloaddata.DetectionNum);
			pstmt.setString(i++, badloaddata.ReportType);
			pstmt.setString(i++, badloaddata.CarNumber);
			pstmt.setString(i++, badloaddata.CarType);
			pstmt.setString(i++, badloaddata.ReportType);
			pstmt.setString(i++, badloaddata.ReportReason);
			pstmt.setString(i++, badloaddata.FsID);
			pstmt.setDate(i++, sqlDate);
			pstmt.setString(i++, badloaddata.LsID);
			pstmt.setDate(i++, sqlDate);

			int cnt = pstmt.executeUpdate();

			if (cnt > 0) {
				log.WriteLog("Insert SUCCESS", true);
				if (pstmt != null) {
					pstmt.close();
				}
				dbclose();
				return true;
			} else {
				log.WriteLog("Insert FAIL", true);
				if (pstmt != null) {
					pstmt.close();
				}
				dbclose();
				return false;

			}

		} catch (Exception e) {
			log.WriteLog(e.toString(), true);

			try {
				if (pstmt != null) {
					pstmt.close();
				}
				dbclose();
			} catch (SQLException | IOException e1) {
	
				e1.printStackTrace();
			}
			return false;
		}


		
	}
    //update BNDS
	public boolean updatebnds() 
	{
		String sql="";
	
		pstmt = null;
		try {
			dbstart();
		} catch (IOException e) {
			e.printStackTrace();
		}

		sql = "UPDATE T_ITAC_LDNG_BNDS_MSRMT01D1 SET";
		sql += " VHCL_CLSS_CD=? ,DSGT_RESN=? WHERE TOLOF_CD=? AND MSRMT_INSR_NM =? AND LANE_MSRMT_TIME=?";

		try {
			pstmt = dbConn.prepareStatement(sql);

			int i = 1;
			pstmt.setString(i++, badloaddata.ReportType);
			pstmt.setString(i++, badloaddata.ReportReason);
			pstmt.setString(i++, badloaddata.Ic_code);
			pstmt.setString(i++, badloaddata.DetectionDate);
			pstmt.setString(i++, badloaddata.DetectionNum);

			int cnt = pstmt.executeUpdate();
			if (cnt > 0) {
				log.WriteLog("UPDATE SUCCESS", true);
				if (pstmt != null) {
					pstmt.close();
				}
				dbclose();
				return true;
			} else {
				log.WriteLog("UPDATE FAIL", true);
				if (pstmt != null) {
					pstmt.close();
				}
				dbclose();
				return false;

			}

		} catch (Exception e) {
			log.WriteLog(e.toString(), true);

			try {
				if (pstmt != null) {
					pstmt.close();
				}
				dbclose();
			} catch (SQLException | IOException e1) {
	
				e1.printStackTrace();
			}
			return false;

		}

	}
    //insert WIGT
	public boolean insertweight(List<String> list) {
		String sql = "";
		String str = "";

		pstmt = null;
		try {
			dbstart();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Date sqlDate = new Date(System.currentTimeMillis());

		sql = "INSERT INTO T_ITAC_LDNG_WIGT_MSRMT01D1";
		sql += " VALUES(?,?,?,?,?" + ",?,?,?,?,?" + ",?,?,?,?,?" + ",?,?,?,?,?" + ",?,?,?,?,?" + ",?,?,?,?,?"
				+ ",?,?,?,?,?" + ",?,?,?)";

		try {
			pstmt = dbConn.prepareStatement(sql);
			dbConn.setAutoCommit(false);
			for (int i = 0; i < list.size(); i++) {
				str = list.get(i).toString();
				String[] Array = str.split("\\|");
	
				int j = 1;
				pstmt.setString(j++, Array[0]);
				pstmt.setString(j++, Array[1]);
				pstmt.setString(j++, Array[2]);
				pstmt.setString(j++, Array[3]);
				pstmt.setString(j++, Array[4]);
				pstmt.setString(j++, Array[5]);
				pstmt.setInt(j++, Integer.parseInt(Array[6]));
				pstmt.setDouble(j++, Double.parseDouble(Array[7]));
				pstmt.setString(j++, Array[8]);
				pstmt.setDouble(j++, Double.parseDouble(Array[9]));
				pstmt.setString(j++, Array[10]);
				pstmt.setString(j++, Array[11]);
				pstmt.setString(j++, Array[12]);
				pstmt.setString(j++, Array[13]);
				pstmt.setString(j++, Array[14]);
				pstmt.setString(j++, Array[15]);
				pstmt.setString(j++, Array[16]);
				pstmt.setString(j++, Array[17]);
				pstmt.setString(j++, Array[18]);
				pstmt.setString(j++, Array[19]);
				pstmt.setString(j++, Array[20]);
				pstmt.setString(j++, Array[21]);
				pstmt.setString(j++, Array[22]);
				pstmt.setString(j++, Array[23]);
				pstmt.setString(j++, Array[24]);
				pstmt.setString(j++, Array[25]);
				pstmt.setString(j++, Array[26]);
				pstmt.setString(j++, Array[27]);
				pstmt.setString(j++, Array[28]);
				pstmt.setString(j++, Array[29]);
				pstmt.setString(j++, Array[30]);
				pstmt.setString(j++, Array[31]);
				pstmt.setString(j++, Array[32]);
				pstmt.setString(j++, Array[33]);
				pstmt.setString(j++, "superAdmin");
				pstmt.setDate(j++, sqlDate);
				pstmt.setString(j++, "superAdmin");
				pstmt.setDate(j++, sqlDate);
				pstmt.addBatch();
				if ((i % 1000) == 0) {

					pstmt.executeBatch();
					pstmt.clearBatch();
					dbConn.commit();
					;
				}
			}
			pstmt.executeBatch();
			pstmt.clearBatch();
			dbConn.commit();

		} catch (SQLException e) {

			e.printStackTrace();
			log.WriteLog(e.toString(), true);
			try {
				dbConn.rollback();
				dbConn.commit();
				dbclose();
			} catch (SQLException | IOException e1) {

				e1.printStackTrace();
			}
			return false;
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				dbConn.setAutoCommit(true);
				dbclose();
			} catch (SQLException | IOException e) {

				e.printStackTrace();
			}
		}
		return true;
	}

}
