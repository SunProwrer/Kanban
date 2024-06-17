package viewmodel;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.hse.kanban.RoomsActivity;

import model.database.checkers.Checker;
import model.database.dao.KanbanDao;
import model.entity.UserEntity;

// AuthActivityViewModel.java
public class AuthActivityViewModel extends AndroidViewModel {
    private final KanbanDao kanbanDao;
    private MutableLiveData<AuthState> authState;
    private MutableLiveData<String> authMessage;
    private String login;
    private String password;

    public AuthActivityViewModel(@NonNull Application application, KanbanDao kanbanDao) {
        super(application);
        this.kanbanDao = kanbanDao;
        authState = new MutableLiveData<>();
        authMessage = new MutableLiveData<>();
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        // TODO hash password
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public LiveData<AuthState> getAuthState() {
        return authState;
    }

    public LiveData<String> getAuthMessage() {
        return authMessage;
    }

    public void logIn() {
        if (loginOrPasswordIsEmpty()) {
            authState.setValue(AuthState.FAILURE);
            authMessage.setValue("Login or password cannot be empty");
            return;
        }

        if (Checker.checkLoginAndPassword(kanbanDao, login, password)) {
            authState.setValue(AuthState.SUCCESS);
        } else {
            authState.setValue(AuthState.FAILURE);
            authMessage.setValue("Invalid login or password");
        }
    }

    public void register() {
        if (loginOrPasswordIsEmpty()) {
            authState.setValue(AuthState.FAILURE);
            authMessage.setValue("Login or password cannot be empty");
            return;
        }

        if (!Checker.checkLogin(kanbanDao, login)) {
            createUser();
            authState.setValue(AuthState.SUCCESS);
        } else {
            authState.setValue(AuthState.FAILURE);
            authMessage.setValue("Login already exists");
        }
    }

    private boolean loginOrPasswordIsEmpty() {
        return login.isEmpty() || password.isEmpty();
    }

    private void createUser() {
        UserEntity user = new UserEntity();
        user.login = login;
        user.password = password;
        kanbanDao.insertUser(user);
    }
}

