package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.DrawableCompat;
import cn.pedant.SweetAlert.SweetAlertDialog;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ImageView image;
    int tag, activeplayer=0, temp=0, tie;
    TextView status;
    ImageButton reset;
    boolean gameActive = true;
    int[] istapped = new int[] {2,2,2,2,2,2,2,2,2};

    int[][] winpos = new int[][] {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};

    public void resetGame(View view) {
        SweetAlertDialog swd = new SweetAlertDialog(this,SweetAlertDialog.WARNING_TYPE);
        swd.setTitleText("Alert!!");
        swd.setContentText("Are you sure, you want to reset?");

        swd.setConfirmButton("Yes", new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                ((ImageView)findViewById(R.id.imageView1)).setImageResource(0);
                ((ImageView)findViewById(R.id.imageView2)).setImageResource(0);
                ((ImageView)findViewById(R.id.imageView3)).setImageResource(0);
                ((ImageView)findViewById(R.id.imageView4)).setImageResource(0);
                ((ImageView)findViewById(R.id.imageView5)).setImageResource(0);
                ((ImageView)findViewById(R.id.imageView6)).setImageResource(0);
                ((ImageView)findViewById(R.id.imageView7)).setImageResource(0);
                ((ImageView)findViewById(R.id.imageView8)).setImageResource(0);
                ((ImageView)findViewById(R.id.imageView9)).setImageResource(0);


                status.setText("O's turn, tap to play!!");
                setTint(Color.rgb(220,211,211));
                gameActive = true;
                activeplayer = 0;
                for(int i=0; i<istapped.length; i++)
                    istapped[i] = 2;

                sweetAlertDialog.dismissWithAnimation();

            }
        });
        swd.setCancelButton("No", new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismissWithAnimation();
            }
        });

        swd.show();
    }

    public void setTint(int x) {
        Drawable buttonDrawable = status.getBackground();
        buttonDrawable = DrawableCompat.wrap(buttonDrawable);
        //the color is a direct color int and not a color resource
        DrawableCompat.setTint(buttonDrawable, x);
        status.setBackground(buttonDrawable);
    }

    public boolean tieCheck() {
        for(int x : istapped) {
            Log.d("General",Integer.toString(x));
            if(x != 2) temp++;
            Log.d("General","temp"+Integer.toString(temp));
        }

        if(temp == 9) {
            status.setText("Oops! It's a Tie, Play Again!!");
            return true;
        }
        else {
            temp=0;
            return false;
        }
    }

    public void cellTapped(View view) {

        if(!gameActive) {
            Toast.makeText(this, "Please, restart the game to play!", Toast.LENGTH_SHORT).show();
            return;
        }

        image = (ImageView) view;
        tag = Integer.parseInt(image.getTag().toString());


        if(activeplayer == 0 && istapped[tag] == 2) {
            image.setImageResource(R.drawable.o);
            istapped[tag] = activeplayer;
            activeplayer = 1;

            if(tieCheck()) return;

            status.setText("Now, X's turn!!");
        }
        else if(activeplayer == 1 && istapped[tag] == 2) {
            image.setImageResource(R.drawable.x);
            istapped[tag] = activeplayer;
            activeplayer = 0;

            if(tieCheck()) return;

            status.setText("Now, O's turn");
        }
        else return;

        image.setTranslationY(-1000f);
        image.animate().translationYBy(1000f);


        for(int[] pos : winpos) {
            if(istapped[pos[0]] == istapped[pos[1]] && istapped[pos[1]] == istapped[pos[2]] && istapped[pos[0]] != 2) {
                if(istapped[pos[0]] == 0) status.setText("Congratulations, O won!!");
                else status.setText("Congratulations, X won!!");

                gameActive=false;
                //status.setBackgroundColor(Color.rgb(33,184,36));
                setTint(Color.rgb(33,184,36));

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        status = findViewById(R.id.status);
        reset = findViewById(R.id.reset);
    }
}