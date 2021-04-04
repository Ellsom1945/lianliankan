package top.ysccx.myfirstapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;


public class Tab3Fragment extends Fragment {
    private MediaPlayer mp;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab3, null);
        final MainActivity mainActivity = (MainActivity) getActivity();
        mp = mainActivity.mp;
        sp = getContext().getSharedPreferences("config",Context.MODE_PRIVATE);
        Switch switch1 = view.findViewById(R.id.swich1);
        switch1.setChecked(sp.getBoolean("music",true));
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editor = sp.edit();
                if(buttonView.isPressed()) {
                    if (isChecked) {
                        mp.start();
                        editor.putBoolean("music",true);
                        Toast.makeText(getContext(), "打开音乐", Toast.LENGTH_SHORT).show();
                    } else {
                        mp.pause();
                        mp.seekTo(0);
                        editor.putBoolean("music",false);
                        Toast.makeText(getContext(), "关闭音乐", Toast.LENGTH_SHORT).show();
                    }
                }
                editor.commit();
            }
        });
        Switch switch2 = view.findViewById(R.id.swich2);
        switch2.setChecked(sp.getBoolean("sound",true));
        switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editor = sp.edit();
                if(buttonView.isPressed()) {
                    if (isChecked) {
                        mainActivity.sound = true;
                        editor.putBoolean("sound",true);
                        Toast.makeText(getContext(), "打开音效", Toast.LENGTH_SHORT).show();
                    } else {
                        mainActivity.sound = false;
                        editor.putBoolean("sound",false);
                        Toast.makeText(getContext(), "关闭音效", Toast.LENGTH_SHORT).show();
                    }
                }
                editor.commit();
            }
        });
        return view;
    }
}

