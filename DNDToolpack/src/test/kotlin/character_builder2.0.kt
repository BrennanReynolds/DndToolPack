import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import javafx.scene.control.*
import javafx.scene.layout.Pane
import tornadofx.*
import java.io.File

class CharacterMaker20: App(character_builder20::class)

class character_builder20: View() {

    override val root: TabPane by fxml()

    val characterselect_button: Button by fxid()
    val newcharacter_button: Button by fxid()
    val racechange_button: Button by fxid()
    val subracechange_button: Button by fxid()
    val controller: MyController by inject()
    val race_chosen: Label by fxid()
    val subrace_chosen: Label by fxid()

    val character_list: ChoiceBox<String> by fxid()


    init {

        newcharacter_button.setOnAction { openInternalWindow<name_creature>() }
        racechange_button.setOnAction { openInternalWindow<race_picker>() }


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

    class race_picker : View() {

        override val root: Pane by fxml()
        val race_selector: ChoiceBox<String> by fxid()
        val raceselector_button: Button by fxid()
        val controller: MyController by inject()

        init {

            race_selector.items.addAll(controller.race_list)

            raceselector_button.setOnAction { controller.add_race(race_selector.selectionModel.selectedItem.toString()) }
        }
    }

        class MyController : Controller() {
            val mainpage: character_builder20 by inject()
            var character_list: MutableList<Creature> = mutableListOf()
            val race_list = mutableListOf<String>()
            val currentCreature = SimpleIntegerProperty()
            var current: Int by currentCreature

            init{

                File("races.txt").readLines().forEach{

                    race_list.add(it)
                }
            }


            fun add_name(input: String) {

                character_list.add(Creature(input, "not picked", "not picked"))
                mainpage.character_list.items.add(character_list.last().stringout())


            }
            fun add_race(input: String){


            }


        }
    }
