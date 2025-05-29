package com.example.project4.controllers;

import com.example.project4.models.ProductItem;
import com.example.project4.repositories.OrderHistoryRepository;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

import java.time.LocalDate;
import java.util.List;

public class AdminSaleController {

    @FXML
    private BarChart<String, Number> orderChart;

    @FXML
    private LineChart<String, Number> incomeChart; // ✅ Changed from BarChart to LineChart

    @FXML
    private Label totalOrder;

    @FXML
    private Label todayIncome;

    @FXML
    private Label totalIncome;

    private final OrderHistoryRepository repository = new OrderHistoryRepository();

    @FXML
    public void initialize() {
        List<ProductItem> allOrders = repository.getAllOrders();

        int totalOrdersCount = 0;
        double totalIncomeSum = 0;
        double todayIncomeSum = 0;

        XYChart.Series<String, Number> orderSeries = new XYChart.Series<>();
        XYChart.Series<String, Number> incomeSeries = new XYChart.Series<>();
        orderSeries.setName("Orders");
        incomeSeries.setName("Income");

        for (ProductItem item : allOrders) {
            String date = item.getDateTime().split(" ")[0]; // Assumes format: "YYYY-MM-DD HH:mm"
            double itemTotal = item.getQuantity() * item.getPrice();

            totalOrdersCount++;
            totalIncomeSum += itemTotal;
            if (date.equals(LocalDate.now().toString())) {
                todayIncomeSum += itemTotal;
            }

            XYChart.Data<String, Number> orderData = findData(orderSeries, date);
            XYChart.Data<String, Number> incomeData = findData(incomeSeries, date);

            if (orderData != null) {
                orderData.setYValue(orderData.getYValue().intValue() + item.getQuantity());
            } else {
                orderSeries.getData().add(new XYChart.Data<>(date, item.getQuantity()));
            }

            if (incomeData != null) {
                incomeData.setYValue(incomeData.getYValue().doubleValue() + itemTotal);
            } else {
                incomeSeries.getData().add(new XYChart.Data<>(date, itemTotal));
            }
        }

        orderChart.getData().clear();
        orderChart.getData().add(orderSeries);

        incomeChart.getData().clear(); // ✅ Works with LineChart too
        incomeChart.getData().add(incomeSeries);

        totalOrder.setText(String.valueOf(totalOrdersCount));
        totalIncome.setText(String.format("₱%.2f", totalIncomeSum));
        todayIncome.setText(String.format("₱%.2f", todayIncomeSum));
    }

    private XYChart.Data<String, Number> findData(XYChart.Series<String, Number> series, String date) {
        for (XYChart.Data<String, Number> data : series.getData()) {
            if (data.getXValue().equals(date)) {
                return data;
            }
        }
        return null;
    }
}
