package com.example.android_project.adapter;

import android.app.Activity;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_project.CreatePetActivity;
import com.example.android_project.R;
import com.example.android_project.model.Pet;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.annotation.Documented;

public class PetAdapter extends FirestoreRecyclerAdapter<Pet, PetAdapter.ViewHolder>{
    private FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
    Activity activity;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public PetAdapter(@NonNull FirestoreRecyclerOptions<Pet> options, Activity activity) {
        super(options);
        this.activity = activity;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int i, @NonNull Pet Pet) {
        DocumentSnapshot documentSnapshot =
                getSnapshots().getSnapshot(viewHolder.getAbsoluteAdapterPosition());
        final String id = documentSnapshot.getId();

        viewHolder.name.setText(Pet.getName());
        viewHolder.age.setText(Pet.getAge());
        viewHolder.color.setText(Pet.getColor());

        viewHolder.btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, CreatePetActivity.class);
                i.putExtra("id_pet", id);
                activity.startActivity(i);
            }
        });

        viewHolder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePet(id);
            }
        });
    }

    private void deletePet(String id) {
        mFirestore.collection("pet").document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(activity, "Removal correctly", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(activity, "Error deleting", Toast.LENGTH_LONG).show();
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_pet_single, parent, false);
        return new ViewHolder(v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, age, color;
        ImageView btn_delete, btn_update;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name_view);
            age = itemView.findViewById(R.id.age_view);
            color = itemView.findViewById(R.id.color_view);
            btn_delete = itemView.findViewById(R.id.btn_delete);
            btn_update = itemView.findViewById(R.id.btn_update);
        }
    }
}
