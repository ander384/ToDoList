package todolist.christine.anderson.todolist.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import todolist.christine.anderson.todolist.R;
import todolist.christine.anderson.todolist.activities.ToDoItemActivity;
import todolist.christine.anderson.todolist.models.ToDoItemCollection;
import todolist.christine.anderson.todolist.models.ToDoItemModel;

/**
 * Created by Christine on 11/13/2017.
 */

public class ToDoListAdapter extends RecyclerView.Adapter<ToDoListAdapter.ToDoItemHolder> {


    @Override
    public ToDoItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.cell_to_do_item, parent, false);

        return new ToDoItemHolder(view);
    }

    @Override
    public void onBindViewHolder(ToDoItemHolder holder, int position)
    {
        ToDoItemModel item = ToDoItemCollection.GetInstance().getToDoItems().get(position);

        holder.setup(item);
    }

    @Override
    public int getItemCount() {
        return ToDoItemCollection.GetInstance().getToDoItems().size();
    }



    public class ToDoItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private ToDoItemModel toDoItem;
        private TextView toDoItemTitleTextView;

        public ToDoItemHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            this.toDoItemTitleTextView = itemView.findViewById(R.id.tv_to_do_item_title);
        }

        public void setup(ToDoItemModel itemModel)
        {
            this.toDoItem = itemModel;
            this.toDoItemTitleTextView.setText(itemModel.getTitle());
        }

        public void onClick(View itemView)
        {
            Intent intent = new Intent(itemView.getContext(), ToDoItemActivity.class);
            intent.putExtra("item_id", toDoItem.getId());
            itemView.getContext().startActivity(intent);
        }
    }
}