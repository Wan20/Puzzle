package com.example.wansu.puzzle;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class GameActivity extends AppCompatActivity {
    private TextView move_counter;
    private TextView feedback;  //status respon on the game
    //no respon if game not played and warning
    //Rule about moving button.
    private Button[] buttons; //button pengganti gambar dalam puzzle
    private Boolean bad_move = false; //Status change
    private Integer[] goal = new Integer[]
            {0, 1, 2, 3, 4, 5, 6, 7, 8}; //string akhir permainan

    private ArrayList<Integer> cells = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        buttons = findButtons();

        //perulangan button pengganti gambar kita asumsikan alurnya melalui angka yang tersusun sempurna
        for (int i = 0; i < 9; i++) {
            this.cells.add(i);
        }
        //Shuffle array on list when activity game running
        Collections.shuffle(this.cells);

        //grid view
        fill_grid();

        move_counter = (TextView) findViewById(R.id.MoveCounter);
        feedback = (TextView) findViewById(R.id.FeedbackText);

        //memasukan respon perpindahan pada makeMove
        for (int i = 1; i < 9; i++) {
            buttons[i].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    makeMove((Button) v);
                }
            });
        }
        //memulai pertambahan ketika mendapat respon dari
        //makeMove dan default value bernilai 0
        move_counter.setText("0");
        //respon dari perubahan value akan ditampilkan pada
        //tampilan textview di layout game.xml
        feedback.setText(R.string.game_feedback_text);
    }

    //pengelompokan array findbuttons
    public Button[] findButtons() {
        Button[] a = new Button[9];
        a[0] = (Button) findViewById(R.id.Button00);
        a[1] = (Button) findViewById(R.id.Button01);
        a[2] = (Button) findViewById(R.id.Button02);
        a[3] = (Button) findViewById(R.id.Button03);
        a[4] = (Button) findViewById(R.id.Button04);
        a[5] = (Button) findViewById(R.id.Button05);
        a[6] = (Button) findViewById(R.id.Button06);
        a[7] = (Button) findViewById(R.id.Button07);
        a[8] = (Button) findViewById(R.id.Button08);
        return a;
    }

    public void makeMove(final Button a) {
        bad_move = true; //salah / perpindahan button tidak sesuai rule
        int a_text, a_pos, zuk_pos; //inisialisasi integer
//yang akan digunakan
        a_text = Integer.parseInt((String) a.getText());
        a_pos = find_pos(a_text);
        zuk_pos = find_pos(0);
//rule perpindahan button
        switch (zuk_pos) {
            case (0):
                if (a_pos == 1 || a_pos == 3)
                    bad_move = false;
                break;
            case (1):
                if (a_pos == 0 || a_pos == 2 || a_pos == 4)
                    bad_move = false;
                break;
            case (2):
                if (a_pos == 1 || a_pos == 5)
                    bad_move = false;
                break;
            case (3):
                if (a_pos == 0 || a_pos == 4 || a_pos == 6)
                    bad_move = false;
                break;
            case (4):
                if (a_pos == 1 || a_pos == 3 || a_pos == 5 || a_pos == 7)
                    bad_move = false;
                break;
            case (5):
                if (a_pos == 2 || a_pos == 4 || a_pos == 8)
                    bad_move = false;
                break;
            case (6):
                if (a_pos == 3 || a_pos == 7)
                    bad_move = false;
                break;
            case (7):
                if (a_pos == 4 || a_pos == 6 || a_pos == 8)
                    bad_move = false;
                break;
            case (8):
                if (a_pos == 5 || a_pos == 7)
                    bad_move = false;
                break;
        }
        if (bad_move == true) {
            feedback.setText("Move Not Allowed");
            return;
        }
        feedback.setText("Move OK");
        cells.remove(a_pos);
        cells.add(a_pos, 0);
        cells.remove(zuk_pos);
        cells.add(zuk_pos, a_text);
        fill_grid();
        //penambahan +1 tiap kali ada perubahan tataletak pada button
        move_counter.setText(Integer.toString(Integer.parseInt((String) move_counter.getText()) + 1));
        //perulangan jika hampir mencapai target goal
        for (int i = 0; i < 9; i++) {
            if (cells.get(i) == goal[i]) {
                return;
            }
        }
        //maka munculkan text ini untuk merayakan
        feedback.setText("Final Execute");
        //konfigurasi penempatan default button
    }

    public void fill_grid() {
        for (int i = 0; i < 9; i++) {
            int text = cells.get(i);
            RelativeLayout.LayoutParams absParams = new RelativeLayout.LayoutParams(buttons[text].getLayoutParams());
            switch (i) {
                case (0):
                    absParams.x = 5;
                    absParams.y = 5;
                    buttons[text].setLayoutParams(absParams);
                    break;
                case (1):
                    absParams.x = 110;
                    absParams.y = 5;
                    buttons[text].setLayoutParams(absParams);
                    break;
                case (2):
                    absParams.x = 215;
                    absParams.y = 5;
                    buttons[text].setLayoutParams(absParams);
                    break;
                case (3):
                    absParams.x = 5;
                    absParams.y = 110;
                    buttons[text].setLayoutParams(absParams);
                    break;
                case (4):
                    absParams.x = 110;
                    absParams.y = 110;
                    buttons[text].setLayoutParams(absParams);
                    break;
                case (5):
                    absParams.x = 215;
                    absParams.y = 110;
                    buttons[text].setLayoutParams(absParams);
                    break;
                case (6):
                    absParams.x = 5;
                    absParams.y = 215;
                    buttons[text].setLayoutParams(absParams);
                    break;
                case (7):
                    absParams.x = 110;
                    absParams.y = 215;
                    buttons[text].setLayoutParams(absParams);
                    break;
                case (8):
                    absParams.x = 215;
                    absParams.y = 215;
                    buttons[text].setLayoutParams(absParams);
                    break;
            }
        }
    }

    public int find_pos(int element) {
        int i = 0;
        for (i = 0; i < 9; i++) {
            if (cells.get(i) == element) {
                break;
            }
        }
        return i;
    }
}
