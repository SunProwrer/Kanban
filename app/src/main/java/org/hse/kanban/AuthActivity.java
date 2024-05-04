package org.hse.kanban;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import backend.AuthActivityBackend;
import database.DatabaseManager;
import database.dao.KanbanDao;

public class AuthActivity extends AppCompatActivity {
    private AuthActivityBackend backend;
    private KanbanDao kanbanDao;

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
    }

    private void initElements(){
        kanbanDao = DatabaseManager.getInstance(this).getKanbanDao();
        backend = new AuthActivityBackend(this, kanbanDao);
    }
}