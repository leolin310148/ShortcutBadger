package me.leolin.shortcutbadger.example;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import me.leolin.shortcutbadger.ShortcutBadgeException;
import me.leolin.shortcutbadger.ShortcutBadger;


public class MainActivity extends Activity {

    private EditText numInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numInput = (EditText) findViewById(R.id.numInput);

        Button button = (Button) findViewById(R.id.btnSetBadge);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int badgeCount = 0;
                    try {
                        badgeCount = Integer.parseInt(numInput.getText().toString());
                    } catch (NumberFormatException e) {
                        Toast.makeText(getApplicationContext(), "Error input", Toast.LENGTH_SHORT).show();
                    }

                    ShortcutBadger.setBadge(getApplicationContext(), badgeCount);

                    Toast.makeText(getApplicationContext(), "Set count=" + badgeCount, Toast.LENGTH_SHORT).show();
                } catch (ShortcutBadgeException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
