package org.hse.kanban;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RoomActivity extends AppCompatActivity {
    public static final String USER = "extra_user_id";
    public static final String ROOM = "extra_room_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_room);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void initElements() {

    }

    private void getDataFromIntent() {
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }
        extras.getInt(USER);
        extras.getInt(ROOM);
    }
}