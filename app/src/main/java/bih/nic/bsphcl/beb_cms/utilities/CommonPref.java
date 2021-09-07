package bih.nic.bsphcl.beb_cms.utilities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import bih.nic.bsphcl.beb_cms.entities.UserInformationEntity;


/**
 * Created by CKS on 15/06/2018.
 */
public class CommonPref {

	static Context context;

	CommonPref() {

	}

	CommonPref(Context context) {
		CommonPref.context = context;
	}


	public static void setUserDetails(Context context, UserInformationEntity UserInfo2) {

		String key = "_USER_DETAILS";

		SharedPreferences prefs = context.getSharedPreferences(key,
				Context.MODE_PRIVATE);
		Editor editor = prefs.edit();
		editor.putString("MessageString", UserInfo2.getUserName());
		editor.putString("UserID", UserInfo2.getUserID());
		editor.putString("UserName", UserInfo2.getUserName());
		editor.putString("Password", UserInfo2.getPassword());
		editor.putBoolean("Authenticated", UserInfo2.getAuthenticated());
		editor.putString("ImeiNo", UserInfo2.getImeiNo());
		editor.putString("Role", UserInfo2.getRole());
		editor.putString("SubDivision", UserInfo2.getSubDivision());
		editor.putString("ContactNo", UserInfo2.getContactNo());
		editor.putString("Email", UserInfo2.getEmail());
		editor.putString("Division", UserInfo2.getDivision());
		editor.commit();
	}

	public static UserInformationEntity getUserDetails(Context context) {
		String key = "_USER_DETAILS";
		UserInformationEntity UserInfo2 = new UserInformationEntity();
		SharedPreferences prefs = context.getSharedPreferences(key,
				Context.MODE_PRIVATE);
		UserInfo2.setMessageString(prefs.getString("MessageString", ""));
		UserInfo2.setUserID(prefs.getString("UserID", ""));
		UserInfo2.setUserName(prefs.getString("UserName", ""));
		UserInfo2.setAuthenticated(prefs.getBoolean("Authenticated", false));
		UserInfo2.setImeiNo(prefs.getString("ImeiNo", ""));
		UserInfo2.setPassword(prefs.getString("Password", ""));
		UserInfo2.setRole(prefs.getString("Role", ""));
		UserInfo2.setSubDivision(prefs.getString("SubDivision", ""));
		UserInfo2.setContactNo(prefs.getString("ContactNo", ""));
		UserInfo2.setEmail(prefs.getString("Email", ""));
		UserInfo2.setDivision(prefs.getString("Division", ""));
		return UserInfo2;
	}

	public static void setCheckUpdate(Activity context,boolean first) {
		String key = "_USER_DETAILS";

		SharedPreferences prefs = context.getSharedPreferences(key,
				Context.MODE_PRIVATE);

		Editor editor = prefs.edit();
		editor.putBoolean("first", first);

		editor.commit();

	}

	public static boolean getCheckUpdate(Context context) {
		String key = "_USER_DETAILS";
		SharedPreferences prefs = context.getSharedPreferences(key,
				Context.MODE_PRIVATE);
		return prefs.getBoolean("first", false);
	}
}
