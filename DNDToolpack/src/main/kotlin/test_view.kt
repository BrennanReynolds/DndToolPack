
package demo

import javafx.scene.control.Label
import javafx.scene.layout.HBox
import tornadofx.App
import tornadofx.View

class HelloWorld : View() {
    override val root = HBox(Label("Hello world!"))
}

class HelloWorldApp : App() {
    override val primaryView = HelloWorld::class
}