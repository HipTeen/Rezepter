package gui.controller

import java.text.NumberFormat
import javafx.beans.property.{SimpleDoubleProperty, SimpleStringProperty}
import javafx.collections.FXCollections
import javafx.scene.control.{ComboBox, TextField}
import javafx.scene.layout.GridPane

import data.IngredientItem

/**
 *
 */
class MainWindowController(val ingredientPane: GridPane) {

  var items: List[IngredientItem] = Nil

  def createAmountField: TextField = {
    val amountField: TextField = new TextField()
    amountField.setMaxWidth(40)
    amountField
  }

  def createUnitField: ComboBox[String] = {
    new ComboBox[String](FXCollections.observableArrayList(
      "g", "kg", "ml", "l", "Prise", "TL", "EL"))
  }

  def createIngredientField: TextField = {
    val ingredientField: TextField = new TextField()
    ingredientField.setPrefWidth(200)
    ingredientField
  }

  def createIngredientRow: GridPane = {
    val item: IngredientItem = new IngredientItem(new SimpleStringProperty(), new SimpleStringProperty(),
      new SimpleDoubleProperty())
    restoreIngredientRow(item)
  }

  def reset() : Unit = {
    items = Nil
  }

  def restoreIngredientRow(item: IngredientItem):GridPane = {
    val rowPane: GridPane = new GridPane
    val amountField: TextField = createAmountField
    val unitField: ComboBox[String] = createUnitField
    val ingredientField: TextField = createIngredientField
    amountField.textProperty().bindBidirectional(item.amount, NumberFormat.getNumberInstance)
    unitField.valueProperty().bindBidirectional(item.unit)
    ingredientField.textProperty().bindBidirectional(item.ingredient)

    items = items :+  item

    rowPane.add(amountField, 0, 0)
    rowPane.add(unitField, 1, 0)
    rowPane.add(ingredientField, 2, 0)
    rowPane
  }

}
