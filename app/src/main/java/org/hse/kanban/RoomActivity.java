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
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import adapters.RecyclerItemClickListener;
import adapters.TaskAdapter;
import viewmodel.RoomActivityViewModel;
import model.database.DatabaseManager;
import model.database.dao.KanbanDao;

public class RoomActivity extends AppCompatActivity {
    public static final String USER = "extra_user_id";
    public static final String ROOM = "extra_room_id";
    private static final String TAG = "RoomActivity";
    RoomActivityViewModel viewModel;
    KanbanDao kanbanDao;
    TextView nameOfRoomLabel;
    EditText headerOfNewTask;
    RecyclerView recyclerView;
    Button addTaskButton;
    Button switchToTODO;
    Button switchToDOING;
    Button switchToDONE;

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
    }

    private void initElements() {
        kanbanDao = DatabaseManager.getInstance(this).getKanbanDao();

        // Использование ViewModelProvider для получения экземпляра ViewModel
        viewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new RoomActivityViewModel(getApplication(), kanbanDao, getIdUser(), getIdRoom());
            }
        }).get(RoomActivityViewModel.class);

        nameOfRoomLabel = findViewById(R.id.label_name);
        headerOfNewTask = findViewById(R.id.input_newTask);
        recyclerView = findViewById(R.id.layout_tasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        // Инициализация адаптера
        viewModel.getTasksLiveData().observe(this, tasks -> {
            TaskAdapter adapter = new TaskAdapter(this, tasks);
            recyclerView.setAdapter(adapter);
        });

        addTaskButton = findViewById(R.id.button_addTask);
        switchToTODO = findViewById(R.id.button_todo);
        switchToDOING = findViewById(R.id.button_doing);
        switchToDONE = findViewById(R.id.button_done);
    }

    private void setDataToViews() {
        nameOfRoomLabel.setText(viewModel.getNameOfRoom());
        updateTextOnButtons();
    }

    private void updateTextOnButtons() {
        switchToTODO.setText(getNameOfButton(R.string.room_button_todo, viewModel.getCountOfTODOTasks()));
        switchToDOING.setText(getNameOfButton(R.string.room_button_doing, viewModel.getCountOfDOINGTasks()));
        switchToDONE.setText(getNameOfButton(R.string.room_button_done, viewModel.getCountOfDONETasks()));
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
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                viewModel.setHeaderOfNewTask(s.toString());
            }
        });

        addTaskButton.setOnClickListener(v -> {
            viewModel.createNewTask();
            updateTextOnButtons();
        });

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        viewModel.goToTask(position);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

        switchToTODO.setOnClickListener(v -> viewModel.changeStatusToTODO());
        switchToDOING.setOnClickListener(v -> viewModel.changeStatusToDOING());
        switchToDONE.setOnClickListener(v -> viewModel.changeStatusToDONE());
    }
}
