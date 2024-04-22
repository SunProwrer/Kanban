package database.dataclass;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "user", indices = {@Index(value = {"name"}, unique = true)})
public class UserEntity {
    @PrimaryKey
    public int user_id;

    @ColumnInfo(name = "name")
    @NonNull
    public String name = "";

    @ColumnInfo(name = "login")
    @NonNull
    public String login = "";

    @ColumnInfo(name = "pass")
    public int pass;
}
