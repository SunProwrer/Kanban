package org.hse.kanban;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import adapters.RecyclerItemClickListener;
import adapters.RoomAdapter;
import viewmodel.RoomsActivityViewModel;
import model.database.DatabaseManager;
import model.database.dao.KanbanDao;

public class RoomsActivity extends AppCompatActivity {
    public static final String LOGIN = "extra_login";
    private static final String TAG = "RoomsActivity";
    private RoomsActivityViewModel viewModel;
    private TextView loginLabel;
    private EditText nameOfNewRoom;
    private RecyclerView recyclerView;
    private Button addRoomButton;
    private RoomAdapter adapter;

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
        observeViewModel();
    }

    private void initElements() {
        String login = getLogin();

        viewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication()))
                .get(RoomsActivityViewModel.class);
        viewModel.init(login);

        loginLabel = findViewById(R.id.label_login);
        loginLabel.setText(login);

        nameOfNewRoom = findViewById(R.id.input_newRoom);
        recyclerView = findViewById(R.id.layout_rooms);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        adapter = new RoomAdapter(this, new ArrayList<>());
        recyclerView.setAdapter(adapter);

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
                viewModel.setNameOfNewRoom(s.toString());
            }
        });

        addRoomButton.setOnClickListener(v -> viewModel.createNewRoom());

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        viewModel.goToRoom(RoomsActivity.this, position);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
    }

    private void observeViewModel() {
        viewModel.getRoomsLiveData().observe(this, rooms -> adapter.updateRooms(rooms));
    }

    private String getLogin() {
        Bundle extras = getIntent().getExtras();
        return (extras == null) ? "name" : extras.getString(LOGIN);
    }
}
