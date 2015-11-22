package quadx.com.utils;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SAFWAN on 11/15/15.
 */
public class GoogleAccountManager {

    private Context context;

    public GoogleAccountManager(Context context) {
        this.context = context;
    }

    public List<Account> getAccounts() {

        List<Account> accounts = new ArrayList<>();
        AccountManager manager = (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);
        Account[] list = manager.getAccounts();

        for (Account account : list) {
            if (account.type.equalsIgnoreCase("com.google")) {
                accounts.add(account);
            }
        }
        return accounts;
    }

    public Account getAccountByUserName(String userName) {
        AccountManager manager = (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);
        Account[] list = manager.getAccounts();

        for (Account account : list) {
            if (account.name.equals(userName) && account.type.equalsIgnoreCase("com.google")) {
                return account;
            }
        }
        return null;
    }
}
