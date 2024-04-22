package database.dataclass;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "user", indices = {@Index(value = {"room_name"}, unique = true)})
public class RoomEntity {
    @PrimaryKey
    public int room_id;

    @ColumnInfo(name = "room_name")
    @NonNull
    public String roomName = "";
}
