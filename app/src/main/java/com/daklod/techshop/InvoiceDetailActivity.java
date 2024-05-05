package com.daklod.techshop;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rajendra.techshop.DTO.CUSTOMER;
import com.rajendra.techshop.DTO.EMPLOYEE;
import com.daklod.techshop.DTO.INVOICE;
import com.daklod.techshop.adapter.InvoiceDetailAdapter;
import com.daklod.techshop.controller.InvoiceApi;
import com.daklod.techshop.model.Item;

import java.util.List;

public class InvoiceDetailActivity extends AppCompatActivity {

    private TextView txtInvoiceId, txtCustomer, txtEmployee, txtStatus, txtTotal;
    private RecyclerView recyclerViewItems;
    private InvoiceDetailAdapter adapter;

    // Khai báo các service để gọi API
    private InvoiceApi invoiceApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_detail);

        // Khởi tạo đối tượng InvoiceApi
        invoiceApi = new InvoiceApi();

        // Khởi tạo toolbar và hiển thị nút back
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Ánh xạ các view từ layout
        txtInvoiceId = findViewById(R.id.txtInvoiceId);
        txtCustomer = findViewById(R.id.txtCustomer);
        txtEmployee = findViewById(R.id.txtEmployee);
        txtStatus = findViewById(R.id.txtStatus);
        txtTotal = findViewById(R.id.txtTotal);
        recyclerViewItems = findViewById(R.id.recyclerViewItems);

        // Nhận đối tượng INVOICE từ Intent
        INVOICE invoice = getIntent().getParcelableExtra("invoice");

        if (invoice != null) {
            // Hiển thị thông tin chi tiết của đơn hàng
            txtInvoiceId.setText("Đơn hàng: " + invoice.getInvoice_id());
            txtTotal.setText(String.format("%,.0f đ", invoice.getTotal()));

            // Gọi API để lấy thông tin khách hàng dựa trên customer_id
            invoiceApi.getCustomerById(invoice.getCustomer_id(), new InvoiceApi.CustomerCallback() {
                @Override
                public void onCustomerReceived(CUSTOMER customer) {
                    if (customer != null) {
                        String customerInfo = "Thông tin khách hàng: \n"
                                + customer.getName()
                                + "\n" + customer.getPhone()
                                + "\n" + customer.getAddress();
                        txtCustomer.setText(customerInfo);
                    } else {
                        txtCustomer.setText("Không có thông tin khách hàng");
                    }
                }

                @Override
                public void onFailure(String errorMessage) {
                    txtCustomer.setText("Lỗi khi gọi API khách hàng");
                }
            });

            // Gọi API để lấy thông tin nhân viên dựa trên employee_id
            invoiceApi.getEmployeeById(invoice.getEmployee_id(), new InvoiceApi.EmployeeCallback() {
                @Override
                public void onEmployeeReceived(EMPLOYEE employee) {
                    if (employee != null) {
                        txtEmployee.setText("Nhân viên: " + employee.getName());
                    } else {
                        txtEmployee.setText("Không có thông tin nhân viên");
                    }
                }

                @Override
                public void onFailure(String errorMessage) {
                    txtEmployee.setText("Lỗi khi gọi API nhân viên");
                }
            });

            // Gọi API để lấy thông tin trạng thái hóa đơn dựa trên status_id
            invoiceApi.getStatusNameById(invoice.getStatus_id(), new InvoiceApi.StatusNameCallback() {
                @Override
                public void onStatusNameReceived(String statusName) {
                    txtStatus.setText(statusName);
                }

                @Override
                public void onStatusNameError(String errorMessage) {
                    txtStatus.setText("Không có thông tin trạng thái");
                }
            });

            // Hiển thị danh sách các mặt hàng trong đơn hàng trên RecyclerView
            List<Item> itemList = invoice.getItem();
            recyclerViewItems.setLayoutManager(new LinearLayoutManager(this));
            adapter = new InvoiceDetailAdapter(this, itemList);
            recyclerViewItems.setAdapter(adapter);
        } else {
            // Xử lý khi không nhận được đối tượng INVOICE hợp lệ
            Toast.makeText(this, "Invalid Invoice", Toast.LENGTH_SHORT).show();
            finish(); // Đóng activity nếu không có thông tin đơn hàng hợp lệ
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed(); // Quay lại activity trước đó
        setResult(RESULT_CANCELED); // Trả kết quả về cho HistoryFragment
        finish(); // Đóng InvoiceDetailActivity
        return true;
    }

}
