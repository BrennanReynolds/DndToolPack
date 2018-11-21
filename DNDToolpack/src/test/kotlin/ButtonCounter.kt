import javafx.beans.property.SimpleIntegerProperty
import javafx.scene.control.Label
import javafx.scene.layout.BorderPane
import tornadofx.*

class CounterView : View() {
    override val root : BorderPane by fxml()
    val counter = SimpleIntegerProperty()
    val counterLabel: Label by fxid()

    init {
        counterLabel.bind(counter)
    }

    fun increment() {
        counter.value += 1
    }
}