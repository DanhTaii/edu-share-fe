package com.example.edushare.feature.users;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.edushare.R;
import com.example.edushare.common.base.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        System.out.println(teFullname);

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

        System.out.println(fullname);

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
        User userRequest = new User();
        userRequest.setId(email); // Tạm thời dùng email làm ID
        userRequest.setFullName(fullname);
        userRequest.setEmail(email);

//        userRequest.setPhoneNumber(phone);
        // userRequest.setStudentCode(...); // Nếu có trường này thì set vào

        // 2. GỌI HÀM NÀY THÌ DỮ LIỆU MỚI BAY LÊN SERVER ĐƯỢC
        sendUserToBackend(userRequest);
    }

    private void sendUserToBackend(User user) {
        Log.d("DEBUG_API", "Bắt đầu gọi API cho User: " + user.getEmail());

        UserApi apiService = RetrofitClient.getUserApi();
        apiService.registerUser(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d("DEBUG_API", "Mã phản hồi: " + response.code()); // Thêm dòng này
                if (response.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Lưu DB MySQL thành công!", Toast.LENGTH_LONG).show();
                    // Bạn có thể mở Navicat lên F5 để xem kết quả ngay
                } else {
                    Toast.makeText(RegisterActivity.this, "Lỗi: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("RETROFIT_ERROR", "Lỗi kết nối: " + t.getMessage());
            }
        });
    }

}
