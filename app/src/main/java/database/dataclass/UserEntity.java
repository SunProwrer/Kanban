package database.dataclass;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "users", indices = {@Index(value = {"name"}, unique = true)})
public class UserEntity {
    @PrimaryKey
    public int idUser;

    @ColumnInfo(name = "name")
    @NonNull
    public String name = "";

    @ColumnInfo(name = "password")
    public int password;
}
