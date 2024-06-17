package org.hse.kanban;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import viewmodel.AuthActivityViewModel;
import model.database.DatabaseManager;
import model.database.dao.KanbanDao;
import model.entity.UserEntity;
import viewmodel.AuthState;

// AuthActivity.java
public class AuthActivity extends AppCompatActivity {
    private AuthActivityViewModel viewModel;
    private EditText loginLabel;
    private EditText passwordLabel;
    private Button logInButton;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_auth);
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
        KanbanDao kanbanDao = DatabaseManager.getInstance(this).getKanbanDao();

        viewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new AuthActivityViewModel(getApplication(), kanbanDao);
            }
        }).get(AuthActivityViewModel.class);

        loginLabel = findViewById(R.id.input_login);
        passwordLabel = findViewById(R.id.input_password);
        logInButton = findViewById(R.id.button_auth);
        registerButton = findViewById(R.id.button_register);
    }

    private void setHandlers() {
        loginLabel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                viewModel.setLogin(s.toString());
            }
        });

        passwordLabel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                viewModel.setPassword(s.toString());
            }
        });

        logInButton.setOnClickListener(v -> viewModel.logIn());

        registerButton.setOnClickListener(v -> viewModel.register());
    }

    private void observeViewModel() {
        viewModel.getAuthState().observe(this, authState -> {
            if (authState == AuthState.SUCCESS) {
                goToRooms();
            } else if (authState == AuthState.FAILURE) {
                Toast.makeText(this, viewModel.getAuthMessage().getValue(), Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.getAuthMessage().observe(this, message -> {
            if (message != null && !message.isEmpty()) {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void goToRooms() {
        Intent intent = new Intent(this, RoomsActivity.class);
        intent.putExtra(RoomsActivity.LOGIN, viewModel.getLogin());
        startActivity(intent);
    }
}

