package quadx.com.droidx.ui;

import android.accounts.Account;
import android.app.Fragment;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;

import java.util.List;

import quadx.com.droidx.R;
import quadx.com.utils.GoogleAccountManager;

/**
 * Created by SAFWAN on 11/15/15.
 */
public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.settings);

        final GoogleAccountManager accountManager = new GoogleAccountManager(getActivity());
        List<Account> accounts = accountManager.getAccounts();

        final ListPreference listPreference = (ListPreference) findPreference("prefUsername");

        setListPreferenceData(listPreference, accounts);
    }

    private static void setListPreferenceData(ListPreference lp, List<Account> accounts) {

        CharSequence[] entries = new CharSequence[accounts.size()];
        CharSequence[] entryValues = new CharSequence[accounts.size()];
        int i = 0;
        for (Account account : accounts) {
            entries[i] = account.name;
            entryValues[i] = account.name;
            i++;
        }
        lp.setEntries(entries);
        lp.setDefaultValue("default");
        lp.setEntryValues(entryValues);
    }
}
