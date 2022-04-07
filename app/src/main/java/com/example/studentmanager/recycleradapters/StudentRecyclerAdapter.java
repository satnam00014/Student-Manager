package com.example.studentmanager.recycleradapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.studentmanager.R;
import com.example.studentmanager.datamodel.ClassOfSchool;
import com.example.studentmanager.datamodel.Students;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class StudentRecyclerAdapter  extends RecyclerView.Adapter<StudentRecyclerAdapter.ViewHolder> implements Filterable {


    private final Context context;
    private String ImageURL = "https://picsum.photos/200/300";
    private ArrayList<Students> arrayList;
    public StudentRecyclerAdapter(Context context) {
        this.context = context;
        arrayList = new ArrayList<>();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private final Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint == null || constraint.length() == 0) {
                return filterResults;
            }
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            notifyDataSetChanged();
        }
    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //this is where where view in inflated and will return view holder with view(that means card)
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.student_card, parent, false);
        return new ViewHolder((CardView) view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        //following is to set class name
        holder.studentName.setText("Student Name");

        holder.studentId.setText("Student Id");

        //following is to set images
        Glide.with(context).load(ImageURL)
                .apply(RequestOptions.circleCropTransform()).thumbnail(0.3f).into(holder.studentImageView);
        //below is condition that enable delete class button if condition is true

        holder.deleteButton.setOnClickListener(v -> {
            //call function to delete class this will only be enable when class is not empty
        });
        holder.currentCardView.setOnClickListener(v -> {

        });
    }

    @Override
    public int getItemCount() {
        return 20;
    }


    public void updateData(ArrayList<Students> arrayList){
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }

    // this is the view holder which holds the view
    public class ViewHolder extends RecyclerView.ViewHolder {
        private final CardView currentCardView;
        private final TextView studentName, studentId;
        private final ImageView studentImageView;
        private final FloatingActionButton deleteButton;
        private final FloatingActionButton editButton;
        public ViewHolder(@NonNull CardView cardView) {
            super(cardView);
            this.currentCardView = cardView;
            this.studentName = cardView.findViewById(R.id.student_name_card);
            this.studentId = cardView.findViewById(R.id.student_id_card);
            this.studentImageView = cardView.findViewById(R.id.student_image_card);
            this.deleteButton = cardView.findViewById(R.id.delete_bt_student_card);
            this.editButton = cardView.findViewById(R.id.edit_bt_student_card);
        }
    }
}