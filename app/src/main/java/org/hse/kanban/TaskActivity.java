package org.hse.kanban;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import backend.TaskActivityBackend;
import database.DatabaseManager;
import database.dao.KanbanDao;

public class TaskActivity extends AppCompatActivity {
    public static final String ROOM = "extra_room_id";
    public static final String TASK = "extra_task_id";
    private KanbanDao kanbanDao;
    private TaskActivityBackend backend;
    private TextView headerLabel;
    private TextView statusLabel;
    private TextView deadlineLabel;
    private TextView bodyLabel;
    private Button prevStatus;
    private Button nextStatus;
    private Button deleteTask;
    private Button updateTask;

    public void updateViews() {
        setDataToViews();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_task);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initElements();
        setDataToViews();
        setHandlers();
        PlsDontREPEAT.taskActivity = this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PlsDontREPEAT.roomActivity.updateViews();
    }

    private void initElements() {
        kanbanDao = DatabaseManager.getInstance(this).getKanbanDao();
        int idTask = getIntent().getExtras().getInt(TASK);
        backend = new TaskActivityBackend(this, kanbanDao, idTask);
        backend.taskActivity = this;
        headerLabel = findViewById(R.id.label_taskHead);
        statusLabel = findViewById(R.id.label_status);
        deadlineLabel = findViewById(R.id.label_deadline);
        bodyLabel = findViewById(R.id.label_taskBody);
        prevStatus = findViewById(R.id.button_prev_status);
        nextStatus = findViewById(R.id.button_next_status);
        deleteTask = findViewById(R.id.button_deleteTask);
        updateTask = findViewById(R.id.button_changeTask);
    }

    private void setDataToViews() {
        headerLabel.setText(backend.getTaskHeader());
        statusLabel.setText(backend.getTaskStatus());
        deadlineLabel.setText(backend.getDeadline());
        bodyLabel.setText(backend.getTaskBody());
    }

    private void setHandlers() {
        prevStatus.setOnClickListener(v -> {
            backend.decreaseTaskStatus();
            setDataToViews();
        });

        nextStatus.setOnClickListener(v -> {
            backend.increaseTaskStatus();
            setDataToViews();
        });

        deleteTask.setOnClickListener(v -> {
            backend.deleteTask();
            finish();
        });

        updateTask.setOnClickListener(v -> {
            backend.goUpdateTask();
            setDataToViews();
        });
    }
}