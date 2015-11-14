package quadx.com.droidx.ui.info;

import android.app.Fragment;
import android.custom.adapter.MapAdapter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;

import quadx.com.droidx.R;
import quadx.com.droidx.resources.InfoMap;

public class InfoFragment extends Fragment implements InfoFragmentUpdater {

    public AbsListView mListView;
    public BaseAdapter mAdapter;

    public static InfoFragment newInstance() {
        InfoFragment fragment = new InfoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_droneinfo, container, false);

        mListView = (AbsListView) view.findViewById(R.id.droneInfoList);
        mAdapter = new MapAdapter(InfoMap.ITEM_MAP);
        mListView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void updateInfo() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdapter.notifyDataSetChanged();
            }
        });
    }
}
