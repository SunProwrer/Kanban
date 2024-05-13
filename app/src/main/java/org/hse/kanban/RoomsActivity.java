package org.hse.kanban;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import backend.RoomsActivityBackend;
import database.DatabaseManager;
import database.dao.KanbanDao;

public class RoomsActivity extends AppCompatActivity {
    public static final String LOGIN = "extra_login";
    final String TAG = "RoomsActivity";
    RoomsActivityBackend backend;
    KanbanDao kanbanDao;
    TextView loginLabel;
    EditText nameOfNewRoom;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_rooms);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void initElements() {
        kanbanDao = DatabaseManager.getInstance(this).getKanbanDao();
        String login = getLogin();
        backend = new RoomsActivityBackend(this, kanbanDao, login);

        loginLabel = findViewById(R.id.label_login);
        loginLabel.setText("Логин: " + login);
        nameOfNewRoom = findViewById(R.id.input_newRoom);
        recyclerView = findViewById(R.id.layout_rooms);
    }

    private String getLogin() {
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return "name";
        }
        return extras.getString(LOGIN);
    }
}