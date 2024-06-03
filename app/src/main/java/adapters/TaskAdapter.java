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

import model.entity.TaskEntity;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    private final LayoutInflater inflater;
    private final List<TaskEntity> tasks;

    public TaskAdapter(Context context, List<TaskEntity> sensors) {
        this.tasks = sensors;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.task_element, parent, false);
        return new TaskAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.ViewHolder holder, int position) {
        TaskEntity task = tasks.get(position);
        holder.headerTask.setText(task.header);
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final Button headerTask;
        ViewHolder(View view){
            super(view);
            headerTask = view.findViewById(R.id.widget_button_name_of_task_or_room);
        }
    }
}
