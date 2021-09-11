package com.example.campusrecruitmentCompany;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.campusrecruitmentCompany.ModelClasses.Company;
import com.example.campusrecruitmentCompany.ModelClasses.SelectedApplicants;
import com.example.campusrecruitmentCompany.ModelClasses.StudentResumeDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewStudentProfile_adapter extends RecyclerView.Adapter<ViewStudentProfile_adapter.ViewHolder> implements Filterable {
    public Context context;
    ArrayList<StudentResumeDetails> listData;
    String applicantnme,companynm,desc,studemail;
    DatabaseReference myRef;
    FirebaseUser user;
    Company company=new Company();
    FirebaseAuth auth;
    String userid;
    long id=0;

    public ViewStudentProfile_adapter(Context context, ArrayList<StudentResumeDetails> listData) {
        this.listData = listData;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_view_student_profile_adapter,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        StudentResumeDetails studentResumeDetails=listData.get(position);
        holder.studentname.setText(studentResumeDetails.getStudname());
        holder.fathername.setText(studentResumeDetails.getFathername());
        holder.dob.setText(studentResumeDetails.getDateofbirth());
        holder.nationality.setText(studentResumeDetails.getNationality());
        holder.hobbies.setText(studentResumeDetails.getHobbies());
        holder.schoolnam.setText(studentResumeDetails.getSchoolname());
        holder.schoolpassingyr.setText(studentResumeDetails.getSchoolpassingyear().toString());
        holder.sscpercentage.setText(studentResumeDetails.getSchoolpercenatge().toString());
        holder.degreeclg.setText(studentResumeDetails.getDegclgname());
        holder.degreepassingyr.setText(studentResumeDetails.getDegreepassingyear().toString());
        holder.degreepercentage.setText(studentResumeDetails.getDegreepercentage().toString());
        holder.achieve1.setText(studentResumeDetails.getAchievement1());
        holder.achieve2.setText(studentResumeDetails.getAchievement2());
        holder.achieve3.setText(studentResumeDetails.getAchievement3());
        holder.achieve4.setText(studentResumeDetails.getAchievement4());
        holder.langknown.setText(studentResumeDetails.getLangknown());
        holder.dbknown.setText(studentResumeDetails.getDbknown());
        holder.osknown.setText(studentResumeDetails.getOsknown());
        holder.rolekn.setText(studentResumeDetails.getRole());

        applicantnme=studentResumeDetails.getStudname();
        studemail=studentResumeDetails.getEmail();

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView studentname,fathername,dob,nationality,hobbies,schoolnam,schoolpassingyr,sscpercentage,degreeclg,degreepercentage,degreepassingyr,achieve1,achieve2,achieve3,achieve4,langknown,dbknown,osknown,rolekn;
        private ImageView studimg;
        String id;
        private Button b1,b2;
        public ViewHolder(View itemView) {
            super(itemView);

            studentname=itemView.findViewById(R.id.studentnametext);
            fathername=itemView.findViewById(R.id.parentstext);
            dob=itemView.findViewById(R.id.dob);
            nationality=itemView.findViewById(R.id.nationality);
            hobbies=itemView.findViewById(R.id.hobbies);
            schoolnam=itemView.findViewById(R.id.schoolname);
            schoolpassingyr=itemView.findViewById(R.id.schpassyear);
            sscpercentage=itemView.findViewById(R.id.sscper);
            degreeclg=itemView.findViewById(R.id.clgname);
            degreepassingyr=itemView.findViewById(R.id.gradpassyear);
            degreepercentage=itemView.findViewById(R.id.gradpercent);
            achieve1=itemView.findViewById(R.id.achievements1);
            achieve2=itemView.findViewById(R.id.achievements2);
            achieve3=itemView.findViewById(R.id.achievements3);
            achieve4=itemView.findViewById(R.id.achievements4);
            langknown=itemView.findViewById(R.id.langknown);
            dbknown=itemView.findViewById(R.id.dbknown);
            osknown=itemView.findViewById(R.id.osknown);
            rolekn=itemView.findViewById(R.id.Devtype);
            b1=itemView.findViewById(R.id.studpro_btn);
            long id=0;

            auth= FirebaseAuth.getInstance();
            user=auth.getCurrentUser();
            userid=user.getUid();
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            myRef = database.getReference("Company");
            company=new Company();

            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot npsnapshot : dataSnapshot.getChildren()) {

                        companynm=dataSnapshot.child(userid).child("companyname").getValue().toString();


                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    //Toast.makeText(context,databaseError.getMessage(),Toast.LENGTH_LONG).show();
                }
            });

            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final FirebaseDatabase db = FirebaseDatabase.getInstance();
                    final DatabaseReference myref = db.getReference("SelectedApplicants");

                    SelectedApplicants selectedApplicants=new SelectedApplicants();
                    selectedApplicants.setCompanyname(companynm);
                    selectedApplicants.setJobdescription(desc);
                    selectedApplicants.setStudentemail(studemail);
                    selectedApplicants.setStudentname(applicantnme);
                    myref.child(String.valueOf(id+1)).setValue(selectedApplicants);
                    Toast.makeText(context,"Student Selected Successfully", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}



