package rover.storage;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Represents a writer that writes tasks to a JSON file.
 */
public class JsonFileManager {

    private final ObjectMapper objectMapper;

    /**
     * Creates a new JsonFileManager object.
     */
    public JsonFileManager() {
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Loads the JSON node from the specified file path.
     *
     * @param preferencesFilePath The file path to load the JSON node from.
     * @return The JSON node loaded from the file path.
     * @throws IOException If an I/O error occurs.
     */
    public JsonNode load(String preferencesFilePath) throws IOException {
        return objectMapper.readTree(new File(preferencesFilePath));
    }

    /**
     * Writes the specified JSON node to the specified file path.
     *
     * @param jsonNode The JSON node to write.
     * @param preferencesFilePath The file path to write the JSON node to.
     * @throws IOException If an I/O error occurs.
     */
    public void write(JsonNode jsonNode, String preferencesFilePath) throws IOException {
        objectMapper.writeValue(new File(preferencesFilePath), jsonNode);
    }

}
