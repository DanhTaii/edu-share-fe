package com.example.edushare;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    EditText teFullname, teEmail, tePhone, tePassword, teRewritePassword;
    Button btnCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        teFullname = findViewById(R.id.edt_fullname);
        teEmail = findViewById(R.id.edt_register_email);
        tePhone = findViewById(R.id.edt_phone);
        tePassword = findViewById(R.id.edt_register_password);
        teRewritePassword = findViewById(R.id.edt_register_password_rewrite);
        btnCreate = findViewById(R.id.btn_creaedt_account);

        btnCreate.setOnClickListener(v -> {
            validateForm();
        });
    }

    private void validateForm() {
        String fullname = teFullname.getText().toString().trim();
        String email = teEmail.getText().toString().trim();
        String phone = tePhone.getText().toString().trim();
        String password = tePassword.getText().toString().trim();
        String rewritePassword = teRewritePassword.getText().toString().trim();

        // 1. Fullname
        if (fullname.isEmpty()) {
            teFullname.setError("Không được để trống");
            teFullname.requestFocus();
            return;
        }

        if (fullname.length() < 2) {
            teFullname.setError("Tên quá ngắn");
            teFullname.requestFocus();
            return;
        }

        // 2. Email
        if (email.isEmpty()) {
            teEmail.setError("Email không được để trống");
            teEmail.requestFocus();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            teEmail.setError("Email không hợp lệ");
            teEmail.requestFocus();
            return;
        }

        // 3. Phone
        if (phone.isEmpty()) {
            tePhone.setError("Không được để trống");
            tePhone.requestFocus();
            return;
        }

        if (!phone.matches("\\d{10}")) {
            tePhone.setError("Số điện thoại phải 10 số");
            tePhone.requestFocus();
            return;
        }

        // 4. Password
        if (password.isEmpty()) {
            tePassword.setError("Không được để trống");
            tePassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            tePassword.setError("Mật khẩu tối thiểu 6 ký tự");
            tePassword.requestFocus();
            return;
        }

        // 5. Rewrite Password
        if (!rewritePassword.equals(password)) {
            teRewritePassword.setError("Mật khẩu không khớp");
            teRewritePassword.requestFocus();
            return;
        }

        // Nếu pass hết
        Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
    }

}
