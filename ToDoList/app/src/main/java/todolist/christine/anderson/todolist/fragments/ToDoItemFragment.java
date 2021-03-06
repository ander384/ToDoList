package todolist.christine.anderson.todolist.fragments;

        import android.app.Activity;
        import android.app.DatePickerDialog;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.os.Bundle;
        import android.support.annotation.NonNull;
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
        import android.widget.Toast;

        import org.joda.time.DateTime;
        import org.joda.time.format.DateTimeFormat;
        import org.joda.time.format.DateTimeFormatter;

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
    Button changeDateButton;
    android.support.design.widget.FloatingActionButton confirmButton;
    TextView dateTextView;

    private ToDoItemModel item;
    boolean newItem = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String theId = this.getArguments().getString("item_id");

        this.item = ToDoItemCollection.GetInstance(getActivity().getApplication()).getToDoItem(theId);
        if(item==null)
        {
            this.item = new ToDoItemModel();
            newItem = true;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_to_do_item, container, false);

        titleEditText = v.findViewById(R.id.et_title);
        String myTitle;
        if (this.item.getTitle()==null)
        {
            myTitle = "";
        }
        else{
            myTitle = this.item.getTitle();
        }
        titleEditText.setText(myTitle);

        descriptionEditText = v.findViewById(R.id.et_description);
        String myDescription;
        if(this.item.getDescription()==null)
        {
            myDescription = "";
        }
        else
        {
            myDescription = this.item.getDescription();
        }
        descriptionEditText.setText(myDescription);

        dateTextView = v.findViewById(R.id.tv_date);
        dateTextView.setText(item.getDate().toString(DateTimeFormat.longDate()));//TODO:new to String method

        confirmButton = v.findViewById(R.id.btn_confirm);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(newItem)
                {
                    ToDoItemCollection.GetInstance(getActivity().getApplication()).addToDoItem(item);
                }
                else
                {
                    ToDoItemCollection.GetInstance(getActivity().getApplication()).updateToDoItem(item);
                }

                getActivity().finish();
            }
        });


        changeDateButton = v.findViewById(R.id.btn_change_date);
        changeDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatePickerDialog dialog = new DatePickerDialog(getContext(),null, item.getDate().getYear(), item.getDate().getMonthOfYear()-1, item.getDate().getDayOfMonth());
                dialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        item.setDate(new DateTime(dialog.getDatePicker().getYear(), dialog.getDatePicker().getMonth()+1, dialog.getDatePicker().getDayOfMonth(), 0, 0));
                        dateTextView.setText(item.getDate().toString(DateTimeFormat.longDate()));
                    }
                });
                dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Does nothing
                    }

                });
                dialog.show();

                /*DatePickerFragment fragment = new DatePickerFragment();
                Bundle b = new Bundle();
                b.putString("item_id", item.getId());
                fragment.setArguments(b);

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fl_fragment_container,fragment)
                        .commit();*/
            }
        });

        completeButton = v.findViewById(R.id.btn_complete);
        this.completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToDoItemCollection.GetInstance(getActivity().getApplication()).removeToDoItem(item);
                getActivity().finish();//TODO:figure out
            }
        });

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
                ToDoItemCollection.GetInstance(getActivity().getApplication()).updateToDoItem(item);
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
