package database.dataclass;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "status", indices = {@Index(value = {"status_name"}, unique = true)})
public class StatusEntity {
    @PrimaryKey
    public int status_id;

    @ColumnInfo(name = "status_name")
    @NonNull
    public String statusName = "";
}
