package org.hse.kanban;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import adapters.RecyclerItemClickListener;
import backend.RoomActivityBackend;
import backend.RoomsActivityBackend;
import database.DatabaseManager;
import database.dao.KanbanDao;

public class RoomActivity extends AppCompatActivity {
    public static final String USER = "extra_user_id";
    public static final String ROOM = "extra_room_id";
    private static final String TAG = "RoomActivity";
    RoomActivityBackend backend;
    KanbanDao kanbanDao;
    TextView nameOfRoomLabel;
    EditText headerOfNewTask;
    RecyclerView recyclerView;
    Button addTaskButton;

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

        initElements();
        setHandlers();
    }

    private void initElements() {
        kanbanDao = DatabaseManager.getInstance(this).getKanbanDao();
        backend = new RoomActivityBackend(this, kanbanDao, getIdUser(), getIdRoom());

        nameOfRoomLabel = findViewById(R.id.label_name);
        nameOfRoomLabel.setText(backend.getNameOfRoom());
        headerOfNewTask = findViewById(R.id.input_newTask);
        recyclerView = findViewById(R.id.layout_tasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(backend.getAdapter());
        addTaskButton = findViewById(R.id.button_addTask);
    }

    private String getIdUser() {
        Bundle extras = getIntent().getExtras();
        return extras.getString(USER);
    }

    private String getIdRoom() {
        Bundle extras = getIntent().getExtras();
        return extras.getString(ROOM);
    }

    private void setHandlers() {
        headerOfNewTask.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                backend.setHeaderOfNewTask(s.toString());
            }
        });

        addTaskButton.setOnClickListener(v -> {
            backend.createNewTask();
        });

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        backend.goToTask(position);
                    }
                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
    }
}