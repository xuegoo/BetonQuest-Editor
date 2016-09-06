/**
 * 
 */
package pl.betoncraft.betonquest.editor.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import pl.betoncraft.betonquest.editor.BetonQuestEditor;
import pl.betoncraft.betonquest.editor.custom.DraggableListCell;
import pl.betoncraft.betonquest.editor.data.Instruction;
import pl.betoncraft.betonquest.editor.model.Condition;
import pl.betoncraft.betonquest.editor.model.Event;
import pl.betoncraft.betonquest.editor.model.Objective;

/**
 * Controls "Events, conditions and objectives" tab.
 *
 * @author Jakub Sapalski
 */
public class EcoController {
	
	private static EcoController instance;
	
	@FXML private ListView<Event> eventsList;
	@FXML private ListView<Condition> conditionsList;
	@FXML private ListView<Objective> objectivesList;

	@FXML Label instruction;
	
	public EcoController() {
		instance = this;
	}

	public static void setEvents(ObservableList<Event> events) {
		instance.eventsList.setCellFactory(param -> new DraggableListCell<>());
		instance.eventsList.setItems(events);
		instance.eventsList.getSelectionModel().selectedItemProperty().addListener(event -> {
			instance.update();
		});
	}
	
	public static void setConditions(ObservableList<Condition> conditions) {
		instance.conditionsList.setCellFactory(param -> new DraggableListCell<>());
		instance.conditionsList.setItems(conditions);
		instance.conditionsList.getSelectionModel().selectedItemProperty().addListener(event -> {
			instance.update();
		});
	}
	
	public static void setObjectives(ObservableList<Objective> objectives) {
		instance.objectivesList.setCellFactory(param -> new DraggableListCell<>());
		instance.objectivesList.setItems(objectives);
		instance.objectivesList.getSelectionModel().selectedItemProperty().addListener(event -> {
			instance.update();
		});
	}
	
	@FXML private void update() {
		Node focused = instruction.getScene().getFocusOwner();
		if (focused instanceof ListView<?>) {
			ListView<?> list = (ListView<?>) focused;
			Object object = list.getSelectionModel().getSelectedItem();
			if (object != null) {
				Instruction item = (Instruction) object;
				String name = null;
				switch (item.getClass().getSimpleName()) {
				case "Event":
					name = BetonQuestEditor.getInstance().getLanguage().getString("event");
					break;
				case "Condition":
					name = BetonQuestEditor.getInstance().getLanguage().getString("condition");
					break;
				case "Objective":
					name = BetonQuestEditor.getInstance().getLanguage().getString("objective");
					break;
				}
				if (name != null) {
					instruction.setText(name + " '" + item.getId().get() + "': " + item.getInstruction().get());
				}
			}
		}
	}
	
	@FXML private void addEvent() {
		try {
			Event event = new Event(BetonQuestEditor.getInstance().getDisplayedPackage(), new String());
			if (event.edit()) {
				event.setIndex(eventsList.getItems().size());
				eventsList.getItems().add(event);
			}
		} catch (Exception e) {
			ExceptionController.display(e);
		}
	}
	
	@FXML private void addCondition() {
		try {
			Condition condition = new Condition(BetonQuestEditor.getInstance().getDisplayedPackage(), new String());
			if (condition.edit()) {
				condition.setIndex(conditionsList.getItems().size());
				conditionsList.getItems().add(condition);
			}
		} catch (Exception e) {
			ExceptionController.display(e);
		}
	}
	
	@FXML private void addObjective() {
		try {
			Objective objective = new Objective(BetonQuestEditor.getInstance().getDisplayedPackage(), new String());
			if (objective.edit()) {
				objective.setIndex(objectivesList.getItems().size());
				objectivesList.getItems().add(objective);
			}
		} catch (Exception e) {
			ExceptionController.display(e);
		}
	}
	
	@FXML private void editEvent() {
		try {
			Event event = eventsList.getSelectionModel().getSelectedItem();
			if (event != null) {
				event.edit();
			}
		} catch (Exception e) {
			ExceptionController.display(e);
		}
	}
	
	@FXML private void editCondition() {
		try {
			Condition condition = conditionsList.getSelectionModel().getSelectedItem();
			if (condition != null) {
				condition.edit();
			}
		} catch (Exception e) {
			ExceptionController.display(e);
		}
	}
	
	@FXML private void editObjective() {
		try {
			Objective objective = objectivesList.getSelectionModel().getSelectedItem();
			if (objective != null) {
				objective.edit();
			}
		} catch (Exception e) {
			ExceptionController.display(e);
		}
	}
	
	@FXML private void delEvent() {
		try {
			Event event = eventsList.getSelectionModel().getSelectedItem();
			if (event != null) {
				eventsList.getItems().remove(event);
			}
			BetonQuestEditor.getInstance().refresh();
		} catch (Exception e) {
			ExceptionController.display(e);
		}
	}
	
	@FXML private void delCondition() {
		try {
			Condition condition = conditionsList.getSelectionModel().getSelectedItem();
			if (condition != null) {
				conditionsList.getItems().remove(condition);
			}
			BetonQuestEditor.getInstance().refresh();
		} catch (Exception e) {
			ExceptionController.display(e);
		}
	}
	
	@FXML private void delObjective() {
		try {
			Objective objective = objectivesList.getSelectionModel().getSelectedItem();
			if (objective != null) {
				objectivesList.getItems().remove(objective);
			}
			BetonQuestEditor.getInstance().refresh();
		} catch (Exception e) {
			ExceptionController.display(e);
		}
	}
	
	private void keyAction(KeyEvent event, Action add, Action edit, Action delete) {
		if (event.getCode() == KeyCode.DELETE) {
			if (delete != null) {
				delete.act();
			}
			event.consume();
			return;
		}
		if (event.getCode() == KeyCode.ENTER) {
			if (edit != null) {
				edit.act();
			}
			event.consume();
			return;
		}
		KeyCombination combintation = new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN);
		if (combintation.match(event)) {
			if (add != null) {
				add.act();
			}
			event.consume();
			return;
		}
	}
	
	private interface Action {
		void act();
	}

	@FXML public void eventKey(KeyEvent event) {
		keyAction(event, () -> addEvent(), () -> editEvent(), () -> delEvent());
	}

	@FXML public void conditionKey(KeyEvent event) {
		keyAction(event, () -> addCondition(), () -> editCondition(), () -> delCondition());
	}

	@FXML public void objectiveKey(KeyEvent event) {
		keyAction(event, () -> addObjective(), () -> editObjective(), () -> delObjective());
	}

	public static void clear() {
		instance.eventsList.setItems(FXCollections.observableArrayList());
		instance.conditionsList.setItems(FXCollections.observableArrayList());
		instance.objectivesList.setItems(FXCollections.observableArrayList());
	}
	
}
