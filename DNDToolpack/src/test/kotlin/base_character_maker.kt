import javafx.beans.property.Property
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.control.*
import javafx.scene.layout.BorderPane
import javafx.scene.layout.Pane
import javafx.scene.text.Text
import org.jetbrains.uast.values.UBooleanConstant
import tornadofx.*
import java.util.function.Predicate


class CharacterMaker: App(base_character_maker::class)

class base_character_maker: View() {
    override val root: TabPane by fxml()
    val new_creature: Button by fxid()
    val delete_creature: Button by fxid()
    val creature_name_list: ListView<String> by fxid()
    val controller: MyController by inject()
    val race_selector: ComboBox<String> by fxid()
    val subrace_selector: ComboBox<String> by fxid()


    init {

        new_creature.setOnAction { openInternalWindow<name_creature>() }
        creature_name_list.selectionModel.selectionMode = SelectionMode.SINGLE
        race_selector.items.addAll("Orc", "Human", "Elf")
        race_selector.setOnAction { race_selected() }
        subrace_selector.setOnAction { subrace_selected() }
        creature_name_list.setOnMouseClicked { name_selected() }



    }

    fun name_selected(){

        if(creature_name_list.selectionModel.selectedIndex < 0){

        }
        else race_selector.selectionModel.select(controller.creature_list[creature_name_list.selectionModel.selectedIndex].race)



    }

    fun remove_creature() {

        if(creature_name_list.selectionModel.selectedIndex < 0){

        }
        else controller.remove_creature(creature_name_list.selectionModel.selectedIndex)
    }

    fun race_selected() {

        print(creature_name_list.selectionModel.selectedIndex)

        if(creature_name_list.selectionModel.selectedIndex < 0){

        }
        else controller.add_race(creature_name_list.selectionModel.selectedIndex, race_selector.selectedItem.toString())

        if(race_selector.selectedItem == "Elf"){

            subrace_selector.items.clear()
            subrace_selector.items.addAll("Wood", "High")
        }
        else if(race_selector.selectedItem == "Human"){
            subrace_selector.items.clear()
            subrace_selector.items.addAll("Totally Normal", "2 Armed")
        }
        else{
            subrace_selector.items.clear()
        }

    }

    fun subrace_selected(){

        if(creature_name_list.selectionModel.selectedIndex < 0){

        }
        else {
            controller.add_subrace(creature_name_list.selectionModel.selectedIndex, subrace_selector.selectedItem.toString())

        }


    }

    class Creature(name: String, race: String, subrace: String) {
        val nameProperty = SimpleStringProperty(name)
        var name by nameProperty
        val raceProperty = SimpleStringProperty(race)
        var race by raceProperty
        val subraceProperty = SimpleStringProperty(subrace)
        var subrace by subraceProperty

        fun stringout(): String = name + ", The " + subrace + " " + race

    }

    class name_creature : View() {

        override val root: Pane by fxml()
        val name_input: TextField by fxid()
        val name_enter: Button by fxid()
        val controller: MyController by inject()

        init {


        }

        fun name_entered() {

            controller.add_name(name_input.text)
            name_input.text = ""
            close()
        }


    }

    class MyController : Controller() {
        var creature_list: MutableList<Creature> = mutableListOf()
        val mainpage: base_character_maker by inject()

        fun add_name(temp: String) {

            creature_list.add(Creature(temp, "Unknown", "Unknown"))
            print(creature_list.last().stringout())
            mainpage.creature_name_list.items.add(creature_list.last().stringout())


        }

        fun remove_creature(creature_to_remove: Int) {

            creature_list.removeAt(creature_to_remove)
            mainpage.creature_name_list.items.removeAt(creature_to_remove)

        }

        fun add_race(creature_index: Int, race: String) {

            creature_list[creature_index].raceProperty.set(race)
            mainpage.creature_name_list.items.set(creature_index, creature_list[creature_index].stringout())
        }

        fun add_subrace(creature_index: Int, subrace: String){

            creature_list[creature_index].subraceProperty.set(subrace)
            mainpage.creature_name_list.items.set(creature_index,creature_list[creature_index].stringout())
        }


    }
}