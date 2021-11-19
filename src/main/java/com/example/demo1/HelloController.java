package com.example.demo1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class HelloController {
    private ArrayList<MerchandiseItem> Stock;
    private ListView<MerchandiseItem> ListControl;

    private void loadStockItems() {
        List<String> allLines = null;

        try {
            Path itemsFilePath = Paths.get("ItemsForSale.txt");
            allLines = Files.readAllLines(itemsFilePath);
        } catch (IOException e) {
            System.out.println("Failed to read the items for sale file, Shutting down for now");
            System.exit(-1);
        }
        //if we got here we should have a real value in allLines
        for (var entry : allLines) {
            var entryValues = entry.split(",");
            //I've used a simple number to determine what type of merchandise this is
            //1 is food, 2 is clothing, and three is general merchandise
            ItemType thisItemsType = ItemType.Clothing;
            switch (entryValues[2]) {
                case "1" -> thisItemsType = ItemType.WICFood;
                case "2" -> thisItemsType = ItemType.Clothing; //this one is actually redundant, but included for completeness
                case "3" -> thisItemsType = ItemType.GeneralMerchandise;
            }
            var price = Double.parseDouble(entryValues[1]);
            var newItem = new MerchandiseItem(entryValues[0], price, thisItemsType);
            Stock.add(newItem);
        }
    }
    public void loadData(){
        ListView<MerchandiseItem> ListControl = new ListView<MerchandiseItem>();
        ObservableList<MerchandiseItem> items = FXCollections.observableArrayList (Stock);
        ListControl.setItems(items);
    }
}
