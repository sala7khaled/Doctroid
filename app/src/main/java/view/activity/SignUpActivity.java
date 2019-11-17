package view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.s7k.doctroid.R;

import dialog.ProgressViewDialog;
import helpers.Validator;
import network.model.User;
import view.base.BaseActivity;

public class SignUpActivity extends BaseActivity {

    public SignUpActivity() {
        super(R.layout.activity_sign_up, true);
    }

    EditText firstName, lastName, email, password, confirmPassword, phone;
    ImageView errorDialog, male, female;
    TextView errorMessage;
    Button signUp;
    boolean maleSelected = false;
    boolean femaleSelected = false;

    @Override
    protected void doOnCreate(Bundle bundle) {
        toolbarTextView.setText("Sign Up");
        toolbarBackImageView.setVisibility(View.VISIBLE);

        initializeComponents();
        setListeners();

    }

    private void initializeComponents() {
        firstName = findViewById(R.id.signUp_firstName_editText);
        lastName = findViewById(R.id.signUp_lastName_editText);
        email = findViewById(R.id.signUp_email_editText);
        password = findViewById(R.id.signUp_password_editText);
        confirmPassword = findViewById(R.id.signUp_confirmPassword_editText);
        male = findViewById(R.id.signUp_male_imageView);
        female = findViewById(R.id.signUp_female_imageView);
        signUp = findViewById(R.id.signUp_signUp_button);
        phone = findViewById(R.id.signUp_phone_editText);
        errorMessage = findViewById(R.id.signUp_errorMessage_textView);
        errorDialog = findViewById(R.id.signUp_errorDialog_imageView);
    }

    private void setListeners() {

        male.setOnClickListener(view -> {
            maleSelected = true;
            femaleSelected = false;
            male.setImageDrawable(getResources().getDrawable(R.drawable.signup_male_selected));
            female.setImageDrawable(getResources().getDrawable(R.drawable.signup_female_deselected));
        });
        
        female.setOnClickListener(view -> {
            maleSelected = false;
            femaleSelected = true;
            male.setImageDrawable(getResources().getDrawable(R.drawable.signup_male_deselected));
            female.setImageDrawable(getResources().getDrawable(R.drawable.signup_female_selected));
        });

        signUp.setOnClickListener(view -> {

            if(firstName.getText().toString().trim().isEmpty() || lastName.getText().toString().trim().isEmpty())
            {
                errorDialog.setVisibility(View.VISIBLE);
                errorMessage.setVisibility(View.VISIBLE);
                errorMessage.setText("Enter your full name");
            }
            else if(!Validator.isValidEmail(email.getText().toString().trim()))
            {
                errorDialog.setVisibility(View.VISIBLE);
                errorMessage.setVisibility(View.VISIBLE);
                errorMessage.setText(getString(R.string.email_not_valid));
            }
            else if(!Validator.isValidPhoneNumber(phone.getText().toString().trim()))
            {
                errorDialog.setVisibility(View.VISIBLE);
                errorMessage.setVisibility(View.VISIBLE);
                errorMessage.setText(getString(R.string.phone_not_valid));
            }
            else if(!Validator.isConfirmPassMatchPass(password.getText().toString().trim(),
                    confirmPassword.getText().toString().trim()))
            {
                errorDialog.setVisibility(View.VISIBLE);
                errorMessage.setVisibility(View.VISIBLE);
                errorMessage.setText(getString(R.string.password_not_valid));
            }
            else if(!maleSelected && !femaleSelected)
            {
                errorDialog.setVisibility(View.VISIBLE);
                errorMessage.setVisibility(View.VISIBLE);
                errorMessage.setText("Please select a gender");
            }
            else
            {
                errorDialog.setVisibility(View.INVISIBLE);
                errorMessage.setVisibility(View.INVISIBLE);
            }

            if (Validator.registerValidation(this, firstName, lastName,
                    email, password, confirmPassword, phone))
            {
                if (maleSelected || femaleSelected)
                {
                    createNewUser();
                }
            }
            
        });

    }

    private void createNewUser() {

        String firstNameSTR = firstName.getText().toString().trim();
        String lastNameSTR = lastName.getText().toString().trim();
        String emailSTR = email.getText().toString().trim();
        String phoneSTR = phone.getText().toString().trim();
        String passwordSTR = password.getText().toString().trim();
        String genderSTR;

        if(maleSelected)
        {
            genderSTR = "male";
        }
        else if(femaleSelected)
        {
            genderSTR = "female";
        }
        else
        {
            genderSTR = "empty";
        }

        // TODO send User info to Database
        ProgressViewDialog progressViewDialog = new ProgressViewDialog(this);
        progressViewDialog.isShowing();
        progressViewDialog.setDialogCancelable(false);
        progressViewDialog.setCanceledOnTouchOutside(false);
        progressViewDialog.showProgressDialog("Creating new account");

        User user = new User(firstNameSTR, lastNameSTR, emailSTR, phoneSTR, passwordSTR, genderSTR);
        Toast.makeText(this,
                user.getFullName() + "\n"+user.getEmail() +"\n"+ user.getPhone() +"\n"+ user.getGender(),
                Toast.LENGTH_SHORT).show();

        //progressViewDialog.hideDialog();

    }

}
