package gui.view

import java.io.File
import javafx.application.Application
import javafx.beans.value.{ChangeListener, ObservableValue}
import javafx.collections.ObservableList
import javafx.event.{ActionEvent, EventHandler}
import javafx.scene.control.{Button, ListView, ScrollPane, TextArea}
import javafx.scene.layout._
import javafx.scene.{Node, Scene}
import javafx.stage.{FileChooser, Stage}

import gui.controller.{MainWindowController, RecipeIO}



/**
 *
 */
class MainWindow extends Application {

  override def start(stage: Stage): Unit = {
    stage.setTitle("FXML Welcome")
    val rootPane: BorderPane = new BorderPane

    val centerPane: VBox = new VBox()
    val ingredientPane: GridPane = new GridPane
    val scrollIngriedientPane: ScrollPane = new ScrollPane
    scrollIngriedientPane.setContent(ingredientPane)
    scrollIngriedientPane.setPrefHeight(300)

    val description: TextArea = new TextArea()
    description.prefHeight(300)

    centerPane.getChildren.add(scrollIngriedientPane)
    centerPane.getChildren.add(description)
    rootPane.setCenter(centerPane)

    val commandPane: HBox = new HBox()
    rootPane.setBottom(commandPane)


    val addButton: Button = new Button("Add")
    val recipeView : ListView[String] = new ListView[String]()
    val controller: MainWindowController = new MainWindowController(ingredientPane, recipeView)
    addButton.setOnAction(new EventHandler[ActionEvent] {
      override def handle(t: ActionEvent): Unit = {
        val ingredientRows: ObservableList[Node] = ingredientPane.getChildren
        ingredientPane.add(controller.createIngredientRow, 0, ingredientRows.size())
      }
    })

    rootPane.setLeft(recipeView)
    controller.fillRecipeView()

    commandPane.getChildren.add(addButton)

    val saveButton: Button = new Button("Save")
    saveButton.setOnAction(new EventHandler[ActionEvent] {
      override def handle(t: ActionEvent): Unit = {
        val chooser = new FileChooser
        val file: File = chooser.showSaveDialog(stage)
        if (file != null) {
          RecipeIO.save(file, controller.items, description.getText)
        }
      }
    })

    val loadButton: Button = new Button("Load")
    loadButton.setOnAction(new EventHandler[ActionEvent] {
      override def handle(t: ActionEvent): Unit = {
        val chooser = new FileChooser
        val file: File = chooser.showOpenDialog(stage)
        if (file != null) {
          controller.reset()
          ingredientPane.getChildren.clear()
          RecipeIO.load(file, controller, description, ingredientPane)
        }
      }
    })

    recipeView.getSelectionModel.selectedItemProperty().addListener(
    new ChangeListener[String] {
      override def changed(observable: ObservableValue[_ <: String], oldValue: String, newValue: String): Unit = {
        RecipeIO.load(new File("/home/thorsten/Dokumente/recipes/" + newValue), controller, description, ingredientPane)
      }
    }
    )

    commandPane.getChildren.add(saveButton)
    commandPane.getChildren.add(loadButton)

    def scene: Scene = new Scene(rootPane, 800, 600)
    stage.setScene(scene)
    stage.show()
  }
}
