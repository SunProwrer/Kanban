package model.repository;

import android.app.Application;

import java.util.List;

import model.database.AppDatabase;
import model.database.dao.KanbanDao;
import model.entity.UserEntity;

public class UserRepository {
    private final KanbanDao kanbanDao;

    public UserRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        kanbanDao = db.kanbanDao();
    }

    public List<UserEntity> getUsers() {
        return kanbanDao.getUsers();
    }

    public List<UserEntity> getUserById(long idUser) {
        return kanbanDao.getUserById(idUser);
    }

    public List<UserEntity> getUserByLoginAndPassword(String login, String password) {
        return kanbanDao.getUserByLoginAndPassword(login, password);
    }

    public List<UserEntity> getUserByLogin(String login) {
        return kanbanDao.getUserByLogin(login);
    }

    public void updateUser(UserEntity user) {
        kanbanDao.updateUser(user);
    }

    public void insertUser(UserEntity user) {
        kanbanDao.insertUser(user);
    }

    public void insertUsers(List<UserEntity> users) {
        kanbanDao.insertUsers(users);
    }

    public void deleteUser(UserEntity user) {
        kanbanDao.deleteUser(user);
    }
}
