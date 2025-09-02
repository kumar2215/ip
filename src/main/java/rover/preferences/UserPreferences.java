package rover.preferences;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Represents the user preferences.
 */
public class UserPreferences {

    private ObjectNode jsonNode;

    /**
     * Creates a new UserPreferences object with default values.
     */
    public UserPreferences() {
        ObjectMapper objectMapper = new ObjectMapper();
        this.jsonNode = objectMapper.createObjectNode();
        this.jsonNode.put("name", "");
        this.jsonNode.put("userImage", "default");
        this.jsonNode.put("roverImage", "default");
    }

    /**
     * Creates a new UserPreferences object.
     *
     * @param jsonNode The JSON node representing the user preferences.
     */
    public UserPreferences(ObjectNode jsonNode) {
        this();
        if (jsonNode != null) {
            this.jsonNode = jsonNode;
        }
    }

    /**
     * Sets the name of the user.
     *
     * @param name The name of the user.
     * @return True if the name is successfully set, false otherwise.
     */
    public boolean setName(String name) {
        jsonNode.put("name", name);
        return jsonNode.get("name").asText().equals(name);
    }

    /**
     * Sets the image of the user.
     *
     * @param userImagePath The path to the image of the user.
     * @return True if the image is successfully set, false otherwise.
     */
    public boolean setUserImage(String userImagePath) {
        jsonNode.put("userImage", userImagePath);
        return jsonNode.get("userImage").asText().equals(userImagePath);
    }

    /**
     * Sets the image of the rover.
     *
     * @param roverImagePath The path to the image of the rover.
     * @return True if the image is successfully set, false otherwise.
     */
    public boolean setRoverImage(String roverImagePath) {
        jsonNode.put("roverImage", roverImagePath);
        return jsonNode.get("roverImage").asText().equals(roverImagePath);
    }

    /**
     * Returns the JSON node representing the user preferences.
     *
     * @return The JSON node representing the user preferences.
     */
    public JsonNode getJsonNode() {
        return jsonNode;
    }
}
