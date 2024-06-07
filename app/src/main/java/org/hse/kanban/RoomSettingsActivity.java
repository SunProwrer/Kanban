package org.hse.kanban;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import backend.RoomActivityBackend;
import backend.RoomSettingsActivityBackend;
import database.DatabaseManager;
import database.dao.KanbanDao;

public class RoomSettingsActivity extends AppCompatActivity {
    public static final String USER = "extra_user_id";
    public static final String ROOM = "extra_room_id";
    RoomSettingsActivityBackend backend;
    KanbanDao kanbanDao;
    EditText roomNameInput;
    Button applyNameButton;
    EditText newNameUserInput;
    Button newNameUserButton;
    RecyclerView usersInRoomList;
    Button deleteRoomButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_room_settings);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initElements();
        setDataToViews();
        setHandlers();
    }

    private void initElements() {
        kanbanDao = DatabaseManager.getInstance(this).getKanbanDao();
        Bundle extras = getIntent().getExtras();
        backend = new RoomSettingsActivityBackend(this, kanbanDao, extras.getInt(ROOM));
        roomNameInput = findViewById(R.id.input_roomName);
        applyNameButton = findViewById(R.id.button_editRoomName);
        newNameUserInput = findViewById(R.id.input_newUser);
        newNameUserButton = findViewById(R.id.button_addUser);
        usersInRoomList = findViewById(R.id.recycler_users_list);
        deleteRoomButton = findViewById(R.id.button_deleteRoom);
    }

    private void setDataToViews() {

    }

    private void setHandlers() {

    }
}