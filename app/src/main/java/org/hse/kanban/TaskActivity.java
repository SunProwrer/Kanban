package org.hse.kanban;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import viewmodel.TaskActivityViewModel;
import model.database.DatabaseManager;
import model.database.dao.KanbanDao;

public class TaskActivity extends AppCompatActivity {
    public static final String ROOM = "extra_room_id";
    public static final String TASK = "extra_task_id";
    private KanbanDao kanbanDao;
    private TaskActivityViewModel backend;
    private TextView headerLabel;
    private TextView statusLabel;
    private TextView deadlineLabel;
    private TextView bodyLabel;
    private Button prevStatus;
    private Button nextStatus;

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
    }
    
    private void initElements() {
        kanbanDao = DatabaseManager.getInstance(this).getKanbanDao();
        backend = new TaskActivityViewModel(this, kanbanDao);
        headerLabel = findViewById(R.id.label_taskHead);
        statusLabel = findViewById(R.id.label_status);
        deadlineLabel = findViewById(R.id.label_deadline);
        bodyLabel = findViewById(R.id.label_taskBody);
        prevStatus = findViewById(R.id.button_prev_status);
        nextStatus = findViewById(R.id.button_next_status);
    }

    private void setDataToViews() {

    }

    private void setHandlers() {

    }

    private void getData() {

    }
}