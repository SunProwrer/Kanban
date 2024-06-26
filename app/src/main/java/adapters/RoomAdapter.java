package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.hse.kanban.R;

import java.util.List;

import model.entity.RoomEntity;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder> {
    private final LayoutInflater inflater;
    private List<RoomEntity> rooms;

    public RoomAdapter(Context context, List<RoomEntity> rooms) {
        this.rooms = rooms;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.task_element, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RoomEntity room = rooms.get(position);
        holder.nameRoom.setText(room.name);
    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }

    public void updateRooms(List<RoomEntity> newRooms) {
        this.rooms = newRooms;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final Button nameRoom;

        ViewHolder(View view) {
            super(view);
            nameRoom = view.findViewById(R.id.widget_button_name_of_task_or_room);
        }
    }
}

