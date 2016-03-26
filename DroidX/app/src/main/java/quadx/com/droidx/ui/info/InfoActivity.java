package quadx.com.droidx.ui.info;

import android.custom.adapter.MapAdapter;
import android.widget.AbsListView;
import android.widget.BaseAdapter;

import quadx.com.droidx.R;
import quadx.com.droidx.resources.InfoMap;
import quadx.com.droidx.ui.MainActivity;

public class InfoActivity extends MainActivity implements InfoFragmentUpdater {

    public AbsListView mListView;
    public BaseAdapter mAdapter;

    protected void setViewResources() {
        super.setViewResources();
        //-------------------------------------------------------
        mListView = (AbsListView) findViewById(R.id.droneInfoList);
        mAdapter = new MapAdapter(InfoMap.ITEM_MAP);
        mListView.setAdapter(mAdapter);
        //-------------------------------------------------------
    }

    @Override
    protected int getLayoutViewId() {
        return R.layout.activity_droneinfo;
    }

    @Override
    public void updateInfo() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdapter.notifyDataSetChanged();
            }
        });
    }
}
