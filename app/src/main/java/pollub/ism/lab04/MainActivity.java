package pollub.ism.lab04;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    char symbolX = 'X', symbolO = 'O', actualsymbol;
    char[][] gamestatus = new char[3][3]; // status gry przechowywany będzie w tablicy dwuwymiarowej
    String[] savestatus = new String[9];
    boolean[] saveview = {true,true,true,true,true,true,true,true,true};
    boolean firstplayer = true;
    public Button button1, button2, button3, button4, button5,button6, button7, button8, button9;
    int amount=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArray("gamestatus",savestatus);
        outState.putBooleanArray("isenable",saveview);
        outState.putBoolean("whichplayer", firstplayer);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        savestatus =savedInstanceState.getStringArray("gamestatus");
        saveview =savedInstanceState.getBooleanArray("isenable");
        firstplayer = savedInstanceState.getBoolean("whichplayer");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Button button;
        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                button = (Button) findViewById(getResources().getIdentifier("button_" + i + "_"+j, "id", this.getPackageName()));
                button.setText(savestatus[i*3+j]);
                button.setEnabled(saveview[i*3+j]);
                if (savestatus[i*3+j]!=null){
                    gamestatus[i][j]=savestatus[i*3+j].charAt(0);
                }
            }
        }
    }

    public void kliknieciePrzycisku(View view) {
        String nazwaprzycisku = view.getResources().getResourceEntryName(view.getId()); // wyciągnięcie nazwy przycisku po kliknięciu
        String[] idSplit = nazwaprzycisku.split("_");
        int wiersz = Integer.parseInt(idSplit[1]);
        int kolumna = Integer.parseInt(idSplit[2]);
        amount++;

        if (firstplayer) {
            gamestatus[wiersz][kolumna] = symbolX;
            firstplayer = false;
            actualsymbol=symbolX;
            savestatus[(wiersz)*3+(kolumna)] = Character.toString(symbolX);
        }
        else {
            gamestatus[wiersz][kolumna] = symbolO;
            firstplayer = true;
            actualsymbol=symbolO;
            savestatus[(wiersz)*3+(kolumna)] = Character.toString(symbolO);
        }

        Button button = (Button) findViewById(view.getId());
        button.setText(Character.toString(gamestatus[wiersz][kolumna]));
        button.setEnabled(false);

        if (checkrow(gamestatus,actualsymbol)==true || checkcolumn(gamestatus,actualsymbol)==true ||checkslant(gamestatus,actualsymbol)==true || checkslant2(gamestatus,actualsymbol)==true){
            setenabled();
            Toast.makeText(this,"Wygrały " + actualsymbol, Toast.LENGTH_LONG).show();
        }
        else if(amount==9) {
            setenabled();
            Toast.makeText(this,"Remis", Toast.LENGTH_LONG).show();
        }
        saveview[wiersz*3+kolumna]=false;

    }

    public static boolean checkrow(char[][] gamestatus, char symbol) {
        for (int i = 0; i <= 2; i++) {
            boolean win = true;
            for (int j = 0; j <= 2; j++) {
                if (gamestatus[i][j] != symbol) {
                    win = false;
                    break;
                }
            }
            if (win) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkslant(char[][] gamestatus, char symbol) {
        for (int i = 0; i <= 2; i++) {
            if (gamestatus[i][i] != symbol) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkslant2(char[][] gamestatus, char symbol) {
            if (gamestatus[2][0] != symbol || gamestatus[1][1] != symbol || gamestatus[0][2] != symbol) {
                return false;
            }
        return true;
    }

    public static boolean checkcolumn(char[][] gamestatus, char symbol) {
        for (int i = 0; i <= 2; i++) {
            boolean win = true;
            for (int j = 0; j <= 2; j++) {
                if (gamestatus[j][i] != symbol) {
                    win = false;
                    break;
                }
            }
            if (win) {
                return true;
            }
        }
        return false;
    }

    public void setenabled(){
        button1 = (Button) findViewById(R.id.button_0_0);
        button2 = (Button) findViewById(R.id.button_0_1);
        button3 = (Button) findViewById(R.id.button_0_2);
        button4 = (Button) findViewById(R.id.button_1_0);
        button5 = (Button) findViewById(R.id.button_1_1);
        button6 = (Button) findViewById(R.id.button_1_2);
        button7 = (Button) findViewById(R.id.button_2_0);
        button8 = (Button) findViewById(R.id.button_2_1);
        button9 = (Button) findViewById(R.id.button_2_2);
        button1.setEnabled(false);
        button2.setEnabled(false);
        button3.setEnabled(false);
        button4.setEnabled(false);
        button5.setEnabled(false);
        button6.setEnabled(false);
        button7.setEnabled(false);
        button8.setEnabled(false);
        button9.setEnabled(false);
    }
}