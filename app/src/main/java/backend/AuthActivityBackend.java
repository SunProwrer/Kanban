package backend;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import org.hse.kanban.RoomsActivity;

import database.checkers.Checker;
import database.dao.KanbanDao;
import database.dataclass.UserEntity;

public class AuthActivityBackend {
    private Activity activity;
    private final Context context;
    private final KanbanDao kanbanDao;
    private String login;
    private String password;

    public AuthActivityBackend(Context _context, KanbanDao _kanbanDao){
        context = _context;
        kanbanDao = _kanbanDao;

        login = "";
        password = "";
    }
    public void setLogin(String _login){
        login = _login;
    }
    public void setPassword(String _password){
        // TODO hash password
        password = _password;
    }

    private String getLogin(){
        return login;
    }
    private String getPassword(){
        return password;
    }

    public void logIn(){
        if (loginOrPasswordIsEmpty())
            return;

        if (Checker.checkLoginAndPassword(kanbanDao, login, password)){
            goToRooms();
        }
        else{
            // TODO message
        }
    }

    public void register(){
        if (loginOrPasswordIsEmpty())
            return;

        if (!Checker.checkLogin(kanbanDao, login)){
            createUser();
            goToRooms();
        }
        else{
            // TODO message
        }
    }

    private boolean loginOrPasswordIsEmpty() {
        return login.isEmpty() || password.isEmpty();
    }

    private void goToRooms(){
        Intent intent = new Intent(context, RoomsActivity.class);
        intent.putExtra(RoomsActivity.LOGIN, login);
        context.startActivity(intent);
    }

    private void createUser(){
        UserEntity user = new UserEntity(this.login, this.password);
//        user.login = login;
//        user.password = password;
        kanbanDao.insertUser(user);
    }
}
