package model.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "users", indices = {@Index(value = {"login"}, unique = true)})
public class UserEntity {
    @PrimaryKey(autoGenerate = true)
    public int idUser;

    @ColumnInfo(name = "login")
    @NonNull
    public String login = "";

    @ColumnInfo(name = "password")
    @NonNull
    public String password = "";

    public UserEntity(String _login, String _password){
        login = _login;
        password = _password;
    }

    public UserEntity(){
        login = "";
        password = "";
    }
}
