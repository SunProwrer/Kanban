package model.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "tasks", indices = {@Index(value = {"header"}, unique = true)}
        , foreignKeys = {@ForeignKey(entity = RoomEntity.class, parentColumns = "idRoom", childColumns = "idRoom", onDelete = ForeignKey.CASCADE)})
public class TaskEntity {
    @PrimaryKey(autoGenerate = true)
    public int idTask;

    @ColumnInfo(name = "idRoom", index = true)
    public int idRoom;

    @ColumnInfo(name = "header")
    @NonNull
    public String header = "";

    @ColumnInfo(name = "body")
    public String body = "";

    @ColumnInfo(name = "deadline")
    @NonNull
    public Date deadline = new Date();

    @ColumnInfo(name = "status")
    public int status;

    public static int TODO = 0;
    public static int DOING = 1;
    public static int DONE = 2;
}
