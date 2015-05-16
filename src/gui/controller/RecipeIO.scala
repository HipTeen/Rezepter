package gui.controller

import java.io._
import javafx.beans.property.{SimpleDoubleProperty, SimpleStringProperty}
import javafx.collections.ObservableList
import javafx.scene.Node
import javafx.scene.control.TextArea
import javafx.scene.layout.GridPane

import data.IngredientItem

/**
 *
 */
object RecipeIO {

  def readIngredients(reader: BufferedReader, controller: MainWindowController, ingredientPane: GridPane): Unit = {
    val line: String = reader.readLine()
    if (!line.equals("###")) {
      val split: Array[String] = line.split(";")
      val row: GridPane = controller.restoreIngredientRow(new IngredientItem(
        new SimpleStringProperty(split(2)),
        new SimpleStringProperty(split(1)),
        new SimpleDoubleProperty(split(0).toDouble)))
      row
      val ingredientRows: ObservableList[Node] = ingredientPane.getChildren
      ingredientPane.add(row, 0, ingredientRows.size())
      readIngredients(reader, controller, ingredientPane)
    }
  }

  def readDescription(reader: BufferedReader): String = {
    if (reader.ready()) {
      reader.readLine() + "\n" + readDescription(reader)
    } else {
      ""
    }
  }

  def load(file: File, controller: MainWindowController, area: TextArea, ingredientPane: GridPane) = {
    val reader = new BufferedReader(new FileReader(file))
    ingredientPane.getChildren.clear()
    readIngredients(reader, controller, ingredientPane)
    area.setText(readDescription(reader))
  }


  def writeIngredient(items: List[IngredientItem], writer: BufferedWriter): Unit = {
    if (items.nonEmpty) {
      val item: IngredientItem = items.head
      writer.write(item.amount.doubleValue() + ";" + item.unit.getValueSafe + ";" + item.ingredient.getValueSafe)
      writer.newLine()
      writeIngredient(items.tail, writer)
    }
  }

  def save(file: File, items: List[IngredientItem], description: String): Unit = {
    file.createNewFile()
    val writer = new BufferedWriter(new FileWriter(file))
    writeIngredient(items, writer)
    writer.write("###")
    writer.newLine()
    writer.write(description)
    writer.close()
  }

}
