package org.hse.kanban;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import androidx.room.RoomOpenHelper;
import androidx.sqlite.db.SupportSQLiteDatabase;

import adapters.RecyclerItemClickListener;
import backend.RoomActivityBackend;
import database.DatabaseManager;
import database.dao.KanbanDao;

public class RoomActivity extends AppCompatActivity {
    public static final String USER = "extra_user_id";
    public static final String ROOM = "extra_room_id";
    private static final String TAG = "RoomActivity";
    RoomActivityBackend backend;
    KanbanDao kanbanDao;
    EditText nameOfRoomLabel;
    EditText headerOfNewTask;
    RecyclerView recyclerView;
    Button addTaskButton;
    Button switchToTODO;
    Button switchToDOING;
    Button switchToDONE;
    Button changeName;

    public void updateViews() {
        setDataToViews();
    }

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
        setDataToViews();
        setHandlers();
        PlsDontREPEAT.roomActivity = this;
    }

    private void initElements() {
        kanbanDao = DatabaseManager.getInstance(this).getKanbanDao();
        backend = new RoomActivityBackend(this, kanbanDao, getIdUser(), getIdRoom());

        nameOfRoomLabel = findViewById(R.id.label_name);
        headerOfNewTask = findViewById(R.id.input_newTask);
        recyclerView = findViewById(R.id.layout_tasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(backend.getAdapter());
        addTaskButton = findViewById(R.id.button_addTask);
        switchToTODO = findViewById(R.id.button_todo);
        switchToDOING = findViewById(R.id.button_doing);
        switchToDONE = findViewById(R.id.button_done);
        changeName = findViewById(R.id.button_settings);
    }

    private void setDataToViews() {
        nameOfRoomLabel.setText(backend.getNameOfRoom());
        updateTextOnButtons();
    }

    private void updateTextOnButtons() {
        switchToTODO.setText(getNameOfButton(R.string.room_button_todo, backend.getCountOfTODOTasks()));
        switchToDOING.setText(getNameOfButton(R.string.room_button_doing, backend.getCountOfDOINGTasks()));
        switchToDONE.setText(getNameOfButton(R.string.room_button_done, backend.getCountOfDONETasks()));
    }

    private String getNameOfButton(int msg, int count) {
        return String.format(getString(msg), count);
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

        nameOfRoomLabel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                backend.setNewNameOfRoom(s.toString());
            }
        });

        addTaskButton.setOnClickListener(v -> {
            backend.createNewTask();
            updateTextOnButtons();
        });

        changeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backend.saveNewNameOfRoom();
            }
        });

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        backend.goToTask(position);
//                        updateTextOnButtons();
                    }
                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

        switchToTODO.setOnClickListener(v -> backend.changeStatusToTODO());
        switchToDOING.setOnClickListener(v -> backend.changeStatusToDOING());
        switchToDONE.setOnClickListener(v -> backend.changeStatusToDONE());
    }
}