package com.rajendra.techshop.controller;

import android.util.Log;

import com.rajendra.techshop.DTO.INVOICE;
import com.rajendra.techshop.DTO.INVOICE_DETAIL;
import com.rajendra.techshop.DTO.INVOICE_STATUS;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
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
        Call<INVOICE_STATUS> getInvoiceStatusById(@Path("invoiceStatusId") int invoiceStatusId);
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
    public INVOICE_STATUS getInvoiceStatusById(int invoiceStatusId) {
        try {
            Response<INVOICE_STATUS> response = invoiceService.getInvoiceStatusById(invoiceStatusId).execute();
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
}
