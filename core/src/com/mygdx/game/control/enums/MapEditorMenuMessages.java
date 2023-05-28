package org.example.control.enums;

public enum MapEditorMenuMessages {
    EMPTY_WIDTH("width's field is empty"),
    EMPTY_HEIGHT("height's field is empty"),
    EMPTY_TYPE("type's field is empty"),
    EMPTY_COUNT("count's field is empty"),
    EMPTY_DIRECTION("direction's field is empty"),
    DIGIT_COORDINATES("you must enter digit for coordinates"),
    DIGIT_COUNT("you must enter digit for count"),
    ALREADY_BUILDING("there is a building in this place"),
    INVALID_COORDINATES("invalid coordinates"),
    INVALID_TYPE("invalid type"),
    INVALID_DIRECTION("invalid direction"),
    INVALID_BUILDING("there is no building with this name"),
    INVALID_TROOPS("there is no troops with this name"),
    INVALID_TILE_TYPE("invalid tile type"),
    BAD_COORDINATES("can't place here"),
    SUCCEED("operation succeed"),
    ALREADY_EXISTS("a map with this id already exists"),
    FAILED("task failed successfully");
    private final String message;

    MapEditorMenuMessages(String messages) {
        message = messages;
    }

    @Override
    public String toString() {
        return message;
    }
}
