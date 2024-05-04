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
    private Context context;
    private String login;
    private String password;
    private KanbanDao kanbanDao;

    private String getLogin(){
        return login;
    }
    private String getPassword(){
        return password;
    }
    public void setLogin(String _login){
        login = _login;
    }
    public void setPassword(String _password){
        // TODO hash password
        password = _password;
    }

    public AuthActivityBackend(Context _context, KanbanDao _kanbanDao){
        context = _context;
        kanbanDao = _kanbanDao;
    }

    public void logIn(){
        if (Checker.checkLoginAndPassword(kanbanDao, login, password)){
            goToRooms();
        }
        else{
            // TODO message
        }
    }
    public void register(){
        if (!Checker.checkLogin(kanbanDao, login)){
            createUser();
            goToRooms();
        }
        else{
            // TODO message
        }
    }

    private void goToRooms(){
        Intent intent = new Intent(context, RoomsActivity.class);
        intent.putExtra("login", login);
        context.startActivity(intent);
    }

    private void createUser(){
        UserEntity user = new UserEntity();
        user.login = login;
        user.password = password;
        kanbanDao.insertUser(user);
    }
}
