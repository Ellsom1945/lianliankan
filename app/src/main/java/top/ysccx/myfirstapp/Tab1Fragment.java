package top.ysccx.myfirstapp;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;


public class Tab1Fragment extends Fragment {
    private GameConf config;
    private GameService gameService;
    private GameView gameView;
    private Button startButton;
    private TextView timeTextView;
    private AlertDialog.Builder lostDialog;
    private AlertDialog.Builder successDialog;
    private Timer timer;
    private int gameTime;
    private boolean isPlaying = false;
    private Piece selectedPiece = null;
    private AudioAttributes ad =new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build();
    private SoundPool soundPool = new SoundPool.Builder().setMaxStreams(16).setAudioAttributes(ad).build();
    private int sdp;
    private int wrong;
    private DataBaseHelper myDBHelper;
    private SQLiteDatabase db;
    private EditText et;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab1, null);
        gameView = view.findViewById(R.id.gameView);
        setHasOptionsMenu(true);
        timeTextView = view.findViewById(R.id.timeText);
        timeTextView.setVisibility(View.INVISIBLE);
        startButton = view.findViewById(R.id.startButton);
        sdp = soundPool.load(getContext(),R.raw.sdp,1);
        wrong = soundPool.load(getContext(),R.raw.wrong,1);
        //Toast.makeText(getContext(), sb.toString(), Toast.LENGTH_SHORT).show();
        init();
        return view;
    }

    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);
        menu.add(1,1,1,"?????????");
        menu.add(1,2,1,"????????????");
        SubMenu grade = menu.addSubMenu("??????");
        grade.setHeaderTitle("??????????????????");
        grade.add(1,11,2,"??????");
        grade.add(1,12,3,"??????");
        grade.add(1,13,4,"??????");
        grade.add(1,14,5,"??????");
        menu.add(1,3,3,"????????????");
        menu.add(1,4,4,"??????");
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        switch (id){
            case 1:
                //Toast.makeText(getContext(),"????????????",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setClass(getContext(), RankingActivity.class);
                startActivity(intent);
                break;
            case 2:
                gameService.shuffle();
                gameView.postInvalidate();
                break;
            case 3:
                if(isPlaying) {
                    startGame(0);
                }
                else {
                    Toast.makeText(getContext(), "???????????????????????????~", Toast.LENGTH_SHORT).show();
                }
                break;
            case 4:
                Toast.makeText(getContext(),"????????????????????????",Toast.LENGTH_SHORT).show();
                break;
            case 11:
                config.setxSize(5);
                config.setySize(6);
                config.setBeginImage();
                if(isPlaying){
                    startGame(0);
                }
                break;
            case 12:
                config.setxSize(6);
                config.setySize(7);
                config.setBeginImage();
                if(isPlaying){
                    startGame(0);
                }
                break;
            case 13:
                config.setxSize(7);
                config.setySize(8);
                config.setBeginImage();
                if(isPlaying){
                    startGame(0);
                }
                break;
            case 14:
                config.setxSize(8);
                config.setySize(9);
                config.setBeginImage();
                if(isPlaying){
                    startGame(0);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * ????????????????????????
     */
    private void init() {
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int screenWidth = size.x;
        int screenHeight = size.y;
        config = new GameConf(7, 8, screenWidth, screenHeight, GameConf.DEFAULT_TIME, getContext());
        gameService = new GameServiceImpl(this.config);
        et = new EditText(getContext());
        gameView.setGameService(gameService);
        gameView.setSelectImage(ImageUtil.getSelectImage(getContext()));
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View source) {
                startGame(0);
                startButton.setVisibility(View.INVISIBLE);
                gameView.setBackgroundColor(0xFFF9E3);
                timeTextView.setVisibility(View.VISIBLE);
            }
        });
        // ?????????????????????????????????????????????
        this.gameView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent e) {
                if (e.getAction() == MotionEvent.ACTION_DOWN) {
                    gameViewTouchDown(e);
                }
                if (e.getAction() == MotionEvent.ACTION_UP) {
                    gameViewTouchUp(e);
                }
                return true;
            }
        });
        // ?????????????????????????????????
        lostDialog = createDialog("GAME OVER", "????????????", R.drawable.lost)
                .setPositiveButton("??????", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startGame(0);
                    }
                });
        // ?????????????????????????????????
        successDialog = createDialog("Success", "???????????????????????????????????????",
                R.drawable.success).setView(et).setPositiveButton("??????",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String input = et.getText().toString();
                        myDBHelper = new DataBaseHelper(getContext(),"ranking",null,1);
                        db = myDBHelper.getWritableDatabase();
                        Time t=new Time();
                        t.setToNow();
                        int year = t.year;
                        int month = t.month+1;
                        int day = t.monthDay;
                        String date = year+"/"+month+"/"+day;
                        ContentValues cv = new ContentValues();
                        cv.put("name",input);
                        cv.put("time",String.valueOf(gameTime));
                        cv.put("date",date);
                        db.insert("users",null,cv);
                        Intent intent = new Intent();
                        intent.setAction("top.ysccx.broadcast");
                        intent.putExtra("name",input);
                        intent.putExtra("time",String.valueOf(gameTime));
                        getActivity().sendBroadcast(intent);
                        startActivity(new Intent(getActivity(),RankingActivity.class));
                    }
                });
    }

    /**
     * Handler??????????????????
     */
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x123:
                    timeTextView.setText("???????????? " + (200-gameTime));
                    gameTime++; // ????????????????????????
                    // ????????????0, ????????????
                    if (gameTime > 200) {
                        // ????????????
                        stopTimer();
                        // ?????????????????????
                        isPlaying = false;
                        // ????????????????????????
                        lostDialog.show();
                        return;
                    }
                    break;
            }
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        // ????????????
        stopTimer();
    }

    @Override
    public void onResume() {
        super.onResume();
        // ???????????????????????????
        if(isPlaying) {
            startGame(0);
        }
    }

    /**
     * ?????????????????????????????????
     *
     * @param event
     */
    private void gameViewTouchDown(MotionEvent event) {
        Piece[][] pieces = gameService.getPieces();
        float touchX = event.getX();
        Log.i("X",String.valueOf(touchX));
        float touchY = event.getY();
        Log.i("Y",String.valueOf(touchY));
        Piece currentPiece = gameService.findPiece(touchX, touchY);
        if (currentPiece == null)
            return;
        this.gameView.setSelectedPiece(currentPiece);
        if (this.selectedPiece == null) {
            this.selectedPiece = currentPiece;
            this.gameView.postInvalidate();
            return;
        }
        // ?????????????????????????????????
        if (this.selectedPiece != null) {
            LinkInfo linkInfo = this.gameService.link(this.selectedPiece,
                    currentPiece);
            if (linkInfo == null) {
                this.selectedPiece = currentPiece;
                if(((MainActivity)getActivity()).sound){
                    soundPool.play(wrong, 0.5f, 0.5f, 0, 0, 1);
                }
                this.gameView.postInvalidate();
            } else {
                handleSuccessLink(linkInfo, this.selectedPiece, currentPiece, pieces);
            }
        }
    }

    /**
     * ?????????????????????????????????
     *
     * @param e
     */
    private void gameViewTouchUp(MotionEvent e) {
        this.gameView.postInvalidate();
    }

    /**
     * ???gameTime???????????????????????????????????????
     *
     * @param gameTime
     *            ????????????
     */
    private void startGame(int gameTime) {
        this.gameTime = gameTime;
        gameView.startGame();
        isPlaying = true;
        if(timer==null) {
            this.timer = new Timer();
            this.timer.schedule(new TimerTask() {
                public void run() {
                    handler.sendEmptyMessage(0x123);
                }
            }, 0, 1000);
        }
        this.selectedPiece = null;
    }

    /**
     * ?????????????????????
     *
     * @param linkInfo
     *            ????????????
     * @param prePiece
     *            ?????????????????????
     * @param currentPiece
     *            ??????????????????
     * @param pieces
     *            ??????????????????????????????
     */
    private void handleSuccessLink(LinkInfo linkInfo, Piece prePiece,
                                   Piece currentPiece, Piece[][] pieces) {
        // ??????????????????, ???GamePanel??????LinkInfo
        this.gameView.setLinkInfo(linkInfo);
        // ???gameView????????????????????????null
        this.gameView.setSelectedPiece(null);
        this.gameView.postInvalidate();
        // ?????????Piece????????????????????????
        pieces[prePiece.getIndexX()][prePiece.getIndexY()] = null;
        pieces[currentPiece.getIndexX()][currentPiece.getIndexY()] = null;
        // ????????????????????????null???
        this.selectedPiece = null;
        if(((MainActivity)getActivity()).sound){
            soundPool.play(sdp, 0.5f, 0.5f, 0, 0, 1);
        }
        // ?????????????????????????????????, ????????????, ????????????
        if (!this.gameService.hasPieces()) {
            // ????????????
            this.successDialog.show();
            // ???????????????
            stopTimer();
            // ??????????????????
            //isPlaying = false;
        }
    }

    /**
     * ??????????????????????????????
     *
     * @param title
     *            ??????
     * @param message
     *            ??????
     * @param imageResource
     *            ??????
     * @return
     */
    private AlertDialog.Builder createDialog(String title, String message,
                                             int imageResource) {
        return new AlertDialog.Builder(getContext()).setTitle(title)
                .setMessage(message).setIcon(imageResource);
    }

    /**
     * ????????????
     */
    private void stopTimer() {
        // ???????????????
        if(timer!=null) {
            this.timer.cancel();
            this.timer = null;
        }
    }
}
