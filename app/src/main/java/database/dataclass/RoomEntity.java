package database.dataclass;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "rooms", indices = {@Index(value = {"name"}, unique = true)})
public class RoomEntity {
    @PrimaryKey(autoGenerate = true)
    public int idRoom;

    @ColumnInfo(name = "name")
    @NonNull
    public String name = "";
}
