package quadx.com.droidx.resources;

import android.accounts.Account;
import android.custom.view.JoyStickView;

import quadx.com.utils.GoogleAccountManager;

/**
 * Created by SAFWAN on 11/19/15.
 */
public class Settings {

    public static Account userAccount;

    public static boolean sendCrashReport;
    public static int syncFrequency;

    public static String joyStickType;

    public static void setUserAccount(Account userAccount) {
        Settings.userAccount = userAccount;
    }

    public static void setSendCrashReport(boolean sendCrashReport) {
        Settings.sendCrashReport = sendCrashReport;
    }

    public static void setSyncFrequency(int syncFrequency) {
        Settings.syncFrequency = syncFrequency;
    }

    public static void setJoyStickView(String joyStickType) {
        Settings.joyStickType = joyStickType;
    }
}
