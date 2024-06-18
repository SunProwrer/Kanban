package org.hse.kanban;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;
import java.util.Date;

import backend.TaskActivityBackend;
import backend.TaskChangeActivityBackend;
import database.DatabaseManager;
import database.dao.KanbanDao;

public class TaskChangeActivity extends AppCompatActivity {
    public static final String TAG = "TaskChangeActivity";
    public static final String TASK = "extra_task_id";
    private KanbanDao kanbanDao;
    private TaskChangeActivityBackend backend;
    private EditText headerLabel;
    private TextView statusLabel;
    private DatePicker deadlineLabel;
    private EditText bodyLabel;
    private Button prevStatus;
    private Button nextStatus;
    private Button saveTask;
    private Button cancelTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_task_change);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initElements();
        setDataToViews();
        setHandlers();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PlsDontREPEAT.taskActivity.updateViews();
    }

    private void initElements() {
        kanbanDao = DatabaseManager.getInstance(this).getKanbanDao();
        int idTask = getIntent().getExtras().getInt(TASK);
        backend = new TaskChangeActivityBackend(this, kanbanDao, idTask);
        headerLabel = findViewById(R.id.input_taskHead);
        statusLabel = findViewById(R.id.label_status);
        deadlineLabel = findViewById(R.id.input_deadline);
        bodyLabel = findViewById(R.id.input_taskBody);
        prevStatus = findViewById(R.id.button_prev_status);
        nextStatus = findViewById(R.id.button_next_status);
        saveTask = findViewById(R.id.button_saveTask);
        cancelTask = findViewById(R.id.button_cancel);
    }

    private void setDataToViews() {
        headerLabel.setText(backend.getTaskHeader());
        statusLabel.setText(backend.getTaskStatus());
//        deadlineLabel.setText(backend.getDeadline());
        bodyLabel.setText(backend.getTaskBody());
        Calendar date = Calendar.getInstance();
        date.setTime(backend.getDeadlineDate());
        deadlineLabel.init(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year + 1900, monthOfYear, dayOfMonth);
                //backend.updateDeadline(calendar);
                backend.updateDeadline(year, monthOfYear, dayOfMonth);
            }
        });
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

        saveTask.setOnClickListener(v -> {
            backend.applyChanges();
            finish();
        });

        cancelTask.setOnClickListener(v -> {
            finish();
        });

        headerLabel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                backend.updateHeader(s.toString());
            }
        });

        bodyLabel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                backend.updateBody(s.toString());
            }
        });
    }
}