package org.example.db_homeassignment;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class HelloController {
    @FXML
    private ComboBox<String> languageComboBox;
    @FXML
    private TableView<Product> productTableView;
    @FXML
    private TableColumn<Product, Integer> idColumn;
    @FXML
    private TableColumn<Product, String> nameColumn;
    @FXML
    private TableColumn<Product, String> descriptionColumn;

    @FXML
    public void initialize() {
        languageComboBox.setItems(FXCollections.observableArrayList("English", "French"));
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
    }

    @FXML
    protected void onLoadProductsClick() {
        String selectedLanguage = languageComboBox.getValue();
        if (selectedLanguage != null) {
            loadProducts(selectedLanguage);
        }
    }

    private void loadProducts(String language) {
        String tableName = language.equals("English") ? "product_en" : "product_fr";
        ObservableList<Product> products = FXCollections.observableArrayList();

        try (Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/db_homeassignment", "root", "");
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                products.add(new Product(id, name, description));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        productTableView.setItems(products);
    }
}
