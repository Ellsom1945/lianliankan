package top.ysccx.myfirstapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView mBv;
    private ViewPager mVp;
    public MediaPlayer mp;
    public boolean sound = true;
    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        mp = MediaPlayer.create(this, R.raw.bg);
        mp.setLooping(true);
        sp = this.getSharedPreferences("config",MODE_PRIVATE);
        if(sp.getBoolean("music",true)) {
            mp.start();
        }
        if(!sp.getBoolean("sound",true)) {
            sound = false;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sp = this.getSharedPreferences("config",MODE_PRIVATE);
        if(sp.getBoolean("music",true)) {
            mp.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        sp = this.getSharedPreferences("config",MODE_PRIVATE);
        if(sp.getBoolean("music",true)) {
            mp.start();
        }
    }

    /*public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(1,1,1,"打乱重排");
        menu.add(1,2,2,"重新开始");
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        switch (id){
            case 1:
                Toast.makeText(this,"打乱重排",Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(this,"重新开始",Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }*/

    private void initView() {
        mBv = findViewById(R.id.bv);
        mVp = findViewById(R.id.vp);
        mVp.setOffscreenPageLimit(2);
        mBv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.tab1:
                        mVp.setCurrentItem(0);
                        break;

                    case R.id.tab2:
                        mVp.setCurrentItem(1);
                        break;

                    case R.id.tab3:
                        mVp.setCurrentItem(2);
                        break;
                }
                return true;
            }
        });

        //数据填充
        setupViewPager(mVp);
        //ViewPager监听
        mVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mBv.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        BottomAdapter adapter = new BottomAdapter(getSupportFragmentManager());
        adapter.addFragment(new Tab1Fragment());
        adapter.addFragment(new Tab2Fragment());
        adapter.addFragment(new Tab3Fragment());
        viewPager.setAdapter(adapter);
    }
}
