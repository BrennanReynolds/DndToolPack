

import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.scene.control.ProgressBar
import javafx.scene.control.ProgressIndicator
import javafx.scene.layout.AnchorPane
import tornadofx.*



class crazytestview : View("My View") {
    override val root : AnchorPane by fxml()

    val counter = SimpleDoubleProperty(0.0)
    val testbar: ProgressBar by fxid()
    val testcircle: ProgressIndicator by fxid()

    init{

        testcircle.bind(counter)
        testbar.bind(counter)


    }

    fun increment(){
        counter.value += 0.1
        println("${counter.value}")
        if((counter.value > 0.9) && (counter.value < 1.0)) counter.value = 1.0
        if (counter.value > 1.0) counter.value = 0.0
    }



}
