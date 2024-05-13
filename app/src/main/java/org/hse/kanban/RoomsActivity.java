package org.hse.kanban;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import adapters.RecyclerItemClickListener;
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
    Button addRoomButton;

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

        initElements();
        setHandlers();
    }

    private void initElements() {
        kanbanDao = DatabaseManager.getInstance(this).getKanbanDao();
        backend = new RoomsActivityBackend(this, kanbanDao, getLogin());

        loginLabel = findViewById(R.id.label_login);
        loginLabel.setText(backend.getLogin());
        nameOfNewRoom = findViewById(R.id.input_newRoom);
        recyclerView = findViewById(R.id.layout_rooms);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(backend.getAdapter());
        addRoomButton = findViewById(R.id.button_addRoom);
    }

    private void setHandlers() {
        nameOfNewRoom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                backend.setNameOfNewRoom(s.toString());
            }
        });

        addRoomButton.setOnClickListener(v -> {
            backend.createNewRoom();
        });

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        backend.goToRoom(position);
                    }
                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
    }

    private String getLogin() {
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return "name";
        }
        return extras.getString(LOGIN);
    }
}