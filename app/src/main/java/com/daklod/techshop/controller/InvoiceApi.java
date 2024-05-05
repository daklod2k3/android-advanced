package com.daklod.techshop.controller;

import android.util.Log;

import com.daklod.techshop.DTO.CUSTOMER;
import com.daklod.techshop.DTO.EMPLOYEE;
import com.daklod.techshop.DTO.INVOICE;
import com.daklod.techshop.DTO.INVOICE_DETAIL;
import com.daklod.techshop.DTO.INVOICE_STATUS;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public class InvoiceApi extends Api {

    private static final String TAG = "InvoiceApi";

    public interface InvoiceService {
        @GET("/api/v1/invoice")
        Call<List<INVOICE>> getAllInvoices();

        @POST("/api/v1/invoice")
        Call<INVOICE> createInvoice(@Body INVOICE invoice);

        @GET("/api/v1/invoice/{id}")
        Call<INVOICE> getInvoiceById(@Path("id") int id);

        @GET("/api/v1/invoice/{id}/details")
        Call<List<INVOICE_DETAIL>> getInvoiceDetailByInvoiceId(@Path("id") int id);

        @GET("/api/v1/invoice/status/{invoiceStatusId}")
        Call<INVOICE_STATUS> getStatusNameById(@Path("invoiceStatusId") int invoiceStatusId);

        @GET("/api/v1/customer/{customerId}")
        Call<CUSTOMER> getCustomerById(@Path("customerId") int customerId);

        @GET("/api/v1/employee/{employeeId}")
        Call<EMPLOYEE> getEmployeeById(@Path("employeeId") int employeeId);

    }

    private InvoiceService invoiceService;

    public InvoiceApi() {
        super();
        invoiceService = retrofit.create(InvoiceService.class);
    }

    // Lấy tất cả hóa đơn
    public List<INVOICE> getAllInvoices() {

        try {
            Response<List<INVOICE>> response = invoiceService.getAllInvoices().execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                // Xử lý lỗi khi request không thành công
                Log.e("InvoiceApi", "Request not successful: " + response.message());
                return null;
            }
        } catch (IOException e) {
            // Xử lý lỗi khi có lỗi trong quá trình kết nối hoặc đọc dữ liệu
            Log.e("InvoiceApi", "Error: " + e.getMessage());
            return null;
        }
    }

    // Thêm hóa đơn mới
    public INVOICE createInvoice(INVOICE invoice) {
        try {
            Response<INVOICE> response = invoiceService.createInvoice(invoice).execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                Log.e("InvoiceApi", "Request not successful: " + response.message());
                return null;
            }
        } catch (IOException e) {
            Log.e("InvoiceApi", "Error: " + e.getMessage());
            return null;
        }
    }

    // Lấy hóa đơn theo id
    public INVOICE getInvoiceById(int invoiceId) {
        try {
            Response<INVOICE> response = invoiceService.getInvoiceById(invoiceId).execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                // Xử lý lỗi khi request không thành công
                Log.e("InvoiceApi", "Request not successful: " + response.message());
                return null;
            }
        } catch (IOException e) {
            // Xử lý lỗi khi có lỗi trong quá trình kết nối hoặc đọc dữ liệu
            Log.e("InvoiceApi", "Error: " + e.getMessage());
            return null;
        }
    }

    // Lấy chi tiết hóa đơn theo id
    public List<INVOICE_DETAIL> getInvoiceDetailByInvoiceId(int invoiceId) {
        try {
            Response<List<INVOICE_DETAIL>> response = invoiceService.getInvoiceDetailByInvoiceId(invoiceId).execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                // Xử lý lỗi khi request không thành công
                Log.e("InvoiceApi", "Request not successful: " + response.message());
                return null;
            }
        } catch (IOException e) {
            // Xử lý lỗi khi có lỗi trong quá trình kết nối hoặc đọc dữ liệu
            Log.e("InvoiceApi", "Error: " + e.getMessage());
            return null;
        }
    }

    // Lấy trạng thái hóa đơn theo invoiceStatusId
    public void getStatusNameById(int statusId, final StatusNameCallback callback) {
        InvoiceService statusService = retrofit.create(InvoiceService.class);
        Call<INVOICE_STATUS> call = statusService.getStatusNameById(statusId);

        call.enqueue(new Callback<INVOICE_STATUS>() {
            @Override
            public void onResponse(Call<INVOICE_STATUS> call, Response<INVOICE_STATUS> response) {
                if (response.isSuccessful()) {
                    INVOICE_STATUS status = response.body();
                    if (status != null) {
                        callback.onStatusNameReceived(status.getName());
                    } else {
                        callback.onStatusNameError("Status not found");
                    }
                } else {
                    Log.e(TAG, "Request not successful: " + response.message());
                    callback.onStatusNameError("Request not successful");
                }
            }

            @Override
            public void onFailure(Call<INVOICE_STATUS> call, Throwable t) {
                Log.e(TAG, "Error: " + t.getMessage());
                callback.onStatusNameError("Error: " + t.getMessage());
            }
        });
    }

    // Lấy thông tin khách hàng theo ID
    public void getCustomerById(int customerId, final CustomerCallback callback) {
        InvoiceService customerService = retrofit.create(InvoiceService.class);

        customerService.getCustomerById(customerId).enqueue(new Callback<CUSTOMER>() {
            @Override
            public void onResponse(Call<CUSTOMER> call, Response<CUSTOMER> response) {
                if (response.isSuccessful()) {
                    callback.onCustomerReceived(response.body());
                } else {
                    callback.onFailure("Request not successful: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<CUSTOMER> call, Throwable t) {
                callback.onFailure("Error: " + t.getMessage());
            }
        });
    }

    // Lấy thông tin nhân viên theo ID
    public void getEmployeeById(int employeeId, final EmployeeCallback callback) {
        InvoiceService employeeService = retrofit.create(InvoiceService.class);

        employeeService.getEmployeeById(employeeId).enqueue(new Callback<EMPLOYEE>() {
            @Override
            public void onResponse(Call<EMPLOYEE> call, Response<EMPLOYEE> response) {
                if (response.isSuccessful()) {
                    callback.onEmployeeReceived(response.body());
                } else {
                    callback.onFailure("Request not successful: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<EMPLOYEE> call, Throwable t) {
                callback.onFailure("Error: " + t.getMessage());
            }
        });
    }

    public interface StatusNameCallback {
        void onStatusNameReceived(String statusName);

        void onStatusNameError(String errorMessage);
    }

    public interface CustomerCallback {
        void onCustomerReceived(CUSTOMER customer);

        void onFailure(String errorMessage);
    }

    public interface EmployeeCallback {
        void onEmployeeReceived(EMPLOYEE employee);

        void onFailure(String errorMessage);
    }

}
