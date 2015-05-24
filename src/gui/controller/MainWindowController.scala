package gui.controller

import java.io.File
import java.text.NumberFormat
import javafx.beans.property.{SimpleDoubleProperty, SimpleStringProperty}
import javafx.collections.{FXCollections, ObservableList}
import javafx.scene.control.{ComboBox, ListView, TextField}
import javafx.scene.layout.GridPane

import data.IngredientItem

/**
 *
 */
class MainWindowController(val ingredientPane: GridPane, val recipeListView : ListView[String]) {

  var items: List[IngredientItem] = Nil

  def isFile(filename: String, pathToFile: String): Boolean = {
    val file: File = new File(pathToFile + filename)
    file.isFile
  }

  def getFiles(filenames: List[String], path : String): ObservableList[String] = {
    val obList : ObservableList[String] = FXCollections.observableArrayList()
    filenames.map(filename => if (isFile(filename, path)) obList.add(filename))
    obList
  }

  def getRecipeFileNames: ObservableList[String] = {
    val path = "/home/thorsten/Dokumente/recipes/"
    val dir : File = new File(path)
    getFiles(dir.list().toList, path)
  }

  def fillRecipeView() : Unit = {
    recipeListView.setItems(getRecipeFileNames)
  }

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
