package com.daklod.techshop.controller;

import com.daklod.techshop.DTO.CUSTOMER;
import com.daklod.techshop.DTO.EMPLOYEE;
import com.daklod.techshop.DTO.INVOICE;
import com.daklod.techshop.DTO.INVOICE_DETAIL;
import com.daklod.techshop.DTO.INVOICE_STATUS;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public class InvoiceApi extends Api {

    private InvoiceService invoiceService;
    private static final String TAG = "InvoiceApi";

    public InvoiceApi() {
        super();
        invoiceService = retrofit.create(InvoiceService.class);
    }

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

    public void getAllInvoices(final InvoiceCallback<List<INVOICE>> callback) {
        invoiceService.getAllInvoices().enqueue(new Callback<List<INVOICE>>() {
            @Override
            public void onResponse(Call<List<INVOICE>> call, Response<List<INVOICE>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Request not successful: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<INVOICE>> call, Throwable t) {
                callback.onError("Error: " + t.getMessage());
            }
        });
    }

    public void createInvoice(INVOICE invoice, final InvoiceCallback<INVOICE> callback) {
        invoiceService.createInvoice(invoice).enqueue(new Callback<INVOICE>() {
            @Override
            public void onResponse(Call<INVOICE> call, Response<INVOICE> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Request not successful: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<INVOICE> call, Throwable t) {
                callback.onError("Error: " + t.getMessage());
            }
        });
    }

    public void getInvoiceById(int invoiceId, final InvoiceCallback<INVOICE> callback) {
        invoiceService.getInvoiceById(invoiceId).enqueue(new Callback<INVOICE>() {
            @Override
            public void onResponse(Call<INVOICE> call, Response<INVOICE> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Request not successful: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<INVOICE> call, Throwable t) {
                callback.onError("Error: " + t.getMessage());
            }
        });
    }

    public void getInvoiceDetailByInvoiceId(int invoiceId, final InvoiceCallback<List<INVOICE_DETAIL>> callback) {
        invoiceService.getInvoiceDetailByInvoiceId(invoiceId).enqueue(new Callback<List<INVOICE_DETAIL>>() {
            @Override
            public void onResponse(Call<List<INVOICE_DETAIL>> call, Response<List<INVOICE_DETAIL>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Request not successful: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<INVOICE_DETAIL>> call, Throwable t) {
                callback.onError("Error: " + t.getMessage());
            }
        });
    }

    public void getStatusNameById(int statusId, final StatusNameCallback callback) {
        invoiceService.getStatusNameById(statusId).enqueue(new Callback<INVOICE_STATUS>() {
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
                    callback.onStatusNameError("Request not successful: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<INVOICE_STATUS> call, Throwable t) {
                callback.onStatusNameError("Error: " + t.getMessage());
            }
        });
    }

    public void getCustomerById(int customerId, final CustomerCallback callback) {
        invoiceService.getCustomerById(customerId).enqueue(new Callback<CUSTOMER>() {
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

    public void getEmployeeById(int employeeId, final EmployeeCallback callback) {
        invoiceService.getEmployeeById(employeeId).enqueue(new Callback<EMPLOYEE>() {
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

    public interface InvoiceCallback<T> {
        void onSuccess(T data);

        void onError(String errorMessage);
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
