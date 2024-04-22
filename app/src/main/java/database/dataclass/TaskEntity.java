package database.dataclass;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "task", indices = {@Index(value = {"status_name"}, unique = true)}
, foreignKeys = {@ForeignKey(entity = StatusEntity.class, parentColumns = "id", childColumns = "status_id", onDelete = ForeignKey.CASCADE),
@ForeignKey(entity = UserEntity.class, parentColumns = "id", childColumns = "user_id", onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = RoomEntity.class, parentColumns = "id", childColumns = "room_id", onDelete = ForeignKey.CASCADE)})
public class TaskEntity {
    @PrimaryKey
    public int taskId;

    @ColumnInfo(name = "task_header")
    @NonNull
    public String taskHeader = "";

    @ColumnInfo(name = "task_body")
    public String taskBody = "";

    @ColumnInfo(name = "start_date")
    @NonNull
    public Date startDate = new Date();

    @ColumnInfo(name = "end_date")
    public Date endDate;

    @ColumnInfo(name = "status_id", index = true)
    public int statusId;

    @ColumnInfo(name = "user_id", index = true)
    public int userId;

    @ColumnInfo(name = "room_id", index = true)
    public int roomId;
}
