import com.intellij.configurationStore.getPaths
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
    val deletecharacter_button: Button by fxid()
    val racechange_button: Button by fxid()
    val subracechange_button: Button by fxid()
    val controller: MyController by inject()
    val race_chosen: Label by fxid()
    val subrace_chosen: Label by fxid()
    val currentCreature_label: Label by fxid()

    val character_box: ChoiceBox<String> by fxid()


    init {

        newcharacter_button.setOnAction { openInternalWindow<name_creature>() }
        racechange_button.setOnAction { openInternalWindow<race_picker>() }
        characterselect_button.setOnAction { controller.select_creature() }
        subracechange_button.setOnAction { openInternalWindow<subrace_picker>() }
        deletecharacter_button.setOnAction { controller.delete_character() }


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

    class race_picker : Fragment() {

        override val root: Pane by fxml()
        val race_selector: ChoiceBox<String> by fxid()
        val raceselector_button: Button by fxid()
        val controller: MyController by inject()

        init {

            race_selector.items.clear()
            race_selector.items.addAll(controller.race_list)
            race_selector.selectionModel.select(controller.currentCreatureRace())



            raceselector_button.setOnAction { go() }
        }
        fun go(){
            controller.add_race(race_selector.selectionModel.selectedItem.toString())
            race_selector.selectionModel.clearSelection()
            close()
        }
    }

    class subrace_picker : Fragment() {

        override val root: Pane by fxml()
        val subrace_selector: ChoiceBox<String> by fxid()
        val subraceselector_button: Button by fxid()
        val controller: MyController by inject()

        init {

            subraceselector_button.setOnAction { go() }
            subrace_selector.items.addAll(controller.subrace_list_builder())
            for(i in (0..subrace_selector.items.size-1)){
                if(subrace_selector.items[i]== controller.character_list[controller.currentCreature.value].subrace){
                    subrace_selector.selectionModel.select(i)
                }
            }
        }



        fun go(){
            controller.add_subrace(subrace_selector.selectionModel.selectedItem.toString())
            subrace_selector.selectionModel.clearSelection()
            close()
        }
    }


    class MyController : Controller() {
        val mainpage: character_builder20 by inject()
        var character_list: MutableList<Creature> = mutableListOf()
        val race_list = mutableListOf<String>()
        var currentCreature = SimpleIntegerProperty()
        var current: Int by currentCreature

        init{

                print((File(".").absolutePath))

                File(".\\DNDToolpack\\src\\test\\resources\\races.txt").readLines().forEach{

                    var result: List<String> = it.split(",").map { it.trim() }



                   race_list.add(result[0])
                }
            
            }


            fun add_name(input: String) {

                character_list.add(Creature(input, "Creature", "Generic"))
                mainpage.character_box.items.add(character_list.last().stringout())
                mainpage.character_box.selectionModel.selectLast()
                select_creature()


            }

            fun delete_character(){

                character_list.removeAt(currentCreature.value)
                mainpage.character_box.items.clear()
                for(i in 0..character_list.lastIndex){
                    mainpage.character_box.items.add(character_list[i].stringout())
                }
                mainpage.currentCreature_label.text = "None"
                select_creature()

            }
            fun add_race(input: String){

                if(input!=character_list[currentCreature.value].race){

                    character_list[currentCreature.value].subraceProperty.set("Generic")
                    mainpage.subrace_chosen.text = character_list[currentCreature.value].subrace
                }

                character_list[currentCreature.value].raceProperty.set(input)
                mainpage.character_box.items[currentCreature.value] = character_list[currentCreature.value].stringout()
                mainpage.race_chosen.text = input
                mainpage.character_box.selectionModel.select(currentCreature.value)


            }

            fun currentCreatureRace(): Int{

                var out = 0

                for(i in 0..race_list.lastIndex){

                    if(character_list[currentCreature.value].race == race_list[i]) out=i
                }
                return out
            }

            fun select_creature(){

                if(mainpage.character_box.selectionModel.isEmpty) currentCreature.set(0)
                else {
                    currentCreature.set(mainpage.character_box.selectionModel.selectedIndex)
                }
                print(currentCreature)
                mainpage.race_chosen.text = character_list[currentCreature.value].race
                mainpage.subrace_chosen.text = character_list[currentCreature.value].subrace
                mainpage.currentCreature_label.text = "Current: " + character_list[currentCreature.value].name
                mainpage.character_box.selectionModel.select(currentCreature.value)

                }

            fun subrace_list_builder():MutableList<String>{

                val subrace_list = mutableListOf<String>()

                File(".\\DNDToolpack\\src\\test\\resources\\races.txt").readLines().forEach{

                    var result: List<String> = it.split(",").map { it.trim() }

                    if(result[0]==character_list[currentCreature.value].race){

                        for (i in 1..(result.size-1)) subrace_list.add(result[i])
                    }




                }
                print(subrace_list)
                return subrace_list
            }

            fun add_subrace(input: String){

                character_list[currentCreature.value].subraceProperty.set(input)
                mainpage.character_box.items[currentCreature.value] = character_list[currentCreature.value].stringout()
                mainpage.subrace_chosen.text = input
                mainpage.character_box.selectionModel.select(currentCreature.value)

            }


        }
    }
