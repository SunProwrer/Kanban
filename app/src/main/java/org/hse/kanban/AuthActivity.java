package org.hse.kanban;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

import backend.AuthActivityBackend;
import database.DatabaseManager;
import database.dao.KanbanDao;
import database.dataclass.UserEntity;

public class AuthActivity extends AppCompatActivity {
    private AuthActivityBackend backend;
    private KanbanDao kanbanDao;
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
    }

    private void initElements(){
        kanbanDao = DatabaseManager.getInstance(this).getKanbanDao();
        backend = new AuthActivityBackend(this, kanbanDao);

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
                backend.setLogin(s.toString());
            }
        });

        passwordLabel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                backend.setPassword(s.toString());
            }
        });

        logInButton.setOnClickListener(v -> backend.logIn());

        registerButton.setOnClickListener(v -> backend.register());
    }
}