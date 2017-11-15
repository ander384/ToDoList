package todolist.christine.anderson.todolist.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import todolist.christine.anderson.todolist.models.ToDoItemCollection;
import todolist.christine.anderson.todolist.models.ToDoItemModel;
import todolist.christine.anderson.todolist.R;

/**
 * Created by Christine on 11/6/2017.
 */

public class ToDoItemFragment extends Fragment {

    EditText titleEditText;
    EditText descriptionEditText;
    Button completeButton;
    TextView dateTextView;

    private ToDoItemModel item;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String theId = this.getArguments().getString("item_id");

        this.item = ToDoItemCollection.GetInstance().getToDoItem(theId);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_to_do_item, container, false);

        titleEditText = v.findViewById(R.id.et_title);
        titleEditText.setText(this.item.getTitle());

        descriptionEditText = v.findViewById(R.id.et_description);
        descriptionEditText.setText(this.item.getDescription());

        dateTextView = v.findViewById(R.id.tv_date);
        dateTextView.setText(item.dateToString());//TODO:new to String method

        completeButton = v.findViewById(R.id.btn_complete);

        this.titleEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                item.setTitle(editable.toString());
            }
        });

        this.descriptionEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                item.setDescription(editable.toString());
            }
        });

        return v;
    }
}
