package main

import javafx.application.Application

import data.Ingredient
import data.unit.Gramm
import gui.view.MainWindow

/**
 *
 */
object HelloWorld {

  def main(args: Array[String]) {
    val ingredient = new Ingredient("Milch")
    val unit = new Gramm(500)
    println(unit)
    println(ingredient)
    Application.launch(classOf[MainWindow], args: _*)
  }

}
