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
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class ClassesRecyclerAdapter extends RecyclerView.Adapter<ClassesRecyclerAdapter.ViewHolder> implements Filterable {


    private final Context context;
    public ClassesRecyclerAdapter(Context context) {
        this.context = context;
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
                .inflate(R.layout.classroom_card, parent, false);
        return new ViewHolder((CardView) view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        //following is to set class name
        holder.className.setText("Classname");

        //following is to set number of students in class.
        holder.numberOfStudents.setText("8 students");

        //following is to set images
        Glide.with(context).load(R.drawable.button_background)
                .apply(RequestOptions.circleCropTransform()).thumbnail(0.3f).into(holder.classImageView);
        //below is condition that enable delete class button if condition is true
        if (true){
            holder.deleteButton.setVisibility(View.VISIBLE);
        }
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



    // this is the view holder which holds the view
    public class ViewHolder extends RecyclerView.ViewHolder {
        private final CardView currentCardView;
        private final TextView className, numberOfStudents;
        private final ImageView classImageView;
        private final FloatingActionButton deleteButton;
        public ViewHolder(@NonNull CardView cardView) {
            super(cardView);
            this.currentCardView = cardView;
            this.className = cardView.findViewById(R.id.class_name_card);
            this.numberOfStudents = cardView.findViewById(R.id.student_numbers_card);
            this.classImageView = cardView.findViewById(R.id.class_image_card);
            this.deleteButton = cardView.findViewById(R.id.delete_bt_folder_card);
        }
    }
}