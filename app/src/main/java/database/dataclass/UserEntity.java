package database.dataclass;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "users", indices = {@Index(value = {"login"}, unique = true)})
public class UserEntity {
    @PrimaryKey
    public int idUser;

    @ColumnInfo(name = "login")
    @NonNull
    public String login = "";

    @ColumnInfo(name = "password")
    @NonNull
    public String password = "";
}
