package com.carpediem.vv.funny.Fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.carpediem.vv.funny.Base.BaseFragment;
import com.carpediem.vv.funny.R;

/**
 * Created by Administrator on 2016/6/28.
 */
public class GameFragment extends BaseFragment {

    private LinearLayout testVersion;

    public static GameFragment newInstance(String content) {
        Bundle args = new Bundle();
        args.putString("ARGS", content);
        GameFragment fragment = new GameFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected View initView() {
        View view = View.inflate(mActivity, R.layout.fragment_game, null);
        testVersion = (LinearLayout) view.findViewById(R.id.test_version);
        testVersion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mActivity,"版本检测",Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }


}
