package database.dataclass;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "access", foreignKeys = {@ForeignKey(entity = UserEntity.class, parentColumns = "idUser", childColumns = "idUser", onDelete = ForeignKey.CASCADE),@ForeignKey(entity = RoomEntity.class, parentColumns = "idRoom", childColumns = "idRoom", onDelete = ForeignKey.CASCADE)})
public class AccessEntity {
    @PrimaryKey
    public int idAccess;

    @ColumnInfo(name = "idRoom", index = true)
    public int idRoom;

    @ColumnInfo(name = "idUser", index = true)
    public int idUser;

    @ColumnInfo(name = "role")
    public int role;
}